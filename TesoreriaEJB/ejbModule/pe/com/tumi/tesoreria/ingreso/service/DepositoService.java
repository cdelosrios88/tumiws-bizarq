package pe.com.tumi.tesoreria.ingreso.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.AjusteMonto;
import pe.com.tumi.common.util.Constante;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiario;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiarioDetalle;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiarioDetalleId;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiarioId;
import pe.com.tumi.contabilidad.core.domain.Modelo;
import pe.com.tumi.contabilidad.core.domain.ModeloDetalle;
import pe.com.tumi.contabilidad.core.domain.PlanCuenta;
import pe.com.tumi.contabilidad.core.facade.ModeloFacadeRemote;
import pe.com.tumi.empresa.domain.Subsucursal;
import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.parametro.general.domain.Archivo;
import pe.com.tumi.seguridad.login.domain.Usuario;
import pe.com.tumi.tesoreria.banco.domain.Bancocuenta;
import pe.com.tumi.tesoreria.banco.domain.Bancofondo;
import pe.com.tumi.tesoreria.ingreso.bo.DepositoIngresoBO;
import pe.com.tumi.tesoreria.ingreso.bo.IngresoBO;
import pe.com.tumi.tesoreria.ingreso.bo.IngresoDetalleBO;
import pe.com.tumi.tesoreria.ingreso.domain.DepositoIngreso;
import pe.com.tumi.tesoreria.ingreso.domain.Ingreso;
import pe.com.tumi.tesoreria.ingreso.domain.IngresoDetalle;

public class DepositoService {

	protected static Logger log = Logger.getLogger(DepositoService.class);
	
	IngresoBO boIngreso = (IngresoBO)TumiFactory.get(IngresoBO.class);
	IngresoDetalleBO boIngresoDetalle = (IngresoDetalleBO)TumiFactory.get(IngresoDetalleBO.class);
	DepositoIngresoBO boDepositoIngreso = (DepositoIngresoBO)TumiFactory.get(DepositoIngresoBO.class);
	IngresoService ingresoService = (IngresoService)TumiFactory.get(IngresoService.class);
	
	private final Integer INGRESODETALLE_FONDOFIJO = 1;
	private final Integer INGRESODETALLE_AJUSTE = 2;
	private final Integer INGRESODETALLE_OTROS = 3;
	private final Integer INGRESODETALLE_BANCO = 4;
	
	
	public List<Ingreso> obtenerListaIngresoParaDepositar(Integer intIdEmpresa, Integer intMoneda, Integer intFormaPago, Usuario usuario)throws BusinessException{
		List<Ingreso> listaIngreso = null;
		try{
			listaIngreso = boIngreso.getListaParaDepositar(intIdEmpresa,intMoneda, usuario);
			if(listaIngreso == null  || listaIngreso.isEmpty())
				return null;
			
			List<Ingreso> listaIngresoTemp = new ArrayList<Ingreso>();
			if(intFormaPago == null){
				listaIngresoTemp = listaIngreso;
			}else if(intFormaPago.equals(Constante.PARAM_T_PAGOINGRESO_EFECTIVO)){
				for(Ingreso ingreso : listaIngreso){
					if(ingreso.getStrNumeroCheque() == null)
						listaIngresoTemp.add(ingreso);
				}
			}else if(intFormaPago.equals(Constante.PARAM_T_PAGOINGRESO_CHEQUE)){
				for(Ingreso ingreso : listaIngreso){
					if(ingreso.getStrNumeroCheque() != null)
						listaIngresoTemp.add(ingreso);
				}
			}
			
			listaIngreso = listaIngresoTemp;
			
			for(Ingreso ingreso : listaIngreso){
				ingresoService.procesarItems(ingreso);
				obtenerMontoDepositable(ingreso);
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return listaIngreso;
	}
	
	private void obtenerMontoDepositable(Ingreso ingreso) throws Exception{
		List<DepositoIngreso> listaDepositoIngreso = boDepositoIngreso.getPorIngreso(ingreso);
		if(listaDepositoIngreso==null) return;
		BigDecimal bdMontoDepositadoActualmente = new BigDecimal(0);
		for(DepositoIngreso depositoIngreso : listaDepositoIngreso){
			bdMontoDepositadoActualmente = bdMontoDepositadoActualmente.add(depositoIngreso.getBdMontoCancelado());
		}
		ingreso.setBdMontoDepositable(ingreso.getBdMontoTotal().subtract(bdMontoDepositadoActualmente));
	}
	
	private Timestamp obtenerFechaActual(){
		return new Timestamp(new Date().getTime());
	}
	
	private Integer	obtenerPeriodoActual(){
		String strPeriodo = "";
		Calendar cal = Calendar.getInstance();
		int año = cal.get(Calendar.YEAR);
		int mes = cal.get(Calendar.MONTH);
		mes = mes + 1; 
		if(mes<10){
			strPeriodo = año + "0" + mes;
		}else{
			strPeriodo  = año + "" + mes;
		}		
		return Integer.parseInt(strPeriodo);		
	}
	
	private BigDecimal obtenerMontoDepositoIngreso(List<Ingreso> listaIngreso)throws Exception{
		BigDecimal bdMontoDepositoIngreso = new BigDecimal(0);
		for(Ingreso ingreso : listaIngreso){
			bdMontoDepositoIngreso = bdMontoDepositoIngreso.add(ingreso.getBdMontoDepositar());
		}		
		return bdMontoDepositoIngreso;
	}
	
	private BigDecimal obtenerMontoDepositoTotal(BigDecimal bdMontoDepositoIngreso, BigDecimal bdOtrosIngresos)throws Exception{
		if(bdOtrosIngresos!=null){
			return bdMontoDepositoIngreso.add(bdOtrosIngresos);
		}
		return bdMontoDepositoIngreso;
	}
	
	private Modelo obtenerModelo(Integer intIdEmpresa)throws Exception{
		ModeloFacadeRemote modeloFacade = (ModeloFacadeRemote) EJBFactory.getRemote(ModeloFacadeRemote.class);
		List<Modelo> listaModelo = modeloFacade.obtenerTipoModeloActual(Constante.PARAM_T_TIPOMODELOCONTABLE_DEPOSITOBANCO, intIdEmpresa);
		
		return listaModelo.get(0);
	}
	
	private PlanCuenta obtenerPlanCuenta(Integer intTipoIngresoDetalle, BigDecimal bdMonto, Bancofondo bancoFondo, 
			List<ModeloDetalle> listaModeloDetalle, Bancofondo bancoFondoIngresar) throws Exception{
		PlanCuenta planCuenta = null;
		if(intTipoIngresoDetalle.equals(INGRESODETALLE_FONDOFIJO)){
			planCuenta = bancoFondoIngresar.getFondoDetalleUsar().getPlanCuenta();
		}else if(intTipoIngresoDetalle.equals(INGRESODETALLE_AJUSTE)){
			if(bdMonto.signum()>0){
				for(ModeloDetalle modeloDetalle : listaModeloDetalle){
					if(modeloDetalle.getListModeloDetalleNivel().get(0).getStrDescripcion().equals("InDe_MontoAbono_N<Ingr_MontoTotal_N")){
						planCuenta = modeloDetalle.getPlanCuenta();break;
					}
				}				
			}else{
				for(ModeloDetalle modeloDetalle : listaModeloDetalle){
					if(modeloDetalle.getListModeloDetalleNivel().get(0).getStrDescripcion().equals("InDe_MontoAbono_N>Ingr_MontoTotal_N")){
						planCuenta = modeloDetalle.getPlanCuenta();break;
					}
				}
			}						
		}else if(intTipoIngresoDetalle.equals(INGRESODETALLE_OTROS)){
			for(ModeloDetalle modeloDetalle : listaModeloDetalle){
				if(modeloDetalle.getListModeloDetalleNivel().get(0).getStrDescripcion().equals("Otros Ingresos")){
					planCuenta = modeloDetalle.getPlanCuenta();break;
				}
			}
		}else if(intTipoIngresoDetalle.equals(INGRESODETALLE_BANCO)){
			planCuenta = bancoFondo.getBancoCuentaSeleccionada().getPlanCuenta();
		}
		return planCuenta;
	}
	
	private IngresoDetalle generarIngresoDetalle(Integer intTipoIngresoDetalle, Bancofondo bancoFondoDepositar, 
		Usuario usuario, BigDecimal bdMonto,List<ModeloDetalle> listaModeloDetalle, Bancofondo bancoFondoIngresar)throws Exception{
		log.info("intTipoIngresoDetalle:"+intTipoIngresoDetalle+" "+bdMonto);
		Integer intIdEmpresa = bancoFondoDepositar.getId().getIntEmpresaPk();
		Sucursal sucursal = usuario.getSucursal();
		Subsucursal subsucursal = usuario.getSubSucursal();
		PlanCuenta planCuenta = null;			
		
		planCuenta = obtenerPlanCuenta(intTipoIngresoDetalle, bdMonto, bancoFondoDepositar, listaModeloDetalle, bancoFondoIngresar);
			
		IngresoDetalle ingresoDetalle = new IngresoDetalle();
		ingresoDetalle.getId().setIntPersEmpresa(intIdEmpresa);
		ingresoDetalle.getId().setIntIdIngresoGeneral(null);
		ingresoDetalle.getId().setIntIdIngresoDetalle(null);
		ingresoDetalle.setIntParaDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_DEPOSITODEBANCO);
		ingresoDetalle.setIntParaTipoComprobante(null);
		ingresoDetalle.setStrSerieDocumento(null);
		ingresoDetalle.setStrNumeroDocumento(null);
		ingresoDetalle.setStrDescripcionIngreso(planCuenta.getStrDescripcion());
		ingresoDetalle.setIntPersEmpresaGirado(intIdEmpresa);
		if(intTipoIngresoDetalle.equals(INGRESODETALLE_FONDOFIJO)){
			ingresoDetalle.setIntPersPersonaGirado(sucursal.getIntPersPersonaPk());
			ingresoDetalle.setBdMontoAbono(bdMonto);
		}else if(intTipoIngresoDetalle.equals(INGRESODETALLE_AJUSTE)){
			ingresoDetalle.setIntPersPersonaGirado(bancoFondoDepositar.getIntPersonabancoPk());
			ingresoDetalle.setBdAjusteDeposito(bdMonto);
			if(bdMonto.signum()>0){
				ingresoDetalle.setBdMontoAbono(bdMonto);
			}else{
				ingresoDetalle.setBdMontoCargo(bdMonto);
			}
		}else if(intTipoIngresoDetalle.equals(INGRESODETALLE_OTROS)){
			ingresoDetalle.setIntPersPersonaGirado(bancoFondoDepositar.getIntPersonabancoPk());
			ingresoDetalle.setBdMontoAbono(bdMonto);
		}
		
		ingresoDetalle.setIntCuentaGirado(null);
		ingresoDetalle.setIntParaTipoPagoCuenta(null);
		ingresoDetalle.setIntSucuIdSucursalIn(sucursal.getId().getIntIdSucursal());
		ingresoDetalle.setIntSucuIdSubsucursalIn(subsucursal.getId().getIntIdSubSucursal());
		ingresoDetalle.setIntParaTipoMoneda(bancoFondoDepositar.getIntMonedaCod());		
		ingresoDetalle.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
		ingresoDetalle.setTsFechaRegistro(obtenerFechaActual());
		ingresoDetalle.setIntPersEmpresaUsuario(intIdEmpresa);
		ingresoDetalle.setIntPersPersonaUsuario(usuario.getPersona().getIntIdPersona());
		
		ingresoDetalle.setIntPersEmpresaCuenta(planCuenta.getId().getIntEmpresaCuentaPk());
		ingresoDetalle.setIntContPeriodoCuenta(planCuenta.getId().getIntPeriodoCuenta());
		ingresoDetalle.setStrContNumeroCuenta(planCuenta.getId().getStrNumeroCuenta());
		
		return ingresoDetalle;
	}
	
	private LibroDiarioDetalle generarLibroDiarioDetalle(Integer intTipoIngresoDetalle, Bancofondo bancoFondoDepositar, 
		Usuario usuario, BigDecimal bdMonto,List<ModeloDetalle> listaModeloDetalle, Bancofondo bancoFondoIngresar) throws Exception{
		
		Integer intIdEmpresa = bancoFondoDepositar.getId().getIntEmpresaPk();
		Sucursal sucursal = usuario.getSucursal();
		Subsucursal subsucursal = usuario.getSubSucursal();
		PlanCuenta planCuenta = null;			
		
		planCuenta = obtenerPlanCuenta(intTipoIngresoDetalle, bdMonto, bancoFondoDepositar, listaModeloDetalle, bancoFondoIngresar);		
		
		LibroDiarioDetalle libroDiarioDetalle = new LibroDiarioDetalle();
		libroDiarioDetalle.setId(new LibroDiarioDetalleId());
		libroDiarioDetalle.getId().setIntPersEmpresaLibro(intIdEmpresa);
		libroDiarioDetalle.getId().setIntContPeriodoLibro(obtenerPeriodoActual());
		libroDiarioDetalle.setIntPersEmpresaCuenta(planCuenta.getId().getIntEmpresaCuentaPk());
		libroDiarioDetalle.setIntContPeriodo(planCuenta.getId().getIntPeriodoCuenta());
		libroDiarioDetalle.setStrContNumeroCuenta(planCuenta.getId().getStrNumeroCuenta());
		
		if(intTipoIngresoDetalle.equals(INGRESODETALLE_FONDOFIJO)){
			libroDiarioDetalle.setIntPersPersona(sucursal.getIntPersPersonaPk());
			libroDiarioDetalle.setIntParaDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_INGRESODECAJA);
			libroDiarioDetalle.setIntSucuIdSucursal(sucursal.getId().getIntIdSucursal());
			libroDiarioDetalle.setIntSudeIdSubSucursal(subsucursal.getId().getIntIdSubSucursal());
			libroDiarioDetalle.setBdHaberSoles(bdMonto);
			libroDiarioDetalle.setBdDebeSoles(null);
			
		}else if(intTipoIngresoDetalle.equals(INGRESODETALLE_AJUSTE)){
			libroDiarioDetalle.setIntPersPersona(sucursal.getIntPersPersonaPk());
			libroDiarioDetalle.setIntParaDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_INGRESODECAJA);
			libroDiarioDetalle.setIntSucuIdSucursal(sucursal.getId().getIntIdSucursal());
			libroDiarioDetalle.setIntSudeIdSubSucursal(subsucursal.getId().getIntIdSubSucursal());
			if(bdMonto.signum()>0){
				libroDiarioDetalle.setBdHaberSoles(bdMonto);
				libroDiarioDetalle.setBdDebeSoles(null);
			}else{
				libroDiarioDetalle.setBdHaberSoles(null);
				libroDiarioDetalle.setBdDebeSoles(bdMonto);
			}
			
		}else if(intTipoIngresoDetalle.equals(INGRESODETALLE_OTROS)){
			libroDiarioDetalle.setIntPersPersona(sucursal.getIntPersPersonaPk());
			libroDiarioDetalle.setIntParaDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_INGRESODECAJA);
			libroDiarioDetalle.setIntSucuIdSucursal(sucursal.getId().getIntIdSucursal());
			libroDiarioDetalle.setIntSudeIdSubSucursal(subsucursal.getId().getIntIdSubSucursal());
			libroDiarioDetalle.setBdHaberSoles(bdMonto);
			libroDiarioDetalle.setBdDebeSoles(null);
			
		}else if(intTipoIngresoDetalle.equals(INGRESODETALLE_BANCO)){
			libroDiarioDetalle.setIntPersPersona(bancoFondoDepositar.getIntPersonabancoPk());
			libroDiarioDetalle.setIntParaDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_DEPOSITODEBANCO);
			libroDiarioDetalle.setIntSucuIdSucursal(Constante.SUCURSAL_SEDECENTRAL);
			libroDiarioDetalle.setIntSudeIdSubSucursal(Constante.SUBSUCURSAL_SEDE1);
			libroDiarioDetalle.setBdHaberSoles(null);
			libroDiarioDetalle.setBdDebeSoles(bdMonto);
		}
		
		
		libroDiarioDetalle.setStrSerieDocumento(null);
		libroDiarioDetalle.setStrNumeroDocumento("");
		libroDiarioDetalle.setIntPersEmpresaSucursal(intIdEmpresa);
		libroDiarioDetalle.setIntParaMonedaDocumento(bancoFondoDepositar.getIntMonedaCod());		
		libroDiarioDetalle.setStrComentario(planCuenta.getStrDescripcion().length()<20?planCuenta.getStrDescripcion():planCuenta.getStrDescripcion().substring(0, 20));
		
		libroDiarioDetalle.setBdHaberExtranjero(null);
		libroDiarioDetalle.setBdDebeExtranjero(null);
		
		return libroDiarioDetalle;
	}
	
	private DepositoIngreso generarDepositoIngreso(Ingreso ingresoDeposito, Ingreso ingreso) throws Exception{
		DepositoIngreso depositoIngreso = new DepositoIngreso();
		depositoIngreso.getId().setIntIdEmpresaDeposito(ingresoDeposito.getId().getIntIdEmpresa());
		depositoIngreso.getId().setIntItemDepositoGeneral(ingresoDeposito.getId().getIntIdIngresoGeneral());
		depositoIngreso.setIntIdEmpresa(ingreso.getId().getIntIdEmpresa());
		depositoIngreso.setIntIdIngresoGeneral(ingreso.getId().getIntIdIngresoGeneral());
		depositoIngreso.setBdMontoCancelado(ingreso.getBdMontoDepositar());
		return depositoIngreso;
	}
	
	public Ingreso grabarDeposito(List<Ingreso> listaIngreso, Usuario usuario, Bancofondo bancoFondoDepositar, String strObservacion, 
			Archivo archivo, BigDecimal bdOtrosIngresos, String strNumeroOperacion, Bancofondo bancoFondoIngresar)throws Exception{
		
		Ingreso ingresoDeposito = generarIngresoDeposito(listaIngreso, usuario, bancoFondoDepositar, strObservacion, 
				archivo, bdOtrosIngresos, strNumeroOperacion, bancoFondoIngresar);
		
		ingresoDeposito = ingresoService.grabarIngresoGeneral(ingresoDeposito);
		
		int contador = 0;
		for(Ingreso ingreso : listaIngreso){
			contador = contador + 1;
			DepositoIngreso depositoIngreso = generarDepositoIngreso(ingresoDeposito, ingreso);
			depositoIngreso.getId().setIntItemDepositoIngreso(contador);
			log.info(depositoIngreso);
			boDepositoIngreso.grabar(depositoIngreso);
			
			if(ingreso.getBdMontoDepositable().equals(ingreso.getBdMontoDepositar())){
				ingreso.setIntParaEstadoDepositado(Constante.PARAM_T_ESTADODEPOSITADO_DEPOSITADO);
				log.info(ingreso);
				boIngreso.modificar(ingreso);
			}
		}
		
		return ingresoDeposito;
	}
	
	public Ingreso generarIngresoDeposito(List<Ingreso> listaIngreso, Usuario usuario, Bancofondo bancoFondoDepositar, String strObservacion, 
		Archivo archivo, BigDecimal bdOtrosIngresos, String strNumeroOperacion, Bancofondo bancoFondoIngresar) 
		throws BusinessException{
		Ingreso ingreso = new Ingreso();
		try{			
			
			Ingreso ingresoReferencia = listaIngreso.get(0);
			Integer intIdEmpresa = ingresoReferencia.getId().getIntIdEmpresa();
			Sucursal sucursal = usuario.getSucursal();
			Subsucursal	subsucursal = usuario.getSubSucursal();
			Bancocuenta	bancoCuenta = bancoFondoDepositar.getBancoCuentaSeleccionada();
			List<ModeloDetalle> listaModeloDetalle = obtenerModelo(intIdEmpresa).getListModeloDetalle();
			BigDecimal bdMontoDepositoIngresado = obtenerMontoDepositoIngreso(listaIngreso);
			BigDecimal bdMontoDepositoTotal = obtenerMontoDepositoTotal(bdMontoDepositoIngresado, bdOtrosIngresos);
			BigDecimal bdMontoDepositoAjustado = AjusteMonto.obtenerMontoAjustado(bdMontoDepositoTotal);
			BigDecimal bdMontoAjuste = bdMontoDepositoAjustado.subtract(bdMontoDepositoTotal);
			Integer intFormaPago = ingresoReferencia.getIntParaFormaPago();
			bancoFondoDepositar.setIntMonedaCod(bancoCuenta.getCuentaBancaria().getIntMonedaCod());
			
			ingreso.getId().setIntIdEmpresa(intIdEmpresa);
			ingreso.getId().setIntIdIngresoGeneral(null);
			ingreso.setIntParaTipoOperacion(Constante.PARAM_T_TIPODEOPERACION_DEPOSITO);
			ingreso.setIntParaFormaPago(intFormaPago);
			ingreso.setIntParaDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_DEPOSITODEBANCO);
			ingreso.setIntItemPeriodoIngreso(obtenerPeriodoActual());
			ingreso.setIntItemIngreso(null);
			ingreso.setIntSucuIdSucursal(sucursal.getId().getIntIdSucursal());
			ingreso.setIntSudeIdSubsucursal(subsucursal.getId().getIntIdSubSucursal());
			ingreso.setIntParaTipoSuboperacion(Constante.PARAM_T_TIPODESUBOPERACION_CANCELACION);
			ingreso.setTsFechaProceso(obtenerFechaActual());
			ingreso.setDtFechaIngreso(obtenerFechaActual());
			ingreso.setIntParaFondoFijo(bancoFondoDepositar.getIntTipoFondoFijo());
			ingreso.setIntItemBancoFondo(bancoFondoDepositar.getId().getIntItembancofondo());
			ingreso.setIntItemBancoCuenta(bancoCuenta.getId().getIntItembancocuenta());
			ingreso.setStrNumeroCheque(null);
			ingreso.setIntPersEmpresaGirado(intIdEmpresa);
			ingreso.setIntPersPersonaGirado(sucursal.getIntPersPersonaPk());
			ingreso.setIntCuentaGirado(null);
			ingreso.setIntParaTipoMoneda(bancoFondoDepositar.getIntMonedaCod());
			ingreso.setBdTipoCambio(null);
			ingreso.setBdMontoTotal(bdMontoDepositoAjustado);
			ingreso.setStrObservacion(strObservacion);
			ingreso.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			ingreso.setTsFechaRegistro(obtenerFechaActual());
			ingreso.setIntPersEmpresaUsuario(intIdEmpresa);
			ingreso.setIntPersPersonaUsuario(usuario.getPersona().getIntIdPersona());
			if(archivo!=null){
				ingreso.setIntParaTipoDeposito(archivo.getId().getIntParaTipoCod());
				ingreso.setIntItemArchivoDeposito(archivo.getId().getIntItemArchivo());
				ingreso.setIntItemHistoricoDeposito(archivo.getId().getIntItemHistorico());
			}
			ingreso.setStrNumeroOperacion(strNumeroOperacion);
			
			
			IngresoDetalle ingresoDetalleFondoFijo = generarIngresoDetalle(INGRESODETALLE_FONDOFIJO, bancoFondoDepositar, usuario, bdMontoDepositoIngresado, null, bancoFondoIngresar);
			ingreso.getListaIngresoDetalle().add(ingresoDetalleFondoFijo);
			
			if(bdMontoAjuste != null && !(bdMontoAjuste.equals(BigDecimal.ZERO))){
				IngresoDetalle ingresoDetalleAjuste = generarIngresoDetalle(INGRESODETALLE_AJUSTE, bancoFondoDepositar, usuario, bdMontoAjuste, listaModeloDetalle, bancoFondoIngresar);
				ingreso.getListaIngresoDetalle().add(ingresoDetalleAjuste);
			}
			if(bdOtrosIngresos != null && !(bdOtrosIngresos.equals(BigDecimal.ZERO))){
				IngresoDetalle ingresoDetalleOtros = generarIngresoDetalle(INGRESODETALLE_OTROS, bancoFondoDepositar, usuario, bdOtrosIngresos, listaModeloDetalle, bancoFondoIngresar);
				ingreso.getListaIngresoDetalle().add(ingresoDetalleOtros);
			}
			
			
			LibroDiario libroDiario = new LibroDiario();
			libroDiario.setId(new LibroDiarioId());
			libroDiario.getId().setIntPersEmpresaLibro(intIdEmpresa);
			libroDiario.getId().setIntContPeriodoLibro(obtenerPeriodoActual());
			libroDiario.setStrGlosa(strObservacion);
			libroDiario.setTsFechaRegistro(obtenerFechaActual());
			libroDiario.setTsFechaDocumento(obtenerFechaActual());
			libroDiario.setIntPersEmpresaUsuario(intIdEmpresa);
			libroDiario.setIntPersPersonaUsuario(usuario.getIntPersPersonaPk());
			libroDiario.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			libroDiario.setIntParaTipoDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_DEPOSITODEBANCO);
			libroDiario.setListaLibroDiarioDetalle(new ArrayList<LibroDiarioDetalle>());
			
			LibroDiarioDetalle libroDiarioDetalleBanco = generarLibroDiarioDetalle(INGRESODETALLE_BANCO, bancoFondoDepositar, usuario, bdMontoDepositoAjustado, null, null);
			libroDiario.getListaLibroDiarioDetalle().add(libroDiarioDetalleBanco);
			
			LibroDiarioDetalle libroDiarioDetalleFondo = generarLibroDiarioDetalle(INGRESODETALLE_FONDOFIJO, bancoFondoDepositar, usuario, bdMontoDepositoIngresado, null, bancoFondoIngresar);
			libroDiario.getListaLibroDiarioDetalle().add(libroDiarioDetalleFondo);
			
			if(bdMontoAjuste != null && !(bdMontoAjuste.equals(BigDecimal.ZERO))){
				LibroDiarioDetalle libroDiarioDetalleAjuste = generarLibroDiarioDetalle(INGRESODETALLE_AJUSTE, bancoFondoDepositar, usuario, bdMontoAjuste, listaModeloDetalle, null);
				libroDiario.getListaLibroDiarioDetalle().add(libroDiarioDetalleAjuste);
			}			
			if(bdOtrosIngresos != null && !(bdOtrosIngresos.equals(BigDecimal.ZERO))){
				LibroDiarioDetalle libroDetalleOtros = generarLibroDiarioDetalle(INGRESODETALLE_OTROS, bancoFondoDepositar, usuario, bdOtrosIngresos, listaModeloDetalle, null);
				libroDiario.getListaLibroDiarioDetalle().add(libroDetalleOtros);
			}
			
			ingreso.setLibroDiario(libroDiario);
			
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return ingreso;
	}
}
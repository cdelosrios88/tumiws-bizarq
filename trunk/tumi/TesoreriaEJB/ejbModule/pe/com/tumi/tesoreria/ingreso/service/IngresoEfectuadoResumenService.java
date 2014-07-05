package pe.com.tumi.tesoreria.ingreso.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.cobranza.gestion.domain.GestorCobranza;
import pe.com.tumi.cobranza.planilla.domain.CobroPlanillas;
import pe.com.tumi.cobranza.planilla.domain.EfectuadoResumen;
import pe.com.tumi.cobranza.planilla.domain.EfectuadoResumenId;
import pe.com.tumi.cobranza.planilla.facade.PlanillaFacadeRemote;
import pe.com.tumi.common.util.Constante;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiario;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiarioDetalle;
import pe.com.tumi.contabilidad.core.domain.Modelo;
import pe.com.tumi.contabilidad.core.domain.ModeloDetalle;
import pe.com.tumi.contabilidad.core.facade.ModeloFacadeRemote;
import pe.com.tumi.empresa.domain.Subsucursal;
import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.seguridad.login.domain.Usuario;
import pe.com.tumi.tesoreria.banco.domain.Bancofondo;
import pe.com.tumi.tesoreria.ingreso.bo.ReciboManualBO;
import pe.com.tumi.tesoreria.ingreso.bo.ReciboManualDetalleBO;
import pe.com.tumi.tesoreria.ingreso.domain.Ingreso;
import pe.com.tumi.tesoreria.ingreso.domain.IngresoDetalle;
import pe.com.tumi.tesoreria.ingreso.domain.ReciboManual;
import pe.com.tumi.tesoreria.ingreso.domain.ReciboManualDetalle;

public class IngresoEfectuadoResumenService {

	protected static Logger log = Logger.getLogger(IngresoEfectuadoResumenService.class);
	
	ReciboManualBO boReciboManual = (ReciboManualBO)TumiFactory.get(ReciboManualBO.class);
	ReciboManualDetalleBO boReciboManualDetalle = (ReciboManualDetalleBO)TumiFactory.get(ReciboManualDetalleBO.class);
	IngresoService ingresoService = (IngresoService)TumiFactory.get(IngresoService.class);
	
	
	private BigDecimal obtenerMontoIngresoTotal(List<EfectuadoResumen> listaEfectuadoResumen)throws Exception{
		BigDecimal bdMontoIngresoTotal = new BigDecimal(0);
		for(EfectuadoResumen efectuadoResumen : listaEfectuadoResumen){
			bdMontoIngresoTotal = bdMontoIngresoTotal.add(efectuadoResumen.getBdMontIngresar());
		}
		return bdMontoIngresoTotal;
	}

	
	private IngresoDetalle generarIngresoDetalle(EfectuadoResumen efectuadoResumen, ModeloDetalle modeloDetalle, Bancofondo bancoFondo, 
		Usuario usuario)throws Exception{
		
		Integer intIdEmpresa = efectuadoResumen.getId().getIntEmpresa();
		Persona personaIngreso = efectuadoResumen.getPersonaIngreso();
		Sucursal sucursal = usuario.getSucursal();
		Subsucursal subsucursal = usuario.getSubSucursal();
		
		IngresoDetalle ingresoDetalle = new IngresoDetalle();
		ingresoDetalle.getId().setIntPersEmpresa(intIdEmpresa);
		ingresoDetalle.getId().setIntIdIngresoGeneral(null);
		ingresoDetalle.getId().setIntIdIngresoDetalle(null);
		ingresoDetalle.setIntParaDocumentoGeneral(efectuadoResumen.getIntParaDocumentoGeneralCod());
		ingresoDetalle.setIntParaTipoComprobante(null);
		ingresoDetalle.setStrSerieDocumento(null);
		ingresoDetalle.setStrNumeroDocumento(""+efectuadoResumen.getId().getIntItemEfectuadoResumen());
		ingresoDetalle.setStrDescripcionIngreso(modeloDetalle.getPlanCuenta().getStrDescripcion());
		ingresoDetalle.setIntPersEmpresaGirado(intIdEmpresa);
		ingresoDetalle.setIntPersPersonaGirado(personaIngreso.getIntIdPersona());
		ingresoDetalle.setIntCuentaGirado(null);
		ingresoDetalle.setIntParaTipoPagoCuenta(null);
		ingresoDetalle.setIntSucuIdSucursalIn(sucursal.getId().getIntIdSucursal());
		ingresoDetalle.setIntSucuIdSubsucursalIn(subsucursal.getId().getIntIdSubSucursal());
		ingresoDetalle.setIntParaTipoMoneda(bancoFondo.getIntMonedaCod());
		ingresoDetalle.setBdAjusteDeposito(null);
		ingresoDetalle.setBdMontoCargo(null);
		ingresoDetalle.setBdMontoAbono(efectuadoResumen.getBdMontIngresar());
		ingresoDetalle.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
		ingresoDetalle.setTsFechaRegistro(obtenerFechaActual());
		ingresoDetalle.setIntPersEmpresaUsuario(intIdEmpresa);
		ingresoDetalle.setIntPersPersonaUsuario(usuario.getPersona().getIntIdPersona());
		
		return ingresoDetalle;
	}
	
	private LibroDiarioDetalle generarLibroDiarioDetalleHaber(EfectuadoResumen efectuadoResumen, ModeloDetalle modeloDetalle, Bancofondo bancoFondo, 
		Usuario usuario) throws Exception{
		Integer intIdEmpresa = efectuadoResumen.getId().getIntEmpresa();
		Persona personaIngreso = efectuadoResumen.getPersonaIngreso();
		Sucursal sucursal = usuario.getSucursal();
		Subsucursal subsucursal = usuario.getSubSucursal();
		
		LibroDiarioDetalle libroDiarioDetalleHaber = new LibroDiarioDetalle();
		libroDiarioDetalleHaber.getId().setIntPersEmpresaLibro(intIdEmpresa);
		libroDiarioDetalleHaber.getId().setIntContPeriodoLibro(obtenerPeriodoActual());
		libroDiarioDetalleHaber.setIntPersEmpresaCuenta(modeloDetalle.getId().getIntPersEmpresaCuenta());
		libroDiarioDetalleHaber.setIntContPeriodo(modeloDetalle.getId().getIntContPeriodoCuenta());
		libroDiarioDetalleHaber.setStrContNumeroCuenta(modeloDetalle.getId().getStrContNumeroCuenta());
		libroDiarioDetalleHaber.setIntPersPersona(personaIngreso.getIntIdPersona());
		libroDiarioDetalleHaber.setIntParaDocumentoGeneral(efectuadoResumen.getIntParaDocumentoGeneralCod());
		libroDiarioDetalleHaber.setStrSerieDocumento(null);
		libroDiarioDetalleHaber.setStrNumeroDocumento(""+efectuadoResumen.getId().getIntItemEfectuadoResumen());
		libroDiarioDetalleHaber.setIntPersEmpresaSucursal(intIdEmpresa);
		libroDiarioDetalleHaber.setIntSucuIdSucursal(sucursal.getId().getIntIdSucursal());
		libroDiarioDetalleHaber.setIntSudeIdSubSucursal(subsucursal.getId().getIntIdSubSucursal());
		libroDiarioDetalleHaber.setIntParaMonedaDocumento(bancoFondo.getIntMonedaCod());
		libroDiarioDetalleHaber.setBdHaberSoles(efectuadoResumen.getBdMontIngresar());
		libroDiarioDetalleHaber.setBdDebeSoles(null);
		libroDiarioDetalleHaber.setBdHaberExtranjero(null);
		libroDiarioDetalleHaber.setBdDebeExtranjero(null);
		
		return libroDiarioDetalleHaber;
	}
	
	
	public Ingreso generarIngresoEfectuadoResumen(List<EfectuadoResumen> listaEfectuadoResumen, Bancofondo bancoFondo, Usuario usuario) 
		throws BusinessException{
		Ingreso ingreso = new Ingreso();
		try{
			EfectuadoResumen efectuadoResumenReferencia = listaEfectuadoResumen.get(0);
			Integer intIdEmpresa = efectuadoResumenReferencia.getId().getIntEmpresa();
			Integer intParaTipoSuboperacion = obtenerTipoOperacion(listaEfectuadoResumen);
			Sucursal sucursal = usuario.getSucursal();
			Subsucursal	subsucursal = usuario.getSubSucursal();
			Persona personaIngreso = efectuadoResumenReferencia.getPersonaIngreso();
			String strObservacion = efectuadoResumenReferencia.getStrObservacionIngreso();
			Modelo modelo = obtenerModelo(intIdEmpresa);
			ModeloDetalle modeloDetalle = modelo.getListModeloDetalle().get(0);
			BigDecimal bdMontoIngresoTotal = obtenerMontoIngresoTotal(listaEfectuadoResumen);
			
			ingreso.getId().setIntIdEmpresa(intIdEmpresa);
			ingreso.getId().setIntIdIngresoGeneral(null);
			ingreso.setIntParaTipoOperacion(Constante.PARAM_T_TIPODEOPERACION_INGRESO);
			ingreso.setIntParaFormaPago(efectuadoResumenReferencia.getIntParaFormaPago());
			ingreso.setIntParaDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_INGRESODECAJA);
			ingreso.setIntItemPeriodoIngreso(obtenerPeriodoActual());
			ingreso.setIntItemIngreso(null);
			ingreso.setIntSucuIdSucursal(sucursal.getId().getIntIdSucursal());
			ingreso.setIntSudeIdSubsucursal(subsucursal.getId().getIntIdSubSucursal());
			ingreso.setIntParaTipoSuboperacion(intParaTipoSuboperacion);
			ingreso.setTsFechaProceso(obtenerFechaActual());
			ingreso.setDtFechaIngreso(obtenerFechaActual());
			ingreso.setIntParaFondoFijo(bancoFondo.getIntTipoFondoFijo());
			ingreso.setIntItemBancoFondo(bancoFondo.getId().getIntItembancofondo());
			ingreso.setIntItemBancoCuenta(null);
			ingreso.setStrNumeroCheque(efectuadoResumenReferencia.getStrNumeroCheque());
			ingreso.setIntPersEmpresaGirado(intIdEmpresa);
			ingreso.setIntPersPersonaGirado(personaIngreso.getIntIdPersona());
			ingreso.setIntCuentaGirado(null);
			ingreso.setIntParaTipoMoneda(bancoFondo.getIntMonedaCod());
			ingreso.setBdTipoCambio(null);
			ingreso.setBdMontoTotal(bdMontoIngresoTotal);
			ingreso.setStrObservacion(strObservacion);
			ingreso.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			ingreso.setTsFechaRegistro(obtenerFechaActual());
			ingreso.setIntPersEmpresaUsuario(intIdEmpresa);
			ingreso.setIntPersPersonaUsuario(usuario.getPersona().getIntIdPersona());
			ingreso.setIntParaEstadoDepositado(Constante.PARAM_T_ESTADODEPOSITADO_PENDIENTE);
			
			
			for(EfectuadoResumen efectuadoResumen : listaEfectuadoResumen){
				IngresoDetalle ingresoDetalle = generarIngresoDetalle(efectuadoResumen, modeloDetalle, bancoFondo, usuario);
				ingreso.getListaIngresoDetalle().add(ingresoDetalle);
			}
			
			
			LibroDiario libroDiario = new LibroDiario();
			libroDiario.getId().setIntPersEmpresaLibro(intIdEmpresa);
			libroDiario.getId().setIntContPeriodoLibro(obtenerPeriodoActual());
			libroDiario.setStrGlosa(strObservacion);
			libroDiario.setTsFechaRegistro(obtenerFechaActual());
			libroDiario.setTsFechaDocumento(obtenerFechaActual());
			libroDiario.setIntPersEmpresaUsuario(intIdEmpresa);
			libroDiario.setIntPersPersonaUsuario(usuario.getIntPersPersonaPk());
			libroDiario.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			libroDiario.setIntParaTipoDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_INGRESODECAJA);
			
			
			for(EfectuadoResumen efectuadoResumen : listaEfectuadoResumen){
				LibroDiarioDetalle libroDiarioDetalleHaber = generarLibroDiarioDetalleHaber(efectuadoResumen, modeloDetalle, bancoFondo, usuario);
				libroDiario.getListaLibroDiarioDetalle().add(libroDiarioDetalleHaber);
			}		
			
			
			LibroDiarioDetalle libroDiarioDetalleDebe = new LibroDiarioDetalle();
			libroDiarioDetalleDebe.getId().setIntPersEmpresaLibro(intIdEmpresa);
			libroDiarioDetalleDebe.getId().setIntContPeriodoLibro(obtenerPeriodoActual());
			libroDiarioDetalleDebe.setIntPersEmpresaCuenta(bancoFondo.getFondoDetalleUsar().getIntEmpresacuentaPk());
			libroDiarioDetalleDebe.setIntContPeriodo(bancoFondo.getFondoDetalleUsar().getIntPeriodocuenta());
			libroDiarioDetalleDebe.setStrContNumeroCuenta(bancoFondo.getFondoDetalleUsar().getStrNumerocuenta());
			libroDiarioDetalleDebe.setIntPersPersona(personaIngreso.getIntIdPersona());
			libroDiarioDetalleDebe.setIntParaDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_INGRESODECAJA);
			libroDiarioDetalleDebe.setStrSerieDocumento(null);
			libroDiarioDetalleDebe.setStrNumeroDocumento(null);
			libroDiarioDetalleDebe.setIntPersEmpresaSucursal(intIdEmpresa);
			libroDiarioDetalleDebe.setIntSucuIdSucursal(sucursal.getId().getIntIdSucursal());
			libroDiarioDetalleDebe.setIntSudeIdSubSucursal(subsucursal.getId().getIntIdSubSucursal());
			libroDiarioDetalleDebe.setIntParaMonedaDocumento(bancoFondo.getIntMonedaCod());
			libroDiarioDetalleDebe.setBdHaberSoles(null);
			libroDiarioDetalleDebe.setBdDebeSoles(bdMontoIngresoTotal);			
			libroDiarioDetalleDebe.setBdHaberExtranjero(null);
			libroDiarioDetalleDebe.setBdDebeExtranjero(null);
			
			libroDiario.getListaLibroDiarioDetalle().add(libroDiarioDetalleDebe);
			
			ingreso.setLibroDiario(libroDiario);
			
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return ingreso;
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
	
	private Timestamp obtenerFechaActual(){
		return new Timestamp(new Date().getTime());
	}
	
	private Modelo obtenerModelo(Integer intIdEmpresa)throws Exception{
		ModeloFacadeRemote modeloFacade = (ModeloFacadeRemote) EJBFactory.getRemote(ModeloFacadeRemote.class);
		List<Modelo> listaModelo = modeloFacade.obtenerTipoModeloActual(Constante.PARAM_T_TIPOMODELOCONTABLE_CANCELACIONPLANILLAEFECTUADA, intIdEmpresa);
		
		return listaModelo.get(0);
	}	
	
	private Integer obtenerTipoOperacion(List<EfectuadoResumen> listaEfectuadoResumen)throws Exception{
		Integer intTipoOperacion = null;
		//Si la lista es de tipoOperacion Cancelacion si todos los EfectuadoResumen son de Cancelacion
		for(EfectuadoResumen efectuadoResumen : listaEfectuadoResumen){
			if(efectuadoResumen.getBdMontIngresar().equals(efectuadoResumen.getBdMontoDisponibelIngresar())){
				intTipoOperacion = Constante.PARAM_T_TIPODESUBOPERACION_CANCELACION;
			}else{
				intTipoOperacion = Constante.PARAM_T_TIPODESUBOPERACION_ACUENTA;
				break;
			}
		}
		
		return intTipoOperacion;
	}
	
	private CobroPlanillas generarCobroPlanillas(Ingreso ingreso, EfectuadoResumen efectuadoResumen)throws Exception{
		CobroPlanillas cobroPlanillas = new CobroPlanillas();
		cobroPlanillas.getId().setIntEmpresa(efectuadoResumen.getId().getIntEmpresa());
		cobroPlanillas.getId().setIntItemEfectuadoResumen(efectuadoResumen.getId().getIntItemEfectuadoResumen());
		cobroPlanillas.setIntPersEmpresaIngreso(ingreso.getId().getIntIdEmpresa());
		cobroPlanillas.setIntItemIngresoGeneral(ingreso.getId().getIntIdIngresoGeneral());
		cobroPlanillas.setBdMontoPago(efectuadoResumen.getBdMontIngresar());
		cobroPlanillas.setIntParaTipoMoneda(Constante.PARAM_T_TIPOMONEDA_SOLES);
		cobroPlanillas.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
		
		return cobroPlanillas;
		
	}
	
	private ReciboManualDetalle generarReciboManualDetalle(Ingreso ingreso, GestorCobranza gestorCobranza)throws Exception{
		ReciboManual reciboManual = ingreso.getReciboManual();
		
		ReciboManualDetalle reciboManualDetalle = new ReciboManualDetalle();
		reciboManualDetalle.getId().setIntPersEmpresa(reciboManual.getId().getIntPersEmpresa());
		reciboManualDetalle.getId().setIntItemReciboManual(reciboManual.getId().getIntItemReciboManual());
		reciboManualDetalle.setIntNumeroRecibo(reciboManual.getIntNumeroActual());
		reciboManualDetalle.setIntPersEmpresaIngreso(ingreso.getId().getIntIdEmpresa());
		reciboManualDetalle.setIntItemIngresoGeneral(ingreso.getId().getIntIdIngresoGeneral());
		reciboManualDetalle.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);		
		reciboManualDetalle.setIntPersPersonaGestor(gestorCobranza.getId().getIntPersPersonaGestorPk());
		
		return reciboManualDetalle;
	}
	
	public Ingreso grabarIngresoEfectuadoResumen(Ingreso ingreso, List<EfectuadoResumen> listaEfectuadoResumen) throws BusinessException{
		try{
			PlanillaFacadeRemote planillaFacade = (PlanillaFacadeRemote) EJBFactory.getRemote(PlanillaFacadeRemote.class);
			ingreso = ingresoService.grabarIngresoGeneral(ingreso);
			
			
			ReciboManual reciboManual = ingreso.getReciboManual();
			if(reciboManual != null){
				GestorCobranza gestorCobranza = reciboManual.getGestorCobranza();
				ReciboManualDetalle reciboManualDetalle = generarReciboManualDetalle(ingreso, gestorCobranza);
				log.info(reciboManualDetalle);
				boReciboManualDetalle.grabar(reciboManualDetalle);
			}			
			
			for(EfectuadoResumen efectuadoResumen : listaEfectuadoResumen){
				CobroPlanillas cobroPlanillas = generarCobroPlanillas(ingreso, efectuadoResumen);
				log.info(cobroPlanillas);
				planillaFacade.grabarCobroPlanillas(cobroPlanillas);
			}
				
			for(EfectuadoResumen efectuadoResumen : listaEfectuadoResumen){
				if(efectuadoResumen.getBdMontIngresar().equals(efectuadoResumen.getBdMontoDisponibelIngresar())){
					efectuadoResumen.setIntParaEstadoPago(Constante.PARAM_T_ESTADOPAGO_CANCELADO);
					log.info(efectuadoResumen);
					planillaFacade.modificarEfectuadoResumen(efectuadoResumen);
				}
			}
			
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return ingreso;
	}
	
	public List<EfectuadoResumen> obtenerListaEfectuadoResumenPorIngreso(Ingreso ingreso) throws BusinessException{
		List<EfectuadoResumen> lista = new ArrayList<EfectuadoResumen>();
		try{
			PlanillaFacadeRemote planillaFacade = (PlanillaFacadeRemote) EJBFactory.getRemote(PlanillaFacadeRemote.class);
			
			for(IngresoDetalle ingresoDetalle : ingreso.getListaIngresoDetalle()){
				EfectuadoResumenId efectuadoResumenId = new EfectuadoResumenId();
				efectuadoResumenId.setIntEmpresa(ingresoDetalle.getId().getIntPersEmpresa());
				efectuadoResumenId.setIntItemEfectuadoResumen(Integer.parseInt(ingresoDetalle.getStrNumeroDocumento()));
				
				log.info(efectuadoResumenId);				
				EfectuadoResumen efectuadoResumen = planillaFacade.getEfectuadoResumenPorId(efectuadoResumenId);
				efectuadoResumen.setBdMontIngresar(ingresoDetalle.getBdMontoAbono());
				lista.add(efectuadoResumen);
			}			
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
}
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
import pe.com.tumi.contabilidad.cierre.domain.LibroDiarioDetalleId;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiarioId;
import pe.com.tumi.contabilidad.core.domain.Modelo;
import pe.com.tumi.contabilidad.core.domain.ModeloDetalle;
import pe.com.tumi.contabilidad.core.facade.ModeloFacadeRemote;
import pe.com.tumi.credito.socio.estructura.domain.composite.EstructuraComp;
import pe.com.tumi.empresa.domain.Subsucursal;
import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.parametro.general.domain.Archivo;
import pe.com.tumi.seguridad.login.domain.Usuario;
import pe.com.tumi.tesoreria.banco.domain.Bancofondo;
import pe.com.tumi.tesoreria.ingreso.bo.IngresoBO;
import pe.com.tumi.tesoreria.ingreso.bo.ReciboManualBO;
import pe.com.tumi.tesoreria.ingreso.bo.ReciboManualDetalleBO;
import pe.com.tumi.tesoreria.ingreso.domain.Ingreso;
import pe.com.tumi.tesoreria.ingreso.domain.IngresoDetalle;
import pe.com.tumi.tesoreria.ingreso.domain.IngresoDetalleId;
import pe.com.tumi.tesoreria.ingreso.domain.IngresoId;
import pe.com.tumi.tesoreria.ingreso.domain.ReciboManual;
import pe.com.tumi.tesoreria.ingreso.domain.ReciboManualDetalle;
import pe.com.tumi.tesoreria.ingreso.domain.ReciboManualDetalleId;

public class IngresoEfectuadoResumenService {

	protected static Logger log = Logger.getLogger(IngresoEfectuadoResumenService.class);
	
	IngresoBO boIngreso = (IngresoBO)TumiFactory.get(IngresoBO.class);
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

	
	private List<EfectuadoResumen> generarIngresoDetalle(EfectuadoResumen efectuadoResumen, ModeloDetalle modeloDetalle, Bancofondo bancoFondo, 
		Usuario usuario)throws Exception{
		
		Integer intIdEmpresa = efectuadoResumen.getId().getIntEmpresa();		
		BigDecimal bdMontoIngresado = efectuadoResumen.getBdMontIngresar();
//		List<IngresoDetalle> ltsIngresoDetalle = new ArrayList<IngresoDetalle>();
//		CobroPlanillas cobPlanilla = null;
		
		try {
			PlanillaFacadeRemote planillaFacade = (PlanillaFacadeRemote) EJBFactory.getRemote(PlanillaFacadeRemote.class);
			
			//lista de planilla efectuadas a cancelar (orden de cancelacion aleatorio)
			
			for (EfectuadoResumen detPllaEfect : efectuadoResumen.getListaDetallePlanillaEfectuada()) {

				IngresoDetalle ingresoDetalle = new IngresoDetalle();
				ingresoDetalle.setId(new IngresoDetalleId());
				ingresoDetalle.getId().setIntPersEmpresa(detPllaEfect.getId().getIntEmpresa());
				ingresoDetalle.getId().setIntIdIngresoGeneral(null);
				ingresoDetalle.getId().setIntIdIngresoDetalle(null);
				ingresoDetalle.setIntParaDocumentoGeneral(efectuadoResumen.getIntParaDocumentoGeneralCod());
				ingresoDetalle.setIntParaTipoComprobante(null);
				ingresoDetalle.setStrSerieDocumento(null);
				ingresoDetalle.setStrNumeroDocumento(""+detPllaEfect.getId().getIntItemEfectuadoResumen());
				ingresoDetalle.setStrDescripcionIngreso(modeloDetalle.getPlanCuenta().getStrDescripcion());
				ingresoDetalle.setIntPersEmpresaGirado(intIdEmpresa);
				ingresoDetalle.setIntPersPersonaGirado(efectuadoResumen.getEntidadIngreso().getEstructuraDetalle().getEstructura().getJuridica().getIntIdPersona());
				ingresoDetalle.setIntCuentaGirado(null);
				
				ingresoDetalle.setIntSucuIdSucursalIn(detPllaEfect.getIntIdsucursaladministraPk());
				ingresoDetalle.setIntSucuIdSubsucursalIn(detPllaEfect.getIntIdsubsucursaladministra());
				ingresoDetalle.setIntParaTipoMoneda(bancoFondo.getIntMonedaCod());
				ingresoDetalle.setBdAjusteDeposito(null);
				ingresoDetalle.setBdMontoCargo(null);
				
//				ingresoDetalle.setBdMontoAbono(efectuadoResumen.getBdMontIngresar());
				ingresoDetalle.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
				ingresoDetalle.setTsFechaRegistro(obtenerFechaActual());
				ingresoDetalle.setIntPersEmpresaUsuario(intIdEmpresa);
				ingresoDetalle.setIntPersPersonaUsuario(usuario.getPersona().getIntIdPersona());
				
				//obtenemos el cobro planilla para el detalle
				BigDecimal bdMontoPagado = BigDecimal.ZERO;
				List<CobroPlanillas> lstCobroPlanillas = planillaFacade.getPorEfectuadoResumen(detPllaEfect);
				if (lstCobroPlanillas!=null && !lstCobroPlanillas.isEmpty()) {
					for (CobroPlanillas cobroPlanillas : lstCobroPlanillas) {
						bdMontoPagado = bdMontoPagado.add(cobroPlanillas.getBdMontoPago());
					}
				}

				if ((detPllaEfect.getBdMontoTotal().subtract(bdMontoPagado)).compareTo(bdMontoIngresado)==1) {
					ingresoDetalle.setIntParaTipoPagoCuenta(Constante.PARAM_T_TIPOPAGOCUENTA_ACUENTA);
					ingresoDetalle.setBdMontoAbono(bdMontoIngresado);			
					detPllaEfect.setIntEstadoCod(1);
					detPllaEfect.setIngresoDetallePllaEfect(ingresoDetalle);
					break;
				}else if ((detPllaEfect.getBdMontoTotal().subtract(bdMontoPagado)).compareTo(bdMontoIngresado)==-1) {
					ingresoDetalle.setIntParaTipoPagoCuenta(Constante.PARAM_T_TIPOPAGOCUENTA_CANCELACION);
					ingresoDetalle.setBdMontoAbono(detPllaEfect.getBdMontoTotal());
					bdMontoIngresado = bdMontoIngresado.subtract(detPllaEfect.getBdMontoTotal());
					detPllaEfect.setIntEstadoCod(2);
					detPllaEfect.setIngresoDetallePllaEfect(ingresoDetalle);
				}else if ((detPllaEfect.getBdMontoTotal().subtract(bdMontoPagado)).compareTo(bdMontoIngresado)==0) {
					ingresoDetalle.setIntParaTipoPagoCuenta(Constante.PARAM_T_TIPOPAGOCUENTA_CANCELACION);
					ingresoDetalle.setBdMontoAbono(bdMontoIngresado);			
					detPllaEfect.setIntEstadoCod(2);
					detPllaEfect.setIngresoDetallePllaEfect(ingresoDetalle);
					break;
				}
			}	
		} catch (Exception e) {
			throw new BusinessException(e);
		}
		return efectuadoResumen.getListaDetallePlanillaEfectuada();
	}
	
	private List<LibroDiarioDetalle> generarLibroDiarioDetalleHaber(EfectuadoResumen efectuadoResumen, ModeloDetalle modeloDetalle, Bancofondo bancoFondo, 
		Usuario usuario) throws Exception{
		Integer intIdEmpresa = efectuadoResumen.getId().getIntEmpresa();
//		Persona personaIngreso = efectuadoResumen.getPersonaIngreso();
//		Sucursal sucursal = usuario.getSucursal();
//		Subsucursal subsucursal = usuario.getSubSucursal();
//		BigDecimal bdMontoIngresado = efectuadoResumen.getBdMontIngresar();
		List<LibroDiarioDetalle> lstLibroDiarioDetalleHaber = new ArrayList<LibroDiarioDetalle>();
		
		for (EfectuadoResumen detPllaEfect : efectuadoResumen.getListaDetallePlanillaEfectuada()) {
			LibroDiarioDetalle libroDiarioDetalleHaber = new LibroDiarioDetalle();
			libroDiarioDetalleHaber.setId(new LibroDiarioDetalleId());
			libroDiarioDetalleHaber.getId().setIntPersEmpresaLibro(intIdEmpresa);
			libroDiarioDetalleHaber.getId().setIntContPeriodoLibro(obtenerPeriodoActual());
			libroDiarioDetalleHaber.setIntPersEmpresaCuenta(modeloDetalle.getId().getIntPersEmpresaCuenta());
			libroDiarioDetalleHaber.setIntContPeriodo(modeloDetalle.getId().getIntContPeriodoCuenta());
			libroDiarioDetalleHaber.setStrContNumeroCuenta(modeloDetalle.getId().getStrContNumeroCuenta());
			libroDiarioDetalleHaber.setIntPersPersona(efectuadoResumen.getEntidadIngreso().getEstructuraDetalle().getEstructura().getJuridica().getIntIdPersona());
			libroDiarioDetalleHaber.setIntParaDocumentoGeneral(detPllaEfect.getIntParaDocumentoGeneralCod());
			libroDiarioDetalleHaber.setStrSerieDocumento(null);
			libroDiarioDetalleHaber.setStrNumeroDocumento(""+detPllaEfect.getId().getIntItemEfectuadoResumen());
			libroDiarioDetalleHaber.setIntPersEmpresaSucursal(intIdEmpresa);
			libroDiarioDetalleHaber.setIntSucuIdSucursal(detPllaEfect.getIntIdsucursaladministraPk());
			libroDiarioDetalleHaber.setIntSudeIdSubSucursal(detPllaEfect.getIntIdsubsucursaladministra());
			libroDiarioDetalleHaber.setIntParaMonedaDocumento(bancoFondo.getIntMonedaCod());
			libroDiarioDetalleHaber.setStrComentario(modeloDetalle.getPlanCuenta().getStrDescripcion());
			libroDiarioDetalleHaber.setBdDebeSoles(null);
			libroDiarioDetalleHaber.setBdHaberExtranjero(null);
			libroDiarioDetalleHaber.setBdDebeExtranjero(null);
			
			
			libroDiarioDetalleHaber.setBdHaberSoles(detPllaEfect.getIngresoDetallePllaEfect().getBdMontoAbono() );
			lstLibroDiarioDetalleHaber.add(libroDiarioDetalleHaber);
			
			
//			if (detPllaEfect.getIntEstadoCod().equals(1)) {
//				
//			}else{
//				
//			}
//			if (detPllaEfect.getBdMontoTotal().compareTo(bdMontoIngresado)==1) {
//				libroDiarioDetalleHaber.setBdHaberSoles(bdMontoIngresado);
//				lstLibroDiarioDetalleHaber.add(libroDiarioDetalleHaber);
//				break;
//			}else if (detPllaEfect.getBdMontoTotal().compareTo(bdMontoIngresado)==-1) {
//				libroDiarioDetalleHaber.setBdHaberSoles(detPllaEfect.getBdMontoTotal());
//				bdMontoIngresado = bdMontoIngresado.subtract(detPllaEfect.getBdMontoTotal());
//				lstLibroDiarioDetalleHaber.add(libroDiarioDetalleHaber);
//			}			
		}
		
		
		return lstLibroDiarioDetalleHaber;
	}
	
	
	public EfectuadoResumen generarIngresoEfectuadoResumen(EfectuadoResumen efectuadoResumenReferencia, Bancofondo bancoFondo, Usuario usuario) 
		throws BusinessException{
		Ingreso ingreso = new Ingreso();
//		EfectuadoResumen efectuadoResumenReferencia = listaEfectuadoResumen.get(0);
		try{			
			Integer intIdEmpresa = efectuadoResumenReferencia.getId().getIntEmpresa();
			
			Sucursal sucursal = usuario.getSucursal();
			Subsucursal	subsucursal = usuario.getSubSucursal();
			EstructuraComp entidadIngreso = efectuadoResumenReferencia.getEntidadIngreso();
//			Persona personaIngreso = efectuadoResumenReferencia.getPersonaIngreso();
			String strObservacion = efectuadoResumenReferencia.getStrObservacionIngreso();
			Modelo modelo = obtenerModelo(intIdEmpresa);
			ModeloDetalle modeloDetalle = modelo.getListModeloDetalle().get(0);
			BigDecimal bdMontoIngresoTotal = efectuadoResumenReferencia.getBdMontIngresar();
			Archivo archivoAdjuntoCheque = efectuadoResumenReferencia.getArchivoCheque();
			
			ingreso.setId(new IngresoId());
			ingreso.getId().setIntIdEmpresa(intIdEmpresa);
			ingreso.getId().setIntIdIngresoGeneral(null);
			ingreso.setIntParaTipoOperacion(Constante.PARAM_T_TIPODEOPERACION_INGRESO);
			ingreso.setIntParaFormaPago(efectuadoResumenReferencia.getIntParaFormaPago());
			ingreso.setIntParaDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_INGRESODECAJA);
			ingreso.setIntItemPeriodoIngreso(obtenerPeriodoActual());
			ingreso.setIntItemIngreso(null);
			ingreso.setIntSucuIdSucursal(sucursal.getId().getIntIdSucursal());
			ingreso.setIntSudeIdSubsucursal(subsucursal.getId().getIntIdSubSucursal());
			ingreso.setIntParaTipoSuboperacion(null); //jchavez 04.07.2014
//			ingreso.setIntParaTipoSuboperacion(intParaTipoSuboperacion);
			ingreso.setTsFechaProceso(obtenerFechaActual()); //jchavez 07.07.2014
			ingreso.setDtFechaIngreso(obtenerFechaActual());
			
			ingreso.setIntParaFondoFijo(bancoFondo.getIntTipoFondoFijo());
			ingreso.setIntItemBancoFondo(bancoFondo.getId().getIntItembancofondo());
			ingreso.setIntItemBancoCuenta(null);
			ingreso.setStrNumeroCheque(efectuadoResumenReferencia.getStrNumeroCheque());
			ingreso.setIntPersEmpresaGirado(intIdEmpresa);
			ingreso.setIntPersPersonaGirado(entidadIngreso.getEstructuraDetalle().getEstructura().getJuridica().getIntIdPersona()); //jchavez 04.07.2014
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
			//jchavez 08.07.2014 nuevos campos
			ingreso.setIntParaModalidadPago(null);
			ingreso.setIntPeriodoSocio(null);
			
			if (efectuadoResumenReferencia.getIntParaFormaPago().equals(Constante.PARAM_T_FORMAPAGOEGRESO_CHEQUE)) {
				ingreso.setIntParaTipoIngreso(archivoAdjuntoCheque.getId().getIntParaTipoCod());
				ingreso.setIntItemArchivoIngreso(archivoAdjuntoCheque.getId().getIntItemArchivo());
				ingreso.setIntHistoricoIngreso(archivoAdjuntoCheque.getId().getIntItemHistorico());
			}
			efectuadoResumenReferencia.setIngresoPllaEfect(ingreso);
			
//			ingreso.setListaIngresoDetalle(new ArrayList<IngresoDetalle>());
			efectuadoResumenReferencia.setListaDetallePlanillaEfectuada(generarIngresoDetalle(efectuadoResumenReferencia, modeloDetalle, bancoFondo, usuario));
//			ingreso.getListaIngresoDetalle().addAll(lstIngresoDetalle);
			
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
			libroDiario.setIntParaTipoDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_INGRESODECAJA);
			libroDiario.setListaLibroDiarioDetalle(new ArrayList<LibroDiarioDetalle>());
			
//			for(EfectuadoResumen efectuadoResumen : listaEfectuadoResumen){
//				LibroDiarioDetalle libroDiarioDetalleHaber = generarLibroDiarioDetalleHaber(efectuadoResumen, modeloDetalle, bancoFondo, usuario);
//				libroDiario.getListaLibroDiarioDetalle().add(libroDiarioDetalleHaber);
//			}		
			
//			LibroDiarioDetalle libroDiarioDetalleHaber = generarLibroDiarioDetalleHaber(efectuadoResumenReferencia, modeloDetalle, bancoFondo, usuario);
			libroDiario.getListaLibroDiarioDetalle().addAll(generarLibroDiarioDetalleHaber(efectuadoResumenReferencia, modeloDetalle, bancoFondo, usuario));
			
			LibroDiarioDetalle libroDiarioDetalleDebe = new LibroDiarioDetalle();
			libroDiarioDetalleDebe.setId(new LibroDiarioDetalleId());
			libroDiarioDetalleDebe.getId().setIntPersEmpresaLibro(intIdEmpresa);
			libroDiarioDetalleDebe.getId().setIntContPeriodoLibro(obtenerPeriodoActual());
			libroDiarioDetalleDebe.setIntPersEmpresaCuenta(bancoFondo.getFondoDetalleUsar().getIntEmpresacuentaPk());
			libroDiarioDetalleDebe.setIntContPeriodo(bancoFondo.getFondoDetalleUsar().getIntPeriodocuenta());
			libroDiarioDetalleDebe.setStrContNumeroCuenta(bancoFondo.getFondoDetalleUsar().getStrNumerocuenta());
			libroDiarioDetalleDebe.setIntPersPersona(entidadIngreso.getEstructuraDetalle().getEstructura().getJuridica().getIntIdPersona());
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
			libroDiarioDetalleDebe.setStrComentario(bancoFondo.getStrObservacion());
			libroDiario.getListaLibroDiarioDetalle().add(libroDiarioDetalleDebe);
			
			efectuadoResumenReferencia.getIngresoPllaEfect().setLibroDiario(libroDiario);
//			ingreso.setLibroDiario(libroDiario);
			
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return efectuadoResumenReferencia;
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
	
//	private Integer obtenerTipoOperacion(List<EfectuadoResumen> listaEfectuadoResumen)throws Exception{
//		Integer intTipoOperacion = null;
//		//Si la lista es de tipoOperacion Cancelacion si todos los EfectuadoResumen son de Cancelacion
//		for(EfectuadoResumen efectuadoResumen : listaEfectuadoResumen){
//			if(efectuadoResumen.getBdMontIngresar().equals(efectuadoResumen.getBdMontoDisponibelIngresar())){
//				intTipoOperacion = Constante.PARAM_T_TIPODESUBOPERACION_CANCELACION;
//			}else{
//				intTipoOperacion = Constante.PARAM_T_TIPODESUBOPERACION_ACUENTA;
//				break;
//			}
//		}
//		
//		return intTipoOperacion;
//	}
	
	private CobroPlanillas generarCobroPlanillas(Ingreso ingreso, EfectuadoResumen efectuadoResumen)throws Exception{
		CobroPlanillas cobroPlanillas = new CobroPlanillas();
		cobroPlanillas.getId().setIntEmpresa(efectuadoResumen.getId().getIntEmpresa());
		cobroPlanillas.getId().setIntItemEfectuadoResumen(efectuadoResumen.getId().getIntItemEfectuadoResumen());
		cobroPlanillas.setIntPersEmpresaIngreso(ingreso.getId().getIntIdEmpresa());
		cobroPlanillas.setIntItemIngresoGeneral(ingreso.getId().getIntIdIngresoGeneral());
		cobroPlanillas.setBdMontoPago(efectuadoResumen.getIngresoDetallePllaEfect().getBdMontoAbono());
		cobroPlanillas.setIntParaTipoMoneda(Constante.PARAM_T_TIPOMONEDA_SOLES);
		cobroPlanillas.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
		cobroPlanillas.setTsFechaPago(obtenerFechaActual());
		
		return cobroPlanillas;
		
	}
	
	private ReciboManualDetalle generarReciboManualDetalle(Ingreso ingreso, GestorCobranza gestorCobranza)throws Exception{
		ReciboManual reciboManual = ingreso.getReciboManual();
		
		ReciboManualDetalle reciboManualDetalle = new ReciboManualDetalle();
		reciboManualDetalle.setId(new ReciboManualDetalleId());
		reciboManualDetalle.getId().setIntPersEmpresa(reciboManual.getId().getIntPersEmpresa());
		reciboManualDetalle.getId().setIntItemReciboManual(reciboManual.getId().getIntItemReciboManual());
		reciboManualDetalle.setIntNumeroRecibo(reciboManual.getReciboManualDetalleUltimo().getIntNumeroRecibo());
		reciboManualDetalle.setIntPersEmpresaIngreso(ingreso.getId().getIntIdEmpresa());
		reciboManualDetalle.setIntItemIngresoGeneral(ingreso.getId().getIntIdIngresoGeneral());
		reciboManualDetalle.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);		
		reciboManualDetalle.setIntPersPersonaGestor(gestorCobranza.getId().getIntPersPersonaGestorPk());
		reciboManualDetalle.setIntPersonaRegistro(ingreso.getIntPersPersonaUsuario());
		return reciboManualDetalle;
	}
	
	public Ingreso grabarIngresoEfectuadoResumen(Ingreso ingreso, List<EfectuadoResumen> listaEfectuadoResumen) throws BusinessException{
		EfectuadoResumen efectResumen = listaEfectuadoResumen.get(0);
		try{
			PlanillaFacadeRemote planillaFacade = (PlanillaFacadeRemote) EJBFactory.getRemote(PlanillaFacadeRemote.class);
			ingreso.setListaIngresoDetalle(new ArrayList<IngresoDetalle>());
			for (EfectuadoResumen efectuadoResumen : efectResumen.getListaDetallePlanillaEfectuada()) {
				ingreso.getListaIngresoDetalle().add(efectuadoResumen.getIngresoDetallePllaEfect());
			}
			ingreso = ingresoService.grabarIngresoGeneral(ingreso);
			
			
			ReciboManual reciboManual = efectResumen.getReciboManualIngreso();
			if(reciboManual != null){
				ingreso.setReciboManual(reciboManual);
				GestorCobranza gestorCobranza = efectResumen.getGestorCobranza();
				ReciboManualDetalle reciboManualDetalle = generarReciboManualDetalle(ingreso, gestorCobranza);
				log.info(reciboManualDetalle);
				boReciboManualDetalle.grabar(reciboManualDetalle);
			}			
			
			for(EfectuadoResumen efectuadoResumen : efectResumen.getListaDetallePlanillaEfectuada()){
				CobroPlanillas cobroPlanillas = generarCobroPlanillas(ingreso, efectuadoResumen);
				log.info(cobroPlanillas);
				planillaFacade.grabarCobroPlanillas(cobroPlanillas);
			}
			
			Boolean blnCancelado = true;
			for(EfectuadoResumen efectuadoResumen : efectResumen.getListaDetallePlanillaEfectuada()){
//				if(efectuadoResumen.getBdMontIngresar().equals(efectuadoResumen.getBdMontoDisponibelIngresar())){
//					efectuadoResumen.setIntParaEstadoPago(Constante.PARAM_T_ESTADOPAGO_CANCELADO);
				if (efectuadoResumen.getIntEstadoCod().equals(Constante.PARAM_T_ESTADOPAGO_CANCELADO)) {
					planillaFacade.modificarEfectuadoResumen(efectuadoResumen);
				}else blnCancelado = false;
				log.info(efectuadoResumen);					
//				}
			}
			if (!blnCancelado) {
				ingreso.setIntParaTipoSuboperacion(Constante.PARAM_T_TIPODESUBOPERACION_ACUENTA);
				ingreso = boIngreso.modificar(ingreso);
			}else{
				ingreso.setIntParaTipoSuboperacion(Constante.PARAM_T_TIPODESUBOPERACION_CANCELACION);
				ingreso = boIngreso.modificar(ingreso);
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
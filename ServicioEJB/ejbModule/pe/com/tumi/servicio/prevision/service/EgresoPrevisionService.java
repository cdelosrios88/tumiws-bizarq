package pe.com.tumi.servicio.prevision.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiario;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiarioDetalle;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiarioDetalleId;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiarioId;
import pe.com.tumi.contabilidad.cierre.facade.LibroDiarioFacadeRemote;
import pe.com.tumi.contabilidad.core.domain.Modelo;
import pe.com.tumi.contabilidad.core.domain.ModeloDetalle;
import pe.com.tumi.contabilidad.core.domain.ModeloDetalleNivel;
import pe.com.tumi.contabilidad.core.domain.ModeloDetalleNivelComp;
import pe.com.tumi.contabilidad.core.domain.ModeloDetalleNivelId;
import pe.com.tumi.contabilidad.core.domain.PlanCuenta;
import pe.com.tumi.contabilidad.core.domain.PlanCuentaId;
import pe.com.tumi.contabilidad.core.facade.ModeloFacadeRemote;
import pe.com.tumi.contabilidad.core.facade.PlanCuentaFacadeRemote;
import pe.com.tumi.credito.socio.captacion.domain.Captacion;
import pe.com.tumi.credito.socio.captacion.domain.CaptacionId;
import pe.com.tumi.credito.socio.captacion.facade.CaptacionFacadeRemote;
import pe.com.tumi.credito.socio.core.domain.SocioEstructura;
import pe.com.tumi.credito.socio.core.facade.SocioFacadeRemote;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.parametro.general.domain.Archivo;
import pe.com.tumi.parametro.general.domain.TipoCambio;
import pe.com.tumi.parametro.general.domain.TipoCambioId;
import pe.com.tumi.parametro.general.facade.GeneralFacadeRemote;
import pe.com.tumi.persona.core.domain.CuentaBancaria;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.seguridad.login.domain.Usuario;
import pe.com.tumi.servicio.prevision.domain.BeneficiarioPrevision;
import pe.com.tumi.servicio.prevision.domain.EstadoPrevision;
import pe.com.tumi.servicio.prevision.domain.ExpedientePrevision;
import pe.com.tumi.servicio.solicitudPrestamo.domain.EgresoDetalleInterfaz;
import pe.com.tumi.tesoreria.banco.domain.Bancocuenta;
import pe.com.tumi.tesoreria.egreso.domain.ControlFondosFijos;
import pe.com.tumi.tesoreria.egreso.domain.Egreso;
import pe.com.tumi.tesoreria.egreso.domain.EgresoDetalle;

public class EgresoPrevisionService {
	
	protected static Logger log = Logger.getLogger(EgresoPrevisionService.class);
	
	public List<EgresoDetalleInterfaz> cargarListaEgresoDetalleInterfaz(ExpedientePrevision expedientePrevision, 
			BeneficiarioPrevision beneficiarioPrevision) throws BusinessException{
		List<EgresoDetalleInterfaz> listaEgresoDetalleInterfaz = new ArrayList<EgresoDetalleInterfaz>();
		try{
			BigDecimal bdPorcentaje = beneficiarioPrevision.getBdPorcentajeBeneficio().divide(new BigDecimal(100));
			log.info("bdPorcentaje:"+bdPorcentaje);
			
			EgresoDetalleInterfaz egresoDetalleInterfaz = new EgresoDetalleInterfaz();
			egresoDetalleInterfaz.setIntParaConcepto(expedientePrevision.getIntParaDocumentoGeneral());
			egresoDetalleInterfaz.setStrNroSolicitud(""+expedientePrevision.getId().getIntItemExpediente());
			egresoDetalleInterfaz.setBdCapital(expedientePrevision.getBdMontoBrutoBeneficio().multiply(bdPorcentaje));
			egresoDetalleInterfaz.setBdInteres(expedientePrevision.getBdMontoInteresBeneficio());
			egresoDetalleInterfaz.setBdGastosAdministrativos(expedientePrevision.getBdMontoGastosADM()!=null?expedientePrevision.getBdMontoGastosADM().multiply(bdPorcentaje).setScale(2,BigDecimal.ROUND_HALF_UP):null);
			
			BigDecimal bdSubTotal = null;
			if(egresoDetalleInterfaz.getIntParaConcepto().equals(Constante.PARAM_T_DOCUMENTOGENERAL_AES)){
				bdSubTotal = egresoDetalleInterfaz.getBdCapital();
				
			}else if(egresoDetalleInterfaz.getIntParaConcepto().equals(Constante.PARAM_T_DOCUMENTOGENERAL_FONDOSEPELIO)){
				bdSubTotal = egresoDetalleInterfaz.getBdCapital().subtract(egresoDetalleInterfaz.getBdGastosAdministrativos());
				
			}else if(egresoDetalleInterfaz.getIntParaConcepto().equals(Constante.PARAM_T_DOCUMENTOGENERAL_FONDORETIRO)){
				bdSubTotal = egresoDetalleInterfaz.getBdCapital()
					.add(egresoDetalleInterfaz.getBdInteres());
					//aun no se añade mora
					//.subtract(egresoDetalleInterfaz.getBdMora());
			}
			
			egresoDetalleInterfaz.setBdSubTotal(bdSubTotal);
			
			listaEgresoDetalleInterfaz.add(egresoDetalleInterfaz);
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return listaEgresoDetalleInterfaz;
	}
	
	private SocioEstructura obtenerSocioEstructura(Persona persona, Integer IntIdEmpresa) throws Exception{
		SocioFacadeRemote socioFacade = (SocioFacadeRemote) EJBFactory.getRemote(SocioFacadeRemote.class);
		List<SocioEstructura> lista = socioFacade.getListaSocioEstrucuraPorIdPersona(persona.getIntIdPersona(), IntIdEmpresa);		
		return lista.get(0);
	}
	
	private Captacion obtenerCaptacion(ExpedientePrevision expedientePrevision)throws Exception{
		CaptacionFacadeRemote captacionFacade = (CaptacionFacadeRemote) EJBFactory.getRemote(CaptacionFacadeRemote.class);
		CaptacionId captacionId = new CaptacionId();
		captacionId.setIntPersEmpresaPk(expedientePrevision.getIntPersEmpresa());
		captacionId.setIntParaTipoCaptacionCod(expedientePrevision.getIntParaTipoCaptacion());
		captacionId.setIntItem(expedientePrevision.getIntItem());
		
		return captacionFacade.listarCaptacionPorPK(captacionId);
	}
	
	private Modelo obtenerModelo(ExpedientePrevision expedientePrevision)throws Exception{
		Integer intIdEmpresa = expedientePrevision.getId().getIntPersEmpresaPk();		
		ModeloFacadeRemote modeloFacade = (ModeloFacadeRemote) EJBFactory.getRemote(ModeloFacadeRemote.class);
		List<Modelo> listaModelo = null; 
		
		if(expedientePrevision.getIntParaDocumentoGeneral().equals(Constante.PARAM_T_DOCUMENTOGENERAL_AES)){
//			listaModelo = modeloFacade.obtenerTipoModeloActual(Constante.PARAM_T_TIPOMODELOCONTABLE_CANCELACIONAES, intIdEmpresa);
			listaModelo = modeloFacade.obtenerTipoModeloActual(Constante.PARAM_T_TIPOMODELOCONTABLE_GIROPREVISION_AES, intIdEmpresa);
			
		}else if(expedientePrevision.getIntParaDocumentoGeneral().equals(Constante.PARAM_T_DOCUMENTOGENERAL_FONDOSEPELIO)){
//			listaModelo = modeloFacade.obtenerTipoModeloActual(Constante.PARAM_T_TIPOMODELOCONTABLE_CANCELACIONSEPELIO, intIdEmpresa);
			listaModelo = modeloFacade.obtenerTipoModeloActual(Constante.PARAM_T_TIPOMODELOCONTABLE_GIROPREVISION_FDOSEPELIO, intIdEmpresa);
			
		}else if(expedientePrevision.getIntParaDocumentoGeneral().equals(Constante.PARAM_T_DOCUMENTOGENERAL_FONDORETIRO)){
//			listaModelo = modeloFacade.obtenerTipoModeloActual(Constante.PARAM_T_TIPOMODELOCONTABLE_CANCELACIONRETIRO, intIdEmpresa);
			listaModelo = modeloFacade.obtenerTipoModeloActual(Constante.PARAM_T_TIPOMODELOCONTABLE_GIROPREVISION_FDORETIRO, intIdEmpresa);
			
		}
		if (listaModelo!=null && !listaModelo.isEmpty()) {
			return listaModelo.get(0);
		}else return null;
		
	}
	
	private Timestamp obtenerFechaActual(){
		return new Timestamp(new Date().getTime());
	}
	
	private TipoCambio obtenerTipoCambioActual(Integer intParaTipoMoneda, Integer intIdEmpresa)throws Exception{
		GeneralFacadeRemote generalFacade = (GeneralFacadeRemote) EJBFactory.getRemote(GeneralFacadeRemote.class);
		TipoCambioId tipoCambioId = new TipoCambioId();
		tipoCambioId.setIntPersEmpresa(intIdEmpresa);
		tipoCambioId.setIntParaMoneda(intParaTipoMoneda);
		tipoCambioId.setDtParaFecha(obtenerFechaActual());
		tipoCambioId.setIntParaClaseTipoCambio(Constante.PARAM_T_TIPOCAMBIO_BANCARIO);
		
		return generalFacade.getTipoCambioPorPK(tipoCambioId);
	}
	
	private TipoCambio obtenerTipoCambioSolicitud(ExpedientePrevision expedientePrevision, Integer intParaTipoMoneda, Integer intIdEmpresa) 
		throws Exception{
		GeneralFacadeRemote generalFacade = (GeneralFacadeRemote) EJBFactory.getRemote(GeneralFacadeRemote.class);		
		EstadoPrevision estadoPrevisionUltimo = expedientePrevision.getEstadoPrevisionUltimo();
		
		TipoCambioId tipoCambioId = new TipoCambioId();
		tipoCambioId.setIntPersEmpresa(intIdEmpresa);
		tipoCambioId.setIntParaMoneda(intParaTipoMoneda);
		tipoCambioId.setDtParaFecha(estadoPrevisionUltimo.getTsFechaEstado());
		tipoCambioId.setIntParaClaseTipoCambio(Constante.PARAM_T_TIPOCAMBIO_BANCARIO);
		
		return generalFacade.getTipoCambioPorPK(tipoCambioId);		
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
	
	private BigDecimal obtenerMontoDeExpediente(ExpedientePrevision expedientePrevision, ModeloDetalleNivel modeloDetalleNivel)throws Exception{
		BigDecimal bdMonto = null;
		//log.info(modeloDetalleNivel);
		if(modeloDetalleNivel.getStrDescripcion().equals("ExPS_MontoBrutoBeneficio_N")){
			bdMonto = expedientePrevision.getBdMontoBrutoBeneficio();
			
		}else if(modeloDetalleNivel.getStrDescripcion().equals("ExPS_MontoGastosAdm_N")){
			bdMonto = expedientePrevision.getBdMontoGastosADM();
			
		}else if(modeloDetalleNivel.getStrDescripcion().equals("ExPS_MontoInteresBeneficio_N")){
			bdMonto = expedientePrevision.getBdMontoInteresBeneficio();
			
		}
		//log.info("bdMonto:"+bdMonto);
		return bdMonto;
	}
	
	private BigDecimal obtenerMontoDiferencial(BigDecimal bdMontoMonedaExtranjera, TipoCambio tipoCambioSolicitud, TipoCambio tipoCambioActual){
		BigDecimal bdMontoSolesSolicitud = bdMontoMonedaExtranjera.multiply(tipoCambioSolicitud.getBdPromedio());
		BigDecimal bdMontoSolesActual = bdMontoMonedaExtranjera.multiply(tipoCambioActual.getBdPromedio());
		
		return bdMontoSolesActual.subtract(bdMontoSolesSolicitud);
	}
	
	private EgresoDetalle generarEgresoDetalle(SocioEstructura socioEstructura, ModeloDetalle modeloDetalle, 
			ExpedientePrevision expedientePrevision, ControlFondosFijos controlFondosFijos, Usuario usuario, TipoCambio tipoCambioSolicitud, 
			TipoCambio tipoCambioActual)
		throws Exception{
		boolean esMonedaExtranjera;
		
		Captacion captacion = expedientePrevision.getCaptacion();
		ModeloDetalleNivel modeloDetalleNivel = modeloDetalle.getListModeloDetalleNivel().get(0);
		Persona personaGirar = expedientePrevision.getPersonaGirar();
		Integer intIdEmpresa = expedientePrevision.getId().getIntPersEmpresaPk();
		
		EgresoDetalle egresoDetalle = new EgresoDetalle();
		egresoDetalle.getId().setIntPersEmpresaEgreso(intIdEmpresa);
		egresoDetalle.setIntParaDocumentoGeneral(expedientePrevision.getIntParaDocumentoGeneral());
		egresoDetalle.setIntParaTipoComprobante(null);
		egresoDetalle.setStrSerieDocumento(null);
		egresoDetalle.setStrNumeroDocumento(""+expedientePrevision.getId().getIntItemExpediente());
		egresoDetalle.setStrDescripcionEgreso(modeloDetalle.getPlanCuenta().getStrDescripcion());
		egresoDetalle.setIntPersEmpresaGirado(intIdEmpresa);
		egresoDetalle.setIntPersonaGirado(personaGirar.getIntIdPersona());
		egresoDetalle.setIntCuentaGirado(expedientePrevision.getCuenta().getId().getIntCuenta());
		egresoDetalle.setIntSucuIdSucursalEgreso(socioEstructura.getIntIdSucursalAdministra());
		egresoDetalle.setIntSudeIdSubsucursalEgreso(socioEstructura.getIntIdSubsucurAdministra());
		egresoDetalle.setIntParaTipoMoneda(captacion.getIntParaMonedaCod());
		if(egresoDetalle.getIntParaTipoMoneda().equals(Constante.PARAM_T_TIPOMONEDA_SOL)){
			esMonedaExtranjera = Boolean.FALSE;
		}else{
			esMonedaExtranjera = Boolean.TRUE;
		}
		
		if(modeloDetalle.getIntParaOpcionDebeHaber().equals(Constante.PARAM_T_OPCIONDEBEHABER_DEBE)){
			egresoDetalle.setBdMontoCargo(obtenerMontoDeExpediente(expedientePrevision,modeloDetalleNivel));
			if(egresoDetalle.getBdMontoCargo()==null) return null;
			if(esMonedaExtranjera) egresoDetalle.setBdMontoDiferencial(obtenerMontoDiferencial(egresoDetalle.getBdMontoCargo(),tipoCambioSolicitud, tipoCambioActual));
		
		}else if(modeloDetalle.getIntParaOpcionDebeHaber().equals(Constante.PARAM_T_OPCIONDEBEHABER_HABER)){
			egresoDetalle.setBdMontoAbono(obtenerMontoDeExpediente(expedientePrevision,modeloDetalleNivel));
			if(egresoDetalle.getBdMontoAbono()==null) return null;
			if(esMonedaExtranjera) egresoDetalle.setBdMontoDiferencial(obtenerMontoDiferencial(egresoDetalle.getBdMontoAbono(),tipoCambioSolicitud, tipoCambioActual));
		}
		
		egresoDetalle.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
		egresoDetalle.setTsFechaRegistro(obtenerFechaActual());
		egresoDetalle.setIntPersEmpresaUsuario(intIdEmpresa);
		egresoDetalle.setIntPersPersonaUsuario(usuario.getIntPersPersonaPk());
		egresoDetalle.setIntPersEmpresaLibroDestino(null);
		egresoDetalle.setIntContPeriodoLibroDestino(null);
		egresoDetalle.setIntContCodigoLibroDestino(null);
		egresoDetalle.setIntPersEmpresaCuenta(modeloDetalle.getId().getIntPersEmpresaCuenta());
		egresoDetalle.setIntContPeriodoCuenta(modeloDetalle.getId().getIntContPeriodoCuenta());
		egresoDetalle.setStrContNumeroCuenta(modeloDetalle.getId().getStrContNumeroCuenta());
		egresoDetalle.setIntParaTipoFondoFijo(controlFondosFijos.getId().getIntParaTipoFondoFijo());
		egresoDetalle.setIntItemPeriodoFondo(controlFondosFijos.getId().getIntItemPeriodoFondo());
		egresoDetalle.setIntSucuIdSucursal(controlFondosFijos.getId().getIntSucuIdSucursal());
		egresoDetalle.setIntItemFondoFijo(controlFondosFijos.getId().getIntItemFondoFijo());
		
		return egresoDetalle;
	}
	
	private EgresoDetalle generarEgresoDetalleCheque(SocioEstructura socioEstructura, ModeloDetalle modeloDetalle, 
			ExpedientePrevision expedientePrevision, Usuario usuario, TipoCambio tipoCambioSolicitud, 
			TipoCambio tipoCambioActual)
		throws Exception{
		boolean esMonedaExtranjera;
		
		Captacion captacion = expedientePrevision.getCaptacion();
		ModeloDetalleNivel modeloDetalleNivel = modeloDetalle.getListModeloDetalleNivel().get(0);
		Persona personaGirar = expedientePrevision.getPersonaGirar();
		Integer intIdEmpresa = expedientePrevision.getId().getIntPersEmpresaPk();
		
		EgresoDetalle egresoDetalle = new EgresoDetalle();
		egresoDetalle.getId().setIntPersEmpresaEgreso(intIdEmpresa);
		egresoDetalle.setIntParaDocumentoGeneral(expedientePrevision.getIntParaDocumentoGeneral());
		egresoDetalle.setIntParaTipoComprobante(null);
		egresoDetalle.setStrSerieDocumento(null);
		egresoDetalle.setStrNumeroDocumento(""+expedientePrevision.getId().getIntItemExpediente());
		egresoDetalle.setStrDescripcionEgreso(modeloDetalle.getPlanCuenta().getStrDescripcion());
		egresoDetalle.setIntPersEmpresaGirado(intIdEmpresa);
		egresoDetalle.setIntPersonaGirado(personaGirar.getIntIdPersona());
		egresoDetalle.setIntCuentaGirado(expedientePrevision.getCuenta().getId().getIntCuenta());
		egresoDetalle.setIntSucuIdSucursalEgreso(socioEstructura.getIntIdSucursalAdministra());
		egresoDetalle.setIntSudeIdSubsucursalEgreso(socioEstructura.getIntIdSubsucurAdministra());
		egresoDetalle.setIntParaTipoMoneda(captacion.getIntParaMonedaCod());
		if(egresoDetalle.getIntParaTipoMoneda().equals(Constante.PARAM_T_TIPOMONEDA_SOL)){
			esMonedaExtranjera = Boolean.FALSE;
		}else{
			esMonedaExtranjera = Boolean.TRUE;
		}
		
		if(modeloDetalle.getIntParaOpcionDebeHaber().equals(Constante.PARAM_T_OPCIONDEBEHABER_DEBE)){
			egresoDetalle.setBdMontoCargo(obtenerMontoDeExpediente(expedientePrevision,modeloDetalleNivel));
			if(egresoDetalle.getBdMontoCargo()==null) return null;
			if(esMonedaExtranjera) egresoDetalle.setBdMontoDiferencial(obtenerMontoDiferencial(egresoDetalle.getBdMontoCargo(),tipoCambioSolicitud, tipoCambioActual));
		
		}else if(modeloDetalle.getIntParaOpcionDebeHaber().equals(Constante.PARAM_T_OPCIONDEBEHABER_HABER)){
			egresoDetalle.setBdMontoAbono(obtenerMontoDeExpediente(expedientePrevision,modeloDetalleNivel));
			if(egresoDetalle.getBdMontoAbono()==null) return null;
			if(esMonedaExtranjera) egresoDetalle.setBdMontoDiferencial(obtenerMontoDiferencial(egresoDetalle.getBdMontoAbono(),tipoCambioSolicitud, tipoCambioActual));
		}
		
		egresoDetalle.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
		egresoDetalle.setTsFechaRegistro(obtenerFechaActual());
		egresoDetalle.setIntPersEmpresaUsuario(intIdEmpresa);
		egresoDetalle.setIntPersPersonaUsuario(usuario.getIntPersPersonaPk());
		egresoDetalle.setIntPersEmpresaLibroDestino(null);
		egresoDetalle.setIntContPeriodoLibroDestino(null);
		egresoDetalle.setIntContCodigoLibroDestino(null);
		egresoDetalle.setIntPersEmpresaCuenta(modeloDetalle.getId().getIntPersEmpresaCuenta());
		egresoDetalle.setIntContPeriodoCuenta(modeloDetalle.getId().getIntContPeriodoCuenta());
		egresoDetalle.setStrContNumeroCuenta(modeloDetalle.getId().getStrContNumeroCuenta());
		egresoDetalle.setIntParaTipoFondoFijo(null);
		egresoDetalle.setIntItemPeriodoFondo(null);
		egresoDetalle.setIntSucuIdSucursal(null);
		egresoDetalle.setIntItemFondoFijo(null);
		
		return egresoDetalle;
	}
	
	private PlanCuenta obtenerPlanCuentaOrigenCFF(ControlFondosFijos controlFondosFijos) throws Exception{		
		log.info(controlFondosFijos);
		
		LibroDiarioFacadeRemote libroDiarioFacade = (LibroDiarioFacadeRemote) EJBFactory.getRemote(LibroDiarioFacadeRemote.class);		
		Egreso egresoCFF = controlFondosFijos.getEgreso();
		PlanCuenta planCuenta = new PlanCuenta();
		planCuenta.setId(new PlanCuentaId());
		LibroDiarioId libroDiarioId = new LibroDiarioId();
		libroDiarioId.setIntPersEmpresaLibro(egresoCFF.getIntPersEmpresaLibro());
		libroDiarioId.setIntContPeriodoLibro(egresoCFF.getIntContPeriodoLibro());
		libroDiarioId.setIntContCodigoLibro(egresoCFF.getIntContCodigoLibro());
		
		LibroDiario libroDiario = libroDiarioFacade.getLibroDiarioPorPk(libroDiarioId);
		List<LibroDiarioDetalle> listaLibroDiarioDetalle = libroDiarioFacade.getListaLibroDiarioDetallePorLibroDiario(libroDiario);
		
		for(LibroDiarioDetalle libroDiarioDetalle : listaLibroDiarioDetalle){
			if(libroDiarioDetalle.getBdDebeSoles()!=null || libroDiarioDetalle.getBdDebeExtranjero()!=null){
				planCuenta.getId().setIntEmpresaCuentaPk(libroDiarioDetalle.getIntPersEmpresaCuenta());
				planCuenta.getId().setIntPeriodoCuenta(libroDiarioDetalle.getIntContPeriodo());
				planCuenta.getId().setStrNumeroCuenta(libroDiarioDetalle.getStrContNumeroCuenta());
			}
		}
		
		return planCuenta;
	}
	
	private LibroDiarioDetalle generarLibroDiarioDetalle(SocioEstructura socioEstructura, ModeloDetalle modeloDetalle, 
			ExpedientePrevision expedientePrevision, ControlFondosFijos controlFondosFijos, TipoCambio tipoCambioActual)
		throws Exception{
	
		boolean esMonedaExtranjera;
		
		Captacion captacion = expedientePrevision.getCaptacion();
		Integer intIdEmpresa = expedientePrevision.getId().getIntPersEmpresaPk();
		Persona personaGirar = expedientePrevision.getPersonaGirar();
		List<EgresoDetalleInterfaz> listaEgresoDetalleInterfaz = expedientePrevision.getListaEgresoDetalleInterfaz();
		
		LibroDiarioDetalle libroDiarioDetalle = new LibroDiarioDetalle();
		libroDiarioDetalle.setId(new LibroDiarioDetalleId());
		libroDiarioDetalle.getId().setIntPersEmpresaLibro(intIdEmpresa);
		libroDiarioDetalle.getId().setIntContPeriodoLibro(obtenerPeriodoActual());		
		
		if(modeloDetalle !=null){
			libroDiarioDetalle.setIntPersEmpresaCuenta(modeloDetalle.getId().getIntPersEmpresaCuenta());
			libroDiarioDetalle.setIntContPeriodo(modeloDetalle.getId().getIntContPeriodoCuenta());
			libroDiarioDetalle.setStrContNumeroCuenta(modeloDetalle.getId().getStrContNumeroCuenta());			
		}else{
			PlanCuenta planCuenta = obtenerPlanCuentaOrigenCFF(controlFondosFijos);
			libroDiarioDetalle.setIntPersEmpresaCuenta(planCuenta.getId().getIntEmpresaCuentaPk());
			libroDiarioDetalle.setIntContPeriodo(planCuenta.getId().getIntPeriodoCuenta());
			libroDiarioDetalle.setStrContNumeroCuenta(planCuenta.getId().getStrNumeroCuenta());			
			libroDiarioDetalle.setIntParaDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_RENDICION);
		}
		
		libroDiarioDetalle.setIntPersPersona(personaGirar.getIntIdPersona());
		libroDiarioDetalle.setIntParaDocumentoGeneral(expedientePrevision.getIntParaDocumentoGeneral());
		libroDiarioDetalle.setStrSerieDocumento(null);
		libroDiarioDetalle.setIntParaMonedaDocumento(captacion.getIntParaMonedaCod());
		if(!libroDiarioDetalle.getIntParaMonedaDocumento().equals(Constante.PARAM_T_TIPOMONEDA_SOL)){
			libroDiarioDetalle.setIntTipoCambio(tipoCambioActual.getId().getIntParaClaseTipoCambio());
			esMonedaExtranjera = Boolean.TRUE;
		}else{
			esMonedaExtranjera = Boolean.FALSE;
		}
		
		if(modeloDetalle!=null){
			libroDiarioDetalle.setStrNumeroDocumento(""+expedientePrevision.getId().getIntItemExpediente());
			libroDiarioDetalle.setIntPersEmpresaSucursal(socioEstructura.getIntEmpresaSucUsuario());
			libroDiarioDetalle.setIntSucuIdSucursal(socioEstructura.getIntIdSucursalUsuario());
			libroDiarioDetalle.setIntSudeIdSubSucursal(socioEstructura.getIntIdSubSucursalUsuario());			
		}else{
			libroDiarioDetalle.setIntPersEmpresaSucursal(controlFondosFijos.getSucursal().getId().getIntPersEmpresaPk());
			libroDiarioDetalle.setIntSucuIdSucursal(controlFondosFijos.getSucursal().getId().getIntIdSucursal());
			libroDiarioDetalle.setIntSudeIdSubSucursal(controlFondosFijos.getSubsucursal().getId().getIntIdSubSucursal());			
		}
	
		if(modeloDetalle!=null){
			ModeloDetalleNivel modeloDetalleNivel = modeloDetalle.getListModeloDetalleNivel().get(0);
			if(modeloDetalle.getIntParaOpcionDebeHaber().equals(Constante.PARAM_T_OPCIONDEBEHABER_DEBE)){
				if(!esMonedaExtranjera){
					libroDiarioDetalle.setBdDebeSoles(obtenerMontoDeExpediente(expedientePrevision, modeloDetalleNivel));					
				}else{
					libroDiarioDetalle.setBdDebeExtranjero(obtenerMontoDeExpediente(expedientePrevision, modeloDetalleNivel));
					libroDiarioDetalle.setBdDebeSoles(libroDiarioDetalle.getBdDebeExtranjero().multiply(tipoCambioActual.getBdPromedio()));
				}			
			}else if(modeloDetalle.getIntParaOpcionDebeHaber().equals(Constante.PARAM_T_OPCIONDEBEHABER_HABER)){
				if(!esMonedaExtranjera){
					libroDiarioDetalle.setBdHaberSoles(obtenerMontoDeExpediente(expedientePrevision, modeloDetalleNivel));					
				}else{
					libroDiarioDetalle.setBdHaberExtranjero(obtenerMontoDeExpediente(expedientePrevision, modeloDetalleNivel));
					libroDiarioDetalle.setBdHaberSoles(libroDiarioDetalle.getBdHaberExtranjero().multiply(tipoCambioActual.getBdPromedio()));
				}
			}
		}else{
			EgresoDetalleInterfaz egresoDetalleInterfaz = listaEgresoDetalleInterfaz.get(0);
			if(!esMonedaExtranjera){
				libroDiarioDetalle.setBdHaberSoles(egresoDetalleInterfaz.getBdSubTotal());
			}else{
				libroDiarioDetalle.setBdHaberExtranjero(egresoDetalleInterfaz.getBdSubTotal());
				libroDiarioDetalle.setBdHaberSoles(libroDiarioDetalle.getBdHaberExtranjero().multiply(tipoCambioActual.getBdPromedio()));
			}
		}
		return libroDiarioDetalle;
	}
	
	private LibroDiarioDetalle generarLibroDiarioDetalleCheque(SocioEstructura socioEstructura, ModeloDetalle modeloDetalle, 
			ExpedientePrevision expedientePrevision, Bancocuenta bancoCuenta, TipoCambio tipoCambioActual)
		throws Exception{
	
		boolean esMonedaExtranjera;
		
		Captacion captacion = expedientePrevision.getCaptacion();
		Integer intIdEmpresa = expedientePrevision.getId().getIntPersEmpresaPk();
		Persona personaGirar = expedientePrevision.getPersonaGirar();
		List<EgresoDetalleInterfaz> listaEgresoDetalleInterfaz = expedientePrevision.getListaEgresoDetalleInterfaz();
		
		LibroDiarioDetalle libroDiarioDetalle = new LibroDiarioDetalle();
		libroDiarioDetalle.getId().setIntPersEmpresaLibro(intIdEmpresa);
		libroDiarioDetalle.getId().setIntContPeriodoLibro(obtenerPeriodoActual());		
		
		if(modeloDetalle !=null){
			libroDiarioDetalle.setIntPersEmpresaCuenta(modeloDetalle.getId().getIntPersEmpresaCuenta());
			libroDiarioDetalle.setIntContPeriodo(modeloDetalle.getId().getIntContPeriodoCuenta());
			libroDiarioDetalle.setStrContNumeroCuenta(modeloDetalle.getId().getStrContNumeroCuenta());			
		}else{
			libroDiarioDetalle.setIntPersEmpresaCuenta(bancoCuenta.getIntEmpresacuentaPk());
			libroDiarioDetalle.setIntContPeriodo(bancoCuenta.getIntPeriodocuenta());
			libroDiarioDetalle.setStrContNumeroCuenta(bancoCuenta.getStrNumerocuenta());			
			libroDiarioDetalle.setIntParaDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_RENDICION);
		}
		
		libroDiarioDetalle.setIntPersPersona(personaGirar.getIntIdPersona());
		libroDiarioDetalle.setIntParaDocumentoGeneral(expedientePrevision.getIntParaDocumentoGeneral());
		libroDiarioDetalle.setStrSerieDocumento(null);
		libroDiarioDetalle.setIntParaMonedaDocumento(captacion.getIntParaMonedaCod());
		if(!libroDiarioDetalle.getIntParaMonedaDocumento().equals(Constante.PARAM_T_TIPOMONEDA_SOL)){
			libroDiarioDetalle.setIntTipoCambio(tipoCambioActual.getId().getIntParaClaseTipoCambio());
			esMonedaExtranjera = Boolean.TRUE;
		}else{
			esMonedaExtranjera = Boolean.FALSE;
		}
		
		if(modeloDetalle!=null){
			libroDiarioDetalle.setStrNumeroDocumento(""+expedientePrevision.getId().getIntItemExpediente());
			libroDiarioDetalle.setIntPersEmpresaSucursal(socioEstructura.getIntEmpresaSucUsuario());
			libroDiarioDetalle.setIntSucuIdSucursal(socioEstructura.getIntIdSucursalUsuario());
			libroDiarioDetalle.setIntSudeIdSubSucursal(socioEstructura.getIntIdSubSucursalUsuario());			
		}else{
			libroDiarioDetalle.setIntPersEmpresaSucursal(Constante.PARAM_EMPRESASESION);
			libroDiarioDetalle.setIntSucuIdSucursal(Constante.SUCURSAL_SEDECENTRAL);
			libroDiarioDetalle.setIntSudeIdSubSucursal(Constante.SUBSUCURSAL_SEDE1);			
		}
	
		if(modeloDetalle!=null){
			ModeloDetalleNivel modeloDetalleNivel = modeloDetalle.getListModeloDetalleNivel().get(0);
			if(modeloDetalle.getIntParaOpcionDebeHaber().equals(Constante.PARAM_T_OPCIONDEBEHABER_DEBE)){
				if(!esMonedaExtranjera){
					libroDiarioDetalle.setBdDebeSoles(obtenerMontoDeExpediente(expedientePrevision, modeloDetalleNivel));					
				}else{
					libroDiarioDetalle.setBdDebeExtranjero(obtenerMontoDeExpediente(expedientePrevision, modeloDetalleNivel));
					libroDiarioDetalle.setBdDebeSoles(libroDiarioDetalle.getBdDebeExtranjero().multiply(tipoCambioActual.getBdPromedio()));
				}			
			}else if(modeloDetalle.getIntParaOpcionDebeHaber().equals(Constante.PARAM_T_OPCIONDEBEHABER_HABER)){
				if(!esMonedaExtranjera){
					libroDiarioDetalle.setBdHaberSoles(obtenerMontoDeExpediente(expedientePrevision, modeloDetalleNivel));					
				}else{
					libroDiarioDetalle.setBdHaberExtranjero(obtenerMontoDeExpediente(expedientePrevision, modeloDetalleNivel));
					libroDiarioDetalle.setBdHaberSoles(libroDiarioDetalle.getBdHaberExtranjero().multiply(tipoCambioActual.getBdPromedio()));
				}
			}
		}else{
			EgresoDetalleInterfaz egresoDetalleInterfaz = listaEgresoDetalleInterfaz.get(0);
			if(!esMonedaExtranjera){
				libroDiarioDetalle.setBdHaberSoles(egresoDetalleInterfaz.getBdSubTotal());
			}else{
				libroDiarioDetalle.setBdHaberExtranjero(egresoDetalleInterfaz.getBdSubTotal());
				libroDiarioDetalle.setBdHaberSoles(libroDiarioDetalle.getBdHaberExtranjero().multiply(tipoCambioActual.getBdPromedio()));
			}
		}
		return libroDiarioDetalle;
	}
	
	public Egreso generarEgresoPrevision(ExpedientePrevision expedientePrevision, ControlFondosFijos controlFondosFijos, Usuario usuario
			) throws BusinessException{
		Egreso egreso = new Egreso();
		try{
			BeneficiarioPrevision beneficiarioPrevisionSeleccionado = expedientePrevision.getBeneficiarioPrevisionGirar();
			Persona personaGirar = expedientePrevision.getPersonaGirar();
			String 	strGlosaEgreso = expedientePrevision.getStrGlosaEgreso();
			List<EgresoDetalleInterfaz> listaEgresoDetalleInterfaz = expedientePrevision.getListaEgresoDetalleInterfaz();
			Persona personaApoderado = beneficiarioPrevisionSeleccionado.getPersonaApoderado();
			Archivo archivoCartaPoder = beneficiarioPrevisionSeleccionado.getArchivoCartaPoder();
			BigDecimal bdTotalEgresoDetalleInterfaz = listaEgresoDetalleInterfaz.get(0).getBdSubTotal();
			
			Integer intIdEmpresa = expedientePrevision.getId().getIntPersEmpresaPk();
			SocioEstructura socioEstructura = obtenerSocioEstructura(personaGirar, intIdEmpresa);
			Captacion captacion = obtenerCaptacion(expedientePrevision);
			Modelo modelo = obtenerModelo(expedientePrevision);
			
			expedientePrevision.setCaptacion(captacion);
			TipoCambio tipoCambioActual = obtenerTipoCambioActual(captacion.getIntParaMonedaCod(), intIdEmpresa);			
			TipoCambio tipoCambioSolicitud = obtenerTipoCambioSolicitud(expedientePrevision, captacion.getIntParaMonedaCod(), intIdEmpresa);
			
			egreso.getId().setIntPersEmpresaEgreso(intIdEmpresa);
			egreso.setIntParaTipoOperacion(Constante.PARAM_T_TIPODEOPERACION_EGRESO);
			egreso.setIntParaFormaPago(Constante.PARAM_T_FORMAPAGOEGRESO_EFECTIVO);
			egreso.setIntParaDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_RENDICION);
			egreso.setIntItemPeriodoEgreso(obtenerPeriodoActual());
			egreso.setIntItemEgreso(null);
			egreso.setIntSucuIdSucursal(usuario.getSucursal().getId().getIntIdSucursal());
			egreso.setIntSudeIdSubsucursal(usuario.getSubSucursal().getId().getIntIdSubSucursal());
			if(beneficiarioPrevisionSeleccionado.isEsUltimoBeneficiarioAGirar()){
				egreso.setIntParaSubTipoOperacion(Constante.PARAM_T_TIPODESUBOPERACION_CANCELACION);
			}else{
				egreso.setIntParaSubTipoOperacion(Constante.PARAM_T_TIPODESUBOPERACION_ACUENTA);
			}
			egreso.setTsFechaProceso(obtenerFechaActual());
			egreso.setDtFechaEgreso(obtenerFechaActual());
			egreso.setIntParaTipoFondoFijo(controlFondosFijos.getId().getIntParaTipoFondoFijo());
			egreso.setIntItemPeriodoFondo(controlFondosFijos.getId().getIntItemPeriodoFondo());
			egreso.setIntItemFondoFijo(controlFondosFijos.getId().getIntItemFondoFijo());
			egreso.setIntItemBancoFondo(null);
			egreso.setIntItemBancoCuenta(null);
			egreso.setIntItemBancoCuentaCheque(null);
			egreso.setIntNumeroPlanilla(null);
			egreso.setIntNumeroCheque(null);
			egreso.setIntNumeroTransferencia(null);
			egreso.setTsFechaPagoDiferido(null);
			egreso.setIntPersEmpresaGirado(intIdEmpresa);
			egreso.setIntPersPersonaGirado(personaGirar.getIntIdPersona());
			egreso.setIntCuentaGirado(expedientePrevision.getCuenta().getId().getIntCuenta());
			//20.05.2014 jchavez cambio de tipo de dato
			egreso.setStrPersCuentaBancariaGirado(null);	
//			egreso.setIntPersCuentaBancariaGirado(null);
			egreso.setIntPersEmpresaBeneficiario(beneficiarioPrevisionSeleccionado.getId().getIntPersEmpresaPrevision());
			egreso.setIntPersPersonaBeneficiario(beneficiarioPrevisionSeleccionado.getIntPersPersonaBeneficiario());
			egreso.setIntPersCuentaBancariaBeneficiario(null);
			if(personaApoderado != null){
				egreso.setIntPersPersonaApoderado(personaApoderado.getIntIdPersona());
				egreso.setIntPersEmpresaApoderado(intIdEmpresa);
			}
			egreso.setBdMontoTotal(bdTotalEgresoDetalleInterfaz);
			egreso.setStrObservacion(strGlosaEgreso);
			egreso.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			egreso.setTsFechaRegistro(obtenerFechaActual());
			egreso.setIntPersEmpresaUsuario(intIdEmpresa);
			egreso.setIntPersPersonaUsuario(usuario.getIntPersPersonaPk());
			egreso.setIntPersEmpresaEgresoAnula(null);
			egreso.setIntPersPersonaEgresoAnula(null);
			if(archivoCartaPoder != null){
				egreso.setIntParaTipoApoderado(archivoCartaPoder.getId().getIntParaTipoCod());
				egreso.setIntItemArchivoApoderado(archivoCartaPoder.getId().getIntItemArchivo());
				egreso.setIntItemHistoricoApoderado(archivoCartaPoder.getId().getIntItemHistorico());
			}
			egreso.setIntParaTipoGiro(null);
			egreso.setIntItemArchivoGiro(null);
			egreso.setIntItemHistoricoGiro(null);
			
			for(ModeloDetalle modeloDetalle : modelo.getListModeloDetalle()){
				EgresoDetalle egresoDetalle = generarEgresoDetalle(socioEstructura, modeloDetalle, expedientePrevision, 
						controlFondosFijos, usuario, tipoCambioSolicitud, tipoCambioActual);
				log.info(egresoDetalle);
				if(egresoDetalle!=null)egreso.getListaEgresoDetalle().add(egresoDetalle);
			}
			
			LibroDiario libroDiario = new LibroDiario();
			libroDiario.setId(new LibroDiarioId());
			libroDiario.getId().setIntPersEmpresaLibro(intIdEmpresa);
			libroDiario.getId().setIntContPeriodoLibro(obtenerPeriodoActual());
			libroDiario.setStrGlosa(strGlosaEgreso);
			libroDiario.setTsFechaRegistro(obtenerFechaActual());
			libroDiario.setTsFechaDocumento(obtenerFechaActual());
			libroDiario.setIntPersEmpresaUsuario(intIdEmpresa);
			libroDiario.setIntPersPersonaUsuario(usuario.getIntPersPersonaPk());
			libroDiario.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			libroDiario.setIntParaTipoDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_RENDICION);
			libroDiario.setListaLibroDiarioDetalle(new ArrayList<LibroDiarioDetalle>());
			for(ModeloDetalle modeloDetalle : modelo.getListModeloDetalle()){
				LibroDiarioDetalle libroDiarioDetalle = generarLibroDiarioDetalle(socioEstructura, modeloDetalle, 
						expedientePrevision, controlFondosFijos, tipoCambioActual);
				log.info(libroDiarioDetalle);
				if(libroDiarioDetalle!=null)	libroDiario.getListaLibroDiarioDetalle().add(libroDiarioDetalle);
			}
			
			LibroDiarioDetalle libroDiarioDetalle = generarLibroDiarioDetalle(socioEstructura, null, 
					expedientePrevision, controlFondosFijos, tipoCambioActual);
			libroDiario.getListaLibroDiarioDetalle().add(libroDiarioDetalle);
			
			egreso.setLibroDiario(libroDiario);
			egreso.setControlFondosFijos(controlFondosFijos);
			
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return egreso;
	}
//	
//	public Egreso generarEgresoPrevisionCheque(ExpedientePrevision expedientePrevision, Bancocuenta bancoCuenta, Usuario usuario, 
//		Integer intNumeroCheque, Integer intTipoDocumentoValidar) throws BusinessException{
//		Egreso egreso = new Egreso();
//		try{
//			BeneficiarioPrevision beneficiarioPrevisionSeleccionado = expedientePrevision.getBeneficiarioPrevisionGirar();
//			Persona personaGirar = expedientePrevision.getPersonaGirar();
//			String 	strGlosaEgreso = expedientePrevision.getStrGlosaEgreso();
//			List<EgresoDetalleInterfaz> listaEgresoDetalleInterfaz = expedientePrevision.getListaEgresoDetalleInterfaz();
//			Persona personaApoderado = beneficiarioPrevisionSeleccionado.getPersonaApoderado();
//			Archivo archivoCartaPoder = beneficiarioPrevisionSeleccionado.getArchivoCartaPoder();
//			BigDecimal bdTotalEgresoDetalleInterfaz = listaEgresoDetalleInterfaz.get(0).getBdSubTotal();
//			
//			Integer intIdEmpresa = expedientePrevision.getId().getIntPersEmpresaPk();
//			SocioEstructura socioEstructura = obtenerSocioEstructura(personaGirar, intIdEmpresa);
//			Captacion captacion = obtenerCaptacion(expedientePrevision);
//			Modelo modelo = obtenerModelo(expedientePrevision);
//			
//			expedientePrevision.setCaptacion(captacion);
//			TipoCambio tipoCambioActual = obtenerTipoCambioActual(captacion.getIntParaMonedaCod(), intIdEmpresa);			
//			TipoCambio tipoCambioSolicitud = obtenerTipoCambioSolicitud(expedientePrevision, captacion.getIntParaMonedaCod(), intIdEmpresa);
//			
//			egreso.getId().setIntPersEmpresaEgreso(intIdEmpresa);
//			egreso.setIntParaTipoOperacion(Constante.PARAM_T_TIPODEOPERACION_EGRESO);
//			egreso.setIntParaFormaPago(Constante.PARAM_T_FORMAPAGOEGRESO_EFECTIVO);
//			egreso.setIntParaDocumentoGeneral(intTipoDocumentoValidar);
//			egreso.setIntItemPeriodoEgreso(obtenerPeriodoActual());
//			egreso.setIntItemEgreso(null);
//			egreso.setIntSucuIdSucursal(usuario.getSucursal().getId().getIntIdSucursal());
//			egreso.setIntSudeIdSubsucursal(usuario.getSubSucursal().getId().getIntIdSubSucursal());
//			if(beneficiarioPrevisionSeleccionado.isEsUltimoBeneficiarioAGirar()){
//				egreso.setIntParaSubTipoOperacion(Constante.PARAM_T_TIPODESUBOPERACION_CANCELACION);
//			}else{
//				egreso.setIntParaSubTipoOperacion(Constante.PARAM_T_TIPODESUBOPERACION_ACUENTA);
//			}
//			egreso.setTsFechaProceso(obtenerFechaActual());
//			egreso.setDtFechaEgreso(obtenerFechaActual());
//			egreso.setIntParaTipoFondoFijo(null);
//			egreso.setIntItemPeriodoFondo(null);
//			egreso.setIntItemFondoFijo(null);
//			egreso.setIntItemBancoFondo(bancoCuenta.getId().getIntItembancofondo());
//			egreso.setIntItemBancoCuenta(bancoCuenta.getId().getIntItembancocuenta());
//			egreso.setIntItemBancoCuentaCheque(null);
//			egreso.setIntNumeroPlanilla(null);
//			egreso.setIntNumeroCheque(intNumeroCheque);
//			egreso.setIntNumeroTransferencia(null);
//			egreso.setTsFechaPagoDiferido(null);
//			egreso.setIntPersEmpresaGirado(intIdEmpresa);
//			egreso.setIntPersPersonaGirado(personaGirar.getIntIdPersona());
//			egreso.setIntCuentaGirado(expedientePrevision.getCuenta().getId().getIntCuenta());
//			egreso.setIntPersCuentaBancariaGirado(null);
//			egreso.setIntPersEmpresaBeneficiario(beneficiarioPrevisionSeleccionado.getId().getIntPersEmpresaPrevision());
//			egreso.setIntPersPersonaBeneficiario(beneficiarioPrevisionSeleccionado.getIntPersPersonaBeneficiario());
//			egreso.setIntPersCuentaBancariaBeneficiario(null);
//			if(personaApoderado != null){
//				egreso.setIntPersPersonaApoderado(personaApoderado.getIntIdPersona());
//				egreso.setIntPersEmpresaApoderado(intIdEmpresa);
//			}
//			egreso.setBdMontoTotal(bdTotalEgresoDetalleInterfaz);
//			egreso.setStrObservacion(strGlosaEgreso);
//			egreso.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
//			egreso.setTsFechaRegistro(obtenerFechaActual());
//			egreso.setIntPersEmpresaUsuario(intIdEmpresa);
//			egreso.setIntPersPersonaUsuario(usuario.getIntPersPersonaPk());
//			egreso.setIntPersEmpresaEgresoAnula(null);
//			egreso.setIntPersPersonaEgresoAnula(null);
//			if(archivoCartaPoder != null){
//				egreso.setIntParaTipoApoderado(archivoCartaPoder.getId().getIntParaTipoCod());
//				egreso.setIntItemArchivoApoderado(archivoCartaPoder.getId().getIntItemArchivo());
//				egreso.setIntItemHistoricoApoderado(archivoCartaPoder.getId().getIntItemHistorico());
//			}
//			egreso.setIntParaTipoGiro(null);
//			egreso.setIntItemArchivoGiro(null);
//			egreso.setIntItemHistoricoGiro(null);
//			
//			for(ModeloDetalle modeloDetalle : modelo.getListModeloDetalle()){
//				EgresoDetalle egresoDetalle = generarEgresoDetalleCheque(socioEstructura, modeloDetalle, expedientePrevision, 
//						usuario, tipoCambioSolicitud, tipoCambioActual);
//				log.info(egresoDetalle);
//				if(egresoDetalle!=null)egreso.getListaEgresoDetalle().add(egresoDetalle);
//			}
//			
//			LibroDiario libroDiario = new LibroDiario();
//			libroDiario.getId().setIntPersEmpresaLibro(intIdEmpresa);
//			libroDiario.getId().setIntContPeriodoLibro(obtenerPeriodoActual());
//			libroDiario.setStrGlosa(strGlosaEgreso);
//			libroDiario.setTsFechaRegistro(obtenerFechaActual());
//			libroDiario.setTsFechaDocumento(obtenerFechaActual());
//			libroDiario.setIntPersEmpresaUsuario(intIdEmpresa);
//			libroDiario.setIntPersPersonaUsuario(usuario.getIntPersPersonaPk());
//			libroDiario.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
//			libroDiario.setIntParaTipoDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_RENDICION);
//			
//			for(ModeloDetalle modeloDetalle : modelo.getListModeloDetalle()){
//				LibroDiarioDetalle libroDiarioDetalle = generarLibroDiarioDetalleCheque(socioEstructura, modeloDetalle, 
//						expedientePrevision, bancoCuenta, tipoCambioActual);
//				log.info(libroDiarioDetalle);
//				if(libroDiarioDetalle!=null)	libroDiario.getListaLibroDiarioDetalle().add(libroDiarioDetalle);
//			}
//			
//			LibroDiarioDetalle libroDiarioDetalle = generarLibroDiarioDetalleCheque(socioEstructura, null, 
//					expedientePrevision, bancoCuenta, tipoCambioActual);
//			libroDiario.getListaLibroDiarioDetalle().add(libroDiarioDetalle);
//			
//			egreso.setLibroDiario(libroDiario);
//			egreso.setControlFondosFijos(null);
//			
//		}catch(Exception e){
//			throw new BusinessException(e);
//		}
//		return egreso;
//	}	
	
	public Egreso generarEgresoPrevisionTransferencia(ExpedientePrevision expedientePrevision, Bancocuenta bancoCuentaOrigen, Usuario usuario, 
		Integer intNumeroTransferencia, Integer intTipoDocumentoValidar, CuentaBancaria cuentaBancariaDestino) throws BusinessException{
		Egreso egreso = new Egreso();
		try{
			BeneficiarioPrevision beneficiarioPrevisionSeleccionado = expedientePrevision.getBeneficiarioPrevisionGirar();
			Persona personaGirar = expedientePrevision.getPersonaGirar();
			String 	strGlosaEgreso = expedientePrevision.getStrGlosaEgreso();
			List<EgresoDetalleInterfaz> listaEgresoDetalleInterfaz = expedientePrevision.getListaEgresoDetalleInterfaz();
			Persona personaApoderado = beneficiarioPrevisionSeleccionado.getPersonaApoderado();
			Archivo archivoCartaPoder = beneficiarioPrevisionSeleccionado.getArchivoCartaPoder();
			BigDecimal bdTotalEgresoDetalleInterfaz = listaEgresoDetalleInterfaz.get(0).getBdSubTotal();
			
			Integer intIdEmpresa = expedientePrevision.getId().getIntPersEmpresaPk();
			SocioEstructura socioEstructura = obtenerSocioEstructura(personaGirar, intIdEmpresa);
			Captacion captacion = obtenerCaptacion(expedientePrevision);
			Modelo modelo = obtenerModelo(expedientePrevision);
			if (modelo != null) {
				expedientePrevision.setCaptacion(captacion);
				TipoCambio tipoCambioActual = obtenerTipoCambioActual(captacion.getIntParaMonedaCod(), intIdEmpresa);			
				TipoCambio tipoCambioSolicitud = obtenerTipoCambioSolicitud(expedientePrevision, captacion.getIntParaMonedaCod(), intIdEmpresa);
				
				egreso.getId().setIntPersEmpresaEgreso(intIdEmpresa);
				egreso.setIntParaTipoOperacion(Constante.PARAM_T_TIPODEOPERACION_EGRESO);
				egreso.setIntParaFormaPago(Constante.PARAM_T_FORMAPAGOEGRESO_EFECTIVO);
				egreso.setIntParaDocumentoGeneral(intTipoDocumentoValidar);
				egreso.setIntItemPeriodoEgreso(obtenerPeriodoActual());
				egreso.setIntItemEgreso(null);
				egreso.setIntSucuIdSucursal(usuario.getSucursal().getId().getIntIdSucursal());
				egreso.setIntSudeIdSubsucursal(usuario.getSubSucursal().getId().getIntIdSubSucursal());
				if(beneficiarioPrevisionSeleccionado.isEsUltimoBeneficiarioAGirar()){
					egreso.setIntParaSubTipoOperacion(Constante.PARAM_T_TIPODESUBOPERACION_CANCELACION);
				}else{
					egreso.setIntParaSubTipoOperacion(Constante.PARAM_T_TIPODESUBOPERACION_ACUENTA);
				}
				egreso.setTsFechaProceso(obtenerFechaActual());
				egreso.setDtFechaEgreso(obtenerFechaActual());
				egreso.setIntParaTipoFondoFijo(null);
				egreso.setIntItemPeriodoFondo(null);
				egreso.setIntItemFondoFijo(null);
				egreso.setIntItemBancoFondo(bancoCuentaOrigen.getId().getIntItembancofondo());
				egreso.setIntItemBancoCuenta(bancoCuentaOrigen.getId().getIntItembancocuenta());
				egreso.setIntItemBancoCuentaCheque(null);
				egreso.setIntNumeroPlanilla(null);
				egreso.setIntNumeroCheque(null);
				egreso.setIntNumeroTransferencia(intNumeroTransferencia);
				egreso.setTsFechaPagoDiferido(null);
				egreso.setIntPersEmpresaGirado(intIdEmpresa);
				egreso.setIntPersPersonaGirado(personaGirar.getIntIdPersona());
				egreso.setIntCuentaGirado(expedientePrevision.getCuenta().getId().getIntCuenta());
				//20.05.2014 jchavez cambio de tipo de dato
				egreso.setStrPersCuentaBancariaGirado(cuentaBancariaDestino.getStrNroCuentaBancaria());	
//				egreso.setIntPersCuentaBancariaGirado(cuentaBancariaDestino.getId().getIntIdCuentaBancaria());
				egreso.setIntPersEmpresaBeneficiario(beneficiarioPrevisionSeleccionado.getId().getIntPersEmpresaPrevision());
				egreso.setIntPersPersonaBeneficiario(beneficiarioPrevisionSeleccionado.getIntPersPersonaBeneficiario());
				egreso.setIntPersCuentaBancariaBeneficiario(null);
				if(personaApoderado != null){
					egreso.setIntPersPersonaApoderado(personaApoderado.getIntIdPersona());
					egreso.setIntPersEmpresaApoderado(intIdEmpresa);
				}
				egreso.setBdMontoTotal(bdTotalEgresoDetalleInterfaz);
				egreso.setStrObservacion(strGlosaEgreso);
				egreso.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
				egreso.setTsFechaRegistro(obtenerFechaActual());
				egreso.setIntPersEmpresaUsuario(intIdEmpresa);
				egreso.setIntPersPersonaUsuario(usuario.getIntPersPersonaPk());
				egreso.setIntPersEmpresaEgresoAnula(null);
				egreso.setIntPersPersonaEgresoAnula(null);
				if(archivoCartaPoder != null){
					egreso.setIntParaTipoApoderado(archivoCartaPoder.getId().getIntParaTipoCod());
					egreso.setIntItemArchivoApoderado(archivoCartaPoder.getId().getIntItemArchivo());
					egreso.setIntItemHistoricoApoderado(archivoCartaPoder.getId().getIntItemHistorico());
				}
				egreso.setIntParaTipoGiro(null);
				egreso.setIntItemArchivoGiro(null);
				egreso.setIntItemHistoricoGiro(null);

				for(ModeloDetalle modeloDetalle : modelo.getListModeloDetalle()){
					EgresoDetalle egresoDetalle = generarEgresoDetalleCheque(socioEstructura, modeloDetalle, expedientePrevision, 
							usuario, tipoCambioSolicitud, tipoCambioActual);
					log.info(egresoDetalle);
					if(egresoDetalle!=null)egreso.getListaEgresoDetalle().add(egresoDetalle);
				}
				
				LibroDiario libroDiario = new LibroDiario();
				libroDiario.getId().setIntPersEmpresaLibro(intIdEmpresa);
				libroDiario.getId().setIntContPeriodoLibro(obtenerPeriodoActual());
				libroDiario.setStrGlosa(strGlosaEgreso);
				libroDiario.setTsFechaRegistro(obtenerFechaActual());
				libroDiario.setTsFechaDocumento(obtenerFechaActual());
				libroDiario.setIntPersEmpresaUsuario(intIdEmpresa);
				libroDiario.setIntPersPersonaUsuario(usuario.getIntPersPersonaPk());
				libroDiario.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
				libroDiario.setIntParaTipoDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_RENDICION);
				
				for(ModeloDetalle modeloDetalle : modelo.getListModeloDetalle()){
					LibroDiarioDetalle libroDiarioDetalle = generarLibroDiarioDetalleCheque(socioEstructura, modeloDetalle, 
							expedientePrevision, bancoCuentaOrigen, tipoCambioActual);
					log.info(libroDiarioDetalle);
					if(libroDiarioDetalle!=null)	libroDiario.getListaLibroDiarioDetalle().add(libroDiarioDetalle);
				}
				
				LibroDiarioDetalle libroDiarioDetalle = generarLibroDiarioDetalleCheque(socioEstructura, null, 
						expedientePrevision, bancoCuentaOrigen, tipoCambioActual);
				libroDiario.getListaLibroDiarioDetalle().add(libroDiarioDetalle);
				
				egreso.setLibroDiario(libroDiario);
				egreso.setControlFondosFijos(null);
			}else
				throw new BusinessException("No se encontró modelo para realizar este giro.");
			
			
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return egreso;
	}
	
	/*******************************************************************
	 * MODIFICACIONES PARA REALIZAR GIRO POR :
	 * SERVICIO - GIRO NORMAL
	 * TESORERIA - FONDO FIJO Y BANCO (CHEQUES Y TRANSFERENCIA TERCEROS)
	 * AUTOR: JUNIOR CHAVEZ
	 * FECHA: 15.05.2014
	 *******************************************************************/
	
	/**
	 * GIRO PREVISION - POR SERVICIO. APLICA TBM PARA FONDOS FIJOS
	 */
	
	/**
	 * Creación: JCHAVEZ 21.01.2014
	 * Generación del egreso de Giro de Prevision, usado en GiroPrevisionController, método grabar()
	 * @param expedientePrevision
	 * @param controlFondosFijos
	 * @param usuario
	 * @return
	 * @throws BusinessException
	 */
	public Egreso generarEgresoGiroPrevision(ExpedientePrevision expedientePrevision, ControlFondosFijos controlFondosFijos, Usuario usuario) throws BusinessException{
		Egreso egreso = new Egreso();
		Calendar cal = Calendar.getInstance();
		Integer anioActual = cal.get(Calendar.YEAR);
		//Datos del Expediente Previsión
		Integer intIdEmpresa = null;
		BeneficiarioPrevision beneficiarioPrevisionSeleccionado = null;
		Persona personaGirar = null;
		String 	strGlosaEgreso = null;
		List<EgresoDetalleInterfaz> listaEgresoDetalleInterfaz = null;
		//Datos de Beneficio Previsión
		Persona personaApoderado = null;
		Archivo archivoCartaPoder = null;
		BigDecimal bdTotalEgresoDetalleInterfaz = null;
		try{
			ModeloFacadeRemote modeloFacade = (ModeloFacadeRemote) EJBFactory.getRemote(ModeloFacadeRemote.class);
			//Expediente Previsión
			intIdEmpresa = expedientePrevision.getId().getIntPersEmpresaPk();
			beneficiarioPrevisionSeleccionado = expedientePrevision.getBeneficiarioPrevisionGirar();
			personaGirar = expedientePrevision.getPersonaGirar();
			strGlosaEgreso = expedientePrevision.getStrGlosaEgreso();
			listaEgresoDetalleInterfaz = expedientePrevision.getListaEgresoDetalleInterfaz();
			//Beneficio Previsión
			personaApoderado = beneficiarioPrevisionSeleccionado.getPersonaApoderado();
			archivoCartaPoder = beneficiarioPrevisionSeleccionado.getArchivoCartaPoder();
			
			bdTotalEgresoDetalleInterfaz = listaEgresoDetalleInterfaz.get(0).getBdSubTotal();
			
			// Se obtiene SocioEstructura por persona a Girar Previsión
			SocioEstructura socioEstructura = obtenerSocioEstructura(personaGirar, intIdEmpresa);
			if (socioEstructura==null) {
				egreso.setIntErrorGeneracionEgreso(1);
				egreso.setStrMsgErrorGeneracionEgreso("Ocurrio un error durante el proceso de giro de préstamos. Socio Estructura retorna nulo.");
				return egreso;
			}
			
			//Se obtiene captacion de CSO_CONFCAPTACION (usado para obtener el Tipo de Cambio actual y de la solicitud)
			Captacion captacion = obtenerCaptacion(expedientePrevision);
			if (captacion==null) {
				egreso.setIntErrorGeneracionEgreso(1);
				egreso.setStrMsgErrorGeneracionEgreso("Ocurrio un error durante el proceso de giro de préstamos. Captacion retorna nulo.");
				return egreso;
			}else if (captacion!=null) {
				expedientePrevision.setCaptacion(captacion);
			}
			
			//Se recupera Modelo Detalle Nivel, tanto para un préstamo nuevo como un represtamo (Parametro 151)
			ModeloDetalleNivel modeloFiltro = new ModeloDetalleNivel();
			modeloFiltro.setId(new ModeloDetalleNivelId());
			modeloFiltro.getId().setIntEmpresaPk(intIdEmpresa);
			modeloFiltro.getId().setIntPersEmpresaCuenta(intIdEmpresa);
			modeloFiltro.getId().setIntContPeriodoCuenta(anioActual);
			modeloFiltro.setIntDatoTablas(Integer.valueOf(Constante.PARAM_T_DOCUMENTOGENERAL));
			modeloFiltro.setIntDatoArgumento(expedientePrevision.getIntParaDocumentoGeneral());
			
			if (expedientePrevision.getIntParaDocumentoGeneral().equals(Constante.PARAM_T_DOCUMENTOGENERAL_FONDOSEPELIO)) {
				modeloFiltro.setIntTipoModeloContable(Constante.PARAM_T_TIPOMODELOCONTABLE_GIROPREVISION_FDOSEPELIO);
			}
			if (expedientePrevision.getIntParaDocumentoGeneral().equals(Constante.PARAM_T_DOCUMENTOGENERAL_FONDORETIRO)) {
				modeloFiltro.setIntTipoModeloContable(Constante.PARAM_T_TIPOMODELOCONTABLE_GIROPREVISION_FDORETIRO);
			}
			if (expedientePrevision.getIntParaDocumentoGeneral().equals(Constante.PARAM_T_DOCUMENTOGENERAL_AES)) {
				modeloFiltro.setIntTipoModeloContable(Constante.PARAM_T_TIPOMODELOCONTABLE_GIROPREVISION_AES);
			}
			
			List<ModeloDetalleNivelComp> listaModeloGiro = modeloFacade.getModeloGiroPrevision(modeloFiltro);
			//Se verifica que exista el modelo detalle nivel
			if (listaModeloGiro==null || listaModeloGiro.isEmpty()) {
				egreso.setIntErrorGeneracionEgreso(1);
				egreso.setStrMsgErrorGeneracionEgreso("Ocurrio un error durante el proceso de giro de préstamos. Modelo Detalle Nivel retorna nulo.");
				return egreso;
			}			
			//Generación del tipo de cambio de la solicitud y de la fecha actual
			TipoCambio tipoCambioActual = null;
			TipoCambio tipoCambioSolicitud = null;
			if(captacion.getIntParaMonedaCod().compareTo(Constante.PARAM_T_TIPOMONEDA_SOLES)==0){
				tipoCambioActual = new TipoCambio();
				tipoCambioActual.setId(new TipoCambioId());
				tipoCambioActual.getId().setIntParaClaseTipoCambio(new Integer(1));
				tipoCambioActual.getId().setIntParaMoneda(Constante.PARAM_T_TIPOMONEDA_SOLES);
				tipoCambioActual.getId().setIntPersEmpresa(2);
				tipoCambioActual.setBdCompra(BigDecimal.ONE);
				tipoCambioActual.setBdPromedio(BigDecimal.ONE);
				tipoCambioActual.setBdVenta(BigDecimal.ONE);

				tipoCambioSolicitud = new TipoCambio();
				tipoCambioSolicitud.setId(new TipoCambioId());
				tipoCambioSolicitud.getId().setIntParaClaseTipoCambio(new Integer(1));
				tipoCambioSolicitud.getId().setIntParaMoneda(Constante.PARAM_T_TIPOMONEDA_SOLES);
				tipoCambioSolicitud.getId().setIntPersEmpresa(2);
				tipoCambioSolicitud.setBdCompra(BigDecimal.ONE);
				tipoCambioSolicitud.setBdPromedio(BigDecimal.ONE);
				tipoCambioSolicitud.setBdVenta(BigDecimal.ONE);
				
			}else{
				tipoCambioActual = obtenerTipoCambioActual(captacion.getIntParaMonedaCod(), intIdEmpresa);			
				tipoCambioSolicitud = obtenerTipoCambioSolicitud(expedientePrevision, captacion.getIntParaMonedaCod(), intIdEmpresa);
			}
			
			//Generación del Egreso (cabecera)
			egreso.getId().setIntPersEmpresaEgreso(intIdEmpresa);
			egreso.setIntParaTipoOperacion(Constante.PARAM_T_TIPODEOPERACION_EGRESO);
			egreso.setIntParaFormaPago(Constante.PARAM_T_FORMAPAGOEGRESO_EFECTIVO);
			egreso.setIntParaDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_RENDICION);
			egreso.setIntItemPeriodoEgreso(obtenerPeriodoActual());
			egreso.setIntItemEgreso(null);
			egreso.setIntSucuIdSucursal(usuario.getSucursal().getId().getIntIdSucursal());
			egreso.setIntSudeIdSubsucursal(usuario.getSubSucursal().getId().getIntIdSubSucursal());
			//Si es el ultimo beneficiario se GIRA, sino es A CUENTA
			if(beneficiarioPrevisionSeleccionado.isEsUltimoBeneficiarioAGirar()){
				egreso.setIntParaSubTipoOperacion(Constante.PARAM_T_TIPODESUBOPERACION_CANCELACION);
			}else{
				egreso.setIntParaSubTipoOperacion(Constante.PARAM_T_TIPODESUBOPERACION_ACUENTA);
			}
			egreso.setTsFechaProceso(obtenerFechaActual());
			egreso.setDtFechaEgreso(obtenerFechaActual());
			egreso.setIntParaTipoFondoFijo(controlFondosFijos.getId().getIntParaTipoFondoFijo());
			egreso.setIntItemPeriodoFondo(controlFondosFijos.getId().getIntItemPeriodoFondo());
			egreso.setIntItemFondoFijo(controlFondosFijos.getId().getIntItemFondoFijo());
			egreso.setIntItemBancoFondo(null);
			egreso.setIntItemBancoCuenta(null);
			egreso.setIntItemBancoCuentaCheque(null);
			egreso.setIntNumeroPlanilla(null);
			egreso.setIntNumeroCheque(null);
			egreso.setIntNumeroTransferencia(null);
			egreso.setTsFechaPagoDiferido(null);
			egreso.setIntPersEmpresaGirado(intIdEmpresa);
			egreso.setIntPersPersonaGirado(personaGirar.getIntIdPersona());
			egreso.setIntCuentaGirado(expedientePrevision.getCuenta().getId().getIntCuenta());
			//20.05.2014 jchavez cambio de tipo de dato
			egreso.setStrPersCuentaBancariaGirado(null);	
//			egreso.setIntPersCuentaBancariaGirado(null);
			egreso.setIntPersEmpresaBeneficiario(beneficiarioPrevisionSeleccionado.getId().getIntPersEmpresaPrevision());
			egreso.setIntPersPersonaBeneficiario(beneficiarioPrevisionSeleccionado.getIntPersPersonaBeneficiario());
			egreso.setIntPersCuentaBancariaBeneficiario(null);
			if(personaApoderado != null){
				egreso.setIntPersPersonaApoderado(personaApoderado.getIntIdPersona());
				egreso.setIntPersEmpresaApoderado(intIdEmpresa);
			}
			egreso.setBdMontoTotal(bdTotalEgresoDetalleInterfaz);
			egreso.setStrObservacion(strGlosaEgreso);
			egreso.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			egreso.setTsFechaRegistro(obtenerFechaActual());
			egreso.setIntPersEmpresaUsuario(intIdEmpresa);
			egreso.setIntPersPersonaUsuario(usuario.getIntPersPersonaPk());
			egreso.setIntPersEmpresaEgresoAnula(null);
			egreso.setIntPersPersonaEgresoAnula(null);
			if(archivoCartaPoder != null){
				egreso.setIntParaTipoApoderado(archivoCartaPoder.getId().getIntParaTipoCod());
				egreso.setIntItemArchivoApoderado(archivoCartaPoder.getId().getIntItemArchivo());
				egreso.setIntItemHistoricoApoderado(archivoCartaPoder.getId().getIntItemHistorico());
			}
			egreso.setIntParaTipoGiro(null);
			egreso.setIntItemArchivoGiro(null);
			egreso.setIntItemHistoricoGiro(null);
			
			//Generación de Egreso Detalle 
			EgresoDetalle egresoDetalle = generarEgresoDetallePrevision(socioEstructura, listaModeloGiro, expedientePrevision, 
					controlFondosFijos, usuario, tipoCambioSolicitud, tipoCambioActual);
			log.info("Egreso Detalle generado: "+egresoDetalle);
			if(egresoDetalle!=null) egreso.getListaEgresoDetalle().add(egresoDetalle);

			LibroDiario libroDiario = new LibroDiario();
			libroDiario.setId(new LibroDiarioId());
			libroDiario.setListaLibroDiarioDetalle(new ArrayList<LibroDiarioDetalle>());
			libroDiario.getId().setIntPersEmpresaLibro(intIdEmpresa);
			libroDiario.getId().setIntContPeriodoLibro(obtenerPeriodoActual());
			libroDiario.setStrGlosa(strGlosaEgreso);
			libroDiario.setTsFechaRegistro(obtenerFechaActual());
			libroDiario.setTsFechaDocumento(obtenerFechaActual());
			libroDiario.setIntPersEmpresaUsuario(intIdEmpresa);
			libroDiario.setIntPersPersonaUsuario(usuario.getIntPersPersonaPk());
			libroDiario.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			libroDiario.setIntParaTipoDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_RENDICION);
			
			BigDecimal bdMontoDebeHaber = egreso.getListaEgresoDetalle().get(0).getBdMontoAbono()!=null
												?egreso.getListaEgresoDetalle().get(0).getBdMontoAbono()
												:egreso.getListaEgresoDetalle().get(0).getBdMontoCargo();
			//Generando el Libro Diario Detalle del Giro
			LibroDiarioDetalle libroDiarioDetalle = generarLibroDiarioDetalleGiroPrevision(socioEstructura, listaModeloGiro, 
					expedientePrevision, controlFondosFijos, tipoCambioActual, bdMontoDebeHaber);
			libroDiario.getListaLibroDiarioDetalle().add(libroDiarioDetalle);
			
			//Generando el Libro Diario Detalle fondo de cambio
			LibroDiarioDetalle libroDiarioDetalleFondoFijo = generarCFFLibroDiarioDetalle(expedientePrevision, controlFondosFijos, tipoCambioActual,bdMontoDebeHaber); 
			
			
			if(libroDiarioDetalleFondoFijo != null) libroDiario.getListaLibroDiarioDetalle().add(libroDiarioDetalleFondoFijo);
			
			egreso.setLibroDiario(libroDiario);
			egreso.setControlFondosFijos(controlFondosFijos);
			
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return egreso;
	}
	
	/**
	 * GIRO PREVISION POR BANCO(CHEQUE). 
	 */	
	
	/**
	 * Genera el egreso cuando se realiza el giro prevision por Cheque
	 * @param expedientePrevision
	 * @param bancoCuenta
	 * @param usuario
	 * @param intNumeroCheque
	 * @param intTipoDocumentoValidar
	 * @return
	 * @throws BusinessException
	 */
	public Egreso generarEgresoPrevisionCheque(ExpedientePrevision expedientePrevision, Bancocuenta bancoCuenta, Usuario usuario,
			Integer intNumeroCheque, Integer intTipoDocumentoValidar) throws BusinessException{
			Egreso egreso = new Egreso();
			Calendar cal = Calendar.getInstance();
			Integer anioActual = cal.get(Calendar.YEAR);
			//Datos del Expediente Previsión
			Integer intIdEmpresa = null;
			BeneficiarioPrevision beneficiarioPrevisionSeleccionado = null;
			Persona personaGirar = null;
			String 	strGlosaEgreso = null;
			List<EgresoDetalleInterfaz> listaEgresoDetalleInterfaz = null;
			//Datos de Beneficio Previsión
			Persona personaApoderado = null;
			Archivo archivoCartaPoder = null;
			BigDecimal bdTotalEgresoDetalleInterfaz = null;
			try{
				ModeloFacadeRemote modeloFacade = (ModeloFacadeRemote) EJBFactory.getRemote(ModeloFacadeRemote.class);
				//Expediente Previsión
				intIdEmpresa = expedientePrevision.getId().getIntPersEmpresaPk();
				beneficiarioPrevisionSeleccionado = expedientePrevision.getBeneficiarioPrevisionGirar();
				personaGirar = expedientePrevision.getPersonaGirar();
				strGlosaEgreso = expedientePrevision.getStrGlosaEgreso();
				listaEgresoDetalleInterfaz = expedientePrevision.getListaEgresoDetalleInterfaz();
				//Beneficio Previsión
				personaApoderado = beneficiarioPrevisionSeleccionado.getPersonaApoderado();
				archivoCartaPoder = beneficiarioPrevisionSeleccionado.getArchivoCartaPoder();
				
				bdTotalEgresoDetalleInterfaz = listaEgresoDetalleInterfaz.get(0).getBdSubTotal();
				
				// Se obtiene SocioEstructura por persona a Girar Previsión
				SocioEstructura socioEstructura = obtenerSocioEstructura(personaGirar, intIdEmpresa);
				if (socioEstructura==null) {
					egreso.setIntErrorGeneracionEgreso(1);
					egreso.setStrMsgErrorGeneracionEgreso("Ocurrio un error durante el proceso de giro de préstamos. Socio Estructura retorna nulo.");
					return egreso;
				}
				
				//Se obtiene captacion de CSO_CONFCAPTACION (usado para obtener el Tipo de Cambio actual y de la solicitud)
				Captacion captacion = obtenerCaptacion(expedientePrevision);
				if (captacion==null) {
					egreso.setIntErrorGeneracionEgreso(1);
					egreso.setStrMsgErrorGeneracionEgreso("Ocurrio un error durante el proceso de giro de préstamos. Captacion retorna nulo.");
					return egreso;
				}else if (captacion!=null) {
					expedientePrevision.setCaptacion(captacion);
				}
				
				//Se recupera Modelo Detalle Nivel, tanto para un préstamo nuevo como un represtamo (Parametro 151)
				ModeloDetalleNivel modeloFiltro = new ModeloDetalleNivel();
				modeloFiltro.setId(new ModeloDetalleNivelId());
				modeloFiltro.getId().setIntEmpresaPk(intIdEmpresa);
				modeloFiltro.getId().setIntPersEmpresaCuenta(intIdEmpresa);
				modeloFiltro.getId().setIntContPeriodoCuenta(anioActual);
				modeloFiltro.setIntDatoTablas(Integer.valueOf(Constante.PARAM_T_DOCUMENTOGENERAL));
				modeloFiltro.setIntDatoArgumento(expedientePrevision.getIntParaDocumentoGeneral());
				
				if (expedientePrevision.getIntParaDocumentoGeneral().equals(Constante.PARAM_T_DOCUMENTOGENERAL_FONDOSEPELIO)) {
					modeloFiltro.setIntTipoModeloContable(Constante.PARAM_T_TIPOMODELOCONTABLE_GIROPREVISION_FDOSEPELIO);
				}
				if (expedientePrevision.getIntParaDocumentoGeneral().equals(Constante.PARAM_T_DOCUMENTOGENERAL_FONDORETIRO)) {
					modeloFiltro.setIntTipoModeloContable(Constante.PARAM_T_TIPOMODELOCONTABLE_GIROPREVISION_FDORETIRO);
				}
				if (expedientePrevision.getIntParaDocumentoGeneral().equals(Constante.PARAM_T_DOCUMENTOGENERAL_AES)) {
					modeloFiltro.setIntTipoModeloContable(Constante.PARAM_T_TIPOMODELOCONTABLE_GIROPREVISION_AES);
				}
				
				List<ModeloDetalleNivelComp> listaModeloGiro = modeloFacade.getModeloGiroPrevision(modeloFiltro);
				//Se verifica que exista el modelo detalle nivel
				if (listaModeloGiro==null || listaModeloGiro.isEmpty()) {
					egreso.setIntErrorGeneracionEgreso(1);
					egreso.setStrMsgErrorGeneracionEgreso("Ocurrio un error durante el proceso de giro de préstamos. Modelo Detalle Nivel retorna nulo.");
					return egreso;
				}			
				//Generación del tipo de cambio de la solicitud y de la fecha actual
				TipoCambio tipoCambioActual = null;
				TipoCambio tipoCambioSolicitud = null;
				if(captacion.getIntParaMonedaCod().compareTo(Constante.PARAM_T_TIPOMONEDA_SOLES)==0){
					tipoCambioActual = new TipoCambio();
					tipoCambioActual.setId(new TipoCambioId());
					tipoCambioActual.getId().setIntParaClaseTipoCambio(new Integer(1));
					tipoCambioActual.getId().setIntParaMoneda(Constante.PARAM_T_TIPOMONEDA_SOLES);
					tipoCambioActual.getId().setIntPersEmpresa(2);
					tipoCambioActual.setBdCompra(BigDecimal.ONE);
					tipoCambioActual.setBdPromedio(BigDecimal.ONE);
					tipoCambioActual.setBdVenta(BigDecimal.ONE);

					tipoCambioSolicitud = new TipoCambio();
					tipoCambioSolicitud.setId(new TipoCambioId());
					tipoCambioSolicitud.getId().setIntParaClaseTipoCambio(new Integer(1));
					tipoCambioSolicitud.getId().setIntParaMoneda(Constante.PARAM_T_TIPOMONEDA_SOLES);
					tipoCambioSolicitud.getId().setIntPersEmpresa(2);
					tipoCambioSolicitud.setBdCompra(BigDecimal.ONE);
					tipoCambioSolicitud.setBdPromedio(BigDecimal.ONE);
					tipoCambioSolicitud.setBdVenta(BigDecimal.ONE);
					
				}else{
					tipoCambioActual = obtenerTipoCambioActual(captacion.getIntParaMonedaCod(), intIdEmpresa);			
					tipoCambioSolicitud = obtenerTipoCambioSolicitud(expedientePrevision, captacion.getIntParaMonedaCod(), intIdEmpresa);
				}
				
				//Generación del Egreso (cabecera)
				egreso.getId().setIntPersEmpresaEgreso(intIdEmpresa);
				egreso.setIntParaTipoOperacion(Constante.PARAM_T_TIPODEOPERACION_EGRESO);
				egreso.setIntParaFormaPago(Constante.PARAM_T_FORMAPAGOEGRESO_CHEQUE);
				egreso.setIntParaDocumentoGeneral(intTipoDocumentoValidar);			
				egreso.setIntItemPeriodoEgreso(obtenerPeriodoActual());
				egreso.setIntItemEgreso(null);
				egreso.setIntSucuIdSucursal(usuario.getSucursal().getId().getIntIdSucursal());
				egreso.setIntSudeIdSubsucursal(usuario.getSubSucursal().getId().getIntIdSubSucursal());
				//Si es el ultimo beneficiario se GIRA, sino es A CUENTA
				if(beneficiarioPrevisionSeleccionado.isEsUltimoBeneficiarioAGirar()){
					egreso.setIntParaSubTipoOperacion(Constante.PARAM_T_TIPODESUBOPERACION_CANCELACION);
				}else{
					egreso.setIntParaSubTipoOperacion(Constante.PARAM_T_TIPODESUBOPERACION_ACUENTA);
				}
				egreso.setTsFechaProceso(obtenerFechaActual());
				egreso.setDtFechaEgreso(obtenerFechaActual());
				egreso.setIntParaTipoFondoFijo(null);
				egreso.setIntItemPeriodoFondo(null);
				egreso.setIntItemFondoFijo(null);
				egreso.setIntItemBancoFondo(bancoCuenta.getId().getIntItembancofondo());
				egreso.setIntItemBancoCuenta(bancoCuenta.getId().getIntItembancocuenta());
				egreso.setIntItemBancoCuentaCheque(null);
				egreso.setIntNumeroPlanilla(null);
				egreso.setIntNumeroCheque(intNumeroCheque);
				egreso.setIntNumeroTransferencia(null);
				egreso.setTsFechaPagoDiferido(null);
				egreso.setIntPersEmpresaGirado(intIdEmpresa);
				egreso.setIntPersPersonaGirado(personaGirar.getIntIdPersona());
				egreso.setIntCuentaGirado(expedientePrevision.getCuenta().getId().getIntCuenta());
				//20.05.2014 jchavez cambio de tipo de dato
				egreso.setStrPersCuentaBancariaGirado(null);				
//				egreso.setIntPersCuentaBancariaGirado(null);
				egreso.setIntPersEmpresaBeneficiario(beneficiarioPrevisionSeleccionado.getId().getIntPersEmpresaPrevision());
				egreso.setIntPersPersonaBeneficiario(beneficiarioPrevisionSeleccionado.getIntPersPersonaBeneficiario());
				egreso.setIntPersCuentaBancariaBeneficiario(null);
				if(personaApoderado != null){
					egreso.setIntPersPersonaApoderado(personaApoderado.getIntIdPersona());
					egreso.setIntPersEmpresaApoderado(intIdEmpresa);
				}
				egreso.setBdMontoTotal(bdTotalEgresoDetalleInterfaz);
				egreso.setStrObservacion(strGlosaEgreso);
				egreso.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
				egreso.setTsFechaRegistro(obtenerFechaActual());
				egreso.setIntPersEmpresaUsuario(intIdEmpresa);
				egreso.setIntPersPersonaUsuario(usuario.getIntPersPersonaPk());
				egreso.setIntPersEmpresaEgresoAnula(null);
				egreso.setIntPersPersonaEgresoAnula(null);
				if(archivoCartaPoder != null){
					egreso.setIntParaTipoApoderado(archivoCartaPoder.getId().getIntParaTipoCod());
					egreso.setIntItemArchivoApoderado(archivoCartaPoder.getId().getIntItemArchivo());
					egreso.setIntItemHistoricoApoderado(archivoCartaPoder.getId().getIntItemHistorico());
				}
				egreso.setIntParaTipoGiro(null);
				egreso.setIntItemArchivoGiro(null);
				egreso.setIntItemHistoricoGiro(null);
				
				//Generación de Egreso Detalle 
				EgresoDetalle egresoDetalle = generarEgresoDetallePrevision(socioEstructura, listaModeloGiro, expedientePrevision, null, usuario, tipoCambioSolicitud, tipoCambioActual);
				log.info("Egreso Detalle generado: "+egresoDetalle);
				if(egresoDetalle!=null) egreso.getListaEgresoDetalle().add(egresoDetalle);

				LibroDiario libroDiario = new LibroDiario();
				libroDiario.setId(new LibroDiarioId());
				libroDiario.setListaLibroDiarioDetalle(new ArrayList<LibroDiarioDetalle>());
				libroDiario.getId().setIntPersEmpresaLibro(intIdEmpresa);
				libroDiario.getId().setIntContPeriodoLibro(obtenerPeriodoActual());
				libroDiario.setStrGlosa(strGlosaEgreso);
				libroDiario.setTsFechaRegistro(obtenerFechaActual());
				libroDiario.setTsFechaDocumento(obtenerFechaActual());
				libroDiario.setIntPersEmpresaUsuario(intIdEmpresa);
				libroDiario.setIntPersPersonaUsuario(usuario.getIntPersPersonaPk());
				libroDiario.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
//				libroDiario.setIntParaTipoDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_RENDICION);
				libroDiario.setIntParaTipoDocumentoGeneral(intTipoDocumentoValidar);
				
				BigDecimal bdMontoDebeHaber = egreso.getListaEgresoDetalle().get(0).getBdMontoAbono()!=null
													?egreso.getListaEgresoDetalle().get(0).getBdMontoAbono()
													:egreso.getListaEgresoDetalle().get(0).getBdMontoCargo();
				//Generando el Libro Diario Detalle del Giro
				LibroDiarioDetalle libroDiarioDetalle = generarLibroDiarioDetalleGiroPrevision(socioEstructura, listaModeloGiro, 
						expedientePrevision, null, tipoCambioActual, bdMontoDebeHaber);
				libroDiario.getListaLibroDiarioDetalle().add(libroDiarioDetalle);
				
				//Generando el Libro Diario Detalle fondo de cambio
				LibroDiarioDetalle libroDiarioDetalleBancoPorCheque = generarBancoLibroDiarioDetalle(expedientePrevision, bancoCuenta, tipoCambioActual,bdMontoDebeHaber, intTipoDocumentoValidar); 
				if(libroDiarioDetalleBancoPorCheque != null) libroDiario.getListaLibroDiarioDetalle().add(libroDiarioDetalleBancoPorCheque);
				
				egreso.setLibroDiario(libroDiario);
//				egreso.setControlFondosFijos(controlFondosFijos);
				
			}catch(Exception e){
				throw new BusinessException(e);
			}
			return egreso;
		}
	
	
	/**
	 * GIRO PREVISION POR BANCO(TRANSFERENCIA TERCEROS SIN TELECREDITO). 
	 */	
	
	/**
	 * Genera el egreso cuando se realiza el giro prevision por Transferencia
	 * @param expedientePrevision
	 * @param bancoCuenta
	 * @param usuario
	 * @param intNumeroCheque
	 * @param intTipoDocumentoValidar
	 * @return
	 * @throws BusinessException
	 */
	public Egreso generarEgresoPrevisionTransferenciaTerceros(ExpedientePrevision expedientePrevision, Bancocuenta bancoCuenta, Usuario usuario,
			Integer intNumeroTransferencia, Integer intTipoDocumentoValidar, CuentaBancaria cuentaBancariaDestino) throws BusinessException{
			Egreso egreso = new Egreso();
			Calendar cal = Calendar.getInstance();
			Integer anioActual = cal.get(Calendar.YEAR);
			//Datos del Expediente Previsión
			Integer intIdEmpresa = null;
			BeneficiarioPrevision beneficiarioPrevisionSeleccionado = null;
			Persona personaGirar = null;
			String 	strGlosaEgreso = null;
			List<EgresoDetalleInterfaz> listaEgresoDetalleInterfaz = null;
			//Datos de Beneficio Previsión
			Persona personaApoderado = null;
			Archivo archivoCartaPoder = null;
			BigDecimal bdTotalEgresoDetalleInterfaz = null;
			try{
				ModeloFacadeRemote modeloFacade = (ModeloFacadeRemote) EJBFactory.getRemote(ModeloFacadeRemote.class);
				//Expediente Previsión
				intIdEmpresa = expedientePrevision.getId().getIntPersEmpresaPk();
				beneficiarioPrevisionSeleccionado = expedientePrevision.getBeneficiarioPrevisionGirar();
				personaGirar = expedientePrevision.getPersonaGirar();
				strGlosaEgreso = expedientePrevision.getStrGlosaEgreso();
				listaEgresoDetalleInterfaz = expedientePrevision.getListaEgresoDetalleInterfaz();
				//Beneficio Previsión
				personaApoderado = beneficiarioPrevisionSeleccionado.getPersonaApoderado();
				archivoCartaPoder = beneficiarioPrevisionSeleccionado.getArchivoCartaPoder();
				
				bdTotalEgresoDetalleInterfaz = listaEgresoDetalleInterfaz.get(0).getBdSubTotal();
				
				// Se obtiene SocioEstructura por persona a Girar Previsión
				SocioEstructura socioEstructura = obtenerSocioEstructura(personaGirar, intIdEmpresa);
				if (socioEstructura==null) {
					egreso.setIntErrorGeneracionEgreso(1);
					egreso.setStrMsgErrorGeneracionEgreso("Ocurrio un error durante el proceso de giro de préstamos. Socio Estructura retorna nulo.");
					return egreso;
				}
				
				//Se obtiene captacion de CSO_CONFCAPTACION (usado para obtener el Tipo de Cambio actual y de la solicitud)
				Captacion captacion = obtenerCaptacion(expedientePrevision);
				if (captacion==null) {
					egreso.setIntErrorGeneracionEgreso(1);
					egreso.setStrMsgErrorGeneracionEgreso("Ocurrio un error durante el proceso de giro de préstamos. Captacion retorna nulo.");
					return egreso;
				}else if (captacion!=null) {
					expedientePrevision.setCaptacion(captacion);
				}
				
				//Se recupera Modelo Detalle Nivel, tanto para un préstamo nuevo como un represtamo (Parametro 151)
				ModeloDetalleNivel modeloFiltro = new ModeloDetalleNivel();
				modeloFiltro.setId(new ModeloDetalleNivelId());
				modeloFiltro.getId().setIntEmpresaPk(intIdEmpresa);
				modeloFiltro.getId().setIntPersEmpresaCuenta(intIdEmpresa);
				modeloFiltro.getId().setIntContPeriodoCuenta(anioActual);
				modeloFiltro.setIntDatoTablas(Integer.valueOf(Constante.PARAM_T_DOCUMENTOGENERAL));
				modeloFiltro.setIntDatoArgumento(expedientePrevision.getIntParaDocumentoGeneral());
				
				if (expedientePrevision.getIntParaDocumentoGeneral().equals(Constante.PARAM_T_DOCUMENTOGENERAL_FONDOSEPELIO)) {
					modeloFiltro.setIntTipoModeloContable(Constante.PARAM_T_TIPOMODELOCONTABLE_GIROPREVISION_FDOSEPELIO);
				}
				if (expedientePrevision.getIntParaDocumentoGeneral().equals(Constante.PARAM_T_DOCUMENTOGENERAL_FONDORETIRO)) {
					modeloFiltro.setIntTipoModeloContable(Constante.PARAM_T_TIPOMODELOCONTABLE_GIROPREVISION_FDORETIRO);
				}
				if (expedientePrevision.getIntParaDocumentoGeneral().equals(Constante.PARAM_T_DOCUMENTOGENERAL_AES)) {
					modeloFiltro.setIntTipoModeloContable(Constante.PARAM_T_TIPOMODELOCONTABLE_GIROPREVISION_AES);
				}
				
				List<ModeloDetalleNivelComp> listaModeloGiro = modeloFacade.getModeloGiroPrevision(modeloFiltro);
				//Se verifica que exista el modelo detalle nivel
				if (listaModeloGiro==null || listaModeloGiro.isEmpty()) {
					egreso.setIntErrorGeneracionEgreso(1);
					egreso.setStrMsgErrorGeneracionEgreso("Ocurrio un error durante el proceso de giro de préstamos. Modelo Detalle Nivel retorna nulo.");
					return egreso;
				}			
				//Generación del tipo de cambio de la solicitud y de la fecha actual
				TipoCambio tipoCambioActual = null;
				TipoCambio tipoCambioSolicitud = null;
				if(captacion.getIntParaMonedaCod().compareTo(Constante.PARAM_T_TIPOMONEDA_SOLES)==0){
					tipoCambioActual = new TipoCambio();
					tipoCambioActual.setId(new TipoCambioId());
					tipoCambioActual.getId().setIntParaClaseTipoCambio(new Integer(1));
					tipoCambioActual.getId().setIntParaMoneda(Constante.PARAM_T_TIPOMONEDA_SOLES);
					tipoCambioActual.getId().setIntPersEmpresa(2);
					tipoCambioActual.setBdCompra(BigDecimal.ONE);
					tipoCambioActual.setBdPromedio(BigDecimal.ONE);
					tipoCambioActual.setBdVenta(BigDecimal.ONE);

					tipoCambioSolicitud = new TipoCambio();
					tipoCambioSolicitud.setId(new TipoCambioId());
					tipoCambioSolicitud.getId().setIntParaClaseTipoCambio(new Integer(1));
					tipoCambioSolicitud.getId().setIntParaMoneda(Constante.PARAM_T_TIPOMONEDA_SOLES);
					tipoCambioSolicitud.getId().setIntPersEmpresa(2);
					tipoCambioSolicitud.setBdCompra(BigDecimal.ONE);
					tipoCambioSolicitud.setBdPromedio(BigDecimal.ONE);
					tipoCambioSolicitud.setBdVenta(BigDecimal.ONE);
					
				}else{
					tipoCambioActual = obtenerTipoCambioActual(captacion.getIntParaMonedaCod(), intIdEmpresa);			
					tipoCambioSolicitud = obtenerTipoCambioSolicitud(expedientePrevision, captacion.getIntParaMonedaCod(), intIdEmpresa);
				}
				
				//Generación del Egreso (cabecera)
				egreso.getId().setIntPersEmpresaEgreso(intIdEmpresa);
				egreso.setIntParaTipoOperacion(Constante.PARAM_T_TIPODEOPERACION_EGRESO);
				egreso.setIntParaFormaPago(Constante.PARAM_T_FORMAPAGOEGRESO_TRANSFERENCIA);
				egreso.setIntParaDocumentoGeneral(intTipoDocumentoValidar);			
				egreso.setIntItemPeriodoEgreso(obtenerPeriodoActual());
				egreso.setIntItemEgreso(null);
				egreso.setIntSucuIdSucursal(usuario.getSucursal().getId().getIntIdSucursal());
				egreso.setIntSudeIdSubsucursal(usuario.getSubSucursal().getId().getIntIdSubSucursal());
				//Si es el ultimo beneficiario se GIRA, sino es A CUENTA
				if(beneficiarioPrevisionSeleccionado.isEsUltimoBeneficiarioAGirar()){
					egreso.setIntParaSubTipoOperacion(Constante.PARAM_T_TIPODESUBOPERACION_CANCELACION);
				}else{
					egreso.setIntParaSubTipoOperacion(Constante.PARAM_T_TIPODESUBOPERACION_ACUENTA);
				}
				egreso.setTsFechaProceso(obtenerFechaActual());
				egreso.setDtFechaEgreso(obtenerFechaActual());
				egreso.setIntParaTipoFondoFijo(null);
				egreso.setIntItemPeriodoFondo(null);
				egreso.setIntItemFondoFijo(null);
				egreso.setIntItemBancoFondo(bancoCuenta.getId().getIntItembancofondo());
				egreso.setIntItemBancoCuenta(bancoCuenta.getId().getIntItembancocuenta());
				egreso.setIntItemBancoCuentaCheque(null);
				egreso.setIntNumeroPlanilla(null);
				egreso.setIntNumeroCheque(null);
				egreso.setIntNumeroTransferencia(intNumeroTransferencia);
				egreso.setTsFechaPagoDiferido(null);
				egreso.setIntPersEmpresaGirado(intIdEmpresa);
				egreso.setIntPersPersonaGirado(personaGirar.getIntIdPersona());
				egreso.setIntCuentaGirado(expedientePrevision.getCuenta().getId().getIntCuenta());
				//20.05.2014 jchavez cambio de tipo de dato
				egreso.setStrPersCuentaBancariaGirado(cuentaBancariaDestino!=null?cuentaBancariaDestino.getStrNroCuentaBancaria():null);
				egreso.setIntPersEmpresaBeneficiario(beneficiarioPrevisionSeleccionado.getId().getIntPersEmpresaPrevision());
				egreso.setIntPersPersonaBeneficiario(beneficiarioPrevisionSeleccionado.getIntPersPersonaBeneficiario());
				egreso.setIntPersCuentaBancariaBeneficiario(null);
				if(personaApoderado != null){
					egreso.setIntPersPersonaApoderado(personaApoderado.getIntIdPersona());
					egreso.setIntPersEmpresaApoderado(intIdEmpresa);
				}
				egreso.setBdMontoTotal(bdTotalEgresoDetalleInterfaz);
				egreso.setStrObservacion(strGlosaEgreso);
				egreso.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
				egreso.setTsFechaRegistro(obtenerFechaActual());
				egreso.setIntPersEmpresaUsuario(intIdEmpresa);
				egreso.setIntPersPersonaUsuario(usuario.getIntPersPersonaPk());
				egreso.setIntPersEmpresaEgresoAnula(null);
				egreso.setIntPersPersonaEgresoAnula(null);
				if(archivoCartaPoder != null){
					egreso.setIntParaTipoApoderado(archivoCartaPoder.getId().getIntParaTipoCod());
					egreso.setIntItemArchivoApoderado(archivoCartaPoder.getId().getIntItemArchivo());
					egreso.setIntItemHistoricoApoderado(archivoCartaPoder.getId().getIntItemHistorico());
				}
				egreso.setIntParaTipoGiro(null);
				egreso.setIntItemArchivoGiro(null);
				egreso.setIntItemHistoricoGiro(null);
				
				//Generación de Egreso Detalle 
				EgresoDetalle egresoDetalle = generarEgresoDetallePrevision(socioEstructura, listaModeloGiro, expedientePrevision, null, usuario, tipoCambioSolicitud, tipoCambioActual);
				log.info("Egreso Detalle generado: "+egresoDetalle);
				if(egresoDetalle!=null) egreso.getListaEgresoDetalle().add(egresoDetalle);

				LibroDiario libroDiario = new LibroDiario();
				libroDiario.setId(new LibroDiarioId());
				libroDiario.setListaLibroDiarioDetalle(new ArrayList<LibroDiarioDetalle>());
				libroDiario.getId().setIntPersEmpresaLibro(intIdEmpresa);
				libroDiario.getId().setIntContPeriodoLibro(obtenerPeriodoActual());
				libroDiario.setStrGlosa(strGlosaEgreso);
				libroDiario.setTsFechaRegistro(obtenerFechaActual());
				libroDiario.setTsFechaDocumento(obtenerFechaActual());
				libroDiario.setIntPersEmpresaUsuario(intIdEmpresa);
				libroDiario.setIntPersPersonaUsuario(usuario.getIntPersPersonaPk());
				libroDiario.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
//				libroDiario.setIntParaTipoDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_RENDICION);
				libroDiario.setIntParaTipoDocumentoGeneral(intTipoDocumentoValidar);
				
				BigDecimal bdMontoDebeHaber = egreso.getListaEgresoDetalle().get(0).getBdMontoAbono()!=null
													?egreso.getListaEgresoDetalle().get(0).getBdMontoAbono()
													:egreso.getListaEgresoDetalle().get(0).getBdMontoCargo();
				//Generando el Libro Diario Detalle del Giro
				LibroDiarioDetalle libroDiarioDetalle = generarLibroDiarioDetalleGiroPrevision(socioEstructura, listaModeloGiro, 
						expedientePrevision, null, tipoCambioActual, bdMontoDebeHaber);
				libroDiario.getListaLibroDiarioDetalle().add(libroDiarioDetalle);
				
				//Generando el Libro Diario Detalle fondo de cambio
				LibroDiarioDetalle libroDiarioDetalleBancoPorCheque = generarBancoLibroDiarioDetalle(expedientePrevision, bancoCuenta, tipoCambioActual,bdMontoDebeHaber, intTipoDocumentoValidar); 
				if(libroDiarioDetalleBancoPorCheque != null) libroDiario.getListaLibroDiarioDetalle().add(libroDiarioDetalleBancoPorCheque);
				
				egreso.setLibroDiario(libroDiario);
//				egreso.setControlFondosFijos(controlFondosFijos);
				
			}catch(Exception e){
				throw new BusinessException(e);
			}
			return egreso;
		}
	/**
	 * JCHAVEZ 21.01.2014
	 * Genera el Egreso Detalle para Giro Prevision
	 * Giro de Fondo de Sepelio, Fondo de Retiro y AES
	 * @param socioEstructura
	 * @param modeloDetalle
	 * @param expedientePrevision
	 * @param controlFondosFijos
	 * @param usuario
	 * @param tipoCambioSolicitud
	 * @param tipoCambioActual
	 * @return
	 * @throws Exception
	 */
	private EgresoDetalle generarEgresoDetallePrevision(SocioEstructura socioEstructura, List<ModeloDetalleNivelComp> o, 
			ExpedientePrevision expedientePrevision, ControlFondosFijos controlFondosFijos, Usuario usuario, TipoCambio tipoCambioSolicitud, 
			TipoCambio tipoCambioActual) throws Exception{
		
		boolean esMonedaExtranjera;
		
		Captacion captacion = expedientePrevision.getCaptacion();
		Persona personaGirar = expedientePrevision.getPersonaGirar();
		Integer intIdEmpresa = expedientePrevision.getId().getIntPersEmpresaPk();
		
		EgresoDetalle egresoDetalle = new EgresoDetalle();
		egresoDetalle.getId().setIntPersEmpresaEgreso(intIdEmpresa);
		//Seteamos el Documento General del expediente prevision girado
		egresoDetalle.setIntParaDocumentoGeneral(expedientePrevision.getIntParaDocumentoGeneral());
		egresoDetalle.setIntParaTipoComprobante(null);
		egresoDetalle.setStrSerieDocumento(null);
		egresoDetalle.setStrNumeroDocumento(""+expedientePrevision.getId().getIntItemExpediente());
		egresoDetalle.setStrDescripcionEgreso(o.get(0).getStrDescCuenta());
		egresoDetalle.setIntPersEmpresaGirado(intIdEmpresa);
		egresoDetalle.setIntPersonaGirado(personaGirar.getIntIdPersona());
		egresoDetalle.setIntCuentaGirado(expedientePrevision.getCuenta().getId().getIntCuenta());
		egresoDetalle.setIntSucuIdSucursalEgreso(socioEstructura.getIntIdSucursalAdministra());
		egresoDetalle.setIntSudeIdSubsucursalEgreso(socioEstructura.getIntIdSubsucurAdministra());
		egresoDetalle.setIntParaTipoMoneda(captacion.getIntParaMonedaCod());
		if(egresoDetalle.getIntParaTipoMoneda().equals(Constante.PARAM_T_TIPOMONEDA_SOL)){
			esMonedaExtranjera = Boolean.FALSE;
		}else{
			esMonedaExtranjera = Boolean.TRUE;
		}
		//Calculo del monto girado
		BigDecimal bdMontoTotal = BigDecimal.ONE;
		for (ModeloDetalleNivelComp campo : o) {
			//Giro Fondo Sepelio
			if (campo.getStrCampoConsumir().toUpperCase().equals("EXPS_MONTONETOBENEFICIO_N".toUpperCase())) {
				bdMontoTotal = bdMontoTotal.multiply(expedientePrevision.getBdMontoNetoBeneficio()!=null
														?expedientePrevision.getBdMontoNetoBeneficio()
														:BigDecimal.ONE);
			}
			if (campo.getStrCampoConsumir().toUpperCase().equals("BEPR_PROCENTAJEBENEFICIO_N".toUpperCase())) {
				bdMontoTotal = bdMontoTotal.multiply(expedientePrevision.getBeneficiarioPrevisionGirar().getBdPorcentajeBeneficio()!=null
														?expedientePrevision.getBeneficiarioPrevisionGirar().getBdPorcentajeBeneficio()
														:BigDecimal.ONE)
										   .divide(new BigDecimal(100)).setScale(2, RoundingMode.HALF_UP);
			}
		}
		//Asignando posicion del monto DEBE - HABER
		if (o.get(0).getIntParamDebeHaber().equals(1)) egresoDetalle.setBdMontoCargo(bdMontoTotal);
		if (o.get(0).getIntParamDebeHaber().equals(2)) egresoDetalle.setBdMontoAbono(bdMontoTotal);
		//Caso moneda extranjera
		if(esMonedaExtranjera) egresoDetalle.setBdMontoDiferencial(obtenerMontoDiferencial(egresoDetalle.getBdMontoAbono()!=null?egresoDetalle.getBdMontoAbono():egresoDetalle.getBdMontoCargo(), tipoCambioActual, tipoCambioSolicitud));
	
		egresoDetalle.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
		egresoDetalle.setTsFechaRegistro(obtenerFechaActual());
		egresoDetalle.setIntPersEmpresaUsuario(intIdEmpresa);
		egresoDetalle.setIntPersPersonaUsuario(usuario.getIntPersPersonaPk());
		egresoDetalle.setIntPersEmpresaLibroDestino(null);
		egresoDetalle.setIntContPeriodoLibroDestino(null);
		egresoDetalle.setIntContCodigoLibroDestino(null);
		egresoDetalle.setIntPersEmpresaCuenta(o.get(0).getIntEmpresaCuenta());
		egresoDetalle.setIntContPeriodoCuenta(o.get(0).getIntPeriodoCuenta());
		egresoDetalle.setStrContNumeroCuenta(o.get(0).getStrNumeroCuenta());
		
		if(controlFondosFijos!=null){
			egresoDetalle.setIntParaTipoFondoFijo(controlFondosFijos.getId().getIntParaTipoFondoFijo());
			egresoDetalle.setIntItemPeriodoFondo(controlFondosFijos.getId().getIntItemPeriodoFondo());
			egresoDetalle.setIntSucuIdSucursal(controlFondosFijos.getId().getIntSucuIdSucursal());
			egresoDetalle.setIntItemFondoFijo(controlFondosFijos.getId().getIntItemFondoFijo());
		}
		return egresoDetalle;
	}
	/**
	 * JCHAVEZ 21.01.2014
	 * Genera el Libro Diario Detalle para Giro Prevision
	 * Giro de Fondo de Sepelio...
	 * @param socioEstructura
	 * @param o
	 * @param expedientePrevision
	 * @param controlFondosFijos
	 * @param tipoCambioActual
	 * @param bdMontoDebeHaber
	 * @return
	 * @throws Exception
	 */
	private LibroDiarioDetalle generarLibroDiarioDetalleGiroPrevision(SocioEstructura socioEstructura, List<ModeloDetalleNivelComp> o, 
			ExpedientePrevision expedientePrevision, ControlFondosFijos controlFondosFijos, TipoCambio tipoCambioActual, BigDecimal bdMontoDebeHaber)
		throws Exception{
	
		
		Captacion captacion = expedientePrevision.getCaptacion();
		Integer intIdEmpresa = expedientePrevision.getId().getIntPersEmpresaPk();
		Persona personaGirar = expedientePrevision.getPersonaGirar();
		
		LibroDiarioDetalle libroDiarioDetalle = new LibroDiarioDetalle();
		libroDiarioDetalle.setId(new LibroDiarioDetalleId());
		libroDiarioDetalle.getId().setIntPersEmpresaLibro(intIdEmpresa);
		libroDiarioDetalle.getId().setIntContPeriodoLibro(obtenerPeriodoActual());		
		
		if(o.get(0) !=null){
			libroDiarioDetalle.setIntPersEmpresaCuenta(o.get(0).getIntEmpresaCuenta());
			libroDiarioDetalle.setIntContPeriodo(o.get(0).getIntPeriodoCuenta());
			libroDiarioDetalle.setStrContNumeroCuenta(o.get(0).getStrNumeroCuenta());			
			libroDiarioDetalle.setStrComentario(o.get(0).getStrDescCuenta().length()<20?o.get(0).getStrDescCuenta():o.get(0).getStrDescCuenta().substring(0, 20));
		}
		
		libroDiarioDetalle.setIntPersPersona(personaGirar.getIntIdPersona());
		libroDiarioDetalle.setIntParaDocumentoGeneral(expedientePrevision.getIntParaDocumentoGeneral());
		libroDiarioDetalle.setStrSerieDocumento(null);
		libroDiarioDetalle.setIntParaMonedaDocumento(captacion.getIntParaMonedaCod());
		if(!libroDiarioDetalle.getIntParaMonedaDocumento().equals(Constante.PARAM_T_TIPOMONEDA_SOL)){
			libroDiarioDetalle.setIntTipoCambio(tipoCambioActual.getId().getIntParaClaseTipoCambio());
			if (o.get(0).getIntParamDebeHaber().equals(1)) libroDiarioDetalle.setBdDebeExtranjero(bdMontoDebeHaber.multiply(tipoCambioActual.getBdCompra()));
			if (o.get(0).getIntParamDebeHaber().equals(2)) libroDiarioDetalle.setBdHaberExtranjero(bdMontoDebeHaber.multiply(tipoCambioActual.getBdCompra()));
		}else{
			if (o.get(0).getIntParamDebeHaber().equals(1)) libroDiarioDetalle.setBdDebeSoles(bdMontoDebeHaber);
			if (o.get(0).getIntParamDebeHaber().equals(2)) libroDiarioDetalle.setBdHaberSoles(bdMontoDebeHaber);
		}
		
		libroDiarioDetalle.setStrNumeroDocumento(""+expedientePrevision.getId().getIntItemExpediente());
		libroDiarioDetalle.setIntPersEmpresaSucursal(socioEstructura.getIntEmpresaSucUsuario());
		libroDiarioDetalle.setIntSucuIdSucursal(socioEstructura.getIntIdSucursalUsuario());
		libroDiarioDetalle.setIntSudeIdSubSucursal(socioEstructura.getIntIdSubSucursalUsuario());			

		return libroDiarioDetalle;
	}	
	
	private LibroDiarioDetalle generarCFFLibroDiarioDetalle(ExpedientePrevision expedientePrevision, ControlFondosFijos controlFondosFijos, TipoCambio tipoCambioActual, BigDecimal bdMontoDebeHaber)
		throws Exception{
		LibroDiarioDetalle libroDiarioDetalle = null;
		Integer intIdEmpresa = expedientePrevision.getId().getIntPersEmpresaPk();
		PlanCuentaId pId = new PlanCuentaId();
		Captacion captacion = expedientePrevision.getCaptacion();

		try {
			PlanCuentaFacadeRemote planCuentaFacade = (PlanCuentaFacadeRemote)EJBFactory.getRemote(PlanCuentaFacadeRemote.class);
			libroDiarioDetalle = new LibroDiarioDetalle();
			libroDiarioDetalle.setId(new LibroDiarioDetalleId());
			libroDiarioDetalle.getId().setIntPersEmpresaLibro(intIdEmpresa);
			libroDiarioDetalle.getId().setIntContPeriodoLibro(obtenerPeriodoActual());

			libroDiarioDetalle.setIntPersPersona(expedientePrevision.getPersonaGirar().getIntIdPersona());
			//Datos Generales del Plan de Cuenta 
			PlanCuenta planCuenta = obtenerPlanCuentaOrigenCFF(controlFondosFijos);
			libroDiarioDetalle.setIntPersEmpresaCuenta(planCuenta.getId().getIntEmpresaCuentaPk());
			libroDiarioDetalle.setIntContPeriodo(planCuenta.getId().getIntPeriodoCuenta());
			libroDiarioDetalle.setStrContNumeroCuenta(planCuenta.getId().getStrNumeroCuenta());			
			libroDiarioDetalle.setIntParaDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_RENDICION);
			//Descripción del Plan de Cuenta
			pId.setIntEmpresaCuentaPk(planCuenta.getId().getIntEmpresaCuentaPk());
			pId.setStrNumeroCuenta(planCuenta.getId().getStrNumeroCuenta());
			pId.setIntPeriodoCuenta(planCuenta.getId().getIntPeriodoCuenta());
			libroDiarioDetalle.setStrComentario(planCuentaFacade.getPlanCuentaPorPk(pId).getStrDescripcion().length()<20?planCuentaFacade.getPlanCuentaPorPk(pId).getStrDescripcion():planCuentaFacade.getPlanCuentaPorPk(pId).getStrDescripcion().substring(0, 20));
				
			libroDiarioDetalle.setStrSerieDocumento(null);
			libroDiarioDetalle.setIntPersEmpresaSucursal(controlFondosFijos.getSucursal().getId().getIntPersEmpresaPk());
			libroDiarioDetalle.setIntSucuIdSucursal(controlFondosFijos.getSucursal().getId().getIntIdSucursal());
			libroDiarioDetalle.setIntSudeIdSubSucursal(controlFondosFijos.getSubsucursal().getId().getIntIdSubSucursal());			

			if(captacion.getIntParaMonedaCod().compareTo(Constante.PARAM_T_TIPOMONEDA_SOL)==0){
				libroDiarioDetalle.setIntParaMonedaDocumento(Constante.PARAM_T_TIPOMONEDA_SOL);
			}else{
				libroDiarioDetalle.setIntParaMonedaDocumento(captacion.getIntParaMonedaCod());
			}

			libroDiarioDetalle.setIntTipoCambio(tipoCambioActual.getId().getIntParaClaseTipoCambio());
			
			
			if(libroDiarioDetalle.getIntParaMonedaDocumento().equals(Constante.PARAM_T_TIPOMONEDA_SOL)){
				libroDiarioDetalle.setBdHaberSoles(bdMontoDebeHaber);
			}else{
				/******cambio en soles ******/
				libroDiarioDetalle.setBdHaberSoles(bdMontoDebeHaber.multiply(tipoCambioActual.getBdCompra()));
				libroDiarioDetalle.setBdHaberExtranjero(bdMontoDebeHaber);
			}			
		} catch (Exception e) {
			log.error("Error en generarLibroDiarioDetalleGiroPrevisionFondoFijo --> "+e);
		}		
		return libroDiarioDetalle;
	}
	
	/**
	 * JCHAVEZ 18.02.2014
	 * Genera el LDD para el banco que girará el monto por bancos.
	 * @param expedientePrevision
	 * @param bancoCuenta
	 * @param tipoCambioActual
	 * @param bdMontoDebeHaber
	 * @return
	 * @throws Exception
	 */
	private LibroDiarioDetalle generarBancoLibroDiarioDetalle(ExpedientePrevision expedientePrevision, Bancocuenta bancoCuenta, TipoCambio tipoCambioActual, BigDecimal bdMontoDebeHaber, Integer intTipoDocumentoValidar) throws Exception{
		LibroDiarioDetalle libroDiarioDetalle = null;
		Integer intIdEmpresa = expedientePrevision.getId().getIntPersEmpresaPk();
		Captacion captacion = expedientePrevision.getCaptacion();
		try {
			libroDiarioDetalle = new LibroDiarioDetalle();
			libroDiarioDetalle.setId(new LibroDiarioDetalleId());
			libroDiarioDetalle.getId().setIntPersEmpresaLibro(intIdEmpresa);
			libroDiarioDetalle.getId().setIntContPeriodoLibro(obtenerPeriodoActual());
			
			libroDiarioDetalle.setIntPersPersona(expedientePrevision.getPersonaGirar().getIntIdPersona());
			
			libroDiarioDetalle.setIntPersEmpresaCuenta(bancoCuenta.getId().getIntEmpresaPk());
			libroDiarioDetalle.setIntContPeriodo(bancoCuenta.getIntPeriodocuenta());
			libroDiarioDetalle.setStrContNumeroCuenta(bancoCuenta.getStrNumerocuenta());			
//			libroDiarioDetalle.setIntParaDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_RENDICION);
			libroDiarioDetalle.setIntParaDocumentoGeneral(intTipoDocumentoValidar);
			libroDiarioDetalle.setStrComentario(bancoCuenta.getStrNombrecuenta());
			libroDiarioDetalle.setStrSerieDocumento(null);
			libroDiarioDetalle.setIntPersEmpresaSucursal(Constante.PARAM_EMPRESASESION);
			libroDiarioDetalle.setIntSucuIdSucursal(Constante.PARAM_SUCURSALSESION);
			libroDiarioDetalle.setIntSudeIdSubSucursal(Constante.PARAM_SUBSUCURSALSESION);			
		
			if(captacion.getIntParaMonedaCod().compareTo(Constante.PARAM_T_TIPOMONEDA_SOL)==0){
				libroDiarioDetalle.setIntParaMonedaDocumento(Constante.PARAM_T_TIPOMONEDA_SOL);
			}else{
				libroDiarioDetalle.setIntParaMonedaDocumento(captacion.getIntParaMonedaCod());
			}

			libroDiarioDetalle.setIntTipoCambio(tipoCambioActual.getId().getIntParaClaseTipoCambio());
			
			
			if(libroDiarioDetalle.getIntParaMonedaDocumento().equals(Constante.PARAM_T_TIPOMONEDA_SOL)){
				libroDiarioDetalle.setBdHaberSoles(bdMontoDebeHaber);
			}else{
				/******cambio en soles ******/
				libroDiarioDetalle.setBdHaberSoles(bdMontoDebeHaber.multiply(tipoCambioActual.getBdCompra()));
				libroDiarioDetalle.setBdHaberExtranjero(bdMontoDebeHaber);
			}			
		} catch (Exception e) {
			log.error("Error en generarLibroDiarioDetalleGiroPorSedeCentral --> "+e);
		}
		return libroDiarioDetalle;
	}
	
	
}
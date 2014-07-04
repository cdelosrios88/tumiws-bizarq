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
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.Cuenta;
import pe.com.tumi.credito.socio.aperturaCuenta.core.facade.CuentaFacadeRemote;
import pe.com.tumi.credito.socio.core.domain.SocioEstructura;
import pe.com.tumi.credito.socio.core.facade.SocioFacadeRemote;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.movimiento.concepto.domain.CuentaConcepto;
import pe.com.tumi.movimiento.concepto.domain.CuentaConceptoDetalle;
import pe.com.tumi.movimiento.concepto.domain.CuentaConceptoId;
import pe.com.tumi.movimiento.concepto.facade.ConceptoFacadeRemote;
import pe.com.tumi.parametro.general.domain.Archivo;
import pe.com.tumi.parametro.general.domain.TipoCambio;
import pe.com.tumi.parametro.general.domain.TipoCambioId;
import pe.com.tumi.parametro.general.facade.GeneralFacadeRemote;
import pe.com.tumi.persona.core.domain.CuentaBancaria;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.seguridad.login.domain.Usuario;
import pe.com.tumi.servicio.prevision.domain.BeneficiarioLiquidacion;
import pe.com.tumi.servicio.prevision.domain.EstadoLiquidacion;
import pe.com.tumi.servicio.prevision.domain.ExpedienteLiquidacion;
import pe.com.tumi.servicio.prevision.domain.ExpedienteLiquidacionDetalle;
import pe.com.tumi.servicio.solicitudPrestamo.domain.EgresoDetalleInterfaz;
import pe.com.tumi.tesoreria.banco.domain.Bancocuenta;
import pe.com.tumi.tesoreria.egreso.domain.ControlFondosFijos;
import pe.com.tumi.tesoreria.egreso.domain.Egreso;
import pe.com.tumi.tesoreria.egreso.domain.EgresoDetalle;

public class EgresoLiquidacionService {
	
	protected static Logger log = Logger.getLogger(EgresoLiquidacionService.class);
	
	public List<EgresoDetalleInterfaz> cargarListaEgresoDetalleInterfaz(List<ExpedienteLiquidacionDetalle> listaExpedienteLiquidacionDetalle,Integer intIdBeneficiario) 
		throws BusinessException{
		List<EgresoDetalleInterfaz> listaEgresoDetalleInterfaz = new ArrayList<EgresoDetalleInterfaz>();
		try{
			
			listaEgresoDetalleInterfaz = new ArrayList<EgresoDetalleInterfaz>();
			
			log.info("intIdBeneficiario:"+intIdBeneficiario);
			for(ExpedienteLiquidacionDetalle expedienteLiquidacionDetalle : listaExpedienteLiquidacionDetalle){
				BeneficiarioLiquidacion beneficiarioLiquidacionSeleccionado = null;
				for(BeneficiarioLiquidacion beneficiarioLiquidacion : expedienteLiquidacionDetalle.getListaBeneficiarioLiquidacion()){
					log.info(beneficiarioLiquidacion);
					if(beneficiarioLiquidacion.getIntPersPersonaBeneficiario().equals(intIdBeneficiario)){
						beneficiarioLiquidacionSeleccionado = beneficiarioLiquidacion;
						break;
					}
				}
				
				if(beneficiarioLiquidacionSeleccionado==null) continue;
				
				BigDecimal bdPorcentaje = beneficiarioLiquidacionSeleccionado.getBdPorcentajeBeneficio().divide(new BigDecimal(100));
				log.info("bdPorcentaje:"+bdPorcentaje);
				
				EgresoDetalleInterfaz egresoDetalleInterfaz = new EgresoDetalleInterfaz();
				egresoDetalleInterfaz.setStrNroSolicitud(""+expedienteLiquidacionDetalle.getId().getIntCuenta());
				egresoDetalleInterfaz.setBdCapital(expedienteLiquidacionDetalle.getBdSaldo().multiply(bdPorcentaje));
				egresoDetalleInterfaz.setBdInteres(new BigDecimal(0));
				egresoDetalleInterfaz.setBdGastosAdministrativos(new BigDecimal(0));
				egresoDetalleInterfaz.setBdSubTotal(egresoDetalleInterfaz.getBdCapital());
				
				expedienteLiquidacionDetalle.setCuentaConcepto(obtenerCuentaConcepto(expedienteLiquidacionDetalle));
				egresoDetalleInterfaz.setExpedienteLiquidacionDetalle(expedienteLiquidacionDetalle);
				egresoDetalleInterfaz.getExpedienteLiquidacionDetalle().setBeneficiarioLiquidacion(beneficiarioLiquidacionSeleccionado);
				listaEgresoDetalleInterfaz.add(egresoDetalleInterfaz);
			}
		}catch(Exception e){
			throw new BusinessException(e);
		}
		
		return listaEgresoDetalleInterfaz;
	}
	
	private CuentaConcepto obtenerCuentaConcepto(ExpedienteLiquidacionDetalle expedienteLiquidacionDetalle)throws Exception{
		ConceptoFacadeRemote conceptoFacade = (ConceptoFacadeRemote) EJBFactory.getRemote(ConceptoFacadeRemote.class);
		
		CuentaConceptoId cuentaConceptoId = new CuentaConceptoId();
		cuentaConceptoId.setIntPersEmpresaPk(expedienteLiquidacionDetalle.getId().getIntPersEmpresa());
		cuentaConceptoId.setIntCuentaPk(expedienteLiquidacionDetalle.getId().getIntCuenta());
		cuentaConceptoId.setIntItemCuentaConcepto(expedienteLiquidacionDetalle.getId().getIntItemCuentaConcepto());
		
		return conceptoFacade.getCuentaConceptoYUltimoDetallaPorId(cuentaConceptoId);
	}	
	
	private Timestamp obtenerFechaActual(){
		return new Timestamp(new Date().getTime());
	}
	
	private SocioEstructura obtenerSocioEstructura(Persona persona, Integer intIdEmpresa) throws Exception{
		SocioFacadeRemote socioFacade = (SocioFacadeRemote) EJBFactory.getRemote(SocioFacadeRemote.class);
		List<SocioEstructura> lista = socioFacade.getListaSocioEstrucuraPorIdPersona(persona.getIntIdPersona(), intIdEmpresa);		
		return lista.get(0);
	}
	
	private Modelo obtenerModelo(Integer intIdEmpresa)throws Exception{
		ModeloFacadeRemote modeloFacade = (ModeloFacadeRemote) EJBFactory.getRemote(ModeloFacadeRemote.class);
		List<Modelo> listaModelo = null;		
		listaModelo = modeloFacade.obtenerTipoModeloActual(Constante.PARAM_T_TIPOMODELOCONTABLE_LIQUIDACIONCUENTA, intIdEmpresa);		
		return listaModelo.get(0);
	}
	
	private void cargarModeloDetalleEnEgresoDetalleInterfaz(Modelo modelo, List<EgresoDetalleInterfaz> listaEgresoDetalleInterfaz) throws Exception{
		
		for(Object o : listaEgresoDetalleInterfaz){
			EgresoDetalleInterfaz egresoDetalleInterfaz = (EgresoDetalleInterfaz)o;
			CuentaConceptoDetalle cuentaConceptoDetalle = egresoDetalleInterfaz.getExpedienteLiquidacionDetalle().getCuentaConcepto().getDetalle();
			for(ModeloDetalle modeloDetalle : modelo.getListModeloDetalle()){
				ModeloDetalleNivel modeloDetalleNivel = modeloDetalle.getListModeloDetalleNivel().get(0);
				if(modeloDetalleNivel.getIntValor().equals(cuentaConceptoDetalle.getIntParaTipoConceptoCod())){
					egresoDetalleInterfaz.setModeloDetalle(modeloDetalle);
					break;
				}
			}
		}
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
	
	private BigDecimal cargarTotalEgresoDetalleInterfaz(List<EgresoDetalleInterfaz> listaEgresoDetalleInterfaz)throws Exception{
		BigDecimal bdTotal = new BigDecimal(0);
		for(EgresoDetalleInterfaz egresoDetalleInterfaz : listaEgresoDetalleInterfaz){
				bdTotal = bdTotal.add(egresoDetalleInterfaz.getBdSubTotal());
		}
		return bdTotal;
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

	
	private TipoCambio obtenerTipoCambioSolicitud(ExpedienteLiquidacion expedienteLiquidacion, Integer intParaTipoMoneda, Integer intIdEmpresa) 
		throws Exception{
		GeneralFacadeRemote generalFacade = (GeneralFacadeRemote) EJBFactory.getRemote(GeneralFacadeRemote.class);		
		EstadoLiquidacion estadoLiquidacionUltimo = expedienteLiquidacion.getEstadoLiquidacionUltimo();		
		TipoCambioId tipoCambioId = new TipoCambioId();
		tipoCambioId.setIntPersEmpresa(intIdEmpresa);
		tipoCambioId.setIntParaMoneda(intParaTipoMoneda);
		tipoCambioId.setDtParaFecha(estadoLiquidacionUltimo.getTsFechaEstado());
		tipoCambioId.setIntParaClaseTipoCambio(Constante.PARAM_T_TIPOCAMBIO_BANCARIO);
		
		return generalFacade.getTipoCambioPorPK(tipoCambioId);		
	}
	
	private BigDecimal obtenerMontoDiferencial(BigDecimal bdMontoMonedaExtranjera, TipoCambio tipoCambioActual, TipoCambio tipoCambioSolicitud){
		BigDecimal bdMontoSolesSolicitud = bdMontoMonedaExtranjera.multiply(tipoCambioSolicitud.getBdPromedio());
		BigDecimal bdMontoSolesActual = bdMontoMonedaExtranjera.multiply(tipoCambioActual.getBdPromedio());
		
		return bdMontoSolesActual.subtract(bdMontoSolesSolicitud);
	}
	
	private EgresoDetalle generarEgresoDetalle(SocioEstructura socioEstructura,	EgresoDetalleInterfaz egresoDetalleInterfaz, 
			ExpedienteLiquidacion expedienteLiquidacion, ControlFondosFijos controlFondosFijos, Usuario usuario)
		throws Exception{
		
		Integer intIdEmpresa = expedienteLiquidacion.getId().getIntPersEmpresaPk();
		Persona personaGirar = expedienteLiquidacion.getPersonaGirar();
		
		TipoCambio tipoCambioActual = null;
		TipoCambio tipoCambioSolicitud = null;
		Cuenta cuenta = egresoDetalleInterfaz.getExpedienteLiquidacionDetalle().getCuenta();
		ModeloDetalle modeloDetalle = egresoDetalleInterfaz.getModeloDetalle();
		boolean esMonedaExtranjera;
		
		EgresoDetalle egresoDetalle = new EgresoDetalle();		
		egresoDetalle.getId().setIntPersEmpresaEgreso(intIdEmpresa);
		egresoDetalle.setIntParaDocumentoGeneral(expedienteLiquidacion.getIntParaDocumentoGeneral());
		egresoDetalle.setIntParaTipoComprobante(null);
		egresoDetalle.setStrSerieDocumento(null);
		egresoDetalle.setStrNumeroDocumento(""+cuenta.getIntSecuenciaCuenta());
		egresoDetalle.setStrDescripcionEgreso(modeloDetalle.getPlanCuenta().getStrDescripcion());
		egresoDetalle.setIntPersEmpresaGirado(intIdEmpresa);
		egresoDetalle.setIntPersonaGirado(personaGirar.getIntIdPersona());
		egresoDetalle.setIntCuentaGirado(cuenta.getId().getIntCuenta());
		egresoDetalle.setIntSucuIdSucursalEgreso(socioEstructura.getIntIdSucursalAdministra());
		egresoDetalle.setIntSudeIdSubsucursalEgreso(socioEstructura.getIntIdSubsucurAdministra());
		egresoDetalle.setIntParaTipoMoneda(cuenta.getIntParaTipoMonedaCod());
		if(egresoDetalle.getIntParaTipoMoneda().equals(Constante.PARAM_T_TIPOMONEDA_SOL)){
			esMonedaExtranjera = Boolean.FALSE;
		}else{
			esMonedaExtranjera = Boolean.TRUE;
			tipoCambioActual = obtenerTipoCambioActual(egresoDetalle.getIntParaTipoMoneda(), intIdEmpresa);			
			tipoCambioSolicitud = obtenerTipoCambioSolicitud(expedienteLiquidacion, egresoDetalle.getIntParaTipoMoneda(), intIdEmpresa);		
		}
		
		if(modeloDetalle.getIntParaOpcionDebeHaber().equals(Constante.PARAM_T_OPCIONDEBEHABER_DEBE)){
			egresoDetalle.setBdMontoCargo(egresoDetalleInterfaz.getBdSubTotal());
			if(egresoDetalle.getBdMontoCargo()==null) return null;
			if(esMonedaExtranjera) egresoDetalle.setBdMontoDiferencial(obtenerMontoDiferencial(
					egresoDetalle.getBdMontoCargo(),tipoCambioActual, tipoCambioSolicitud));
		
		}else if(modeloDetalle.getIntParaOpcionDebeHaber().equals(Constante.PARAM_T_OPCIONDEBEHABER_HABER)){
			egresoDetalle.setBdMontoAbono(egresoDetalleInterfaz.getBdSubTotal());
			if(egresoDetalle.getBdMontoAbono()==null) return null;
			if(esMonedaExtranjera) egresoDetalle.setBdMontoDiferencial(obtenerMontoDiferencial(
					egresoDetalle.getBdMontoAbono(),tipoCambioActual, tipoCambioSolicitud));
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
	
	private EgresoDetalle generarEgresoDetalleCheque(SocioEstructura socioEstructura,	EgresoDetalleInterfaz egresoDetalleInterfaz, 
			ExpedienteLiquidacion expedienteLiquidacion, Bancocuenta bancoCuenta, Usuario usuario)
		throws Exception{
		
		Integer intIdEmpresa = expedienteLiquidacion.getId().getIntPersEmpresaPk();
		Persona personaGirar = expedienteLiquidacion.getPersonaGirar();
		
		TipoCambio tipoCambioActual = null;
		TipoCambio tipoCambioSolicitud = null;
		Cuenta cuenta = egresoDetalleInterfaz.getExpedienteLiquidacionDetalle().getCuenta();
		ModeloDetalle modeloDetalle = egresoDetalleInterfaz.getModeloDetalle();
		boolean esMonedaExtranjera;
		
		EgresoDetalle egresoDetalle = new EgresoDetalle();		
		egresoDetalle.getId().setIntPersEmpresaEgreso(intIdEmpresa);
		egresoDetalle.setIntParaDocumentoGeneral(expedienteLiquidacion.getIntParaDocumentoGeneral());
		egresoDetalle.setIntParaTipoComprobante(null);
		egresoDetalle.setStrSerieDocumento(null);
		egresoDetalle.setStrNumeroDocumento(""+cuenta.getIntSecuenciaCuenta());
		egresoDetalle.setStrDescripcionEgreso(modeloDetalle.getPlanCuenta().getStrDescripcion());
		egresoDetalle.setIntPersEmpresaGirado(intIdEmpresa);
		egresoDetalle.setIntPersonaGirado(personaGirar.getIntIdPersona());
		egresoDetalle.setIntCuentaGirado(cuenta.getId().getIntCuenta());
		egresoDetalle.setIntSucuIdSucursalEgreso(socioEstructura.getIntIdSucursalAdministra());
		egresoDetalle.setIntSudeIdSubsucursalEgreso(socioEstructura.getIntIdSubsucurAdministra());
		egresoDetalle.setIntParaTipoMoneda(cuenta.getIntParaTipoMonedaCod());
		if(egresoDetalle.getIntParaTipoMoneda().equals(Constante.PARAM_T_TIPOMONEDA_SOL)){
			esMonedaExtranjera = Boolean.FALSE;
		}else{
			esMonedaExtranjera = Boolean.TRUE;
			tipoCambioActual = obtenerTipoCambioActual(egresoDetalle.getIntParaTipoMoneda(), intIdEmpresa);			
			tipoCambioSolicitud = obtenerTipoCambioSolicitud(expedienteLiquidacion, egresoDetalle.getIntParaTipoMoneda(), intIdEmpresa);		
		}
		
		if(modeloDetalle.getIntParaOpcionDebeHaber().equals(Constante.PARAM_T_OPCIONDEBEHABER_DEBE)){
			egresoDetalle.setBdMontoCargo(egresoDetalleInterfaz.getBdSubTotal());
			if(egresoDetalle.getBdMontoCargo()==null) return null;
			if(esMonedaExtranjera) egresoDetalle.setBdMontoDiferencial(obtenerMontoDiferencial(
					egresoDetalle.getBdMontoCargo(),tipoCambioActual, tipoCambioSolicitud));
		
		}else if(modeloDetalle.getIntParaOpcionDebeHaber().equals(Constante.PARAM_T_OPCIONDEBEHABER_HABER)){
			egresoDetalle.setBdMontoAbono(egresoDetalleInterfaz.getBdSubTotal());
			if(egresoDetalle.getBdMontoAbono()==null) return null;
			if(esMonedaExtranjera) egresoDetalle.setBdMontoDiferencial(obtenerMontoDiferencial(
					egresoDetalle.getBdMontoAbono(),tipoCambioActual, tipoCambioSolicitud));
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
		LibroDiarioFacadeRemote libroDiarioFacade = (LibroDiarioFacadeRemote) EJBFactory.getRemote(LibroDiarioFacadeRemote.class);		
		
		Egreso egresoCFF = controlFondosFijos.getEgreso();
		PlanCuenta planCuenta = new PlanCuenta();
		planCuenta.setId(new PlanCuentaId());
		LibroDiarioId libroDiarioId = new LibroDiarioId();
		libroDiarioId.setIntPersEmpresaLibro(egresoCFF.getIntPersEmpresaLibro());
		libroDiarioId.setIntContPeriodoLibro(egresoCFF.getIntContPeriodoLibro());
		libroDiarioId.setIntContCodigoLibro(egresoCFF.getIntContCodigoLibro());
		
		//log.info(egresoCFF);
		//log.info(libroDiarioId);
		LibroDiario libroDiario = libroDiarioFacade.getLibroDiarioPorPk(libroDiarioId);
		//log.info(libroDiario);
		List<LibroDiarioDetalle> listaLibroDiarioDetalle = libroDiarioFacade.getListaLibroDiarioDetallePorLibroDiario(libroDiario);
		
		for(LibroDiarioDetalle libroDiarioDetalle : listaLibroDiarioDetalle){
			//log.info(libroDiario);
			if(libroDiarioDetalle.getBdDebeSoles()!=null || libroDiarioDetalle.getBdDebeExtranjero()!=null){
				planCuenta.getId().setIntEmpresaCuentaPk(libroDiarioDetalle.getIntPersEmpresaCuenta());
				planCuenta.getId().setIntPeriodoCuenta(libroDiarioDetalle.getIntContPeriodo());
				planCuenta.getId().setStrNumeroCuenta(libroDiarioDetalle.getStrContNumeroCuenta());
			}
		}
		
		return planCuenta;
	}
	
	private LibroDiarioDetalle generarLibroDiarioDetalle(SocioEstructura socioEstructura, EgresoDetalleInterfaz egresoDetalleInterfaz,
		ExpedienteLiquidacion expedienteLiquidacion, ControlFondosFijos controlFondosFijos, BigDecimal bdTotalEgresoDetalleInterfaz)
		throws Exception{
		
		Integer intIdEmpresa = expedienteLiquidacion.getId().getIntPersEmpresaPk();
		Persona personaGirar = expedienteLiquidacion.getPersonaGirar();
		TipoCambio tipoCambioActual = null;
		
		ModeloDetalle modeloDetalle = null;
		Integer intMoneda = null; 
		if(egresoDetalleInterfaz !=null){
			intMoneda = egresoDetalleInterfaz.getExpedienteLiquidacionDetalle().getCuenta().getIntParaTipoMonedaCod();
			modeloDetalle = egresoDetalleInterfaz.getModeloDetalle();			
		}else{
			intMoneda = Constante.PARAM_T_TIPOMONEDA_SOL;
		}
		
		
		boolean esMonedaExtranjera;
		LibroDiarioDetalle libroDiarioDetalle = new LibroDiarioDetalle();
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
		libroDiarioDetalle.setIntParaDocumentoGeneral(expedienteLiquidacion.getIntParaDocumentoGeneral());
		libroDiarioDetalle.setStrSerieDocumento(null);
		libroDiarioDetalle.setIntParaMonedaDocumento(intMoneda);
		if(!libroDiarioDetalle.getIntParaMonedaDocumento().equals(Constante.PARAM_T_TIPOMONEDA_SOL)){
			tipoCambioActual = obtenerTipoCambioActual(intMoneda, intIdEmpresa);
			libroDiarioDetalle.setIntTipoCambio(tipoCambioActual.getId().getIntParaClaseTipoCambio());
			esMonedaExtranjera = Boolean.TRUE;
		}else{
			esMonedaExtranjera = Boolean.FALSE;
		}
		
		if(modeloDetalle!=null){
			libroDiarioDetalle.setStrNumeroDocumento(""+expedienteLiquidacion.getId().getIntItemExpediente());
			libroDiarioDetalle.setIntPersEmpresaSucursal(socioEstructura.getIntEmpresaSucUsuario());
			libroDiarioDetalle.setIntSucuIdSucursal(socioEstructura.getIntIdSucursalUsuario());
			libroDiarioDetalle.setIntSudeIdSubSucursal(socioEstructura.getIntIdSubSucursalUsuario());			
		}else{
			libroDiarioDetalle.setIntPersEmpresaSucursal(controlFondosFijos.getSucursal().getId().getIntPersEmpresaPk());
			libroDiarioDetalle.setIntSucuIdSucursal(controlFondosFijos.getSucursal().getId().getIntIdSucursal());
			libroDiarioDetalle.setIntSudeIdSubSucursal(controlFondosFijos.getSubsucursal().getId().getIntIdSubSucursal());			
		}
	
		if(modeloDetalle!=null){
			if(modeloDetalle.getIntParaOpcionDebeHaber().equals(Constante.PARAM_T_OPCIONDEBEHABER_DEBE)){
				if(!esMonedaExtranjera){
					libroDiarioDetalle.setBdDebeSoles(egresoDetalleInterfaz.getBdSubTotal());					
				}else{
					libroDiarioDetalle.setBdDebeExtranjero(egresoDetalleInterfaz.getBdSubTotal());
					libroDiarioDetalle.setBdDebeSoles(libroDiarioDetalle.getBdDebeExtranjero().multiply(tipoCambioActual.getBdPromedio()));
				}			
			}else if(modeloDetalle.getIntParaOpcionDebeHaber().equals(Constante.PARAM_T_OPCIONDEBEHABER_HABER)){
				if(!esMonedaExtranjera){
					libroDiarioDetalle.setBdHaberSoles(egresoDetalleInterfaz.getBdSubTotal());					
				}else{
					libroDiarioDetalle.setBdHaberExtranjero(egresoDetalleInterfaz.getBdSubTotal());
					libroDiarioDetalle.setBdHaberSoles(libroDiarioDetalle.getBdHaberExtranjero().multiply(tipoCambioActual.getBdPromedio()));
				}
			}
		}else{
			if(!esMonedaExtranjera){
				libroDiarioDetalle.setBdHaberSoles(bdTotalEgresoDetalleInterfaz);
			}else{
				libroDiarioDetalle.setBdHaberExtranjero(bdTotalEgresoDetalleInterfaz);
				libroDiarioDetalle.setBdHaberSoles(libroDiarioDetalle.getBdHaberExtranjero().multiply(tipoCambioActual.getBdPromedio()));
			}
		}
		return libroDiarioDetalle;
	}
	

	
	private LibroDiarioDetalle generarLibroDiarioDetalleCheque(SocioEstructura socioEstructura, EgresoDetalleInterfaz egresoDetalleInterfaz,
		ExpedienteLiquidacion expedienteLiquidacion, Bancocuenta bancoCuenta, BigDecimal bdTotalEgresoDetalleInterfaz)
		throws Exception{
		
		Integer intIdEmpresa = expedienteLiquidacion.getId().getIntPersEmpresaPk();
		Persona personaGirar = expedienteLiquidacion.getPersonaGirar();
		TipoCambio tipoCambioActual = null;
		
		ModeloDetalle modeloDetalle = null;
		Integer intMoneda = null; 
		if(egresoDetalleInterfaz !=null){
			intMoneda = egresoDetalleInterfaz.getExpedienteLiquidacionDetalle().getCuenta().getIntParaTipoMonedaCod();
			modeloDetalle = egresoDetalleInterfaz.getModeloDetalle();			
		}else{
			intMoneda = Constante.PARAM_T_TIPOMONEDA_SOL;
		}
		
		
		boolean esMonedaExtranjera;
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
		libroDiarioDetalle.setIntParaDocumentoGeneral(expedienteLiquidacion.getIntParaDocumentoGeneral());
		libroDiarioDetalle.setStrSerieDocumento(null);
		libroDiarioDetalle.setIntParaMonedaDocumento(intMoneda);
		if(!libroDiarioDetalle.getIntParaMonedaDocumento().equals(Constante.PARAM_T_TIPOMONEDA_SOL)){
			tipoCambioActual = obtenerTipoCambioActual(intMoneda, intIdEmpresa);
			libroDiarioDetalle.setIntTipoCambio(tipoCambioActual.getId().getIntParaClaseTipoCambio());
			esMonedaExtranjera = Boolean.TRUE;
		}else{
			esMonedaExtranjera = Boolean.FALSE;
		}
		
		if(modeloDetalle!=null){
			libroDiarioDetalle.setStrNumeroDocumento(""+expedienteLiquidacion.getId().getIntItemExpediente());
			libroDiarioDetalle.setIntPersEmpresaSucursal(socioEstructura.getIntEmpresaSucUsuario());
			libroDiarioDetalle.setIntSucuIdSucursal(socioEstructura.getIntIdSucursalUsuario());
			libroDiarioDetalle.setIntSudeIdSubSucursal(socioEstructura.getIntIdSubSucursalUsuario());			
		}else{
			libroDiarioDetalle.setIntPersEmpresaSucursal(Constante.PARAM_EMPRESASESION);
			libroDiarioDetalle.setIntSucuIdSucursal(Constante.SUCURSAL_SEDECENTRAL);
			libroDiarioDetalle.setIntSudeIdSubSucursal(Constante.SUBSUCURSAL_SEDE1);		
		}
	
		if(modeloDetalle!=null){
			if(modeloDetalle.getIntParaOpcionDebeHaber().equals(Constante.PARAM_T_OPCIONDEBEHABER_DEBE)){
				if(!esMonedaExtranjera){
					libroDiarioDetalle.setBdDebeSoles(egresoDetalleInterfaz.getBdSubTotal());					
				}else{
					libroDiarioDetalle.setBdDebeExtranjero(egresoDetalleInterfaz.getBdSubTotal());
					libroDiarioDetalle.setBdDebeSoles(libroDiarioDetalle.getBdDebeExtranjero().multiply(tipoCambioActual.getBdPromedio()));
				}			
			}else if(modeloDetalle.getIntParaOpcionDebeHaber().equals(Constante.PARAM_T_OPCIONDEBEHABER_HABER)){
				if(!esMonedaExtranjera){
					libroDiarioDetalle.setBdHaberSoles(egresoDetalleInterfaz.getBdSubTotal());					
				}else{
					libroDiarioDetalle.setBdHaberExtranjero(egresoDetalleInterfaz.getBdSubTotal());
					libroDiarioDetalle.setBdHaberSoles(libroDiarioDetalle.getBdHaberExtranjero().multiply(tipoCambioActual.getBdPromedio()));
				}
			}
		}else{
			if(!esMonedaExtranjera){
				libroDiarioDetalle.setBdHaberSoles(bdTotalEgresoDetalleInterfaz);
			}else{
				libroDiarioDetalle.setBdHaberExtranjero(bdTotalEgresoDetalleInterfaz);
				libroDiarioDetalle.setBdHaberSoles(libroDiarioDetalle.getBdHaberExtranjero().multiply(tipoCambioActual.getBdPromedio()));
			}
		}
		return libroDiarioDetalle;
	}

	public Egreso generarEgresoLiquidacion(ExpedienteLiquidacion expedienteLiquidacion, ControlFondosFijos controlFondosFijos, Usuario usuario) 
		throws BusinessException{
		Egreso egreso = new Egreso();
		try{
			//Expediente Liquidacion
			Persona personaGirar = expedienteLiquidacion.getPersonaGirar();
			Integer intIdEmpresa = expedienteLiquidacion.getId().getIntPersEmpresaPk();
			List<EgresoDetalleInterfaz> listaEgresoDetalleInterfaz = expedienteLiquidacion.getListaEgresoDetalleInterfaz();
			boolean esUltimoBeneficiarioAGirar = expedienteLiquidacion.isEsUltimoBeneficiarioAGirar();	
			Integer intIdPersonaBeneficiarioSeleccionar = expedienteLiquidacion.getIntIdPersonaBeneficiarioGirar();
			String	strGlosaEgreso = expedienteLiquidacion.getStrGlosaEgreso();
			
			BigDecimal bdTotalEgresoDetalleInterfaz = cargarTotalEgresoDetalleInterfaz(listaEgresoDetalleInterfaz);			
			SocioEstructura socioEstructura = obtenerSocioEstructura(personaGirar, intIdEmpresa);
			Modelo modelo = obtenerModelo(intIdEmpresa);			
			cargarModeloDetalleEnEgresoDetalleInterfaz(modelo, listaEgresoDetalleInterfaz);
			
			egreso.getId().setIntPersEmpresaEgreso(intIdEmpresa);
			egreso.setIntParaTipoOperacion(Constante.PARAM_T_TIPODEOPERACION_EGRESO);
			egreso.setIntParaFormaPago(Constante.PARAM_T_FORMAPAGOEGRESO_EFECTIVO);
			egreso.setIntParaDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_RENDICION);
			egreso.setIntItemPeriodoEgreso(obtenerPeriodoActual());
			egreso.setIntItemEgreso(null);
			egreso.setIntSucuIdSucursal(usuario.getSucursal().getId().getIntIdSucursal());
			egreso.setIntSudeIdSubsucursal(usuario.getSubSucursal().getId().getIntIdSubSucursal());
			if(esUltimoBeneficiarioAGirar){
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
			egreso.setIntCuentaGirado(null);
			egreso.setIntCuentaGirado(null);
			//20.05.2014 jchavez cambio de tipo de dato
			egreso.setStrPersCuentaBancariaGirado(null);			
//			egreso.setIntPersCuentaBancariaGirado(null);
			egreso.setIntPersEmpresaBeneficiario(intIdEmpresa);
			egreso.setIntPersPersonaBeneficiario(intIdPersonaBeneficiarioSeleccionar);
			egreso.setIntPersCuentaBancariaBeneficiario(null);
			egreso.setIntPersPersonaApoderado(null);
			egreso.setIntPersEmpresaApoderado(null);			
			egreso.setBdMontoTotal(bdTotalEgresoDetalleInterfaz);
			egreso.setStrObservacion(strGlosaEgreso);
			egreso.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			egreso.setTsFechaRegistro(obtenerFechaActual());
			egreso.setIntPersEmpresaUsuario(intIdEmpresa);
			egreso.setIntPersPersonaUsuario(usuario.getIntPersPersonaPk());
			egreso.setIntPersEmpresaEgresoAnula(null);
			egreso.setIntPersPersonaEgresoAnula(null);
			egreso.setIntParaTipoApoderado(null);
			egreso.setIntItemArchivoApoderado(null);
			egreso.setIntItemHistoricoApoderado(null);			
			egreso.setIntParaTipoGiro(null);
			egreso.setIntItemArchivoGiro(null);
			egreso.setIntItemHistoricoGiro(null);
			
			for(Object o : listaEgresoDetalleInterfaz){
				EgresoDetalleInterfaz egresoDetalleInterfaz = (EgresoDetalleInterfaz)o;
				EgresoDetalle egresoDetalle = generarEgresoDetalle(socioEstructura, egresoDetalleInterfaz, expedienteLiquidacion, 
						controlFondosFijos, usuario);
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
			
			for(Object o : listaEgresoDetalleInterfaz){
				EgresoDetalleInterfaz egresoDetalleInterfaz = (EgresoDetalleInterfaz)o;
				LibroDiarioDetalle libroDiarioDetalle = generarLibroDiarioDetalle(socioEstructura, egresoDetalleInterfaz, expedienteLiquidacion,
						controlFondosFijos, null);
				log.info(libroDiarioDetalle);
				if(libroDiarioDetalle!=null)	libroDiario.getListaLibroDiarioDetalle().add(libroDiarioDetalle);
			}
			
			LibroDiarioDetalle libroDiarioDetalle = generarLibroDiarioDetalle(socioEstructura, null, expedienteLiquidacion, 
					controlFondosFijos,	bdTotalEgresoDetalleInterfaz);
			log.info(libroDiarioDetalle);
			if(libroDiarioDetalle!=null)	libroDiario.getListaLibroDiarioDetalle().add(libroDiarioDetalle);
			
			expedienteLiquidacion.setListaEgresoDetalleInterfaz(listaEgresoDetalleInterfaz);
			egreso.setLibroDiario(libroDiario);
			egreso.setControlFondosFijos(controlFondosFijos);
			
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return egreso;
	}
//	
//	public Egreso generarEgresoLiquidacionCheque(ExpedienteLiquidacion expedienteLiquidacion, Bancocuenta bancoCuenta, Usuario usuario, 
//		Integer intNumeroCheque, Integer intTipoDocumentoValidar) throws BusinessException{
//		Egreso egreso = new Egreso();
//		try{
//			Persona personaGirar = expedienteLiquidacion.getPersonaGirar();
//			Integer intIdEmpresa = expedienteLiquidacion.getId().getIntPersEmpresaPk();
//			List<EgresoDetalleInterfaz> listaEgresoDetalleInterfaz = expedienteLiquidacion.getListaEgresoDetalleInterfaz();
//			boolean esUltimoBeneficiarioAGirar = expedienteLiquidacion.isEsUltimoBeneficiarioAGirar();	
//			Integer intIdPersonaBeneficiarioSeleccionar = expedienteLiquidacion.getIntIdPersonaBeneficiarioGirar();
//			String	strGlosaEgreso = expedienteLiquidacion.getStrGlosaEgreso();
//			
//			BigDecimal bdTotalEgresoDetalleInterfaz = cargarTotalEgresoDetalleInterfaz(listaEgresoDetalleInterfaz);			
//			SocioEstructura socioEstructura = obtenerSocioEstructura(personaGirar, intIdEmpresa);
//			Modelo modelo = obtenerModelo(intIdEmpresa);			
//			cargarModeloDetalleEnEgresoDetalleInterfaz(modelo, listaEgresoDetalleInterfaz);
//			
//			egreso.getId().setIntPersEmpresaEgreso(intIdEmpresa);
//			egreso.setIntParaTipoOperacion(Constante.PARAM_T_TIPODEOPERACION_EGRESO);
//			egreso.setIntParaFormaPago(Constante.PARAM_T_FORMAPAGOEGRESO_EFECTIVO);
//			egreso.setIntParaDocumentoGeneral(intTipoDocumentoValidar);
//			egreso.setIntItemPeriodoEgreso(obtenerPeriodoActual());
//			egreso.setIntItemEgreso(null);
//			egreso.setIntSucuIdSucursal(usuario.getSucursal().getId().getIntIdSucursal());
//			egreso.setIntSudeIdSubsucursal(usuario.getSubSucursal().getId().getIntIdSubSucursal());
//			if(esUltimoBeneficiarioAGirar){
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
//			egreso.setIntCuentaGirado(null);
//			egreso.setIntCuentaGirado(null);
//			egreso.setIntPersCuentaBancariaGirado(null);
//			egreso.setIntPersEmpresaBeneficiario(intIdEmpresa);
//			egreso.setIntPersPersonaBeneficiario(intIdPersonaBeneficiarioSeleccionar);
//			egreso.setIntPersCuentaBancariaBeneficiario(null);
//			egreso.setIntPersPersonaApoderado(null);
//			egreso.setIntPersEmpresaApoderado(null);			
//			egreso.setBdMontoTotal(bdTotalEgresoDetalleInterfaz);
//			egreso.setStrObservacion(strGlosaEgreso);
//			egreso.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
//			egreso.setTsFechaRegistro(obtenerFechaActual());
//			egreso.setIntPersEmpresaUsuario(intIdEmpresa);
//			egreso.setIntPersPersonaUsuario(usuario.getIntPersPersonaPk());
//			egreso.setIntPersEmpresaEgresoAnula(null);
//			egreso.setIntPersPersonaEgresoAnula(null);
//			egreso.setIntParaTipoApoderado(null);
//			egreso.setIntItemArchivoApoderado(null);
//			egreso.setIntItemHistoricoApoderado(null);			
//			egreso.setIntParaTipoGiro(null);
//			egreso.setIntItemArchivoGiro(null);
//			egreso.setIntItemHistoricoGiro(null);
//			
//			for(Object o : listaEgresoDetalleInterfaz){
//				EgresoDetalleInterfaz egresoDetalleInterfaz = (EgresoDetalleInterfaz)o;
//				EgresoDetalle egresoDetalle = generarEgresoDetalleCheque(socioEstructura, egresoDetalleInterfaz, expedienteLiquidacion, 
//						bancoCuenta, usuario);
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
//			for(Object o : listaEgresoDetalleInterfaz){
//				EgresoDetalleInterfaz egresoDetalleInterfaz = (EgresoDetalleInterfaz)o;
//				LibroDiarioDetalle libroDiarioDetalle = generarLibroDiarioDetalleCheque(socioEstructura, egresoDetalleInterfaz, expedienteLiquidacion,
//						bancoCuenta, null);
//				log.info(libroDiarioDetalle);
//				if(libroDiarioDetalle!=null)	libroDiario.getListaLibroDiarioDetalle().add(libroDiarioDetalle);
//			}
//			
//			LibroDiarioDetalle libroDiarioDetalle = generarLibroDiarioDetalleCheque(socioEstructura, null, expedienteLiquidacion, 
//					bancoCuenta,	bdTotalEgresoDetalleInterfaz);
//			log.info(libroDiarioDetalle);
//			if(libroDiarioDetalle!=null)	libroDiario.getListaLibroDiarioDetalle().add(libroDiarioDetalle);
//			
//			expedienteLiquidacion.setListaEgresoDetalleInterfaz(listaEgresoDetalleInterfaz);
//			egreso.setLibroDiario(libroDiario);
//			egreso.setControlFondosFijos(null);
//			
//		}catch(Exception e){
//			throw new BusinessException(e);
//		}
//		return egreso;
//	}	
	
	public Egreso generarEgresoLiquidacionTransferencia(ExpedienteLiquidacion expedienteLiquidacion, Bancocuenta bancoCuentaOrigen, Usuario usuario, 
		Integer intNumeroTransferencia, Integer intTipoDocumentoValidar, CuentaBancaria cuentaBancariaDestino) throws BusinessException{
		Egreso egreso = new Egreso();
		try{
			Persona personaGirar = expedienteLiquidacion.getPersonaGirar();
			Integer intIdEmpresa = expedienteLiquidacion.getId().getIntPersEmpresaPk();
			List<EgresoDetalleInterfaz> listaEgresoDetalleInterfaz = expedienteLiquidacion.getListaEgresoDetalleInterfaz();
			boolean esUltimoBeneficiarioAGirar = expedienteLiquidacion.isEsUltimoBeneficiarioAGirar();	
			Integer intIdPersonaBeneficiarioSeleccionar = expedienteLiquidacion.getIntIdPersonaBeneficiarioGirar();
			String	strGlosaEgreso = expedienteLiquidacion.getStrGlosaEgreso();
			
			BigDecimal bdTotalEgresoDetalleInterfaz = cargarTotalEgresoDetalleInterfaz(listaEgresoDetalleInterfaz);			
			SocioEstructura socioEstructura = obtenerSocioEstructura(personaGirar, intIdEmpresa);
			Modelo modelo = obtenerModelo(intIdEmpresa);			
			cargarModeloDetalleEnEgresoDetalleInterfaz(modelo, listaEgresoDetalleInterfaz);
			
			egreso.getId().setIntPersEmpresaEgreso(intIdEmpresa);
			egreso.setIntParaTipoOperacion(Constante.PARAM_T_TIPODEOPERACION_EGRESO);
			egreso.setIntParaFormaPago(Constante.PARAM_T_FORMAPAGOEGRESO_EFECTIVO);
			egreso.setIntParaDocumentoGeneral(intTipoDocumentoValidar);
			egreso.setIntItemPeriodoEgreso(obtenerPeriodoActual());
			egreso.setIntItemEgreso(null);
			egreso.setIntSucuIdSucursal(usuario.getSucursal().getId().getIntIdSucursal());
			egreso.setIntSudeIdSubsucursal(usuario.getSubSucursal().getId().getIntIdSubSucursal());
			if(esUltimoBeneficiarioAGirar){
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
			egreso.setIntCuentaGirado(null);
			egreso.setIntCuentaGirado(null);
			//20.05.2014 jchavez cambio de tipo de dato
			egreso.setStrPersCuentaBancariaGirado(cuentaBancariaDestino.getStrNroCuentaBancaria());
//			egreso.setIntPersCuentaBancariaGirado(cuentaBancariaDestino.getId().getIntIdCuentaBancaria());
			egreso.setIntPersEmpresaBeneficiario(intIdEmpresa);
			egreso.setIntPersPersonaBeneficiario(intIdPersonaBeneficiarioSeleccionar);
			egreso.setIntPersCuentaBancariaBeneficiario(null);
			egreso.setIntPersPersonaApoderado(null);
			egreso.setIntPersEmpresaApoderado(null);			
			egreso.setBdMontoTotal(bdTotalEgresoDetalleInterfaz);
			egreso.setStrObservacion(strGlosaEgreso);
			egreso.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			egreso.setTsFechaRegistro(obtenerFechaActual());
			egreso.setIntPersEmpresaUsuario(intIdEmpresa);
			egreso.setIntPersPersonaUsuario(usuario.getIntPersPersonaPk());
			egreso.setIntPersEmpresaEgresoAnula(null);
			egreso.setIntPersPersonaEgresoAnula(null);
			egreso.setIntParaTipoApoderado(null);
			egreso.setIntItemArchivoApoderado(null);
			egreso.setIntItemHistoricoApoderado(null);			
			egreso.setIntParaTipoGiro(null);
			egreso.setIntItemArchivoGiro(null);
			egreso.setIntItemHistoricoGiro(null);
			
			for(Object o : listaEgresoDetalleInterfaz){
				EgresoDetalleInterfaz egresoDetalleInterfaz = (EgresoDetalleInterfaz)o;
				EgresoDetalle egresoDetalle = generarEgresoDetalleCheque(socioEstructura, egresoDetalleInterfaz, expedienteLiquidacion, 
						bancoCuentaOrigen, usuario);
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
			
			for(Object o : listaEgresoDetalleInterfaz){
				EgresoDetalleInterfaz egresoDetalleInterfaz = (EgresoDetalleInterfaz)o;
				LibroDiarioDetalle libroDiarioDetalle = generarLibroDiarioDetalleCheque(socioEstructura, egresoDetalleInterfaz, expedienteLiquidacion,
						bancoCuentaOrigen, null);
				log.info(libroDiarioDetalle);
				if(libroDiarioDetalle!=null)	libroDiario.getListaLibroDiarioDetalle().add(libroDiarioDetalle);
			}
			
			LibroDiarioDetalle libroDiarioDetalle = generarLibroDiarioDetalleCheque(socioEstructura, null, expedienteLiquidacion, 
					bancoCuentaOrigen,	bdTotalEgresoDetalleInterfaz);
			log.info(libroDiarioDetalle);
			if(libroDiarioDetalle!=null)	libroDiario.getListaLibroDiarioDetalle().add(libroDiarioDetalle);
			
			expedienteLiquidacion.setListaEgresoDetalleInterfaz(listaEgresoDetalleInterfaz);
			egreso.setLibroDiario(libroDiario);
			egreso.setControlFondosFijos(null);
			
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
	 * GIRO LIQUIDACION - POR SERVICIO. USADO TBM EN FONDO DE CAMBIO
	 */
	
	/**
	 * Creacion: JCHAVEZ 28.01.2014
	 * Generación del egreso de Giro de Liquidación, usado en GiroLiquidacionController, método grabar()
	 * @param expedienteLiquidacion
	 * @param controlFondosFijos
	 * @param usuario
	 * @return
	 * @throws BusinessException
	 */
	public Egreso generarEgresoGiroLiquidacion(ExpedienteLiquidacion expedienteLiquidacion, ControlFondosFijos controlFondosFijos, Usuario usuario) 
		throws BusinessException{
		Egreso egreso = new Egreso();
		Calendar cal = Calendar.getInstance();
		Integer anioActual = cal.get(Calendar.YEAR);
		try{
			ModeloFacadeRemote modeloFacade = (ModeloFacadeRemote) EJBFactory.getRemote(ModeloFacadeRemote.class);
			CuentaFacadeRemote cuentaFacade = (CuentaFacadeRemote) EJBFactory.getRemote(CuentaFacadeRemote.class);
			//Expediente Liquidacion
			BeneficiarioLiquidacion beneficiarioLiquidacionSeleccionado =  expedienteLiquidacion.getBeneficiarioLiquidacionGirar();
			Persona personaGirar = expedienteLiquidacion.getPersonaGirar();
			Integer intIdEmpresa = expedienteLiquidacion.getId().getIntPersEmpresaPk();
			List<EgresoDetalleInterfaz> listaEgresoDetalleInterfaz = expedienteLiquidacion.getListaEgresoDetalleInterfaz();
			boolean esUltimoBeneficiarioAGirar = expedienteLiquidacion.isEsUltimoBeneficiarioAGirar();	
			Integer intIdPersonaBeneficiarioSeleccionar = expedienteLiquidacion.getIntIdPersonaBeneficiarioGirar();
			String	strGlosaEgreso = expedienteLiquidacion.getStrGlosaEgreso();
			Persona personaApoderado = beneficiarioLiquidacionSeleccionado.getPersonaApoderado();
			Archivo archivoCartaPoder = beneficiarioLiquidacionSeleccionado.getArchivoCartaPoder();
			BigDecimal bdTotalEgresoDetalleInterfaz = cargarTotalEgresoDetalleInterfaz(listaEgresoDetalleInterfaz);			
			
			//Se obtiene datos de la cuenta a liquidar
//			CuentaId cId = new CuentaId();
//			cId.setIntPersEmpresaPk(beneficiarioLiquidacionSeleccionado.getId().getIntPersEmpresa());
//			cId.setIntCuenta(beneficiarioLiquidacionSeleccionado.getId().getIntCuenta());
			Cuenta cuenta = cuentaFacade.getCuentaPorPkYSituacion(expedienteLiquidacion.getListaEgresoDetalleInterfaz().get(0).getExpedienteLiquidacionDetalle().getCuenta());
			expedienteLiquidacion.setCuentaLiquidar(expedienteLiquidacion.getListaEgresoDetalleInterfaz().get(0).getExpedienteLiquidacionDetalle().getCuenta());
			log.info("Tipo de moneda: "+cuenta.getIntParaTipoMonedaCod());
			
			// Se obtiene SocioEstructura por persona a Girar Previsión
			SocioEstructura socioEstructura = obtenerSocioEstructura(personaGirar, intIdEmpresa);
			if (socioEstructura==null) {
				egreso.setIntErrorGeneracionEgreso(1);
				egreso.setStrMsgErrorGeneracionEgreso("Ocurrio un error durante el proceso de giro de préstamos. Socio Estructura retorna nulo.");
				return egreso;
			}
//			Modelo modelo = obtenerModelo(intIdEmpresa);
			
			List<ModeloDetalleNivelComp> listaModeloGiro = new ArrayList<ModeloDetalleNivelComp>();
			//Se recupera Modelo Detalle Nivel, tanto para un préstamo nuevo como un represtamo (Parametro 151)
			if (expedienteLiquidacion.getListaExpedienteLiquidacionDetalle()!=null && !expedienteLiquidacion.getListaExpedienteLiquidacionDetalle().isEmpty()) {
				for (ExpedienteLiquidacionDetalle expLiqDet : expedienteLiquidacion.getListaExpedienteLiquidacionDetalle()) {
					ModeloDetalleNivel modeloFiltro = new ModeloDetalleNivel();
					modeloFiltro.setId(new ModeloDetalleNivelId());
					modeloFiltro.getId().setIntEmpresaPk(intIdEmpresa);
					modeloFiltro.getId().setIntPersEmpresaCuenta(intIdEmpresa);
					modeloFiltro.getId().setIntContPeriodoCuenta(anioActual);
					modeloFiltro.setIntDatoTablas(Integer.valueOf(Constante.PARAM_T_TIPOCUENTA));
					modeloFiltro.setIntDatoArgumento(expLiqDet.getId().getIntItemCuentaConcepto());
					modeloFiltro.setIntTipoModeloContable(Constante.PARAM_T_TIPOMODELOCONTABLE_LIQUIDACIONCUENTA);
					List<ModeloDetalleNivelComp> listaModeloGiroTemp = modeloFacade.getModeloGiroPrevision(modeloFiltro);
					for (ModeloDetalleNivelComp o : listaModeloGiroTemp) {
						o.setIntDatoArgumento(expLiqDet.getId().getIntItemCuentaConcepto());
					}
					listaModeloGiro.addAll(listaModeloGiroTemp);
				}
			}
			
			//Se verifica que exista el modelo detalle nivel
			if (listaModeloGiro==null || listaModeloGiro.isEmpty()) {
				egreso.setIntErrorGeneracionEgreso(1);
				egreso.setStrMsgErrorGeneracionEgreso("Ocurrio un error durante el proceso de giro de préstamos. Modelo Detalle Nivel retorna nulo.");
				return egreso;
			}	
			
			//Generación del tipo de cambio de la solicitud y de la fecha actual
			TipoCambio tipoCambioActual = null;
			TipoCambio tipoCambioSolicitud = null;
			if(cuenta.getIntParaTipoMonedaCod().compareTo(Constante.PARAM_T_TIPOMONEDA_SOLES)==0){
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
				tipoCambioActual = obtenerTipoCambioActual(cuenta.getIntParaTipoMonedaCod(), intIdEmpresa);			
				tipoCambioSolicitud = obtenerTipoCambioSolicitud(expedienteLiquidacion, cuenta.getIntParaTipoMonedaCod(), intIdEmpresa);
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
			if(esUltimoBeneficiarioAGirar){
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
			egreso.setIntCuentaGirado(expedienteLiquidacion.getCuentaLiquidar().getId().getIntCuenta());
			//20.05.2014 jchavez cambio de tipo de dato
			egreso.setStrPersCuentaBancariaGirado(null);
//			egreso.setIntPersCuentaBancariaGirado(null);
			egreso.setIntPersEmpresaBeneficiario(intIdEmpresa);
			egreso.setIntPersPersonaBeneficiario(intIdPersonaBeneficiarioSeleccionar);
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
			for (ExpedienteLiquidacionDetalle expLiqDet : expedienteLiquidacion.getListaExpedienteLiquidacionDetalle()) {
				EgresoDetalle egresoDetalle = generarEgresoDetalleLiquidacion(socioEstructura, listaModeloGiro, expedienteLiquidacion, expLiqDet, 
						controlFondosFijos, usuario, tipoCambioSolicitud, tipoCambioActual);
				log.info("Egreso Detalle generado: "+egresoDetalle);
				if(egresoDetalle!=null) egreso.getListaEgresoDetalle().add(egresoDetalle);
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
			
//			for(Object o : listaEgresoDetalleInterfaz){
//				EgresoDetalleInterfaz egresoDetalleInterfaz = (EgresoDetalleInterfaz)o;
//				LibroDiarioDetalle libroDiarioDetalle = generarLibroDiarioDetalle(socioEstructura, egresoDetalleInterfaz, expedienteLiquidacion,
//						controlFondosFijos, null);
//				log.info(libroDiarioDetalle);
//				if(libroDiarioDetalle!=null)	libroDiario.getListaLibroDiarioDetalle().add(libroDiarioDetalle);
//			}
			BigDecimal bdMontoDebeHaberTotal = BigDecimal.ZERO;
			libroDiario.setListaLibroDiarioDetalle(new ArrayList<LibroDiarioDetalle>());
			//Generando el Libro Diario Detalle del Giro			
			for (EgresoDetalle egresoDetalle : egreso.getListaEgresoDetalle()) {
				BigDecimal bdMontoDebeHaber = egresoDetalle.getBdMontoAbono()!=null
													?egresoDetalle.getBdMontoAbono()
													:egresoDetalle.getBdMontoCargo();
				LibroDiarioDetalle libroDiarioDetalle = generarLibroDiarioDetalleLiquidacion(socioEstructura, listaModeloGiro, 
						expedienteLiquidacion, controlFondosFijos, tipoCambioActual, bdMontoDebeHaber,egresoDetalle);
				libroDiario.getListaLibroDiarioDetalle().add(libroDiarioDetalle);
				bdMontoDebeHaberTotal = bdMontoDebeHaberTotal.add(bdMontoDebeHaber);
			}
			
			//Generando el Libro Diario Detalle fondo de cambio
			LibroDiarioDetalle libroDiarioDetalleFondoFijo = generarCFFLibroDiarioDetalle(expedienteLiquidacion, controlFondosFijos, tipoCambioActual,bdMontoDebeHaberTotal);			
			if(libroDiarioDetalleFondoFijo != null) libroDiario.getListaLibroDiarioDetalle().add(libroDiarioDetalleFondoFijo);
			


			
//			LibroDiarioDetalle libroDiarioDetalle = generarLibroDiarioDetalle(socioEstructura, null, expedienteLiquidacion, 
//					controlFondosFijos,	bdTotalEgresoDetalleInterfaz);
//			log.info(libroDiarioDetalle);
//			if(libroDiarioDetalle!=null)	libroDiario.getListaLibroDiarioDetalle().add(libroDiarioDetalle);
//			
			expedienteLiquidacion.setListaEgresoDetalleInterfaz(listaEgresoDetalleInterfaz);
			egreso.setLibroDiario(libroDiario);
			egreso.setControlFondosFijos(controlFondosFijos);
			
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return egreso;
	}	
	
	/**
	 * GIRO LIQUIDACION POR BANCO(CHEQUE). 
	 */
	
	/**
	 * modificacion jchavez 15.05.2014
	 * @param expedienteLiquidacion
	 * @param bancoCuenta
	 * @param usuario
	 * @return
	 * @throws BusinessException
	 */
	public Egreso generarEgresoLiquidacionCheque(ExpedienteLiquidacion expedienteLiquidacion, Bancocuenta bancoCuenta, Usuario usuario,
			Integer intNumeroCheque, Integer intTipoDocumentoValidar) throws BusinessException{
		Egreso egreso = new Egreso();
		Calendar cal = Calendar.getInstance();
		Integer anioActual = cal.get(Calendar.YEAR);
		try{
			ModeloFacadeRemote modeloFacade = (ModeloFacadeRemote) EJBFactory.getRemote(ModeloFacadeRemote.class);
			CuentaFacadeRemote cuentaFacade = (CuentaFacadeRemote) EJBFactory.getRemote(CuentaFacadeRemote.class);
			//Expediente Liquidacion
			BeneficiarioLiquidacion beneficiarioLiquidacionSeleccionado =  expedienteLiquidacion.getBeneficiarioLiquidacionGirar();
			Persona personaGirar = expedienteLiquidacion.getPersonaGirar();
			Integer intIdEmpresa = expedienteLiquidacion.getId().getIntPersEmpresaPk();
			List<EgresoDetalleInterfaz> listaEgresoDetalleInterfaz = expedienteLiquidacion.getListaEgresoDetalleInterfaz();
			boolean esUltimoBeneficiarioAGirar = expedienteLiquidacion.isEsUltimoBeneficiarioAGirar();	
			Integer intIdPersonaBeneficiarioSeleccionar = expedienteLiquidacion.getIntIdPersonaBeneficiarioGirar();
			String	strGlosaEgreso = expedienteLiquidacion.getStrGlosaEgreso();
			Persona personaApoderado = beneficiarioLiquidacionSeleccionado.getPersonaApoderado();
			Archivo archivoCartaPoder = beneficiarioLiquidacionSeleccionado.getArchivoCartaPoder();
			BigDecimal bdTotalEgresoDetalleInterfaz = cargarTotalEgresoDetalleInterfaz(listaEgresoDetalleInterfaz);			
			
			//Se obtiene datos de la cuenta a liquidar
			Cuenta cuenta = cuentaFacade.getCuentaPorPkYSituacion(expedienteLiquidacion.getListaEgresoDetalleInterfaz().get(0).getExpedienteLiquidacionDetalle().getCuenta());
			expedienteLiquidacion.setCuentaLiquidar(expedienteLiquidacion.getListaEgresoDetalleInterfaz().get(0).getExpedienteLiquidacionDetalle().getCuenta());
			log.info("Tipo de moneda: "+cuenta.getIntParaTipoMonedaCod());
			
			// Se obtiene SocioEstructura por persona a Girar Previsión
			SocioEstructura socioEstructura = obtenerSocioEstructura(personaGirar, intIdEmpresa);
			if (socioEstructura==null) {
				egreso.setIntErrorGeneracionEgreso(1);
				egreso.setStrMsgErrorGeneracionEgreso("Ocurrio un error durante el proceso de giro de préstamos. Socio Estructura retorna nulo.");
				return egreso;
			}
			
			List<ModeloDetalleNivelComp> listaModeloGiro = new ArrayList<ModeloDetalleNivelComp>();
			//Se recupera Modelo Detalle Nivel, tanto para un préstamo nuevo como un represtamo (Parametro 151)
			if (expedienteLiquidacion.getListaExpedienteLiquidacionDetalle()!=null && !expedienteLiquidacion.getListaExpedienteLiquidacionDetalle().isEmpty()) {
				for (ExpedienteLiquidacionDetalle expLiqDet : expedienteLiquidacion.getListaExpedienteLiquidacionDetalle()) {
					ModeloDetalleNivel modeloFiltro = new ModeloDetalleNivel();
					modeloFiltro.setId(new ModeloDetalleNivelId());
					modeloFiltro.getId().setIntEmpresaPk(intIdEmpresa);
					modeloFiltro.getId().setIntPersEmpresaCuenta(intIdEmpresa);
					modeloFiltro.getId().setIntContPeriodoCuenta(anioActual);
					modeloFiltro.setIntDatoTablas(Integer.valueOf(Constante.PARAM_T_TIPOCUENTA));
					modeloFiltro.setIntDatoArgumento(expLiqDet.getId().getIntItemCuentaConcepto());
					modeloFiltro.setIntTipoModeloContable(Constante.PARAM_T_TIPOMODELOCONTABLE_LIQUIDACIONCUENTA);
					List<ModeloDetalleNivelComp> listaModeloGiroTemp = modeloFacade.getModeloGiroPrevision(modeloFiltro);
					for (ModeloDetalleNivelComp o : listaModeloGiroTemp) {
						o.setIntDatoArgumento(expLiqDet.getId().getIntItemCuentaConcepto());
					}
					listaModeloGiro.addAll(listaModeloGiroTemp);
				}
			}
			
			//Se verifica que exista el modelo detalle nivel
			if (listaModeloGiro==null || listaModeloGiro.isEmpty()) {
				egreso.setIntErrorGeneracionEgreso(1);
				egreso.setStrMsgErrorGeneracionEgreso("Ocurrio un error durante el proceso de giro de préstamos. Modelo Detalle Nivel retorna nulo.");
				return egreso;
			}	
			
			//Generación del tipo de cambio de la solicitud y de la fecha actual
			TipoCambio tipoCambioActual = null;
			TipoCambio tipoCambioSolicitud = null;
			if(cuenta.getIntParaTipoMonedaCod().compareTo(Constante.PARAM_T_TIPOMONEDA_SOLES)==0){
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
				tipoCambioActual = obtenerTipoCambioActual(cuenta.getIntParaTipoMonedaCod(), intIdEmpresa);			
				tipoCambioSolicitud = obtenerTipoCambioSolicitud(expedienteLiquidacion, cuenta.getIntParaTipoMonedaCod(), intIdEmpresa);
			}
			
			//Generación del Egreso (cabecera)
			egreso.getId().setIntPersEmpresaEgreso(intIdEmpresa);
			egreso.setIntParaTipoOperacion(Constante.PARAM_T_TIPODEOPERACION_EGRESO);
//			egreso.setIntParaFormaPago(Constante.PARAM_T_FORMAPAGOEGRESO_EFECTIVO);
//			egreso.setIntParaDocumentoGeneral(intTipoDocumentoValidar);
			egreso.setIntParaFormaPago(Constante.PARAM_T_FORMAPAGOEGRESO_CHEQUE);
			egreso.setIntParaDocumentoGeneral(intTipoDocumentoValidar);
			egreso.setIntItemPeriodoEgreso(obtenerPeriodoActual());
			egreso.setIntItemEgreso(null);
			egreso.setIntSucuIdSucursal(usuario.getSucursal().getId().getIntIdSucursal());
			egreso.setIntSudeIdSubsucursal(usuario.getSubSucursal().getId().getIntIdSubSucursal());
			if(esUltimoBeneficiarioAGirar){
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
			/*
			egreso.setIntItemBancoFondo(null);
			egreso.setIntItemBancoCuenta(null);
			*/
			egreso.setIntItemBancoCuentaCheque(null);
			egreso.setIntNumeroPlanilla(null);
			egreso.setIntNumeroCheque(intNumeroCheque);
			egreso.setIntNumeroTransferencia(null);
			egreso.setTsFechaPagoDiferido(null);
			egreso.setIntPersEmpresaGirado(intIdEmpresa);
			egreso.setIntPersPersonaGirado(personaGirar.getIntIdPersona());
			egreso.setIntCuentaGirado(expedienteLiquidacion.getCuentaLiquidar().getId().getIntCuenta());
			//20.05.2014 jchavez cambio de tipo de dato
			egreso.setStrPersCuentaBancariaGirado(null);
//			egreso.setIntPersCuentaBancariaGirado(null);
			egreso.setIntPersEmpresaBeneficiario(intIdEmpresa);
			egreso.setIntPersPersonaBeneficiario(intIdPersonaBeneficiarioSeleccionar);
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
			for (ExpedienteLiquidacionDetalle expLiqDet : expedienteLiquidacion.getListaExpedienteLiquidacionDetalle()) {
				EgresoDetalle egresoDetalle = generarEgresoDetalleLiquidacion(socioEstructura, listaModeloGiro, expedienteLiquidacion, expLiqDet, 
						null, usuario, tipoCambioSolicitud, tipoCambioActual);
				log.info("Egreso Detalle generado: "+egresoDetalle);
				if(egresoDetalle!=null) egreso.getListaEgresoDetalle().add(egresoDetalle);
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
//			libroDiario.setIntParaTipoDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_RENDICION);
			libroDiario.setIntParaTipoDocumentoGeneral(intTipoDocumentoValidar);
			
			BigDecimal bdMontoDebeHaberTotal = BigDecimal.ZERO;
			libroDiario.setListaLibroDiarioDetalle(new ArrayList<LibroDiarioDetalle>());
			//Generando el Libro Diario Detalle del Giro			
			for (EgresoDetalle egresoDetalle : egreso.getListaEgresoDetalle()) {
				BigDecimal bdMontoDebeHaber = egresoDetalle.getBdMontoAbono()!=null
													?egresoDetalle.getBdMontoAbono()
													:egresoDetalle.getBdMontoCargo();
				LibroDiarioDetalle libroDiarioDetalle = generarLibroDiarioDetalleLiquidacion(socioEstructura, listaModeloGiro, 
						expedienteLiquidacion, null, tipoCambioActual, bdMontoDebeHaber,egresoDetalle);
				libroDiario.getListaLibroDiarioDetalle().add(libroDiarioDetalle);
				bdMontoDebeHaberTotal = bdMontoDebeHaberTotal.add(bdMontoDebeHaber);
			}
			
			//Generando el Libro Diario Detalle fondo de cambio
			LibroDiarioDetalle libroDiarioDetalleBancoPorCheque = generarBancoLibroDiarioDetalle(expedienteLiquidacion, bancoCuenta, tipoCambioActual,bdMontoDebeHaberTotal, intTipoDocumentoValidar);			
			if(libroDiarioDetalleBancoPorCheque != null) libroDiario.getListaLibroDiarioDetalle().add(libroDiarioDetalleBancoPorCheque);
			
			expedienteLiquidacion.setListaEgresoDetalleInterfaz(listaEgresoDetalleInterfaz);
			egreso.setLibroDiario(libroDiario);
			
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return egreso;
	}	
	
	/**
	 * GIRO LIQUIDACION POR BANCO(TRANSFERENCIA TERCEROS). 
	 */
	
	/**
	 * 
	 * @param expedienteLiquidacion
	 * @param bancoCuenta
	 * @param usuario
	 * @return
	 * @throws BusinessException
	 */
	public Egreso generarEgresoLiquidacionTransferenciaTerceros(ExpedienteLiquidacion expedienteLiquidacion, Bancocuenta bancoCuenta, Usuario usuario,
			Integer intNumeroTransferencia, Integer intTipoDocumentoValidar, CuentaBancaria cuentaBancariaDestino) throws BusinessException{
		Egreso egreso = new Egreso();
		Calendar cal = Calendar.getInstance();
		Integer anioActual = cal.get(Calendar.YEAR);
		try{
			ModeloFacadeRemote modeloFacade = (ModeloFacadeRemote) EJBFactory.getRemote(ModeloFacadeRemote.class);
			CuentaFacadeRemote cuentaFacade = (CuentaFacadeRemote) EJBFactory.getRemote(CuentaFacadeRemote.class);
			//Expediente Liquidacion
			BeneficiarioLiquidacion beneficiarioLiquidacionSeleccionado =  expedienteLiquidacion.getBeneficiarioLiquidacionGirar();
			Persona personaGirar = expedienteLiquidacion.getPersonaGirar();
			Integer intIdEmpresa = expedienteLiquidacion.getId().getIntPersEmpresaPk();
			List<EgresoDetalleInterfaz> listaEgresoDetalleInterfaz = expedienteLiquidacion.getListaEgresoDetalleInterfaz();
			boolean esUltimoBeneficiarioAGirar = expedienteLiquidacion.isEsUltimoBeneficiarioAGirar();	
			Integer intIdPersonaBeneficiarioSeleccionar = expedienteLiquidacion.getIntIdPersonaBeneficiarioGirar();
			String	strGlosaEgreso = expedienteLiquidacion.getStrGlosaEgreso();
			Persona personaApoderado = beneficiarioLiquidacionSeleccionado.getPersonaApoderado();
			Archivo archivoCartaPoder = beneficiarioLiquidacionSeleccionado.getArchivoCartaPoder();
			BigDecimal bdTotalEgresoDetalleInterfaz = cargarTotalEgresoDetalleInterfaz(listaEgresoDetalleInterfaz);			
			
			//Se obtiene datos de la cuenta a liquidar
			Cuenta cuenta = cuentaFacade.getCuentaPorPkYSituacion(expedienteLiquidacion.getListaEgresoDetalleInterfaz().get(0).getExpedienteLiquidacionDetalle().getCuenta());
			expedienteLiquidacion.setCuentaLiquidar(expedienteLiquidacion.getListaEgresoDetalleInterfaz().get(0).getExpedienteLiquidacionDetalle().getCuenta());
			log.info("Tipo de moneda: "+cuenta.getIntParaTipoMonedaCod());
			
			// Se obtiene SocioEstructura por persona a Girar Previsión
			SocioEstructura socioEstructura = obtenerSocioEstructura(personaGirar, intIdEmpresa);
			if (socioEstructura==null) {
				egreso.setIntErrorGeneracionEgreso(1);
				egreso.setStrMsgErrorGeneracionEgreso("Ocurrio un error durante el proceso de giro de préstamos. Socio Estructura retorna nulo.");
				return egreso;
			}
			
			List<ModeloDetalleNivelComp> listaModeloGiro = new ArrayList<ModeloDetalleNivelComp>();
			//Se recupera Modelo Detalle Nivel, tanto para un préstamo nuevo como un represtamo (Parametro 151)
			if (expedienteLiquidacion.getListaExpedienteLiquidacionDetalle()!=null && !expedienteLiquidacion.getListaExpedienteLiquidacionDetalle().isEmpty()) {
				for (ExpedienteLiquidacionDetalle expLiqDet : expedienteLiquidacion.getListaExpedienteLiquidacionDetalle()) {
					ModeloDetalleNivel modeloFiltro = new ModeloDetalleNivel();
					modeloFiltro.setId(new ModeloDetalleNivelId());
					modeloFiltro.getId().setIntEmpresaPk(intIdEmpresa);
					modeloFiltro.getId().setIntPersEmpresaCuenta(intIdEmpresa);
					modeloFiltro.getId().setIntContPeriodoCuenta(anioActual);
					modeloFiltro.setIntDatoTablas(Integer.valueOf(Constante.PARAM_T_TIPOCUENTA));
					modeloFiltro.setIntDatoArgumento(expLiqDet.getId().getIntItemCuentaConcepto());
					modeloFiltro.setIntTipoModeloContable(Constante.PARAM_T_TIPOMODELOCONTABLE_LIQUIDACIONCUENTA);
					List<ModeloDetalleNivelComp> listaModeloGiroTemp = modeloFacade.getModeloGiroPrevision(modeloFiltro);
					for (ModeloDetalleNivelComp o : listaModeloGiroTemp) {
						o.setIntDatoArgumento(expLiqDet.getId().getIntItemCuentaConcepto());
					}
					listaModeloGiro.addAll(listaModeloGiroTemp);
				}
			}
			
			//Se verifica que exista el modelo detalle nivel
			if (listaModeloGiro==null || listaModeloGiro.isEmpty()) {
				egreso.setIntErrorGeneracionEgreso(1);
				egreso.setStrMsgErrorGeneracionEgreso("Ocurrio un error durante el proceso de giro de préstamos. Modelo Detalle Nivel retorna nulo.");
				return egreso;
			}	
			
			//Generación del tipo de cambio de la solicitud y de la fecha actual
			TipoCambio tipoCambioActual = null;
			TipoCambio tipoCambioSolicitud = null;
			if(cuenta.getIntParaTipoMonedaCod().compareTo(Constante.PARAM_T_TIPOMONEDA_SOLES)==0){
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
				tipoCambioActual = obtenerTipoCambioActual(cuenta.getIntParaTipoMonedaCod(), intIdEmpresa);			
				tipoCambioSolicitud = obtenerTipoCambioSolicitud(expedienteLiquidacion, cuenta.getIntParaTipoMonedaCod(), intIdEmpresa);
			}
			
			//Generación del Egreso (cabecera)
			egreso.getId().setIntPersEmpresaEgreso(intIdEmpresa);
			egreso.setIntParaTipoOperacion(Constante.PARAM_T_TIPODEOPERACION_EGRESO);
//			egreso.setIntParaFormaPago(Constante.PARAM_T_FORMAPAGOEGRESO_EFECTIVO);
//			egreso.setIntParaDocumentoGeneral(intTipoDocumentoValidar);
			egreso.setIntParaFormaPago(Constante.PARAM_T_FORMAPAGOEGRESO_TRANSFERENCIA);
			egreso.setIntParaDocumentoGeneral(intTipoDocumentoValidar);
			egreso.setIntItemPeriodoEgreso(obtenerPeriodoActual());
			egreso.setIntItemEgreso(null);
			egreso.setIntSucuIdSucursal(usuario.getSucursal().getId().getIntIdSucursal());
			egreso.setIntSudeIdSubsucursal(usuario.getSubSucursal().getId().getIntIdSubSucursal());
			if(esUltimoBeneficiarioAGirar){
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
			/*
			egreso.setIntItemBancoFondo(null);
			egreso.setIntItemBancoCuenta(null);
			*/
			egreso.setIntItemBancoCuentaCheque(null);
			egreso.setIntNumeroPlanilla(null);
			egreso.setIntNumeroCheque(null);
			egreso.setIntNumeroTransferencia(intNumeroTransferencia);
			egreso.setTsFechaPagoDiferido(null);
			egreso.setIntPersEmpresaGirado(intIdEmpresa);
			egreso.setIntPersPersonaGirado(personaGirar.getIntIdPersona());
			egreso.setIntCuentaGirado(expedienteLiquidacion.getCuentaLiquidar().getId().getIntCuenta());
			//20.05.2014 jchavez cambio de tipo de dato
			egreso.setStrPersCuentaBancariaGirado(cuentaBancariaDestino!=null?cuentaBancariaDestino.getStrNroCuentaBancaria():null);
//			egreso.setIntPersCuentaBancariaGirado(cuentaBancariaDestino!=null?Integer.parseInt(cuentaBancariaDestino.getStrNroCuentaBancaria()):null);
			egreso.setIntPersEmpresaBeneficiario(intIdEmpresa);
			egreso.setIntPersPersonaBeneficiario(intIdPersonaBeneficiarioSeleccionar);
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
			for (ExpedienteLiquidacionDetalle expLiqDet : expedienteLiquidacion.getListaExpedienteLiquidacionDetalle()) {
				EgresoDetalle egresoDetalle = generarEgresoDetalleLiquidacion(socioEstructura, listaModeloGiro, expedienteLiquidacion, expLiqDet, 
						null, usuario, tipoCambioSolicitud, tipoCambioActual);
				log.info("Egreso Detalle generado: "+egresoDetalle);
				if(egresoDetalle!=null) egreso.getListaEgresoDetalle().add(egresoDetalle);
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
//			libroDiario.setIntParaTipoDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_RENDICION);
			libroDiario.setIntParaTipoDocumentoGeneral(intTipoDocumentoValidar);
			
			BigDecimal bdMontoDebeHaberTotal = BigDecimal.ZERO;
			libroDiario.setListaLibroDiarioDetalle(new ArrayList<LibroDiarioDetalle>());
			//Generando el Libro Diario Detalle del Giro			
			for (EgresoDetalle egresoDetalle : egreso.getListaEgresoDetalle()) {
				BigDecimal bdMontoDebeHaber = egresoDetalle.getBdMontoAbono()!=null
													?egresoDetalle.getBdMontoAbono()
													:egresoDetalle.getBdMontoCargo();
				LibroDiarioDetalle libroDiarioDetalle = generarLibroDiarioDetalleLiquidacion(socioEstructura, listaModeloGiro, 
						expedienteLiquidacion, null, tipoCambioActual, bdMontoDebeHaber,egresoDetalle);
				libroDiario.getListaLibroDiarioDetalle().add(libroDiarioDetalle);
				bdMontoDebeHaberTotal = bdMontoDebeHaberTotal.add(bdMontoDebeHaber);
			}
			
			//Generando el Libro Diario Detalle fondo de cambio
			LibroDiarioDetalle libroDiarioDetalleBancoPorCheque = generarBancoLibroDiarioDetalle(expedienteLiquidacion, bancoCuenta, tipoCambioActual,bdMontoDebeHaberTotal, intTipoDocumentoValidar);			
			if(libroDiarioDetalleBancoPorCheque != null) libroDiario.getListaLibroDiarioDetalle().add(libroDiarioDetalleBancoPorCheque);
			
			expedienteLiquidacion.setListaEgresoDetalleInterfaz(listaEgresoDetalleInterfaz);
			egreso.setLibroDiario(libroDiario);
			
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return egreso;
	}	
	
	/**
	 * JCHAVEZ 29.01.2014
	 * Genera el Egreso Detalle para Giro Liquidacion
	 * @param socioEstructura
	 * @param o
	 * @param expedienteLiquidacion
	 * @param expedienteLiquidacionDetalle
	 * @param controlFondosFijos
	 * @param usuario
	 * @param tipoCambioSolicitud
	 * @param tipoCambioActual
	 * @return
	 * @throws Exception
	 */
	private EgresoDetalle generarEgresoDetalleLiquidacion(SocioEstructura socioEstructura, List<ModeloDetalleNivelComp> o, 
			ExpedienteLiquidacion expedienteLiquidacion, ExpedienteLiquidacionDetalle expedienteLiquidacionDetalle, ControlFondosFijos controlFondosFijos, Usuario usuario, TipoCambio tipoCambioSolicitud, 
			TipoCambio tipoCambioActual) throws Exception{
		
		boolean esMonedaExtranjera;
		
		Persona personaGirar = expedienteLiquidacion.getPersonaGirar();
		Integer intIdEmpresa = expedienteLiquidacion.getId().getIntPersEmpresaPk();
		
		EgresoDetalle egresoDetalle = new EgresoDetalle();
		egresoDetalle.getId().setIntPersEmpresaEgreso(intIdEmpresa);
		//Seteamos el Documento General del expediente prevision girado
		egresoDetalle.setIntParaDocumentoGeneral(expedienteLiquidacion.getIntParaDocumentoGeneral());
		egresoDetalle.setIntParaTipoComprobante(null);
		egresoDetalle.setStrSerieDocumento(null);
		egresoDetalle.setStrNumeroDocumento(""+expedienteLiquidacion.getId().getIntItemExpediente());
		
		egresoDetalle.setIntPersEmpresaGirado(intIdEmpresa);
		egresoDetalle.setIntPersonaGirado(personaGirar.getIntIdPersona());
		egresoDetalle.setIntCuentaGirado(expedienteLiquidacion.getCuentaLiquidar().getId().getIntCuenta());
		egresoDetalle.setIntSucuIdSucursalEgreso(socioEstructura.getIntIdSucursalAdministra());
		egresoDetalle.setIntSudeIdSubsucursalEgreso(socioEstructura.getIntIdSubsucurAdministra());
		egresoDetalle.setIntParaTipoMoneda(expedienteLiquidacion.getCuentaLiquidar().getIntParaTipoMonedaCod());
		if(egresoDetalle.getIntParaTipoMoneda().equals(Constante.PARAM_T_TIPOMONEDA_SOL)){
			esMonedaExtranjera = Boolean.FALSE;
		}else{
			esMonedaExtranjera = Boolean.TRUE;
		}
		//Calculo del monto girado
		BigDecimal bdMontoTotal = BigDecimal.ONE;
		for (ModeloDetalleNivelComp campo : o) {
			if (campo.getIntDatoArgumento().equals(expedienteLiquidacionDetalle.getId().getIntItemCuentaConcepto())) {
				if (campo.getStrCampoConsumir().equals(Constante.PARAM_T_MODELO_GIROLIQUIDACION_MONTOCUENTA)) {
					bdMontoTotal = bdMontoTotal.multiply(expedienteLiquidacionDetalle.getBdSaldo()!=null
															?expedienteLiquidacionDetalle.getBdSaldo()
															:BigDecimal.ONE);
				}
				if (campo.getStrCampoConsumir().equals(Constante.PARAM_T_MODELO_GIROLIQUIDACION_PORCENTAJEBENEFICIO)) {
					
					if (expedienteLiquidacionDetalle.getBeneficiarioLiquidacion()==null) {
						for (BeneficiarioLiquidacion x : expedienteLiquidacionDetalle.getListaBeneficiarioLiquidacion()) {
							if (x.getIntPersPersonaBeneficiario().equals(expedienteLiquidacion.getIntIdPersonaBeneficiarioGirar()) && x.getId().getIntItemCuentaConcepto().equals(expedienteLiquidacionDetalle.getId().getIntItemCuentaConcepto())) {
								expedienteLiquidacionDetalle.setBeneficiarioLiquidacion(x);
							}
						}
						
						bdMontoTotal = bdMontoTotal.multiply(expedienteLiquidacionDetalle.getBeneficiarioLiquidacion().getBdPorcentajeBeneficio()!=null
								?expedienteLiquidacionDetalle.getBeneficiarioLiquidacion().getBdPorcentajeBeneficio()
								:BigDecimal.ONE)
									.divide(new BigDecimal(100)).setScale(2, RoundingMode.HALF_UP);
					}
				}			
			}
		}
		
		//Asignando posicion del monto DEBE - HABER
		for (ModeloDetalleNivelComp debeHaber : o) {
			if (debeHaber.getIntDatoArgumento().equals(expedienteLiquidacionDetalle.getId().getIntItemCuentaConcepto())) {
				if (debeHaber.getIntParamDebeHaber().equals(1)) egresoDetalle.setBdMontoCargo(bdMontoTotal);
				if (debeHaber.getIntParamDebeHaber().equals(2)) egresoDetalle.setBdMontoAbono(bdMontoTotal);
				
				egresoDetalle.setIntPersEmpresaCuenta(debeHaber.getIntEmpresaCuenta());
				egresoDetalle.setIntContPeriodoCuenta(debeHaber.getIntPeriodoCuenta());
				egresoDetalle.setStrContNumeroCuenta(debeHaber.getStrNumeroCuenta());
				egresoDetalle.setStrDescripcionEgreso(debeHaber.getStrDescCuenta());
				break;
			}
		}
		
		//Caso moneda extranjera
		if(esMonedaExtranjera) egresoDetalle.setBdMontoDiferencial(obtenerMontoDiferencial(egresoDetalle.getBdMontoAbono()!=null?egresoDetalle.getBdMontoAbono():egresoDetalle.getBdMontoCargo(), tipoCambioActual, tipoCambioSolicitud));
	
		egresoDetalle.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
		egresoDetalle.setTsFechaRegistro(obtenerFechaActual());
		egresoDetalle.setIntPersEmpresaUsuario(intIdEmpresa);
		egresoDetalle.setIntPersPersonaUsuario(usuario.getIntPersPersonaPk());
		egresoDetalle.setIntPersEmpresaLibroDestino(null);
		egresoDetalle.setIntContPeriodoLibroDestino(null);
		egresoDetalle.setIntContCodigoLibroDestino(null);

		
		if(controlFondosFijos!=null){
			egresoDetalle.setIntParaTipoFondoFijo(controlFondosFijos.getId().getIntParaTipoFondoFijo());
			egresoDetalle.setIntItemPeriodoFondo(controlFondosFijos.getId().getIntItemPeriodoFondo());
			egresoDetalle.setIntSucuIdSucursal(controlFondosFijos.getId().getIntSucuIdSucursal());
			egresoDetalle.setIntItemFondoFijo(controlFondosFijos.getId().getIntItemFondoFijo());
		}
		egresoDetalle.setExpedienteLiquidacionDetalle(expedienteLiquidacionDetalle);
		return egresoDetalle;
	}
	
	/**
	 * JCHAVEZ 29.01.2014
	 * Genera el Libro Diario Detalle para Giro Liquidacion
	 * @param socioEstructura
	 * @param o
	 * @param expedienteLiquidacion
	 * @param controlFondosFijos
	 * @param tipoCambioActual
	 * @param bdMontoDebeHaber
	 * @param egresoDetalle
	 * @return
	 * @throws Exception
	 */
	private LibroDiarioDetalle generarLibroDiarioDetalleLiquidacion(SocioEstructura socioEstructura, List<ModeloDetalleNivelComp> o, 
			ExpedienteLiquidacion expedienteLiquidacion, ControlFondosFijos controlFondosFijos, TipoCambio tipoCambioActual, BigDecimal bdMontoDebeHaber
			, EgresoDetalle egresoDetalle)
		throws Exception{
		
		Integer intIdEmpresa = expedienteLiquidacion.getId().getIntPersEmpresaPk();
		Persona personaGirar = expedienteLiquidacion.getPersonaGirar();
		
		LibroDiarioDetalle libroDiarioDetalle = new LibroDiarioDetalle();
		libroDiarioDetalle.setId(new LibroDiarioDetalleId());
		libroDiarioDetalle.getId().setIntPersEmpresaLibro(intIdEmpresa);
		libroDiarioDetalle.getId().setIntContPeriodoLibro(obtenerPeriodoActual());		
		libroDiarioDetalle.setIntParaMonedaDocumento(expedienteLiquidacion.getCuentaLiquidar().getIntParaTipoMonedaCod());
		libroDiarioDetalle.setIntTipoCambio(tipoCambioActual.getId().getIntParaClaseTipoCambio());
		
		if(o!=null && !o.isEmpty()){
			for (ModeloDetalleNivelComp modelo : o) {
				if (modelo.getIntDatoArgumento().equals(egresoDetalle.getExpedienteLiquidacionDetalle().getId().getIntItemCuentaConcepto())) {
					libroDiarioDetalle.setIntPersEmpresaCuenta(modelo.getIntEmpresaCuenta());
					libroDiarioDetalle.setIntContPeriodo(modelo.getIntPeriodoCuenta());
					libroDiarioDetalle.setStrContNumeroCuenta(modelo.getStrNumeroCuenta());			
					libroDiarioDetalle.setStrComentario(modelo.getStrDescCuenta().length()<20?modelo.getStrDescCuenta():modelo.getStrDescCuenta().substring(0, 20));
					
					if(!libroDiarioDetalle.getIntParaMonedaDocumento().equals(Constante.PARAM_T_TIPOMONEDA_SOL)){
						if (modelo.getIntParamDebeHaber().equals(1)) libroDiarioDetalle.setBdDebeExtranjero(bdMontoDebeHaber.multiply(tipoCambioActual.getBdCompra()));
						if (modelo.getIntParamDebeHaber().equals(2)) libroDiarioDetalle.setBdHaberExtranjero(bdMontoDebeHaber.multiply(tipoCambioActual.getBdCompra()));
					}else{
						if (modelo.getIntParamDebeHaber().equals(1)) libroDiarioDetalle.setBdDebeSoles(bdMontoDebeHaber);
						if (modelo.getIntParamDebeHaber().equals(2)) libroDiarioDetalle.setBdHaberSoles(bdMontoDebeHaber);
					}
					break;
				}
			}
		}
		
		libroDiarioDetalle.setIntPersPersona(personaGirar.getIntIdPersona());
		libroDiarioDetalle.setIntParaDocumentoGeneral(expedienteLiquidacion.getIntParaDocumentoGeneral());
		libroDiarioDetalle.setStrSerieDocumento(null);
		
//		if(!libroDiarioDetalle.getIntParaMonedaDocumento().equals(Constante.PARAM_T_TIPOMONEDA_SOL)){
//			libroDiarioDetalle.setIntTipoCambio(tipoCambioActual.getId().getIntParaClaseTipoCambio());
//			if (o.get(0).getIntParamDebeHaber().equals(1)) libroDiarioDetalle.setBdDebeExtranjero(bdMontoDebeHaber.multiply(tipoCambioActual.getBdCompra()));
//			if (o.get(0).getIntParamDebeHaber().equals(2)) libroDiarioDetalle.setBdHaberExtranjero(bdMontoDebeHaber.multiply(tipoCambioActual.getBdCompra()));
//		}else{
//			if (o.get(0).getIntParamDebeHaber().equals(1)) libroDiarioDetalle.setBdDebeSoles(bdMontoDebeHaber);
//			if (o.get(0).getIntParamDebeHaber().equals(2)) libroDiarioDetalle.setBdHaberSoles(bdMontoDebeHaber);
//		}
		
		libroDiarioDetalle.setStrNumeroDocumento(""+expedienteLiquidacion.getId().getIntItemExpediente());
		libroDiarioDetalle.setIntPersEmpresaSucursal(socioEstructura.getIntEmpresaSucUsuario());
		libroDiarioDetalle.setIntSucuIdSucursal(socioEstructura.getIntIdSucursalUsuario());
		libroDiarioDetalle.setIntSudeIdSubSucursal(socioEstructura.getIntIdSubSucursalUsuario());			

		return libroDiarioDetalle;
	}	
	/**
	 * GENERACION DEL LLD USANDO EL CFF
	 * @param expedienteLiquidacion
	 * @param controlFondosFijos
	 * @param tipoCambioActual
	 * @param bdMontoDebeHaberTotal
	 * @return
	 * @throws Exception
	 */
	private LibroDiarioDetalle generarCFFLibroDiarioDetalle(ExpedienteLiquidacion expedienteLiquidacion, ControlFondosFijos controlFondosFijos, TipoCambio tipoCambioActual, BigDecimal bdMontoDebeHaberTotal) throws Exception{
		LibroDiarioDetalle libroDiarioDetalle = null;
		Integer intIdEmpresa = expedienteLiquidacion.getId().getIntPersEmpresaPk();
		PlanCuentaId pId = new PlanCuentaId();
		Cuenta cuenta = expedienteLiquidacion.getCuentaLiquidar();
	
		try {
			PlanCuentaFacadeRemote planCuentaFacade = (PlanCuentaFacadeRemote)EJBFactory.getRemote(PlanCuentaFacadeRemote.class);
			libroDiarioDetalle = new LibroDiarioDetalle();
			libroDiarioDetalle.setId(new LibroDiarioDetalleId());
			libroDiarioDetalle.getId().setIntPersEmpresaLibro(intIdEmpresa);
			libroDiarioDetalle.getId().setIntContPeriodoLibro(obtenerPeriodoActual());
	
			libroDiarioDetalle.setIntPersPersona(expedienteLiquidacion.getPersonaGirar().getIntIdPersona());
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
	
			if(cuenta.getIntParaTipoMonedaCod().compareTo(Constante.PARAM_T_TIPOMONEDA_SOL)==0){
				libroDiarioDetalle.setIntParaMonedaDocumento(Constante.PARAM_T_TIPOMONEDA_SOL);
			}else{
				libroDiarioDetalle.setIntParaMonedaDocumento(cuenta.getIntParaTipoMonedaCod());
			}
	
			libroDiarioDetalle.setIntTipoCambio(tipoCambioActual.getId().getIntParaClaseTipoCambio());
			
			
			if(libroDiarioDetalle.getIntParaMonedaDocumento().equals(Constante.PARAM_T_TIPOMONEDA_SOL)){
				libroDiarioDetalle.setBdHaberSoles(bdMontoDebeHaberTotal);
			}else{
				/******cambio en soles ******/
				libroDiarioDetalle.setBdHaberSoles(bdMontoDebeHaberTotal.multiply(tipoCambioActual.getBdCompra()));
				libroDiarioDetalle.setBdHaberExtranjero(bdMontoDebeHaberTotal);
			}			
		} catch (Exception e) {
			log.error("Error en generarLibroDiarioDetalleGiroPrevisionFondoFijo --> "+e);
		}		
		return libroDiarioDetalle;
	}
	
	/**
	 * 
	 * @param expedienteLiquidacion
	 * @param controlFondosFijos
	 * @param tipoCambioActual
	 * @param bdMontoDebeHaberTotal
	 * @return
	 * @throws Exception
	 */
	private LibroDiarioDetalle generarBancoLibroDiarioDetalle(ExpedienteLiquidacion expedienteLiquidacion, Bancocuenta bancoCuenta, TipoCambio tipoCambioActual, BigDecimal bdMontoDebeHaberTotal, Integer intTipoDocumentoValidar) throws Exception{
		LibroDiarioDetalle libroDiarioDetalle = null;
		Integer intIdEmpresa = expedienteLiquidacion.getId().getIntPersEmpresaPk();
		Cuenta cuenta = expedienteLiquidacion.getCuentaLiquidar(); 
	
		try {
			libroDiarioDetalle = new LibroDiarioDetalle();
			libroDiarioDetalle.setId(new LibroDiarioDetalleId());
			libroDiarioDetalle.getId().setIntPersEmpresaLibro(intIdEmpresa);
			libroDiarioDetalle.getId().setIntContPeriodoLibro(obtenerPeriodoActual());
	
			libroDiarioDetalle.setIntPersPersona(expedienteLiquidacion.getPersonaGirar().getIntIdPersona());
			
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
	
			if(cuenta.getIntParaTipoMonedaCod().compareTo(Constante.PARAM_T_TIPOMONEDA_SOL)==0){
				libroDiarioDetalle.setIntParaMonedaDocumento(Constante.PARAM_T_TIPOMONEDA_SOL);
			}else{
				libroDiarioDetalle.setIntParaMonedaDocumento(cuenta.getIntParaTipoMonedaCod());
			}
	
			libroDiarioDetalle.setIntTipoCambio(tipoCambioActual.getId().getIntParaClaseTipoCambio());
			
			
			if(libroDiarioDetalle.getIntParaMonedaDocumento().equals(Constante.PARAM_T_TIPOMONEDA_SOL)){
				libroDiarioDetalle.setBdHaberSoles(bdMontoDebeHaberTotal);
			}else{
				/******cambio en soles ******/
				libroDiarioDetalle.setBdHaberSoles(bdMontoDebeHaberTotal.multiply(tipoCambioActual.getBdCompra()));
				libroDiarioDetalle.setBdHaberExtranjero(bdMontoDebeHaberTotal);
			}			
		} catch (Exception e) {
			log.error("Error en generarLibroDiarioDetalleGiroPrevisionFondoFijo --> "+e);
		}		
		return libroDiarioDetalle;
	}
}
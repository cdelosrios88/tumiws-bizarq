package pe.com.tumi.tesoreria.egreso.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiario;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiarioDetalle;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiarioId;
import pe.com.tumi.contabilidad.cierre.facade.LibroDiarioFacadeRemote;
import pe.com.tumi.contabilidad.core.domain.Modelo;
import pe.com.tumi.contabilidad.core.domain.ModeloDetalle;
import pe.com.tumi.contabilidad.core.domain.ModeloDetalleNivel;
import pe.com.tumi.contabilidad.core.domain.PlanCuenta;
import pe.com.tumi.contabilidad.core.domain.PlanCuentaId;
import pe.com.tumi.contabilidad.core.facade.ModeloFacadeRemote;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.parametro.general.domain.TipoCambio;
import pe.com.tumi.parametro.general.domain.TipoCambioId;
import pe.com.tumi.parametro.general.facade.GeneralFacadeRemote;
import pe.com.tumi.persona.core.domain.CuentaBancaria;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.seguridad.login.domain.Usuario;
import pe.com.tumi.servicio.solicitudPrestamo.domain.EgresoDetalleInterfaz;
import pe.com.tumi.tesoreria.banco.domain.Bancocuenta;
import pe.com.tumi.tesoreria.egreso.domain.ControlFondosFijos;
import pe.com.tumi.tesoreria.egreso.domain.Egreso;
import pe.com.tumi.tesoreria.egreso.domain.EgresoDetalle;
import pe.com.tumi.tesoreria.egreso.domain.Movilidad;
import pe.com.tumi.tesoreria.egreso.domain.MovilidadDetalle;

public class EgresoMovilidadService {
	
	protected static Logger log = Logger.getLogger(EgresoMovilidadService.class);
	


	public List<EgresoDetalleInterfaz> cargarListaEgresoDetalleInterfaz(Movilidad movilidad)throws BusinessException{
		List<EgresoDetalleInterfaz> listaEgresoDetalleInterfaz = new ArrayList<EgresoDetalleInterfaz>();
		try{
			HashSet<Integer> hashTipoMovilidad = new HashSet<Integer>();
			for(MovilidadDetalle movilidadDetalle : movilidad.getListaMovilidadDetalle()){
				hashTipoMovilidad.add(movilidadDetalle.getIntParaTipoMovilidad());
			}
			
			for(Integer intTipoMovilidad : hashTipoMovilidad){
				EgresoDetalleInterfaz eDI = new EgresoDetalleInterfaz();
				BigDecimal bdMontoAcumuladoPorTipo = new BigDecimal(0);
				for(MovilidadDetalle movilidadDetalle : movilidad.getListaMovilidadDetalle()){
					if(movilidadDetalle.getIntParaTipoMovilidad().equals(intTipoMovilidad)){
						bdMontoAcumuladoPorTipo = bdMontoAcumuladoPorTipo.add(movilidadDetalle.getBdMonto());
					}
				}
				eDI.setBdMonto(bdMontoAcumuladoPorTipo);
				eDI.setIntParaConcepto(intTipoMovilidad);
				
				listaEgresoDetalleInterfaz.add(eDI);
			}
		}catch(Exception e){
			throw new BusinessException(e);
		}
		
		return listaEgresoDetalleInterfaz;
	}
	
	
	private BigDecimal obtenerMontoTotalMovilidad(List<Movilidad> listaMovilidad){
		BigDecimal bdMontoTotal = new BigDecimal(0);
		for(Movilidad movilidad : listaMovilidad){
			bdMontoTotal = bdMontoTotal.add(movilidad.getBdMontoAcumulado());
		}
		return bdMontoTotal;
	}
	
	private Integer	obtenerPeriodoActual(){
		String strPeriodo = "";
		Calendar cal = Calendar.getInstance();
		int a�o = cal.get(Calendar.YEAR);
		int mes = cal.get(Calendar.MONTH);
		mes = mes + 1; 
		if(mes<10){
			strPeriodo = a�o + "0" + mes;
		}else{
			strPeriodo  = a�o + "" + mes;
		}		
		return Integer.parseInt(strPeriodo);		
	}
	
	private Timestamp obtenerFechaActual(){
		return new Timestamp(new Date().getTime());
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
	
	private TipoCambio obtenerTipoCambioActual(Integer intParaTipoMoneda, Integer intIdEmpresa)throws Exception{
		GeneralFacadeRemote generalFacade = (GeneralFacadeRemote) EJBFactory.getRemote(GeneralFacadeRemote.class);
		TipoCambioId tipoCambioId = new TipoCambioId();
		tipoCambioId.setIntPersEmpresa(intIdEmpresa);
		tipoCambioId.setIntParaMoneda(intParaTipoMoneda);
		tipoCambioId.setDtParaFecha(obtenerFechaActual());
		tipoCambioId.setIntParaClaseTipoCambio(Constante.PARAM_T_TIPOCAMBIO_BANCARIO);
		
		return generalFacade.getTipoCambioPorPK(tipoCambioId);
	}
	
	private TipoCambio obtenerTipoCambioSolicitud(Movilidad movilidad, Integer intParaTipoMoneda, Integer intIdEmpresa) 
		throws Exception{
		GeneralFacadeRemote generalFacade = (GeneralFacadeRemote) EJBFactory.getRemote(GeneralFacadeRemote.class);		
		
		TipoCambioId tipoCambioId = new TipoCambioId();
		tipoCambioId.setIntPersEmpresa(intIdEmpresa);
		tipoCambioId.setIntParaMoneda(intParaTipoMoneda);
		tipoCambioId.setDtParaFecha(movilidad.getTsFechaRegistro());
		tipoCambioId.setIntParaClaseTipoCambio(Constante.PARAM_T_TIPOCAMBIO_BANCARIO);
		
		return generalFacade.getTipoCambioPorPK(tipoCambioId);		
	}
	
	private BigDecimal obtenerMontoDiferencial(BigDecimal bdMontoMonedaExtranjera, TipoCambio tipoCambioSolicitud, TipoCambio tipoCambioActual){
		BigDecimal bdMontoSolesSolicitud = bdMontoMonedaExtranjera.multiply(tipoCambioSolicitud.getBdPromedio());
		BigDecimal bdMontoSolesActual = bdMontoMonedaExtranjera.multiply(tipoCambioActual.getBdPromedio());
		
		return bdMontoSolesActual.subtract(bdMontoSolesSolicitud);
	}
	
	private EgresoDetalle generarEgresoDetalle(EgresoDetalleInterfaz egresoDetalleInterfaz,	Movilidad movilidad, 
			ControlFondosFijos controlFondosFijos, Usuario usuario)	throws Exception{
		
		Integer intIdEmpresa = movilidad.getId().getIntPersEmpresaMovilidad();
		Persona personaGirar = movilidad.getPersona();
		
		TipoCambio tipoCambioActual = null;
		TipoCambio tipoCambioSolicitud = null;
		ModeloDetalle modeloDetalle = egresoDetalleInterfaz.getModeloDetalle();
		boolean esMonedaExtranjera;
		
		EgresoDetalle egresoDetalle = new EgresoDetalle();		
		egresoDetalle.getId().setIntPersEmpresaEgreso(intIdEmpresa);
		egresoDetalle.setIntParaDocumentoGeneral(movilidad.getIntParaDocumentoGeneral());
		egresoDetalle.setIntParaTipoComprobante(null);
		egresoDetalle.setStrSerieDocumento(null);
		egresoDetalle.setStrNumeroDocumento(""+movilidad.getId().getIntItemMovilidad());
		egresoDetalle.setStrDescripcionEgreso(modeloDetalle.getPlanCuenta().getStrDescripcion());
		egresoDetalle.setIntPersEmpresaGirado(intIdEmpresa);
		egresoDetalle.setIntPersonaGirado(personaGirar.getIntIdPersona());
		egresoDetalle.setIntCuentaGirado(null);
		egresoDetalle.setIntSucuIdSucursalEgreso(movilidad.getIntSucuIdSucursal());
		egresoDetalle.setIntSudeIdSubsucursalEgreso(movilidad.getIntSudeIdSubsucursal());
		egresoDetalle.setIntParaTipoMoneda(controlFondosFijos.getIntParaMoneda());
		if(egresoDetalle.getIntParaTipoMoneda().equals(Constante.PARAM_T_TIPOMONEDA_SOLES)){
			esMonedaExtranjera = Boolean.FALSE;
		}else{
			esMonedaExtranjera = Boolean.TRUE;
			tipoCambioActual = obtenerTipoCambioActual(egresoDetalle.getIntParaTipoMoneda(), intIdEmpresa);			
			tipoCambioSolicitud = obtenerTipoCambioSolicitud(movilidad, egresoDetalle.getIntParaTipoMoneda(), intIdEmpresa);		
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
		egresoDetalle.setIntPersEmpresaCuenta(modeloDetalle.getPlanCuenta().getId().getIntEmpresaCuentaPk());
		egresoDetalle.setIntContPeriodoCuenta(modeloDetalle.getPlanCuenta().getId().getIntPeriodoCuenta());
		egresoDetalle.setStrContNumeroCuenta(modeloDetalle.getPlanCuenta().getId().getStrNumeroCuenta());
		egresoDetalle.setIntParaTipoFondoFijo(controlFondosFijos.getId().getIntParaTipoFondoFijo());
		egresoDetalle.setIntItemPeriodoFondo(controlFondosFijos.getId().getIntItemPeriodoFondo());
		egresoDetalle.setIntSucuIdSucursal(controlFondosFijos.getId().getIntSucuIdSucursal());
		egresoDetalle.setIntItemFondoFijo(controlFondosFijos.getId().getIntItemFondoFijo());
		
		return egresoDetalle;
	}
	
	private EgresoDetalle generarEgresoDetalleCheque(EgresoDetalleInterfaz egresoDetalleInterfaz,	Movilidad movilidad, 
			Bancocuenta bancoCuenta, Usuario usuario)	throws Exception{
		
		Integer intIdEmpresa = movilidad.getId().getIntPersEmpresaMovilidad();
		Persona personaGirar = movilidad.getPersona();
		Integer intIdMoneda = bancoCuenta.getCuentaBancaria().getIntMonedaCod();
		
		TipoCambio tipoCambioActual = null;
		TipoCambio tipoCambioSolicitud = null;
		ModeloDetalle modeloDetalle = egresoDetalleInterfaz.getModeloDetalle();
		boolean esMonedaExtranjera;
		
		EgresoDetalle egresoDetalle = new EgresoDetalle();		
		egresoDetalle.getId().setIntPersEmpresaEgreso(intIdEmpresa);
		egresoDetalle.setIntParaDocumentoGeneral(movilidad.getIntParaDocumentoGeneral());
		egresoDetalle.setIntParaTipoComprobante(null);
		egresoDetalle.setStrSerieDocumento(null);
		egresoDetalle.setStrNumeroDocumento(""+movilidad.getId().getIntItemMovilidad());
		egresoDetalle.setStrDescripcionEgreso(modeloDetalle.getPlanCuenta().getStrDescripcion());
		egresoDetalle.setIntPersEmpresaGirado(intIdEmpresa);
		egresoDetalle.setIntPersonaGirado(personaGirar.getIntIdPersona());
		egresoDetalle.setIntCuentaGirado(null);
		egresoDetalle.setIntSucuIdSucursalEgreso(movilidad.getIntSucuIdSucursal());
		egresoDetalle.setIntSudeIdSubsucursalEgreso(movilidad.getIntSudeIdSubsucursal());
		egresoDetalle.setIntParaTipoMoneda(intIdMoneda);
		if(egresoDetalle.getIntParaTipoMoneda().equals(Constante.PARAM_T_TIPOMONEDA_SOLES)){
			esMonedaExtranjera = Boolean.FALSE;
		}else{
			esMonedaExtranjera = Boolean.TRUE;
			tipoCambioActual = obtenerTipoCambioActual(egresoDetalle.getIntParaTipoMoneda(), intIdEmpresa);			
			tipoCambioSolicitud = obtenerTipoCambioSolicitud(movilidad, egresoDetalle.getIntParaTipoMoneda(), intIdEmpresa);		
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
		egresoDetalle.setIntPersEmpresaCuenta(modeloDetalle.getPlanCuenta().getId().getIntEmpresaCuentaPk());
		egresoDetalle.setIntContPeriodoCuenta(modeloDetalle.getPlanCuenta().getId().getIntPeriodoCuenta());
		egresoDetalle.setStrContNumeroCuenta(modeloDetalle.getPlanCuenta().getId().getStrNumeroCuenta());
		egresoDetalle.setIntParaTipoFondoFijo(null);
		egresoDetalle.setIntItemPeriodoFondo(null);
		egresoDetalle.setIntSucuIdSucursal(null);
		egresoDetalle.setIntItemFondoFijo(null);
		
		return egresoDetalle;
	}
	
	private LibroDiarioDetalle generarLibroDiarioDetalle(EgresoDetalleInterfaz egresoDetalleInterfaz,
		Movilidad movilidad, ControlFondosFijos controlFondosFijos, BigDecimal bdMontoTotal) throws Exception{
			
		Integer intIdEmpresa = movilidad.getId().getIntPersEmpresaMovilidad();
		Persona personaGirar = movilidad.getPersona();
		TipoCambio tipoCambioActual = null;
		ModeloDetalle modeloDetalle = null;
		if(egresoDetalleInterfaz!=null) modeloDetalle = egresoDetalleInterfaz.getModeloDetalle();
			
		
		Integer intMoneda = controlFondosFijos.getIntParaMoneda();
		
			
		boolean esMonedaExtranjera;
		LibroDiarioDetalle libroDiarioDetalle = new LibroDiarioDetalle();
		libroDiarioDetalle.getId().setIntPersEmpresaLibro(intIdEmpresa);
		libroDiarioDetalle.getId().setIntContPeriodoLibro(obtenerPeriodoActual());		
			
		if(egresoDetalleInterfaz !=null){
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
		libroDiarioDetalle.setIntParaDocumentoGeneral(movilidad.getIntParaDocumentoGeneral());
		libroDiarioDetalle.setStrSerieDocumento(null);
		libroDiarioDetalle.setIntParaMonedaDocumento(intMoneda);
		if(!libroDiarioDetalle.getIntParaMonedaDocumento().equals(Constante.PARAM_T_TIPOMONEDA_SOLES)){
			tipoCambioActual = obtenerTipoCambioActual(intMoneda, intIdEmpresa);
			libroDiarioDetalle.setIntTipoCambio(tipoCambioActual.getId().getIntParaClaseTipoCambio());
			esMonedaExtranjera = Boolean.TRUE;
		}else{
			esMonedaExtranjera = Boolean.FALSE;
		}
		
		if(egresoDetalleInterfaz!=null){
			libroDiarioDetalle.setStrNumeroDocumento(""+movilidad.getId().getIntItemMovilidad());
			libroDiarioDetalle.setIntPersEmpresaSucursal(intIdEmpresa);
			libroDiarioDetalle.setIntSucuIdSucursal(movilidad.getIntSucuIdSucursal());
			libroDiarioDetalle.setIntSudeIdSubSucursal(movilidad.getIntSudeIdSubsucursal());			
		}else{
			libroDiarioDetalle.setIntPersEmpresaSucursal(controlFondosFijos.getSucursal().getId().getIntPersEmpresaPk());
			libroDiarioDetalle.setIntSucuIdSucursal(controlFondosFijos.getSucursal().getId().getIntIdSucursal());
			libroDiarioDetalle.setIntSudeIdSubSucursal(controlFondosFijos.getSubsucursal().getId().getIntIdSubSucursal());			
		}
		
		if(egresoDetalleInterfaz!=null){
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
				libroDiarioDetalle.setBdHaberSoles(bdMontoTotal);
			}else{
				libroDiarioDetalle.setBdHaberExtranjero(bdMontoTotal);
				libroDiarioDetalle.setBdHaberSoles(libroDiarioDetalle.getBdHaberExtranjero().multiply(tipoCambioActual.getBdPromedio()));
			}
		}		
		return libroDiarioDetalle;
	}
	
	private LibroDiarioDetalle generarLibroDiarioDetalleCheque(EgresoDetalleInterfaz egresoDetalleInterfaz,
		Movilidad movilidad, Bancocuenta bancoCuenta, BigDecimal bdMontoTotal) throws Exception{
				
		Integer intIdEmpresa = movilidad.getId().getIntPersEmpresaMovilidad();
		Persona personaGirar = movilidad.getPersona();
		TipoCambio tipoCambioActual = null;
		ModeloDetalle modeloDetalle = null;
		if(egresoDetalleInterfaz!=null) modeloDetalle = egresoDetalleInterfaz.getModeloDetalle();			
	
		Integer intMoneda = bancoCuenta.getCuentaBancaria().getIntMonedaCod();
				
		boolean esMonedaExtranjera;
		LibroDiarioDetalle libroDiarioDetalle = new LibroDiarioDetalle();
		libroDiarioDetalle.getId().setIntPersEmpresaLibro(intIdEmpresa);
		libroDiarioDetalle.getId().setIntContPeriodoLibro(obtenerPeriodoActual());		
			
		if(egresoDetalleInterfaz !=null){
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
		libroDiarioDetalle.setIntParaDocumentoGeneral(movilidad.getIntParaDocumentoGeneral());
		libroDiarioDetalle.setStrSerieDocumento(null);
		libroDiarioDetalle.setIntParaMonedaDocumento(intMoneda);
		if(!libroDiarioDetalle.getIntParaMonedaDocumento().equals(Constante.PARAM_T_TIPOMONEDA_SOLES)){
			tipoCambioActual = obtenerTipoCambioActual(intMoneda, intIdEmpresa);
			libroDiarioDetalle.setIntTipoCambio(tipoCambioActual.getId().getIntParaClaseTipoCambio());
			esMonedaExtranjera = Boolean.TRUE;
		}else{
			esMonedaExtranjera = Boolean.FALSE;
		}
		
		if(egresoDetalleInterfaz!=null){
			libroDiarioDetalle.setStrNumeroDocumento(""+movilidad.getId().getIntItemMovilidad());
			libroDiarioDetalle.setIntPersEmpresaSucursal(intIdEmpresa);
			libroDiarioDetalle.setIntSucuIdSucursal(movilidad.getIntSucuIdSucursal());
			libroDiarioDetalle.setIntSudeIdSubSucursal(movilidad.getIntSudeIdSubsucursal());			
		}else{
			libroDiarioDetalle.setIntPersEmpresaSucursal(Constante.PARAM_EMPRESASESION);
			libroDiarioDetalle.setIntSucuIdSucursal(Constante.SUCURSAL_SEDECENTRAL);
			libroDiarioDetalle.setIntSudeIdSubSucursal(Constante.SUBSUCURSAL_SEDE1);			
		}
		
		if(egresoDetalleInterfaz!=null){
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
				libroDiarioDetalle.setBdHaberSoles(bdMontoTotal);
			}else{
				libroDiarioDetalle.setBdHaberExtranjero(bdMontoTotal);
				libroDiarioDetalle.setBdHaberSoles(libroDiarioDetalle.getBdHaberExtranjero().multiply(tipoCambioActual.getBdPromedio()));
			}
		}		
		return libroDiarioDetalle;
	}
	
	private void asignarModeloDetalle(EgresoDetalleInterfaz egresoDetalleInterfaz, List<ModeloDetalle> listaModeloDetalle){
		for(ModeloDetalle modeloDetalle : listaModeloDetalle){
			for(ModeloDetalleNivel modeloDetalleNivel : modeloDetalle.getListModeloDetalleNivel()){
				//en intParaConcepto se guardo el valor del tipo de movilidaddetalle
				if(egresoDetalleInterfaz.getIntParaConcepto().equals(modeloDetalleNivel.getIntValor())){
					egresoDetalleInterfaz.setModeloDetalle(modeloDetalle);
					break;
				}
			}	
		}
	}
	
	public Egreso generarEgresoMovilidad(List<Movilidad> listaMovilidad, ControlFondosFijos controlFondosFijos, Usuario usuario) throws BusinessException{
		Egreso egreso = new Egreso();
		try{
			
			ModeloFacadeRemote modeloFacade = (ModeloFacadeRemote) EJBFactory.getRemote(ModeloFacadeRemote.class);			
			
			Integer intIdEmpresa = controlFondosFijos.getId().getIntPersEmpresa();
			Persona personaGirar = listaMovilidad.get(0).getPersona();
			String strGlosaEgreso = listaMovilidad.get(0).getStrGlosaEgreso();
			BigDecimal bdMontoTotal = obtenerMontoTotalMovilidad(listaMovilidad);
			
			
			List<Modelo> listaModeloGiro = modeloFacade.obtenerTipoModeloActual(Constante.PARAM_T_TIPOMODELOCONTABLE_MOVILIDAD, intIdEmpresa);
			Modelo modelo = listaModeloGiro.get(0);
			
			egreso.getId().setIntPersEmpresaEgreso(intIdEmpresa);
			egreso.setIntParaTipoOperacion(Constante.PARAM_T_TIPODEOPERACION_EGRESO);
			egreso.setIntParaFormaPago(Constante.PARAM_T_FORMAPAGOEGRESO_EFECTIVO);
			egreso.setIntParaDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_RENDICION);
			egreso.setIntItemPeriodoEgreso(obtenerPeriodoActual());
			egreso.setIntItemEgreso(null);
			egreso.setIntSucuIdSucursal(usuario.getSucursal().getId().getIntIdSucursal());
			egreso.setIntSudeIdSubsucursal(usuario.getSubSucursal().getId().getIntIdSubSucursal());
			egreso.setIntParaSubTipoOperacion(Constante.PARAM_T_TIPODESUBOPERACION_CANCELACION);
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
//			egreso.setIntPersCuentaBancariaGirado(null);
			//jchavez 20.05.2014 cambio de tipo de dato
			egreso.setStrPersCuentaBancariaGirado(null);
			egreso.setIntPersEmpresaBeneficiario(null);
			egreso.setIntPersPersonaBeneficiario(null);
			egreso.setIntPersCuentaBancariaBeneficiario(null);
			egreso.setIntPersPersonaApoderado(null);
			egreso.setIntPersEmpresaApoderado(null);
			egreso.setBdMontoTotal(bdMontoTotal);
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
			
			
			for(Movilidad movilidad : listaMovilidad){
				for(EgresoDetalleInterfaz egresoDetalleInterfaz : movilidad.getListaEgresoDetalleInterfaz()){
					asignarModeloDetalle(egresoDetalleInterfaz, modelo.getListModeloDetalle());
					EgresoDetalle egresoDetalle = generarEgresoDetalle(egresoDetalleInterfaz, movilidad, controlFondosFijos, usuario);
					egreso.getListaEgresoDetalle().add(egresoDetalle);
				}
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
			

			for(Movilidad movilidad : listaMovilidad){
				for(EgresoDetalleInterfaz egresoDetalleInterfaz : movilidad.getListaEgresoDetalleInterfaz()){
					LibroDiarioDetalle libroDiarioDetalle = generarLibroDiarioDetalle(egresoDetalleInterfaz, movilidad, controlFondosFijos, bdMontoTotal);
					libroDiario.getListaLibroDiarioDetalle().add(libroDiarioDetalle);
				}
				LibroDiarioDetalle libroDiarioDetalleBanco = generarLibroDiarioDetalle(null, movilidad, controlFondosFijos, bdMontoTotal);
				libroDiario.getListaLibroDiarioDetalle().add(libroDiarioDetalleBanco);
			}
			
			
			
			egreso.setLibroDiario(libroDiario);
			egreso.setControlFondosFijos(controlFondosFijos);
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return egreso;
	}
	
	/**
	 * 
	 * @param listaMovilidad
	 * @param bancoCuenta
	 * @param usuario
	 * @param intNumeroCheque
	 * @param intTipoDocumentoValidar
	 * @return
	 * @throws BusinessException
	 */
	public Egreso generarEgresoMovilidadCheque(List<Movilidad> listaMovilidad, Bancocuenta bancoCuenta, Usuario usuario, Integer intNumeroCheque, 
		Integer intTipoDocumentoValidar) throws BusinessException{
		Egreso egreso = new Egreso();
		try{
			
			ModeloFacadeRemote modeloFacade = (ModeloFacadeRemote) EJBFactory.getRemote(ModeloFacadeRemote.class);			
			
			Integer intIdEmpresa = bancoCuenta.getId().getIntEmpresaPk();
			Persona personaGirar = listaMovilidad.get(0).getPersona();
			String strGlosaEgreso = listaMovilidad.get(0).getStrGlosaEgreso();
			BigDecimal bdMontoTotal = obtenerMontoTotalMovilidad(listaMovilidad);
			
			
			List<Modelo> listaModeloGiro = modeloFacade.obtenerTipoModeloActual(Constante.PARAM_T_TIPOMODELOCONTABLE_MOVILIDAD, intIdEmpresa);
			Modelo modelo = listaModeloGiro.get(0);
			
			egreso.getId().setIntPersEmpresaEgreso(intIdEmpresa);
			egreso.setIntParaTipoOperacion(Constante.PARAM_T_TIPODEOPERACION_EGRESO);
			egreso.setIntParaFormaPago(Constante.PARAM_T_FORMAPAGOEGRESO_EFECTIVO);
			egreso.setIntParaDocumentoGeneral(intTipoDocumentoValidar);
			egreso.setIntItemPeriodoEgreso(obtenerPeriodoActual());
			egreso.setIntItemEgreso(null);
			egreso.setIntSucuIdSucursal(usuario.getSucursal().getId().getIntIdSucursal());
			egreso.setIntSudeIdSubsucursal(usuario.getSubSucursal().getId().getIntIdSubSucursal());
			egreso.setIntParaSubTipoOperacion(Constante.PARAM_T_TIPODESUBOPERACION_CANCELACION);
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
			egreso.setIntCuentaGirado(null);
			//jchavez 20.05.2014 cambio de tipo de dato
			egreso.setStrPersCuentaBancariaGirado(null);
//			egreso.setIntPersCuentaBancariaGirado(null);
			egreso.setIntPersEmpresaBeneficiario(null);
			egreso.setIntPersPersonaBeneficiario(null);
			egreso.setIntPersCuentaBancariaBeneficiario(null);
			egreso.setIntPersPersonaApoderado(null);
			egreso.setIntPersEmpresaApoderado(null);
			egreso.setBdMontoTotal(bdMontoTotal);
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
			
			
			for(Movilidad movilidad : listaMovilidad){
				for(EgresoDetalleInterfaz egresoDetalleInterfaz : movilidad.getListaEgresoDetalleInterfaz()){
					asignarModeloDetalle(egresoDetalleInterfaz, modelo.getListModeloDetalle());
					EgresoDetalle egresoDetalle = generarEgresoDetalleCheque(egresoDetalleInterfaz, movilidad, bancoCuenta, usuario);
					egreso.getListaEgresoDetalle().add(egresoDetalle);
				}
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
			

			for(Movilidad movilidad : listaMovilidad){
				for(EgresoDetalleInterfaz egresoDetalleInterfaz : movilidad.getListaEgresoDetalleInterfaz()){
					LibroDiarioDetalle libroDiarioDetalle = generarLibroDiarioDetalleCheque(egresoDetalleInterfaz, movilidad, bancoCuenta, bdMontoTotal);
					libroDiario.getListaLibroDiarioDetalle().add(libroDiarioDetalle);
				}
				LibroDiarioDetalle libroDiarioDetalleBanco = generarLibroDiarioDetalleCheque(null, movilidad, bancoCuenta, bdMontoTotal);
				libroDiario.getListaLibroDiarioDetalle().add(libroDiarioDetalleBanco);
			}

			egreso.setLibroDiario(libroDiario);
			egreso.setControlFondosFijos(null);
		}catch(Exception e){
			log.error("Error en generarEgresoMovilidadCheque ---> "+e);
			throw new BusinessException(e);
		}
		return egreso;
	}	
	
	public Egreso generarEgresoMovilidadTransferencia(List<Movilidad> listaMovilidad, Bancocuenta bancoCuentaOrigen, Usuario usuario, 
		Integer intNumeroTransferencia, Integer intTipoDocumentoValidar, CuentaBancaria cuentaBancariaDestino) throws BusinessException{
		Egreso egreso = new Egreso();
		try{
			
			ModeloFacadeRemote modeloFacade = (ModeloFacadeRemote) EJBFactory.getRemote(ModeloFacadeRemote.class);			
			
			Integer intIdEmpresa = bancoCuentaOrigen.getId().getIntEmpresaPk();
			Persona personaGirar = listaMovilidad.get(0).getPersona();
			String strGlosaEgreso = listaMovilidad.get(0).getStrGlosaEgreso();
			BigDecimal bdMontoTotal = obtenerMontoTotalMovilidad(listaMovilidad);
			
			
			List<Modelo> listaModeloGiro = modeloFacade.obtenerTipoModeloActual(Constante.PARAM_T_TIPOMODELOCONTABLE_MOVILIDAD, intIdEmpresa);
			Modelo modelo = listaModeloGiro.get(0);
			
			egreso.getId().setIntPersEmpresaEgreso(intIdEmpresa);
			egreso.setIntParaTipoOperacion(Constante.PARAM_T_TIPODEOPERACION_EGRESO);
			egreso.setIntParaFormaPago(Constante.PARAM_T_FORMAPAGOEGRESO_EFECTIVO);
			egreso.setIntParaDocumentoGeneral(intTipoDocumentoValidar);
			egreso.setIntItemPeriodoEgreso(obtenerPeriodoActual());
			egreso.setIntItemEgreso(null);
			egreso.setIntSucuIdSucursal(usuario.getSucursal().getId().getIntIdSucursal());
			egreso.setIntSudeIdSubsucursal(usuario.getSubSucursal().getId().getIntIdSubSucursal());
			egreso.setIntParaSubTipoOperacion(Constante.PARAM_T_TIPODESUBOPERACION_CANCELACION);
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
			//jchavez 20.05.2014 cambio de tipo de dato
			egreso.setStrPersCuentaBancariaGirado(cuentaBancariaDestino.getStrNroCuentaBancaria());
//			egreso.setIntPersCuentaBancariaGirado(cuentaBancariaDestino.getId().getIntIdCuentaBancaria());
			egreso.setIntPersEmpresaBeneficiario(null);
			egreso.setIntPersPersonaBeneficiario(null);
			egreso.setIntPersCuentaBancariaBeneficiario(null);
			egreso.setIntPersPersonaApoderado(null);
			egreso.setIntPersEmpresaApoderado(null);
			egreso.setBdMontoTotal(bdMontoTotal);
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
			
			
			for(Movilidad movilidad : listaMovilidad){
				for(EgresoDetalleInterfaz egresoDetalleInterfaz : movilidad.getListaEgresoDetalleInterfaz()){
					asignarModeloDetalle(egresoDetalleInterfaz, modelo.getListModeloDetalle());
					EgresoDetalle egresoDetalle = generarEgresoDetalleCheque(egresoDetalleInterfaz, movilidad, bancoCuentaOrigen, usuario);
					egreso.getListaEgresoDetalle().add(egresoDetalle);
				}
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
			

			for(Movilidad movilidad : listaMovilidad){
				for(EgresoDetalleInterfaz egresoDetalleInterfaz : movilidad.getListaEgresoDetalleInterfaz()){
					LibroDiarioDetalle libroDiarioDetalle = generarLibroDiarioDetalleCheque(egresoDetalleInterfaz, movilidad, bancoCuentaOrigen, bdMontoTotal);
					libroDiario.getListaLibroDiarioDetalle().add(libroDiarioDetalle);
				}
				LibroDiarioDetalle libroDiarioDetalleBanco = generarLibroDiarioDetalleCheque(null, movilidad, bancoCuentaOrigen, bdMontoTotal);
				libroDiario.getListaLibroDiarioDetalle().add(libroDiarioDetalleBanco);
			}
			
			
			
			egreso.setLibroDiario(libroDiario);
			egreso.setControlFondosFijos(null);
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return egreso;
	}

	
}
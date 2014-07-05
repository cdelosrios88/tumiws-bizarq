package pe.com.tumi.tesoreria.egreso.facade;


import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import pe.com.tumi.common.util.DocumentoGeneral;
import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.facade.TumiFacade;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.parametro.general.domain.Archivo;
import pe.com.tumi.persona.core.domain.CuentaBancaria;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.seguridad.login.domain.Usuario;
import pe.com.tumi.servicio.solicitudPrestamo.domain.EgresoDetalleInterfaz;
import pe.com.tumi.servicio.solicitudPrestamo.domain.ExpedienteCredito;
import pe.com.tumi.tesoreria.banco.domain.Bancocuenta;
import pe.com.tumi.tesoreria.banco.domain.Bancofondo;
import pe.com.tumi.tesoreria.cierre.fdoFijo.domain.ControlFondoFijoAnula;
import pe.com.tumi.tesoreria.egreso.bo.ControlFondoFijoBO;
import pe.com.tumi.tesoreria.egreso.bo.EgresoBO;
import pe.com.tumi.tesoreria.egreso.bo.EgresoDetalleBO;
import pe.com.tumi.tesoreria.egreso.bo.MovilidadBO;
import pe.com.tumi.tesoreria.egreso.bo.MovilidadDetalleBO;
import pe.com.tumi.tesoreria.egreso.bo.SaldoBO;
import pe.com.tumi.tesoreria.egreso.bo.SolicitudPersonalBO;
import pe.com.tumi.tesoreria.egreso.bo.SolicitudPersonalDetalleBO;
import pe.com.tumi.tesoreria.egreso.bo.SolicitudPersonalPagoBO;
import pe.com.tumi.tesoreria.egreso.domain.CierreDiarioArqueo;
import pe.com.tumi.tesoreria.egreso.domain.ControlFondosFijos;
import pe.com.tumi.tesoreria.egreso.domain.ControlFondosFijosId;
import pe.com.tumi.tesoreria.egreso.domain.Egreso;
import pe.com.tumi.tesoreria.egreso.domain.EgresoDetalle;
import pe.com.tumi.tesoreria.egreso.domain.EgresoId;
import pe.com.tumi.tesoreria.egreso.domain.Movilidad;
import pe.com.tumi.tesoreria.egreso.domain.MovilidadDetalle;
import pe.com.tumi.tesoreria.egreso.domain.Saldo;
import pe.com.tumi.tesoreria.egreso.domain.SolicitudPersonal;
import pe.com.tumi.tesoreria.egreso.domain.SolicitudPersonalDetalle;
import pe.com.tumi.tesoreria.egreso.domain.SolicitudPersonalPago;
import pe.com.tumi.tesoreria.egreso.service.CierreDiarioArqueoService;
import pe.com.tumi.tesoreria.egreso.service.DocumentoGeneralService;
import pe.com.tumi.tesoreria.egreso.service.EgresoMovilidadService;
import pe.com.tumi.tesoreria.egreso.service.EgresoService;
import pe.com.tumi.tesoreria.egreso.service.EgresoUtilService;
import pe.com.tumi.tesoreria.egreso.service.GiroMovilidadService;
import pe.com.tumi.tesoreria.egreso.service.MovilidadService;
import pe.com.tumi.tesoreria.egreso.service.SaldoService;
import pe.com.tumi.tesoreria.egreso.service.SolicitudPersonalService;
import pe.com.tumi.tesoreria.logistica.domain.DocumentoSunat;
import pe.com.tumi.tesoreria.logistica.service.EgresoDocumentoSunatService;

@Stateless
public class EgresoFacade extends TumiFacade implements EgresoFacadeRemote, EgresoFacadeLocal {
	
	MovilidadService movilidadService = (MovilidadService)TumiFactory.get(MovilidadService.class);	
	EgresoService egresoService = (EgresoService)TumiFactory.get(EgresoService.class);
	EgresoUtilService egresoUtilService = (EgresoUtilService)TumiFactory.get(EgresoUtilService.class);
	EgresoMovilidadService egresoMovilidadService = (EgresoMovilidadService)TumiFactory.get(EgresoMovilidadService.class);
	GiroMovilidadService giroMovilidadService = (GiroMovilidadService)TumiFactory.get(GiroMovilidadService.class);
	DocumentoGeneralService documentoGeneralService = (DocumentoGeneralService)TumiFactory.get(DocumentoGeneralService.class);
	CierreDiarioArqueoService cierreDiarioArqueoService = (CierreDiarioArqueoService)TumiFactory.get(CierreDiarioArqueoService.class);
	SaldoService saldoService = (SaldoService)TumiFactory.get(SaldoService.class);
	EgresoDocumentoSunatService egresoDocumentoSunatService = (EgresoDocumentoSunatService)TumiFactory.get(EgresoDocumentoSunatService.class);
	SolicitudPersonalService solicitudPersonalService = (SolicitudPersonalService)TumiFactory.get(SolicitudPersonalService.class);
	
	MovilidadBO boMovilidad = (MovilidadBO)TumiFactory.get(MovilidadBO.class);
	MovilidadDetalleBO boMovilidadDetalle = (MovilidadDetalleBO)TumiFactory.get(MovilidadDetalleBO.class);
	EgresoBO boEgreso = (EgresoBO)TumiFactory.get(EgresoBO.class);
	EgresoDetalleBO boEgresoDetalle = (EgresoDetalleBO)TumiFactory.get(EgresoDetalleBO.class);
	ControlFondoFijoBO boControlFondosFijos = (ControlFondoFijoBO)TumiFactory.get(ControlFondoFijoBO.class);
	SaldoBO boSaldo = (SaldoBO)TumiFactory.get(SaldoBO.class);
	SolicitudPersonalBO boSolicitudPersonal = (SolicitudPersonalBO)TumiFactory.get(SolicitudPersonalBO.class);
	SolicitudPersonalDetalleBO boSolicitudPersonalDetalle = (SolicitudPersonalDetalleBO)TumiFactory.get(SolicitudPersonalDetalleBO.class);
	SolicitudPersonalPagoBO boSolicitudPersonalPago = (SolicitudPersonalPagoBO)TumiFactory.get(SolicitudPersonalPagoBO.class);
	
	
    public Movilidad grabarMovilidad(Movilidad o) throws BusinessException{
    	Movilidad dto = null;
		try{
			dto = movilidadService.grabarMovilidad(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Movilidad> buscarMovilidad(Movilidad o) throws BusinessException{
    	List<Movilidad> lista = null;
		try{
			lista = movilidadService.buscarMovilidad(o);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
		return lista;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<MovilidadDetalle> getListaMovilidadDetalleValidar(Movilidad m, MovilidadDetalle md) throws BusinessException{
    	List<MovilidadDetalle> lista = null;
		try{
			lista = movilidadService.getListaMovilidadDetalleValidar(m, md);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
		return lista;
	}

    public Movilidad modificarMovilidad(Movilidad o) throws BusinessException{
    	Movilidad dto = null;
		try{
			dto = movilidadService.modificarMovilidad(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;
	}
    
    public Movilidad anularMovilidad(Movilidad o) throws BusinessException{
    	Movilidad dto = null;
		try{
			dto = movilidadService.anularMovilidad(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;
	}
	
    public ControlFondosFijos grabarAperturaFondoCheque(Egreso egreso) throws BusinessException{
    	ControlFondosFijos dto = null;
		try{
			dto = egresoService.grabarAperturaCheque(egreso);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;
	}
    
    public ControlFondosFijos grabarAperturaFondoRendicion(Egreso egreso, ControlFondosFijos rendicion) throws BusinessException{
    	ControlFondosFijos dto = null;
		try{
			dto = egresoService.grabarAperturaRendicion(egreso, rendicion);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<ControlFondosFijos> buscarControlFondosFijos(ControlFondosFijos o) throws BusinessException{
    	List<ControlFondosFijos> lista = null;
		try{
			lista = egresoService.buscarControlFondosFijos(o);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
		return lista;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Egreso getEgresoPorId(EgresoId pId) throws BusinessException{
    	Egreso dto = null;
		try{
			dto = boEgreso.getPorPk(pId);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
		return dto;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public ControlFondosFijos obtenerControlFondosFijosUltimo(List<ControlFondosFijos> l) throws BusinessException{
    	ControlFondosFijos dto = null;
		try{
			dto = egresoService.obtenerControlFondosFijosUltimo(l);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
		return dto;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<ControlFondosFijos> buscarApertura(ControlFondosFijos o, List<Sucursal> listaSucursal) throws BusinessException{
    	List<ControlFondosFijos> lista = null;
		try{
			lista = egresoService.buscarApertura(o, listaSucursal);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
		return lista;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<ControlFondosFijos> buscarCierre(ControlFondosFijos o, List<Sucursal> listaSucursal) throws BusinessException{
    	List<ControlFondosFijos> lista = null;
		try{
			lista = egresoService.buscarCierre(o, listaSucursal);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
		return lista;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public ControlFondosFijos modificarControlFondosFijos(ControlFondosFijos o) throws BusinessException{
    	ControlFondosFijos dto = null;
		try{
			dto = boControlFondosFijos.modificar(o);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
		return dto;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<EgresoDetalle> getListaEgresoDetallePorEgreso(Egreso o) throws BusinessException{
    	List<EgresoDetalle> lista = null;
		try{
			lista = boEgresoDetalle.getPorEgreso(o);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
		return lista;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Egreso getEgresoPorControlFondosFijos(ControlFondosFijos o) throws BusinessException{
    	Egreso egreso;
		try{
			egreso = boEgreso.getPorControlFondosFijos(o);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
		return egreso;
	}
    
    public Egreso grabarTransferenciaEntreCuentas(Egreso egreso) throws BusinessException{
    	Egreso dto = null;
		try{
			dto = egresoService.grabarTransferencia(egreso);
   		}catch(BusinessException e){
   			context.setRollbackOnly();
   			throw e;
   		}catch(Exception e){
   			context.setRollbackOnly();
   			throw new BusinessException(e);
   		}
		return dto;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Egreso> buscarTransferencia(Egreso egreso, Date dtFiltroDesde, Date dtFiltroHasta, List<Persona> listaPersona) throws BusinessException{
    	List<Egreso> lista = null;
		try{
			lista = egresoService.buscarTransferencia(egreso, dtFiltroDesde, dtFiltroHasta, listaPersona);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
		return lista;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Movilidad> buscarMovilidadPorIdPersona(Integer intIdPersona, Integer intIdEmpresa) throws BusinessException{
    	List<Movilidad> lista = null;
		try{
			lista = movilidadService.buscarMovilidadPorIdPersona(intIdPersona, intIdEmpresa);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
		return lista;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<ControlFondosFijos> buscarControlFondosParaGiro(Integer intIdSucursal, Integer intIdSubSucursal, Integer intIdEmpresa) throws BusinessException{
    	List<ControlFondosFijos> lista = null;
		try{
			lista = egresoService.buscarControlFondosParaGiro(intIdSucursal, intIdSubSucursal, intIdEmpresa);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
		return lista;
	}
    
    public Egreso grabarEgresoParaGiroPrestamo(Egreso egreso) throws BusinessException{
    	Egreso dto = null;
		try{			
			dto = egresoService.grabarEgresoParaGiroPrestamo(egreso);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public ControlFondosFijos getControlFondosFijosPorId(ControlFondosFijosId controlFondosFijosId) throws BusinessException{
    	ControlFondosFijos dto = null;
		try{
			dto = boControlFondosFijos.getPorPk(controlFondosFijosId);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
		return dto;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Egreso obtenerEgresoYLibroDiario(EgresoId egresoId) throws BusinessException{
    	Egreso dto = null;
		try{
			dto = egresoUtilService.obtenerEgresoYLibroDiario(egresoId);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
		return dto;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Integer obtenerMonedaDeCFF(ControlFondosFijos controlFondosFijos) throws BusinessException{
    	try{
			return egresoUtilService.obtenerMonedaDeCFF(controlFondosFijos);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Movilidad> buscarMovilidadParaGiroDesdeFondo(Integer intIdPersona, Integer intIdEmpresa) throws BusinessException{
    	List<Movilidad> lista = null;
		try{
			lista = movilidadService.buscarMovilidadParaGirar(intIdPersona, intIdEmpresa);
			
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
		return lista;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<ControlFondosFijos> obtenerListaNumeroApertura(Integer intTipoFondoFijo, Integer intAño, Integer intIdSucursal) 
    throws BusinessException{
    	List<ControlFondosFijos> lista = null;
		try{
			lista = egresoUtilService.obtenerListaNumeroApertura(intTipoFondoFijo, intAño, intIdSucursal);
			
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
		return lista;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Egreso> buscarEgresoParaFondosFijos(List<Persona>listaPersona, Egreso egresoFiltro,
		List<ControlFondosFijos> listaControlFondosFijos, Date dtFechaDesde, Date dtFechaHasta) throws BusinessException{
    	List<Egreso> lista = null;
		try{
			lista = egresoUtilService.buscarEgresoParaFondosFijos(listaPersona, egresoFiltro, listaControlFondosFijos, 
					dtFechaDesde, dtFechaHasta);
			
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
		return lista;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<EgresoDetalleInterfaz> cargarListaEgresoDetalleInterfaz(Movilidad movilidad)throws BusinessException{
    	List<EgresoDetalleInterfaz> lista = null;
		try{
			lista = egresoMovilidadService.cargarListaEgresoDetalleInterfaz(movilidad);
			
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
		return lista;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Egreso generarEgresoMovilidad(List<Movilidad> listaMovilidad, ControlFondosFijos controlFondosFijos, Usuario usuario) 
    throws BusinessException{
    	Egreso egreso = null;
		try{
			egreso = egresoMovilidadService.generarEgresoMovilidad(listaMovilidad, controlFondosFijos, usuario);
			
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
		return egreso;
	}
    
    public Egreso grabarGiroMovilidad(Egreso egreso, List<Movilidad> listaMovilidad)throws BusinessException{
    	Egreso dto = null;
		try{
			dto = giroMovilidadService.grabarGiroMovilidad(egreso, listaMovilidad);
			
   		}catch(BusinessException e){
   			context.setRollbackOnly();
   			throw e;
   		}catch(Exception e){
   			context.setRollbackOnly();
   			throw new BusinessException(e);
   		}
		return dto;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Movilidad> obtenerListaMovilidadPorEgreso(Egreso egreso)throws BusinessException{
    	List<Movilidad> lista = null;
		try{
			lista = movilidadService.obtenerListaMovilidadPorEgreso(egreso);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
		return lista;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Egreso getEgresoPorExpedienteCredito(ExpedienteCredito expedienteCredito)throws BusinessException{
    	Egreso egreso = null;
		try{
			egreso = egresoUtilService.obtenerEgresoPorExpedienteCredito(expedienteCredito);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
		return egreso;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Archivo getArchivoPorEgreso(Egreso egreso)throws BusinessException{
    	Archivo archivo = null;
		try{
			archivo = egresoUtilService.getArchivoPorEgreso(egreso);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
		return archivo;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<DocumentoGeneral> filtrarDuplicidadDocumentoGeneralParaEgreso(
    	List<DocumentoGeneral> listaDocumentoPorAgregar, Integer intTipoDocumentoAgregar, List<DocumentoGeneral>listaDocumentoAgregados)
    	throws BusinessException{
    	List<DocumentoGeneral> lista = null;
		try{
			lista = documentoGeneralService.filtrarDuplicidadDocumentoGeneralParaEgreso(listaDocumentoPorAgregar, 
					intTipoDocumentoAgregar, listaDocumentoAgregados);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
		return lista;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<ControlFondosFijos> obtenerControlFondosFijosACerrar(Integer intIdEmpresa,Integer intIdSucursal, Integer intIdSubsucursal)throws BusinessException{
    	List<ControlFondosFijos> lista = null;
		try{
			lista = cierreDiarioArqueoService.obtenerControlFondosFijosACerrar(intIdEmpresa, intIdSucursal, intIdSubsucursal);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
		return lista;
	}
    
    public CierreDiarioArqueo grabarCierreDiarioArqueo(CierreDiarioArqueo cierreDiarioArqueo) throws BusinessException{
    	CierreDiarioArqueo dto = null;
		try{
			dto = cierreDiarioArqueoService.grabarCierreDiarioArqueo(cierreDiarioArqueo);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<CierreDiarioArqueo> buscarCierreDiarioArqueo(CierreDiarioArqueo cierreDiarioArqueo, List<Persona> listaPersona)throws BusinessException{
    	List<CierreDiarioArqueo> lista = null;
		try{
			lista = cierreDiarioArqueoService.buscarCierreDiarioArqueo(cierreDiarioArqueo, listaPersona);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
		return lista;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void procesarSaldo(Date dtFechaInicio, Date dtFechaFin, Usuario usuario, List<Bancofondo> listaBanco, List<Bancofondo> listaFondo) 
    throws BusinessException{    	
		try{
			saldoService.procesarSaldo(dtFechaInicio, dtFechaFin, usuario, listaBanco, listaFondo);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Saldo obtenerSaldoUltimaFechaRegistro(Integer intIdEmpresa) throws BusinessException{
    	Saldo dto = null;
		try{
			dto = boSaldo.getSaldoUltimaFechaRegistro(intIdEmpresa);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
		return dto;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Date obtenerUltimaFechaSaldo(Integer intIdEmpresa) throws BusinessException{
    	Date dto = null;
		try{
			dto = saldoService.obtenerUltimaFechaSaldo(intIdEmpresa);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
		return dto;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Saldo> buscarSaldo(Saldo saldo)throws BusinessException{
    	List<Saldo> lista = null;
		try{
			lista = saldoService.buscarSaldo(saldo);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
		return lista;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Egreso generarEgresoMovilidadCheque(List<Movilidad> listaMovilidad, Bancocuenta bancoCuenta, Usuario usuario, Integer intNumeroCheque, 
    	Integer intTipoDocumentoValidar)  throws BusinessException{
    	Egreso egreso = null;
		try{
			egreso = egresoMovilidadService.generarEgresoMovilidadCheque(listaMovilidad, bancoCuenta, usuario, intNumeroCheque, intTipoDocumentoValidar);
			
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
		return egreso;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Egreso> buscarEgresoParaCaja(List<Persona>listaPersona, Egreso egresoFiltro, Date dtDesdeFiltro, Date dtHastaFiltro)throws BusinessException{
    	List<Egreso> lista = null;
		try{
			lista = egresoUtilService.buscarEgresoParaCaja(listaPersona, egresoFiltro, dtDesdeFiltro, dtHastaFiltro);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
		return lista;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void anularSaldo(Integer intIdEmpresa, Date dtFechaInicio)throws BusinessException{
    	try{
			saldoService.anularSaldo(intIdEmpresa, dtFechaInicio);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Egreso> buscarEgresoParaTelecredito(Egreso egreso)throws BusinessException{
    	List<Egreso> lista = null;
		try{
			lista = egresoUtilService.buscarEgresoParaTelecredito(egreso);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
		return lista;
	}
    
    public Egreso grabarTransferenciaTelecredito(Egreso egreso, List<Egreso> listaEgresoTelecredito) throws BusinessException{
    	Egreso dto = null;
		try{
			dto = egresoService.grabarTransferenciaTelecredito(egreso, listaEgresoTelecredito);
   		}catch(BusinessException e){
   			context.setRollbackOnly();
   			throw e;
   		}catch(Exception e){
   			context.setRollbackOnly();
   			throw new BusinessException(e);
   		}
		return dto;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Egreso generarEgresoMovilidadTransferencia(List<Movilidad> listaMovilidad, Bancocuenta bancoCuenta, Usuario usuario, Integer intNumeroCheque, 
    	Integer intTipoDocumentoValidar, CuentaBancaria cuentaBancariaDestino)  throws BusinessException{
    	Egreso egreso = null;
		try{
			egreso = egresoMovilidadService.generarEgresoMovilidadTransferencia(listaMovilidad, bancoCuenta, usuario, intNumeroCheque, 
					intTipoDocumentoValidar, cuentaBancariaDestino);
			
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
		return egreso;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public ControlFondosFijos obtenerControlFondosFijosPorEgresoDetalle(EgresoDetalle egresoDetalle) throws BusinessException{
    	ControlFondosFijos controlFondosFijos = null;
		try{
			controlFondosFijos = egresoUtilService.obtenerControlFondosFijosPorEgresoDetalle(egresoDetalle);			
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
		return controlFondosFijos;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Egreso> obtenerListaEgresoPorControlFondosFijos(ControlFondosFijos controlFondosFijos)throws BusinessException{
    	List<Egreso> lista = null;
		try{
			lista = boEgreso.getListaPorControlFondosFijos(controlFondosFijos);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
		return lista;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<EgresoDetalleInterfaz> cargarListaEgresoDetalleInterfaz(DocumentoSunat documentoSunat)throws BusinessException{
    	List<EgresoDetalleInterfaz> lista = null;
		try{
			lista = egresoDocumentoSunatService.cargarListaEgresoDetalleInterfaz(documentoSunat);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
		return lista;
	}
    
    public Egreso grabarGiroDocumentoSunat(List<EgresoDetalleInterfaz> listaEgresoDetalleInterfaz, ControlFondosFijos controlFondosFijos, 
    	Usuario usuario)throws BusinessException{
    	Egreso dto = null;
		try{
			dto = egresoDocumentoSunatService.grabarGiroDocumentoSunat(listaEgresoDetalleInterfaz, controlFondosFijos, usuario);			
   		}catch(BusinessException e){
   			context.setRollbackOnly();
   			throw e;
   		}catch(Exception e){
   			context.setRollbackOnly();
   			throw new BusinessException(e);
   		}
		return dto;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<DocumentoSunat> obtenerListaDocumentoSunatPorEgreso(Egreso egreso)throws BusinessException{
    	List<DocumentoSunat> lista = null;
		try{
			lista = egresoDocumentoSunatService.obtenerListaDocumentoSunatPorEgreso(egreso);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
		return lista;
	}
    
    public SolicitudPersonal grabarSolicitudPersonal(SolicitudPersonal solicitudPersonal)throws BusinessException{
    	SolicitudPersonal dto = null;
    	try{
    		dto = solicitudPersonalService.grabarSolicitudPersonal(solicitudPersonal);
       	}catch(BusinessException e){
       		context.setRollbackOnly();
       		throw e;
       	}catch(Exception e){
       		context.setRollbackOnly();
       		throw new BusinessException(e);
       	}
    	return dto;
    }
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<SolicitudPersonal> buscarSolicitudPersonal(SolicitudPersonal solicitudPersonalFiltro, List<Persona> listaPersonaFiltro)
    throws BusinessException{
    	List<SolicitudPersonal> lista = null;
		try{
			lista = solicitudPersonalService.buscarSolicitudPersonal(solicitudPersonalFiltro, listaPersonaFiltro);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
		return lista;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public SolicitudPersonal modificarSolicitudPersonaDirecto(SolicitudPersonal solicitudPersonal) throws BusinessException{
    	SolicitudPersonal dto = null;
		try{
			dto = boSolicitudPersonal.modificar(solicitudPersonal);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
		return dto;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<SolicitudPersonalDetalle> getListaSolicitudPersonalDetalle(SolicitudPersonal solicitudPersonal)throws BusinessException{
    	List<SolicitudPersonalDetalle> lista = null;
		try{
			lista = boSolicitudPersonalDetalle.getPorSolicitudPersonal(solicitudPersonal);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
		return lista;
	}
    
    public SolicitudPersonal modificarSolicitudPersonal(SolicitudPersonal solicitudPersonal)throws BusinessException{
    	SolicitudPersonal dto = null;
    	try{
    		dto = solicitudPersonalService.modificarSolicitudPersonal(solicitudPersonal);
       	}catch(BusinessException e){
       		context.setRollbackOnly();
       		throw e;
       	}catch(Exception e){
       		context.setRollbackOnly();
       		throw new BusinessException(e);
       	}
    	return dto;
    }
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<SolicitudPersonalPago> getListaSolicitudPersonalPago(SolicitudPersonal solicitudPersonal)throws BusinessException{
    	List<SolicitudPersonalPago> lista = null;
		try{
			lista = boSolicitudPersonalPago.getPorSolicitudPersonal(solicitudPersonal);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
		return lista;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Egreso procesarItems(Egreso egreso)throws BusinessException{
    	Egreso dto = null;
		try{
			dto = egresoUtilService.procesarItems(egreso);
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
		return dto;
	}

	public ControlFondoFijoAnula grabarAnulaCierre(ControlFondosFijos o, Integer intEmpresa, Integer intPersona, String strObservacion, Integer intParaTipoAnulaFondo) throws BusinessException{
	ControlFondoFijoAnula dto = null;
		try{
			dto = egresoService.grabarAnulaCierre(o, intEmpresa, intPersona, strObservacion, intParaTipoAnulaFondo);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;
	}    
}
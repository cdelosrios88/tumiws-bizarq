package pe.com.tumi.servicio.prevision.facade;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.Cuenta;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.CuentaId;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.facade.TumiFacade;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.parametro.general.domain.Archivo;
import pe.com.tumi.parametro.general.domain.ArchivoId;
import pe.com.tumi.parametro.general.facade.GeneralFacadeRemote;
import pe.com.tumi.persona.core.domain.CuentaBancaria;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.seguridad.login.domain.Usuario;
import pe.com.tumi.servicio.configuracion.domain.ConfServDetalleId;
import pe.com.tumi.servicio.prevision.bo.AutorizaPrevisionBO;
import pe.com.tumi.servicio.prevision.bo.BeneficiarioPrevisionBO;
import pe.com.tumi.servicio.prevision.bo.EstadoPrevisionBO;
import pe.com.tumi.servicio.prevision.bo.ExpedientePrevisionBO;
import pe.com.tumi.servicio.prevision.bo.FallecidoPrevisionBO;
import pe.com.tumi.servicio.prevision.bo.RequisitoPrevisionBO;
import pe.com.tumi.servicio.prevision.domain.AutorizaPrevision;
import pe.com.tumi.servicio.prevision.domain.BeneficiarioPrevision;
import pe.com.tumi.servicio.prevision.domain.EstadoPrevision;
import pe.com.tumi.servicio.prevision.domain.ExpedientePrevision;
import pe.com.tumi.servicio.prevision.domain.ExpedientePrevisionId;
import pe.com.tumi.servicio.prevision.domain.FallecidoPrevision;
import pe.com.tumi.servicio.prevision.domain.RequisitoPrevision;
import pe.com.tumi.servicio.prevision.domain.composite.ExpedientePrevisionComp;
import pe.com.tumi.servicio.prevision.domain.composite.RequisitoPrevisionComp;
import pe.com.tumi.servicio.prevision.domain.composite.RequisitoPrevisionComp2;
import pe.com.tumi.servicio.prevision.service.EgresoPrevisionService;
import pe.com.tumi.servicio.prevision.service.GiroPrevisionService;
import pe.com.tumi.servicio.prevision.service.solicitudPrevisionService;
import pe.com.tumi.servicio.solicitudPrestamo.domain.EgresoDetalleInterfaz;
import pe.com.tumi.tesoreria.banco.domain.Bancocuenta;
import pe.com.tumi.tesoreria.egreso.domain.ControlFondosFijos;
import pe.com.tumi.tesoreria.egreso.domain.Egreso;

@Stateless
public class PrevisionFacade extends TumiFacade implements PrevisionFacadeRemote, PrevisionFacadeLocal {
	
	private GiroPrevisionService giroPrevisionService = (GiroPrevisionService)TumiFactory.get(GiroPrevisionService.class);
	private EgresoPrevisionService egresoPrevisionService = (EgresoPrevisionService)TumiFactory.get(EgresoPrevisionService.class);
	private BeneficiarioPrevisionBO boBeneficiarioPrevision = (BeneficiarioPrevisionBO)TumiFactory.get(BeneficiarioPrevisionBO.class);	
	private ExpedientePrevisionBO boExpedientePrevision = (ExpedientePrevisionBO)TumiFactory.get(ExpedientePrevisionBO.class);	
	private solicitudPrevisionService solicitudPrevisionService = (solicitudPrevisionService)TumiFactory.get(solicitudPrevisionService.class);
	private FallecidoPrevisionBO boFallecidoPrevision = (FallecidoPrevisionBO)TumiFactory.get(FallecidoPrevisionBO.class);
	private AutorizaPrevisionBO boAutorizaPrevision = (AutorizaPrevisionBO)TumiFactory.get(AutorizaPrevisionBO.class);
	//private AutorizaVerificaPrevisionBO boAutorizaVerificaPrevision = (AutorizaVerificaPrevisionBO)TumiFactory.get(AutorizaVerificaPrevisionBO.class);
	//AUTOR Y FECHA CREACION: JCHAVEZ / 23-09-2013
	private EstadoPrevisionBO boEstadoPrevision = (EstadoPrevisionBO)TumiFactory.get(EstadoPrevisionBO.class);
	private RequisitoPrevisionBO boRequisitoPrevision = (RequisitoPrevisionBO)TumiFactory.get(RequisitoPrevisionBO.class);
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<ExpedientePrevision> buscarExpedienteParaGiro(List<Persona> listaPersonaFiltro, Integer intTipoCreditoFiltro,
			EstadoPrevision estadoPrevisionFiltro, Integer intItemExpedienteFiltro, Integer intTipoBusquedaSucursal, 
			Integer intIdSucursalFiltro, Integer intIdSubsucursalFiltro) throws BusinessException{
		List<ExpedientePrevision> lista = null;
   		try{
   			lista = giroPrevisionService.buscarExpedienteParaGiro(listaPersonaFiltro, intTipoCreditoFiltro, estadoPrevisionFiltro,
   					intItemExpedienteFiltro, intTipoBusquedaSucursal, intIdSucursalFiltro, intIdSubsucursalFiltro);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return lista;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<BeneficiarioPrevision> getListaBeneficiarioPrevision(ExpedientePrevision expedientePrevision) throws BusinessException{
		List<BeneficiarioPrevision> lista = null;
   		try{
   			lista = boBeneficiarioPrevision.getPorExpediente(expedientePrevision);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return lista;
	}
    
    public ExpedientePrevision grabarGiroPrevision(ExpedientePrevision expedientePrevision) throws BusinessException{
    	ExpedientePrevision dto = null;
		try{
			dto = giroPrevisionService.grabarGiroPrevision(expedientePrevision);
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
	public List<ExpedientePrevision> buscarExpedienteParaGiroDesdeFondo(Integer IntIdPersona, Integer intIdEmpresa, Integer intTipoDocumento) 
		throws BusinessException{
		List<ExpedientePrevision> lista = null;
   		try{
   			lista = giroPrevisionService.buscarExpedienteParaGiroDesdeFondo(IntIdPersona, intIdEmpresa, intTipoDocumento);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return lista;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<EgresoDetalleInterfaz> cargarListaEgresoDetalleInterfaz(ExpedientePrevision expedientePrevision, 
			BeneficiarioPrevision beneficiarioPrevision) throws BusinessException{
		List<EgresoDetalleInterfaz> lista = null;
   		try{
   			lista = egresoPrevisionService.cargarListaEgresoDetalleInterfaz(expedientePrevision, beneficiarioPrevision);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return lista;
	}
	//Usado en la generación de egreso de previsión, durante la grabación del giro
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Egreso generarEgresoPrevision(ExpedientePrevision expedientePrevision, ControlFondosFijos controlFondosFijos, Usuario usuario) 
		throws BusinessException{
		Egreso egreso = null;
   		try{
   			egreso = egresoPrevisionService.generarEgresoPrevision(expedientePrevision, controlFondosFijos, usuario);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return egreso;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public BeneficiarioPrevision getBeneficiarioPrevisionPorEgreso(Egreso egreso) throws BusinessException{
		BeneficiarioPrevision beneficiarioPrevision = null;
   		try{
   			beneficiarioPrevision = boBeneficiarioPrevision.getPorEgreso(egreso);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return beneficiarioPrevision;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public ExpedientePrevision getExpedientePrevisionPorBeneficiarioPrevision(BeneficiarioPrevision beneficiarioPrevision) 
		throws BusinessException{
		ExpedientePrevision expedientePrevision = null;
   		try{
   			expedientePrevision = boExpedientePrevision.getPorBeneficiarioPrevision(beneficiarioPrevision);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return expedientePrevision;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<ExpedientePrevision> getListaExpedienteCreditoCompDeBusqueda() throws BusinessException{
		List<ExpedientePrevision> lista = null;
		try{
			lista = solicitudPrevisionService.getListaExpedientePrevisionBusqueda();
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<ExpedientePrevisionComp> getListaExpedienteCreditoCompDeBusqueda(ExpedientePrevisionComp expedientePrevisionComp) throws BusinessException{
		List<ExpedientePrevisionComp> lista = null;
		ExpedientePrevisionComp expPrevComp = new ExpedientePrevisionComp();
		try{
			lista = solicitudPrevisionService.getListaExpedientePrevisionCompBusqueda(expPrevComp);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public ExpedientePrevision grabarExpedientePrevision(ExpedientePrevision o) throws BusinessException{
		ExpedientePrevision prevision = null;
		try{
			prevision = solicitudPrevisionService.grabarExpedientePrevision(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return prevision;
	}
	
	public ExpedientePrevision modificarExpedientePrevision(ExpedientePrevision o) throws BusinessException{
		ExpedientePrevision prevision = null;
		try{
			prevision = solicitudPrevisionService.modificarExpedientePrevision(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return prevision;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<EstadoPrevision> getListaEstadosPrevisionPorExpediente(ExpedientePrevision expedientePrevision) throws BusinessException{
		List<EstadoPrevision> lista = null;
   		try{
   			lista = solicitudPrevisionService.getListaEstadoPrevisionPorExpedienteId(expedientePrevision);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return lista;
	}
	
	
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<ExpedientePrevisionComp> getListaExpedienteCreditoPorEmpresaYCuenta(ExpedientePrevisionId expedientePrevisionId) throws BusinessException{
		List<ExpedientePrevisionComp> lista = null;
   		try{
   			lista = solicitudPrevisionService.getListaExpedienteCreditoPorEmpresaYCuenta(expedientePrevisionId);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return lista;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public ExpedientePrevision getExpedientePrevisionPorId(ExpedientePrevisionId expedientePrevisionId) throws BusinessException{
		ExpedientePrevision expedientePrevision = null;
		try{
			expedientePrevision = boExpedientePrevision.getPorPk(expedientePrevisionId);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return expedientePrevision;
	}
	
	public ExpedientePrevision getExpedientePrevisionCompletoPorIdExpedientePrevision(ExpedientePrevisionId o) throws BusinessException{
		ExpedientePrevision prevision = null;
		try{
			prevision = solicitudPrevisionService.getExpedientePrevisionCompletoPorIdExpedientePrevision(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return prevision;
	}
	/**
	 * JCHAVEZ 18.02.2014
	 * Se modifico el procedimiento para generar el egreso segun las ultimas indicaciones, contemplando los campos a grabar
	 * JCHAVEZ 15.05.2014
	 * Modificacion . 
	 */	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Egreso generarEgresoPrevisionCheque(ExpedientePrevision expedientePrevision, Bancocuenta bancoCuenta, Usuario usuario, 
		Integer intNumeroCheque, Integer intTipoDocumentoValidar)throws BusinessException{
		Egreso egreso = null;
   		try{
   			egreso = egresoPrevisionService.generarEgresoPrevisionCheque(expedientePrevision, bancoCuenta, usuario, intNumeroCheque, intTipoDocumentoValidar);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return egreso;
	}
	/**
	 * modificado jchavez 15.05.2014
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Egreso generarEgresoPrevisionTransferencia(ExpedientePrevision expedientePrevision, Bancocuenta bancoCuentaOrigen, Usuario usuario, 
		Integer intNumeroTransferencia, Integer intTipoDocumentoValidar, CuentaBancaria cuentaBancariaDestino)throws BusinessException{
		Egreso egreso = null;
   		try{
   			egreso = egresoPrevisionService.generarEgresoPrevisionTransferenciaTerceros(expedientePrevision, bancoCuentaOrigen, usuario, intNumeroTransferencia, intTipoDocumentoValidar, cuentaBancariaDestino);
//   			egreso = egresoPrevisionService.generarEgresoPrevisionTransferencia(expedientePrevision, bancoCuentaOrigen, usuario, 
//   					intNumeroTransferencia,	intTipoDocumentoValidar, cuentaBancariaDestino);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return egreso;
	}	
	
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<ExpedientePrevision> getListaExpedientePrevisionPorCuenta(Cuenta cuenta) throws BusinessException{
		List<ExpedientePrevision> lista = null;
		try{
			lista = solicitudPrevisionService.getListaExpedientePrevisionPorCuenta(cuenta);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public EstadoPrevision getListaExpedientePrevisionPorCuenta(ExpedientePrevision expediente) throws BusinessException{
		EstadoPrevision estado = null;
		try{
			estado = solicitudPrevisionService.getUltimoEstadoExpedientePrevision(expediente);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return estado;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<ExpedientePrevision> buscarExpedientePrevisionFiltro(List<Persona> listaPersonaFiltro, Integer intTipoCreditoFiltro,Integer intSubTipoCreditoFiltro,
			EstadoPrevision estadoPrevisionFiltro, Integer intItemExpedienteFiltro, Integer intTipoBusquedaSucursal, 
			Integer intIdSucursalFiltro, Integer intIdSubsucursalFiltro) throws BusinessException{
		List<ExpedientePrevision> lista = null;
   		try{
   			lista = solicitudPrevisionService.buscarExpedientePrevisionFiltro(listaPersonaFiltro, intTipoCreditoFiltro, intSubTipoCreditoFiltro, estadoPrevisionFiltro, intItemExpedienteFiltro, intTipoBusquedaSucursal, intIdSucursalFiltro, intIdSubsucursalFiltro);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return lista;
	}

	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public EstadoPrevision getUltimoEstadoExpedientePrevision(ExpedientePrevision expediente) throws BusinessException{
		EstadoPrevision estado = null;
		try{
			estado = solicitudPrevisionService.getUltimoEstadoExpedientePrevision(expediente);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return estado;
	}
	
	// fallecidos de prevision

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<FallecidoPrevision> getListaFallecidosPrevisionPorExpediente(ExpedientePrevision expedientePrevision) throws BusinessException{
		List<FallecidoPrevision> lista = null;
   		try{
   			lista = boFallecidoPrevision.getPorExpediente(expedientePrevision);
   				//solicitudPrevisionService.getli  .getPorExpediente(expedientePrevision);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return lista;
	}
	//autor y fecha rVillarreal 18/06/2014
	
	 public List<FallecidoPrevision> getListaNombreCompletoAes(Integer intPersEmpresaPk, Integer intCuenta, Integer intExpediente) throws BusinessException{
			List<FallecidoPrevision> lista = null;
	    	try{
	    		lista = boFallecidoPrevision.getListaNombreCompletoAes(intPersEmpresaPk, intCuenta, intExpediente);
			}catch(BusinessException e){
				throw e;
			}catch(Exception e){
				throw new BusinessException(e);
			}
			return lista;
		}
	 //autor y fecha rVillarreal 19/06/2014
	 public List<FallecidoPrevision> getListaVinculoAes(Integer intPersEmpresaPk, Integer intCuenta, Integer intExpediente) throws BusinessException{
			List<FallecidoPrevision> lista = null;
	    	try{
	    		lista = boFallecidoPrevision.getListaVinculoAes(intPersEmpresaPk, intCuenta, intExpediente);
			}catch(BusinessException e){
				throw e;
			}catch(Exception e){
				throw new BusinessException(e);
			}
			return lista;
		}
	/*
	public List<FallecidoPrevision> getListaFallecidosPrevisiondf(ExpedientePrevision expedientePrevision) throws BusinessException{
		List<FallecidoPrevision> lista = null;
   		try{
   			lista = boFallecidoPrevision.getPorExpediente(expedientePrevision);
   				//solicitudPrevisionService.getli  .getPorExpediente(expedientePrevision);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return lista;
	}*/
	
	

	
	
	/*	public List<ExpedientePrevisionComp> getListaAutorizacionPrevisionCompDeBusqueda(ExpedientePrevisionComp o) throws BusinessException{
		List<ExpedientePrevisionComp> lista = null;
		try{
			lista = solicitudPrestamoService.getListaAutorizacionCreditoCompDeBusqueda(o);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}*/
	
	
	
	/*
	 * COMENTADO 31/01/2013
	 * 
	public ExpedientePrevision grabarAutorizacionPrevision(ExpedientePrevision o) throws BusinessException{
    	ExpedientePrevision prevision = null;
    	try{
			prevision = solicitudPrevisionService.grabarAutorizacionPrevision(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return prevision;
	}

	public List<AutorizaPrevision> getListaAutorizaPrevisionPorPkExpediente(ExpedientePrevisionId pId) throws BusinessException{
		List<AutorizaPrevision> lista = null;
		try{
			lista = boAutorizaPrevision.getListaPorPkExpedientePrevision(pId);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	*/
	
	public List<AutorizaPrevision> getListaVerificaPrevisionPorPkExpediente(ExpedientePrevisionId pId) throws BusinessException{
		List<AutorizaPrevision> lista = null;
		try{
			lista = boAutorizaPrevision.getListaPorPkExpedientePrevision(pId);
		}catch(BusinessException e){
			System.out.println("BusinessException ---> "+e);
			throw e;
		}catch(Exception e1){
			System.out.println("ExceptionException ---> "+e1);
			throw new BusinessException(e1);
		}
		return lista;
	}
	
	

	/**
		 * Cambia los estados a los autorizaCredito, Archivos Infocorp, reniec, AutorizaVerfifica y
		 * a los requiisitos de solicitud asociados al expediente.
		 * Se aplicara cuando el expediente pase a aestado Observado.
		 */
	public void eliminarVerificaAutorizacionAdjuntosPorObservacion(ExpedientePrevision pExpedientePrev) throws BusinessException{
			
			try {
				solicitudPrevisionService.eliminarVerificaAutorizacionAdjuntosPorObservacion(pExpedientePrev);
				
			}catch(BusinessException e){
				context.setRollbackOnly();
				throw e;
			}catch(Exception e){
				context.setRollbackOnly();
				throw new BusinessException(e);
			}
		}
	/**
	 * AUTOR Y FECHA CREACION: JCHAVEZ / 23-09-2013
     * OBTENER EXPEDIENTE PREVISION (SERVICIO) POR CUENTA SOCIO
	 * @param cuenta
	 * @return
	 * @throws BusinessException
	 */
	public List<ExpedientePrevision> getListaPorCuenta (Cuenta cuenta) throws BusinessException {
		List<ExpedientePrevision> lista = null;
    	try{
    		lista = boExpedientePrevision.getListaPorCuenta(cuenta);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	/**
	 * AUTOR Y FECHA CREACION: JCHAVEZ / 23-09-2013
     * OBTENER ESTADO PREVISION (SERVICIO) POR EXPEDIENTE PREVISION
	 * @param expedientePrevision
	 * @return
	 * @throws BusinessException
	 */
	public List<EstadoPrevision> getEstPrevPorExpediente(ExpedientePrevision expedientePrevision) throws BusinessException {
		List<EstadoPrevision> lista = null;
    	try{
    		lista = boEstadoPrevision.getPorExpediente(expedientePrevision);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	/**
	 * AUTOR Y FECHA CREACION: JCHAVEZ / 23-09-2013
     * OBTENER BENEFICIARIO PREVISION (SERVICIO) POR EXPEDIENTE PREVISION
	 * @param expedientePrevision
	 * @return
	 * @throws BusinessException
	 */
	public List<BeneficiarioPrevision> getBenefPorExpediente(ExpedientePrevision expedientePrevision) throws BusinessException{
		List<BeneficiarioPrevision> lista = null;
    	try{
    		lista = boBeneficiarioPrevision.getPorExpediente(expedientePrevision);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	/**
	 * AUTOR Y FECHA CREACION: JCHAVEZ / 23-09-2013
     * OBTENER FALLECIDO PREVISION (SERVICIO) POR EXPEDIENTE PREVISION
	 * @param expedientePrevision
	 * @return
	 * @throws BusinessException
	 */
	public List<FallecidoPrevision> getFallecidoPorExpediente(ExpedientePrevision expedientePrevision) throws BusinessException{
		List<FallecidoPrevision> lista = null;
    	try{
    		lista = boFallecidoPrevision.getPorExpediente(expedientePrevision);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	/**
	 * Recupera expedientes de prevision segun filtros de busqueda
	 * @param expPrevisionComp
	 * @return
	 * @throws BusinessException
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<ExpedientePrevisionComp> getListaBusqExpPrevFiltros(ExpedientePrevisionComp expPrevisionComp)throws BusinessException{
		List<ExpedientePrevisionComp> lista = null;
   		try{
   			lista = solicitudPrevisionService.getListaBusqExpPrevFiltros(expPrevisionComp);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return lista;
	}
	
	
	/**
	 * Recupera expedientes de prevision para autorizacion segun filtros de busqueda
	 * @param expPrevisionComp
	 * @return
	 * @throws BusinessException
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<ExpedientePrevisionComp> getListaBusqAutExpPrevFiltros(ExpedientePrevisionComp expPrevisionComp)throws BusinessException{
		List<ExpedientePrevisionComp> lista = null;
   		try{
   			lista = solicitudPrevisionService.getListaBusqAutExpPrevFiltros(expPrevisionComp);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return lista;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Egreso generarEgresoGiroPrevision(ExpedientePrevision expedientePrevision, ControlFondosFijos controlFondosFijos, Usuario usuario) 
		throws BusinessException{
		Egreso egreso = null;
   		try{
   			egreso = egresoPrevisionService.generarEgresoGiroPrevision(expedientePrevision, controlFondosFijos, usuario);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return egreso;
	}
	/**
	 * JCHAVEZ 28.01.2014
	 * Recupera lista de expediente prevision y su ultimo estado credito.
	 */
	public List<ExpedientePrevision> getUltimoEstadoExpPrev (CuentaId cuentaId) throws BusinessException {
		List<ExpedientePrevision> lista = null;
    	try{
    		lista = boExpedientePrevision.getUltimoEstadoExpPrev(cuentaId);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}

	/**
	 * JCHAVEZ 05.02.2014
	 * Recupera lista de expediente PREVISION por pk del expediente PREVISION y requisito detalle
	 * @param pId
	 * @param rId
	 * @return
	 * @throws BusinessException
	 */
	public List<RequisitoPrevision> getListaPorPkExpedientePrevisionYRequisitoDetalle(ExpedientePrevisionId pId, ConfServDetalleId rId, Integer intBeneficiario) throws BusinessException{
		List<RequisitoPrevision>  lista = null;
		try{
			lista = boRequisitoPrevision.getListaPorPkExpedientePrevisionYRequisitoDetalle(pId, rId, intBeneficiario);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	/**
	 * JCHAVEZ 05.02.2014
	 * Recupera el archivo del requisito PREVISION registrado.
	 * @param reqPrev
	 * @return
	 * @throws BusinessException
	 */
    public Archivo getArchivoPorRequisitoPrevision(RequisitoPrevision reqPrev)throws BusinessException{
    	Archivo archivo = null;
		try{
			GeneralFacadeRemote generalFacade = (GeneralFacadeRemote) EJBFactory.getRemote(GeneralFacadeRemote.class);
			
			ArchivoId archivoId = new ArchivoId();
			archivoId.setIntParaTipoCod(reqPrev.getIntParaTipoArchivo());
			archivoId.setIntItemArchivo(reqPrev.getIntParaItemArchivo());
			archivoId.setIntItemHistorico(reqPrev.getIntParaItemHistorico());
			archivo = generalFacade.getArchivoPorPK(archivoId);			
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
		return archivo;
	}
    
	/**
	 * JCHAVEZ 06.02.2014
	 * Grabacion del requisito en giro previsión, aplican a giros que superen el monto maximo asignado al fondo.
	 * @param reqPrev
	 * @return
	 * @throws BusinessException
	 */
	public RequisitoPrevision grabarRequisito(RequisitoPrevision reqPrev) throws BusinessException{
		RequisitoPrevision dto = null;
		try{
			dto = boRequisitoPrevision.grabar(reqPrev);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;
	}
	/**
	 * JCHAVEZ 06.02.2014
	 * Procedimiento que recupera los Requisitos necesarios para el giro en Giro PREVISION
	 * @param expPrev
	 * @return
	 * @throws BusinessException
	 */
	public List<RequisitoPrevisionComp> getRequisitoGiroPrevision(ExpedientePrevision expPrev, Integer intIdMaestro, Integer intParaTipoDescripcion) throws BusinessException{
		List<RequisitoPrevisionComp> lista = null;
		try{
			lista = boExpedientePrevision.getRequisitoGiroPrevision(expPrev,intIdMaestro,intParaTipoDescripcion);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
    /**
     * Procedimiento usado en "Banco", para solucionar problema stub con RequisitoPrevisionComp - atributo MyFile.
     */
	public List<RequisitoPrevisionComp2> getRequisitoGiroPrevisionBanco(ExpedientePrevision expPrev, Integer intIdMaestro, Integer intParaTipoDescripcion) throws BusinessException{
		List<RequisitoPrevisionComp> lista = null;
		List<RequisitoPrevisionComp2> lstReqPrev = new ArrayList<RequisitoPrevisionComp2>();
		RequisitoPrevisionComp2 reqPrev = new RequisitoPrevisionComp2();
		try{
			lista = boExpedientePrevision.getRequisitoGiroPrevision(expPrev,intIdMaestro,intParaTipoDescripcion);
			for (RequisitoPrevisionComp o : lista) {
				reqPrev = new RequisitoPrevisionComp2();
				reqPrev.setIntEmpresa(o.getIntEmpresa());
				reqPrev.setIntItemRequisito(o.getIntItemRequisito());
				reqPrev.setIntItemRequisitoDetalle(o.getIntItemRequisitoDetalle());
				reqPrev.setStrDescripcionRequisito(o.getStrDescripcionRequisito());
				reqPrev.setIntParaTipoOperacion(o.getIntParaTipoOperacion());
				reqPrev.setIntParaSubTipoOperacion(o.getIntParaSubTipoOperacion());
				lstReqPrev.add(reqPrev);
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lstReqPrev;
	}
	
	/**
	 * jchavez 15.05.2014
	 * grabacion de giro prevision por tesoreria (banco y fondo fijo)
	 */
    public ExpedientePrevision grabarGiroPrevisionPorTesoreria(ExpedientePrevision expedientePrevision) throws BusinessException{
    	ExpedientePrevision dto = null;
		try{
			dto = giroPrevisionService.grabarGiroPrevisionPorTesoreria(expedientePrevision);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;
	}
    //autor rVillarreal 21.05.2014
    public List<RequisitoPrevisionComp> getRequisitoGiroPrevisionPKrequisitoDetalle(Integer intPersEmpresaPk, Integer intItemReqAut, Integer intItemReqAutEstDetalle) throws BusinessException{
		List<RequisitoPrevisionComp> lista = null;
		try{
			lista = boExpedientePrevision.getRequisitoGiroPrevisionPKrequisitoDetalle(intPersEmpresaPk,intItemReqAut,intItemReqAutEstDetalle);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
    //autor rVillarreal 04.06.2014
    public List<BeneficiarioPrevision> getListaNombreCompletoBeneficiario(Integer intPersEmpresaPk, Integer intCuenta, Integer intExpediente) throws BusinessException{
		List<BeneficiarioPrevision> lista = null;
    	try{
    		lista = boBeneficiarioPrevision.getListaNombreCompletoBeneficiario(intPersEmpresaPk, intCuenta, intExpediente);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
  //autor rVillarreal 04.06.2014
    public List<BeneficiarioPrevision> getListaVinculo(Integer intPersEmpresaPk, Integer intCuenta, Integer intExpediente) throws BusinessException{
		List<BeneficiarioPrevision> lista = null;
    	try{
    		lista = boBeneficiarioPrevision.getListaVinculo(intPersEmpresaPk, intCuenta, intExpediente);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
}
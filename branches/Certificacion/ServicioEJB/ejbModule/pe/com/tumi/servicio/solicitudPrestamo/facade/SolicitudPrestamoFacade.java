package pe.com.tumi.servicio.solicitudPrestamo.facade;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import pe.com.tumi.contabilidad.cierre.domain.LibroDiario;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.Cuenta;
import pe.com.tumi.credito.socio.core.domain.SocioComp;
import pe.com.tumi.credito.socio.core.domain.SocioEstructuraPK;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.facade.TumiFacade;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.movimiento.concepto.domain.Cronograma;
import pe.com.tumi.movimiento.concepto.domain.Expediente;
import pe.com.tumi.movimiento.concepto.domain.composite.ExpedienteComp;
import pe.com.tumi.seguridad.login.domain.Usuario;
import pe.com.tumi.servicio.solicitudPrestamo.bo.AutorizaCreditoBO;
import pe.com.tumi.servicio.solicitudPrestamo.bo.AutorizaVerificacionBO;
import pe.com.tumi.servicio.solicitudPrestamo.bo.CapacidadCreditoBO;
import pe.com.tumi.servicio.solicitudPrestamo.bo.EstadoCreditoBO;
import pe.com.tumi.servicio.solicitudPrestamo.bo.ExpedienteCreditoBO;
import pe.com.tumi.servicio.solicitudPrestamo.bo.GarantiaCreditoBO;
import pe.com.tumi.servicio.solicitudPrestamo.domain.AutorizaCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.AutorizaVerificacion;
import pe.com.tumi.servicio.solicitudPrestamo.domain.CapacidadCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.EstadoCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.ExpedienteCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.ExpedienteCreditoId;
import pe.com.tumi.servicio.solicitudPrestamo.domain.GarantiaCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.RequisitoCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.composite.CronogramaCreditoComp;
import pe.com.tumi.servicio.solicitudPrestamo.domain.composite.ExpedienteCreditoComp;
import pe.com.tumi.servicio.solicitudPrestamo.service.SolicitudPrestamoService;

/**
 * Session Bean implementation class SolicitudPrestamoFacade
 */
@Stateless
public class SolicitudPrestamoFacade extends TumiFacade implements SolicitudPrestamoFacadeRemote, SolicitudPrestamoFacadeLocal {
	
	private SolicitudPrestamoService solicitudPrestamoService = (SolicitudPrestamoService)TumiFactory.get(SolicitudPrestamoService.class);
	//private CuentaCtaCteService cuentaCtaCteService = (CuentaCtaCteService)TumiFactory.get(CuentaCtaCteService.class);
	private GarantiaCreditoBO boGarantiaCredito = (GarantiaCreditoBO)TumiFactory.get(GarantiaCreditoBO.class);
	private CapacidadCreditoBO boCapacidadCredito = (CapacidadCreditoBO)TumiFactory.get(CapacidadCreditoBO.class);
	private AutorizaCreditoBO boAutorizaCredito = (AutorizaCreditoBO)TumiFactory.get(AutorizaCreditoBO.class);
	private AutorizaVerificacionBO boAutorizaVerificacion = (AutorizaVerificacionBO)TumiFactory.get(AutorizaVerificacionBO.class);
	private EstadoCreditoBO boEstadoCredito = (EstadoCreditoBO)TumiFactory.get(EstadoCreditoBO.class);
	private ExpedienteCreditoBO boExpedienteCredito = (ExpedienteCreditoBO)TumiFactory.get(ExpedienteCreditoBO.class);

	
	/**
	 * 
	 */
	public List<ExpedienteCreditoComp> getListaExpedienteCreditoCompDeBusqueda(ExpedienteCreditoComp o) throws BusinessException{
		List<ExpedienteCreditoComp> lista = null;
		try{
			lista = solicitudPrestamoService.getListaExpedienteCreditoCompDeBusqueda(o);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	
	/**
	 * 
	 */
	public List<ExpedienteCreditoComp> getListaAutorizacionCreditoCompDeBusqueda(ExpedienteCreditoComp o) throws BusinessException{
		List<ExpedienteCreditoComp> lista = null;
		try{
			lista = solicitudPrestamoService.getListaAutorizacionCreditoCompDeBusqueda(o);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	
	/**
	 * 
	 */
	public ExpedienteCredito grabarExpedienteCredito(ExpedienteCredito o) throws BusinessException{
    	ExpedienteCredito credito = null;
		try{
			credito = solicitudPrestamoService.grabarExpedienteCredito(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return credito;
	}
	
	
	/**
	 * 
	 */
	public ExpedienteCredito modificarExpedienteCredito(ExpedienteCredito o) throws BusinessException{
    	ExpedienteCredito credito = null;
		try{
			credito = solicitudPrestamoService.modificarExpedienteCredito(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return credito;
	}
	
	
	/**
	 * 
	 */
	public ExpedienteCredito grabarAutorizacionPrestamo(ExpedienteCredito o) throws BusinessException{
    	ExpedienteCredito credito = null;
    	try{
			credito = solicitudPrestamoService.grabarAutorizacionPrestamo(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return credito;
	}
	
	
	/**
	 * 
	 */
	public ExpedienteCredito getSolicitudPrestamoPorIdExpedienteCredito(ExpedienteCreditoId pId) throws BusinessException{
		ExpedienteCredito expedienteCredito = null;
		try{
			expedienteCredito = solicitudPrestamoService.getExpedienteCreditoPorIdExpedienteCredito(pId);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return expedienteCredito;
	}
	
	
	/**
	 * 
	 */
	public Integer getCantidadPersonasAseguradasPorPkPersona(Integer idPersona) throws BusinessException{
    	Integer escalar = null;
		try{
			escalar = boGarantiaCredito.getCantidadPersonasGarantizadasPorPkPersona(idPersona);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return escalar;
	}
	
	
	/**
	 * 
	 */
	public List<CapacidadCredito> getListaCapacidadCreditoPorPkExpedienteYSocioEstructura(ExpedienteCreditoId pId,SocioEstructuraPK pIdSocio) throws BusinessException{
		List<CapacidadCredito> lista = null;
		try{
			lista = boCapacidadCredito.getListaCapacidadCreditoPorPkExpedienteYSocioEstructura(pId, pIdSocio);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	
	/**
	 * 
	 */
	public List<AutorizaCredito> getListaAutorizaCreditoPorPkExpediente(ExpedienteCreditoId pId) throws BusinessException{
		List<AutorizaCredito> lista = null;
		try{
			lista = boAutorizaCredito.getListaPorPkExpedienteCredito(pId);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	
	/**
	 * 
	 * @param pId
	 * @return
	 * @throws BusinessException
	 */
	public List<AutorizaCredito> getListaVerificaCreditoPorPkExpediente(ExpedienteCreditoId pId) throws BusinessException{
		List<AutorizaCredito> lista = null;
		try{
			lista = boAutorizaCredito.getListaPorPkExpedienteCredito(pId);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	
	/**
	 * 
	 */
	 public List<GarantiaCredito> getListaGarantiasPorPkPersona(Integer intPersEmpresaPk, Integer idPersona) throws BusinessException{
			
			List<GarantiaCredito> listaGarantias = null;
			try{
				listaGarantias = boGarantiaCredito.getListaGarantiasPorPkPersona(intPersEmpresaPk, idPersona);
	   		}catch(BusinessException e){
	   			throw e;
	   		}catch(Exception e){
	   			throw new BusinessException(e);
	   		}
	   		
	   		return listaGarantias;
		}
	 
	 
	 /**
	  * 
	  */
	 @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	    public LibroDiario generarLibroDiarioPrestamo(ExpedienteCredito expedienteCredito) 
		throws BusinessException{
		 	LibroDiario libroDiario = null;
			try{
				libroDiario = solicitudPrestamoService.generarLibroDiarioAutorizacion(expedienteCredito);
			}catch(BusinessException e){
				throw e;
			}catch(Exception e){
				System.out.println("ERROR EN generarLibroDiarioPrestamo --> "+e);
				throw new BusinessException(e);
			}
			return libroDiario;
		}
	 
	 /**
	  * 
	  */
	 @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	    public LibroDiario generarLibroDiarioAnulacion(ExpedienteCredito expedienteCredito) 
		throws BusinessException{
		 	LibroDiario libroDiario = null;
			try{
				libroDiario = solicitudPrestamoService.generarLibroDiarioAnulacion(expedienteCredito);
			}catch(BusinessException e){
				throw e;
			}catch(Exception e){
				throw new BusinessException(e);
			}
			return libroDiario;
		}
	 
	 
	 /**
	  * 
	  */
	public List<AutorizaVerificacion> getListaVerifificacionesCreditoPorPkExpediente(ExpedienteCreditoId pId) throws BusinessException{
			List<AutorizaVerificacion> lista = null;
			try{
				lista = boAutorizaVerificacion.getListaPorPkExpedienteCredito(pId);
			}catch(BusinessException e){
				throw e;
			}catch(Exception e){
				throw new BusinessException(e);
			}
			return lista;
		} 
	
	
	/**
	 * 
	 */
	 @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	 public EstadoCredito ultimoEstadoCredito(ExpedienteCredito expedienteCredito) 
		throws BusinessException{
		 EstadoCredito ultimoEstado = null;
			try{
				ultimoEstado = solicitudPrestamoService.obtenerUltimoEstadoCredito(expedienteCredito);
			}catch(BusinessException e){
				throw e;
			}catch(Exception e){
				throw new BusinessException(e);
			}
			return ultimoEstado;
		}
	 
	 
	 /**
	  * 
	  */
	public List<ExpedienteCredito> getListaExpedienteCreditoPorCuenta(Cuenta o) throws BusinessException{
		List<ExpedienteCredito> lista = null;
		try{
			lista = solicitudPrestamoService.getListaExpedienteCreditoPorCuenta(o);
			}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	
	/**
	 * 
	 */
	public List<GarantiaCredito> getListaGarantiaCreditoPorId(ExpedienteCreditoId o) throws BusinessException{
		List<GarantiaCredito> lista = null;
		try{
			lista = solicitudPrestamoService.getListaGarantiaCreditoPorId(o);
			}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	
	/**
	 * 
	 */
	public ExpedienteCredito grabarExpedienteRefinanciamiento(ExpedienteCredito o) throws BusinessException{
    	ExpedienteCredito credito = null;
		try{
			credito = solicitudPrestamoService.grabarExpedienteRefinanciamiento(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return credito;
	}
	
	
	/**
	 * 
	 */
	public List<ExpedienteCreditoComp> getListaExpedienteRefinanciamientoCompDeBusqueda(ExpedienteCreditoComp o) throws BusinessException{
		List<ExpedienteCreditoComp> lista = null;
		try{
			lista = solicitudPrestamoService.getListaExpedienteRefinanciamientoCompDeBusqueda(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return lista;
	}
	
	
	/**
	 * 
	 */
	public List<ExpedienteCredito> getListaPorExpediente(ExpedienteCredito o) throws BusinessException{
		List<ExpedienteCredito> lista = null;
		try{
			lista = solicitudPrestamoService.getListaPorExpediente(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return lista;
	}
	
	
	/**
	 * 
	 * @param expedienteCredito
	 * @return
	 * @throws BusinessException
	 */
	 @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	    public LibroDiario generarLibroDiarioRefinanciamiento(ExpedienteCredito expedienteCredito) 
		throws BusinessException{
		 	LibroDiario libroDiario = null;
			try{
				libroDiario = solicitudPrestamoService.generarLibroDiarioAutorizacionRefinanciamiento(expedienteCredito);
			}catch(BusinessException e){
				context.setRollbackOnly();
				throw e;
			}catch(Exception e){
				context.setRollbackOnly();
				throw new BusinessException(e);
			}
			return libroDiario;
		}
	 
	// CarteraCredito carteraCredito = null;
	/* @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	 public void getDatosDeCartera(Integer empresa)throws BusinessException{
		 	//LibroDiario libroDiario = null;
		 	CarteraCreditoId carteraCreditoId = null;
		 	CarteraCredito carteraCredito = null;

			try{
				carteraCredito = new CarteraCredito();
				carteraCreditoId = new CarteraCreditoId();
				carteraCreditoId.setIntPersEmpresaCartera(empresa);
				carteraCredito.setId(carteraCreditoId);

				carteraCredito = solicitudPrestamoService.getDatosDeCartera(carteraCredito);
			}catch(Exception e){
				System.out.println("ERROR EN generarLibroDiarioPrestamo --> "+e);
				throw new BusinessException(e);
			}
	}*/
	 
	/* @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	 public void generarAsientoRefinanciamiento()throws BusinessException{
		 	//LibroDiario libroDiario = null;
		 	//CarteraCreditoId carteraCreditoId = null;
		 	//CarteraCredito carteraCredito = null;

			try{
				ExpedienteCredito expedienteCredito = new ExpedienteCredito();
				solicitudPrestamoService.generarAsientoRefinanciamiento(new Integer(2));
			}catch(Exception e){
				System.out.println("ERROR EN generarAsientoRefinanciamiento --> "+e);
				throw new BusinessException(e);
			}
	}*/
	  
	/* @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	 public void generarSolicitudCtaCte(SocioComp socioComp,Usuario usuario, String strPeriodo, RequisitoCredito requisitoCred, 
		Expediente expedientePorRefinanciar )throws BusinessException{
		 
		 try {
			 //CuentaCtaCteService cuentaCtaCteService = (CuentaCtaCteService)TumiFactory.get(CuentaCtaCteService.class);
			 // public void generarSolicitudCtaCteRefinan(SocioComp socioComp, Usuario usuario, String strPeriodo, RequisitoCredito requisitoCred, Expediente expedientePorRefinanciar )
			 if(socioComp != null){
				 //cuentaCtaCteService.generarSolicitudCtaCteRefinan(socioComp,usuario, strPeriodo, requisitoCred, expedientePorRefinanciar);
				 solicitudPrestamoService.generarSolicitudCtaCteRefinan(socioComp,usuario, strPeriodo, requisitoCred, expedientePorRefinanciar);
			 }

		} catch (Exception e) {
			System.out.println("Error en generarSolicitudCtaCte ---> "+e);
			e.printStackTrace();
		}
 
	 }*/
	 
	 
	 /**
	  * 
	  */
	 @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	 public RequisitoCredito recuperarRequisitoRefinanciamiento(ExpedienteCredito expediente) throws BusinessException{
		 RequisitoCredito reqCredito = null;
		 try {
			 reqCredito =  solicitudPrestamoService.recuperarRequisitoRefinanciamiento(expediente);
		} catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		} 
		return reqCredito;
		 
	 }
	 
	 /**
	  *  Genera el coronograma de movimiento.
	  *  Recuperando el cronograma desde servicio.
	  */
	 @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	 public List<Cronograma> generarCronogramaMovimiento(ExpedienteCreditoId expedienteId) throws BusinessException{
		 List<Cronograma> lstCrono = null;
		 try {
			 lstCrono =  solicitudPrestamoService.generarCronogramaMovimiento(expedienteId);
		} catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		 
		return lstCrono;
		 
	 }
	 
	/*public LibroDiario generarProcesoAutorizacionRefinanciamientoTotal(ExpedienteCredito expedienteCredito,Integer intParaEstadoAprobado, 
																Integer intParaTipoOperacion, ActionEvent event, Usuario usuario, 
																SocioComp beanSocioComp, ExpedienteCreditoComp expedienteCreditoCompSelected,
																String strPeriodo, RequisitoCredito requisitoCred, 
																Expediente expedienteMov)throws BusinessException {
		LibroDiario libroDiario = null;
		 try {
			 libroDiario = solicitudPrestamoService.generarProcesoAutorizacionRefinanciamientoTotal(expedienteCredito,intParaEstadoAprobado, intParaTipoOperacion,
						 event,  usuario,  beanSocioComp,  expedienteCreditoCompSelected, strPeriodo,  requisitoCred,  expedienteMov);
		} catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return libroDiario ; 
		 
	 }*/
	
	 
	 /**
	  * 
	  */
	public EstadoCredito grabarEstadoAutorizadoRefinan(EstadoCredito estado)throws BusinessException {
		EstadoCredito estadoRef = null;
		try {
			estadoRef = boEstadoCredito.grabar(estado);
		} catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return estadoRef ; 
		
	}

	
	/**
	 * 
	 */
	public Integer getNumeroCreditosGarantizadosPorPersona(Integer intPersEmpresaPk, Integer idPersona)throws BusinessException{
		Integer intNroGarantias = 0;
		try{
			intNroGarantias = solicitudPrestamoService.getNumeroCreditosGarantizadosPorPersona(intPersEmpresaPk, idPersona);
		} catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return intNroGarantias ; 

	}
	
	
	/**
	 * 
	 */
	public Expediente generarExpedienteMovimiento(ExpedienteCreditoComp expedienteCreditoCompSelected) throws BusinessException{
		Expediente expedienteMovNuevo = null;
		try{
			expedienteMovNuevo = solicitudPrestamoService.generarExpedienteMovimiento(expedienteCreditoCompSelected);
		} catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return expedienteMovNuevo ; 

	}
	
	
	/**
	 * 
	 */
	public List<EstadoCredito> getListaEstadosPorExpedienteCreditoId(ExpedienteCreditoId pId)throws BusinessException{
		List<EstadoCredito> lstEstados = null;
		try {
			lstEstados = boEstadoCredito.getListaPorPkExpedienteCredito(pId);
		}  catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return lstEstados;
	}
	
	
	/**
	 * Recupera la lista completa de las actividades. Grilla de busqueda de solicitud.
	 * PENDIENTE: LOS FILTROS DE LA BUSQUEDA.
	 */
	public List<ExpedienteCreditoComp> getListaExpedienteActividadCompDeBusqueda(ExpedienteCreditoComp o) throws BusinessException{
		List<ExpedienteCreditoComp> lista = null;
		try{
			lista = solicitudPrestamoService.getListaExpedienteActividadCompDeBusqueda(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return lista;
	}

	
	/**
	 * 
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
		public ExpedienteCredito getExpedienteActividadCompletoPorIdExpedienteActividad(ExpedienteCreditoId pId) throws BusinessException{
	    	ExpedienteCredito credito = null;
			try{
				credito = solicitudPrestamoService.getExpedienteActividadCompletoPorIdExpedienteActividad(pId);
			}catch(BusinessException e){
				context.setRollbackOnly();
				throw e;
			}catch(Exception e){
				context.setRollbackOnly();
				throw new BusinessException(e);
			}
			return credito;
		}
		
		
	/**
	 * 
	 */
		@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
		public List<ExpedienteCreditoComp> getListaExpedienteActividadCompPorAutorizar(ExpedienteCreditoComp o) throws BusinessException{
			List<ExpedienteCreditoComp> lista = null;
			try{
				lista = solicitudPrestamoService.getListaExpedienteActividadCompPorAutorizar(o);
			}catch(BusinessException e){
				context.setRollbackOnly();
				throw e;
			}catch(Exception e){
				context.setRollbackOnly();
				throw new BusinessException(e);
			}
			return lista;
		}
		
		
		/**
		 * 
		 */
		@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	    public LibroDiario generarProcesosAutorizacionActividad(ExpedienteCredito expedienteCredito, Usuario usuario) throws BusinessException{
		 	LibroDiario libroDiario = null;
		 	//LibroDiarioFacadeRemote libroDiarioFacade = null;
			try{
				// reviara actividad
				//libroDiario = solicitudPrestamoService.obtieneLibroDiarioYDiarioDetalleParaActividad(expedienteCredito, usuario);
				libroDiario = solicitudPrestamoService.generarProcesosAutorizacionActividad(expedienteCredito, usuario);

			}catch(BusinessException e){
				context.setRollbackOnly();
				throw e;
			}catch(Exception e){
				context.setRollbackOnly();
				throw new BusinessException(e);
			}
			return libroDiario;
		}
		
		
		/**
		 * Busqueda de la grilla de solicitud de credito
		 * @param intTipoConsultaBusqueda
		 * @param strConsultaBusqueda
		 * @param strNumeroSolicitudBusq
		 * @param estadoCreditoBusqueda
		 * @param expCreditoBusqueda
		 * @return
		 * @throws BusinessException
		 */
		public List<ExpedienteCreditoComp> getListaExpedienteCreditoCompDeBusquedaFiltro(Integer intTipoConsultaBusqueda, String strConsultaBusqueda, 
				String strNumeroSolicitudBusq, EstadoCredito estadoCreditoBusqueda, ExpedienteCredito expCreditoBusqueda) 
			throws BusinessException{
				List<ExpedienteCreditoComp> lista = null;
				try{
					lista = solicitudPrestamoService.getListaExpedienteCreditoCompDeBusquedaFiltro(intTipoConsultaBusqueda,strConsultaBusqueda, strNumeroSolicitudBusq,
															estadoCreditoBusqueda,expCreditoBusqueda);
				}catch(BusinessException e){
					context.setRollbackOnly();
					throw e;
				}catch(Exception e){
					context.setRollbackOnly();
					throw new BusinessException(e);
				}
				return lista;
		}
		
		
		/**
		 * Devuleve los expedientes de una cuenta y cuyo ultimo estado.
		 * @param cuenta
		 * @param intEstadoSol
		 * @return
		 * @throws BusinessException
		 */
		public List<ExpedienteCredito> getListaExpedienteCreditoPorCuentaYUltimoEstado(Cuenta cuenta, Integer intEstadoSol) 
			throws BusinessException{
				List<ExpedienteCredito> lista = null;
				try{
					lista = solicitudPrestamoService.getListaExpedienteCreditoPorCuentaYUltimoEstado(cuenta, intEstadoSol);
				}catch(BusinessException e){
					context.setRollbackOnly();
					throw e;
				}catch(Exception e){
					context.setRollbackOnly();
					throw new BusinessException(e);
				}
				return lista;
		}
		
		
		public List<ExpedienteCreditoComp> getListaExpedienteCreditoAutorizacionCompDeBusquedaFiltro(Integer intTipoConsultaBusqueda,String strConsultaBusqueda, 
				String strNumeroSolicitudBusq,
				EstadoCredito estadoCondicionFiltro, 
				Integer intTipoCreditoFiltro, 
				EstadoCredito estadoLiquidacionFechas,
				EstadoCredito estadoLiquidacionSuc,
				Integer intIdSubsucursalFiltro) throws BusinessException{
			List<ExpedienteCreditoComp> lista = null;
			
			try{
				lista = solicitudPrestamoService.getListaExpedienteCreditoAutorizacionCompDeBusquedaFiltro(intTipoConsultaBusqueda, strConsultaBusqueda, 
																												strNumeroSolicitudBusq,
																												estadoCondicionFiltro, 
																												intTipoCreditoFiltro, 
																												estadoLiquidacionFechas,
																												estadoLiquidacionSuc,
																												intIdSubsucursalFiltro);
			}catch(BusinessException e){
				context.setRollbackOnly();
				throw e;
			}catch(Exception e){
				context.setRollbackOnly();
				throw new BusinessException(e);
			}
			return lista;
	}
		
		
		
	/**
	 * Cambia los estados a los autorizaCredito, Archivos Infocorp, reniec, AutorizaVerfifica y
	 * a los requiisitos de solicitud asociados al expediente.
	 * Se aplicara cuando el expediente pase a aestado Observado.
	 */
	public void eliminarVerificaAutorizacionAdjuntosPorObservacion(ExpedienteCredito pExpedienteCredito,Integer intTipoCredito, Integer intSubTipo) throws BusinessException{
			
			try {
				
				solicitudPrestamoService.eliminarVerificaAutorizacionAdjuntosPorObservacion(pExpedienteCredito, intTipoCredito,  intSubTipo);
				
			}catch(BusinessException e){
				context.setRollbackOnly();
				throw e;
			}catch(Exception e){
				context.setRollbackOnly();
				throw new BusinessException(e);
			}
		}
	
	/**
	 * Cobranza envioplanilla capacidad de credito
	 */
	public List<CapacidadCredito> getListaPorPkExpedienteCredito(ExpedienteCreditoId pId)throws BusinessException
	{
		List<CapacidadCredito> lista = null;
		try
		{
			lista = boCapacidadCredito.getListaPorPkExpedienteCredito(pId);	
		}
		catch(BusinessException e)
		{
			throw e;
		}
		catch(Exception e)
		{
			throw new BusinessException(e);
		}
		return lista;
	}
	
	/**
	 * 
	 * @param expedienteCredito
	 * @param usuario
	 * @return
	 * @throws BusinessException
	 */
	public String generarProcesosAutorizacionRefinanciamiento(ExpedienteCredito expedienteCredito, ExpedienteCredito expedienteCreditoAut, Usuario usuario, SocioComp beanSocioComp)throws BusinessException{
		//LibroDiario diario = null;
		String mensaje = "";
		try {
			mensaje = solicitudPrestamoService.generarProcesosAutorizacionRefinanciamiento(expedienteCredito,expedienteCreditoAut,usuario,beanSocioComp);	
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return mensaje;
	}
	
	
	
	/**
	 * Recupera los expedientes de refinanciamineto segun filtros de busqueda.
	 * @param expedienteBusqueda
	 * @return
	 * @throws BusinessException
	 */
	/*public List<ExpedienteCreditoComp> getBuscarRefinanciamientoCompFiltros(Integer intBusqTipo,String strBusqCadena,String strBusqNroSol,Integer intBusqSucursal,
									Integer intBusqEstado,Date dtBusqFechaEstadoDesde,Date dtBusqFechaEstadoHasta) throws BusinessException{
		List<ExpedienteCreditoComp> lista = null;
		try{
			lista = solicitudPrestamoService.getBuscarRefinanciamientoCompFiltros(intBusqTipo,strBusqCadena,strBusqNroSol,intBusqSucursal,intBusqEstado,dtBusqFechaEstadoDesde,dtBusqFechaEstadoHasta);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}*/
	
	
	public List<ExpedienteComp> recuperarDetallesCreditoRefinanciado(ExpedienteCreditoComp registroSeleccionadoBusqueda) throws BusinessException{
	List<ExpedienteComp> lista = null;
	try{
		lista = solicitudPrestamoService.recuperarDetallesCreditoRefinanciado(registroSeleccionadoBusqueda);
	}catch(Exception e){
		throw new BusinessException(e);
	}
	return lista;
	}
	
	
	/**
	 * Recupera los expedientes de autorizacion de refinanciamineto segun filtros de busqueda.
	 * @param expedienteBusqueda
	 * @return
	 * @throws BusinessException
	 */
	/*public List<ExpedienteCreditoComp> getBuscarAutRefinanciamientoCompFiltros(Integer intBusqTipo,String strBusqCadena,String strBusqNroSol,Integer intBusqSucursal,
									Integer intBusqEstado,Date dtBusqFechaEstadoDesde,Date dtBusqFechaEstadoHasta) throws BusinessException{
		List<ExpedienteCreditoComp> lista = null;
		try{
			lista = solicitudPrestamoService.getBuscarAutRefinanciamientoCompFiltros(intBusqTipo,strBusqCadena,strBusqNroSol,intBusqSucursal,intBusqEstado,dtBusqFechaEstadoDesde,dtBusqFechaEstadoHasta);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}*/
	
	
	/**
	 * 
	 * @param intTipoConsultaBusqueda
	 * @param strConsultaBusqueda
	 * @param strNumeroSolicitudBusq
	 * @param estadoCreditoBusqueda
	 * @param expCreditoBusqueda
	 * @return
	 * @throws BusinessException
	 */
	/*public List<ExpedienteCreditoComp> getListaExpedienteCreditoBusquedaFiltro(Integer intTipoConsultaBusqueda, String strConsultaBusqueda, 
			String strNumeroSolicitudBusq, EstadoCredito estadoCreditoBusqueda, ExpedienteCredito expCreditoBusqueda) 
		throws BusinessException{
			List<ExpedienteCreditoComp> lista = null;
			try{
				lista = solicitudPrestamoService.getListaExpedienteCreditoCompDeBusquedaFiltro(intTipoConsultaBusqueda,strConsultaBusqueda, strNumeroSolicitudBusq,
														estadoCreditoBusqueda,expCreditoBusqueda);
			}catch(BusinessException e){
				context.setRollbackOnly();
				throw e;
			}catch(Exception e){
				context.setRollbackOnly();
				throw new BusinessException(e);
			}
			return lista;
	}*/
	
	/**
	 * Recupera los expedientes de credito segun filtros de busqueda
	 * @param expedienteCompBusq
	 * @return
	 * @throws BusinessException
	 */
	public List<ExpedienteCreditoComp> getListaBusqCreditoFiltros(ExpedienteCreditoComp expedienteCompBusq)throws BusinessException{
		List<ExpedienteCreditoComp> lstCreditoCreditoComp = null;
		try {
			lstCreditoCreditoComp = solicitudPrestamoService.getListaBusqCreditoFiltros(expedienteCompBusq);
			
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lstCreditoCreditoComp;
		
	}
	
	/**
	 * Recupera los expedientes de autorizacion credito segun filtros de busqueda
	 * @param expedienteCompBusq
	 * @return
	 * @throws BusinessException
	 */
	public List<ExpedienteCreditoComp> getListaBusqCreditosAutFiltros(ExpedienteCreditoComp expedienteCompBusq)throws BusinessException{
		List<ExpedienteCreditoComp> lstCreditoCreditoComp = null;
		try {
			lstCreditoCreditoComp = solicitudPrestamoService.getListaBusqCreditosAutFiltros(expedienteCompBusq);
			
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lstCreditoCreditoComp;
		
	}
		
	
	
	/**
	 * Recupera los expedientes de refinanciamiento de credito segun filtros de busqueda
	 * @param expedienteCompBusq
	 * @return
	 * @throws BusinessException
	 */
	public List<ExpedienteCreditoComp> getListaBusqRefinanFiltros(ExpedienteCreditoComp expedienteCompBusq)throws BusinessException{
		List<ExpedienteCreditoComp> lstCreditoCreditoComp = null;
		try {
			lstCreditoCreditoComp = solicitudPrestamoService.getListaBusqRefinanFiltros(expedienteCompBusq);
			
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lstCreditoCreditoComp;
		
	}
	
	/**
	 * Recupera los expedientes de autorizacion de refinanciamineto segun filtros de busqueda.
	 * @param expedienteBusqueda
	 * @return
	 * @throws BusinessException
	 */
	public List<ExpedienteCreditoComp> getListaBusqAutRefFiltros(ExpedienteCreditoComp expedienteCompBusq) throws BusinessException{
		List<ExpedienteCreditoComp> lista = null;
		try{
			lista = solicitudPrestamoService.getListaBusqAutRefFiltros(expedienteCompBusq);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	
		/**
		 * Recupera los expedientes de actividad segun filtros de busqueda
		 * @param expedienteCompBusq
		 * @return
		 * @throws BusinessException
		 */
		public List<ExpedienteCreditoComp> getListaBusqActividadFiltros(ExpedienteCreditoComp expedienteCompBusq)throws BusinessException{
			List<ExpedienteCreditoComp> lstCreditoCreditoComp = null;
			try {
				lstCreditoCreditoComp = solicitudPrestamoService.getListaBusqActividadFiltros(expedienteCompBusq);
				
			}catch(BusinessException e){
				throw e;
			}catch(Exception e){
				throw new BusinessException(e);
			}
			return lstCreditoCreditoComp;
			
		}
		
		
		/**
		 * Recupera los expedientes de actividad por autorizar segun filtros de busqueda
		 * @param expedienteCompBusq
		 * @return
		 * @throws BusinessException
		 */
		public List<ExpedienteCreditoComp> getListaBusqActAutFiltros(ExpedienteCreditoComp expedienteCompBusq)throws BusinessException{
			List<ExpedienteCreditoComp> lstCreditoCreditoComp = null;
			try {
				lstCreditoCreditoComp = solicitudPrestamoService.getListaBusqActAutFiltros(expedienteCompBusq);
				
			}catch(BusinessException e){
				throw e;
			}catch(Exception e){
				throw new BusinessException(e);
			}
			return lstCreditoCreditoComp;
			
		}
		
			/**
			 * Recupera cronogramaComp a partir de los cronograma credito
			 * @param expedienteId
			 * @param intNroCuota
			 * @return
			 * @throws BusinessException
			 */
			public CronogramaCreditoComp recuperarCronogramaCompVistaPopUp(ExpedienteCreditoId expedienteId, Integer intNroCuota)throws BusinessException{
				CronogramaCreditoComp CronogramaComp = null;
				try {
					CronogramaComp = solicitudPrestamoService.recuperarCronogramaCompVistaPopUp(expedienteId, intNroCuota);
					
				}catch(BusinessException e){
					throw e;
				}catch(Exception e){
					throw new BusinessException(e);
				}
				return CronogramaComp;
				
			}
		
			/**
			 * Recupera los expedientes de credito segun filtros de busqueda
			 * @param expedienteCompBusq
			 * @return
			 * @throws BusinessException
			 */
			public List<ExpedienteCreditoComp> getListaBusqCreditoEspecialFiltros(ExpedienteCreditoComp expedienteCompBusq)throws BusinessException{
				List<ExpedienteCreditoComp> lstCreditoCreditoComp = null;
				try {
					lstCreditoCreditoComp = solicitudPrestamoService.getListaBusqCreditoEspecialFiltros(expedienteCompBusq);
					
				}catch(BusinessException e){
					throw e;
				}catch(Exception e){
					throw new BusinessException(e);
				}
				return lstCreditoCreditoComp;
				
			}
			
			
			
			
			
			
			/**
			 * Recupera los expedientes de credito especial segun filtros de grilla.
			 * @param intPersEmpresaPk
			 * @param intCuenta
			 * @param intItemExpediente
			 * @return
			 * @throws BusinessException
			 */
			public List<ExpedienteCredito> getMaxExpedienteRefinan(Integer intPersEmpresaPk, Integer intCuenta, Integer intItemExpediente)throws BusinessException{
				List<ExpedienteCredito> lstCreditoCredito = null;
				try {
					lstCreditoCredito =  boExpedienteCredito.getMaxExpedienteRefinan(intPersEmpresaPk, intCuenta, intItemExpediente);
					
				}catch(BusinessException e){
					throw e;
				}catch(Exception e){
					throw new BusinessException(e);
				}
				return lstCreditoCredito;
				
			}
			
			
	
}

	



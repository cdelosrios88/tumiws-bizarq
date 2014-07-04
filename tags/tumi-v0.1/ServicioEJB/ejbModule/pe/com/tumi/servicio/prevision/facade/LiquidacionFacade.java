package pe.com.tumi.servicio.prevision.facade;

//import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

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
import pe.com.tumi.servicio.liquidacion.bo.RequisitoLiquidacionBO;
import pe.com.tumi.servicio.liquidacion.service.SolicitudLiquidacionService;
import pe.com.tumi.servicio.prevision.bo.BeneficiarioLiquidacionBO;
import pe.com.tumi.servicio.prevision.bo.ExpedienteLiquidacionBO;
import pe.com.tumi.servicio.prevision.bo.ExpedienteLiquidacionDetalleBO;
import pe.com.tumi.servicio.prevision.domain.BeneficiarioLiquidacion;
import pe.com.tumi.servicio.prevision.domain.EstadoLiquidacion;
import pe.com.tumi.servicio.prevision.domain.ExpedienteLiquidacion;
import pe.com.tumi.servicio.prevision.domain.ExpedienteLiquidacionComp;
import pe.com.tumi.servicio.prevision.domain.ExpedienteLiquidacionDetalle;
import pe.com.tumi.servicio.prevision.domain.ExpedienteLiquidacionId;
import pe.com.tumi.servicio.prevision.domain.ExpedientePrevision;
import pe.com.tumi.servicio.prevision.domain.RequisitoLiquidacion;
import pe.com.tumi.servicio.prevision.domain.composite.RequisitoLiquidacionComp;
//import pe.com.tumi.servicio.prevision.domain.composite.RequisitoLiquidacionComp2;
import pe.com.tumi.servicio.prevision.service.EgresoLiquidacionService;
import pe.com.tumi.servicio.prevision.service.GiroLiquidacionService;
import pe.com.tumi.servicio.solicitudPrestamo.domain.EgresoDetalleInterfaz;
import pe.com.tumi.tesoreria.banco.domain.Bancocuenta;
import pe.com.tumi.tesoreria.egreso.domain.ControlFondosFijos;
import pe.com.tumi.tesoreria.egreso.domain.Egreso;

@Stateless
public class LiquidacionFacade extends TumiFacade implements LiquidacionFacadeRemote, LiquidacionFacadeLocal {
	private GiroLiquidacionService giroLiquidacionService = (GiroLiquidacionService)TumiFactory.get(GiroLiquidacionService.class);
	private EgresoLiquidacionService egresoLiquidacionService = (EgresoLiquidacionService)TumiFactory.get(EgresoLiquidacionService.class);
	private BeneficiarioLiquidacionBO boBeneficiarioLiquidacion = (BeneficiarioLiquidacionBO)TumiFactory.get(BeneficiarioLiquidacionBO.class);
	private ExpedienteLiquidacionBO boExpedienteLiquidacion = (ExpedienteLiquidacionBO)TumiFactory.get(ExpedienteLiquidacionBO.class);
	private ExpedienteLiquidacionDetalleBO boExpedienteLiquidacionDetalle = (ExpedienteLiquidacionDetalleBO)TumiFactory.get(ExpedienteLiquidacionDetalleBO.class);
	private SolicitudLiquidacionService solicitudLiquidacionService = (SolicitudLiquidacionService)TumiFactory.get(SolicitudLiquidacionService.class);
	private RequisitoLiquidacionBO boRequisitoLiquidacion = (RequisitoLiquidacionBO)TumiFactory.get(RequisitoLiquidacionBO.class);
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<ExpedienteLiquidacion> buscarExpedienteParaGiro(List<Persona> listaPersonaFiltro, Integer intTipoCreditoFiltro,
			EstadoLiquidacion estadoLiquidacionFiltro, Integer intItemExpedienteFiltro, Integer intTipoBusquedaSucursal, 
			Integer intIdSucursalFiltro, Integer intIdSubsucursalFiltro) throws BusinessException{
		List<ExpedienteLiquidacion> lista = null;
   		try{
   			lista = giroLiquidacionService.buscarExpedienteParaGiro(listaPersonaFiltro, intTipoCreditoFiltro, estadoLiquidacionFiltro,
   					intItemExpedienteFiltro, intTipoBusquedaSucursal, intIdSucursalFiltro, intIdSubsucursalFiltro);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return lista;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<ExpedienteLiquidacion> buscarExpedienteParaLiquidacion(List<Persona> listaPersonaFiltro, Integer intTipoCreditoFiltro,
			EstadoLiquidacion estadoLiquidacionFiltro, Integer intItemExpedienteFiltro, Integer intTipoBusquedaSucursal, 
			Integer intIdSucursalFiltro, Integer intIdSubsucursalFiltro) throws BusinessException{
		List<ExpedienteLiquidacion> lista = null;
   		try{
   			//solicitudLiquidacionService
   			lista = solicitudLiquidacionService.buscarExpedienteParaLiquidacion(listaPersonaFiltro, intTipoCreditoFiltro, estadoLiquidacionFiltro,
   					intItemExpedienteFiltro, intTipoBusquedaSucursal, intIdSucursalFiltro, intIdSubsucursalFiltro);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return lista;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<BeneficiarioLiquidacion> getListaBeneficiarioLiquidacionPorExpedienteLiquidacionDetalle(
			ExpedienteLiquidacionDetalle expedienteLiquidacionDetalle) throws BusinessException{
		List<BeneficiarioLiquidacion> lista = null;
   		try{
   			lista = boBeneficiarioLiquidacion.getPorExpedienteLiquidacionDetalle(expedienteLiquidacionDetalle);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return lista;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public ExpedienteLiquidacion grabarGiroLiquidacion(ExpedienteLiquidacion expedienteLiquidacion) throws BusinessException{
    	ExpedienteLiquidacion dto = null;
		try{
			dto = giroLiquidacionService.grabarGiroLiquidacion(expedienteLiquidacion);
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
	public List<ExpedienteLiquidacion> buscarExpedienteParaGiroDesdeFondo(Integer intIdPersona, Integer intIdEmpresa) 
		throws BusinessException{
		List<ExpedienteLiquidacion> lista = null;
   		try{
   			lista = giroLiquidacionService.buscarExpedienteParaGiroDesdeFondo(intIdPersona, intIdEmpresa);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return lista;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<EgresoDetalleInterfaz> cargarListaEgresoDetalleInterfaz(List<ExpedienteLiquidacionDetalle> listaExpedienteLiquidacionDetalle,
			Integer intIdBeneficiario)	throws BusinessException{
		List<EgresoDetalleInterfaz> lista = null;
   		try{
   			lista = egresoLiquidacionService.cargarListaEgresoDetalleInterfaz(listaExpedienteLiquidacionDetalle, intIdBeneficiario);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return lista;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Egreso generarEgresoLiquidacion(ExpedienteLiquidacion expedienteLiquidacion, ControlFondosFijos controlFondosFijos, Usuario usuario)	
		throws BusinessException{
		Egreso egreso = null;
   		try{
   			egreso = egresoLiquidacionService.generarEgresoLiquidacion(expedienteLiquidacion, controlFondosFijos, usuario);
   		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
   		return egreso;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<BeneficiarioLiquidacion> getListaBeneficiarioLiquidacionPorEgreso(Egreso egreso) throws BusinessException{
		List<BeneficiarioLiquidacion> lista = null;
   		try{
   			lista = boBeneficiarioLiquidacion.getPorEgreso(egreso);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return lista;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public ExpedienteLiquidacion getExpedienteLiquidacionPorId(ExpedienteLiquidacionId expedienteLiquidacionId) throws BusinessException{
		ExpedienteLiquidacion dro = null;
   		try{
   			dro = boExpedienteLiquidacion.getPorPk(expedienteLiquidacionId);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return dro;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public ExpedienteLiquidacion getExpedienteLiquidacionPorBeneficiario(BeneficiarioLiquidacion beneficiarioLiquidacion) 
	throws BusinessException{
		ExpedienteLiquidacion dro = null;
   		try{
   			ExpedienteLiquidacionId expedienteLiquidacionId = new ExpedienteLiquidacionId();
   			expedienteLiquidacionId.setIntPersEmpresaPk(beneficiarioLiquidacion.getId().getIntPersEmpresa());
   			expedienteLiquidacionId.setIntItemExpediente(beneficiarioLiquidacion.getId().getIntItemExpediente());
   			
   			dro = getExpedienteLiquidacionPorId(expedienteLiquidacionId);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return dro;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<ExpedienteLiquidacionDetalle> getListaExpedienteLiquidacionDetallePorExpediente(ExpedienteLiquidacion expedienteLiquidacion) throws BusinessException{
		List<ExpedienteLiquidacionDetalle> lista = null;
   		try{
   			lista = boExpedienteLiquidacionDetalle.getPorExpedienteLiquidacion(expedienteLiquidacion);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return lista;
	}
	/**
	 * JCHAVEZ 18.02.2014
	 * Se modifico el procedimiento para generar el egreso segun las ultimas indicaciones, contemplando los campos a grabar 
	 */	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Egreso generarEgresoLiquidacionCheque(ExpedienteLiquidacion expedienteLiquidacion, Bancocuenta bancoCuenta, Usuario usuario, 
		Integer intNumeroCheque, Integer intTipoDocumentoValidar)throws BusinessException{
		Egreso egreso = null;
   		try{
   			egreso = egresoLiquidacionService.generarEgresoLiquidacionCheque(expedienteLiquidacion, bancoCuenta, usuario, intNumeroCheque, intTipoDocumentoValidar);
   		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
   		return egreso;
	}
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Egreso generarEgresoLiquidacionTransferencia(ExpedienteLiquidacion expedienteLiquidacion, Bancocuenta bancoCuentaOrigen, Usuario usuario, 
		Integer intNumeroTransferencia, Integer intTipoDocumentoValidar, CuentaBancaria cuentaBancariaDestino)throws BusinessException{
		Egreso egreso = null;
   		try{
   			egreso = egresoLiquidacionService.generarEgresoLiquidacionTransferenciaTerceros(expedienteLiquidacion, bancoCuentaOrigen, usuario, intNumeroTransferencia, intTipoDocumentoValidar, cuentaBancariaDestino);
//   			egreso = egresoLiquidacionService.generarEgresoLiquidacionTransferencia(expedienteLiquidacion, bancoCuentaOrigen, usuario, intNumeroTransferencia, 
//   					intTipoDocumentoValidar, cuentaBancariaDestino);
   		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
   		return egreso;
	}	
	
	//--------- Liquidacion --------------->
	
	
	/*public ExpedienteLiquidacion grabarExpedienteLiquidacion(ExpedienteLiquidacion o, CuentaConcepto c) throws BusinessException{
		ExpedienteLiquidacion prevision = null;
		try{
			prevision = solicitudLiquidacionService.grabarExpedienteLiquidacion(o,c);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return prevision;
	}*/
	
	public ExpedienteLiquidacion grabarExpedienteLiquidacion(ExpedienteLiquidacion o) throws BusinessException{
		ExpedienteLiquidacion prevision = null;
		try{
			prevision = solicitudLiquidacionService.grabarExpedienteLiquidacion(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return prevision;
	}
	
	/*	public ExpedienteLiquidacion modificarExpedienteLiquidacion(ExpedienteLiquidacion o, CuentaConcepto c) throws BusinessException{
		ExpedienteLiquidacion prevision = null;
		try{
			prevision = solicitudLiquidacionService.modificarExpedienteLiquidacion(o,c);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return prevision;
	}*/
		
		public ExpedienteLiquidacion modificarExpedienteLiquidacion(ExpedienteLiquidacion o) throws BusinessException{
			ExpedienteLiquidacion prevision = null;
			try{
				prevision = solicitudLiquidacionService.modificarExpedienteLiquidacion(o);
			}catch(BusinessException e){
				context.setRollbackOnly();
				throw e;
			}catch(Exception e){
				context.setRollbackOnly();
				throw new BusinessException(e);
			}
			return prevision;
		}
	
		
		
		public ExpedienteLiquidacion getExpedientePrevisionCompletoPorIdExpedienteLiquidacion(ExpedienteLiquidacionId o) throws BusinessException{
			ExpedienteLiquidacion liquidacion = null;
			try{
				liquidacion = solicitudLiquidacionService.getExpedienteLiquidacionCompletoPorIdExpedienteLiquidacion(o);
			}catch(BusinessException e){
				context.setRollbackOnly();
				throw e;
			}catch(Exception e){
				context.setRollbackOnly();
				throw new BusinessException(e);
			}
			return liquidacion;
		}
		
		public List<ExpedienteLiquidacion> getListaExpedienteLiquidacionCompBusqueda() throws BusinessException{
			List<ExpedienteLiquidacion> listaLiquidacion = null;
			try{
				listaLiquidacion = solicitudLiquidacionService.getListaExpedienteLiquidacionCompBusqueda();
			}catch(BusinessException e){
				context.setRollbackOnly();
				throw e;
			}catch(Exception e){
				context.setRollbackOnly();
				throw new BusinessException(e);
			}
			return listaLiquidacion;
		}
		
		
		@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
		public List<ExpedienteLiquidacion> buscarExpedienteLiquidacionFiltro(List<Persona> listaPersonaFiltro, Integer intTipoCreditoFiltro,Integer intSubTipoCreditoFiltro,
				EstadoLiquidacion estadoLiquidacionFiltro, Integer intItemExpedienteFiltro, Integer intTipoBusquedaSucursal, 
				Integer intIdSucursalFiltro, Integer intIdSubsucursalFiltro) throws BusinessException{
			List<ExpedienteLiquidacion> lista = null;
	   		try{
	   			lista = solicitudLiquidacionService.buscarExpedienteParaLiquidacion(listaPersonaFiltro, intTipoCreditoFiltro, estadoLiquidacionFiltro, intItemExpedienteFiltro, intTipoBusquedaSucursal, intIdSucursalFiltro, intIdSubsucursalFiltro); 
	   		}catch(BusinessException e){
	   			throw e;
	   		}catch(Exception e){
	   			throw new BusinessException(e);
	   		}
	   		return lista;
		}
		
		
	/**
	 * Recupera los expedientes de liquidacion en base los filtros de la grilla de busqueda.
	 * @param intTipoConsultaBusqueda
	 * @param strConsultaBusqueda
	 * @param strNumeroSolicitudBusq
	 * @param estadoCondicionFiltro
	 * @param intTipoCreditoFiltro
	 * @param estadoLiquidacionFechas
	 * @param estadoLiquidacionSuc
	 * @param intIdSubsucursalFiltro
	 * @return
	 * @throws BusinessException
	 */
		public List<ExpedienteLiquidacionComp> getListaExpedienteLiquidacionCompDeBusquedaFiltro(	Integer intTipoConsultaBusqueda,String strConsultaBusqueda, 
																						 			String strNumeroSolicitudBusq,
																						 			EstadoLiquidacion estadoCondicionFiltro, 
																						 			Integer intTipoCreditoFiltro, 
																						 			EstadoLiquidacion estadoLiquidacionFechas,
																						 			EstadoLiquidacion estadoLiquidacionSuc,
																						 			Integer intIdSubsucursalFiltro) throws BusinessException{
				List<ExpedienteLiquidacionComp> lista = null;
				try{
					lista = solicitudLiquidacionService.getListaExpedienteLiquidacionCompDeBusquedaFiltro(intTipoConsultaBusqueda, strConsultaBusqueda, 
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
		
		
		
		
		@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
		public List<BeneficiarioLiquidacion> getListaBeneficiariosPorExpedienteLiquidacion(ExpedienteLiquidacion expedienteLiquidacion) throws BusinessException{
			List<BeneficiarioLiquidacion> lista = null;
	   		try{
	   			lista = boBeneficiarioLiquidacion.getListaPorExpedienteLiquidacion(expedienteLiquidacion);
	   		}catch(BusinessException e){
	   			throw e;
	   		}catch(Exception e){
	   			throw new BusinessException(e);
	   		}
	   		return lista;
		}
		
		@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
		public void deletePorExpediente(ExpedienteLiquidacionId expedienteLiquidacionId) throws BusinessException{
//			List<BeneficiarioLiquidacion> lista = null;
	   		try{
	   			boBeneficiarioLiquidacion.deletePorExpediente(expedienteLiquidacionId);
	   		}catch(BusinessException e){
				context.setRollbackOnly();
				throw e;
			}catch(Exception e){
				context.setRollbackOnly();
				throw new BusinessException(e);
			}
	   	}
		
		
		
		/**
		 * Recupera los expedientes de liquidacion en base los filtros de la grilla de busqueda.
		 * @param intTipoConsultaBusqueda
		 * @param strConsultaBusqueda
		 * @param strNumeroSolicitudBusq
		 * @param estadoCondicionFiltro
		 * @param intTipoCreditoFiltro
		 * @param estadoLiquidacionFechas
		 * @param estadoLiquidacionSuc
		 * @param intIdSubsucursalFiltro
		 * @return
		 * @throws BusinessException
		 */
			public List<ExpedienteLiquidacionComp> getListaExpedienteAutorizacionLiquidacionCompDeBusquedaFiltro(	Integer intTipoConsultaBusqueda,String strConsultaBusqueda, 
																							 			String strNumeroSolicitudBusq,
																							 			EstadoLiquidacion estadoCondicionFiltro, 
																							 			Integer intTipoCreditoFiltro, 
																							 			EstadoLiquidacion estadoLiquidacionFechas,
																							 			EstadoLiquidacion estadoLiquidacionSuc,
																							 			Integer intIdSubsucursalFiltro) throws BusinessException{
					List<ExpedienteLiquidacionComp> lista = null;
					try{
						lista = solicitudLiquidacionService.getListaExpedienteLiquidacionAutorizacionCompDeBusquedaFiltro(intTipoConsultaBusqueda, strConsultaBusqueda, 
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
			 * xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
			 * @param socioComp
			 * @param strPeriodo
			 * @param requisitoLiq
			 * @param usuario
			 * @param expedienteMovAnterior
			 * @param expedienteLiquidacion
			 * @return
			 * @throws BusinessException
			 */
		/*	public LibroDiario generarProcesosDeLiquidacionCuentas(SocioComp socioComp, Integer intPeriodo, RequisitoLiquidacion requisitoLiq, Usuario usuario, 
					Expediente expedienteMovAnterior, ExpedienteLiquidacion expedienteLiquidacion) throws BusinessException{
					LibroDiario libroDiario = null;
					try {
						solicitudLiquidacionService.generarProcesosDeLiquidacionCuentas_1(socioComp, intPeriodo, requisitoLiq, expedienteLiquidacion,usuario);

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
		 * 	Modifica la tabla expediente liquidacion segun autorizacion de liquidacion
		 * 
		 */
		public ExpedienteLiquidacion modificarExpedienteLiquidacionParaAuditoria(ExpedienteLiquidacion pExpedienteLiquidacion, Integer intTipoCambio,
				Date dtNuevoFechaProgramacionPago, Integer intNuevoMotivoRenuncia) 
			throws BusinessException{
			ExpedienteLiquidacion expedienteLiquidacion = null;
			
			try {
				expedienteLiquidacion = solicitudLiquidacionService.modificarExpedienteLiquidacionParaAuditoria(pExpedienteLiquidacion,intTipoCambio,dtNuevoFechaProgramacionPago,intNuevoMotivoRenuncia);
			
			} catch(BusinessException e){
				context.setRollbackOnly();
				throw e;
			}catch(Exception e){
				context.setRollbackOnly();
				throw new BusinessException(e);
			}
			return expedienteLiquidacion;
		}
		
		
		
		
		@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
		public List<ExpedienteLiquidacion> getListaExpedienteLiquidacionYEstados(Integer intEmpresa,Integer intCuenta) throws BusinessException{
			List<ExpedienteLiquidacion> listaLiquidacion = null;
	   		try{
	   			listaLiquidacion = solicitudLiquidacionService.getListaPorEmpresaYCuenta(intEmpresa, intCuenta);
	   			
	   		}catch(BusinessException e){
	   			throw e;
	   		}catch(Exception e){
	   			throw new BusinessException(e);
	   		}
	   		return listaLiquidacion;
		}
		
		
		/**
		 * Recupera los expedientes de liquidacion segun filtros de busqueda
		 * @return
		 */
		public List<ExpedienteLiquidacionComp> getListaBusqExpLiqFiltros(ExpedienteLiquidacionComp expLiqComp) throws BusinessException{
			List<ExpedienteLiquidacionComp> listaLiquidacion = null;
	   		try{
	   			listaLiquidacion = solicitudLiquidacionService.getListaBusqExpLiqFiltros(expLiqComp);
	   			
	   		}catch(BusinessException e){
	   			throw e;
	   		}catch(Exception e){
	   			throw new BusinessException(e);
	   		}
	   		return listaLiquidacion;
		}
		
		/**
		 * Recupera los expedientes de liquidacion para autorizacion segun filtros de busqueda
		 * @return
		 */
		public List<ExpedienteLiquidacionComp> getListaBusqAutLiqFiltros(ExpedienteLiquidacionComp expLiqComp) throws BusinessException{
			List<ExpedienteLiquidacionComp> listaLiquidacion = null;
	   		try{
	   			listaLiquidacion = solicitudLiquidacionService.getListaBusqAutLiqFiltros(expLiqComp);
	   			
	   		}catch(BusinessException e){
	   			throw e;
	   		}catch(Exception e){
	   			throw new BusinessException(e);
	   		}
	   		return listaLiquidacion;
		}
	/**
	 * Giro de Liquidacion, generacion del Egreso
	 */
	public Egreso generarEgresoGiroLiquidacion(ExpedienteLiquidacion expedienteLiquidacion, ControlFondosFijos controlFondosFijos, Usuario usuario)	
		throws BusinessException{
		Egreso egreso = null;
   		try{
   			egreso = egresoLiquidacionService.generarEgresoGiroLiquidacion(expedienteLiquidacion, controlFondosFijos, usuario);
   		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
   		return egreso;
	}
	
	/**
	 * JCHAVEZ 08.02.2014
	 * Recupera el archivo del requisito LIQUIDACION registrado.
	 * @param reqLiq
	 * @return
	 * @throws BusinessException
	 */
    public Archivo getArchivoPorRequisitoLiquidacion(RequisitoLiquidacion reqLiq)throws BusinessException{
    	Archivo archivo = null;
		try{
			GeneralFacadeRemote generalFacade = (GeneralFacadeRemote) EJBFactory.getRemote(GeneralFacadeRemote.class);
			
			ArchivoId archivoId = new ArchivoId();
			archivoId.setIntParaTipoCod(reqLiq.getIntParaTipoArchivo());
			archivoId.setIntItemArchivo(reqLiq.getIntParaItemArchivo());
			archivoId.setIntItemHistorico(reqLiq.getIntParaItemHistorico());
			archivo = generalFacade.getArchivoPorPK(archivoId);			
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
		return archivo;
	}
    
    /**
     * JCHAVEZ 08.02.2014
	 * Procedimiento que recupera los Requisitos necesarios para el giro en Giro LIQUIDACION
     * @param expLiq
     * @return
     * @throws BusinessException
     */
	public List<RequisitoLiquidacionComp> getRequisitoGiroLiquidacion(ExpedienteLiquidacion expLiq) throws BusinessException{
		List<RequisitoLiquidacionComp> lista = null;
		try{
			lista = boExpedienteLiquidacion.getRequisitoGiroLiquidacion(expLiq);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	/**
	 * JCHAVEZ 08.02.2014
	 * Recupera lista de expediente LIQUIDACION por pk del expediente LIQUIDACION y requisito detalle
	 * jchavez 09.05.2014 se agrega nuevo atributo-indpersonabeneficiario
	 * @param pId
	 * @param rId
	 * @return
	 * @throws BusinessException
	 */
	public List<RequisitoLiquidacion> getListaPorPkExpedienteLiquidacionYRequisitoDetalle(ExpedienteLiquidacionId pId, ConfServDetalleId rId, Integer intBeneficiario) throws BusinessException{
		List<RequisitoLiquidacion>  lista = null;
		try{
			lista = boRequisitoLiquidacion.getListaPorPkExpedienteLiquidacionYRequisitoDetalle(pId, rId,intBeneficiario);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	/**
	 * JCHAVEZ 06.02.2014
	 * Grabacion del requisito en giro liquidación, aplican a giros que superen el monto maximo asignado al fondo.
	 * @param reqLiq
	 * @return
	 * @throws BusinessException
	 */
	public RequisitoLiquidacion grabarRequisito(RequisitoLiquidacion reqLiq) throws BusinessException{
		RequisitoLiquidacion dto = null;
		try{
			dto = boRequisitoLiquidacion.grabar(reqLiq);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;
	}

	public List<RequisitoLiquidacionComp> getRequisitoGiroLiquidacionBanco(ExpedienteLiquidacion expLiq) throws BusinessException{
		List<RequisitoLiquidacionComp> lista = null;
//		List<RequisitoLiquidacionComp2> lstReqLiq = new ArrayList<RequisitoLiquidacionComp2>();
//		RequisitoLiquidacionComp2 reqLiq = new RequisitoLiquidacionComp2();
		
		try{
			lista = boExpedienteLiquidacion.getRequisitoGiroLiquidacion(expLiq);
//			for (RequisitoLiquidacionComp o : lista) {
//				reqLiq = new RequisitoLiquidacionComp2();
//				reqLiq.setIntEmpresa(o.getIntEmpresa());
//				reqLiq.setIntItemRequisito(o.getIntItemRequisito());
//				reqLiq.setIntItemRequisitoDetalle(o.getIntItemRequisitoDetalle());
//				reqLiq.setStrDescripcionRequisito(o.getStrDescripcionRequisito());
//				reqLiq.setIntParaTipoOperacion(o.getIntParaTipoOperacion());
//				reqLiq.setIntParaSubTipoOperacion(o.getIntParaSubTipoOperacion());
//				lstReqLiq.add(reqLiq);
//			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	/**
	 * jchavez 15.05.2014 grabacion de liquidacion por tesoresia
	 * @param expedienteLiquidacion
	 * @return
	 * @throws BusinessException
	 */
    public ExpedienteLiquidacion grabarGiroLiquidacionPorTesoreria(ExpedienteLiquidacion expedienteLiquidacion) throws BusinessException{
    	ExpedienteLiquidacion dto = null;
		try{
			dto = giroLiquidacionService.grabarGiroLiquidacionPorTesoreria(expedienteLiquidacion);
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
	 * jchavez 06.06.2014
	 * Cambia los estados a los autorizaCredito, Archivos Infocorp, reniec, AutorizaVerfifica y
	 * a los requiisitos de solicitud asociados al expediente.
	 * Se aplicara cuando el expediente pase a estado Observado.
	 */
    public void eliminarVerificaAutorizacionAdjuntosPorObservacion(ExpedienteLiquidacion pExpedienteLiquidacion) throws BusinessException{
		
		try {
			solicitudLiquidacionService.eliminarVerificaAutorizacionAdjuntosPorObservacion(pExpedienteLiquidacion);
			
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
	}
}
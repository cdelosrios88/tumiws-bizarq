package pe.com.tumi.servicio.prevision.facade;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import pe.com.tumi.cobranza.planilla.domain.SolicitudCtaCte;
import pe.com.tumi.cobranza.planilla.domain.SolicitudCtaCteTipo;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiario;
import pe.com.tumi.credito.socio.core.domain.SocioComp;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.facade.TumiFacade;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.movimiento.concepto.domain.Movimiento;
import pe.com.tumi.movimiento.concepto.domain.composite.CuentaConceptoComp;
import pe.com.tumi.parametro.auditoria.domain.Auditoria;
import pe.com.tumi.seguridad.login.domain.Usuario;
import pe.com.tumi.servicio.liquidacion.service.SolicitudLiquidacionService;
import pe.com.tumi.servicio.prevision.bo.AutorizaLiquidacionBO;
import pe.com.tumi.servicio.prevision.bo.AutorizaVerificaLiquidacionBO;
import pe.com.tumi.servicio.prevision.domain.AutorizaLiquidacion;
import pe.com.tumi.servicio.prevision.domain.AutorizaVerificaLiquidacion;
import pe.com.tumi.servicio.prevision.domain.ExpedienteLiquidacion;
import pe.com.tumi.servicio.prevision.domain.ExpedienteLiquidacionId;
import pe.com.tumi.servicio.prevision.domain.RequisitoLiquidacion;

/**
 * Session Bean implementation class AutorizacionLiquidacionFacade
 */
@Stateless
public class AutorizacionLiquidacionFacade extends TumiFacade implements AutorizacionLiquidacionFacadeRemote, AutorizacionLiquidacionFacadeLocal {
	private SolicitudLiquidacionService  solicitudLiquidacionService = (SolicitudLiquidacionService)TumiFactory.get(SolicitudLiquidacionService.class);
	private AutorizaLiquidacionBO boAutorizaLiquidacion = (AutorizaLiquidacionBO)TumiFactory.get(AutorizaLiquidacionBO.class);
	private AutorizaVerificaLiquidacionBO boAutorizaVerificaLiquidacion = (AutorizaVerificaLiquidacionBO)TumiFactory.get(AutorizaVerificaLiquidacionBO.class);

	public ExpedienteLiquidacion grabarAutorizacionLiquidacion(ExpedienteLiquidacion o) throws BusinessException{
		ExpedienteLiquidacion prevision = null;
    	try{
			prevision = solicitudLiquidacionService.grabarAutorizacionLiquidacion(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return prevision;
	}

	public List<AutorizaLiquidacion> getListaAutorizaLiquidacionPorPkExpediente(ExpedienteLiquidacionId pId) throws BusinessException{
		List<AutorizaLiquidacion> lista = null;
		try{
			lista = boAutorizaLiquidacion.getListaPorPkExpedienteLiquidacion(pId);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	
	public List<AutorizaVerificaLiquidacion> getListaVerificaLiquidacionPorPkExpediente(ExpedienteLiquidacionId pId) throws BusinessException{
		List<AutorizaVerificaLiquidacion> lista = null;
		try{
			lista = boAutorizaVerificaLiquidacion.getListaPorPkExpedienteLiquidacion(pId);
		}catch(BusinessException e){
			System.out.println("BusinessException ---> "+e);
			throw e;
		}catch(Exception e1){
			System.out.println("ExceptionException ---> "+e1);
			throw new BusinessException(e1);
		}
		return lista;
	}

	
	
	 @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	    public LibroDiario generarLibroDiarioLiquidacion(ExpedienteLiquidacion expedienteLiquidacion) 
		throws BusinessException{
		 	LibroDiario libroDiario = null;
			try{
				libroDiario = solicitudLiquidacionService.generarLibroDiarioAutorizacion(expedienteLiquidacion);
			}catch(BusinessException e){
				throw e;
			}catch(Exception e){
				throw new BusinessException(e);
			}
			return libroDiario;
		}
	 
	 @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	    public LibroDiario generarLibroDiarioAnulacionLiquidacion(ExpedienteLiquidacion expedienteLiquidacion) 
		throws BusinessException{
		 	LibroDiario libroDiario = null;
			try{
				libroDiario = solicitudLiquidacionService.generarLibroDiarioAnulacionLiquidacion(expedienteLiquidacion);
			}catch(BusinessException e){
				throw e;
			}catch(Exception e){
				throw new BusinessException(e);
			}
			return libroDiario;
		}
	 
	 
	 
	 /**
	  * Registra el proceso de liquidacion de cuentas.
	  * Cambio de Estado de la Cuenta.
	  * Cambio de estado de Cuentas concepto detalle.
	  * Generar asientos en libro diario.
	  * Cambio estado de solicitud de liquidacion.
	  * 
	  * @throws BusinessException
	  */
	 public LibroDiario aprobarLiquidacionCuentas(SocioComp socioComp, Integer intPeriodo, RequisitoLiquidacion requisitoLiq, ExpedienteLiquidacion expedienteLiquidacionSeleccionado,Usuario usuario,
			ExpedienteLiquidacion expedienteLiquidacion, Integer intEstadoAprobado, Integer intTipoCambio, Date dtNuevoFechaProgramacionPago, Integer intNuevoMotivoRenuncia, Auditoria auditoria )throws BusinessException{
		 LibroDiario libroDiario = null;
		 try{
			 libroDiario = solicitudLiquidacionService.generarProcesosDeLiquidacionCuentas_1(socioComp, intPeriodo, requisitoLiq, expedienteLiquidacionSeleccionado, usuario, expedienteLiquidacion,intEstadoAprobado, intTipoCambio, dtNuevoFechaProgramacionPago, intNuevoMotivoRenuncia, auditoria);
		
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
		 * Se graba en un solo paso la solicitud, los estados pendiente y aprobado y el tipo de solicitud.
		 * @param o
		 * @return
		 * @throws BusinessException
		 */
		@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
		public SolicitudCtaCte grabarSolicitudCtaCteParaLiquidacion_1(SocioComp socioComp,Integer intPeriodo, RequisitoLiquidacion requisitoLiq, Usuario usuario) 
			throws BusinessException{
			
			SolicitudCtaCte dto = null;
			try{
				dto = solicitudLiquidacionService.grabarSolicitudCtaCteParaLiquidacion_1(socioComp,intPeriodo, requisitoLiq, usuario);
				
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
		 * Genera y graba el libro diario y detalle en base a modelo contable de liquidaciond e cunetas
		 */
		@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
		public LibroDiario generarAsientoContableLiquidacionCuentasYTransferencia_2(SolicitudCtaCteTipo solicitudCtaCteTipo, SocioComp socioComp, Usuario usuario, ExpedienteLiquidacion expedienteLiquidacion) 
			throws BusinessException{
			LibroDiario libroDiario = null;
			try {
				
				libroDiario = solicitudLiquidacionService.generarAsientoContableLiquidacionCuentasYTransferencia_2 (solicitudCtaCteTipo, socioComp,usuario, expedienteLiquidacion);
				
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
	   	 * Recupera las cuentas concepto de retiro, aporters y si tuviera interes de retiro.
	   	 * @param socioComp
	   	 * @return
	   	 */
	   	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	   	public List<CuentaConceptoComp> recuperarCuentasConceptoAporteRetiroEInteres(SocioComp socioComp,ExpedienteLiquidacion expedienteLiquidacion) throws BusinessException{
	   		List<CuentaConceptoComp> listaConceptoComp = null;
	   		try {
	   			listaConceptoComp = solicitudLiquidacionService.recuperarCuentasConceptoAporteRetiroEInteres(socioComp,expedienteLiquidacion);
	   		}catch(BusinessException e){
				context.setRollbackOnly();
				throw e;
			}catch(Exception e){
				context.setRollbackOnly();
				throw new BusinessException(e);
			}
	   		
			return listaConceptoComp;
	   	}
	   	
	   	
	   	/**
		 * Registar la Solcitud de devolucion, estados, tipo y la propia devolucion de retiro
		 */
		@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
		public SolicitudCtaCte grabarSolicitudCtaCteParaDevolucionLiquidacion(SocioComp socioComp,Integer intPeriodo, RequisitoLiquidacion requisitoLiq, Usuario usuario,
				ExpedienteLiquidacion expedienteLiquidacion, LibroDiario libroDiario)  throws BusinessException{
			
			SolicitudCtaCte dto = null; 
			try{
				dto = solicitudLiquidacionService.grabarSolicitudCtaCteParaDevolucionLiquidacion(socioComp,intPeriodo, requisitoLiq, usuario,expedienteLiquidacion, libroDiario);
				
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
		 * Genera los moviemitos para cancelar las cunetas concepto de la liquidaciond ee cuenta   	
		 * @param socioComp
		 * @param libroDiarioLiquidacion
		 * @throws BusinessException
		 */
		public List<Movimiento> validarYGenerarCuentaConceptoMovimentoYCuentaCtoDetalleActualizaciones_3(SocioComp socioComp, LibroDiario libroDiarioLiquidacion, Usuario usuario,ExpedienteLiquidacion expedienteLiquidacion) 
			throws BusinessException{
			List<Movimiento> lstMov = null;
			try {
								
				lstMov = solicitudLiquidacionService.validarYGenerarCuentaConceptoMovimentoYCuentaCtoDetalleActualizaciones_3 (socioComp,libroDiarioLiquidacion,usuario,expedienteLiquidacion);
				
			}catch(BusinessException e){
				context.setRollbackOnly();
				throw e;
			}catch(Exception e){
				context.setRollbackOnly();
				throw new BusinessException(e);
			}
			return lstMov;
			
		}
		
		/**
		 * 
		 * @param pExpedienteLiquidacion
		 * @param intTipoCambio
		 * @param dtNuevoFechaProgramacionPago
		 * @param intNuevoMotivoRenuncia
		 * @return
		 * @throws BusinessException
		 */
		public ExpedienteLiquidacion modificarExpedienteLiquidacionParaAuditoria(ExpedienteLiquidacion pExpedienteLiquidacion, Integer intTipoCambio, Date dtNuevoFechaProgramacionPago, Integer intNuevoMotivoRenuncia) throws BusinessException{
			ExpedienteLiquidacion prevision = null;
	    	try{
				prevision = solicitudLiquidacionService.modificarExpedienteLiquidacionParaAuditoria(pExpedienteLiquidacion, intTipoCambio, dtNuevoFechaProgramacionPago, intNuevoMotivoRenuncia);
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
		 * 
		 * @param auditoria
		 * @return
		 * @throws BusinessException
		 */
		public Auditoria grabarAuditoria(Auditoria auditoria)throws BusinessException {
			Auditoria audi = null;
			try {
				audi = solicitudLiquidacionService.grabarAuditoria(auditoria);
			}catch(BusinessException e){
				context.setRollbackOnly();
				throw e;
			}catch(Exception e){
				context.setRollbackOnly();
				throw new BusinessException(e);
			}
			return audi;

		}
		
		


}

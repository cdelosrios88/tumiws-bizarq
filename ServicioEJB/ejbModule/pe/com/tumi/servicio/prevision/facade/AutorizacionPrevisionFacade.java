package pe.com.tumi.servicio.prevision.facade;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import pe.com.tumi.cobranza.planilla.domain.SolicitudCtaCte;
//import pe.com.tumi.cobranza.planilla.domain.SolicitudCtaCteTipo;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiario;
import pe.com.tumi.credito.socio.core.domain.SocioComp;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.facade.TumiFacade;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.seguridad.login.domain.Usuario;
import pe.com.tumi.servicio.prevision.bo.AutorizaPrevisionBO;
import pe.com.tumi.servicio.prevision.bo.AutorizaVerificaPrevisionBO;
//import pe.com.tumi.servicio.prevision.bo.ExpedientePrevisionBO;
import pe.com.tumi.servicio.prevision.domain.AutorizaPrevision;
import pe.com.tumi.servicio.prevision.domain.AutorizaVerificaPrevision;
//import pe.com.tumi.servicio.prevision.domain.ExpedienteLiquidacion;
import pe.com.tumi.servicio.prevision.domain.ExpedientePrevision;
import pe.com.tumi.servicio.prevision.domain.ExpedientePrevisionId;
//import pe.com.tumi.servicio.prevision.domain.RequisitoLiquidacion;
import pe.com.tumi.servicio.prevision.domain.RequisitoPrevision;
import pe.com.tumi.servicio.prevision.service.solicitudPrevisionService;

/**
 * Session Bean implementation class AutorizacionPrevisionFacade
 */
@Stateless
public class AutorizacionPrevisionFacade extends TumiFacade implements AutorizacionPrevisionFacadeRemote, AutorizacionPrevisionFacadeLocal {
	private solicitudPrevisionService  solicitudPrevisionService = (solicitudPrevisionService)TumiFactory.get(solicitudPrevisionService.class);
//	private ExpedientePrevisionBO boExpedientePrevision = (ExpedientePrevisionBO)TumiFactory.get(ExpedientePrevisionBO.class);	
	private AutorizaPrevisionBO boAutorizaPrevision = (AutorizaPrevisionBO)TumiFactory.get(AutorizaPrevisionBO.class);
	private AutorizaVerificaPrevisionBO boAutorizaVerificaPrevision = (AutorizaVerificaPrevisionBO)TumiFactory.get(AutorizaVerificaPrevisionBO.class);


	
	/**
	 * 
	 */
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
	
	/**
	 * Recupera lista de AutorizaPrevision por id de expediuente
	 */
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
	
	
	/**
	 * Recupera List<AutorizaVerificaPrevision> en base al id del expededinete de prevision
	 */
	public List<AutorizaVerificaPrevision> getListaVerificaPrevisionPorPkExpediente(ExpedientePrevisionId pId) throws BusinessException{
		List<AutorizaVerificaPrevision> lista = null;
		try{
			lista = boAutorizaVerificaPrevision.getListaPorPkExpedientePrevision(pId);
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
	  * Registra el proceso de Autorizacion de Prevsiion.
	  * Cambio de Estado de la Cuenta. xxxxxxxxxxxxxx
	  * Cambio de estado de Cuentas concepto detalle. xxxxxxxxxxxxxxxx
	  * Generar asientos en libro diario.
	  * Cambio estado de solicitud de liquidacion.
	  * 
	  * @throws BusinessException
	  */
	 public LibroDiario aprobarPrevision(SocioComp socioComp, Integer intPeriodo, RequisitoPrevision requisitoPrev, ExpedientePrevision expedientePrevisionSeleccionado,Usuario usuario,
			ExpedientePrevision expedientePrevision, Integer intEstadoAprobado )throws BusinessException{
		 LibroDiario libroDiario = null;
		 try{
			 libroDiario = solicitudPrevisionService.generarProcesosDePrevision_1(socioComp, intPeriodo, requisitoPrev, expedientePrevisionSeleccionado, usuario, expedientePrevision,intEstadoAprobado );
		
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
		 * Se graba en un solo paso la solicitud, los estados pendiente y aprobado y el tipo de solicitud cta cte
		 * Para el caso de prevision.
		 * @param socioComp
		 * @param intPeriodo
		 * @param requisitoLiq
		 * @param usuario
		 * @return
		 * @throws BusinessException
		 */
		@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
		public SolicitudCtaCte grabarSolicitudCtaCteParaPrevision(SocioComp socioComp,Integer intPeriodo, RequisitoPrevision requisitoPrev, Usuario usuario,
				ExpedientePrevision expedientePrevision)  throws BusinessException{
			
			SolicitudCtaCte dto = null;
			try{
				dto = solicitudPrevisionService.grabarSolicitudCtaCteParaPrevision(socioComp,intPeriodo, requisitoPrev, usuario,expedientePrevision);
				
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
		public LibroDiario generarAsientoContablePrevisionRetiroYTransferencia(SolicitudCtaCte solicitudCtaCte, SocioComp socioComp, Usuario usuario, ExpedientePrevision expedientePrevision) 
			throws BusinessException{
			LibroDiario libroDiario = null;
			try {
				
				libroDiario = solicitudPrevisionService.generarAsientoContablePrevisionRetiroYTransferencia(solicitudCtaCte, socioComp,usuario, expedientePrevision);
				
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
		 * Registar la Solcitud de devolucion, estados, tipo y la propia devolucion de retiro
		 */
		@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
		public SolicitudCtaCte grabarSolicitudCtaCteParaPrevisionDevolucionRetiro(SocioComp socioComp,Integer intPeriodo, RequisitoPrevision requisitoPrev, Usuario usuario,
				ExpedientePrevision expedientePrevision, LibroDiario libroDiario)  throws BusinessException{
			
			SolicitudCtaCte dto = null;
			try{
				dto = solicitudPrevisionService.grabarSolicitudCtaCteParaPrevisionDevolucionRetiro(socioComp,intPeriodo, requisitoPrev, usuario,expedientePrevision, libroDiario);
				
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
		 * Registar la Solcitud de devolucion, estados, tipo y la propia devolucion de sepelio
		 */
		@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
		public SolicitudCtaCte grabarSolicitudCtaCteParaPrevisionDevolucionSepelio(SocioComp socioComp,Integer intPeriodo, RequisitoPrevision requisitoPrev, Usuario usuario,
				ExpedientePrevision expedientePrevision, LibroDiario libroDiario)  throws BusinessException{
			
			SolicitudCtaCte dto = null;
			try{
				dto = solicitudPrevisionService.grabarSolicitudCtaCteParaPrevisionDevolucionSepelio(socioComp,intPeriodo, requisitoPrev, usuario,expedientePrevision, libroDiario);
				
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


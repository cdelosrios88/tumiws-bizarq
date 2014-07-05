package pe.com.tumi.cobranza.cuentacte.facade;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import pe.com.tumi.cobranza.cuentacte.service.CuentaCtaCteService;
import pe.com.tumi.cobranza.planilla.bo.DescuentoIndebidoBO;
import pe.com.tumi.cobranza.planilla.bo.DevolucionBO;
import pe.com.tumi.cobranza.planilla.bo.EstadoSolicitudCtaCteBO;
import pe.com.tumi.cobranza.planilla.bo.SolicitudCtaCteBO;
import pe.com.tumi.cobranza.planilla.bo.SolicitudCtaCteTipoBO;
import pe.com.tumi.cobranza.planilla.bo.TransferenciaBO;
import pe.com.tumi.cobranza.planilla.domain.DescuentoIndebido;
import pe.com.tumi.cobranza.planilla.domain.Devolucion;
import pe.com.tumi.cobranza.planilla.domain.EstadoSolicitudCtaCte;
import pe.com.tumi.cobranza.planilla.domain.SolicitudCtaCte;
import pe.com.tumi.cobranza.planilla.domain.SolicitudCtaCteTipo;
import pe.com.tumi.cobranza.planilla.domain.SolicitudCtaCteTipoId;
import pe.com.tumi.cobranza.planilla.domain.Transferencia;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiario;
import pe.com.tumi.credito.socio.core.domain.SocioComp;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.facade.TumiFacade;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.movimiento.concepto.domain.Expediente;
import pe.com.tumi.movimiento.concepto.domain.composite.CuentaConceptoComp;
import pe.com.tumi.seguridad.login.domain.Usuario;
import pe.com.tumi.servicio.prevision.domain.ExpedienteLiquidacion;
import pe.com.tumi.servicio.prevision.domain.RequisitoLiquidacion;
import pe.com.tumi.servicio.solicitudPrestamo.domain.ExpedienteCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.RequisitoCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.composite.ExpedienteCreditoComp;

@Stateless
public class CuentacteFacade extends TumiFacade implements CuentacteFacadeRemote, CuentacteFacadeLocal {
       
	SolicitudCtaCteTipoBO boSolicitutCtaCteTipo = (SolicitudCtaCteTipoBO)TumiFactory.get(SolicitudCtaCteTipoBO.class);
	SolicitudCtaCteBO boSolicitutCtaCte = (SolicitudCtaCteBO)TumiFactory.get(SolicitudCtaCteBO.class);
	EstadoSolicitudCtaCteBO  boEstadoSolCtaCte = (EstadoSolicitudCtaCteBO)TumiFactory.get(EstadoSolicitudCtaCteBO.class);
	CuentaCtaCteService   serviceCuentaCtaCte   = (CuentaCtaCteService)TumiFactory.get(CuentaCtaCteService.class);
	DescuentoIndebidoBO boDescuentoIndebido = (DescuentoIndebidoBO)TumiFactory.get(DescuentoIndebidoBO.class);
	DevolucionBO boDevolucion = (DevolucionBO)TumiFactory.get(DevolucionBO.class);
	TransferenciaBO boTransferencia = (TransferenciaBO)TumiFactory.get(TransferenciaBO.class);
	
	

	
	public SolicitudCtaCteTipo getSolicitudCtaCteTipoPorPk(SolicitudCtaCteTipoId pId) throws BusinessException{
		SolicitudCtaCteTipo dto = null;
		try{
			dto = boSolicitutCtaCteTipo.getSolicitudCtaCteTipoPorPk(pId);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public SolicitudCtaCte grabarSolicitudCtaCteAntedido(SolicitudCtaCte o) throws BusinessException{
		SolicitudCtaCte dto = null;
		try{
			
			dto = serviceCuentaCtaCte.grabarSolicitudCtaCteAntedido(o);
			
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;
	}
	
	//
	public SolicitudCtaCte grabarSolicitudCtaCte(SolicitudCtaCte o) throws BusinessException{
		SolicitudCtaCte dto = null;
		try{
			
			dto = boSolicitutCtaCte.grabarSolicitudCtaCte(o);
			
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public SolicitudCtaCteTipo grabarSolicitudCtaCteTipo(SolicitudCtaCteTipo o) throws BusinessException{
		SolicitudCtaCteTipo dto = null;
		try{
			
			dto = boSolicitutCtaCteTipo.grabarSolicitudCtaCteTipo(o);
			
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public EstadoSolicitudCtaCte grabarEstadoSolicitudCtaCte(EstadoSolicitudCtaCte o) throws BusinessException{
		EstadoSolicitudCtaCte dto = null;
		try{
			
			dto = boEstadoSolCtaCte.grabarEstadoSolicitudCtaCte(o);
			
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
	 * Invoca a: 
	 * serviceCuentaCtaCte.generarTransferenciaRefinanciamiento(solicitudCtaCteTipo, usuario, expedientePorRefinanciar);
	 */
	
			
	public LibroDiario generarProcesosDeRefinanciamiento(SocioComp socioComp, String strPeriodo, RequisitoCredito requisitoCred, Usuario usuario, 
			Expediente expedienteMovAnterior, ExpedienteCredito expedienteCreditoNuevo, ExpedienteCreditoComp expedienteCreditoCompSelected) throws BusinessException{
			LibroDiario libroDiario = null;
			try {
				
				libroDiario = serviceCuentaCtaCte.generarProcesosDeRefinanciamiento_1(socioComp, strPeriodo, requisitoCred, usuario, 
						expedienteMovAnterior, expedienteCreditoNuevo, expedienteCreditoCompSelected);


			} catch(BusinessException e){
				context.setRollbackOnly();
				throw e;
			}catch(Exception e){
				context.setRollbackOnly();
				throw new BusinessException(e);
			}
			return libroDiario ;

	}
	
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<SolicitudCtaCte> getListaPorCuenta (Integer empresasolctacte,  Integer intCsocCuenta)  throws BusinessException{
		List<SolicitudCtaCte> lista = null;
		try {
						
			lista= boSolicitutCtaCte.getListaPorCuenta(empresasolctacte, intCsocCuenta);
				  
		} catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return lista;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<SolicitudCtaCte> SolicitudesTipoTransf (Integer empresasolctacte,  Integer intCsocCuenta)  throws BusinessException{
		List<SolicitudCtaCte> lista = null;
		try {
						
			lista= serviceCuentaCtaCte.SolicitudesTipoTransf(empresasolctacte, intCsocCuenta);
				  
		} catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return lista;
	}

	
	/*/**
	 * Se graba en un solo paso la solicitud, los estados pendiente y aprobado y el tipo de solicitud.
	 * @param o
	 * @return
	 * @throws BusinessException
	 */
	/*@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public SolicitudCtaCte grabarSolicitudCtaCteParaLiquidacion_1(SocioComp socioComp,Integer intPeriodo, RequisitoLiquidacion requisitoLiq, Usuario usuario) 
		throws BusinessException{
		
		SolicitudCtaCte dto = null;
		try{
			dto = serviceCuentaCtaCte.grabarSolicitudCtaCteParaLiquidacion_1(socioComp,intPeriodo, requisitoLiq, usuario);
			
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;
	}*/
	
	
	/*/**
	 * Genera y graba el libro diario y detalle en base a modelo contable de liquidaciond e cunetas
	 */
	/*@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public LibroDiario generarAsientoContableLiquidacionCuentasYTransferencia_2(SolicitudCtaCteTipo solicitudCtaCteTipo, SocioComp socioComp, Usuario usuario, ExpedienteLiquidacion expedienteLiquidacion) 
		throws BusinessException{
		LibroDiario libroDiario = null;
		try {
			
			libroDiario = serviceCuentaCtaCte.generarAsientoContableLiquidacionCuentasYTransferencia_2 (solicitudCtaCteTipo, socioComp,usuario, expedienteLiquidacion);
			
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return libroDiario;
		
	}*/
	
	
	   

	   /**
	    * Actualiza solo la solicitud cta cte tipo.
	    * Utilizado para actulizar los campos de con_periodolibro y con_codigolibro.
	    * @param solicitudCtaCTeTipo
	    * @return
	    */
	   	public SolicitudCtaCteTipo modificarSolicitudCuentaCorrienteTipo (SolicitudCtaCteTipo solicitudCtaCTeTipo) throws BusinessException{
	   		
	   		try {
	   			if(solicitudCtaCTeTipo != null){
	   				solicitudCtaCTeTipo = boSolicitutCtaCteTipo.modificarSolicitudCtaCteTipo(solicitudCtaCTeTipo);
	   			}

	   		}catch(BusinessException e){
				context.setRollbackOnly();
				throw e;
			}catch(Exception e){
				context.setRollbackOnly();
				throw new BusinessException(e);
			}
	   		return solicitudCtaCTeTipo;
	   	}
	
	   	
	   	
	   	/**
	   	 * Recupera las cuentas concepto de retiro, aporters y si tuviera interes de retiro.
	   	 * @param socioComp
	   	 * @return
	   	 */
	  /* 	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	   	public List<CuentaConceptoComp> recuperarCuentasConceptoAporteRetiroEInteres(SocioComp socioComp) throws BusinessException{
	   		List<CuentaConceptoComp> listaConceptoComp = null;
	   		try {
	   			listaConceptoComp = serviceCuentaCtaCte.recuperarCuentasConceptoAporteRetiroEInteres(socioComp);
	   		}catch(BusinessException e){
				context.setRollbackOnly();
				throw e;
			}catch(Exception e){
				context.setRollbackOnly();
				throw new BusinessException(e);
			}
	   		
			return listaConceptoComp;
	   	}*/
	   	

	/**
	 * Genera los moviemitos para cancelar las cunetas concepto de la liquidaciond ee cuenta   	
	 * @param socioComp
	 * @param libroDiarioLiquidacion
	 * @throws BusinessException
	 */
	/*public Boolean validarYGenerarCuentaConceptoMovimentoYCuentaCtoDetalleActualizaciones_3(SocioComp socioComp, LibroDiario libroDiarioLiquidacion, Usuario usuario) 
		throws BusinessException{
		Boolean blnOk = Boolean.FALSE;
		try {
			
			blnOk = serviceCuentaCtaCte.validarYGenerarCuentaConceptoMovimentoYCuentaCtoDetalleActualizaciones_3 (socioComp,libroDiarioLiquidacion,usuario);
			
		}catch(BusinessException e){
			blnOk = Boolean.FALSE;
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			blnOk = Boolean.FALSE;
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return blnOk;
		
	}*/
	
	
	/**
	 * 
	 * @return
	 */
	
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<DescuentoIndebido> getListaDesceuntoIndebidoXEmpYCta(Integer intIdEmpresa, Integer intCsocCuenta) throws BusinessException {
		 List<DescuentoIndebido> lista = null;
		try {
			lista = boDescuentoIndebido.getListPorEmpYCta(intIdEmpresa, intCsocCuenta);
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
	 * Graba un registro en Tabla Devolucion
	 * @param devolucion
	 * @return
	 * @throws BusinessException
	 */
	public Devolucion grabarDevolucion(Devolucion devolucion) throws BusinessException{
	
	Devolucion dto = null;
	try{
		dto = boDevolucion.grabar(devolucion);
		
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
	 * Graba un registro en Tabla Devolucion
	 * @param devolucion
	 * @return
	 * @throws BusinessException
	 */
	public Transferencia grabarTransgerencia(Transferencia transferencia) throws BusinessException{
	
		Transferencia dto = null;
	try{
		dto = boTransferencia.grabarTransferencia(transferencia);
		
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

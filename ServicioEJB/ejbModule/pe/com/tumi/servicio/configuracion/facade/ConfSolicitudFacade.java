package pe.com.tumi.servicio.configuracion.facade;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import pe.com.tumi.credito.socio.creditos.domain.Credito;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.facade.TumiFacade;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.servicio.configuracion.bo.ConfServDetalleBO;
import pe.com.tumi.servicio.configuracion.bo.ConfServSolicitudBO;
import pe.com.tumi.servicio.configuracion.domain.ConfServCancelado;
import pe.com.tumi.servicio.configuracion.domain.ConfServCaptacion;
import pe.com.tumi.servicio.configuracion.domain.ConfServCredito;
import pe.com.tumi.servicio.configuracion.domain.ConfServCreditoEmpresa;
import pe.com.tumi.servicio.configuracion.domain.ConfServDetalle;
import pe.com.tumi.servicio.configuracion.domain.ConfServDetalleId;
import pe.com.tumi.servicio.configuracion.domain.ConfServEstructuraDetalle;
import pe.com.tumi.servicio.configuracion.domain.ConfServGrupoCta;
import pe.com.tumi.servicio.configuracion.domain.ConfServPerfil;
import pe.com.tumi.servicio.configuracion.domain.ConfServRol;
import pe.com.tumi.servicio.configuracion.domain.ConfServSolicitud;
import pe.com.tumi.servicio.configuracion.domain.ConfServSolicitudId;
import pe.com.tumi.servicio.configuracion.domain.ConfServUsuario;
import pe.com.tumi.servicio.configuracion.servicio.ConfSolicitudService;

@Stateless
public class ConfSolicitudFacade extends TumiFacade implements ConfSolicitudFacadeRemote, ConfSolicitudFacadeLocal {
	ConfServSolicitudBO boConfServSolicitud = (ConfServSolicitudBO)TumiFactory.get(ConfServSolicitudBO.class); 
	ConfServDetalleBO boConfServDetalle = (ConfServDetalleBO)TumiFactory.get(ConfServDetalleBO.class);
	ConfSolicitudService confSolicitudService = (ConfSolicitudService)TumiFactory.get(ConfSolicitudService.class);
	
	public ConfServSolicitud grabarConfiguracion(ConfServSolicitud o)throws BusinessException{
		ConfServSolicitud dto = null;
		try{
			dto = boConfServSolicitud.grabar(o);
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
   	public ConfServSolicitud getConfiguracionPorPk(ConfServSolicitudId pId) throws BusinessException{
    	ConfServSolicitud dto = null;
   		try{
   			dto = boConfServSolicitud.getPorPk(pId);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return dto;
   	}
    
    public ConfServSolicitud grabarLiquidacionCuenta(ConfServSolicitud o)throws BusinessException{
		ConfServSolicitud dto = null;
		try{
			dto = confSolicitudService.grabarLiquidacionCuenta(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;
	}
    
    public ConfServSolicitud grabarCredito(ConfServSolicitud o)throws BusinessException{
		ConfServSolicitud dto = null;
		try{
			dto = confSolicitudService.grabarCredito(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;
	}
    
    public ConfServSolicitud grabarCaptacion(ConfServSolicitud o)throws BusinessException{
		ConfServSolicitud dto = null;
		try{
			dto = confSolicitudService.grabarCaptacion(o);
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
    public List<ConfServSolicitud> buscarConfSolicitudRequisito(ConfServSolicitud o, Date fechaFiltroInicio, Date fechaFiltroFin, Integer tipoCuentaFiltro)throws BusinessException{
		List<ConfServSolicitud> lista = null;
		try{
			lista = confSolicitudService.buscarConfSolicitudRequisito(o,fechaFiltroInicio,fechaFiltroFin,tipoCuentaFiltro);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<ConfServSolicitud> buscarConfSolicitudAutorizacion(ConfServSolicitud o, Date fechaFiltroInicio, Date fechaFiltroFin, Integer tipoCuentaFiltro)throws BusinessException{
		List<ConfServSolicitud> lista = null;
		try{
			lista = confSolicitudService.buscarConfSolicitudAutorizacion(o,fechaFiltroInicio,fechaFiltroFin,tipoCuentaFiltro);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
    
    public ConfServSolicitud modificarConfiguracion(ConfServSolicitud o)throws BusinessException{
		ConfServSolicitud dto = null;
		try{
			dto = boConfServSolicitud.modificar(o);
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
    public List<ConfServDetalle> getListaConfServDetallePorCabecera(ConfServSolicitud o)throws BusinessException{
		List<ConfServDetalle> lista = null;
		try{
			lista = confSolicitudService.getListaConfServDetallePorCabecera(o);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
    
    public ConfServDetalle getConfServDetallePorPk(ConfServDetalleId o)throws BusinessException{
		ConfServDetalle dto = null;
		try{
			dto = boConfServDetalle.getPorPk(o);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return dto;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<ConfServRol> getListaConfServRolPorCabecera(ConfServSolicitud o)throws BusinessException{
		List<ConfServRol> lista = null;
		try{
			lista = confSolicitudService.getListaConfServRolPorCabecera(o);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<ConfServGrupoCta> getListaConfServGrupoCtaPorCabecera(ConfServSolicitud o)throws BusinessException{
		List<ConfServGrupoCta> lista = null;
		try{
			lista = confSolicitudService.getListaConfServGrupoCtaPorCabecera(o);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<ConfServEstructuraDetalle> getListaConfServEstructuraPorCabecera(ConfServSolicitud o)throws BusinessException{
		List<ConfServEstructuraDetalle> lista = null;
		try{
			lista = confSolicitudService.getListaConfServEstructuraDetallePorCabecera(o);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}    
    
    public ConfServSolicitud modificarLiquidacionCuenta(ConfServSolicitud o)throws BusinessException{
		ConfServSolicitud dto = null;
		try{
			dto = confSolicitudService.modificarLiquidacionCuenta(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;
	}
    
    public ConfServSolicitud modificarCredito(ConfServSolicitud o)throws BusinessException{
		ConfServSolicitud dto = null;
		try{
			dto = confSolicitudService.modificarCredito(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;
	}
    
    public ConfServSolicitud modificarCaptacion(ConfServSolicitud o)throws BusinessException{
		ConfServSolicitud dto = null;
		try{
			dto = confSolicitudService.modificarCaptacion(o);
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
    public List<ConfServCredito> getListaConfServCreditoPorCabecera(ConfServSolicitud o)throws BusinessException{
		List<ConfServCredito> lista = null;
		try{
			lista = confSolicitudService.getListaConfServCreditoPorCabecera(o);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
    
    public ConfServSolicitud grabarAutorizacion(ConfServSolicitud o)throws BusinessException{
		ConfServSolicitud dto = null;
		try{
			dto = confSolicitudService.grabarAutorizacion(o);
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
    public List<ConfServCreditoEmpresa> getListaConfServCreditoEmpresaPorCabecera(ConfServSolicitud o)throws BusinessException{
		List<ConfServCreditoEmpresa> lista = null;
		try{
			lista = confSolicitudService.getListaConfServCreditoEmpresaPorCabecera(o);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<ConfServCancelado> getListaConfServCanceladoPorCabecera(ConfServSolicitud o)throws BusinessException{
		List<ConfServCancelado> lista = null;
		try{
			lista = confSolicitudService.getListaConfServCanceladoPorCabecera(o);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<ConfServPerfil> getListaConfServPerfilPorCabecera(ConfServSolicitud o)throws BusinessException{
		List<ConfServPerfil> lista = null;
		try{
			lista = confSolicitudService.getListaConfServPerfilPorCabecera(o);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<ConfServUsuario> getListaConfServUsuarioPorCabecera(ConfServSolicitud o)throws BusinessException{
		List<ConfServUsuario> lista = null;
		try{
			lista = confSolicitudService.getListaConfServUsuarioPorCabecera(o);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
    
    public ConfServSolicitud modificarAutorizacion(ConfServSolicitud o)throws BusinessException{
		ConfServSolicitud dto = null;
		try{
			dto = confSolicitudService.modificarAutorizacion(o);
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
    public List<ConfServCaptacion> getListaConfServCaptacionPorCabecera(ConfServSolicitud o)throws BusinessException{
		List<ConfServCaptacion> lista = null;
		try{
			lista = confSolicitudService.getListaConfServCaptacionPorCabecera(o);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<ConfServSolicitud> buscarConfSolicitudRequisitoOptimizado(ConfServSolicitud o, Integer tipoCuentaFiltro, Credito credito)throws BusinessException{
		List<ConfServSolicitud> lista = null;
		try{
			lista = confSolicitudService.buscarConfSolicitudRequisitoOptimizado(o, tipoCuentaFiltro, credito);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
}
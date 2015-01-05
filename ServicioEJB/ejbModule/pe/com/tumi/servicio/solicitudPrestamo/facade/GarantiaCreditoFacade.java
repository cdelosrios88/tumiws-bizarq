package pe.com.tumi.servicio.solicitudPrestamo.facade;

import java.util.List;

import javax.ejb.Stateless;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.facade.TumiFacade;
import pe.com.tumi.framework.negocio.factory.TumiFactory;

import pe.com.tumi.servicio.solicitudPrestamo.bo.GarantiaCreditoBO;

import pe.com.tumi.servicio.solicitudPrestamo.domain.ExpedienteCreditoId;
import pe.com.tumi.servicio.solicitudPrestamo.domain.GarantiaCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.GarantiaCreditoId;

import pe.com.tumi.servicio.solicitudPrestamo.domain.composite.GarantiaCreditoComp;

import pe.com.tumi.servicio.solicitudPrestamo.service.GarantiaCreditoService;

/**
 * Session Bean implementation class SolicitudPrestamoFacade
 */
@Stateless
public class GarantiaCreditoFacade extends TumiFacade implements GarantiaCreditoFacadeRemote, GarantiaCreditoFacadeLocal {
	
	private GarantiaCreditoService garantiaCreditoService = (GarantiaCreditoService)TumiFactory.get(GarantiaCreditoService.class);
	private GarantiaCreditoBO garantiaCreditoBo = (GarantiaCreditoBO)TumiFactory.get(GarantiaCreditoBO.class);
	
	//fyalico, fecha:2014/11/04 mofidificacion: se agrego intTipoCuenta, que es el tipo de cuenta en la tabla Cuenta ej socio, ahorro, etc. 
	public List<GarantiaCreditoComp> getListaGarantiaCreditoCompPorExpediente(ExpedienteCreditoId pId,
																			  Integer intTipoCuenta) throws BusinessException{
		List<GarantiaCreditoComp> lista = null;
		try{
			
			lista = garantiaCreditoService.getListaGarantiaCreditoCompPorExpediente(pId, intTipoCuenta);
			
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		
		return lista;
	}
	
	public GarantiaCredito modificarGarantiaCredito(GarantiaCredito o) throws BusinessException{
		
		GarantiaCredito dto = null;
		try{
			dto = garantiaCreditoBo.modificar(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public GarantiaCredito getGarantiaCredito(GarantiaCreditoId pId) throws BusinessException{
		GarantiaCredito dto = null;
		try{
			            dto = garantiaCreditoBo.getPorPk(pId);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public GarantiaCredito grabarGarantiaCredito(GarantiaCredito o) throws BusinessException{
		GarantiaCredito dto = null;
		try{
			            dto = garantiaCreditoBo.grabar(o);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return dto;
	}	
	/**
	 * AUTOR Y FECHA CREACION: JCHAVEZ / 04-09-2013 
	 * OBTENER LISTA DE GARANTIZADOS
	 */
	public List<GarantiaCredito> getListaGarantiaCreditoPorEmpPersCta(GarantiaCredito garantiaCredito) throws BusinessException{
		List<GarantiaCredito> lista = null;
		try{			
			lista = garantiaCreditoBo.getListaGarantiasPorEmpPersCta(garantiaCredito);			
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		
		return lista;
	}
}
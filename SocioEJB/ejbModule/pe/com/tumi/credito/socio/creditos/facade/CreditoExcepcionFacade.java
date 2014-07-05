package pe.com.tumi.credito.socio.creditos.facade;

import java.util.List;

import javax.ejb.Stateless;

import pe.com.tumi.credito.socio.creditos.bo.CreditoExcepcionBO;
import pe.com.tumi.credito.socio.creditos.domain.CreditoExcepcion;
import pe.com.tumi.credito.socio.creditos.domain.CreditoExcepcionId;
import pe.com.tumi.credito.socio.creditos.domain.CreditoId;
import pe.com.tumi.credito.socio.creditos.service.CreditoExcepcionService;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.facade.TumiFacade;
import pe.com.tumi.framework.negocio.factory.TumiFactory;

/**
 * Session Bean implementation class CreditoExcepcionFacade
 */
@Stateless
public class CreditoExcepcionFacade extends TumiFacade implements CreditoExcepcionFacadeRemote, CreditoExcepcionFacadeLocal {
    
	private CreditoExcepcionBO boCreditoExcepcion = (CreditoExcepcionBO)TumiFactory.get(CreditoExcepcionBO.class);
	private CreditoExcepcionService creditoExcepcionService = (CreditoExcepcionService)TumiFactory.get(CreditoExcepcionService.class);
	
	public List<CreditoExcepcion> getListaCreditoExcepcion(CreditoId o) throws BusinessException{
		List<CreditoExcepcion> lista = null;
		try{
			lista = creditoExcepcionService.getListaCreditoExcepcion(o);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public CreditoExcepcion grabarCreditoExcepcion(CreditoExcepcion o) throws BusinessException{
		CreditoExcepcion creditoExcepcion = null;
		try{
			creditoExcepcion = creditoExcepcionService.grabarCreditoExcepcion(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return creditoExcepcion;
	}
	
	public CreditoExcepcion modificarCreditoExcepcion(CreditoExcepcion o) throws BusinessException{
		CreditoExcepcion creditoExcepcion = null;
		try{
			creditoExcepcion = creditoExcepcionService.modificarCreditoExcepcion(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return creditoExcepcion;
	}
	
	public CreditoExcepcion getCreditoExcepcionPorIdCreditoExcepcion(CreditoExcepcionId pId) throws BusinessException{
		CreditoExcepcion creditoExcepcion = null;
		try{
			creditoExcepcion = creditoExcepcionService.getCreditoExcepcionPorIdCreditoExcepcion(pId);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return creditoExcepcion;
	}
	
	public CreditoExcepcion eliminarCreditoExcepcion(CreditoExcepcion o) throws BusinessException{
		CreditoExcepcion creditoExcepcion = null;
		try{
			creditoExcepcion = boCreditoExcepcion.eliminarCreditoExcepcion(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return creditoExcepcion;
	}

}

package pe.com.tumi.credito.socio.creditos.facade;

import java.util.List;

import javax.ejb.Stateless;

import pe.com.tumi.credito.socio.captacion.bo.CaptacionBO;
import pe.com.tumi.credito.socio.captacion.domain.CaptacionId;
import pe.com.tumi.credito.socio.credito.domain.composite.CreditoComp;
import pe.com.tumi.credito.socio.creditos.bo.CreditoDescuentoBO;
import pe.com.tumi.credito.socio.creditos.domain.Credito;
import pe.com.tumi.credito.socio.creditos.domain.CreditoDescuento;
import pe.com.tumi.credito.socio.creditos.domain.CreditoDescuentoId;
import pe.com.tumi.credito.socio.creditos.domain.CreditoId;
import pe.com.tumi.credito.socio.creditos.service.CreditoDescuentoService;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.facade.TumiFacade;
import pe.com.tumi.framework.negocio.factory.TumiFactory;

/**
 * Session Bean implementation class CreditoDescuentoFacade
 */
@Stateless
public class CreditoDescuentoFacade extends TumiFacade implements CreditoDescuentoFacadeRemote, CreditoDescuentoFacadeLocal {
	
	private CreditoDescuentoBO boCreditoDescuento = (CreditoDescuentoBO)TumiFactory.get(CreditoDescuentoBO.class);
	private CreditoDescuentoService creditoDescuentoService = (CreditoDescuentoService)TumiFactory.get(CreditoDescuentoService.class);
	
	public List<CreditoDescuento> getListaCreditoDescuento(CreditoId o) throws BusinessException{
		List<CreditoDescuento> lista = null;
		try{
			lista = creditoDescuentoService.getListaCreditoDescuento(o);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public CreditoDescuento grabarCreditoDescuento(CreditoDescuento o) throws BusinessException{
		CreditoDescuento creditoDescuento = null;
		try{
			creditoDescuento = creditoDescuentoService.grabarCreditoDescuento(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return creditoDescuento;
	}
	
	public CreditoDescuento modificarCreditoDescuento(CreditoDescuento o) throws BusinessException{
		CreditoDescuento creditoDescuento = null;
		try{
			creditoDescuento = creditoDescuentoService.modificarCreditoDescuento(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return creditoDescuento;
	}
	
	public CreditoDescuento getCreditoDescuentoPorIdCreditoDescuento(CreditoDescuentoId pId) throws BusinessException{
		CreditoDescuento creditoDescuento = null;
		try{
			creditoDescuento = creditoDescuentoService.getCreditoDescuentoPorIdCreditoDescuento(pId);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return creditoDescuento;
	}
	
	public CreditoDescuento eliminarCreditoDescuento(CreditoDescuento o) throws BusinessException{
		CreditoDescuento creditoDescuento = null;
		try{
			creditoDescuento = boCreditoDescuento.eliminarCreditoDescuento(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return creditoDescuento;
	}
}

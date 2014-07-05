package pe.com.tumi.credito.socio.creditos.facade;

import java.util.List;

import javax.ejb.Stateless;

import pe.com.tumi.credito.socio.creditos.bo.CondicionCreditoBO;
import pe.com.tumi.credito.socio.creditos.domain.CondicionCredito;
import pe.com.tumi.credito.socio.creditos.domain.CreditoId;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.facade.TumiFacade;
import pe.com.tumi.framework.negocio.factory.TumiFactory;

/**
 * Session Bean implementation class CondicionCreditoFacade
 */
@Stateless
public class CondicionCreditoFacade extends TumiFacade implements CondicionCreditoFacadeRemote, CondicionCreditoFacadeLocal {

private CondicionCreditoBO boCondicion = (CondicionCreditoBO)TumiFactory.get(CondicionCreditoBO.class);	
	
	public List<CondicionCredito> listarCondicion(CreditoId o) throws BusinessException {
		List<CondicionCredito> lista = null;
		try{
			lista = boCondicion.getListaPorPKCredito(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public CondicionCredito grabar(CondicionCredito o) throws BusinessException{
		CondicionCredito condicionCredito = null;
		try{
			condicionCredito = boCondicion.grabarCondicion(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return condicionCredito;
	}

}

package pe.com.tumi.credito.socio.creditos.facade;

import java.util.List;

import javax.ejb.Stateless;

import pe.com.tumi.credito.socio.credito.domain.composite.CreditoTipoGarantiaComp;
import pe.com.tumi.credito.socio.creditos.bo.CreditoDescuentoBO;
import pe.com.tumi.credito.socio.creditos.bo.CreditoGarantiaBO;
import pe.com.tumi.credito.socio.creditos.domain.Credito;
import pe.com.tumi.credito.socio.creditos.domain.CreditoDescuento;
import pe.com.tumi.credito.socio.creditos.domain.CreditoDescuentoId;
import pe.com.tumi.credito.socio.creditos.domain.CreditoGarantia;
import pe.com.tumi.credito.socio.creditos.domain.CreditoGarantiaId;
import pe.com.tumi.credito.socio.creditos.domain.CreditoId;
import pe.com.tumi.credito.socio.creditos.domain.CreditoTipoGarantiaId;
import pe.com.tumi.credito.socio.creditos.service.CreditoGarantiaService;
import pe.com.tumi.credito.socio.creditos.service.CreditoService;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.facade.TumiFacade;
import pe.com.tumi.framework.negocio.factory.TumiFactory;

/**
 * Session Bean implementation class CreditoGarantiaFacade
 */
@Stateless
public class CreditoGarantiaFacade extends TumiFacade implements CreditoGarantiaFacadeRemote, CreditoGarantiaFacadeLocal {
	
	private CreditoGarantiaService creditoGarantiaService = (CreditoGarantiaService)TumiFactory.get(CreditoGarantiaService.class);
	private CreditoGarantiaBO boCreditoGarantia = (CreditoGarantiaBO)TumiFactory.get(CreditoGarantiaBO.class);
	
	public List<CreditoGarantia> getListaCreditoGarantia(CreditoGarantiaId o) throws BusinessException{
		List<CreditoGarantia> lista = null;
		try{
			lista = creditoGarantiaService.getListaCreditoGarantia(o);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public CreditoGarantia grabarCreditoGarantia(CreditoGarantia o) throws BusinessException{
		CreditoGarantia creditoGarantia = null;
		try{
			creditoGarantia = creditoGarantiaService.grabarCreditoGarantia(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return creditoGarantia;
	}
	
	public CreditoGarantia modificarCreditoGarantia(CreditoGarantia o) throws BusinessException{
		CreditoGarantia creditoGarantia = null;
		try{
			creditoGarantia = creditoGarantiaService.modificarCreditoGarantia(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return creditoGarantia;
	}
	
	public CreditoGarantia getCreditoGarantiaPorIdCreditoGarantia(CreditoGarantiaId pId) throws BusinessException{
		CreditoGarantia creditoGarantia = null;
		try{
			creditoGarantia = creditoGarantiaService.getCreditoGarantiaPorIdCreditoGarantia(pId);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return creditoGarantia;
	}
	
	public CreditoGarantia eliminarCreditoGarantia(CreditoGarantia o) throws BusinessException{
		CreditoGarantia creditoGarantia = null;
		try{
			creditoGarantia = boCreditoGarantia.eliminarCreditoGarantia(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return creditoGarantia;
	}

}

package pe.com.tumi.contabilidad.operaciones.facade;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import pe.com.tumi.contabilidad.operaciones.bo.HojaManualBO;
import pe.com.tumi.contabilidad.operaciones.domain.HojaManual;
import pe.com.tumi.contabilidad.operaciones.domain.HojaManualDetalle;
import pe.com.tumi.contabilidad.operaciones.domain.HojaManualId;
import pe.com.tumi.contabilidad.operaciones.service.HojaManualService;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.facade.TumiFacade;
import pe.com.tumi.framework.negocio.factory.TumiFactory;

@Stateless
public class HojaManualFacade extends TumiFacade implements HojaManualFacadeRemote, HojaManualFacadeLocal {
	HojaManualBO boHojaManual = (HojaManualBO)TumiFactory.get(HojaManualBO.class);
	HojaManualService hojaManualService = (HojaManualService)TumiFactory.get(HojaManualService.class);
	
	public HojaManual grabarHojaManual(HojaManual o)throws BusinessException{
		HojaManual dto = null;
		try{
			dto = hojaManualService.grabarHojaManual(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public HojaManual modificarHojaManual(HojaManual o)throws BusinessException{
		HojaManual dto = null;
		try{
			dto = hojaManualService.modificarHojaManual(o);
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
   	public HojaManual getHojaManualPorPk(HojaManualId pId) throws BusinessException{
    	HojaManual dto = null;
   		try{
   			dto = boHojaManual.getHojaManualPorPk(pId);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return dto;
   	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
   	public List<HojaManual> getListHojaManualBusqueda(HojaManualDetalle o) throws BusinessException{
    	List<HojaManual> lista = null;
   		try{
   			lista = hojaManualService.getListHojaManualBusqueda(o);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return lista;
   	}
}

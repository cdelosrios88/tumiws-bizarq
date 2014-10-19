package pe.com.tumi.tesoreria.cierreLogisticaOper.facade;

import java.util.List;

import javax.ejb.Stateless;

import pe.com.tumi.contabilidad.perdidasSiniestro.domain.PerdidasSiniestro;
import pe.com.tumi.contabilidad.reclamosDevoluciones.domain.ReclamosDevoluciones;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.facade.TumiFacade;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.tesoreria.cierreLogisticaOper.bo.CierreLogisticaOperBo;
import pe.com.tumi.tesoreria.cierreLogisticaOper.domain.CierreLogisticaOper;
import pe.com.tumi.tesoreria.cierreLogisticaOper.domain.composite.CierreLogistica;
import pe.com.tumi.tesoreria.cierreLogisticaOper.service.CierreLogisticaOperService;

/**
 * Session Bean implementation class CierreLogisticaOperFacade
 */
@Stateless
public class CierreLogisticaOperFacade extends TumiFacade implements CierreLogisticaOperFacadeRemote, CierreLogisticaOperFacadeLocal {
	CierreLogisticaOperBo boCierreLogisticaOperBO = (CierreLogisticaOperBo)TumiFactory.get(CierreLogisticaOperBo.class);
	CierreLogisticaOperService CierreLogisticaOperService = (CierreLogisticaOperService)TumiFactory.get(CierreLogisticaOperService.class);
	
	@Override
	public CierreLogisticaOper grabarCierreLogistica(CierreLogisticaOper o)throws BusinessException{
		CierreLogisticaOper dto = null;
	try{
		dto = CierreLogisticaOperService.grabarCierreLogistica(o);
	}catch(BusinessException e){
		context.setRollbackOnly();
		throw e;
	}catch(Exception e){
		context.setRollbackOnly();
		throw new BusinessException(e);
	}
	return dto;
	}
	
	//cierre Logistica
	@Override
	public CierreLogistica grabarCierreLogis(CierreLogistica o)throws BusinessException{
//		CierreLogisticaOper dto = null;
	try{
		CierreLogisticaOperService.grabarCierreLogis(o);
	}catch(BusinessException e){
		context.setRollbackOnly();
		throw e;
	}catch(Exception e){
		context.setRollbackOnly();
		throw new BusinessException(e);
	}
//	return dto;
	return o;
	}
	
	//modificar cierre logistica
	
	@Override
	public CierreLogistica modificarCierreLogis(CierreLogistica o)throws BusinessException{
//		CierreLogistica dto = null;
	try{
		CierreLogisticaOperService.modificarCierreLogis(o);
	}catch(BusinessException e){
		context.setRollbackOnly();
		throw e;
	}catch(Exception e){
		context.setRollbackOnly();
		throw new BusinessException(e);
	}
	return o;
	}
	
//	@Override
//	public CierreLogisticaOper modificarCierreLogistica(CierreLogisticaOper o)throws BusinessException{
//		CierreLogisticaOper dto = null;
//	try{
//		dto = boCierreLogisticaOperBO.modificarCierreLogistica(o);
//	}catch(BusinessException e){
//		context.setRollbackOnly();
//		throw e;
//	}catch(Exception e){
//		context.setRollbackOnly();
//		throw new BusinessException(e);
//	}
//	return dto;
//	}

	@Override
	public List<CierreLogisticaOper> getListaCierreLogisticaVista(CierreLogisticaOper o) throws BusinessException {
		List<CierreLogisticaOper> lista = null;
		try{
			lista = boCierreLogisticaOperBO.getListaCierreLogisticaVista(o);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	@Override  
	public List<CierreLogisticaOper> getListaBuscarCierre(CierreLogisticaOper o) throws BusinessException {
		List<CierreLogisticaOper> lista = null;
		try{
			lista = boCierreLogisticaOperBO.getListaBuscarCierre(o);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
}

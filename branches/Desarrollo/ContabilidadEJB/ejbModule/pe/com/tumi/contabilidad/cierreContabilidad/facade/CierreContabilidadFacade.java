package pe.com.tumi.contabilidad.cierreContabilidad.facade;

import java.util.List;

import javax.ejb.Stateless;

import pe.com.tumi.contabilidad.cierreContabilidad.bo.CierreContabilidadBo;
import pe.com.tumi.contabilidad.cierreContabilidad.domain.CierreContabilidad;
import pe.com.tumi.contabilidad.cierreContabilidad.service.CierreContabilidadService;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.facade.TumiFacade;
import pe.com.tumi.framework.negocio.factory.TumiFactory;

@Stateless
public class CierreContabilidadFacade extends TumiFacade implements CierreContabilidadFacadeRemote, CierreContabilidadFacadeLocal {
	CierreContabilidadBo boCierreContabilidadBO = (CierreContabilidadBo)TumiFactory.get(CierreContabilidadBo.class);
	CierreContabilidadService cierreContabilidadService = (CierreContabilidadService)TumiFactory.get(CierreContabilidadService.class);  

	@Override
	public CierreContabilidad grabarCierreContabilidad(CierreContabilidad o)throws BusinessException{
		CierreContabilidad dto = null;
	try{
		dto = cierreContabilidadService.grabarCierreContabilidad(o);
	}catch(BusinessException e){
		context.setRollbackOnly();
		throw e;
	}catch(Exception e){
		context.setRollbackOnly();
		throw new BusinessException(e);
	}
	return dto;
	}
	@Override
	public CierreContabilidad modificarCierreContabilidad(CierreContabilidad o)throws BusinessException{
		CierreContabilidad dto = null;
	try{
		dto = boCierreContabilidadBO.modificarCierreContabilidad(o);
	}catch(BusinessException e){
		context.setRollbackOnly();
		throw e;
	}catch(Exception e){
		context.setRollbackOnly();
		throw new BusinessException(e);
	}
	return dto;
	}
	@Override  
	public List<CierreContabilidad> getListaBuscarCierre(CierreContabilidad o) throws BusinessException {
		List<CierreContabilidad> lista = null;
		try{
			lista = boCierreContabilidadBO.getListaBuscarCierre(o);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	@Override  
	public List<CierreContabilidad> getListaCierre(CierreContabilidad o) throws BusinessException {
		List<CierreContabilidad> lista = null;
		try{
			lista = boCierreContabilidadBO.getListaCierre(o);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
}

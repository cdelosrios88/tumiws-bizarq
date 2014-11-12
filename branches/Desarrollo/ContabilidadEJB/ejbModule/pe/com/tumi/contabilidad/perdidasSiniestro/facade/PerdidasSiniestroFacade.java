package pe.com.tumi.contabilidad.perdidasSiniestro.facade;

import java.util.List;

import javax.ejb.Stateless;

import pe.com.tumi.contabilidad.impuesto.domain.Impuesto;
import pe.com.tumi.contabilidad.perdidasSiniestro.bo.PerdidasSiniestroBo;
import pe.com.tumi.contabilidad.perdidasSiniestro.domain.PerdidasSiniestro;
import pe.com.tumi.contabilidad.perdidasSiniestro.service.PerdidasSiniestroService;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.facade.TumiFacade;
import pe.com.tumi.framework.negocio.factory.TumiFactory;

@Stateless
public class PerdidasSiniestroFacade extends TumiFacade implements PerdidasSiniestroFacadeRemote, PerdidasSiniestroFacadeLocal {
	PerdidasSiniestroBo boPerdidasSiniestroBO = (PerdidasSiniestroBo)TumiFactory.get(PerdidasSiniestroBo.class);
	PerdidasSiniestroService perdidasSiniestroService = (PerdidasSiniestroService)TumiFactory.get(PerdidasSiniestroService.class);

	@Override
	public PerdidasSiniestro grabarPerdidasSiniestro(PerdidasSiniestro o)throws BusinessException{
		PerdidasSiniestro dto = null;
	try{
		dto = perdidasSiniestroService.grabarPerdidasSiniestro(o);
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
	public PerdidasSiniestro modificarPerdidasSiniestro(PerdidasSiniestro o)throws BusinessException{
		PerdidasSiniestro dto = null;
	try{
		dto = boPerdidasSiniestroBO.modificarPerdidasSiniestro(o);
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
	public List<PerdidasSiniestro> getListaJuridicaRuc(PerdidasSiniestro o) throws BusinessException {
		List<PerdidasSiniestro> lista = null;
		try{
			lista = boPerdidasSiniestroBO.getListaJuridicaRuc(o);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	@Override  
	public List<PerdidasSiniestro> getListaBuscar(PerdidasSiniestro o) throws BusinessException {
		List<PerdidasSiniestro> lista = null;
		try{
			lista = boPerdidasSiniestroBO.getListaBuscar(o);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
}

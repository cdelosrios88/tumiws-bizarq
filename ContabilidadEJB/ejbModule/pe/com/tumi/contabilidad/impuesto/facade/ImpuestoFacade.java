package pe.com.tumi.contabilidad.impuesto.facade;

import java.util.List;

import javax.ejb.Stateless;


import pe.com.tumi.contabilidad.impuesto.bo.ImpuestoBO;
import pe.com.tumi.contabilidad.impuesto.domain.Impuesto;
import pe.com.tumi.contabilidad.impuesto.domain.ImpuestoId;
import pe.com.tumi.contabilidad.impuesto.service.ImpuestoService;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.facade.TumiFacade;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
/**
 * Session Bean implementation class ImpuestoFacade
 */
@Stateless
public class ImpuestoFacade extends TumiFacade implements ImpuestoFacadeRemote, ImpuestoFacadeLocal {
	ImpuestoBO boImpuestoBO = (ImpuestoBO)TumiFactory.get(ImpuestoBO.class);
	ImpuestoService impuestoService = (ImpuestoService)TumiFactory.get(ImpuestoService.class);

	public Impuesto grabarImpuesto(Impuesto o)throws BusinessException{
		Impuesto dto = null;
		try{
			dto = impuestoService.grabarImpuesto(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public Impuesto modificarImpuesto(Impuesto o)throws BusinessException{
		Impuesto dto = null;
		try{
			dto = boImpuestoBO.modificarImpuesto(o);
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
	public List<Impuesto> getListaPersonaJuridica(Impuesto o) throws BusinessException {
		List<Impuesto> lista = null;
		try{
			lista = impuestoService.getListaPersonaJuridica(o);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
		
	}
	@Override
	public List<Impuesto> getListaNombreDniRol(Impuesto o) throws BusinessException {
		List<Impuesto> lista = null;
		try{
			lista = impuestoService.getListaNombreDniRol(o);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	@Override
	public List<Impuesto> getBuscar(Impuesto o) throws BusinessException {
		List<Impuesto> lista = null;
		try{
			lista = boImpuestoBO.getBuscar(o);//impuestoService.getBuscar(o);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	@Override
	public List<Impuesto> getListaImpuesto(Impuesto o) throws BusinessException {
		List<Impuesto> lista = null;
		try{
			lista = boImpuestoBO.getListaImpuesto(o);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	/**
	 * Autor: jchavez / Tarea: Creación / Fecha: 18.08.2014 / 
   	 * Funcionalidad: Método que recupera Impuesto por Pk
   	 * @author jchavez
   	 * @version 1.0
   	 * @param ImpuestoId
   	 * @return dto - impuesto.
   	 * @throws BusinessException
	 */
	public Impuesto getListaPorPk(ImpuestoId o) throws BusinessException {
		Impuesto dto = null;
		try{
			dto = boImpuestoBO.getListaPorPk(o);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return dto;
	}
}

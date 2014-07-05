package pe.com.tumi.parametro.tabla.facade;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.facade.TumiFacade;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.parametro.tabla.bo.TablaBO;
import pe.com.tumi.parametro.tabla.domain.Tabla;

@Stateless
public class TablaFacade extends TumiFacade implements TablaFacadeRemote, TablaFacadeLocal {

	private TablaBO boTabla = (TablaBO)TumiFactory.get(TablaBO.class); 
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Tabla> getListaTablaMaestro() throws BusinessException{
		List<Tabla> lista = null;
		try{
			lista = boTabla.getListaTablaMaestro();
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Tabla> getListaTablaPorIdMaestro(Integer pIntIdMaestro) throws BusinessException{
		List<Tabla> lista = null;
		try{
			lista = boTabla.getListaTablaPorIdMaestro(pIntIdMaestro);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public Tabla getTablaPorIdMaestroYIdDetalle(Integer pIntIdMaestro, Integer pIntIdDetalle) throws BusinessException{
		Tabla domain = null;
		try{
			domain = boTabla.getTablaPorIdMaestroYIdDetalle(pIntIdMaestro, pIntIdDetalle);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return domain;
	}
	
	public List<Tabla> getListaTablaPorAgrupamientoB(Integer pIntIdMaestro, Integer pIntIdAgrupamiento) throws BusinessException{
		List<Tabla> lista = null;
		try{
			lista = boTabla.getListaTablaPorAgrupamientoB(pIntIdMaestro, pIntIdAgrupamiento);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<Tabla> getListaTablaPorAgrupamientoA(Integer pIntIdMaestro, String pStrAgrupamiento) throws BusinessException{
		List<Tabla> lista = null;
		try{
			lista = boTabla.getListaTablaPorAgrupamientoA(pIntIdMaestro, pStrAgrupamiento);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<Tabla> getListaTablaPorIdMaestroYNotInIdDetalle(Integer pIntIdMaestro, String pCsv) throws BusinessException{
		List<Tabla> lista = null;
		try{
			lista = boTabla.getListaTablaPorIdMaestroYNotInIdDetalle(pIntIdMaestro, pCsv);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
}

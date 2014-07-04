package pe.com.tumi.parametro.tabla.bo;

import java.util.HashMap;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.parametro.tabla.dao.TablaDao;
import pe.com.tumi.parametro.tabla.dao.impl.TablaDaoIbatis;
import pe.com.tumi.parametro.tabla.domain.Tabla;

public class TablaBO {
	
	private TablaDao dao = (TablaDao)TumiFactory.get(TablaDaoIbatis.class);
	
	public List<Tabla> getListaTablaMaestro() throws BusinessException{
		List<Tabla> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("pIdParametro", new Integer(0));
			lista = dao.getListaTablaPorIdMaestro(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<Tabla> getListaTablaPorIdMaestro(Integer pIntIdMaestro) throws BusinessException{
		List<Tabla> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("pIdParametro", pIntIdMaestro);
			lista = dao.getListaTablaPorIdMaestro(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public Tabla getTablaPorIdMaestroYIdDetalle(Integer pIntIdMaestro, Integer pIntIdDetalle) throws BusinessException{
		List<Tabla> lista = null;
		Tabla domain = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("pIdParametro", pIntIdMaestro);
			mapa.put("pIdDetalle",   pIntIdDetalle);
			lista = dao.getListaTablaPorIdMaestroYIdDetalle(mapa);
			if(lista!=null){
				if(lista.size()==1){
				   domain = lista.get(0);
				}else if(lista.size()==0){
				   domain = null;
				}else{
				   throw new BusinessException("Obtención de mas de un registro coincidente");
				}
			}
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return domain;
	}
	
	public List<Tabla> getListaTablaPorAgrupamientoB(Integer pIntIdMaestro, Integer pIntIdAgrupamiento) throws BusinessException{
		List<Tabla> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("pIdParametro", 	pIntIdMaestro);
			mapa.put("pIdAgrupamientoB",pIntIdAgrupamiento);
			lista = dao.getListaTablaPorAgrupamientoB(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<Tabla> getListaTablaPorAgrupamientoA(Integer pIntIdMaestro, String pStrIdAgrupamiento) throws BusinessException{
		List<Tabla> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("pIdParametro", 	pIntIdMaestro);
			mapa.put("pIdAgrupamientoA",pStrIdAgrupamiento);
			lista = dao.getListaTablaPorAgrupamientoA(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<Tabla> getListaTablaPorIdMaestroYNotInIdDetalle(Integer pIntIdMaestro, String pCsv) throws BusinessException{
		List<Tabla> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intIdMaestro", 	pIntIdMaestro);
			mapa.put("csvIdDetalle",pCsv);
			lista = dao.getListaTablaPorIdMaestroYNotInIdDetalle(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
}

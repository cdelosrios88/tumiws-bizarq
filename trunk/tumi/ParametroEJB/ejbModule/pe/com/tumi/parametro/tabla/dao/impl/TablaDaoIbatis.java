package pe.com.tumi.parametro.tabla.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.parametro.tabla.dao.TablaDao;
import pe.com.tumi.parametro.tabla.domain.Tabla;

public class TablaDaoIbatis extends TumiDaoIbatis implements TablaDao{
	
	public List<Tabla> getListaTablaPorIdMaestro(Object pO) throws DAOException{
		List<Tabla> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaTabla", pO);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Tabla> getListaTablaPorIdMaestroYIdDetalle(Object pO) throws DAOException{
		List<Tabla> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaTablaPorIdDetalle", pO);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Tabla> getListaTablaPorAgrupamientoA(Object pO) throws DAOException{
		List<Tabla> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaTablaPorAgrupamientoA", pO);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Tabla> getListaTablaPorAgrupamientoB(Object pO) throws DAOException{
		List<Tabla> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaTablaPorAgrupamiento", pO);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Tabla> getListaTablaPorIdMaestroYNotInIdDetalle(Object pO) throws DAOException{
		List<Tabla> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorMaestroYNotInDet", pO);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}
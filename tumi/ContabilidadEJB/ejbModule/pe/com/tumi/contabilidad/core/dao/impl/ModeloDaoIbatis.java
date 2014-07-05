package pe.com.tumi.contabilidad.core.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

import pe.com.tumi.contabilidad.core.dao.ModeloDao;
import pe.com.tumi.contabilidad.core.domain.Modelo;

public class ModeloDaoIbatis extends TumiDaoIbatis implements ModeloDao{

	public Modelo grabar(Modelo o) throws DAOException{
		Modelo dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}

	public Modelo modificar(Modelo o) throws DAOException{
		Modelo dto = null;
		try {
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e){
			throw new DAOException(e);
		}
		return dto;
	}  

	public List<Modelo> getListaPorPk(Object o) throws DAOException{
		List<Modelo> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Modelo> getBusqueda(Object o) throws DAOException{
		List<Modelo> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getBusqueda", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}	

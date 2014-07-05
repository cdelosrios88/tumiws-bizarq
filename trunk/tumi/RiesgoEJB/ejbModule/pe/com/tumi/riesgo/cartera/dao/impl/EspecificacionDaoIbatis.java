package pe.com.tumi.riesgo.cartera.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.riesgo.cartera.dao.EspecificacionDao;
import pe.com.tumi.riesgo.cartera.domain.Especificacion;

public class EspecificacionDaoIbatis extends TumiDaoIbatis implements EspecificacionDao{
	
	public Especificacion grabar(Especificacion o) throws DAOException {
		Especificacion dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public Especificacion modificar(Especificacion o) throws DAOException {
		Especificacion dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<Especificacion> getListaPorPk(Object o) throws DAOException{
		List<Especificacion> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Especificacion> getListaPorIntItemCartera(Object o) throws DAOException{
		List<Especificacion> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorIntItemCartera", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}
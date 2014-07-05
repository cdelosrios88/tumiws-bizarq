package pe.com.tumi.riesgo.cartera.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.riesgo.cartera.dao.TiempoDao;
import pe.com.tumi.riesgo.cartera.domain.Tiempo;

public class TiempoDaoIbatis extends TumiDaoIbatis implements TiempoDao{
	
	public Tiempo grabar(Tiempo o) throws DAOException {
		Tiempo dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public Tiempo modificar(Tiempo o) throws DAOException {
		Tiempo dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<Tiempo> getListaPorPk(Object o) throws DAOException{
		List<Tiempo> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Tiempo> getListaPorIntItemCartera(Object o) throws DAOException{
		List<Tiempo> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorIntItemCartera", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}
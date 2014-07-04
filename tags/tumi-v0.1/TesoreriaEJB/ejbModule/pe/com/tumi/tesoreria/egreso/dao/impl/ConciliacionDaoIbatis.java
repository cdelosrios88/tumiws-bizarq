package pe.com.tumi.tesoreria.egreso.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

import pe.com.tumi.tesoreria.egreso.dao.ConciliacionDao;
import pe.com.tumi.tesoreria.egreso.domain.Conciliacion;

public class ConciliacionDaoIbatis extends TumiDaoIbatis implements ConciliacionDao{

	public Conciliacion grabar(Conciliacion o) throws DAOException{
		Conciliacion dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}

	public Conciliacion modificar(Conciliacion o) throws DAOException{
		Conciliacion dto = null;
		try {
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e){
			throw new DAOException(e);
		}
		return dto;
	}  

	public List<Conciliacion> getListaPorPk(Object o) throws DAOException{
		List<Conciliacion> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}
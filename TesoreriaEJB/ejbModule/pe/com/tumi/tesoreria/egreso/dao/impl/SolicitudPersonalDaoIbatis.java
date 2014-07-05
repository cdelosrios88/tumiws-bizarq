package pe.com.tumi.tesoreria.egreso.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

import pe.com.tumi.tesoreria.egreso.dao.SolicitudPersonalDao;
import pe.com.tumi.tesoreria.egreso.domain.SolicitudPersonal;

public class SolicitudPersonalDaoIbatis extends TumiDaoIbatis implements SolicitudPersonalDao{

	public SolicitudPersonal grabar(SolicitudPersonal o) throws DAOException{
		SolicitudPersonal dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}

	public SolicitudPersonal modificar(SolicitudPersonal o) throws DAOException{
		SolicitudPersonal dto = null;
		try {
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e){
			throw new DAOException(e);
		}
		return dto;
	}

	public List<SolicitudPersonal> getListaPorPk(Object o) throws DAOException{
		List<SolicitudPersonal> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<SolicitudPersonal> getListaPorBuscar(Object o) throws DAOException{
		List<SolicitudPersonal> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorBuscar", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}
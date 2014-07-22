package pe.com.tumi.seguridad.login.dao.impl;

import java.util.HashMap;

import org.apache.log4j.Logger;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.seguridad.login.dao.SessionDao;
import pe.com.tumi.seguridad.login.domain.Session;

public class SessionDaoIbatis extends TumiDaoIbatis implements SessionDao {

	protected  static Logger log = Logger.getLogger(SessionDaoIbatis.class);
	
	public Session grabar(Session o) throws DAOException {
		Session dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public Session modificar(Session o) throws DAOException {
		Session dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public Integer getCntActiveSessionsByUser(Object o) throws DAOException{
		Integer escalar = null;
		try{
			HashMap<String, Object> m = null;
			getSqlMapClientTemplate().queryForObject(getNameSpace() + ".getActiveSessionByUser",o);
			m = (HashMap<String, Object>)o;
			escalar = (Integer)m.get("intEscalar");
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return escalar;
	}
}
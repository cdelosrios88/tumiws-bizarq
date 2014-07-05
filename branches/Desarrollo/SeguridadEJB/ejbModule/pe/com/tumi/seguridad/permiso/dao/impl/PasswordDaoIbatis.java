package pe.com.tumi.seguridad.permiso.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.seguridad.permiso.dao.PasswordDao;
import pe.com.tumi.seguridad.permiso.domain.Password;

public class PasswordDaoIbatis extends TumiDaoIbatis implements PasswordDao {

	protected  static Logger log = Logger.getLogger(PasswordDaoIbatis.class);
	
	public List<Password> getListaPorPk(Object o) throws DAOException {
		List<Password> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Password> getListaPorPkYPass(Object o) throws DAOException {
		List<Password> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPkYPass", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
}

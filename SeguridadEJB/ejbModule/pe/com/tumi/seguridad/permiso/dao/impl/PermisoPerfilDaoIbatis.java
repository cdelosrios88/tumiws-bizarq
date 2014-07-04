package pe.com.tumi.seguridad.permiso.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.seguridad.permiso.dao.PermisoPerfilDao;
import pe.com.tumi.seguridad.permiso.domain.PermisoPerfil;

public class PermisoPerfilDaoIbatis extends TumiDaoIbatis implements PermisoPerfilDao {

	protected  static Logger log = Logger.getLogger(PermisoPerfilDaoIbatis.class);
	
	public PermisoPerfil grabar(PermisoPerfil o) throws DAOException {
		PermisoPerfil dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public PermisoPerfil modificar(PermisoPerfil o) throws DAOException {
		PermisoPerfil dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}

	public List<PermisoPerfil> getListaPorPk(Object o) throws DAOException {
		List<PermisoPerfil> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<PermisoPerfil> getListaPorPkPerfil(Object o) throws DAOException {
		List<PermisoPerfil> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPkPerfil", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}

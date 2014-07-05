package pe.com.tumi.seguridad.permiso.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.seguridad.permiso.dao.PermisoUsuarioDao;
import pe.com.tumi.seguridad.permiso.domain.PermisoUsuario;

public class PermisoUsuarioDaoIbatis extends TumiDaoIbatis implements PermisoUsuarioDao {

	protected  static Logger log = Logger.getLogger(PermisoUsuarioDaoIbatis.class);
	
	public PermisoUsuario grabar(PermisoUsuario o) throws DAOException {
		PermisoUsuario dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public PermisoUsuario modificar(PermisoUsuario o) throws DAOException {
		PermisoUsuario dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}

	public List<PermisoUsuario> getListaPorPk(Object o) throws DAOException {
		List<PermisoUsuario> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}

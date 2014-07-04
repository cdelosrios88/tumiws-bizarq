package pe.com.tumi.seguridad.login.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.seguridad.login.dao.UsuarioDao;
import pe.com.tumi.seguridad.login.domain.Usuario;

public class UsuarioDaoIbatis extends TumiDaoIbatis implements UsuarioDao {

	protected  static Logger log = Logger.getLogger(UsuarioDaoIbatis.class);
	
	public Usuario grabar(Usuario o) throws DAOException {
		Usuario dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public Usuario modificar(Usuario o) throws DAOException {
		Usuario dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}

	public List<Usuario> getListaPorPk(Object o) throws DAOException {
		List<Usuario> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Usuario> getListaPorCodigo(Object o) throws DAOException {
		List<Usuario> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorCodigo", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Usuario> getListaPorCodigoYClave(Object o) throws DAOException {
		List<Usuario> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorCodigoYClave", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}

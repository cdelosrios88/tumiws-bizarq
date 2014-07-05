package pe.com.tumi.seguridad.login.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.seguridad.login.dao.UsuarioPerfilDao;
import pe.com.tumi.seguridad.login.domain.UsuarioPerfil;

public class UsuarioPerfilDaoIbatis extends TumiDaoIbatis implements UsuarioPerfilDao {

	protected  static Logger log = Logger.getLogger(UsuarioPerfilDaoIbatis.class);
	
	public UsuarioPerfil grabar(UsuarioPerfil o) throws DAOException {
		UsuarioPerfil dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public UsuarioPerfil modificar(UsuarioPerfil o) throws DAOException {
		UsuarioPerfil dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}

	public List<UsuarioPerfil> getListaPorPk(Object o) throws DAOException {
		List<UsuarioPerfil> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<UsuarioPerfil> getListaPorPkEmpresaUsuario(Object o) throws DAOException {
		List<UsuarioPerfil> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPkEmpresaUsuario", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<UsuarioPerfil> getListaPorPkEmpresaUsuarioYFechaEliminacion(Object o) throws DAOException {
		List<UsuarioPerfil> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPkEmpUsrYFechaElimi", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}

package pe.com.tumi.seguridad.login.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.seguridad.login.dao.PerfilDao;
import pe.com.tumi.seguridad.login.domain.Perfil;

public class PerfilDaoIbatis extends TumiDaoIbatis implements PerfilDao {

	protected  static Logger log = Logger.getLogger(PerfilDaoIbatis.class);
	
	public Perfil grabar(Perfil o) throws DAOException {
		Perfil dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public Perfil modificar(Perfil o) throws DAOException {
		Perfil dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}

	public List<Perfil> getListaPorPk(Object o) throws DAOException {
		List<Perfil> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Perfil> getListaPorPkEmpresaUsuario(Object o) throws DAOException {
		List<Perfil> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPkEmpresaUsuario", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Perfil> getListaPorPkEmpresaUsuarioYEstado(Object o) throws DAOException {
		List<Perfil> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPkEmpresaUsuarioYSt", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Perfil> getListaPorIdEmpresa(Object o) throws DAOException {
		List<Perfil> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorIdEmpresa", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Perfil> getListaBusqueda(Object o) throws DAOException {
		List<Perfil> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaBusqueda", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}

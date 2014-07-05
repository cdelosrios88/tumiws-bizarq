package pe.com.tumi.seguridad.login.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.empresa.domain.Subsucursal;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.seguridad.login.dao.UsuarioSubSucursalDao;
import pe.com.tumi.seguridad.login.domain.UsuarioSubSucursal;

public class UsuarioSubSucursalDaoIbatis extends TumiDaoIbatis implements UsuarioSubSucursalDao {

	protected  static Logger log = Logger.getLogger(UsuarioSubSucursalDaoIbatis.class);
	
	public UsuarioSubSucursal grabar(UsuarioSubSucursal o) throws DAOException {
		UsuarioSubSucursal dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public UsuarioSubSucursal modificar(UsuarioSubSucursal o) throws DAOException {
		UsuarioSubSucursal dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}

	public List<UsuarioSubSucursal> getListaPorPk(Object o) throws DAOException {
		List<UsuarioSubSucursal> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<UsuarioSubSucursal> getListaPorPkEmpresaUsuario(Object o) throws DAOException {
		List<UsuarioSubSucursal> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPkEmpresaUsuario", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<UsuarioSubSucursal> getListaPorPkEmpresaUsuarioYFechaEliminacion(Object o) throws DAOException {
		List<UsuarioSubSucursal> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPkEmpUsrYFechaElimi", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<UsuarioSubSucursal> getListaPorPkEmpresaUsrYIdSuc(Object o) throws DAOException {
		List<UsuarioSubSucursal> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPkEmpresaUsrYIdSuc", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<UsuarioSubSucursal> getListaPorSucYSubSucursal(Object o) throws DAOException{
		List<UsuarioSubSucursal> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorSucYSubSucursal", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}

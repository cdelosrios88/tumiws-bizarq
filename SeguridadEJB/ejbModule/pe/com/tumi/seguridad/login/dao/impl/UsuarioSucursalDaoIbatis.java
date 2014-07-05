package pe.com.tumi.seguridad.login.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.seguridad.login.dao.UsuarioSucursalDao;
import pe.com.tumi.seguridad.login.domain.UsuarioSucursal;

public class UsuarioSucursalDaoIbatis extends TumiDaoIbatis implements UsuarioSucursalDao {

	protected  static Logger log = Logger.getLogger(UsuarioSucursalDaoIbatis.class);
	
	public UsuarioSucursal grabar(UsuarioSucursal o) throws DAOException {
		UsuarioSucursal dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public UsuarioSucursal modificar(UsuarioSucursal o) throws DAOException {
		UsuarioSucursal dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}

	public List<UsuarioSucursal> getListaPorPk(Object o) throws DAOException {
		List<UsuarioSucursal> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<UsuarioSucursal> getListaPorPkEmpresaUsuario(Object o) throws DAOException {
		List<UsuarioSucursal> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPkEmpresaUsuario", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<UsuarioSucursal> getListaPorPkEmpresaUsuarioYFechaEliminacion(Object o) throws DAOException {
		List<UsuarioSucursal> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPkEmpUsrYFechaElimi", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}

package pe.com.tumi.seguridad.empresa.dao.impl;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.seguridad.empresa.dao.SucursalDao;

public class SucursalDaoIbatis extends TumiDaoIbatis implements SucursalDao{
	
	public Sucursal grabar(Sucursal o) throws DAOException {
		Sucursal dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public Sucursal modificar(Sucursal o) throws DAOException {
		Sucursal dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<Sucursal> getListaSucursalPorIdPersona(Object o) throws DAOException{
		List<Sucursal> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorIdPersona", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Sucursal> getPorPkYIdTipoSucursal(Object o) throws DAOException{
		List<Sucursal> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getPorPkYIdTipoSucursal", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Sucursal> getListaSucursalPorIdSucursal(Object o) throws DAOException{
		List<Sucursal> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorIdSucursal", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}

	public List<Sucursal> getListaSucursalPorPkEmpresa(Object o) throws DAOException{
		List<Sucursal> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPkEmpresa", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Sucursal> getListaSucursalSinZonalPorPkEmpresa(Object o) throws DAOException{
		List<Sucursal> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaSinZonalPorPkEmpresa", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Sucursal> getListaSucursalPorPkEmpresaUsuario(Object o) throws DAOException{
		List<Sucursal> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPkEmpresaUsuario", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Sucursal> getListaPorPkEmpresaUsuarioYSt(Object o) throws DAOException{
		List<Sucursal> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPkEmpresaUsuarioYSt", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Sucursal> getListaSucursalPorPkZonal(Object o) throws DAOException{
		List<Sucursal> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPkZonal", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public Integer getCantidadSucursalPorPkZonal(Object o) throws DAOException {
		Integer escalar = null;
		try{
			HashMap<String, Object> m = new HashMap<String, Object>();
			getSqlMapClientTemplate().queryForObject(getNameSpace() + ".getCantidadPorPkZonal",o,m);
			escalar = (Integer)m.get("intEscalar");
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return escalar;
	}
	
	public List<Sucursal> getListaSucursalDeBusqueda(Object o) throws DAOException{
		List<Sucursal> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaDeBusqueda", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public Sucursal eliminarSucursal(Sucursal o) throws DAOException {
		Sucursal dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".eliminar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<Sucursal> getListaSucursalPorPk(Object o) throws DAOException{
		List<Sucursal> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPkSucursal", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Sucursal> getListaPorEmpresaYTipoSucursal(Object o) throws DAOException{
		List<Sucursal> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getLstPorEmpresaYTipoSucursal", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Sucursal> getListaPorEmpresaYTodoTipoSucursal(Object o) throws DAOException{
		List<Sucursal> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getLstPorEmpresaYTodoTipoSuc", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}

package pe.com.tumi.seguridad.empresa.dao.impl;

import java.util.List;

import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.seguridad.empresa.dao.SucursalZonalDao;

public class SucursalZonalDaoIbatis extends TumiDaoIbatis implements SucursalZonalDao{
	
	public List<Sucursal> getListaSucursalPorPkEmpresa(Object o) throws DAOException{
		List<Sucursal> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPkEmpresa", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Sucursal> getListaSucursalPorPkEmpresaYTipo(Object o) throws DAOException{
		List<Sucursal> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPkEmpYTipo", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Sucursal> getListaSucursalPorPkEmpresaYTipoDeAne(Object o) throws DAOException{
		List<Sucursal> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPkEmpYTipoDeAne", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Sucursal> getListaSucursalPorPkEmpresaYTipoDeLib(Object o) throws DAOException{
		List<Sucursal> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPkEmpYTipoDeLib", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}

	public List<Sucursal> getListaSucursalPorPkEmpresaYIdZonalYTipo(Object o) throws DAOException{
		List<Sucursal> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorEmpYZonalYTipo", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Sucursal> getListaSucursalPorPkEmpresaYIdZonalYTipoDeAne(Object o) throws DAOException{
		List<Sucursal> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorEmpYZonalYTipoDeAne", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Sucursal> getListaSucursalPorPkEmpresaYIdZonalYTipoDeLib(Object o) throws DAOException{
		List<Sucursal> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorEmpYZonalYTipoDeLib", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}

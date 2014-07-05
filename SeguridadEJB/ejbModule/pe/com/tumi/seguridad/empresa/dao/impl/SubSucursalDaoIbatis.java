package pe.com.tumi.seguridad.empresa.dao.impl;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.empresa.domain.Subsucursal;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.seguridad.empresa.dao.SubSucursalDao;

public class SubSucursalDaoIbatis extends TumiDaoIbatis implements SubSucursalDao{
	
	public List<Subsucursal> getListaSubSucursalPorIdSucursal(Object o) throws DAOException{
		List<Subsucursal> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorIdSucursal", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Subsucursal> getListaPorIdSucursalYSt(Object o) throws DAOException{
		List<Subsucursal> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorIdSucursalYSt", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public Subsucursal grabar(Subsucursal o) throws DAOException {
		Subsucursal dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public Subsucursal modificar(Subsucursal o) throws DAOException {
		Subsucursal dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<Subsucursal> getListaSubSucursalPorPK(Object o) throws DAOException{
		List<Subsucursal> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPK", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Subsucursal> getListaPorIdSubSucursal(Object o) throws DAOException{
		List<Subsucursal> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorIdSubSucursal", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Subsucursal> getListPorPkEmpresUsrYIdSucYSt(Object o) throws DAOException{
		List<Subsucursal> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListPorPkEmpresUsrYIdSucYSt", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public Integer getCantidadSubSucursalPorPkSucursal(Object o) throws DAOException {
		Integer escalar = null;
		try{
			HashMap<String, Object> m = new HashMap<String, Object>();
			getSqlMapClientTemplate().queryForObject(getNameSpace() + ".getCantidadPorPkSucursal",o,m);
			escalar = (Integer)m.get("intEscalar");
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return escalar;
	}
	
	

}

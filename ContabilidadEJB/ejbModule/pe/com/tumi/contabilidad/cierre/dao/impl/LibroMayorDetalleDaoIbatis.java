package pe.com.tumi.contabilidad.cierre.dao.impl;

import java.util.List;

import pe.com.tumi.contabilidad.cierre.dao.LibroMayorDao;
import pe.com.tumi.contabilidad.cierre.dao.LibroMayorDetalleDao;
import pe.com.tumi.contabilidad.cierre.domain.LibroMayor;
import pe.com.tumi.contabilidad.cierre.domain.LibroMayorDetalle;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

public class LibroMayorDetalleDaoIbatis extends TumiDaoIbatis implements LibroMayorDetalleDao{

	public LibroMayorDetalle grabar(LibroMayorDetalle o) throws DAOException {
		LibroMayorDetalle dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public LibroMayorDetalle modificar(LibroMayorDetalle o) throws DAOException{
		LibroMayorDetalle dto = null;
		try {
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e){
			throw new DAOException(e);
		}
		return dto;
	}  

	public List<LibroMayorDetalle> getListaPorPk(Object o) throws DAOException{
		List<LibroMayorDetalle> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<LibroMayorDetalle> getListaPorLibroMayor(Object o) throws DAOException{
		List<LibroMayorDetalle> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorLibroMayor", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public void eliminar(Object o) throws DAOException{
		try{
			getSqlMapClientTemplate().queryForList(getNameSpace() + ".eliminar", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
	}
	
	public List<LibroMayorDetalle> getListaPorLibroMayorYPlanCuenta(Object o) throws DAOException{
		List<LibroMayorDetalle> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorLibroMayorYPlanCuenta", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}

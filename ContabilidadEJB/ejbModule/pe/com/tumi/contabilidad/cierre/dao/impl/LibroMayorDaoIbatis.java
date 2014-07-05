package pe.com.tumi.contabilidad.cierre.dao.impl;

import java.util.List;

import pe.com.tumi.contabilidad.cierre.dao.LibroMayorDao;
import pe.com.tumi.contabilidad.cierre.domain.LibroMayor;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

public class LibroMayorDaoIbatis extends TumiDaoIbatis implements LibroMayorDao{

	public LibroMayor grabar(LibroMayor o) throws DAOException {
		LibroMayor dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public LibroMayor modificar(LibroMayor o) throws DAOException{
		LibroMayor dto = null;
		try {
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e){
			throw new DAOException(e);
		}
		return dto;
	}  

	public List<LibroMayor> getListaPorPk(Object o) throws DAOException{
		List<LibroMayor> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<LibroMayor> getListaPorBuscar(Object o) throws DAOException{
		List<LibroMayor> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorBuscar", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}

package pe.com.tumi.contabilidad.cierre.dao.impl;

import java.util.List;

import pe.com.tumi.contabilidad.cierre.dao.LibroDiarioDao;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiario;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

public class LibroDiarioDaoIbatis extends TumiDaoIbatis implements LibroDiarioDao{

	public LibroDiario grabar(LibroDiario o) throws DAOException {
		LibroDiario dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public LibroDiario modificar(LibroDiario o) throws DAOException{
		LibroDiario dto = null;
		try {
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e){
			throw new DAOException(e);
		}
		return dto;
	}  

	public List<LibroDiario> getListaPorPk(Object o) throws DAOException{
		List<LibroDiario> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<LibroDiario> buscarParaCodigo(Object o) throws DAOException{
		List<LibroDiario> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".buscarParaCodigo", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<LibroDiario> buscarUltimoParaCodigo(Object o) throws DAOException{
		List<LibroDiario> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".buscarUltimoParaCodigo", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<LibroDiario> getListaPorBuscar(Object o) throws DAOException{
		List<LibroDiario> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorBuscar", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}

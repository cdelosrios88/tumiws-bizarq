package pe.com.tumi.tesoreria.logistica.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

import pe.com.tumi.tesoreria.logistica.dao.RequisicionDao;
import pe.com.tumi.tesoreria.logistica.domain.Requisicion;

public class RequisicionDaoIbatis extends TumiDaoIbatis implements RequisicionDao{

	public Requisicion grabar(Requisicion o) throws DAOException{
		Requisicion dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}

	public Requisicion modificar(Requisicion o) throws DAOException{
		Requisicion dto = null;
		try {
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e){
			throw new DAOException(e);
		}
		return dto;
	}  

	public List<Requisicion> getListaPorPk(Object o) throws DAOException{
		List<Requisicion> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Requisicion> getListaPorBuscar(Object o) throws DAOException{
		List<Requisicion> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorBuscar", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}

	public List<Requisicion> getListaParaReferencia(Object o) throws DAOException{
		List<Requisicion> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaReferencia", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Requisicion> getListaParaOrdenCompra(Object o) throws DAOException{
		List<Requisicion> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaParaOrdenCompra", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}	
}
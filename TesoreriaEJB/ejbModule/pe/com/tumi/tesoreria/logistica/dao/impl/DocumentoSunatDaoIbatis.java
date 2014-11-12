package pe.com.tumi.tesoreria.logistica.dao.impl;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

import pe.com.tumi.tesoreria.logistica.dao.DocumentoSunatDao;
import pe.com.tumi.tesoreria.logistica.domain.DocumentoSunat;

public class DocumentoSunatDaoIbatis extends TumiDaoIbatis implements DocumentoSunatDao{

	public DocumentoSunat grabar(DocumentoSunat o) throws DAOException{
		DocumentoSunat dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}

	public DocumentoSunat modificar(DocumentoSunat o) throws DAOException{
		DocumentoSunat dto = null;
		try {
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e){
			throw new DAOException(e);
		}
		return dto;
	}  

	public List<DocumentoSunat> getListaPorPk(Object o) throws DAOException{
		List<DocumentoSunat> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<DocumentoSunat> getListaPorBuscar(Object o) throws DAOException{
		List<DocumentoSunat> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorBuscar", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}	
	
	public List<DocumentoSunat> getListaPorOrdenCompra(Object o) throws DAOException{
		List<DocumentoSunat> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorOrdenCompra", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<DocumentoSunat> getListaEnlazados(Object o) throws DAOException{
		List<DocumentoSunat> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaEnlazados", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}	
	
	//Agregado por cdelosrios, 18/11/2013
	public List<DocumentoSunat> getListaPorOrdenCompraYTipoDocumento(Object o) throws DAOException{
		List<DocumentoSunat> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorOrdenCompraYTipoDoc", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	//Fin agregado por cdelosrios, 18/11/2013
	
	//Autor: jchavez / Tarea: Creacion / Fecha: 24.10.2014
	public Integer getValidarCierreDocumento(Object o) throws DAOException{
		Integer  vExisteCierre = null;
		try{
			HashMap<String, Object> m = null;
			getSqlMapClientTemplate().queryForObject(getNameSpace() + ".getValidarCierreDocumento", o);
			m = (HashMap<String, Object>)o;
			vExisteCierre = (Integer)m.get("vExisteCierre");
		}catch(Exception e) {
			throw new DAOException (e);
		}		
		return vExisteCierre;
	}	
	//Fin jchavez - 24.10.2014
}
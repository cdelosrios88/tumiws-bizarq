package pe.com.tumi.tesoreria.logistica.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

import pe.com.tumi.tesoreria.logistica.dao.DocumentoSunatDetalleDao;
import pe.com.tumi.tesoreria.logistica.domain.DocumentoSunatDetalle;

public class DocumentoSunatDetalleDaoIbatis extends TumiDaoIbatis implements DocumentoSunatDetalleDao{

	public DocumentoSunatDetalle grabar(DocumentoSunatDetalle o) throws DAOException{
		DocumentoSunatDetalle dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}

	public DocumentoSunatDetalle modificar(DocumentoSunatDetalle o) throws DAOException{
		DocumentoSunatDetalle dto = null;
		try {
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e){
			throw new DAOException(e);
		}
		return dto;
	}  

	public List<DocumentoSunatDetalle> getListaPorPk(Object o) throws DAOException{
		List<DocumentoSunatDetalle> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<DocumentoSunatDetalle> getListaPorDocumentoSunat(Object o) throws DAOException{
		List<DocumentoSunatDetalle> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorDocumentoSunat", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<DocumentoSunatDetalle> getListaPorOrdenCompraDetalle(Object o) throws DAOException{
		List<DocumentoSunatDetalle> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorOrdenCompraDetalle", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	//Agregado por cdelosrios, 01/11/2013
	public List<DocumentoSunatDetalle> getListaPorDocumentoSunatYTipoDocSunat(Object o) throws DAOException{
		List<DocumentoSunatDetalle> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorDocSunatYTipoDocSunat", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	//Fin agregado por cdelosrios, 01/11/2013
}
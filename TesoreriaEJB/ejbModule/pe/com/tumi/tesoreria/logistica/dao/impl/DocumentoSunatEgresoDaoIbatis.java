package pe.com.tumi.tesoreria.logistica.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

import pe.com.tumi.tesoreria.logistica.dao.DocumentoSunatEgresoDao;
import pe.com.tumi.tesoreria.logistica.domain.DocumentoSunatEgreso;

public class DocumentoSunatEgresoDaoIbatis extends TumiDaoIbatis implements DocumentoSunatEgresoDao{

	public DocumentoSunatEgreso grabar(DocumentoSunatEgreso o) throws DAOException{
		DocumentoSunatEgreso dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}

	public DocumentoSunatEgreso modificar(DocumentoSunatEgreso o) throws DAOException{
		DocumentoSunatEgreso dto = null;
		try {
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e){
			throw new DAOException(e);
		}
		return dto;
	}  

	public List<DocumentoSunatEgreso> getListaPorPk(Object o) throws DAOException{
		List<DocumentoSunatEgreso> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<DocumentoSunatEgreso> getListaPorDocumentoSunat(Object o) throws DAOException{
		List<DocumentoSunatEgreso> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorDocumentoSunat", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}	
}
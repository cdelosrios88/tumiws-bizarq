package pe.com.tumi.tesoreria.logistica.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.tesoreria.logistica.dao.DocumentoSunatDocDao;
import pe.com.tumi.tesoreria.logistica.domain.DocumentoSunatDoc;

public class DocumentoSunatDocDaoIbatis extends TumiDaoIbatis implements DocumentoSunatDocDao{

	public DocumentoSunatDoc grabar(DocumentoSunatDoc o) throws DAOException{
		DocumentoSunatDoc dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}

	public DocumentoSunatDoc modificar(DocumentoSunatDoc o) throws DAOException{
		DocumentoSunatDoc dto = null;
		try {
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e){
			throw new DAOException(e);
		}
		return dto;
	}  

	public List<DocumentoSunatDoc> getListaPorPk(Object o) throws DAOException{
		List<DocumentoSunatDoc> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<DocumentoSunatDoc> getListaPorDocumentoSunat(Object o) throws DAOException{
		List<DocumentoSunatDoc> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorDocumentoSunat", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<DocumentoSunatDoc> getListaPorDocSunatYTipoDoc(Object o) throws DAOException{
		List<DocumentoSunatDoc> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorDocumentoSunatYTipoDoc", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}
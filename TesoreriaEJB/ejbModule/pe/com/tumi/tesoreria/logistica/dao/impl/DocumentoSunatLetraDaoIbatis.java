package pe.com.tumi.tesoreria.logistica.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.tesoreria.logistica.dao.DocumentoSunatLetraDao;
import pe.com.tumi.tesoreria.logistica.domain.DocumentoSunatLetra;

public class DocumentoSunatLetraDaoIbatis extends TumiDaoIbatis implements DocumentoSunatLetraDao{
	
	public DocumentoSunatLetra grabar(DocumentoSunatLetra o) throws DAOException{
		DocumentoSunatLetra dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}

	public DocumentoSunatLetra modificar(DocumentoSunatLetra o) throws DAOException{
		DocumentoSunatLetra dto = null;
		try {
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e){
			throw new DAOException(e);
		}
		return dto;
	}  

	public List<DocumentoSunatLetra> getListaPorPk(Object o) throws DAOException{
		List<DocumentoSunatLetra> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<DocumentoSunatLetra> getListaPorDocumentoSunat(Object o) throws DAOException{
		List<DocumentoSunatLetra> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorDocumentoSunat", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}

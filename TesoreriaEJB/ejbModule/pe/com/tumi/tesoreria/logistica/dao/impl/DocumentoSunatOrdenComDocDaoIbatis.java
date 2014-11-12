package pe.com.tumi.tesoreria.logistica.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.tesoreria.logistica.dao.DocumentoSunatOrdenComDocDao;
import pe.com.tumi.tesoreria.logistica.domain.DocumentoSunatOrdenComDoc;

public class DocumentoSunatOrdenComDocDaoIbatis extends TumiDaoIbatis implements DocumentoSunatOrdenComDocDao{
	
	public DocumentoSunatOrdenComDoc grabar(DocumentoSunatOrdenComDoc o) throws DAOException{
		DocumentoSunatOrdenComDoc dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}

	public DocumentoSunatOrdenComDoc modificar(DocumentoSunatOrdenComDoc o) throws DAOException{
		DocumentoSunatOrdenComDoc dto = null;
		try {
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e){
			throw new DAOException(e);
		}
		return dto;
	}  

	public List<DocumentoSunatOrdenComDoc> getListaPorPk(Object o) throws DAOException{
		List<DocumentoSunatOrdenComDoc> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	//Autor: jchavez / Tarea: Creación / Fecha: 26.10.2014
	public List<DocumentoSunatOrdenComDoc> getListaPorOrdenCompraDoc(Object o) throws DAOException{
		List<DocumentoSunatOrdenComDoc> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorOrdenCompraDoc", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}

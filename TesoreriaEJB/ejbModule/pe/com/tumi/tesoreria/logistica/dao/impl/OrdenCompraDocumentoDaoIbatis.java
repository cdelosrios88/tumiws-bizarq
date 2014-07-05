package pe.com.tumi.tesoreria.logistica.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

import pe.com.tumi.tesoreria.logistica.dao.OrdenCompraDocumentoDao;
import pe.com.tumi.tesoreria.logistica.domain.OrdenCompraDocumento;

public class OrdenCompraDocumentoDaoIbatis extends TumiDaoIbatis implements OrdenCompraDocumentoDao{

	public OrdenCompraDocumento grabar(OrdenCompraDocumento o) throws DAOException{
		OrdenCompraDocumento dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}

	public OrdenCompraDocumento modificar(OrdenCompraDocumento o) throws DAOException{
		OrdenCompraDocumento dto = null;
		try {
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e){
			throw new DAOException(e);
		}
		return dto;
	}  

	public List<OrdenCompraDocumento> getListaPorPk(Object o) throws DAOException{
		List<OrdenCompraDocumento> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<OrdenCompraDocumento> getListaPorOrdenCompra(Object o) throws DAOException{
		List<OrdenCompraDocumento> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorOrdenCompra", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}	
}
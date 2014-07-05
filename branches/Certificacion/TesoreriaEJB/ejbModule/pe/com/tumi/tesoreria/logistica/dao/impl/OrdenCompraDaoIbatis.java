package pe.com.tumi.tesoreria.logistica.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

import pe.com.tumi.tesoreria.logistica.dao.OrdenCompraDao;
import pe.com.tumi.tesoreria.logistica.domain.OrdenCompra;

public class OrdenCompraDaoIbatis extends TumiDaoIbatis implements OrdenCompraDao{

	public OrdenCompra grabar(OrdenCompra o) throws DAOException{
		OrdenCompra dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}

	public OrdenCompra modificar(OrdenCompra o) throws DAOException{
		OrdenCompra dto = null;
		try {
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e){
			throw new DAOException(e);
		}
		return dto;
	}  

	public List<OrdenCompra> getListaPorPk(Object o) throws DAOException{
		List<OrdenCompra> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<OrdenCompra> getListaPorBuscar(Object o) throws DAOException{
		List<OrdenCompra> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorBuscar", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}		
}
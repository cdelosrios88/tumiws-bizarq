package pe.com.tumi.tesoreria.logistica.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

import pe.com.tumi.tesoreria.logistica.dao.OrdenCompraDetalleDao;
import pe.com.tumi.tesoreria.logistica.domain.OrdenCompraDetalle;

public class OrdenCompraDetalleDaoIbatis extends TumiDaoIbatis implements OrdenCompraDetalleDao{

	public OrdenCompraDetalle grabar(OrdenCompraDetalle o) throws DAOException{
		OrdenCompraDetalle dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}

	public OrdenCompraDetalle modificar(OrdenCompraDetalle o) throws DAOException{
		OrdenCompraDetalle dto = null;
		try {
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e){
			throw new DAOException(e);
		}
		return dto;
	}  

	public List<OrdenCompraDetalle> getListaPorPk(Object o) throws DAOException{
		List<OrdenCompraDetalle> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<OrdenCompraDetalle> getListaPorOrdenCompra(Object o) throws DAOException{
		List<OrdenCompraDetalle> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorOrdenCompra", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}	
	
	//Autor: jchavez / Tarea: Creación / Fecha: 02.10.2014
	public List<OrdenCompraDetalle> getSumPrecioTotalXCta(Object o) throws DAOException{
		List<OrdenCompraDetalle> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getSumPrecioTotalXCta", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}
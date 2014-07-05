package pe.com.tumi.servicio.configuracion.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.servicio.configuracion.dao.ConfServEstructuraDetalleDao;
import pe.com.tumi.servicio.configuracion.domain.ConfServEstructuraDetalle;

public class ConfServEstructuraDetalleDaoIbatis extends TumiDaoIbatis implements ConfServEstructuraDetalleDao{
	
	public ConfServEstructuraDetalle grabar(ConfServEstructuraDetalle o) throws DAOException {
		ConfServEstructuraDetalle dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public ConfServEstructuraDetalle modificar(ConfServEstructuraDetalle o) throws DAOException {
		ConfServEstructuraDetalle dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<ConfServEstructuraDetalle> getListaPorPk(Object o) throws DAOException{
		List<ConfServEstructuraDetalle> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<ConfServEstructuraDetalle> getListaPorCabecera(Object o) throws DAOException{
		List<ConfServEstructuraDetalle> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorCabecera", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}
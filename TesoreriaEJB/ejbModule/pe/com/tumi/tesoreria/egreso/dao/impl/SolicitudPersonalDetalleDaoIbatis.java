package pe.com.tumi.tesoreria.egreso.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

import pe.com.tumi.tesoreria.egreso.dao.SolicitudPersonalDetalleDao;
import pe.com.tumi.tesoreria.egreso.domain.SolicitudPersonalDetalle;

public class SolicitudPersonalDetalleDaoIbatis extends TumiDaoIbatis implements SolicitudPersonalDetalleDao{

	public SolicitudPersonalDetalle grabar(SolicitudPersonalDetalle o) throws DAOException{
		SolicitudPersonalDetalle dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}

	public SolicitudPersonalDetalle modificar(SolicitudPersonalDetalle o) throws DAOException{
		SolicitudPersonalDetalle dto = null;
		try {
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e){
			throw new DAOException(e);
		}
		return dto;
	}  

	public List<SolicitudPersonalDetalle> getListaPorPk(Object o) throws DAOException{
		List<SolicitudPersonalDetalle> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<SolicitudPersonalDetalle> getListaPorSolicitudPersonal(Object o) throws DAOException{
		List<SolicitudPersonalDetalle> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorSolicitudPersonal", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public void eliminar(Object o) throws DAOException{
		try{
			getSqlMapClientTemplate().delete(getNameSpace() + ".eliminar", o);
		}catch(Exception e) {
			throw new DAOException (e);
		};
	}			
}
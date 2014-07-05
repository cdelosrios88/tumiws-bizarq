package pe.com.tumi.tesoreria.egreso.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

import pe.com.tumi.tesoreria.egreso.dao.SolicitudPersonalPagoDao;
import pe.com.tumi.tesoreria.egreso.domain.SolicitudPersonalPago;

public class SolicitudPersonalPagoDaoIbatis extends TumiDaoIbatis implements SolicitudPersonalPagoDao{

	public SolicitudPersonalPago grabar(SolicitudPersonalPago o) throws DAOException{
		SolicitudPersonalPago dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}

	public SolicitudPersonalPago modificar(SolicitudPersonalPago o) throws DAOException{
		SolicitudPersonalPago dto = null;
		try {
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e){
			throw new DAOException(e);
		}
		return dto;
	}  

	public List<SolicitudPersonalPago> getListaPorPk(Object o) throws DAOException{
		List<SolicitudPersonalPago> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<SolicitudPersonalPago> getListaPorSolicitudPersonal(Object o) throws DAOException{
		List<SolicitudPersonalPago> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorSolicitudPersonal", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}	
}
package pe.com.tumi.servicio.configuracion.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.servicio.configuracion.dao.ConfServCaptacionDao;
import pe.com.tumi.servicio.configuracion.domain.ConfServCaptacion;

public class ConfServCaptacionDaoIbatis extends TumiDaoIbatis implements ConfServCaptacionDao{
	
	public ConfServCaptacion grabar(ConfServCaptacion o) throws DAOException {
		ConfServCaptacion dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public ConfServCaptacion modificar(ConfServCaptacion o) throws DAOException {
		ConfServCaptacion dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<ConfServCaptacion> getListaPorPk(Object o) throws DAOException{
		List<ConfServCaptacion> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<ConfServCaptacion> getListaPorCabecera(Object o) throws DAOException{
		List<ConfServCaptacion> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorCabecera", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}
package pe.com.tumi.servicio.solicitudPrestamo.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.servicio.solicitudPrestamo.dao.AutorizaVerificacionDao;
import pe.com.tumi.servicio.solicitudPrestamo.domain.AutorizaVerificacion;

public class AutorizaVerificacionDaoIbatis extends TumiDaoIbatis implements AutorizaVerificacionDao{
	
	public AutorizaVerificacion grabar(AutorizaVerificacion o) throws DAOException {
		AutorizaVerificacion dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public AutorizaVerificacion modificar(AutorizaVerificacion o) throws DAOException {
		AutorizaVerificacion dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<AutorizaVerificacion> getListaPorPk(Object o) throws DAOException{
		List<AutorizaVerificacion> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<AutorizaVerificacion> getListaPorExpedienteCredito(Object o) throws DAOException{
		List<AutorizaVerificacion> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorExpedienteCredito", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}
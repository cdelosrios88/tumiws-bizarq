package pe.com.tumi.servicio.solicitudPrestamo.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.servicio.solicitudPrestamo.dao.AutorizaCreditoDao;
import pe.com.tumi.servicio.solicitudPrestamo.domain.AutorizaCredito;

public class AutorizaCreditoDaoIbatis extends TumiDaoIbatis implements AutorizaCreditoDao{
	
	public AutorizaCredito grabar(AutorizaCredito o) throws DAOException {
		AutorizaCredito dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public AutorizaCredito modificar(AutorizaCredito o) throws DAOException {
		AutorizaCredito dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<AutorizaCredito> getListaPorPk(Object o) throws DAOException{
		List<AutorizaCredito> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<AutorizaCredito> getListaPorExpedienteCredito(Object o) throws DAOException{
		List<AutorizaCredito> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorExpedienteCredito", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}
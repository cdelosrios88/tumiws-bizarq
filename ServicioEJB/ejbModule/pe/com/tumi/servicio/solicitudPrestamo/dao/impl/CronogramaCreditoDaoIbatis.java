package pe.com.tumi.servicio.solicitudPrestamo.dao.impl;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.servicio.solicitudPrestamo.dao.CronogramaCreditoDao;
import pe.com.tumi.servicio.solicitudPrestamo.domain.CronogramaCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.CronogramaCreditoId;

public class CronogramaCreditoDaoIbatis extends TumiDaoIbatis implements CronogramaCreditoDao{
	
	public CronogramaCredito grabar(CronogramaCredito o) throws DAOException {
		CronogramaCredito dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public CronogramaCredito modificar(CronogramaCredito o) throws DAOException {
		CronogramaCredito dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<CronogramaCredito> getListaPorPk(Object o) throws DAOException{
		List<CronogramaCredito> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<CronogramaCredito> getListaPorPkExpedienteCredito(Object o) throws DAOException{
		List<CronogramaCredito> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPkExpedienteCredito", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
		
	public void deletePorPk(Object o)throws DAOException{
	    
		try{
	        getSqlMapClientTemplate().delete(getNameSpace() +".deletePorPk", o);
	    }catch(Exception e){
	    	throw new DAOException (e);
	    }
	}
	
	
	/**
	 * Recupera los cronogramas de credito segun expediente de credito y el nro de cuota
	 */
	public List<CronogramaCredito> getListaPorPkExpedienteCuota(Object o) throws DAOException{
		List<CronogramaCredito> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPkExpedienteCuota", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	
}
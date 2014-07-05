package pe.com.tumi.servicio.solicitudPrestamo.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.servicio.solicitudPrestamo.dao.CapacidadCreditoDao;
import pe.com.tumi.servicio.solicitudPrestamo.domain.CapacidadCredito;

public class CapacidadCreditoDaoIbatis extends TumiDaoIbatis implements CapacidadCreditoDao{
	
	public CapacidadCredito grabar(CapacidadCredito o) throws DAOException {
		CapacidadCredito dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			System.out.println("Error grabar ---> "+e);
			throw new DAOException(e);
		}
		return dto;
	}
	
	public CapacidadCredito modificar(CapacidadCredito o) throws DAOException {
		CapacidadCredito dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<CapacidadCredito> getListaPorPk(Object o) throws DAOException{
		List<CapacidadCredito> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<CapacidadCredito> getListaPorPkExpedienteCredito(Object o) throws DAOException{
		List<CapacidadCredito> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPkExpedienteCredito", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<CapacidadCredito> getListaPorPkExpedienteYSocioEstructura(Object o) throws DAOException{
		List<CapacidadCredito> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorExpedienteYSocioEst", o);
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
}
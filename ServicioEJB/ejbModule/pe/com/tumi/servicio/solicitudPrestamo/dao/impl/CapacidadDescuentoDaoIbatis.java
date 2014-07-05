package pe.com.tumi.servicio.solicitudPrestamo.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.servicio.solicitudPrestamo.dao.CapacidadDescuentoDao;
import pe.com.tumi.servicio.solicitudPrestamo.dao.CapacidadCreditoDao;
import pe.com.tumi.servicio.solicitudPrestamo.domain.CapacidadDescuento;
import pe.com.tumi.servicio.solicitudPrestamo.domain.CapacidadDescuentoId;

public class CapacidadDescuentoDaoIbatis extends TumiDaoIbatis implements CapacidadDescuentoDao{
	
	public CapacidadDescuento grabar(CapacidadDescuento o) throws DAOException {
		CapacidadDescuento dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public CapacidadDescuento modificar(CapacidadDescuento o) throws DAOException {
		CapacidadDescuento dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<CapacidadDescuento> getListaPorPk(Object o) throws DAOException{
		List<CapacidadDescuento> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<CapacidadDescuento> getListaPorCapacidadCreditoPk(Object o) throws DAOException{
		List<CapacidadDescuento> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorCapacidadCreditoPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public void deletePorPk(Object o)throws DAOException{
	    
		try{
	        getSqlMapClientTemplate().delete(getNameSpace() +".deletePorPk", o);
	    }catch(Exception e){
	    	System.out.println("Error endeletePorPk --->  "+e);
	    	throw new DAOException (e);
	    }
	}
}
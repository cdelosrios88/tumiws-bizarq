package pe.com.tumi.tesoreria.ingreso.dao.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

import pe.com.tumi.tesoreria.ingreso.dao.ReciboManualDao;
import pe.com.tumi.tesoreria.ingreso.domain.ReciboManual;

public class ReciboManualDaoIbatis extends TumiDaoIbatis implements ReciboManualDao{

	public ReciboManual grabar(ReciboManual o) throws DAOException{
		ReciboManual dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}

	public ReciboManual modificar(ReciboManual o) throws DAOException{
		ReciboManual dto = null;
		try {
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e){
			throw new DAOException(e);
		}
		return dto;
	}  

	public List<ReciboManual> getListaPorPk(Object o) throws DAOException{
		List<ReciboManual> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}	
	
	public List<ReciboManual> getListaPorBuscar(Object o) throws DAOException{
		List<ReciboManual> lista = null;
		try{
			
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorBuscar", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}	
	
	public Integer validarNroReciboPorSuc(Object o) throws DAOException{
		Integer  vResult = null;
		try{
			HashMap<String, Object> m = null;
			getSqlMapClientTemplate().queryForObject(getNameSpace() + ".validarNroReciboPorSuc", o);
			m = (HashMap<String, Object>)o;
			vResult = (Integer)m.get("vResult");
	    
		}catch(Exception e) {
			throw new DAOException (e);
		}
		
		
		return vResult;
	}	
}
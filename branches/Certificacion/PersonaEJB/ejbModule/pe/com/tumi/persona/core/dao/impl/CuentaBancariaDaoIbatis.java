package pe.com.tumi.persona.core.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.persona.core.dao.CuentaBancariaDao;
import pe.com.tumi.persona.core.domain.CuentaBancaria;

public class CuentaBancariaDaoIbatis extends TumiDaoIbatis implements CuentaBancariaDao{
	
	public CuentaBancaria grabar(CuentaBancaria o) throws DAOException {
		CuentaBancaria dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public CuentaBancaria modificar(CuentaBancaria o) throws DAOException {
		CuentaBancaria dto = null;
		try{
			
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<CuentaBancaria> getListaCuentaBancariaPorPK(Object o) throws DAOException{
		List<CuentaBancaria> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPK", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<CuentaBancaria> getListaCuentaBancariaPorIdPersona(Object o) throws DAOException{
		List<CuentaBancaria> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorIdPersona", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<CuentaBancaria> getListaPorStrNroCuentaBancaria(Object o) throws DAOException{
		List<CuentaBancaria> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorStrNro", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}
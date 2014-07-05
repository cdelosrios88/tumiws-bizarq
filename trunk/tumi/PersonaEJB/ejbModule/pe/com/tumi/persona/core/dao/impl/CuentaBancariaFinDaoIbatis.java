package pe.com.tumi.persona.core.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.persona.core.dao.CuentaBancariaDao;
import pe.com.tumi.persona.core.dao.CuentaBancariaFinDao;
import pe.com.tumi.persona.core.domain.CuentaBancaria;
import pe.com.tumi.persona.core.domain.CuentaBancariaFin;

public class CuentaBancariaFinDaoIbatis extends TumiDaoIbatis implements CuentaBancariaFinDao{
	
	public CuentaBancariaFin grabar(CuentaBancariaFin o) throws DAOException {
		CuentaBancariaFin dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}

	
	public List<CuentaBancariaFin> getListaPorPK(Object o) throws DAOException{
		List<CuentaBancariaFin> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPK", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<CuentaBancariaFin> getListaPorCuentaBancaria(Object o) throws DAOException{
		List<CuentaBancariaFin> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorCuentaBancaria", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public void eliminar(Object o) throws DAOException{
		try{
			getSqlMapClientTemplate().queryForList(getNameSpace() + ".eliminar", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
	}
}
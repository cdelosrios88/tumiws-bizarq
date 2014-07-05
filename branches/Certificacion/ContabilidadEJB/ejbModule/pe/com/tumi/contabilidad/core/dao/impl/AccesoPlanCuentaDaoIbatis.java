package pe.com.tumi.contabilidad.core.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

import pe.com.tumi.contabilidad.core.dao.AccesoPlanCuentaDao;
import pe.com.tumi.contabilidad.core.domain.AccesoPlanCuenta;

public class AccesoPlanCuentaDaoIbatis extends TumiDaoIbatis implements AccesoPlanCuentaDao{

	public AccesoPlanCuenta grabar(AccesoPlanCuenta o) throws DAOException{
		AccesoPlanCuenta dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}

	public AccesoPlanCuenta modificar(AccesoPlanCuenta o) throws DAOException{
		AccesoPlanCuenta dto = null;
		try {
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e){
			throw new DAOException(e);
		}
		return dto;
	}  

	public List<AccesoPlanCuenta> getListaPorPk(Object o) throws DAOException{
		List<AccesoPlanCuenta> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<AccesoPlanCuenta> getListaPorPlanCuenta(Object o) throws DAOException{
		List<AccesoPlanCuenta> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPlanCuenta", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}	
}
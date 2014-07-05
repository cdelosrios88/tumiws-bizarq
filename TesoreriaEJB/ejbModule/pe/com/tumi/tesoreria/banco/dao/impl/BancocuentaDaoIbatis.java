package pe.com.tumi.tesoreria.banco.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

import pe.com.tumi.tesoreria.banco.dao.BancocuentaDao;
import pe.com.tumi.tesoreria.banco.domain.Bancocuenta;

public class BancocuentaDaoIbatis extends TumiDaoIbatis implements BancocuentaDao{

	public Bancocuenta grabar(Bancocuenta o) throws DAOException{
		Bancocuenta dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}

	public Bancocuenta modificar(Bancocuenta o) throws DAOException{
		Bancocuenta dto = null;
		try {
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e){
			throw new DAOException(e);
		}
		return dto;
	}  

	public List<Bancocuenta> getListaPorPk(Object o) throws DAOException{
		List<Bancocuenta> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Bancocuenta> getListaPorBancoFondo(Object o) throws DAOException{
		List<Bancocuenta> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorBancoFondo", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Bancocuenta> getListaPorPlanCuenta(Object o) throws DAOException{
		List<Bancocuenta> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPlanCuenta", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}
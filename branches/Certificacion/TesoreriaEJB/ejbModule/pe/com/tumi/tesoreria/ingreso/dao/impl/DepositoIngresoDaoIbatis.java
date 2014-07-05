package pe.com.tumi.tesoreria.ingreso.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

import pe.com.tumi.tesoreria.ingreso.dao.DepositoIngresoDao;
import pe.com.tumi.tesoreria.ingreso.domain.DepositoIngreso;

public class DepositoIngresoDaoIbatis extends TumiDaoIbatis implements DepositoIngresoDao{

	public DepositoIngreso grabar(DepositoIngreso o) throws DAOException{
		DepositoIngreso dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}

	public DepositoIngreso modificar(DepositoIngreso o) throws DAOException{
		DepositoIngreso dto = null;
		try {
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e){
			throw new DAOException(e);
		}
		return dto;
	}  

	public List<DepositoIngreso> getListaPorPk(Object o) throws DAOException{
		List<DepositoIngreso> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}	
	
	public List<DepositoIngreso> getListaPorIngreso(Object o) throws DAOException{
		List<DepositoIngreso> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorIngreso", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<DepositoIngreso> getListaPorIngresoDeposito(Object o) throws DAOException{
		List<DepositoIngreso> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorIngresoDeposito", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}


}
package pe.com.tumi.tesoreria.egreso.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

import pe.com.tumi.tesoreria.egreso.dao.SaldoDao;
import pe.com.tumi.tesoreria.egreso.domain.Saldo;

public class SaldoDaoIbatis extends TumiDaoIbatis implements SaldoDao{

	public Saldo grabar(Saldo o) throws DAOException{
		Saldo dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}

	public Saldo modificar(Saldo o) throws DAOException{
		Saldo dto = null;
		try {
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e){
			throw new DAOException(e);
		}
		return dto;
	}  

	public List<Saldo> getListaPorPk(Object o) throws DAOException{
		List<Saldo> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Saldo> getListaAnterior(Object o) throws DAOException{
		List<Saldo> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaAnterior", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Saldo> getListaUltimaFechaRegistro(Object o) throws DAOException{
		List<Saldo> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".geUltimaFechaSaldo", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Saldo> getListaUltimaFechaSaldo(Object o) throws DAOException{
		List<Saldo> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getUltimaFechaSaldo", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Saldo> getListaPorBuscar(Object o) throws DAOException{
		List<Saldo> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorBuscar", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Map> getListaFechas(Object o) throws DAOException{
		List<Map> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaSaldoFechas", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	//Inicio: REQ14-005 - bizarq - 19/10/2014
	public Integer processDailyAmount(Object o) throws DAOException{
		Integer escalar = null;
		try{
			HashMap<String, Object> m = null;
			getSqlMapClientTemplate().queryForObject(getNameSpace() + ".processDailyAmount",o);
			m = (HashMap<String, Object>)o;
			escalar = (Integer)m.get("intResult");
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return escalar;
	}
	//Inicio: REQ14-005 - bizarq - 19/10/2014
}
package pe.com.tumi.credito.socio.aperturaCuenta.core.dao.impl;

import java.util.List;

import pe.com.tumi.credito.socio.aperturaCuenta.core.dao.CuentaDao;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.Cuenta;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

public class CuentaDaoIbatis extends TumiDaoIbatis implements CuentaDao{
	
	public Cuenta grabar(Cuenta o) throws DAOException {
		Cuenta dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public Cuenta modificar(Cuenta o) throws DAOException {
		Cuenta dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<Cuenta> getListaCuentaPorPK(Object o) throws DAOException{
		List<Cuenta> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public Cuenta actualizarNroCuentaYSecuencia(Cuenta o) throws DAOException {
		Cuenta dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".updateNroCuentaYSecuencia", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<Cuenta> getListaCuentaPorPKYSituacion(Object o) throws DAOException{
		List<Cuenta> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPkYSituacionCuenta", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	
	/**
	 * Recupera la cuenta sin tener en cuenta la situacion.
	 * Liquidaciond e cuentas
	 * @param o
	 * @return
	 * @throws DAOException
	 */
	public List<Cuenta> getListaCuentaPorPkTodoEstado(Object o) throws DAOException{
		List<Cuenta> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaCuentaPorPkTodoEstado", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
}
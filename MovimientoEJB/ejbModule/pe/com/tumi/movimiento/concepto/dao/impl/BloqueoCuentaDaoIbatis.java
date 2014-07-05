package pe.com.tumi.movimiento.concepto.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.movimiento.concepto.dao.BloqueoCuentaDao;
import pe.com.tumi.movimiento.concepto.domain.BloqueoCuenta;

public class BloqueoCuentaDaoIbatis extends TumiDaoIbatis implements BloqueoCuentaDao{
	
	public BloqueoCuenta grabar(BloqueoCuenta o) throws DAOException {
		BloqueoCuenta dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<BloqueoCuenta> getListaPorNroCuentaYMotivo(Object o) throws DAOException {
		List<BloqueoCuenta> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorNroCuentaYMotivo",o);
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return lista;
	}
	
	public List<BloqueoCuenta> getListaPorNroCuenta(Object o) throws DAOException {
		List<BloqueoCuenta> lista = null;
		
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorNroCuenta",o);
		}catch(Exception e) {
			throw new DAOException(e);
		}
		
		return lista;
	}
	public List<BloqueoCuenta> getListaFondoSepelio(Object o) throws DAOException{
		List<BloqueoCuenta> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaFondoSepelio", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}
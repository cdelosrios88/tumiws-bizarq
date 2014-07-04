package pe.com.tumi.movimiento.concepto.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.movimiento.concepto.dao.CuentaConceptoDao;
import pe.com.tumi.movimiento.concepto.domain.CuentaConcepto;
import pe.com.tumi.movimiento.concepto.domain.CuentaConceptoDetalleId;

public class CuentaConceptoDaoIbatis extends TumiDaoIbatis implements CuentaConceptoDao{
	
	public CuentaConcepto grabar(CuentaConcepto o) throws DAOException {
		CuentaConcepto dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public CuentaConcepto modificar(CuentaConcepto o) throws DAOException {
		CuentaConcepto dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<CuentaConcepto> getListaPorPK(Object o) throws DAOException{
		List<CuentaConcepto> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPK", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<CuentaConcepto> getListaPorPKCuenta(Object o) throws DAOException{
		List<CuentaConcepto> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPKCuenta", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}

	
}
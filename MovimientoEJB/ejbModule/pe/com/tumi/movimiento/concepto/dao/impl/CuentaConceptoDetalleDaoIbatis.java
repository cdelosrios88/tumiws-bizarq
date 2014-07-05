package pe.com.tumi.movimiento.concepto.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.movimiento.concepto.dao.CuentaConceptoDetalleDao;
import pe.com.tumi.movimiento.concepto.domain.CuentaConceptoDetalle;
import pe.com.tumi.movimiento.concepto.domain.CuentaConceptoId;

public class CuentaConceptoDetalleDaoIbatis extends TumiDaoIbatis implements CuentaConceptoDetalleDao{
	
	public CuentaConceptoDetalle grabar(CuentaConceptoDetalle o) throws DAOException {
		CuentaConceptoDetalle dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public CuentaConceptoDetalle modificar(CuentaConceptoDetalle o) throws DAOException {
		CuentaConceptoDetalle dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<CuentaConceptoDetalle> getListaPorPK(Object o) throws DAOException{
		List<CuentaConceptoDetalle> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPK", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<CuentaConceptoDetalle> getListaPorPKConcepto(Object o) throws DAOException{
		List<CuentaConceptoDetalle> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPKConcepto", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<CuentaConceptoDetalle> getListaPorPKYTipoConcepto(Object o) throws DAOException{
		List<CuentaConceptoDetalle> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPKYTipoConcepto", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public void modificarDetallePorConcepto(CuentaConceptoDetalle o) throws DAOException {
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificarDetallePorConcepto", o);
		}catch(Exception e) {
			throw new DAOException(e);
		}
	}	
	
	public List<CuentaConceptoDetalle> getListaPorPKCuentaYTipoConcepto(Object o) throws DAOException{
		List<CuentaConceptoDetalle> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPkCuentaYTipoConcepto", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	/**
	 * Recupera el maximo detalle en base a id de la cuenta concepto
	 * @param o
	 * @return
	 * @throws DAOException
	 */
	public List<CuentaConceptoDetalle> getMaxCuentaConceptoDetPorPKCuentaConcepto(Object o) throws DAOException{
		List<CuentaConceptoDetalle> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getMaxCuentaConceptoDetPorPKCuentaConcepto", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	//cobranza flyalico
	public List<CuentaConceptoDetalle> getCuentaConceptoDetofCobranza(Object o) throws DAOException{
		List<CuentaConceptoDetalle> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getCuentaConceptoDetofCobranza", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}

}
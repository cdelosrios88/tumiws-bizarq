package pe.com.tumi.credito.socio.creditos.dao.impl;

import java.util.List;

import pe.com.tumi.credito.socio.creditos.dao.CreditoExcepcionAporteNoTransDao;
import pe.com.tumi.credito.socio.creditos.domain.CreditoExcepcionAporteNoTrans;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

public class CreditoExcepcionAporteNoTransDaoIbatis extends TumiDaoIbatis implements CreditoExcepcionAporteNoTransDao{
	
	public CreditoExcepcionAporteNoTrans grabar(CreditoExcepcionAporteNoTrans o) throws DAOException {
		CreditoExcepcionAporteNoTrans dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public CreditoExcepcionAporteNoTrans modificar(CreditoExcepcionAporteNoTrans o) throws DAOException {
		CreditoExcepcionAporteNoTrans dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<CreditoExcepcionAporteNoTrans> getListaCreditoExcepcionAporteNoTransPorPK(Object o) throws DAOException{
		List<CreditoExcepcionAporteNoTrans> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<CreditoExcepcionAporteNoTrans> getListaCreditoExcepcionAporteNoTransPorPKCreditoExcepcion(Object o) throws DAOException{
		List<CreditoExcepcionAporteNoTrans> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPkCreditoExcepcion", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}
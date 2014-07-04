package pe.com.tumi.credito.socio.creditos.dao.impl;

import java.util.List;

import pe.com.tumi.credito.socio.creditos.dao.CreditoDescuentoCaptacionDao;
import pe.com.tumi.credito.socio.creditos.domain.CreditoDescuentoCaptacion;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

public class CreditoDescuentoCaptacionDaoIbatis extends TumiDaoIbatis implements CreditoDescuentoCaptacionDao{
	
	public CreditoDescuentoCaptacion grabar(CreditoDescuentoCaptacion o) throws DAOException {
		CreditoDescuentoCaptacion dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public CreditoDescuentoCaptacion modificar(CreditoDescuentoCaptacion o) throws DAOException {
		CreditoDescuentoCaptacion dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<CreditoDescuentoCaptacion> getListaCreditoDescuentoCaptacionPorPK(Object o) throws DAOException{
		List<CreditoDescuentoCaptacion> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<CreditoDescuentoCaptacion> getListaCreditoDescuentoCaptacionPorPKCreditoDescuento(Object o) throws DAOException{
		List<CreditoDescuentoCaptacion> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPkCreditoDescuento", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}

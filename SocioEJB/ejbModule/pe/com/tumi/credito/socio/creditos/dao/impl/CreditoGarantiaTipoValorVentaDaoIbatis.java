package pe.com.tumi.credito.socio.creditos.dao.impl;

import java.util.List;

import pe.com.tumi.credito.socio.creditos.dao.CreditoGarantiaTipoValorVentaDao;
import pe.com.tumi.credito.socio.creditos.domain.CreditoGarantiaTipoValorVenta;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

public class CreditoGarantiaTipoValorVentaDaoIbatis extends TumiDaoIbatis implements CreditoGarantiaTipoValorVentaDao{
	
	public CreditoGarantiaTipoValorVenta grabar(CreditoGarantiaTipoValorVenta o) throws DAOException {
		CreditoGarantiaTipoValorVenta dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public CreditoGarantiaTipoValorVenta modificar(CreditoGarantiaTipoValorVenta o) throws DAOException {
		CreditoGarantiaTipoValorVenta dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<CreditoGarantiaTipoValorVenta> getListaTipoValorVentaPorPK(Object o) throws DAOException{
		List<CreditoGarantiaTipoValorVenta> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<CreditoGarantiaTipoValorVenta> getListaTipoValorVentaPorPKCreditoGarantia(Object o) throws DAOException{
		List<CreditoGarantiaTipoValorVenta> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPkCreditoGarantia", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}
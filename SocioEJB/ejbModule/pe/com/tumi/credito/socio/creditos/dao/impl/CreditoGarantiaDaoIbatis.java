package pe.com.tumi.credito.socio.creditos.dao.impl;

import java.util.List;

import pe.com.tumi.credito.socio.captacion.domain.Captacion;
import pe.com.tumi.credito.socio.creditos.dao.CreditoDao;
import pe.com.tumi.credito.socio.creditos.dao.CreditoGarantiaDao;
import pe.com.tumi.credito.socio.creditos.domain.CreditoDescuento;
import pe.com.tumi.credito.socio.creditos.domain.CreditoGarantia;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

public class CreditoGarantiaDaoIbatis extends TumiDaoIbatis implements CreditoGarantiaDao{
	
	public CreditoGarantia grabar(CreditoGarantia o) throws DAOException {
		CreditoGarantia dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public CreditoGarantia modificar(CreditoGarantia o) throws DAOException {
		CreditoGarantia dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<CreditoGarantia> getListaCreditoPorPK(Object o) throws DAOException{
		List<CreditoGarantia> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<CreditoGarantia> getListaCreditoGarantiaPorPKCreditoGarantia(Object o) throws DAOException{
		List<CreditoGarantia> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPkCreditoGarantia", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public CreditoGarantia eliminarCreditoGarantia(CreditoGarantia o) throws DAOException {
		CreditoGarantia dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".eliminar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
}

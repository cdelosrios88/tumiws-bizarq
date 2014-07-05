package pe.com.tumi.credito.socio.creditos.dao.impl;

import java.util.List;

import pe.com.tumi.credito.socio.captacion.domain.Captacion;
import pe.com.tumi.credito.socio.creditos.dao.CreditoDao;
import pe.com.tumi.credito.socio.creditos.dao.CreditoGarantiaDao;
import pe.com.tumi.credito.socio.creditos.dao.CreditoTipoGarantiaDao;
import pe.com.tumi.credito.socio.creditos.domain.CreditoGarantia;
import pe.com.tumi.credito.socio.creditos.domain.CreditoTipoGarantia;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

public class CreditoTipoGarantiaDaoIbatis extends TumiDaoIbatis implements CreditoTipoGarantiaDao{
	
	public CreditoTipoGarantia grabar(CreditoTipoGarantia o) throws DAOException {
		CreditoTipoGarantia dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public CreditoTipoGarantia modificar(CreditoTipoGarantia o) throws DAOException {
		CreditoTipoGarantia dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<CreditoTipoGarantia> getListaCreditoPorPK(Object o) throws DAOException{
		List<CreditoTipoGarantia> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<CreditoTipoGarantia> getListaCreditoTipoGarantiaPorPKCreditoGarantia(Object o) throws DAOException{
		List<CreditoTipoGarantia> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPkCreditoGarantia", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
}

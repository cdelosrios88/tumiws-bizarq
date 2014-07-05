package pe.com.tumi.credito.socio.creditos.dao.impl;

import java.util.List;

import pe.com.tumi.credito.socio.creditos.dao.CreditoTipoGarantiaCondicionLaboralDao;
import pe.com.tumi.credito.socio.creditos.domain.CondicionLaboralTipoGarantia;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

public class CreditoTipoGarantiaCondicionLaboralDaoIbatis extends TumiDaoIbatis implements CreditoTipoGarantiaCondicionLaboralDao{
	
	public CondicionLaboralTipoGarantia grabar(CondicionLaboralTipoGarantia o) throws DAOException {
		CondicionLaboralTipoGarantia dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public CondicionLaboralTipoGarantia modificar(CondicionLaboralTipoGarantia o) throws DAOException {
		CondicionLaboralTipoGarantia dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<CondicionLaboralTipoGarantia> getListaCondicionLaboralTipoGarantiaPorPK(Object o) throws DAOException{
		List<CondicionLaboralTipoGarantia> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<CondicionLaboralTipoGarantia> getListaCondicionLaboralTipoGarantiaPorCreditoTipoGarantia(Object o) throws DAOException{
		List<CondicionLaboralTipoGarantia> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorTipoGarantia", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<CondicionLaboralTipoGarantia> getListaCondicionLaboralPorCreditoTipoGarantia(Object o) throws DAOException{
		List<CondicionLaboralTipoGarantia> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaCondicionLaboralPorTipoGarantia", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}

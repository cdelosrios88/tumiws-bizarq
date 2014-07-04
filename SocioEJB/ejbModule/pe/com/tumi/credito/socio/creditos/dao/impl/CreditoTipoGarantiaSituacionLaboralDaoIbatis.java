package pe.com.tumi.credito.socio.creditos.dao.impl;

import java.util.List;

import pe.com.tumi.credito.socio.creditos.dao.CreditoTipoGarantiaSituacionLaboralDao;
import pe.com.tumi.credito.socio.creditos.domain.SituacionLaboralTipoGarantia;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

public class CreditoTipoGarantiaSituacionLaboralDaoIbatis extends TumiDaoIbatis implements CreditoTipoGarantiaSituacionLaboralDao{
	
	public SituacionLaboralTipoGarantia grabar(SituacionLaboralTipoGarantia o) throws DAOException {
		SituacionLaboralTipoGarantia dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public SituacionLaboralTipoGarantia modificar(SituacionLaboralTipoGarantia o) throws DAOException {
		SituacionLaboralTipoGarantia dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<SituacionLaboralTipoGarantia> getListaSituacionLaboralTipoGarantiaPorPK(Object o) throws DAOException{
		List<SituacionLaboralTipoGarantia> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<SituacionLaboralTipoGarantia> getListaSituacionLaboralTipoGarantiaPorCreditoTipoGarantia(Object o) throws DAOException{
		List<SituacionLaboralTipoGarantia> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorTipoGarantia", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<SituacionLaboralTipoGarantia> getListaSituacionLaboralPorCreditoTipoGarantia(Object o) throws DAOException{
		List<SituacionLaboralTipoGarantia> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaSituacionLaboralPorTipoGarantia", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}

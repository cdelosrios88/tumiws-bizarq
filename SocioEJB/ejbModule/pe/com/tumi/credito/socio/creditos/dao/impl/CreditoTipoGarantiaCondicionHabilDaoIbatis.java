package pe.com.tumi.credito.socio.creditos.dao.impl;

import java.util.List;

import pe.com.tumi.credito.socio.creditos.dao.CreditoTipoGarantiaCondicionHabilDao;
import pe.com.tumi.credito.socio.creditos.domain.CondicionHabilTipoGarantia;
import pe.com.tumi.credito.socio.creditos.domain.CondicionSocioTipoGarantia;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

public class CreditoTipoGarantiaCondicionHabilDaoIbatis extends TumiDaoIbatis implements CreditoTipoGarantiaCondicionHabilDao{
	
	public CondicionHabilTipoGarantia grabar(CondicionHabilTipoGarantia o) throws DAOException {
		CondicionHabilTipoGarantia dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public CondicionHabilTipoGarantia modificar(CondicionHabilTipoGarantia o) throws DAOException {
		CondicionHabilTipoGarantia dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<CondicionHabilTipoGarantia> getListaCondicionHabilTipoGarantiaPorPK(Object o) throws DAOException{
		List<CondicionHabilTipoGarantia> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<CondicionHabilTipoGarantia> getListaCondicionHabilTipoGarantiaPorCreditoTipoGarantia(Object o) throws DAOException{
		List<CondicionHabilTipoGarantia> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorTipoGarantia", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<CondicionHabilTipoGarantia> getListaCondicionHabilPorCreditoTipoGarantia(Object o) throws DAOException{
		List<CondicionHabilTipoGarantia> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaCondicionHabilPorTipoGarantia", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}

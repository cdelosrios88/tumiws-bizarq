package pe.com.tumi.credito.socio.creditos.dao.impl;

import java.util.List;

import pe.com.tumi.credito.socio.creditos.dao.CreditoTipoGarantiaCondicionSocioDao;
import pe.com.tumi.credito.socio.creditos.domain.CondicionSocioTipoGarantia;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

public class CreditoTipoGarantiaCondicionSocioDaoIbatis extends TumiDaoIbatis implements CreditoTipoGarantiaCondicionSocioDao{
	
	public CondicionSocioTipoGarantia grabar(CondicionSocioTipoGarantia o) throws DAOException {
		CondicionSocioTipoGarantia dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public CondicionSocioTipoGarantia modificar(CondicionSocioTipoGarantia o) throws DAOException {
		CondicionSocioTipoGarantia dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<CondicionSocioTipoGarantia> getListaCondicionSocioTipoGarantiaPorPK(Object o) throws DAOException{
		List<CondicionSocioTipoGarantia> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<CondicionSocioTipoGarantia> getListaCondicionSocioTipoGarantiaPorCreditoTipoGarantia(Object o) throws DAOException{
		List<CondicionSocioTipoGarantia> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorTipoGarantia", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<CondicionSocioTipoGarantia> getListaCondicionSocioPorCreditoTipoGarantia(Object o) throws DAOException{
		List<CondicionSocioTipoGarantia> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaCondicionSocioPorTipoGarantia", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}

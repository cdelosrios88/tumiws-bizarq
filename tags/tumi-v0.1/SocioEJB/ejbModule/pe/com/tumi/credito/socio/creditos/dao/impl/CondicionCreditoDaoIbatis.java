package pe.com.tumi.credito.socio.creditos.dao.impl;

import java.util.List;

import pe.com.tumi.credito.socio.creditos.dao.CondicionCreditoDao;
import pe.com.tumi.credito.socio.creditos.domain.CondicionCredito;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

public class CondicionCreditoDaoIbatis extends TumiDaoIbatis implements CondicionCreditoDao{
	
	public CondicionCredito grabar(CondicionCredito o) throws DAOException {
		CondicionCredito dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public CondicionCredito modificar(CondicionCredito o) throws DAOException {
		CondicionCredito dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<CondicionCredito> getListaCondicionPorPK(Object o) throws DAOException{
		List<CondicionCredito> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<CondicionCredito> getListaPorPkCaptacion(Object o) throws DAOException{
		List<CondicionCredito> lista = null;
		
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPkCredito", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<CondicionCredito> getListaCondicionSocioPorPkCaptacion(Object o) throws DAOException{
		List<CondicionCredito> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaCondicionSocioPorPkCredito", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}
package pe.com.tumi.credito.socio.creditos.dao.impl;

import java.util.List;

import pe.com.tumi.credito.socio.creditos.dao.CreditoInteresDao;
import pe.com.tumi.credito.socio.creditos.domain.CreditoInteres;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

public class CreditoInteresDaoIbatis extends TumiDaoIbatis implements CreditoInteresDao{
	
	public CreditoInteres grabar(CreditoInteres o) throws DAOException {
		CreditoInteres dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public CreditoInteres modificar(CreditoInteres o) throws DAOException {
		CreditoInteres dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<CreditoInteres> getListaCreditoInteresPorPK(Object o) throws DAOException{
		List<CreditoInteres> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<CreditoInteres> getListaCreditoInteresPorPKCredito(Object o) throws DAOException{
		List<CreditoInteres> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPkCredito", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public Object eliminar(Object o) throws DAOException{
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".eliminar", o);
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return o;
		
	}
}
package pe.com.tumi.credito.socio.creditos.dao.impl;

import java.util.List;

import pe.com.tumi.credito.socio.creditos.dao.CreditoExcepcionDao;
import pe.com.tumi.credito.socio.creditos.domain.CreditoExcepcion;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

public class CreditoExcepcionDaoIbatis extends TumiDaoIbatis implements CreditoExcepcionDao{
	
	public CreditoExcepcion grabar(CreditoExcepcion o) throws DAOException {
		CreditoExcepcion dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public CreditoExcepcion modificar(CreditoExcepcion o) throws DAOException {
		CreditoExcepcion dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<CreditoExcepcion> getListaCreditoExcepcionPorPK(Object o) throws DAOException{
		List<CreditoExcepcion> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<CreditoExcepcion> getListaCreditoExcepcionPorPKCredito(Object o) throws DAOException{
		List<CreditoExcepcion> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPkCredito", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public CreditoExcepcion eliminarCreditoExcepcion(CreditoExcepcion o) throws DAOException {
		CreditoExcepcion dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".eliminar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
}

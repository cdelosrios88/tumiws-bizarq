package pe.com.tumi.parametro.general.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.parametro.general.dao.TipoCambioDao;
import pe.com.tumi.parametro.general.domain.Archivo;
import pe.com.tumi.parametro.general.domain.TipoCambio;

public class TipoCambioDaoIbatis extends TumiDaoIbatis implements TipoCambioDao{
	
	public TipoCambio grabar(TipoCambio o) throws DAOException {
		TipoCambio dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public TipoCambio modificar(TipoCambio o) throws DAOException {
		TipoCambio dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<TipoCambio> getListaPorPK(Object o) throws DAOException{
		List<TipoCambio> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPK", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<TipoCambio> getBusqueda(Object o) throws DAOException{
		List<TipoCambio> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getBusqueda", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	public List<TipoCambio> getTipoCambioActual(Object o) throws DAOException{
		List<TipoCambio> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getTipoCambioActual", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	
	
}
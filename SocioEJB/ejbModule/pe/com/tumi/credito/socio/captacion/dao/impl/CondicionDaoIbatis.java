package pe.com.tumi.credito.socio.captacion.dao.impl;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.credito.socio.captacion.dao.CondicionDao;
import pe.com.tumi.credito.socio.captacion.domain.Condicion;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

public class CondicionDaoIbatis extends TumiDaoIbatis implements CondicionDao{
	
	public Condicion grabar(Condicion o) throws DAOException {
		Condicion dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public Condicion modificar(Condicion o) throws DAOException {
		Condicion dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<Condicion> getListaCondicionPorPK(Object o) throws DAOException{
		List<Condicion> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Condicion> getListaPorPkCaptacion(Object o) throws DAOException{
		List<Condicion> lista = null;
		
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPkCaptacion", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Condicion> getListaCondicionSocioPorPkCaptacion(Object o) throws DAOException{
		List<Condicion> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaCondicionSocioPorPkCaptacion", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}
package pe.com.tumi.credito.socio.estructura.dao.impl;

import java.util.List;

import pe.com.tumi.credito.socio.estructura.dao.AdminPadronDao;
import pe.com.tumi.credito.socio.estructura.domain.AdminPadron;


import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

public class AdminPadronDaoIbatis extends TumiDaoIbatis implements AdminPadronDao{
	
	public AdminPadron grabar(AdminPadron o) throws DAOException {
		AdminPadron dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public AdminPadron modificar(AdminPadron o) throws DAOException {
		AdminPadron dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<AdminPadron> getListaAdminPadronPorPK(Object o) throws DAOException{
		List<AdminPadron> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}

	@Override
	public List<AdminPadron> getListaBusqueda(Object o) throws DAOException {
		List<AdminPadron> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaBusqueda", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	@Override
	public List<AdminPadron> getLista(Object o) throws DAOException {
		List<AdminPadron> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getLista", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	/**
	 * 
	 */
	
	@Override
	public List<AdminPadron> getTipSocioModPeriodoMes(Object o) throws DAOException {
		List<AdminPadron> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getTipSocioModPeriodoMes", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}

	/**
	 * 
	 * @param o
	 * @return
	 * @throws DAOException
	 */
	public List<AdminPadron> getListaMaximoPorAdminPadron(Object o) throws DAOException {
		List<AdminPadron> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaMaximoPorAdminPadron", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}

}
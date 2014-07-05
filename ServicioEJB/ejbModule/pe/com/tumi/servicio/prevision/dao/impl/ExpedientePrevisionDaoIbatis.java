package pe.com.tumi.servicio.prevision.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.servicio.prevision.dao.ExpedientePrevisionDao;
import pe.com.tumi.servicio.prevision.domain.ExpedientePrevision;
import pe.com.tumi.servicio.prevision.domain.composite.RequisitoPrevisionComp;

public class ExpedientePrevisionDaoIbatis extends TumiDaoIbatis implements ExpedientePrevisionDao{
	
	public ExpedientePrevision grabar(ExpedientePrevision o) throws DAOException {
		ExpedientePrevision dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public ExpedientePrevision modificar(ExpedientePrevision o) throws DAOException {
		ExpedientePrevision dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<ExpedientePrevision> getListaPorPk(Object o) throws DAOException{
		List<ExpedientePrevision> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			System.out.println("ExceptionException --> "+e);
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<ExpedientePrevision> getListaPorCuenta(Object o) throws DAOException{
		List<ExpedientePrevision> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorCuenta", o);
		}catch(Exception e) {
			System.out.println("getListaPorCuentagetListaPorCuenta---> "+e);
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<ExpedientePrevision> getListaExpedientePrevisionBusqueda() throws DAOException{
		List<ExpedientePrevision> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaExpedientePrevisionBusqueda");
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	/**
	 * 
	 */
	public List<ExpedientePrevision> getListaBusqPrevisionFiltros(Object o) throws DAOException{
		List<ExpedientePrevision> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaBusqPrevisionConFiltros",o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	
	/**
	 * Recupera los expedientes de prevision segun filtros de busqeda
	 * @param o
	 * @return
	 * @throws DAOException
	 */
	public List<ExpedientePrevision> getListaBusqExpPrevFiltros(Object o) throws DAOException{
		List<ExpedientePrevision> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaBusqExpPrevFiltros",o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	
	/**
	 * Recupera los expedientes de prevision para autorizacion segun filtros de busqeda
	 * @param o
	 * @return
	 * @throws DAOException
	 */
	public List<ExpedientePrevision> getListaBusqAutExpPrevFiltros(Object o) throws DAOException{
		List<ExpedientePrevision> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaBusqAutExpPrevFiltros",o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	/**
	 * 
	 */
	public List<ExpedientePrevision> getUltimoEstadoExpPrev(Object o) throws DAOException{
		List<ExpedientePrevision> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getUltimoEstadoExpPrev", o);
		}catch(Exception e) {
			System.out.println("getUltimoEstadoExpPrev---> "+e);
			throw new DAOException (e);
		}
		return lista;
	}
	/**
	 * JCHAVEZ 06.02.2014
	 * Procedimiento que recupera los Requisitos necesarios para el giro en Giro PREVISION
	 */
	public List<RequisitoPrevisionComp> getRequisitoGiroPrevision(Object o) throws DAOException{
		List<RequisitoPrevisionComp> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getRequisitoGiroPrevision", o);
		}catch(Exception e) {
			System.out.println("Error en getRequisitoGiroPrevision--->  "+e);
			throw new DAOException (e);
		}
		return lista;
	}
}
package pe.com.tumi.servicio.prevision.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.servicio.prevision.dao.ExpedienteLiquidacionDao;
import pe.com.tumi.servicio.prevision.domain.ExpedienteLiquidacion;
import pe.com.tumi.servicio.prevision.domain.composite.RequisitoLiquidacionComp;

public class ExpedienteLiquidacionDaoIbatis extends TumiDaoIbatis implements ExpedienteLiquidacionDao{
	
	public ExpedienteLiquidacion grabar(ExpedienteLiquidacion o) throws DAOException {
		ExpedienteLiquidacion dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public ExpedienteLiquidacion modificar(ExpedienteLiquidacion o) throws DAOException {
		ExpedienteLiquidacion dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<ExpedienteLiquidacion> getListaPorPk(Object o) throws DAOException{
		List<ExpedienteLiquidacion> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<ExpedienteLiquidacion> getListaCompleta(Object o) throws DAOException{
		List<ExpedienteLiquidacion> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaCompleta", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<ExpedienteLiquidacion> getListaCompletaFiltros(Object o) throws DAOException{
		List<ExpedienteLiquidacion> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaCompletaFiltros", o);
		}catch(Exception e) {
			System.out.println("Error eException ------> "+e);
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<ExpedienteLiquidacion> getListaPorEmpresaYCuenta(Object o) throws DAOException{
		List<ExpedienteLiquidacion> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorEmpresaYCuenta", o);
		}catch(Exception e) {
			System.out.println("Error eException ------> "+e);
			throw new DAOException (e);
		}
		return lista;
	}
	
	/**
	 * Recupera los expedientes de eliquidacion segun filtros de busqueda
	 * @param o
	 * @return
	 * @throws DAOException
	 */
	public List<ExpedienteLiquidacion> getListaBusqExpLiqFiltros(Object o) throws DAOException{
		List<ExpedienteLiquidacion> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaBusqExpLiqFiltros", o);
		}catch(Exception e) {
			System.out.println("Error eException ------> "+e);
			throw new DAOException (e);
		}
		return lista;
	}
	
	/**
	 * Recupera los expedientes de eliquidacion segun filtros de busqueda
	 * @param o
	 * @return
	 * @throws DAOException
	 */
	public List<ExpedienteLiquidacion> getListaBusqAutLiqFiltros(Object o) throws DAOException{
		List<ExpedienteLiquidacion> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaBusqAutLiqFiltros", o);
		}catch(Exception e) {
			System.out.println("Error eException ------> "+e);
			throw new DAOException (e);
		}
		return lista;
	}

	/**
	 * JCHAVEZ 08.02.2014
	 * Procedimiento que recupera los Requisitos necesarios para el giro en Giro LIQUIDACION
	 * @param o
	 * @return
	 * @throws DAOException
	 */
	public List<RequisitoLiquidacionComp> getRequisitoGiroLiquidacion(Object o) throws DAOException{
		List<RequisitoLiquidacionComp> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getRequisitoGiroLiquidacion", o);
		}catch(Exception e) {
			System.out.println("Error en getRequisitoGiroLiquidacion--->  "+e);
			throw new DAOException (e);
		}
		return lista;
	}	
}
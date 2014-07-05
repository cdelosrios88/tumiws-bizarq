package pe.com.tumi.contabilidad.core.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

import pe.com.tumi.contabilidad.core.dao.PlanCuentaDao;
import pe.com.tumi.contabilidad.core.domain.PlanCuenta;

public class PlanCuentaDaoIbatis extends TumiDaoIbatis implements PlanCuentaDao{

	public PlanCuenta grabar(PlanCuenta o) throws DAOException{
		PlanCuenta dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}

	public PlanCuenta modificar(PlanCuenta o) throws DAOException{
		PlanCuenta dto = null;
		try {
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e){
			throw new DAOException(e);
		}
		return dto;
	}  

	public List<PlanCuenta> getListaPorPk(Object o) throws DAOException{
		List<PlanCuenta> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<PlanCuenta> getBusqueda(Object o) throws DAOException{
		List<PlanCuenta> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getBusqueda", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<PlanCuenta> findListCuentaOperacional(Object o) throws DAOException{
		List<PlanCuenta> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".findListCuentaOperacional", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<PlanCuenta> getPeriodos(Object o) throws DAOException{
		List<PlanCuenta> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPeriodo", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<PlanCuenta> getListaPorEmpresaCuentaYPeriodoCuenta(Object o) throws DAOException{
		List<PlanCuenta> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorEmpresaCuentaYPeriodoCuenta", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	/**
	 * AUTOR Y FECHA CREACION: JCHAVEZ / 23.10.2013
	 * Recupera lista Plan Cuenta por periodo base
	 * @param o
	 * @return
	 * @throws DAOException
	 */
	public List<PlanCuenta> getPlanCtaPorPeriodoBase(Object o) throws DAOException{
		List<PlanCuenta> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getPlanCtaPorPeriodoBase", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	/**
	 * AUTOR Y FECHA CREACION: JCHAVEZ / 23.10.2013
	 * Recupera lista Plan Cuenta por periodo proyectado, nro cuenta o descripcion (opcional)
	 * @param o
	 * @return
	 * @throws DAOException
	 */
	public List<PlanCuenta> getBusqPorNroCtaDesc(Object o) throws DAOException{
		List<PlanCuenta> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getBusqPorNroCtaDesc", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}	

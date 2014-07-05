package pe.com.tumi.contabilidad.core.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.contabilidad.core.dao.PlanCuentaDao;
import pe.com.tumi.contabilidad.core.domain.PlanCuenta;

public interface PlanCuentaDao extends TumiDao{
	public PlanCuenta grabar(PlanCuenta pDto) throws DAOException;
	public PlanCuenta modificar(PlanCuenta o) throws DAOException;
	public List<PlanCuenta> getListaPorPk(Object o) throws DAOException;
	public List<PlanCuenta> getBusqueda(Object o) throws DAOException;
	public List<PlanCuenta> findListCuentaOperacional(Object o) throws DAOException;
	public List<PlanCuenta> getPeriodos(Object o) throws DAOException;
	public List<PlanCuenta> getListaPorEmpresaCuentaYPeriodoCuenta(Object o) throws DAOException;
	//AUTOR Y FECHA CREACION: JCHAVEZ / 23.10.2013
	public List<PlanCuenta> getPlanCtaPorPeriodoBase(Object o) throws DAOException;
	public List<PlanCuenta> getBusqPorNroCtaDesc(Object o) throws DAOException;
	//fin agregado JCHAVEZ
}	

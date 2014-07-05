package pe.com.tumi.contabilidad.core.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.contabilidad.core.dao.AccesoPlanCuentaDao;
import pe.com.tumi.contabilidad.core.domain.AccesoPlanCuenta;

public interface AccesoPlanCuentaDao extends TumiDao{
	public AccesoPlanCuenta grabar(AccesoPlanCuenta pDto) throws DAOException;
	public AccesoPlanCuenta modificar(AccesoPlanCuenta o) throws DAOException;
	public List<AccesoPlanCuenta> getListaPorPk(Object o) throws DAOException;
	public List<AccesoPlanCuenta> getListaPorPlanCuenta(Object o) throws DAOException;
}	

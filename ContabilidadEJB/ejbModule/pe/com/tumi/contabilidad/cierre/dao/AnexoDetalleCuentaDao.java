package pe.com.tumi.contabilidad.cierre.dao;

import java.util.List;

import pe.com.tumi.contabilidad.cierre.domain.AnexoDetalle;
import pe.com.tumi.contabilidad.cierre.domain.AnexoDetalleCuenta;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;

public interface AnexoDetalleCuentaDao extends TumiDao{

	public AnexoDetalleCuenta grabar(AnexoDetalleCuenta o) throws DAOException;
	public AnexoDetalleCuenta modificar(AnexoDetalleCuenta o) throws DAOException;
	public List<AnexoDetalleCuenta> getListaPorPk(Object o) throws DAOException;
	public List<AnexoDetalleCuenta> getListaPorAnexoDetalle(Object o) throws DAOException;
	public void eliminar(Object o) throws DAOException;
	//Agregado por cdelosrios, 16/09/2013
	public List<AnexoDetalleCuenta> getListaPorPlanCuenta(Object o) throws DAOException;
	//Fin agregado por cdelosrios, 16/09/2013
}

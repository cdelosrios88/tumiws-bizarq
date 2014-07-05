package pe.com.tumi.contabilidad.cierre.dao;

import java.util.List;

import pe.com.tumi.contabilidad.cierre.domain.CuentaCierreDetalle;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;

public interface CuentaCierreDetalleDao extends TumiDao{

	public CuentaCierreDetalle grabar(CuentaCierreDetalle o) throws DAOException;
	public CuentaCierreDetalle modificar(CuentaCierreDetalle o) throws DAOException;
	public List<CuentaCierreDetalle> getListaPorPk(Object o) throws DAOException;
	public List<CuentaCierreDetalle> getListaPorCuentaCierre(Object o) throws DAOException;
	public void eliminar(Object o) throws DAOException;
}

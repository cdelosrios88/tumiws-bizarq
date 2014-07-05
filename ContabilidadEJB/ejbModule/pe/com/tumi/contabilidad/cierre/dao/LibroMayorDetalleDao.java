package pe.com.tumi.contabilidad.cierre.dao;

import java.util.List;

import pe.com.tumi.contabilidad.cierre.domain.LibroMayor;
import pe.com.tumi.contabilidad.cierre.domain.LibroMayorDetalle;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;

public interface LibroMayorDetalleDao extends TumiDao{

	public LibroMayorDetalle grabar(LibroMayorDetalle o) throws DAOException;
	public LibroMayorDetalle modificar(LibroMayorDetalle o) throws DAOException;
	public List<LibroMayorDetalle> getListaPorPk(Object o) throws DAOException;
	public List<LibroMayorDetalle> getListaPorLibroMayor(Object o) throws DAOException;
	public void eliminar(Object o) throws DAOException;
	public List<LibroMayorDetalle> getListaPorLibroMayorYPlanCuenta(Object o) throws DAOException;
}

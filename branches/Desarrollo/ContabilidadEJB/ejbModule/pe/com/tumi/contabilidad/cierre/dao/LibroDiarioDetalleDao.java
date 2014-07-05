package pe.com.tumi.contabilidad.cierre.dao;

import java.util.List;

import pe.com.tumi.contabilidad.cierre.domain.LibroDiarioDetalle;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;

public interface LibroDiarioDetalleDao extends TumiDao{

	public LibroDiarioDetalle grabar(LibroDiarioDetalle o) throws DAOException;
	public LibroDiarioDetalle modificar(LibroDiarioDetalle o) throws DAOException;
	public List<LibroDiarioDetalle> getListaPorPk(Object o) throws DAOException;
	public List<LibroDiarioDetalle> getListaPorLibroMayorYPlanCuenta(Object o) throws DAOException;
	public List<LibroDiarioDetalle> getListaPorLibroDiario(Object o) throws DAOException;
	public List<LibroDiarioDetalle> getListaPorBuscar(Object o) throws DAOException;
	/* Inicio - GTorresBroussetP - 05.abr.2014 */
	/* Buscar Libro Diario Detalle por Periodo y Documento */
	public List<LibroDiarioDetalle> getListaPorPeriodoDocumento(Object o) throws DAOException;
	/* Fin - GTorresBroussetP - 05.abr.2014 */
}

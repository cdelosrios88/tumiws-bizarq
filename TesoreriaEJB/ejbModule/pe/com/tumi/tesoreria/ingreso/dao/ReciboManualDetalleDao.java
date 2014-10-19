package pe.com.tumi.tesoreria.ingreso.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.tesoreria.ingreso.domain.ReciboManualDetalle;

public interface ReciboManualDetalleDao extends TumiDao{
	public ReciboManualDetalle grabar(ReciboManualDetalle o) throws DAOException;
	public ReciboManualDetalle modificar(ReciboManualDetalle o) throws DAOException;
	public List<ReciboManualDetalle> getListaPorPk(Object o) throws DAOException;
	public List<ReciboManualDetalle> getListaPorReciboManual(Object o) throws DAOException;
	public List<ReciboManualDetalle> getListaPorIngreso(Object o) throws DAOException;
	public String existeNroReciboEnlazado(Object o) throws DAOException;
	public List<ReciboManualDetalle> getListaPorFiltros(Object o) throws DAOException;
}
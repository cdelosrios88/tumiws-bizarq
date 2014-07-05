package pe.com.tumi.movimiento.concepto.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.movimiento.concepto.domain.EstadoExpediente;
import pe.com.tumi.servicio.solicitudPrestamo.domain.EstadoCredito;

public interface EstadoExpedienteDao extends TumiDao {
	public EstadoExpediente grabar(EstadoExpediente o) throws DAOException;
	public EstadoExpediente modificar(EstadoExpediente o) throws DAOException;
	public List<EstadoExpediente> getListaPorPK(Object o) throws DAOException;
	public List<EstadoExpediente> getListaPorExpediente(Object o) throws DAOException;
	public List<EstadoExpediente> getMaxEstadoExpPorPkExpediente(Object o) throws DAOException;
}

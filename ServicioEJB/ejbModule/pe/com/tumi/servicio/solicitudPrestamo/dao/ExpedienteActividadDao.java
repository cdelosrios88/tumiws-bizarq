package pe.com.tumi.servicio.solicitudPrestamo.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.servicio.solicitudPrestamo.domain.ExpedienteActividad;

public interface ExpedienteActividadDao extends TumiDao {
	public ExpedienteActividad grabar(ExpedienteActividad o) throws DAOException;
	public ExpedienteActividad modificar(ExpedienteActividad o) throws DAOException;		
	public List<ExpedienteActividad> getListaPorExpedienteCredito(Object o) throws DAOException;

}

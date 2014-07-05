package pe.com.tumi.seguridad.permiso.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.seguridad.permiso.domain.SolicitudCambio;

public interface SolicitudCambioDao extends TumiDao {
	public SolicitudCambio grabar(SolicitudCambio o) throws DAOException;
	public SolicitudCambio modificar(SolicitudCambio o) throws DAOException;
	public List<SolicitudCambio> getListaPorPk(Object o) throws DAOException;
}

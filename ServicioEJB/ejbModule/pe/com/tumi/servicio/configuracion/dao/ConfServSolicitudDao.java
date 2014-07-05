package pe.com.tumi.servicio.configuracion.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.servicio.configuracion.domain.ConfServSolicitud;

public interface ConfServSolicitudDao extends TumiDao{
	public ConfServSolicitud grabar(ConfServSolicitud o) throws DAOException;
	public ConfServSolicitud modificar(ConfServSolicitud o) throws DAOException;
	public List<ConfServSolicitud> getListaPorPk(Object o) throws DAOException;
	public List<ConfServSolicitud> getListaPorBuscar(Object o) throws DAOException;
	public List<ConfServSolicitud> getListaPorTipoOperacionTipoRequisito(Object o) throws DAOException;
}

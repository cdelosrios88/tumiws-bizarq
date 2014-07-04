package pe.com.tumi.tesoreria.egreso.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.tesoreria.egreso.domain.SolicitudPersonalDetalle;

public interface SolicitudPersonalDetalleDao extends TumiDao{
	public SolicitudPersonalDetalle grabar(SolicitudPersonalDetalle o) throws DAOException;
	public SolicitudPersonalDetalle modificar(SolicitudPersonalDetalle o) throws DAOException;
	public List<SolicitudPersonalDetalle> getListaPorPk(Object o) throws DAOException;
	public List<SolicitudPersonalDetalle> getListaPorSolicitudPersonal(Object o) throws DAOException;
	public void eliminar(Object o) throws DAOException;
}
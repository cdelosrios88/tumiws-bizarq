package pe.com.tumi.servicio.configuracion.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.servicio.configuracion.domain.ConfServEstructuraDetalle;

public interface ConfServEstructuraDetalleDao extends TumiDao{
	public ConfServEstructuraDetalle grabar(ConfServEstructuraDetalle o) throws DAOException;
	public ConfServEstructuraDetalle modificar(ConfServEstructuraDetalle o) throws DAOException;
	public List<ConfServEstructuraDetalle> getListaPorPk(Object o) throws DAOException;
	public List<ConfServEstructuraDetalle> getListaPorCabecera(Object o) throws DAOException;
}

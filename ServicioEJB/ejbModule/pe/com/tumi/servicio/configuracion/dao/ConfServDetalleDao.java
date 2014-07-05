package pe.com.tumi.servicio.configuracion.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.servicio.configuracion.domain.ConfServDetalle;

public interface ConfServDetalleDao extends TumiDao{
	public ConfServDetalle grabar(ConfServDetalle o) throws DAOException;
	public ConfServDetalle modificar(ConfServDetalle o) throws DAOException;
	public List<ConfServDetalle> getListaPorPk(Object o) throws DAOException;
	public List<ConfServDetalle> getListaPorCabecera(Object o) throws DAOException; 
}

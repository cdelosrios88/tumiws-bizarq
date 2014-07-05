package pe.com.tumi.seguridad.permiso.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.seguridad.permiso.domain.AccesoEspecialDetalle;

public interface AccesoEspecialDetalleDao extends TumiDao {
	public AccesoEspecialDetalle grabar(AccesoEspecialDetalle o) throws DAOException;
	public AccesoEspecialDetalle modificar(AccesoEspecialDetalle o) throws DAOException;
	public List<AccesoEspecialDetalle> getListaPorCabecera(Object o) throws DAOException;
	public List<AccesoEspecialDetalle> getListaPorPk(Object o) throws DAOException;
}

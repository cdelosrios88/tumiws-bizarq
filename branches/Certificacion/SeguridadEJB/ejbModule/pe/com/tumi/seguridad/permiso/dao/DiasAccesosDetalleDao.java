package pe.com.tumi.seguridad.permiso.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.seguridad.permiso.domain.DiasAccesosDetalle;

public interface DiasAccesosDetalleDao extends TumiDao {
	public DiasAccesosDetalle grabar(DiasAccesosDetalle o) throws DAOException;
	public DiasAccesosDetalle modificar(DiasAccesosDetalle o) throws DAOException;
	public List<DiasAccesosDetalle> getListaPorPk(Object o) throws DAOException;
	public List<DiasAccesosDetalle> getListaPorCabecera(Object o) throws DAOException;
}

package pe.com.tumi.seguridad.permiso.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.seguridad.permiso.domain.PermisoUsuario;

public interface PermisoUsuarioDao extends TumiDao {
	public PermisoUsuario grabar(PermisoUsuario o) throws DAOException;
	public PermisoUsuario modificar(PermisoUsuario o) throws DAOException;
	public List<PermisoUsuario> getListaPorPk(Object o) throws DAOException;
}

package pe.com.tumi.seguridad.permiso.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.seguridad.permiso.domain.PermisoPerfil;

public interface PermisoPerfilDao extends TumiDao {
	public PermisoPerfil grabar(PermisoPerfil o) throws DAOException;
	public PermisoPerfil modificar(PermisoPerfil o) throws DAOException;
	public List<PermisoPerfil> getListaPorPk(Object o) throws DAOException;
	public List<PermisoPerfil> getListaPorPkPerfil(Object o) throws DAOException;
}

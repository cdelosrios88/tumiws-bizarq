package pe.com.tumi.seguridad.login.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.seguridad.login.domain.UsuarioPerfil;

public interface UsuarioPerfilDao extends TumiDao {
	public UsuarioPerfil grabar(UsuarioPerfil o) throws DAOException;
	public UsuarioPerfil modificar(UsuarioPerfil o) throws DAOException;
	public List<UsuarioPerfil> getListaPorPk(Object o) throws DAOException;
	public List<UsuarioPerfil> getListaPorPkEmpresaUsuario(Object o) throws DAOException;
	public List<UsuarioPerfil> getListaPorPkEmpresaUsuarioYFechaEliminacion(Object o) throws DAOException;
}

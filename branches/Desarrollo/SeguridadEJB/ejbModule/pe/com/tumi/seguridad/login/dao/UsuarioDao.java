package pe.com.tumi.seguridad.login.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.seguridad.login.domain.Usuario;

public interface UsuarioDao extends TumiDao {
	public Usuario grabar(Usuario o) throws DAOException;
	public Usuario modificar(Usuario o) throws DAOException;
	public List<Usuario> getListaPorPk(Object o) throws DAOException;
	public List<Usuario> getListaPorCodigo(Object o) throws DAOException;
	public List<Usuario> getListaPorCodigoYClave(Object o) throws DAOException;
}

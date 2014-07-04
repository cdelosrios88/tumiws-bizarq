package pe.com.tumi.seguridad.login.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.seguridad.login.domain.UsuarioSucursal;

public interface UsuarioSucursalDao extends TumiDao {
	public UsuarioSucursal grabar(UsuarioSucursal o) throws DAOException;
	public UsuarioSucursal modificar(UsuarioSucursal o) throws DAOException;
	public List<UsuarioSucursal> getListaPorPk(Object o) throws DAOException;
	public List<UsuarioSucursal> getListaPorPkEmpresaUsuario(Object o) throws DAOException;
	public List<UsuarioSucursal> getListaPorPkEmpresaUsuarioYFechaEliminacion(Object o) throws DAOException;
}

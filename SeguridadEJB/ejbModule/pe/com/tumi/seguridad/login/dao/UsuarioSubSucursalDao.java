package pe.com.tumi.seguridad.login.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.seguridad.login.domain.UsuarioSubSucursal;

public interface UsuarioSubSucursalDao extends TumiDao {
	public UsuarioSubSucursal grabar(UsuarioSubSucursal o) throws DAOException;
	public UsuarioSubSucursal modificar(UsuarioSubSucursal o) throws DAOException;
	public List<UsuarioSubSucursal> getListaPorPk(Object o) throws DAOException;
	public List<UsuarioSubSucursal> getListaPorPkEmpresaUsuario(Object o) throws DAOException;
	public List<UsuarioSubSucursal> getListaPorPkEmpresaUsuarioYFechaEliminacion(Object o) throws DAOException;
	public List<UsuarioSubSucursal> getListaPorPkEmpresaUsrYIdSuc(Object o) throws DAOException;
	public List<UsuarioSubSucursal> getListaPorSucYSubSucursal(Object o) throws DAOException;
}

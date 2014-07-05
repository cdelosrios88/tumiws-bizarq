package pe.com.tumi.seguridad.empresa.dao;

import java.util.List;

import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.empresa.domain.SucursalId;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;

public interface SucursalDao extends TumiDao {
	
	public Sucursal grabar(Sucursal o) throws DAOException;
	public Sucursal modificar(Sucursal o) throws DAOException;
	public List<Sucursal> getListaSucursalPorIdPersona(Object o) throws DAOException;
	public List<Sucursal> getPorPkYIdTipoSucursal(Object o) throws DAOException;
	public List<Sucursal> getListaSucursalPorIdSucursal(Object o) throws DAOException;
	public List<Sucursal> getListaSucursalPorPkZonal(Object o) throws DAOException;
	public List<Sucursal> getListaSucursalPorPkEmpresa(Object o) throws DAOException;
	public List<Sucursal> getListaSucursalSinZonalPorPkEmpresa(Object o) throws DAOException;
	public List<Sucursal> getListaSucursalPorPkEmpresaUsuario(Object o) throws DAOException;
	public List<Sucursal> getListaPorPkEmpresaUsuarioYSt(Object o) throws DAOException;
	public Integer getCantidadSucursalPorPkZonal(Object o) throws DAOException;
	public List<Sucursal> getListaSucursalDeBusqueda(Object o) throws DAOException;
	public Sucursal eliminarSucursal(Sucursal o) throws DAOException;
	public List<Sucursal> getListaSucursalPorPk(Object o) throws DAOException;
	public List<Sucursal> getListaPorEmpresaYTipoSucursal(Object o) throws DAOException;
	public List<Sucursal> getListaPorEmpresaYTodoTipoSucursal(Object o) throws DAOException;
}

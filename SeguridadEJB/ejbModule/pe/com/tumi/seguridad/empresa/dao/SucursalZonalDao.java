package pe.com.tumi.seguridad.empresa.dao;

import java.util.List;

import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;

public interface SucursalZonalDao extends TumiDao {
	public List<Sucursal> getListaSucursalPorPkEmpresa(Object o) throws DAOException;
	public List<Sucursal> getListaSucursalPorPkEmpresaYTipo(Object o) throws DAOException;
	public List<Sucursal> getListaSucursalPorPkEmpresaYTipoDeAne(Object o) throws DAOException;
	public List<Sucursal> getListaSucursalPorPkEmpresaYTipoDeLib(Object o) throws DAOException;
	public List<Sucursal> getListaSucursalPorPkEmpresaYIdZonalYTipo(Object o) throws DAOException;
	public List<Sucursal> getListaSucursalPorPkEmpresaYIdZonalYTipoDeAne(Object o) throws DAOException;
	public List<Sucursal> getListaSucursalPorPkEmpresaYIdZonalYTipoDeLib(Object o) throws DAOException;
}

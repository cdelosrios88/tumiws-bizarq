package pe.com.tumi.seguridad.empresa.dao;

import java.util.List;

import pe.com.tumi.empresa.domain.Subsucursal;
import pe.com.tumi.empresa.domain.SucursalCodigo;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;

public interface SubSucursalDao extends TumiDao {
	
	public Subsucursal grabar(Subsucursal o) throws DAOException;
	public Subsucursal modificar(Subsucursal o) throws DAOException;
	public List<Subsucursal> getListaSubSucursalPorPK(Object o) throws DAOException;
	public List<Subsucursal> getListaPorIdSubSucursal(Object o) throws DAOException;
	public Integer getCantidadSubSucursalPorPkSucursal(Object o) throws DAOException;
	public List<Subsucursal> getListaSubSucursalPorIdSucursal(Object o) throws DAOException;
	public List<Subsucursal> getListPorPkEmpresUsrYIdSucYSt(Object o) throws DAOException;
	public List<Subsucursal> getListaPorIdSucursalYSt(Object o) throws DAOException;
	
}

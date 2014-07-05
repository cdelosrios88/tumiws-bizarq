package pe.com.tumi.tesoreria.logistica.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.tesoreria.logistica.domain.Proveedor;

public interface ProveedorDao extends TumiDao{
	public Proveedor grabar(Proveedor pDto) throws DAOException;
	public Proveedor modificar(Proveedor o) throws DAOException;
	public List<Proveedor> getListaPorPk(Object o) throws DAOException;
	public List<Proveedor> getListaPorBusqueda(Object o) throws DAOException;
}	

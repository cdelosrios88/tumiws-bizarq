package pe.com.tumi.tesoreria.logistica.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.tesoreria.logistica.domain.ProveedorDetalle;

public interface ProveedorDetalleDao extends TumiDao{
	public ProveedorDetalle grabar(ProveedorDetalle pDto) throws DAOException;
	public ProveedorDetalle modificar(ProveedorDetalle o) throws DAOException;
	public List<ProveedorDetalle> getListaPorPk(Object o) throws DAOException;
	public List<ProveedorDetalle> getListaPorProveedor(Object o) throws DAOException;
	public void eliminar(Object o) throws DAOException;
}
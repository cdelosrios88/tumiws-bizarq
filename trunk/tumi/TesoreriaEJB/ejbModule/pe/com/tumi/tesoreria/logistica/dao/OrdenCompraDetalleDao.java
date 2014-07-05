package pe.com.tumi.tesoreria.logistica.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.tesoreria.logistica.domain.OrdenCompraDetalle;

public interface OrdenCompraDetalleDao extends TumiDao{
	public OrdenCompraDetalle grabar(OrdenCompraDetalle pDto) throws DAOException;
	public OrdenCompraDetalle modificar(OrdenCompraDetalle o) throws DAOException;
	public List<OrdenCompraDetalle> getListaPorPk(Object o) throws DAOException;
	public List<OrdenCompraDetalle> getListaPorOrdenCompra(Object o) throws DAOException;
}
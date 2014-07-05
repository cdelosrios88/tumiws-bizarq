package pe.com.tumi.tesoreria.logistica.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.tesoreria.logistica.domain.OrdenCompra;

public interface OrdenCompraDao extends TumiDao{
	public OrdenCompra grabar(OrdenCompra pDto) throws DAOException;
	public OrdenCompra modificar(OrdenCompra o) throws DAOException;
	public List<OrdenCompra> getListaPorPk(Object o) throws DAOException;
	public List<OrdenCompra> getListaPorBuscar(Object o) throws DAOException;
}
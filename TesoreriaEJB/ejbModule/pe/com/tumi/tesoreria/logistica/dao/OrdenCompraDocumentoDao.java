package pe.com.tumi.tesoreria.logistica.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.tesoreria.logistica.domain.OrdenCompraDocumento;

public interface OrdenCompraDocumentoDao extends TumiDao{
	public OrdenCompraDocumento grabar(OrdenCompraDocumento pDto) throws DAOException;
	public OrdenCompraDocumento modificar(OrdenCompraDocumento o) throws DAOException;
	public List<OrdenCompraDocumento> getListaPorPk(Object o) throws DAOException;
	public List<OrdenCompraDocumento> getListaPorOrdenCompra(Object o) throws DAOException;
	//Autor: jchavez / Tarea: Creacion / Fecha: 22.10.2014
	public List<OrdenCompraDocumento> getListaPorEgreso(Object o) throws DAOException;
}
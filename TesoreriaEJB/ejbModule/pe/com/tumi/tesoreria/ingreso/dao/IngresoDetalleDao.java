package pe.com.tumi.tesoreria.ingreso.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.tesoreria.ingreso.domain.IngresoDetalle;

public interface IngresoDetalleDao extends TumiDao{
	public IngresoDetalle grabar(IngresoDetalle o) throws DAOException;
	public IngresoDetalle modificar(IngresoDetalle o) throws DAOException;
	public List<IngresoDetalle> getListaPorPk(Object o) throws DAOException;
	public List<IngresoDetalle> getListaPorIngreso(Object o) throws DAOException;
	//Agregado 12.12.2013 JCHAVEZ
	public List<IngresoDetalle> getPorControlFondosFijos(Object o) throws DAOException;
}
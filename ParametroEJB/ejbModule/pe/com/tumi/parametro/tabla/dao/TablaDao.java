package pe.com.tumi.parametro.tabla.dao;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.parametro.tabla.domain.Tabla;

public interface TablaDao extends TumiDao{
	public List<Tabla> getListaTablaPorIdMaestro(Object pOParametro) throws DAOException;
	public List<Tabla> getListaTablaPorIdMaestroYIdDetalle(Object pOParametro) throws DAOException;
	public List<Tabla> getListaTablaPorAgrupamientoA(Object pO) throws DAOException;
	public List<Tabla> getListaTablaPorAgrupamientoB(Object pOParametro) throws DAOException;
	public List<Tabla> getListaTablaPorIdMaestroYNotInIdDetalle(Object pO) throws DAOException;
}

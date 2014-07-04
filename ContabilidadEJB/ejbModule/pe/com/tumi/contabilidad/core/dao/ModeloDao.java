package pe.com.tumi.contabilidad.core.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.contabilidad.core.dao.ModeloDao;
import pe.com.tumi.contabilidad.core.domain.Modelo;

public interface ModeloDao extends TumiDao{
	public Modelo grabar(Modelo pDto) throws DAOException;
	public Modelo modificar(Modelo o) throws DAOException;
	public List<Modelo> getListaPorPk(Object o) throws DAOException;
	public List<Modelo> getBusqueda(Object o) throws DAOException;
}	

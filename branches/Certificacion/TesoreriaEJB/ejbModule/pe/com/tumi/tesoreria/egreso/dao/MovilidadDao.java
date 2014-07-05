package pe.com.tumi.tesoreria.egreso.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.tesoreria.egreso.domain.Movilidad;

public interface MovilidadDao extends TumiDao{
	public Movilidad grabar(Movilidad pDto) throws DAOException;
	public Movilidad modificar(Movilidad o) throws DAOException;
	public List<Movilidad> getListaPorPk(Object o) throws DAOException;
	public List<Movilidad> getListaPorBusqueda(Object o) throws DAOException;
	public List<Movilidad> getListaPorPersona(Object o) throws DAOException;
	public List<Movilidad> getListaPorEgreso(Object o) throws DAOException;
	//Agregado 16.12.2013
	public List<Movilidad> getPorFiltroBusqueda(Object o) throws DAOException;
}	

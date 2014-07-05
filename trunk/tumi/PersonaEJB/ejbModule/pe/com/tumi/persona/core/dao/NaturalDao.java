package pe.com.tumi.persona.core.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.persona.core.domain.Natural;
import pe.com.tumi.persona.core.domain.Persona;

public interface NaturalDao extends TumiDao{
	public Natural grabar(Natural o) throws DAOException;
	public Natural modificar(Natural o) throws DAOException;
	public List<Natural> getListaNaturalPorPK(Object o) throws DAOException;
	public List<Natural> getListaNaturalPorInPk(Object o) throws DAOException;
	public List<Natural> getNaturalPorTipoIdentidadYNroIdentidad(Object o) throws DAOException;
	public List<Natural> getListaPerNaturalBusqueda(Object o) throws DAOException;
	public List<Natural> getListaBusqRolODniONomb(Object o) throws DAOException;
	
	
}

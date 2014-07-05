package pe.com.tumi.persona.core.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.persona.core.domain.Persona;

public interface PersonaDao extends TumiDao{
	public Persona grabar(Persona o) throws DAOException;
	public Persona modificar(Persona o) throws DAOException;
	public List<Persona> getListaPersonaPorPK(Object o) throws DAOException;
	public List<Persona> getListaPersonaBusqueda(Object o) throws DAOException;
	public List<Persona> getListaPersonaPorRuc(Object o) throws DAOException;
	public List<Persona> getListaPersonaActivaPorIdPersona(Object o) throws DAOException;
}

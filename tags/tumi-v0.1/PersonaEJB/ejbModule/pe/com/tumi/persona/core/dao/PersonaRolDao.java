package pe.com.tumi.persona.core.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.persona.core.domain.PersonaRol;

public interface PersonaRolDao extends TumiDao{
	public PersonaRol grabar(PersonaRol o) throws DAOException;
	public PersonaRol modificar(PersonaRol o) throws DAOException;
	public List<PersonaRol> getListaPersonaRolPorPK(Object o) throws DAOException;
	public List<PersonaRol> getListaPersonaRolPorPKPersonaEmpresa(Object o) throws DAOException;
	public PersonaRol modificarPersonaRolPorPerEmpYRol(PersonaRol o) throws DAOException;
}

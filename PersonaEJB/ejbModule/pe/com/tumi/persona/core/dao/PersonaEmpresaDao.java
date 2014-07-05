package pe.com.tumi.persona.core.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.persona.core.domain.PersonaEmpresa;

public interface PersonaEmpresaDao extends TumiDao{
	public PersonaEmpresa grabar(PersonaEmpresa o) throws DAOException;
	public PersonaEmpresa modificar(PersonaEmpresa o) throws DAOException;
	public List<PersonaEmpresa> getListaPersonaEmpresaPorPK(Object o) throws DAOException;
	public List<PersonaEmpresa> getListaPersonaEmpresaPorIdPersona(Object o) throws DAOException;
	public List<PersonaEmpresa> getListaPersonaEmpresaPorIdEmpresa(Object o) throws DAOException;
}

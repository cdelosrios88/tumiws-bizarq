package pe.com.tumi.persona.core.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.persona.core.dao.PersonaRolDao;
import pe.com.tumi.persona.core.domain.PersonaRol;

public class PersonaRolDaoIbatis extends TumiDaoIbatis implements PersonaRolDao{
	
	public PersonaRol grabar(PersonaRol o) throws DAOException {
		PersonaRol dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public PersonaRol modificar(PersonaRol o) throws DAOException {
		PersonaRol dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<PersonaRol> getListaPersonaRolPorPK(Object o) throws DAOException{
		List<PersonaRol> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPK", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<PersonaRol> getListaPersonaRolPorPKPersonaEmpresa(Object o) throws DAOException{
		List<PersonaRol> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPkPersonaEmpresa", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public PersonaRol modificarPersonaRolPorPerEmpYRol(PersonaRol o) throws DAOException {
		PersonaRol dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificarPorPerEmpYRol", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
}
package pe.com.tumi.persona.core.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.persona.core.dao.PersonaEmpresaDao;
import pe.com.tumi.persona.core.domain.PersonaEmpresa;

public class PersonaEmpresaDaoIbatis extends TumiDaoIbatis implements PersonaEmpresaDao{
	
	public PersonaEmpresa grabar(PersonaEmpresa o) throws DAOException {
		PersonaEmpresa dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public PersonaEmpresa modificar(PersonaEmpresa o) throws DAOException {
		PersonaEmpresa dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<PersonaEmpresa> getListaPersonaEmpresaPorPK(Object o) throws DAOException{
		List<PersonaEmpresa> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPK", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<PersonaEmpresa> getListaPersonaEmpresaPorIdPersona(Object o) throws DAOException{
		List<PersonaEmpresa> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorIdPersona", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<PersonaEmpresa> getListaPersonaEmpresaPorIdEmpresa(Object o) throws DAOException{
		List<PersonaEmpresa> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorIdEmpresa", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}
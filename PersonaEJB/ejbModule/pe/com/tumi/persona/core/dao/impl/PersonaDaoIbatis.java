package pe.com.tumi.persona.core.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.persona.core.bo.PersonaBO;
import pe.com.tumi.persona.core.dao.PersonaDao;
import pe.com.tumi.persona.core.domain.Persona;

public class PersonaDaoIbatis extends TumiDaoIbatis implements PersonaDao{
	
	protected  static Logger log = Logger.getLogger(PersonaDaoIbatis.class);
	
	public Persona grabar(Persona o) throws DAOException {
		log.info("-----------------------Debugging PersonaDaoIbatis.grabar-----------------------------");
		Persona dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public Persona modificar(Persona o) throws DAOException {
		Persona dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<Persona> getListaPersonaPorPK(Object o) throws DAOException{
		List<Persona> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPK", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Persona> getListaPersonaBusqueda(Object o) throws DAOException{
		List<Persona> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaBusqueda", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Persona> getListaPersonaPorRuc(Object o) throws DAOException{
		List<Persona> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorRuc", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Persona> getListaPersonaActivaPorIdPersona(Object o) throws DAOException{
		List<Persona> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaActivaPorIdPersona", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}
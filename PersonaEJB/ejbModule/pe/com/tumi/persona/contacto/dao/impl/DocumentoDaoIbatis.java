package pe.com.tumi.persona.contacto.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.persona.contacto.dao.DocumentoDao;
import pe.com.tumi.persona.contacto.domain.Documento;

public class DocumentoDaoIbatis extends TumiDaoIbatis implements DocumentoDao{
	
	public Documento grabar(Documento o) throws DAOException {
		Documento dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public Documento modificar(Documento o) throws DAOException {
		Documento dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<Documento> getListaPorPk(Object o) throws DAOException{
		List<Documento> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPK", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Documento> getListaPorIdPersonaYTipoIdentidad(Object o) throws DAOException{
		List<Documento> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorIdPersonaYTipoIdent", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Documento> getListaPorIdPersona(Object o) throws DAOException{
		List<Documento> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorIdPersona", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}
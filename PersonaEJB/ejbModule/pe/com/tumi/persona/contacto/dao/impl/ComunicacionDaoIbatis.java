package pe.com.tumi.persona.contacto.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.persona.contacto.dao.ComunicacionDao;
import pe.com.tumi.persona.contacto.domain.Comunicacion;

public class ComunicacionDaoIbatis extends TumiDaoIbatis implements ComunicacionDao{
	
	public Comunicacion grabar(Comunicacion o) throws DAOException {
		Comunicacion dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public Comunicacion modificar(Comunicacion o) throws DAOException {
		Comunicacion dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<Comunicacion> getListaComunicacionPorPK(Object o) throws DAOException{
		List<Comunicacion> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPK", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Comunicacion> getListaComunicacionPorIdPersona(Object o) throws DAOException{
		List<Comunicacion> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorIdPersona", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}
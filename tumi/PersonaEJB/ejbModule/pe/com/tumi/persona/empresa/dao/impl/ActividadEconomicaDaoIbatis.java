package pe.com.tumi.persona.empresa.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.persona.empresa.dao.ActividadEconomicaDao;
import pe.com.tumi.persona.empresa.domain.ActividadEconomica;

public class ActividadEconomicaDaoIbatis extends TumiDaoIbatis implements ActividadEconomicaDao{
	
	public ActividadEconomica grabar(ActividadEconomica o) throws DAOException {
		ActividadEconomica dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public ActividadEconomica modificar(ActividadEconomica o) throws DAOException {
		ActividadEconomica dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<ActividadEconomica> getListaActividadEconomicaPorPK(Object o) throws DAOException{
		List<ActividadEconomica> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPK", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<ActividadEconomica> getListaActividadEconomicaPorIdPersona(Object o) throws DAOException{
		List<ActividadEconomica> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorIdPersona", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}
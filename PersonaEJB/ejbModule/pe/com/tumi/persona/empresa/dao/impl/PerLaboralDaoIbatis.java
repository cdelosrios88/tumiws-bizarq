package pe.com.tumi.persona.empresa.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.persona.empresa.dao.ActividadEconomicaDao;
import pe.com.tumi.persona.empresa.dao.PerLaboralDao;
import pe.com.tumi.persona.empresa.domain.ActividadEconomica;
import pe.com.tumi.persona.empresa.domain.PerLaboral;

public class PerLaboralDaoIbatis extends TumiDaoIbatis implements PerLaboralDao{
	
	public PerLaboral grabar(PerLaboral o) throws DAOException {
		PerLaboral dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public PerLaboral modificar(PerLaboral o) throws DAOException {
		PerLaboral dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<PerLaboral> getListaPerLaboralPorPK(Object o) throws DAOException{
		List<PerLaboral> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPK", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<PerLaboral> getListaPerLaboralPorIdPersona(Object o) throws DAOException{
		List<PerLaboral> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorIdPersona", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}
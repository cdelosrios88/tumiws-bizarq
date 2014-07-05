package pe.com.tumi.credito.socio.convenio.dao.impl;

import java.util.List;

import pe.com.tumi.credito.socio.convenio.dao.PoblacionDao;
import pe.com.tumi.credito.socio.convenio.domain.Poblacion;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

public class PoblacionDaoIbatis extends TumiDaoIbatis implements PoblacionDao{
	
	public Poblacion grabar(Poblacion o) throws DAOException {
		Poblacion dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public Poblacion modificar(Poblacion o) throws DAOException {
		Poblacion dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<Poblacion> getListaPoblacionPorPK(Object o) throws DAOException{
		List<Poblacion> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Poblacion> getListaPoblacionPorPKConvenio(Object o) throws DAOException{
		List<Poblacion> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPkConvenio", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
}
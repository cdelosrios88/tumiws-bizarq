package pe.com.tumi.seguridad.empresa.dao.impl;

import java.util.List;

import pe.com.tumi.empresa.domain.Zonal;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.seguridad.empresa.dao.ZonalDao;

public class ZonalDaoIbatis extends TumiDaoIbatis implements ZonalDao{
	
	public Zonal grabar(Zonal o) throws DAOException {
		Zonal dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public Zonal modificar(Zonal o) throws DAOException {
		Zonal dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<Zonal> getListaZonalPorPk(Object o) throws DAOException{
		List<Zonal> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPK", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Zonal> getListaZonalPorIdZonal(Object o) throws DAOException{
		List<Zonal> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorIdZonal", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Zonal> getListaZonalPorIdPersona(Object o) throws DAOException{
		List<Zonal> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorIdPersona", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Zonal> getListaZonalDeBusqueda(Object o) throws DAOException{
		List<Zonal> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaDeBusqueda", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}

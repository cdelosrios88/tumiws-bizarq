package pe.com.tumi.persona.core.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.persona.core.dao.NaturalDao;
import pe.com.tumi.persona.core.domain.Natural;
import pe.com.tumi.persona.core.domain.Persona;

public class NaturalDaoIbatis extends TumiDaoIbatis implements NaturalDao{
	
	public Natural grabar(Natural o) throws DAOException {
		Natural dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public Natural modificar(Natural o) throws DAOException {
		Natural dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<Natural> getListaNaturalPorPK(Object o) throws DAOException{
		List<Natural> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPK", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Natural> getListaNaturalPorInPk(Object o) throws DAOException{
		List<Natural> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorInPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Natural> getNaturalPorTipoIdentidadYNroIdentidad(Object o) throws DAOException{
		List<Natural> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorDocIdentidad", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}

	public List<Natural> getListaPerNaturalBusqueda(Object o) throws DAOException {
		List<Natural> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPerNaturalBusqueda", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Natural> getListaBusqRolODniONomb(Object o) throws DAOException {
		List<Natural> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaBusqRolODniONomb", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}
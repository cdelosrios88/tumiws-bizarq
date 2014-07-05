package pe.com.tumi.credito.socio.estructura.dao.impl;

import java.util.List;

import pe.com.tumi.credito.socio.estructura.dao.PadronDao;
import pe.com.tumi.credito.socio.estructura.domain.Padron;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

public class PadronDaoIbatis extends TumiDaoIbatis implements PadronDao{
	
	public Padron grabar(Padron o) throws DAOException {
		Padron dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public Padron modificar(Padron o) throws DAOException {
		Padron dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<Padron> getListaPadronPorPK(Object o) throws DAOException{
		List<Padron> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}

	public List<Padron> getListaBusqueda(Object o) throws DAOException {
		List<Padron> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaBusqueda", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Padron> getPadronPorLibElectoral(Object o) throws DAOException{
		List<Padron> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getPadronPorLibElectoral", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Padron> getPadronSOLOPorLibElectoral(Object o) throws DAOException{
		List<Padron> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getPadronSOLOPorLibElectoral", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
}
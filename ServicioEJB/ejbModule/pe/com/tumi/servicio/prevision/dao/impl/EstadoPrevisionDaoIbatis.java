package pe.com.tumi.servicio.prevision.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.servicio.prevision.dao.EstadoPrevisionDao;
import pe.com.tumi.servicio.prevision.domain.EstadoPrevision;
import pe.com.tumi.servicio.prevision.domain.ExpedientePrevisionId;

public class EstadoPrevisionDaoIbatis extends TumiDaoIbatis implements EstadoPrevisionDao{
	
	public EstadoPrevision grabar(EstadoPrevision o) throws DAOException {
		EstadoPrevision dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public EstadoPrevision modificar(EstadoPrevision o) throws DAOException {
		EstadoPrevision dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<EstadoPrevision> getListaPorPk(Object o) throws DAOException{
		List<EstadoPrevision> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<EstadoPrevision> getListaPorExpediente(Object o) throws DAOException{
		List<EstadoPrevision> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorExpediente", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	public List<EstadoPrevision> getMaxEstadoPrevisionPorPokExpediente(Object o) throws DAOException{
		List<EstadoPrevision> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getMaxEstadoPrevisionPorPokExpediente", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	
}
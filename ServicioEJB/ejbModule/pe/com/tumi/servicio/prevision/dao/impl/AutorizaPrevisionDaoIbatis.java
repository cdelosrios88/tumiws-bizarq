package pe.com.tumi.servicio.prevision.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.servicio.prevision.domain.AutorizaPrevision;
import pe.com.tumi.servicio.prevision.domain.AutorizaVerificaPrevision;

public class AutorizaPrevisionDaoIbatis extends TumiDaoIbatis implements AutorizaPrevisionDao{
	
	public AutorizaPrevision grabar(AutorizaPrevision o) throws DAOException {
		AutorizaPrevision dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public AutorizaPrevision modificar(AutorizaPrevision o) throws DAOException {
		AutorizaPrevision dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<AutorizaPrevision> getListaPorPk(Object o) throws DAOException{
		List<AutorizaPrevision> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<AutorizaPrevision> getListaPorExpedientePrevision(Object o) throws DAOException{
		List<AutorizaPrevision> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorExpedientePrevision", o);
		}catch(Exception e) {
			System.out.println("getListaPorExpedientePrevisiongetListaPorExpedientePrevision---> "+e);
			throw new DAOException (e);
		}
		return lista;
	}
 
}

package pe.com.tumi.servicio.prevision.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.servicio.prevision.domain.AutorizaVerificaPrevision;

public class AutorizaVerificaPrevisionDaoIbatis extends TumiDaoIbatis implements AutorizaVerificaPrevisionDao{
	
	public AutorizaVerificaPrevision grabar(AutorizaVerificaPrevision o) throws DAOException {
		AutorizaVerificaPrevision dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public AutorizaVerificaPrevision modificar(AutorizaVerificaPrevision o) throws DAOException {
		AutorizaVerificaPrevision dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<AutorizaVerificaPrevision> getListaPorPk(Object o) throws DAOException{
		List<AutorizaVerificaPrevision> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<AutorizaVerificaPrevision> getListaPorExpedientePrevision(Object o) throws DAOException{
		List<AutorizaVerificaPrevision> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorExpedientePrevision", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	

}

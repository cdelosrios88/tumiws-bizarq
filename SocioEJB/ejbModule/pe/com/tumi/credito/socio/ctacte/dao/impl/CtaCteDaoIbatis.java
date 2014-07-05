package pe.com.tumi.credito.socio.ctacte.dao.impl;

import java.util.List;

import pe.com.tumi.credito.socio.ctacte.dao.CtaCteDao;
import pe.com.tumi.credito.socio.ctacte.domain.CtaCte;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

public class CtaCteDaoIbatis extends TumiDaoIbatis implements CtaCteDao{
	
	public CtaCte grabar(CtaCte o) throws DAOException {
		CtaCte dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public CtaCte modificar(CtaCte o) throws DAOException {
		CtaCte dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<CtaCte> getListaCtaCtePorPK(Object o) throws DAOException{
		List<CtaCte> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
}
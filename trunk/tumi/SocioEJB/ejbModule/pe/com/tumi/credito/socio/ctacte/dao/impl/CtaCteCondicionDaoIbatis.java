package pe.com.tumi.credito.socio.ctacte.dao.impl;

import java.util.List;

import pe.com.tumi.credito.socio.ctacte.dao.CtaCteCondicionDao;
import pe.com.tumi.credito.socio.ctacte.domain.CtaCteCondicion;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

public class CtaCteCondicionDaoIbatis extends TumiDaoIbatis implements CtaCteCondicionDao{
	
	public CtaCteCondicion grabar(CtaCteCondicion o) throws DAOException {
		CtaCteCondicion dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public CtaCteCondicion modificar(CtaCteCondicion o) throws DAOException {
		CtaCteCondicion dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<CtaCteCondicion> getListaCtaCteCondicionPorPK(Object o) throws DAOException{
		List<CtaCteCondicion> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
}
package pe.com.tumi.credito.socio.ctacte.dao.impl;

import java.util.List;

import pe.com.tumi.credito.socio.ctacte.dao.LineaDao;
import pe.com.tumi.credito.socio.ctacte.domain.Linea;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

public class LineaDaoIbatis extends TumiDaoIbatis implements LineaDao{
	
	public Linea grabar(Linea o) throws DAOException {
		Linea dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public Linea modificar(Linea o) throws DAOException {
		Linea dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<Linea> getListaLineaPorPK(Object o) throws DAOException{
		List<Linea> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
}
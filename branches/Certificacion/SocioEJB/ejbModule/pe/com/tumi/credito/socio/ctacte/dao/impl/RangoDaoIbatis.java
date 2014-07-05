package pe.com.tumi.credito.socio.ctacte.dao.impl;

import java.util.List;

import pe.com.tumi.credito.socio.ctacte.dao.RangoDao;
import pe.com.tumi.credito.socio.ctacte.domain.Rango;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

public class RangoDaoIbatis extends TumiDaoIbatis implements RangoDao{
	
	public Rango grabar(Rango o) throws DAOException {
		Rango dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public Rango modificar(Rango o) throws DAOException {
		Rango dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<Rango> getListaRangoPorPK(Object o) throws DAOException{
		List<Rango> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
}
package pe.com.tumi.cobranza.cierremensual.dao.impl;

import java.util.List;

import pe.com.tumi.cobranza.cierremensual.dao.CierreCobranzaDao;
import pe.com.tumi.cobranza.cierremensual.domain.CierreCobranza;
import pe.com.tumi.cobranza.cierremensual.domain.composite.CierreCobranzaComp;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

public class CierreCobranzaDaoIbatis extends TumiDaoIbatis implements CierreCobranzaDao{
	
	public List<CierreCobranzaComp> getListaCierreCobranza(Object o) throws DAOException{
		List<CierreCobranzaComp> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaCierreCobranza", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<CierreCobranza> getCierreCobranzaPorPK(Object o) throws DAOException {
		List<CierreCobranza> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getCierreCobranzaPorPK", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public CierreCobranza grabar(CierreCobranza o) throws DAOException {
		CierreCobranza dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public CierreCobranza modificar(CierreCobranza o) throws DAOException {
		CierreCobranza dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
}

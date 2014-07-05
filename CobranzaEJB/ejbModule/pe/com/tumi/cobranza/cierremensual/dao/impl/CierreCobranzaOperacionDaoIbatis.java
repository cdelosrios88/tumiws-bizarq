package pe.com.tumi.cobranza.cierremensual.dao.impl;

import java.util.List;

import pe.com.tumi.cobranza.cierremensual.dao.CierreCobranzaOperacionDao;
import pe.com.tumi.cobranza.cierremensual.domain.CierreCobranza;
import pe.com.tumi.cobranza.cierremensual.domain.CierreCobranzaOperacion;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

public class CierreCobranzaOperacionDaoIbatis extends TumiDaoIbatis implements CierreCobranzaOperacionDao{

	public List<CierreCobranzaOperacion> getListaCierreOperacionPorPkCierreCobranza(Object o) throws DAOException{
		List<CierreCobranzaOperacion> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaCierreOperacionPorPkCierreCobranza", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<CierreCobranzaOperacion> getCierreCobranzaOperacionPorPK(Object o) throws DAOException {
		List<CierreCobranzaOperacion> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getCierreCobranzaOperacionPorPK", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public CierreCobranzaOperacion grabar(CierreCobranzaOperacion o) throws DAOException {
		CierreCobranzaOperacion dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public CierreCobranzaOperacion modificar(CierreCobranzaOperacion o) throws DAOException {
		CierreCobranzaOperacion dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
}

package pe.com.tumi.cobranza.cierremensual.dao.impl;

import java.util.List;

import pe.com.tumi.cobranza.cierremensual.dao.CierreCobranzaPlanillaDao;
import pe.com.tumi.cobranza.cierremensual.domain.CierreCobranzaPlanilla;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

public class CierreCobranzaPlanillaDaoIbatis extends TumiDaoIbatis implements CierreCobranzaPlanillaDao{

	public List<CierreCobranzaPlanilla> getListaCierrePlanillaPorPkCierreCobranza(Object o) throws DAOException{
		List<CierreCobranzaPlanilla> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaCierrePlanillaPorPkCierreCobranza", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<CierreCobranzaPlanilla> getCierreCobranzaPlanillaPorPK(Object o) throws DAOException {
		List<CierreCobranzaPlanilla> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getCierreCobranzaPlanillaPorPK", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<CierreCobranzaPlanilla> getListaCierreCobranzaPlanillaPorCobranza(Object o) throws DAOException {
		List<CierreCobranzaPlanilla> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaCierrePlanillaPorCierreCobranza", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public CierreCobranzaPlanilla grabar(CierreCobranzaPlanilla o) throws DAOException {
		CierreCobranzaPlanilla dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public CierreCobranzaPlanilla modificar(CierreCobranzaPlanilla o) throws DAOException {
		CierreCobranzaPlanilla dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}

	
	public List<CierreCobranzaPlanilla> getCierreCobranzaPlanillaValidarEnvio(Object o) throws DAOException {
		List<CierreCobranzaPlanilla> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getCierreCobranzaPlanillaValidarEnvio", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}

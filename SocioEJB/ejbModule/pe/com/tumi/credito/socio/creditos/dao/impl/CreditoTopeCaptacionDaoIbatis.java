package pe.com.tumi.credito.socio.creditos.dao.impl;

import java.util.List;

import pe.com.tumi.credito.socio.creditos.dao.CondicionHabilDao;
import pe.com.tumi.credito.socio.creditos.dao.CreditoTopeCaptacionDao;
import pe.com.tumi.credito.socio.creditos.domain.CondicionHabil;
import pe.com.tumi.credito.socio.creditos.domain.CreditoTopeCaptacion;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

public class CreditoTopeCaptacionDaoIbatis extends TumiDaoIbatis implements CreditoTopeCaptacionDao{
	
	public CreditoTopeCaptacion grabar(CreditoTopeCaptacion o) throws DAOException {
		CreditoTopeCaptacion dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public CreditoTopeCaptacion modificar(CreditoTopeCaptacion o) throws DAOException {
		CreditoTopeCaptacion dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<CreditoTopeCaptacion> getListaCreditoTopeCaptacionPorPK(Object o) throws DAOException{
		List<CreditoTopeCaptacion> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<CreditoTopeCaptacion> getListaCreditoTopeCaptacionTipoMinMaxPorPK(Object o) throws DAOException{
		List<CreditoTopeCaptacion> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaTipoMinMaxPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<CreditoTopeCaptacion> getListaCondicionHabilPorPKCredito(Object o) throws DAOException{
		List<CreditoTopeCaptacion> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPkCredito", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public void deletePorPk(Object o)throws DAOException{
	    
		try{
	        getSqlMapClientTemplate().delete(getNameSpace() +".deletePorPk", o);
	    }catch(Exception e){
	    	throw new DAOException (e);
	    }
	}
	
}
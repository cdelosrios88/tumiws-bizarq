package pe.com.tumi.servicio.prevision.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.servicio.prevision.domain.AutorizaPrevision;
import pe.com.tumi.servicio.prevision.domain.AutorizaVerificaLiquidacion;
import pe.com.tumi.servicio.prevision.domain.AutorizaVerificaPrevision;

public class AutorizaVerificaLiquidacionDaoIbatis extends TumiDaoIbatis implements AutorizaVerificaLiquidacionDao{
	
	public AutorizaVerificaLiquidacion grabar(AutorizaVerificaLiquidacion o) throws DAOException {
		AutorizaVerificaLiquidacion dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public AutorizaVerificaLiquidacion modificar(AutorizaVerificaLiquidacion o) throws DAOException {
		AutorizaVerificaLiquidacion dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<AutorizaVerificaLiquidacion> getListaPorPk(Object o) throws DAOException{
		List<AutorizaVerificaLiquidacion> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<AutorizaVerificaLiquidacion> getListaPorExpedienteLiquidacion(Object o) throws DAOException{
		List<AutorizaVerificaLiquidacion> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorExpedienteLiquidacion", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
}

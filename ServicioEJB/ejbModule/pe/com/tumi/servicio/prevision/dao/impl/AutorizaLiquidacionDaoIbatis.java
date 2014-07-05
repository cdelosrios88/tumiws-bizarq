package pe.com.tumi.servicio.prevision.dao.impl;

import java.util.List;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.servicio.prevision.domain.AutorizaLiquidacion;

public class AutorizaLiquidacionDaoIbatis extends TumiDaoIbatis implements AutorizaLiquidacionDao{
	
	public AutorizaLiquidacion grabar(AutorizaLiquidacion o) throws DAOException {
		AutorizaLiquidacion dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public AutorizaLiquidacion modificar(AutorizaLiquidacion o) throws DAOException {
		AutorizaLiquidacion dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<AutorizaLiquidacion> getListaPorPk(Object o) throws DAOException{
		List<AutorizaLiquidacion> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<AutorizaLiquidacion> getListaPorExpedienteLiquidacion(Object o) throws DAOException{
		List<AutorizaLiquidacion> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorExpedienteLiquidacion", o);
		}catch(Exception e) {
			System.out.println("getListaPorExpedienteLiquidaciongetListaPorExpedienteLiquidacion---> "+e);
			throw new DAOException (e);
		}
		return lista;
	}
	
	

}

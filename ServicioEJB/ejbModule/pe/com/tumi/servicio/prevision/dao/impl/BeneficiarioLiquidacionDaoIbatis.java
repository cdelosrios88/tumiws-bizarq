package pe.com.tumi.servicio.prevision.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.servicio.prevision.dao.BeneficiarioLiquidacionDao;
import pe.com.tumi.servicio.prevision.domain.BeneficiarioLiquidacion;

public class BeneficiarioLiquidacionDaoIbatis extends TumiDaoIbatis implements BeneficiarioLiquidacionDao{
	
	public BeneficiarioLiquidacion grabar(BeneficiarioLiquidacion o) throws DAOException {
		BeneficiarioLiquidacion dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public BeneficiarioLiquidacion modificar(BeneficiarioLiquidacion o) throws DAOException {
		BeneficiarioLiquidacion dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<BeneficiarioLiquidacion> getListaPorPk(Object o) throws DAOException{
		List<BeneficiarioLiquidacion> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<BeneficiarioLiquidacion> getListaPorExpedienteLiquidacionDetalle(Object o) throws DAOException{
		List<BeneficiarioLiquidacion> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorExpedienteLiquidacionDetalle", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<BeneficiarioLiquidacion> getListaPorEgreso(Object o) throws DAOException{
		List<BeneficiarioLiquidacion> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorEgreso", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<BeneficiarioLiquidacion> getListaPorExpedienteLiquidacion(Object o) throws DAOException{
		List<BeneficiarioLiquidacion> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorExpedienteLiquidacion", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	
	public void deletePorExpediente(Object o) throws DAOException{
		try{
			 getSqlMapClientTemplate().delete(getNameSpace() +".deletePorExpediente", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
	}
	
	
}
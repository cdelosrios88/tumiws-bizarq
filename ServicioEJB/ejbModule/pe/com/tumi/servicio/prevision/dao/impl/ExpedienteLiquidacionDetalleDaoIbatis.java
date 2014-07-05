package pe.com.tumi.servicio.prevision.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.servicio.prevision.dao.ExpedienteLiquidacionDetalleDao;
import pe.com.tumi.servicio.prevision.domain.ExpedienteLiquidacion;
import pe.com.tumi.servicio.prevision.domain.ExpedienteLiquidacionDetalle;

public class ExpedienteLiquidacionDetalleDaoIbatis extends TumiDaoIbatis implements ExpedienteLiquidacionDetalleDao{
	
	public ExpedienteLiquidacionDetalle grabar(ExpedienteLiquidacionDetalle o) throws DAOException {
		ExpedienteLiquidacionDetalle dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public ExpedienteLiquidacionDetalle modificar(ExpedienteLiquidacionDetalle o) throws DAOException {
		ExpedienteLiquidacionDetalle dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<ExpedienteLiquidacionDetalle> getListaPorPk(Object o) throws DAOException{
		List<ExpedienteLiquidacionDetalle> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<ExpedienteLiquidacionDetalle> getListaPorCuenta(Object o) throws DAOException{
		List<ExpedienteLiquidacionDetalle> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorCuenta", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<ExpedienteLiquidacionDetalle> getListaPorExpediente(Object o) throws DAOException{
		List<ExpedienteLiquidacionDetalle> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorExpediente", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}


}
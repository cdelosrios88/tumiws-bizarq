package pe.com.tumi.servicio.prevision.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.servicio.prevision.dao.EstadoLiquidacionDao;
import pe.com.tumi.servicio.prevision.dao.EstadoPrevisionDao;
import pe.com.tumi.servicio.prevision.domain.EstadoLiquidacion;
import pe.com.tumi.servicio.prevision.domain.EstadoPrevision;

public class EstadoLiquidacionDaoIbatis extends TumiDaoIbatis implements EstadoLiquidacionDao{
	
	public EstadoLiquidacion grabar(EstadoLiquidacion o) throws DAOException {
		EstadoLiquidacion dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public EstadoLiquidacion modificar(EstadoLiquidacion o) throws DAOException {
		EstadoLiquidacion dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<EstadoLiquidacion> getListaPorPk(Object o) throws DAOException{
		List<EstadoLiquidacion> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<EstadoLiquidacion> getListaPorExpediente(Object o) throws DAOException{
		List<EstadoLiquidacion> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorExpediente", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<EstadoLiquidacion> getMaxEstadoliquidacionPorPkExpediente(Object o) throws DAOException{
		List<EstadoLiquidacion> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getMaxEstadoLiquidacionPorPkExpediente", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<EstadoLiquidacion> getMinEstadoliquidacionPorPkExpediente(Object o) throws DAOException{
		List<EstadoLiquidacion> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getMinEstadoLiquidacionPorPkExpediente", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}
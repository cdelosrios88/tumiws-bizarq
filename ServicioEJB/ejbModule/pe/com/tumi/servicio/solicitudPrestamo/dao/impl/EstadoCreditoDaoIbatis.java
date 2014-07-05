package pe.com.tumi.servicio.solicitudPrestamo.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.servicio.solicitudPrestamo.dao.EstadoCreditoDao;
import pe.com.tumi.servicio.solicitudPrestamo.domain.EstadoCredito;

public class EstadoCreditoDaoIbatis extends TumiDaoIbatis implements EstadoCreditoDao{
	
	public EstadoCredito grabar(EstadoCredito o) throws DAOException {
		EstadoCredito dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public EstadoCredito modificar(EstadoCredito o) throws DAOException {
		EstadoCredito dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<EstadoCredito> getListaPorPk(Object o) throws DAOException{
		List<EstadoCredito> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<EstadoCredito> getListaPorExpedienteCredito(Object o) throws DAOException{
		List<EstadoCredito> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorExpedienteCredito", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<EstadoCredito> getMaxEstadoCreditoPorPokExpedienteCredito(Object o) throws DAOException{
		List<EstadoCredito> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getMaxEstadoCreditoPorPkExpedienteCredito", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<EstadoCredito> getMinEstadoCreditoPorPkExpedienteCredito(Object o) throws DAOException{
		List<EstadoCredito> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getMinEstadoCreditoPorPkExpedienteCredito", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	/**
	 * AUTOR Y FECHA CREACION: JCHAVEZ / 05-09-2013
	 * OBTENER ESTADO CREDITO POR ID EXPEDIENTECREDITO Y ESTADOCREDITO (OPCIONAL)
	 * @param o
	 * @return
	 * @throws DAOException
	 */
	public List<EstadoCredito> getListaPorExpedienteCreditoPkYEstadoCredito(Object o) throws DAOException{
		List<EstadoCredito> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorExpedienteCreditoPkYEstadoCredito", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}
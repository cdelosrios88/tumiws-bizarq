package pe.com.tumi.servicio.solicitudPrestamo.dao.impl;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.servicio.solicitudPrestamo.dao.GarantiaCreditoDao;
import pe.com.tumi.servicio.solicitudPrestamo.domain.GarantiaCredito;

public class GarantiaCreditoDaoIbatis extends TumiDaoIbatis implements GarantiaCreditoDao{
	
	public GarantiaCredito grabar(GarantiaCredito o) throws DAOException {
		GarantiaCredito dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public GarantiaCredito modificar(GarantiaCredito o) throws DAOException {
		GarantiaCredito dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<GarantiaCredito> getListaPorPk(Object o) throws DAOException{
		List<GarantiaCredito> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<GarantiaCredito> getListaPorExpedienteCredito(Object o) throws DAOException{
		List<GarantiaCredito> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorExpedienteCredito", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public Integer getCantidadPersonasGarantizadasPorPkPersona(Object o) throws DAOException {
		Integer escalar = null;
		try{
			HashMap<String, Object> m = new HashMap<String, Object>();
			getSqlMapClientTemplate().queryForObject(getNameSpace() + ".getCantidadGarantizadosPorPkPersona",o,m);
			escalar = (Integer)m.get("intEscalar");
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return escalar;
	}
	
	public List<GarantiaCredito> getListaGarantiasPorPkPersona(Object o) throws DAOException{
		List<GarantiaCredito> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaGarantiasPorPkPersona", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	/**
	 * AUTOR Y FECHA CREACION: JCHAVEZ / 04-09-2013 
	 * OBTENER LISTA DE GARANTIZADOS
	 * @param o
	 * @return
	 * @throws DAOException
	 */
	public List<GarantiaCredito> getListaGarantiasPorEmpPersCta(Object o) throws DAOException{
		List<GarantiaCredito> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaGarantiasPorEmpPersCta", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}
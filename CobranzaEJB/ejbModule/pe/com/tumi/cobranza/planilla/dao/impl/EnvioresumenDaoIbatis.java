package pe.com.tumi.cobranza.planilla.dao.impl;

import java.util.List;
import java.util.HashMap;
import org.apache.log4j.Logger;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

import pe.com.tumi.cobranza.planilla.dao.EnvioresumenDao;
import pe.com.tumi.cobranza.planilla.domain.Envioresumen;

public class EnvioresumenDaoIbatis extends TumiDaoIbatis implements EnvioresumenDao{

	protected static Logger log = Logger.getLogger(EnvioresumenDaoIbatis.class);
	
	public Envioresumen grabar(Envioresumen o) throws DAOException{
		Envioresumen dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
		
	}

	public Envioresumen modificar(Envioresumen o) throws DAOException{
		Envioresumen dto = null;
		try {
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e){
			throw new DAOException(e);
			
		}
		return dto;
	}  

	public List<Envioresumen> getListaPorPk(Object o) throws DAOException{
		List<Envioresumen> lista = null;
		
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Envioresumen> getListaPorEnitdadyPeriodo(Object o) throws DAOException{
		List<Envioresumen> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorEnitdadyPeriodo", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Envioresumen> getListaEnvioResumen(Object o) throws DAOException{
		List<Envioresumen> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaEnvioResumen", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Envioresumen> getListaSucursal(Object o) throws DAOException{
		log.info("daoibatis.getListaSucursal");
		List<Envioresumen> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaSucursal", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Envioresumen> getListaEnvioResumenUE(Object o) throws DAOException{
		log.info("daoibatis.getListaEnvioResumenUE");
		List<Envioresumen> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaEnvioResumenUE", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public Integer getMaxPeriodoPorEmpresaYEstructuraYTipoSocio(Object o) throws DAOException {
		Integer escalar = null;
		try{
			HashMap<String, Object> m = null;
			getSqlMapClientTemplate().queryForObject(getNameSpace() + ".getMaxPerPorEmpYEstYTipSoc",o);
			m = (HashMap<String, Object>)o;
			escalar = (Integer)m.get("intEscalar");
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return escalar;
	}
	
	public Integer getMaxPeriodoPorEmpresaYEstructuraYTipoSocioM(Object o) throws DAOException {
		Integer escalar = null;
		try{
			HashMap<String, Object> m = null;
			getSqlMapClientTemplate().queryForObject(getNameSpace() + ".getMaxPerPorEmpYEstYTipSocM",o);
			m = (HashMap<String, Object>)o;
			escalar = (Integer)m.get("intEscalar");
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return escalar;
	}
	
	public Integer getMaxPeriodoPorEmpresaYEstructuraYTipoSocioCAS(Object o) throws DAOException {
		Integer escalar = null;
		try{
			HashMap<String, Object> m = null;
			getSqlMapClientTemplate().queryForObject(getNameSpace() + ".getMaxPerPorEmpYEstYTipSocCAS",o);
			m = (HashMap<String, Object>)o;
			escalar = (Integer)m.get("intEscalar");
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return escalar;
	}

	@Override
	public List<Envioresumen> getListaEnvioREfectuadoConArchivo(Object o)
			throws DAOException {
		List<Envioresumen> lista = null;
		try
		{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getEfectuadoConArchivo", o);
		}catch(Exception e)
		{
			throw new DAOException(e);
		}
		return lista;
	}
	public List<Envioresumen> getListEnvRes(Object o) throws DAOException{
		List<Envioresumen> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListEnvRes", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}	


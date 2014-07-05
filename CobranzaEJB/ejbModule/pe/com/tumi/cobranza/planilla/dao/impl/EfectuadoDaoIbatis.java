package pe.com.tumi.cobranza.planilla.dao.impl;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

import pe.com.tumi.cobranza.planilla.dao.EfectuadoDao;
import pe.com.tumi.cobranza.planilla.domain.Efectuado;
import pe.com.tumi.cobranza.planilla.domain.EfectuadoConcepto;

public class EfectuadoDaoIbatis extends TumiDaoIbatis implements EfectuadoDao{

	public Efectuado grabar(Efectuado o) throws DAOException{
		Efectuado dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public Efectuado modificar(Efectuado o) throws DAOException{
		Efectuado dto = null;
		try {
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e){
			throw new DAOException(e);
		}
		return dto;
	}  

	public List<Efectuado> getListaPorPk(Object o) throws DAOException{
		List<Efectuado> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Efectuado> getListaPorIdEmpresaYPkEstructuraYTipoModalidadYPeriodo(Object o) throws DAOException {
		List<Efectuado> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getLsPorEmpYPKEstYTipModYPerio",o);
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return lista;
	}
	
	public Integer getMaxPeriodoPorEmpresaYEstructuraYTipoSocioYModalidad(Object o) throws DAOException {
		Integer escalar = null;
		try{
			HashMap<String, Object> m = null;
			getSqlMapClientTemplate().queryForObject(getNameSpace() + ".getMaxPerPorEmpYEstYTipSocYMod",o);
			m = (HashMap<String, Object>)o;
			escalar = (Integer)m.get("intEscalar");
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return escalar;
	}
	
	public Integer getMaxPeriodoPorEmpresaYEstructuraYTipoSocioYModalidadTipoEstrucura(Object o) throws DAOException {
		Integer escalar = null;
		try{
			HashMap<String, Object> m = null;
			getSqlMapClientTemplate().queryForObject(getNameSpace() + ".getMaxPerPorEmpYEstYTipSocYModTipEst",o);
			m = (HashMap<String, Object>)o;
			escalar = (Integer)m.get("intEscalar");
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return escalar;
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
	
	 /** CREADO 06/08/2013 
     * SE OBTINIE EFECTUADO POR PK DE ENVIOMONTO Y PERIODO
     * **/	
	public List<Efectuado> getListaEfectuadoPorPkEnviomontoYPeriodo(Object o) throws DAOException{
		List<Efectuado> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaEfectuadoPorPkEnviomontoYPeriodo", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Efectuado> getListaEfectuadoXNiveCodigoModaliPeriodoTipoSocio(Object o) throws DAOException {
		List<Efectuado> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getLsPorEmpYPKEstYTipModYPerioTipoSocio",o);
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return lista;
	}
	
	public List<Efectuado> getEfectuadoPorItemEnvioConcepto(Object o) throws DAOException{
		List<Efectuado> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getPorItemEnvioConcepto", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}	
	
	/**
	 * Recupera los Efectuados en base a la Empresa, Cuenta y peridodo en cualquier estado.
	 * @param o
	 * @return
	 * @throws DAOException
	 */
	public List<Efectuado> getListaPorEmpCtaPeriodo(Object o) throws DAOException{
		List<Efectuado> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorEmpCtaPeriodo", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Efectuado> getListaPorAdministra(Object o) throws DAOException
	{
		List<Efectuado> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorAdministra",o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Efectuado> getMontoTotalPorConcepto(Object o) throws DAOException
	{
		List<Efectuado> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getMontoTotalPorConcepto",o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public Integer getNumerodeRolesUsuarios(Object o) throws DAOException
	{
		Integer escalar = null;
		try
		{
			HashMap<String, Object> m = null;
			getSqlMapClientTemplate().queryForObject(getNameSpace() + ".getNumerodeRolesUsuarios",o);
			m = (HashMap<String, Object>)o;
			escalar = (Integer)m.get("intEscalar");
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return escalar;
	}
	

}	

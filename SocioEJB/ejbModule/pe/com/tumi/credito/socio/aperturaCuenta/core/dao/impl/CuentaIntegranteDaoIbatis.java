package pe.com.tumi.credito.socio.aperturaCuenta.core.dao.impl;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.credito.socio.aperturaCuenta.core.dao.CuentaIntegranteDao;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.CuentaIntegrante;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

public class CuentaIntegranteDaoIbatis extends TumiDaoIbatis implements CuentaIntegranteDao{
	
	public CuentaIntegrante grabar(CuentaIntegrante o) throws DAOException {
		CuentaIntegrante dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public CuentaIntegrante modificar(CuentaIntegrante o) throws DAOException {
		CuentaIntegrante dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<CuentaIntegrante> getListaCuentaIntegrantePorPK(Object o) throws DAOException{
		List<CuentaIntegrante> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<CuentaIntegrante> getListaCuentaIntegrantePorPKCuenta(Object o) throws DAOException{
		List<CuentaIntegrante> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPkCuenta", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	/**
	 * Recupera Lista CuentaIntegrante cuyo estado de cuenta sea 1 o 3
	 */
	public List<CuentaIntegrante> getListaCuentaIntegrantePorPKSocio(Object o) throws DAOException{
		List<CuentaIntegrante> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPkSocio", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<CuentaIntegrante> getListaPorPKSocioYTipoCuentaYTipoIntegrante(Object o) throws DAOException{
		List<CuentaIntegrante> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getLstPorPkSocioYTipCtaYTipInt", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public String getCsvPersonaPorEmpresaYTipoIntegranteYINCuenta(Object o) throws DAOException {
		String escalar = null;
		try{
			HashMap<String, Object> m = null;
			getSqlMapClientTemplate().queryForObject(getNameSpace() + ".getCsvPersonaPorEmpYIntYINCta",o);
			m = (HashMap<String, Object>)o;
			escalar = (String)m.get("strEscalar");
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return escalar;
	}
	
	public List<CuentaIntegrante> getListaCuentaIntegrantePorPersona(Object o) throws DAOException{
		List<CuentaIntegrante> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPersona", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<CuentaIntegrante> getListaCuentaIntegrantePorCuenta(Object o) throws DAOException{
		List<CuentaIntegrante> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaCuentaIntegrantePorCuenta", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<CuentaIntegrante> getListaIntegrantesPorPKPersona(Object o) throws DAOException{
		List<CuentaIntegrante> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaIntegrantesPorPkPersona", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	//Creado 24/07/2013
	public List<CuentaIntegrante> getListaPorPKSocioYTipoCuenta(Object o) throws DAOException{
		List<CuentaIntegrante> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getLstPorPkSocioYTipCta", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	
	/**
	 * Recupera la Cuenta Integrante es esatdo Activo en base a Empresa y Persona.
	 * @param o
	 * @return
	 * @throws DAOException
	 */
	public List<CuentaIntegrante> getIdCtaActivaXPkSocio(Object o) throws DAOException{
		List<CuentaIntegrante> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getIdCtaActivaXPkSocio", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	/**
	 * AUTOR Y FECHA CREACION: JCHAVEZ / 04.10.2013
	 * Recupera Cta. Integrante por Empresa, Persona Integrante, Tipo Cta. y Situacion Cta.
	 */
	public List<CuentaIntegrante> getLstPorSocioPKTipoCtaSituacionCta(Object o) throws DAOException{
		List<CuentaIntegrante> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getLstPorSocioPKTipoCtaSituacionCta", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<CuentaIntegrante> getCodPersonaOfCobranza(Object o)throws DAOException
	{
		List<CuentaIntegrante> lista = null;
		try
		{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getCodPersonaOfCobranza", o);
		}catch(Exception e)
		{
			throw new DAOException(e);
		}
		return lista;
	}
}
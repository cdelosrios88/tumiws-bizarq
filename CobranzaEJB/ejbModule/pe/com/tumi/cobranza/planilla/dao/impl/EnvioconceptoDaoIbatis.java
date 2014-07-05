package pe.com.tumi.cobranza.planilla.dao.impl;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

import pe.com.tumi.cobranza.planilla.dao.EnvioconceptoDao;
import pe.com.tumi.cobranza.planilla.domain.Envioconcepto;

public class EnvioconceptoDaoIbatis extends TumiDaoIbatis implements EnvioconceptoDao{

	public Envioconcepto grabar(Envioconcepto o) throws DAOException{
		Envioconcepto dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}

	public Envioconcepto grabarSub(Envioconcepto o) throws DAOException{
		Envioconcepto dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabarSub", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public Envioconcepto modificar(Envioconcepto o) throws DAOException{
		Envioconcepto dto = null;
		try {
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e){
			throw new DAOException(e);
		}
		return dto;
	}  

	public List<Envioconcepto> getListaPorPk(Object o) throws DAOException{
		List<Envioconcepto> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Envioconcepto> getListaPorMaxPeriodo(Object o) throws DAOException{
		List<Envioconcepto> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorMaxPeriodo", o);
		}catch(Exception e) {
			throw new DAOException (e);
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
	
	public String getCsvCuentaPorEmpresaYTipoSocioYModalidadYPeriodo(Object o) throws DAOException {
		String escalar = null;
		try{
			HashMap<String, Object> m = null;
			getSqlMapClientTemplate().queryForObject(getNameSpace() + ".getCsvCtaPorEmpYTipSocYModYPer",o);
			m = (HashMap<String, Object>)o;
			escalar = (String)m.get("strEscalar");
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return escalar;
	}
	
	public Integer getMaxPeriodoPorEmpresaYEstructuraYTipoSocioYModalidadTipoEstru(Object o) throws DAOException {
		Integer escalar = null;
		try{
			HashMap<String, Object> m = null;
			getSqlMapClientTemplate().queryForObject(getNameSpace() + ".getMaxPerTipSocYModYTipEstr",o);
			m = (HashMap<String, Object>)o;
			escalar = (Integer)m.get("intEscalar");
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return escalar;
	}
	
	public List<Envioconcepto> getListaPorEmpresaPeriodoYNroCta(Object o) throws DAOException{
		List<Envioconcepto> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorEmpresaPeriodoYNroCta", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Envioconcepto> getListaPorEmpresaNroCta(Object o) throws DAOException{
		List<Envioconcepto> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorEmpresaNroCta", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	
	/**
	 * Recupera los envios por Periodo planilla, Cuenta, ItemCuenta Concepto y estado. 
	 * @param o
	 * @return
	 * @throws DAOException
	 */
	public List<Envioconcepto> getListaXPerCtaItemCto(Object o) throws DAOException{
		List<Envioconcepto> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaXPerCtaItemCto", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	@Override
	public List<Envioconcepto> getEnvioconceptoPorItemEnvioConcepto(Object o) throws DAOException{
		List<Envioconcepto> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getEnvioconceptoPorItemEnvioConcepto", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	/** CREADO 05/08/2013 **/
	public List<Envioconcepto> getListaEnvioconceptoPorPkExpedienteCredito(Object o) throws DAOException{
		List<Envioconcepto> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaEnvioconceptoPorPkExpedienteCredito", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	/** CREADO 08/08/2013 **/
	public List<Envioconcepto> getListaEnvioconceptoPorCtaCptoDetYPer(Object o) throws DAOException{
		List<Envioconcepto> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaEnvioconceptoPorCtaCptoDetalleYPer", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	/**
	 * AUTOR Y FECHA CREACION: JCHAVEZ / 09-09-2013 
	 * OBTENER LISTA ENVIOCONCEPTO POR CUENTA Y PERIODO
	 */
	public List<Envioconcepto> getListaEnvioconceptoPorCtaYPer(Object o) throws DAOException{
		List<Envioconcepto> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaEnvioconceptoPorCtaYPer", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	/**
	 * AUTOR Y FECHA CREACION: JCHAVEZ / 10-09-2013 
	 * OBTENER LISTA ENVIOCONCEPTO POR CUENTA, DEL PERIODO INGRESADO EN ADELANTE (DISTINCT POR PERIODO)
	 */
	public List<Envioconcepto> getListaPorCuentaYPeriodo(Object o) throws DAOException{
		List<Envioconcepto> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorCuentaYPeriodo", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	
	/**
	 * 
	 * @param intEmpresa
	 * @param intPeriodo
	 * @param nroCta
	 * @param intEstado
	 * @return
	 * @throws BusinessException
	 */
	public List<Envioconcepto> getListaPorEmpPerCta(Object o) throws DAOException{
		List<Envioconcepto> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorEmpPerCta", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
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
	
	public List<Envioconcepto> getListaPorEmpresaPeriodoYNroCtaNivelCodigo(Object o) throws DAOException{
		List<Envioconcepto> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorEmpresaPeriodoYNroCtaNivelCodigo", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	

	/**
	 * 
	 * @param o
	 * @return
	 * @throws DAOException
	 */
	public List<Envioconcepto> getListaEnvioMinimoPorEmpCtaYEstado(Object o) throws DAOException{
		List<Envioconcepto> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaEnvioMinimoPorEmpCtaYEstado", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}	
	  

	public List<Envioconcepto> getCuentaOfConArchivo(Object o) throws DAOException{
		List<Envioconcepto> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getCuentaOfConArchivo", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}

	public List<Envioconcepto> getListaCuentaEnConArchivo(Object o) throws DAOException{
		List<Envioconcepto> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaCuentaEnConArchivo", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}	

package pe.com.tumi.movimiento.concepto.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.movimiento.concepto.dao.MovimientoDao;
import pe.com.tumi.movimiento.concepto.domain.Movimiento;

public class MovimientoDaoIbatis extends TumiDaoIbatis implements MovimientoDao{
	
	public Movimiento grabar(Movimiento o) throws DAOException {
		Movimiento dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public Movimiento modificar(Movimiento o) throws DAOException {
		Movimiento dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<Movimiento> getListaPorPK(Object o) throws DAOException{
		List<Movimiento> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPK", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	
	public List<Movimiento> getListaMovimientoPorCuentaEmpresaConcepto(Object o) throws DAOException{
		List<Movimiento> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorCuentaEmpresaConcepto", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
		
	}
	public List<Movimiento> getListXCtaYPersYCptoGeneral(Object o) throws DAOException{
		List<Movimiento> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListXCtaYPersYCptoGeneral", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
		
	}
	
	public List<Movimiento> getListPeriodoMaxCuentaEmpresa(Object o) throws DAOException{
		List<Movimiento> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListPeriodoMaxCuentaEmpresa", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;	
	}
	
	public List<Movimiento> getListXCtaExpedienteYTipoMov(Object o) throws DAOException{
		List<Movimiento> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListXCtaExpedienteYTipoMov", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
		
	}
	
	public List<Movimiento> getListXCtaExpediente(Object o) throws DAOException{
		List<Movimiento> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListXCtaExpediente", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
		
	}
	
	public List<Movimiento> getListaMaximoMovimiento(Object o) throws DAOException{
		List<Movimiento> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaMaximoMovimiento", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	
	public List<Movimiento> getListaMovimientoPorCtaItemConceptoTipoConceptoGeneral(Object o) throws DAOException{
		List<Movimiento> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaMovimientoPorCtaItemConceptoTipoConceptoGeneral", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;

	}
	
	
	/**
	 * Recupera el ultimo moviento en base al id de cuentaconcepto
	 * @param o
	 * @return
	 * @throws DAOException
	 */
	public List<Movimiento> getListaMaximoMovimientoPorCuentaConcepto(Object o) throws DAOException{
		List<Movimiento> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaMaximoMovimientoPorCuentaConcepto", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
		
	}
	/**
	 * CREADO 15-08-2013
	 * OBTENER MOVIMIENTOCTACTO POR PK EXPEDIENTECREDITO
	 **/
	public List<Movimiento> getListaMovimientoPorExpedienteCredito(Object o) throws DAOException{
		List<Movimiento> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaMovimientoPorExpedienteCredito", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
		
	}
	/**
	 * CREADO 20-08-2013
	 * OBTENER MOVIMIENTOCTACTO POR NRO DE CUENTA Y EMPRESA 
	 * (ORDENADOS POR FECHAMOVIMIENTO Y TIPOMOVIMIENTO)
	 **/
	public List<Movimiento> getListXCuentaYEmpresa(Object o) throws DAOException{
		List<Movimiento> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListXCuentaYEmpresa", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
		
	}
	
	/**
	 * 
	 */
	public List<Movimiento> getMaxMovXCtaEmpresaTipoMov(Object o) throws DAOException{
		List<Movimiento> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getMaxMovXCtaEmpresaTipoMov", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
		
	}
	
	
	/**
	 * Recupera el ultimo registro por id de expediente y  tipo concetpogeneral
	 * @param o
	 * @return
	 * @throws DAOException
	 */
	public List<Movimiento> getMaxXExpYCtoGral(Object o) throws DAOException{
		List<Movimiento> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getMaxXExpYCtoGral", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
		
	}
	/**
	 * Agregado 19.05.2014 jchavez
	 * @param o
	 * @return
	 * @throws DAOException
	 */
	public List<Movimiento> getListaMovVtaCtePorPagar(Object o) throws DAOException{
		List<Movimiento> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaMovVtaCtePorPagar", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	/**
	 * Recupera el ultimo registro por id de expediente y  tipo concetpogeneral
	 * @param o
	 * @return
	 * @throws DAOException
	 */
	//rVillarreal
	public List<Movimiento> getMaxMovCtaCteXFecha(Object o) throws DAOException{
		List<Movimiento> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getMaxMovCtaCteXFecha", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
		
	}

	/**
	 * Agregado 05.06.2014 jchavez
	 * @param o
	 * @return
	 * @throws DAOException
	 */
	public List<Movimiento> getListaMovCtaCtePorPagarLiq(Object o) throws DAOException{
		List<Movimiento> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaMovCtaCtePorPagarLiq", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}
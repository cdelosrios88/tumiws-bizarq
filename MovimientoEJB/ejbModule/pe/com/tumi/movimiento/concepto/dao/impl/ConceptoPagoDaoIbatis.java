package pe.com.tumi.movimiento.concepto.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.movimiento.concepto.dao.ConceptoDetallePagoDao;
import pe.com.tumi.movimiento.concepto.dao.ConceptoPagoDao;
import pe.com.tumi.movimiento.concepto.domain.ConceptoPago;


public class ConceptoPagoDaoIbatis extends TumiDaoIbatis implements ConceptoPagoDao {
	
	public ConceptoPago grabar(ConceptoPago o) throws DAOException {
		ConceptoPago dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public ConceptoPago modificar(ConceptoPago o) throws DAOException {
		ConceptoPago dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<ConceptoPago> getListaPorPK(Object o) throws DAOException{
		List<ConceptoPago> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPK", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	
	public List<ConceptoPago> getListaConceptoPagoPorCuentaConceptoDet(Object o) throws DAOException{
		List<ConceptoPago> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaConceptoPagoPorCuentaConceptoDet", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	/**
	 * AUTOR Y FECHA CREACION: JCHAVEZ / 25-09-2013 
	 * OBTENER CONCEPTO PAGO POR CUENTA CONCEPTO DETALLE Y PERIODO
	 */
	public List<ConceptoPago> getListaConceptoPagoPorCtaCptoDetYPeriodo(Object o) throws DAOException{
		List<ConceptoPago> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaConceptoPagoPorCtaCptoDetYPeriodo", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	/**
	 * JCHAVEZ 10.01.2014
	 * Recupera el ultimo registro de Concepto Pago por Pk Cuenta Concepto Detalle
	 */
	public List<ConceptoPago> getUltimoCptoPagoPorCuentaConceptoDet(Object o) throws DAOException{
		List<ConceptoPago> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getUltimoCptoPagoPorCuentaConceptoDet", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<ConceptoPago> getListaConceptoPagotoCobranza(Object o) throws DAOException{
		List<ConceptoPago> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaConceptoPagotoCobranza", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}

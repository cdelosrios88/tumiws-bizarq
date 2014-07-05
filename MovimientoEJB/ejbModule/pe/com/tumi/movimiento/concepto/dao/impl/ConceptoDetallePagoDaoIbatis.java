package pe.com.tumi.movimiento.concepto.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.movimiento.concepto.dao.ConceptoDetallePagoDao;
import pe.com.tumi.movimiento.concepto.domain.ConceptoDetallePago;

public class ConceptoDetallePagoDaoIbatis extends TumiDaoIbatis implements ConceptoDetallePagoDao {
	
	public ConceptoDetallePago grabar(ConceptoDetallePago o) throws DAOException {
		ConceptoDetallePago dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public ConceptoDetallePago modificar(ConceptoDetallePago o) throws DAOException {
		ConceptoDetallePago dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<ConceptoDetallePago> getListaPorPK(Object o) throws DAOException{
		List<ConceptoDetallePago> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPK", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	/**
	 * AUTOR Y FECHA CREACION: JCHAVEZ / 27-09-2013 
	 * OBTENER CONCEPTO DETALLE PAGO POR CONCEPTO PAGO PK 
	 * @param o
	 * @return
	 * @throws DAOException
	 */
	public List<ConceptoDetallePago> getListaPorCptoPagoPK(Object o) throws DAOException{
		List<ConceptoDetallePago> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorCptoPagoPK", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}

}

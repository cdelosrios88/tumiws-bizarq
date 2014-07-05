package pe.com.tumi.movimiento.concepto.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.movimiento.concepto.domain.ConceptoPago;

public interface ConceptoPagoDao extends TumiDao{
	public ConceptoPago grabar(ConceptoPago o) throws DAOException;
	public ConceptoPago modificar(ConceptoPago o) throws DAOException;
	public List<ConceptoPago> getListaPorPK(Object o) throws DAOException;
	public List<ConceptoPago> getListaConceptoPagoPorCuentaConceptoDet(Object o) throws DAOException;
	//AUTOR Y FECHA CREACION: JCHAVEZ / 25-09-2013
 	public List<ConceptoPago> getListaConceptoPagoPorCtaCptoDetYPeriodo(Object o)throws DAOException;
 	//JCHAVEZ 10.01.2014
 	public List<ConceptoPago> getUltimoCptoPagoPorCuentaConceptoDet(Object o) throws DAOException;
 	
 	public List<ConceptoPago> getListaConceptoPagotoCobranza(Object o) throws DAOException;
}

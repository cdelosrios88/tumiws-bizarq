package pe.com.tumi.movimiento.concepto.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.movimiento.concepto.domain.ConceptoDetallePago;

public interface ConceptoDetallePagoDao extends TumiDao{
	public ConceptoDetallePago grabar(ConceptoDetallePago o) throws DAOException;
	public ConceptoDetallePago modificar(ConceptoDetallePago o) throws DAOException;
	public List<ConceptoDetallePago> getListaPorPK(Object o) throws DAOException;
	//AUTOR Y FECHA CREACION: JCHAVEZ / 27-09-2013
	public List<ConceptoDetallePago> getListaPorCptoPagoPK(Object o) throws DAOException;
}

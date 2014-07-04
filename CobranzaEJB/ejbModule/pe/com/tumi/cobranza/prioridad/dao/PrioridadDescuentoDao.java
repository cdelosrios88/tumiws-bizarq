package pe.com.tumi.cobranza.prioridad.dao;

import java.util.List;

import pe.com.tumi.cobranza.prioridad.domain.PrioridadDescuento;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;

public interface PrioridadDescuentoDao extends TumiDao {
	public List<PrioridadDescuento> getListaPorConceptoGnral(Object o) throws DAOException;
	//AUTOR Y FECHA CREACION: JCHAVEZ / 11-09-2013
	public List<PrioridadDescuento> getListaPorTipoCptoGralCtaCptoExpediente(Object o) throws DAOException;
}

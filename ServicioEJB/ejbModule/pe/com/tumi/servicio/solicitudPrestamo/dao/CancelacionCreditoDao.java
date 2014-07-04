package pe.com.tumi.servicio.solicitudPrestamo.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.servicio.solicitudPrestamo.domain.CancelacionCredito;

public interface CancelacionCreditoDao extends TumiDao{
	public List<CancelacionCredito> getListaPorExpedienteCredito(Object o) throws DAOException;
	public List<CancelacionCredito> getListaPorPk(Object o) throws DAOException;
	public CancelacionCredito modificar(CancelacionCredito o) throws DAOException;
	public CancelacionCredito grabar(CancelacionCredito o) throws DAOException;
}

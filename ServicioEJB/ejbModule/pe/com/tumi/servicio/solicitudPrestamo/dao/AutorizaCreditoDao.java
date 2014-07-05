package pe.com.tumi.servicio.solicitudPrestamo.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.servicio.solicitudPrestamo.domain.AutorizaCredito;

public interface AutorizaCreditoDao extends TumiDao{
	public AutorizaCredito grabar(AutorizaCredito o) throws DAOException;
	public AutorizaCredito modificar(AutorizaCredito o) throws DAOException;
	public List<AutorizaCredito> getListaPorPk(Object o) throws DAOException;
	public List<AutorizaCredito> getListaPorExpedienteCredito(Object o) throws DAOException;
}

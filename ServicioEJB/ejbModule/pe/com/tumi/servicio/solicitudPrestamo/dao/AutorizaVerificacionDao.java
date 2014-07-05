package pe.com.tumi.servicio.solicitudPrestamo.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.servicio.solicitudPrestamo.domain.AutorizaVerificacion;

public interface AutorizaVerificacionDao extends TumiDao{
	public AutorizaVerificacion grabar(AutorizaVerificacion o) throws DAOException;
	public AutorizaVerificacion modificar(AutorizaVerificacion o) throws DAOException;
	public List<AutorizaVerificacion> getListaPorPk(Object o) throws DAOException;
	public List<AutorizaVerificacion> getListaPorExpedienteCredito(Object o) throws DAOException;
}

package pe.com.tumi.tesoreria.egreso.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.tesoreria.egreso.domain.SolicitudPersonalPago;

public interface SolicitudPersonalPagoDao extends TumiDao{
	public SolicitudPersonalPago grabar(SolicitudPersonalPago o) throws DAOException;
	public SolicitudPersonalPago modificar(SolicitudPersonalPago o) throws DAOException;
	public List<SolicitudPersonalPago> getListaPorPk(Object o) throws DAOException;
	public List<SolicitudPersonalPago> getListaPorSolicitudPersonal(Object o) throws DAOException;
}
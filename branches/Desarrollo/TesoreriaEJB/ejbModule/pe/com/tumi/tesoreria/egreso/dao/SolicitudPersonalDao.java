package pe.com.tumi.tesoreria.egreso.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.tesoreria.egreso.domain.SolicitudPersonal;

public interface SolicitudPersonalDao extends TumiDao{
	public SolicitudPersonal grabar(SolicitudPersonal o) throws DAOException;
	public SolicitudPersonal modificar(SolicitudPersonal o) throws DAOException;
	public List<SolicitudPersonal> getListaPorPk(Object o) throws DAOException;
	public List<SolicitudPersonal> getListaPorBuscar(Object o) throws DAOException;
}
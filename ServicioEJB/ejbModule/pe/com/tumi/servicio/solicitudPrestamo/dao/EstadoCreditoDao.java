package pe.com.tumi.servicio.solicitudPrestamo.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.servicio.solicitudPrestamo.domain.EstadoCredito;

public interface EstadoCreditoDao extends TumiDao{
	public EstadoCredito grabar(EstadoCredito o) throws DAOException;
	public EstadoCredito modificar(EstadoCredito o) throws DAOException;
	public List<EstadoCredito> getListaPorPk(Object o) throws DAOException;
	public List<EstadoCredito> getListaPorExpedienteCredito(Object o) throws DAOException;
	public List<EstadoCredito> getMaxEstadoCreditoPorPokExpedienteCredito(Object o) throws DAOException;
	public List<EstadoCredito> getMinEstadoCreditoPorPkExpedienteCredito(Object o) throws DAOException;
	//AUTOR Y FECHA CREACION: JCHAVEZ / 05-09-2013
	public List<EstadoCredito> getListaPorExpedienteCreditoPkYEstadoCredito(Object o) throws DAOException;	
}

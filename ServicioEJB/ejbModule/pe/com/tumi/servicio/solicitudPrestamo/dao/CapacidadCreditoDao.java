package pe.com.tumi.servicio.solicitudPrestamo.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.servicio.solicitudPrestamo.domain.CapacidadCredito;

public interface CapacidadCreditoDao extends TumiDao{
	public CapacidadCredito grabar(CapacidadCredito o) throws DAOException;
	public CapacidadCredito modificar(CapacidadCredito o) throws DAOException;
	public List<CapacidadCredito> getListaPorPk(Object o) throws DAOException;
	public List<CapacidadCredito> getListaPorPkExpedienteCredito(Object o) throws DAOException;
	public List<CapacidadCredito> getListaPorPkExpedienteYSocioEstructura(Object o) throws DAOException;
	public void deletePorPk(Object o)throws DAOException;
}

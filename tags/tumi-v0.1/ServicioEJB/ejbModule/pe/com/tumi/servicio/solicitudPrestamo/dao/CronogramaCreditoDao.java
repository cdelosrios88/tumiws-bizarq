package pe.com.tumi.servicio.solicitudPrestamo.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.servicio.solicitudPrestamo.domain.CronogramaCredito;

public interface CronogramaCreditoDao extends TumiDao{
	public CronogramaCredito grabar(CronogramaCredito o) throws DAOException;
	public CronogramaCredito modificar(CronogramaCredito o) throws DAOException;
	public List<CronogramaCredito> getListaPorPk(Object o) throws DAOException;
	public List<CronogramaCredito> getListaPorPkExpedienteCredito(Object o) throws DAOException;
	public void deletePorPk(Object o) throws DAOException;
	public List<CronogramaCredito> getListaPorPkExpedienteCuota(Object o) throws DAOException;
	
}

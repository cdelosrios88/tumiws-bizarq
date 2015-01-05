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
	//Autor: jchavez / Tarea: Creación / Fecha: 02.09.2014
	public List<CronogramaCredito> getListaPorPkExpCredYPeriodo(Object o) throws DAOException;
}

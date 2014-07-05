package pe.com.tumi.servicio.solicitudPrestamo.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.servicio.solicitudPrestamo.domain.RequisitoCredito;

public interface RequisitoCreditoDao extends TumiDao{
	public RequisitoCredito grabar(RequisitoCredito o) throws DAOException;
	public RequisitoCredito modificar(RequisitoCredito o) throws DAOException;
	public List<RequisitoCredito> getListaPorPk(Object o) throws DAOException;
	public List<RequisitoCredito> getListaPorExpedienteCredito(Object o) throws DAOException;
	//JCHAVEZ 03.02.2014
	public List<RequisitoCredito> getListaPorPkExpedienteCreditoYRequisitoDetalle(Object o) throws DAOException;
}

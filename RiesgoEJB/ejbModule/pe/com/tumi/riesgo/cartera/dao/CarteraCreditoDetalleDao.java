package pe.com.tumi.riesgo.cartera.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.riesgo.cartera.domain.CarteraCreditoDetalle;
import pe.com.tumi.riesgo.cartera.domain.composite.CarteraCreditoManual;
import pe.com.tumi.riesgo.cartera.domain.composite.CarteraCreditoManualGen;

public interface CarteraCreditoDetalleDao extends TumiDao {
	public List<CarteraCreditoDetalle> getListaPorCarteraCredito(Object o)
			throws DAOException;

	// AUTOR Y FECHA CREACION: JCHAVEZ / 06-09-2013
	public List<CarteraCreditoDetalle> getListaXExpOCtaCpto(Object o)
			throws DAOException;

	public CarteraCreditoDetalle getMaxPorExpediente(Object o)
			throws DAOException;

	// GTorresBrousset - 31.mar.2014
	public List<CarteraCreditoManual> buscaCarteraManual(Object o)
			throws DAOException;

	public CarteraCreditoManual obtenerCarteraManual(Object o)
			throws DAOException;

	public CarteraCreditoManualGen generarCarteraManual(Object o)
			throws DAOException;

	public CarteraCreditoManual cerrarCarteraManual(Object o)
			throws DAOException;

	public void generarLibroCartera() throws DAOException;
}

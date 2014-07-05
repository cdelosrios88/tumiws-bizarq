package pe.com.tumi.riesgo.cartera.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.riesgo.cartera.dao.CarteraCreditoDetalleDao;
import pe.com.tumi.riesgo.cartera.domain.CarteraCreditoDetalle;
import pe.com.tumi.riesgo.cartera.domain.composite.CarteraCreditoManual;
import pe.com.tumi.riesgo.cartera.domain.composite.CarteraCreditoManualGen;

public class CarteraCreditoDetalleDaoIbatis extends TumiDaoIbatis implements
		CarteraCreditoDetalleDao {
	@SuppressWarnings("unchecked")
	public List<CarteraCreditoDetalle> getListaPorCarteraCredito(Object o)
			throws DAOException {
		List<CarteraCreditoDetalle> lista = null;
		try {
			lista = (List<CarteraCreditoDetalle>) getSqlMapClientTemplate()
					.queryForList(
							getNameSpace() + ".getListaPorCarteraCredito", o);
		} catch (Exception e) {
			throw new DAOException(e);
		}
		return lista;
	}

	/**
	 * AUTOR Y FECHA CREACION: JCHAVEZ / 06-09-2013 OBTENER LISTA
	 * CARTERACREDITODETALLE PK EXPEDIENTE-MOVIMIENTO Y CUENTA CONCEPTO
	 * MODIFICACION: (19-09-2013 JCHAVEZ)
	 */
	@SuppressWarnings("unchecked")
	public List<CarteraCreditoDetalle> getListaXExpOCtaCpto(Object o)
			throws DAOException {
		List<CarteraCreditoDetalle> lista = null;
		try {
			lista = (List<CarteraCreditoDetalle>) getSqlMapClientTemplate()
					.queryForList(getNameSpace() + ".getListaXExpOCtaCpto", o);
		} catch (Exception e) {
			throw new DAOException(e);
		}
		return lista;
	}

	/**
	 * Recupera el ultimo registro por Credito (empresa, cuenta, expediente,
	 * expedietedete) y peridodo
	 * 
	 * @param o
	 * @return
	 * @throws DAOException
	 */
	@SuppressWarnings("unchecked")
	public CarteraCreditoDetalle getMaxPorExpediente(Object o)
			throws DAOException {
		CarteraCreditoDetalle domain = null;
		List<CarteraCreditoDetalle> lista = null;
		try {
			lista = (List<CarteraCreditoDetalle>) getSqlMapClientTemplate()
					.queryForList(getNameSpace() + ".getMaxPorExpediente", o);
			if (lista != null && !lista.isEmpty()) {
				domain = new CarteraCreditoDetalle();
				domain = lista.get(0);
			}
		} catch (Exception e) {
			throw new DAOException(e);
		}
		return domain;
	}

	// GTorresBrousset - 21.mar.2014

	@SuppressWarnings("unchecked")
	public List<CarteraCreditoManual> buscaCarteraManual(Object o)
			throws DAOException {
		List<CarteraCreditoManual> lista = null;
		try {
			lista = (List<CarteraCreditoManual>) getSqlMapClientTemplate()
					.queryForList(getNameSpace() + ".getListaCarteraCredito", o);
		} catch (Exception e) {
			throw new DAOException(e);
		}
		return lista;
	}

	@SuppressWarnings("unchecked")
	public CarteraCreditoManual obtenerCarteraManual(Object o)
			throws DAOException {
		CarteraCreditoManual carteraCreditoManual = null;
		List<CarteraCreditoManual> lista = null;
		try {
			lista = (List<CarteraCreditoManual>) getSqlMapClientTemplate()
					.queryForList(getNameSpace() + ".getCarteraCreditoManual",
							o);
			if (lista != null && !lista.isEmpty()) {
				carteraCreditoManual = new CarteraCreditoManual();
				carteraCreditoManual = lista.get(0);
			}
		} catch (Exception e) {
			throw new DAOException(e);
		}
		return carteraCreditoManual;
	}

	@SuppressWarnings("unchecked")
	public CarteraCreditoManualGen generarCarteraManual(Object o)
			throws DAOException {
		CarteraCreditoManualGen carteraCreditoManualGen = null;
		List<CarteraCreditoManualGen> lista = null;
		try {
			lista = (List<CarteraCreditoManualGen>) getSqlMapClientTemplate()
					.queryForList(
							getNameSpace() + ".generarCarteraCreditoManual", o);
			if (lista != null && !lista.isEmpty()) {
				carteraCreditoManualGen = new CarteraCreditoManualGen();
				carteraCreditoManualGen = lista.get(0);
			}
		} catch (Exception e) {
			throw new DAOException(e);
		}
		return carteraCreditoManualGen;
	}

	@SuppressWarnings("unchecked")
	public CarteraCreditoManual cerrarCarteraManual(Object o)
			throws DAOException {
		CarteraCreditoManual carteraCreditoManual = null;
		List<CarteraCreditoManual> lista = null;
		try {
			lista = (List<CarteraCreditoManual>) getSqlMapClientTemplate()
					.queryForList(getNameSpace() + ".cerrarCarteraManual", o);
			if (lista != null && !lista.isEmpty()) {
				carteraCreditoManual = new CarteraCreditoManual();
				carteraCreditoManual = lista.get(0);
			}
		} catch (Exception e) {
			throw new DAOException(e);
		}
		return carteraCreditoManual;
	}

	public void generarLibroCartera() throws DAOException {
		try {
			getSqlMapClientTemplate().queryForList(
					getNameSpace() + ".generarLibroCartera");
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}

}
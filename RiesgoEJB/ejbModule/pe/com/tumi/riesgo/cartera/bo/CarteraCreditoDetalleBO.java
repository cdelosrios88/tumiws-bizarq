package pe.com.tumi.riesgo.cartera.bo;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.movimiento.concepto.domain.ExpedienteId;
import pe.com.tumi.riesgo.cartera.dao.CarteraCreditoDetalleDao;
import pe.com.tumi.riesgo.cartera.dao.impl.CarteraCreditoDetalleDaoIbatis;
import pe.com.tumi.riesgo.cartera.domain.CarteraCreditoDetalle;
import pe.com.tumi.riesgo.cartera.domain.CarteraCreditoId;
import pe.com.tumi.riesgo.cartera.domain.composite.CarteraCreditoManual;
import pe.com.tumi.riesgo.cartera.domain.composite.CarteraCreditoManualGen;

public class CarteraCreditoDetalleBO {
	protected static Logger log = Logger
			.getLogger(CarteraCreditoDetalleBO.class);
	private CarteraCreditoDetalleDao dao = (CarteraCreditoDetalleDao) TumiFactory
			.get(CarteraCreditoDetalleDaoIbatis.class);

	public List<CarteraCreditoDetalle> getListaPorCarteraCredito(
			CarteraCreditoId pId) throws BusinessException {
		List<CarteraCreditoDetalle> lista = null;
		try {
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("intPersEmpresacartera_n_pk",
					pId.getIntPersEmpresaCartera());
			mapa.put("intCriePperiodocartera", pId.getIntCriePeriodoCartera());
			mapa.put("intCrieIitemcarteracredito",
					pId.getIntCrieItemCarteraCredito());

			lista = dao.getListaPorCarteraCredito(mapa);

		} catch (DAOException e) {
			throw new BusinessException(e);
		} catch (Exception e) {
			throw new BusinessException(e);
		}

		return lista;
	}

	/**
	 * AUTOR Y FECHA CREACION: JCHAVEZ / 06-09-2013 OBTENER LISTA
	 * CARTERACREDITODETALLE PK EXPEDIENTE-MOVIMIENTO Y CUENTA CONCEPTO
	 * MODIFICACION: (19-09-2013 JCHAVEZ)
	 * 
	 * @param pId
	 * @return
	 * @throws BusinessException
	 */
	public List<CarteraCreditoDetalle> getListaXExpOCtaCpto(ExpedienteId pId)
			throws BusinessException {
		log.info("-----------------------Debugging CarteraCreditoDetalleBO.getListaXExpOCtaCpto-----------------------------");
		List<CarteraCreditoDetalle> lista = null;
		try {
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("intPersEmpresa", pId.getIntPersEmpresaPk());
			log.info("pId.getIntPersEmpresaPk()" + pId.getIntPersEmpresaPk());
			mapa.put("intCsocCuenta", pId.getIntCuentaPk());
			log.info("pId.getIntCuentaPk()" + pId.getIntCuentaPk());
			mapa.put("intCserItemexpediente", pId.getIntItemExpediente());
			log.info("pId.getIntItemExpediente()" + pId.getIntItemExpediente());
			mapa.put("intCserItemdetexpediente",
					pId.getIntItemExpedienteDetalle());
			log.info("pId.getIntItemExpedienteDetalle()"
					+ pId.getIntItemExpedienteDetalle());
			// mapa.put("intCmovItemcuentaconcepto", intCmovItemcuentaconcepto);
			// log.info("intCmovItemcuentaconcepto: "+intCmovItemcuentaconcepto);
			lista = dao.getListaXExpOCtaCpto(mapa);
			log.info("lista recuperada de tamaño: " + lista.size());

		} catch (DAOException e) {
			throw new BusinessException(e);
		} catch (Exception e) {
			throw new BusinessException(e);
		}

		return lista;
	}

	/**
	 * Recupera el ultimo registro por Credito (empresa, cuenta, expediente,
	 * expedietedete)
	 * 
	 * @param pId
	 * @return
	 * @throws BusinessException
	 */
	public CarteraCreditoDetalle getMaxPorExpediente(ExpedienteId pId,
			Integer intPeriodo) throws BusinessException {
		CarteraCreditoDetalle carteraDetalle = null;
		try {
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("intPersEmpresa", pId.getIntPersEmpresaPk());
			mapa.put("intCsocCuenta", pId.getIntCuentaPk());
			mapa.put("intCserItemexpediente", pId.getIntItemExpediente());
			mapa.put("intCserItemdetexpediente",
					pId.getIntItemExpedienteDetalle());
			mapa.put("intCriePperiodocartera", intPeriodo);

			carteraDetalle = dao.getMaxPorExpediente(mapa);

		} catch (DAOException e) {
			throw new BusinessException(e);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
		return carteraDetalle;
	}

	/**
	 * 
	 * @param fechaCorte
	 * @param intEmpresa
	 * @param intPersona
	 * @return
	 * @throws BusinessException
	 * @throws Exception
	 */
	public List<CarteraCreditoManual> buscaCarteraManual(int intPeriodoInicial,
			int intPeriodoFinal, Integer intEstadoMen, Integer intEstadoMay)
			throws BusinessException {
		List<CarteraCreditoManual> listaCarCreManual = null;
		try {
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("intPeriodoInicial", intPeriodoInicial);
			mapa.put("intPeriodoFinal", intPeriodoFinal);
			mapa.put("intEstadoMen", intEstadoMen);
			mapa.put("intEstadoMay", intEstadoMay);

			listaCarCreManual = dao.buscaCarteraManual(mapa);
		} catch (DAOException e) {
			throw new BusinessException(e);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
		return listaCarCreManual;
	}

	public CarteraCreditoManual obtenerCarteraManual() throws BusinessException {
		CarteraCreditoManual carteraCreditoManual = null;
		try {
			HashMap<String, Object> mapa = new HashMap<String, Object>();

			carteraCreditoManual = dao.obtenerCarteraManual(mapa);
		} catch (DAOException e) {
			throw new BusinessException(e);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
		return carteraCreditoManual;
	}

	public CarteraCreditoManualGen generarCarteraManual(Date dtFechaCorte,
			Integer intPersEmpresa, Integer intPersPersona)
			throws BusinessException {
		CarteraCreditoManualGen carteraCreditoManualGen = null;
		try {
			java.sql.Date sqlDtFechaCorte = new java.sql.Date(dtFechaCorte.getTime());
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("dtFechaCorte", sqlDtFechaCorte);
			mapa.put("intPersEmpresa", intPersEmpresa);
			mapa.put("intPersPersona", intPersPersona);

			carteraCreditoManualGen = dao.generarCarteraManual(mapa);
			
			if(carteraCreditoManualGen.getIntGenerado() == 1) {
				dao.generarLibroCartera();
			}
		} catch (DAOException e) {
			throw new BusinessException(e);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
		return carteraCreditoManualGen;
	}

	public CarteraCreditoManual cerrarCarteraManual(Integer intPeriodo)
			throws BusinessException {
		CarteraCreditoManual carteraCreditoManual = null;
		try {
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("intPeriodo", intPeriodo);

			carteraCreditoManual = dao.cerrarCarteraManual(mapa);
		} catch (DAOException e) {
			throw new BusinessException(e);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
		return carteraCreditoManual;
	}

}

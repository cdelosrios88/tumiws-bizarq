package pe.com.tumi.riesgo.cartera.facade;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.common.util.Utilitario;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.facade.TumiFacade;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.movimiento.concepto.domain.ExpedienteId;
import pe.com.tumi.riesgo.cartera.bo.CarteraBO;
import pe.com.tumi.riesgo.cartera.bo.CarteraCreditoDetalleBO;
import pe.com.tumi.riesgo.cartera.bo.PlantillaBO;
import pe.com.tumi.riesgo.cartera.bo.PlantillaDetalleBO;
import pe.com.tumi.riesgo.cartera.domain.Cartera;
import pe.com.tumi.riesgo.cartera.domain.CarteraCredito;
import pe.com.tumi.riesgo.cartera.domain.CarteraCreditoDetalle;
import pe.com.tumi.riesgo.cartera.domain.Especificacion;
import pe.com.tumi.riesgo.cartera.domain.Plantilla;
import pe.com.tumi.riesgo.cartera.domain.PlantillaDetalle;
import pe.com.tumi.riesgo.cartera.domain.Prociclico;
import pe.com.tumi.riesgo.cartera.domain.Producto;
import pe.com.tumi.riesgo.cartera.domain.Provision;
import pe.com.tumi.riesgo.cartera.domain.Tiempo;
import pe.com.tumi.riesgo.cartera.domain.composite.CarteraCreditoManual;
import pe.com.tumi.riesgo.cartera.domain.composite.CarteraCreditoManualGen;
import pe.com.tumi.riesgo.cartera.service.CarteraService;

@Stateless
public class CarteraFacade extends TumiFacade implements CarteraFacadeRemote,
		CarteraFacadeLocal {

	PlantillaDetalleBO plantillaDetalleBO = (PlantillaDetalleBO) TumiFactory
			.get(PlantillaDetalleBO.class);
	PlantillaBO plantillaBO = (PlantillaBO) TumiFactory.get(PlantillaBO.class);
	CarteraBO carteraBO = (CarteraBO) TumiFactory.get(CarteraBO.class);
	CarteraService carteraService = (CarteraService) TumiFactory
			.get(CarteraService.class);
	CarteraCreditoDetalleBO carteraCredDetalleBO = (CarteraCreditoDetalleBO) TumiFactory
			.get(CarteraCreditoDetalleBO.class);

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<PlantillaDetalle> getPlantillaDetalleTodo(
			Integer intTipoProvision) throws BusinessException {
		List<PlantillaDetalle> lista = null;
		try {
			lista = plantillaDetalleBO.getTodos(intTipoProvision);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}

	public Cartera grabarCartera(Cartera cartera)
	// public Cartera grabarCartera()
			throws BusinessException {
		Cartera dto = null;
		try {
			dto = carteraService.grabarCartera(cartera);
		} catch (BusinessException e) {
			context.setRollbackOnly();
			throw e;
		} catch (Exception e) {
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;
	}

	public Cartera modificarCartera(Cartera cartera)
	// public Cartera modificarCartera()
			throws BusinessException {
		Cartera dto = null;
		try {
			dto = carteraService.modificarCartera(cartera);
			// dto = carteraService.modificarCartera(null);
		} catch (BusinessException e) {
			context.setRollbackOnly();
			throw e;
		} catch (Exception e) {
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Cartera> buscarCartera(Cartera cartera,
			Producto productoFiltro, Boolean buscarVigente, Boolean buscarCaduco)
			throws BusinessException {
		List<Cartera> lista = null;
		try {
			lista = carteraService.buscarCartera(cartera, productoFiltro,
					buscarVigente, buscarCaduco);
			// lista = carteraService.buscarCartera(cartera, null, true, true);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}

	public Cartera eliminarCartera(Cartera cartera) throws BusinessException {
		try {
			cartera.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
			carteraBO.modificar(cartera);
		} catch (BusinessException e) {
			context.setRollbackOnly();
			throw e;
		} catch (Exception e) {
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return cartera;
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Especificacion> getListaEspecificacionPorIntItemCartera(
			Integer intItemCartera) throws BusinessException, Exception {
		List<Especificacion> lista = null;
		try {
			lista = carteraService
					.getListaEspecificacionPorIntItemCartera(intItemCartera);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Provision> getListaProvisionPorEspecificacion(
			Especificacion especificacion) throws BusinessException, Exception {
		List<Provision> lista = null;
		try {
			lista = carteraService
					.getListaProvisionPorEspecificacion(especificacion);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Prociclico getProciclicoPorEspecificacion(
			Especificacion especificacion) throws BusinessException, Exception {
		Prociclico domain = null;
		try {
			domain = carteraService
					.getProciclicoPorEspecificacion(especificacion);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException(e);
		}
		return domain;
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Plantilla> getPlantillaTodo() throws BusinessException {
		List<Plantilla> lista = null;
		try {
			lista = plantillaBO.getTodos();
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Tiempo> getListaTiempoPorCartera(Cartera cartera)
			throws BusinessException, Exception {
		List<Tiempo> lista = null;
		try {
			lista = carteraService.getListaTiempoPorCartera(cartera);
			// lista = carteraService.getListaTiempoPorCartera(null);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public CarteraCredito getCarteraCreditoPorMaxPeriodo(Integer intEmpresa)
			throws BusinessException, Exception {
		CarteraCredito dato = null;
		try {
			dato = carteraService.getCarteraCreditoPorMaxPeriodo(intEmpresa);

		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException(e);
		}
		return dato;
	}

	/**
	 * AUTOR Y FECHA CREACION: JCHAVEZ / 06-09-2013 OBTENER LISTA
	 * CARTERACREDITODETALLE PK EXPEDIENTE-MOVIMIENTO Y CUENTA CONCEPTO
	 * MODIFICACION: (19-09-2013 JCHAVEZ)
	 */
	public List<CarteraCreditoDetalle> getListaXExpOCtaCpto(ExpedienteId pId)
			throws BusinessException {
		List<CarteraCreditoDetalle> dato = null;
		try {
			dato = carteraCredDetalleBO.getListaXExpOCtaCpto(pId);

		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException(e);
		}
		return dato;
	}

	/**
	 * Recupera el ultimo registro por Credito (empresa, cuenta, expediente,
	 * expedietedete) y expediente }. En estado activo.
	 * 
	 * @param pId
	 * @return
	 * @throws BusinessException
	 * @throws Exception
	 */
	public CarteraCreditoDetalle getMaxPorExpediente(ExpedienteId pId,
			Integer intPeriodo) throws BusinessException {
		CarteraCreditoDetalle carteraDet = null;
		try {
			carteraDet = carteraService.getMaxPorExpediente(pId, intPeriodo);

		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException(e);
		}
		return carteraDet;
	}

	// GTorresBrousset - 21.mar.2014

	/**
	 * Método que obtiene...
	 * 
	 * @param mesFiltro
	 * @param anioFiltro
	 * @param tipoCarteraFiltro
	 * @param estadoFiltro
	 * @return
	 * @throws BusinessException
	 * @throws Exception
	 */
	public List<CarteraCreditoManual> buscaCarteraManual(Integer intMesInicio,
			Integer intMesFinal, Integer intAnio, Integer intEstadoMen,
			Integer intEstadoMay) throws BusinessException {
		String periodoInicial;
		String periodoFinal;
		Integer intPeriodoInicial;
		Integer intPeriodoFinal;
		List<CarteraCreditoManual> listaCarCredManual = null;
		try {
			if (intMesInicio < 10)
				periodoInicial = String.valueOf(intAnio) + "0"
						+ String.valueOf(intMesInicio);
			else
				periodoInicial = String.valueOf(intAnio)
						+ String.valueOf(intMesInicio);

			if (intMesFinal < 10)
				periodoFinal = String.valueOf(intAnio) + "0"
						+ String.valueOf(intMesFinal);
			else
				periodoFinal = String.valueOf(intAnio)
						+ String.valueOf(intMesFinal);

			intPeriodoInicial = Integer.parseInt(periodoInicial);
			intPeriodoFinal = Integer.parseInt(periodoFinal);

			listaCarCredManual = carteraService.buscaCarteraManual(
					intPeriodoInicial, intPeriodoFinal, intEstadoMen,
					intEstadoMay);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException(e);
		}
		return listaCarCredManual;
	}

	public CarteraCreditoManual obtenerCarteraManual() throws BusinessException {
		CarteraCreditoManual carteraCreditoManual = null;
		try {
			carteraCreditoManual = carteraService.obtenerCarteraManual();
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException(e);
		}
		return carteraCreditoManual;
	}

	public CarteraCreditoManualGen generarCarteraManual(Integer intMes,
			Integer intAnio, Integer intPersEmpresa, Integer intPersPersona)
			throws BusinessException {
		CarteraCreditoManualGen carteraCreditoManualGen = null;
		Utilitario util = new Utilitario();
		Date dtFechaCorte = null;
		try {
			dtFechaCorte = util.obtieneUltimoDiaMes(intMes, intAnio);
			carteraCreditoManualGen = carteraService.generarCarteraManual(
					dtFechaCorte, intPersEmpresa, intPersPersona);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException(e);
		}
		return carteraCreditoManualGen;
	}

	public CarteraCreditoManual cerrarCarteraManual(Integer intMes,
			Integer intAnio) throws BusinessException {
		String periodo;
		Integer intPeriodo;
		CarteraCreditoManual carteraCreditoManual = null;
		try {
			if (intMes < 10)
				periodo = String.valueOf(intAnio) + "0"
						+ String.valueOf(intMes);
			else
				periodo = String.valueOf(intAnio) + String.valueOf(intMes);

			intPeriodo = Integer.parseInt(periodo);

			carteraCreditoManual = carteraService
					.cerrarCarteraManual(intPeriodo);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException(e);
		}
		return carteraCreditoManual;
	}
}

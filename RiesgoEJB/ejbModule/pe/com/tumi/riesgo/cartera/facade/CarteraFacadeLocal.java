package pe.com.tumi.riesgo.cartera.facade;

import java.util.List;

import javax.ejb.Local;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.movimiento.concepto.domain.ExpedienteId;
import pe.com.tumi.riesgo.cartera.domain.Cartera;
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

@Local
public interface CarteraFacadeLocal {
	public List<PlantillaDetalle> getPlantillaDetalleTodo(
			Integer intTipoProvision) throws BusinessException;

	public Cartera grabarCartera(Cartera cartera) throws BusinessException;

	public List<Cartera> buscarCartera(Cartera cartera,
			Producto productoFiltro, Boolean buscarVigente, Boolean BuscarCaduco)
			throws BusinessException;

	// public List<Cartera> buscarCartera(Cartera cartera) throws
	// BusinessException;
	public Cartera eliminarCartera(Cartera cartera) throws BusinessException;

	public List<Especificacion> getListaEspecificacionPorIntItemCartera(
			Integer intItemCartera) throws BusinessException, Exception;

	public List<Provision> getListaProvisionPorEspecificacion(
			Especificacion especificacion) throws BusinessException, Exception;

	public Cartera modificarCartera(Cartera cartera) throws BusinessException;

	public Prociclico getProciclicoPorEspecificacion(
			Especificacion especificacion) throws BusinessException, Exception;

	public List<Plantilla> getPlantillaTodo() throws BusinessException;

	public List<Tiempo> getListaTiempoPorCartera(Cartera cartera)
			throws BusinessException, Exception;

	// AUTOR Y FECHA CREACION: JCHAVEZ / 06-09-2013
	// public List<CarteraCreditoDetalle> getListaXExpOCtaCpto(ExpedienteId pId,
	// Integer intCmovItemcuentaconcepto) throws BusinessException;
	public List<CarteraCreditoDetalle> getListaXExpOCtaCpto(ExpedienteId pId)
			throws BusinessException;

	public CarteraCreditoDetalle getMaxPorExpediente(ExpedienteId pId,
			Integer intPeriodo) throws BusinessException;

	// GTorresBrousset - 21.mar.2014
	public List<CarteraCreditoManual> buscaCarteraManual(Integer intMesInicio,
			Integer intMesFinal, Integer intAnio, Integer intEstadoMen,
			Integer intEstadoMay) throws BusinessException;

	public CarteraCreditoManual obtenerCarteraManual() throws BusinessException;

	public CarteraCreditoManualGen generarCarteraManual(Integer intMes,
			Integer intAnio, Integer intPersEmpresa, Integer intPersPersona)
			throws BusinessException;

	public CarteraCreditoManual cerrarCarteraManual(Integer intMes,
			Integer intAnio) throws BusinessException;

}

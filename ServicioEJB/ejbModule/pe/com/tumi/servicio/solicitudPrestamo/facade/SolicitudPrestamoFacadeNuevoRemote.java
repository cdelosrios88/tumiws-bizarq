package pe.com.tumi.servicio.solicitudPrestamo.facade;
import java.util.List;

import javax.ejb.Remote;

import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.Cuenta;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.CuentaId;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.servicio.prevision.domain.EstadoLiquidacion;
import pe.com.tumi.servicio.prevision.domain.ExpedienteLiquidacion;
import pe.com.tumi.servicio.prevision.domain.ExpedienteLiquidacionDetalle;
import pe.com.tumi.servicio.solicitudPrestamo.domain.EstadoCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.ExpedienteCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.ExpedienteCreditoId;

@Remote
public interface SolicitudPrestamoFacadeNuevoRemote {

	public List<ExpedienteCredito> getListaExpedienteCreditoPorCuenta(Cuenta o) throws BusinessException;
	public List<EstadoCredito> getListaEstadosPorExpedienteCreditoId(ExpedienteCreditoId pId)throws BusinessException;
	public List<ExpedienteLiquidacionDetalle> getPorCuentaId(CuentaId cuentaId)throws BusinessException;
	public List<EstadoLiquidacion> getPorExpediente(ExpedienteLiquidacion expedienteLiquidacion)throws BusinessException;
	//AUTOR Y FECHA CREACION: JCHAVEZ / 04-09-2013 
	public List<EstadoCredito> getListaPorExpedienteCreditoPkYEstadoCredito(ExpedienteCreditoId pId, Integer intParaEstadoCreditoCod)throws BusinessException;
}

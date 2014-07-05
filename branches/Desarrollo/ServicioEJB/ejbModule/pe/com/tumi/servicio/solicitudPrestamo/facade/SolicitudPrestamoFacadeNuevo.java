package pe.com.tumi.servicio.solicitudPrestamo.facade;

import java.util.List;

import javax.ejb.Stateless;

import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.Cuenta;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.CuentaId;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.facade.TumiFacade;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.servicio.prevision.bo.EstadoLiquidacionBO;
import pe.com.tumi.servicio.prevision.bo.ExpedienteLiquidacionDetalleBO;
import pe.com.tumi.servicio.prevision.domain.EstadoLiquidacion;
import pe.com.tumi.servicio.prevision.domain.ExpedienteLiquidacion;
import pe.com.tumi.servicio.prevision.domain.ExpedienteLiquidacionDetalle;
import pe.com.tumi.servicio.solicitudPrestamo.bo.EstadoCreditoBO;
import pe.com.tumi.servicio.solicitudPrestamo.domain.EstadoCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.ExpedienteCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.ExpedienteCreditoId;
import pe.com.tumi.servicio.solicitudPrestamo.service.SolicitudPrestamoService;

/**
 * Session Bean implementation class SolicitudPrestamoFacadeNuevo
 */
@Stateless
public class SolicitudPrestamoFacadeNuevo extends TumiFacade implements SolicitudPrestamoFacadeNuevoRemote, SolicitudPrestamoFacadeNuevoLocal {
	private SolicitudPrestamoService solicitudPrestamoService = (SolicitudPrestamoService)TumiFactory.get(SolicitudPrestamoService.class);
	private EstadoCreditoBO boEstadoCredito = (EstadoCreditoBO)TumiFactory.get(EstadoCreditoBO.class);
	private ExpedienteLiquidacionDetalleBO boExpLiqDetalle = (ExpedienteLiquidacionDetalleBO)TumiFactory.get(ExpedienteLiquidacionDetalleBO.class);
	private EstadoLiquidacionBO boEstadoLiquidacion = (EstadoLiquidacionBO)TumiFactory.get(EstadoLiquidacionBO.class);
	
	public List<ExpedienteCredito> getListaExpedienteCreditoPorCuenta(Cuenta o) throws BusinessException{
		List<ExpedienteCredito> lista = null;
		try{
			lista = solicitudPrestamoService.getListaExpedienteCreditoPorCuenta(o);
			}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	//ELIMINAR EN UN FUTURO
	public List<EstadoCredito> getListaEstadosPorExpedienteCreditoId(ExpedienteCreditoId pId)throws BusinessException{
		List<EstadoCredito> lstEstados = null;
		try {
			lstEstados = boEstadoCredito.getListaPorPkExpedienteCredito(pId);
		}  catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return lstEstados;
	}
	
	/**
	 * AUTOR Y FECHA CREACION: JCHAVEZ / 28-08-2013 
	 * OBTENER EXPEDIENTELIQUIDACION POR LA CUENTA DEL SOCIO
	 */
	public List<ExpedienteLiquidacionDetalle> getPorCuentaId(CuentaId cuentaId)throws BusinessException{
		List<ExpedienteLiquidacionDetalle> lista = null;
		try {
			lista = boExpLiqDetalle.getPorCuentaId(cuentaId);
		}  catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return lista;
	}
	
	/**
	 * AUTOR Y FECHA CREACION: JCHAVEZ / 28-08-2013 
	 * OBTENER FECHA DE LIQUIDACION DE CUENTAS NO VIGENTES
	 */
	public List<EstadoLiquidacion> getPorExpediente(ExpedienteLiquidacion expedienteLiquidacion)throws BusinessException{
		List<EstadoLiquidacion> lista = null;
		try {
			lista = boEstadoLiquidacion.getPorExpediente(expedienteLiquidacion);
		}  catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return lista;
	}
	
	/**
	 * AUTOR Y FECHA CREACION: JCHAVEZ / 05-09-2013 
	 * OBTENER ESTADO CREDITO POR ID EXPEDIENTECREDITO Y ESTADOCREDITO (OPCIONAL)
	 */
	public List<EstadoCredito> getListaPorExpedienteCreditoPkYEstadoCredito(ExpedienteCreditoId pId, Integer intParaEstadoCreditoCod)throws BusinessException{
		List<EstadoCredito> lstEstados = null;
		try {
			lstEstados = boEstadoCredito.getListaPorExpedienteCreditoPkYEstadoCredito(pId,intParaEstadoCreditoCod);
		}  catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return lstEstados;
	}

}

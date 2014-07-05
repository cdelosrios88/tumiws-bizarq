package pe.com.tumi.cobranza.prioridad.facade;

import javax.ejb.Stateless;

import pe.com.tumi.cobranza.planilla.domain.Envioconcepto;
import pe.com.tumi.cobranza.prioridad.bo.PrioridadDescuentoBO;
import pe.com.tumi.cobranza.prioridad.domain.PrioridadDescuento;
import pe.com.tumi.cobranza.prioridad.service.PrioridadDescuentoService;
import pe.com.tumi.credito.socio.captacion.domain.CaptacionId;
import pe.com.tumi.credito.socio.creditos.domain.CreditoId;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.facade.TumiFacade;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.movimiento.concepto.domain.CuentaConceptoDetalle;
import pe.com.tumi.movimiento.concepto.domain.Expediente;

@Stateless
public class PrioridadDescuentoFacade extends TumiFacade implements PrioridadDescuentoFacadeRemote, PrioridadDescuentoFacadeLocal {
       
	PrioridadDescuentoService   servicePrioridadDescuento   = (PrioridadDescuentoService)TumiFactory.get(PrioridadDescuentoService.class);
	private PrioridadDescuentoBO prioridadDescuentoBO = (PrioridadDescuentoBO)TumiFactory.get(PrioridadDescuentoBO.class);
	
	public PrioridadDescuento obtenerOrdenPrioridadDescuento(Integer intPersEmpresa, Integer intParaTipoconceptogeneral, CaptacionId captacionId,CreditoId creditoId) throws BusinessException{
		PrioridadDescuento dto = null;
		try{
			dto = servicePrioridadDescuento.obtenerOrdenPrioridadDescuento(intPersEmpresa, intParaTipoconceptogeneral, captacionId, creditoId);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;
	}
	/**
	 * AUTOR Y FECHA CREACION: JCHAVEZ / 11-09-2013
	 * OBTENER PRIORIDADDESCUENTO POR TIPOCONCEPTOGRAL Y 
	 * MOVIMIENTO [CUENTACONCEPTODETALLE (intParaTipocaptacion - intcsocItem) O EXPEDIENTECREDITO (intParaTipoCredito - intCsocItemCredito)]
	 */
	public PrioridadDescuento getPrioridadPorTipoCptoGralCtaCptoExpediente(Envioconcepto envioconcepto, Integer intParaTipomovimiento, CuentaConceptoDetalle ctaCptoDet, Expediente expediente) throws BusinessException {
		PrioridadDescuento dto = null;
    	try{
    		dto = prioridadDescuentoBO.getPrioridadPorTipoCptoGralCtaCptoExpediente(envioconcepto, intParaTipomovimiento, ctaCptoDet, expediente);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return dto;
	}

}

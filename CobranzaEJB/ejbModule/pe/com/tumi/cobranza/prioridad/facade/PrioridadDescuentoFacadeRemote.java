package pe.com.tumi.cobranza.prioridad.facade;
import javax.ejb.Remote;

import pe.com.tumi.cobranza.planilla.domain.Envioconcepto;
import pe.com.tumi.cobranza.prioridad.domain.PrioridadDescuento;
import pe.com.tumi.credito.socio.captacion.domain.CaptacionId;
import pe.com.tumi.credito.socio.creditos.domain.CreditoId;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.movimiento.concepto.domain.CuentaConceptoDetalle;
import pe.com.tumi.movimiento.concepto.domain.Expediente;

@Remote
public interface PrioridadDescuentoFacadeRemote {
	public PrioridadDescuento obtenerOrdenPrioridadDescuento(Integer intPersEmpresa, Integer intParaTipoconceptogeneral, CaptacionId captacionId,CreditoId creditoId) throws BusinessException;
	//AUTOR Y FECHA CREACION: JCHAVEZ / 11-09-2013
	public PrioridadDescuento getPrioridadPorTipoCptoGralCtaCptoExpediente(Envioconcepto envioconcepto, Integer intParaTipomovimiento, CuentaConceptoDetalle ctaCptoDet, Expediente expediente) throws BusinessException;
}

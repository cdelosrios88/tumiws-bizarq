package pe.com.tumi.cobranza.prioridad.service;

import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.cobranza.prioridad.bo.PrioridadDescuentoBO;
import pe.com.tumi.cobranza.prioridad.domain.PrioridadDescuento;
import pe.com.tumi.credito.socio.captacion.domain.CaptacionId;
import pe.com.tumi.credito.socio.creditos.domain.CreditoId;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;

public class PrioridadDescuentoService {
	protected  static Logger log = Logger.getLogger(PrioridadDescuentoBO.class);
	
   private PrioridadDescuentoBO boPrioridadDescuento = (PrioridadDescuentoBO)TumiFactory.get(PrioridadDescuentoBO.class);
   public PrioridadDescuento obtenerOrdenPrioridadDescuento(Integer intPersEmpresa,
		                                                     Integer intParaTipoconceptogeneral,
		                                                     CaptacionId captacionId,
		                                                     CreditoId   creditoId
		                                                     )throws BusinessException,Exception{
	   PrioridadDescuento dto = null;
	   List<PrioridadDescuento> lista = boPrioridadDescuento.getListaPorConceptoGnral(intPersEmpresa, intParaTipoconceptogeneral);
	   log.debug(lista.size()); 
	   for (PrioridadDescuento prioridadDescuento : lista) {
	    	if (intPersEmpresa.equals(prioridadDescuento.getId().getIntPersEmpresa()) &&
	    		intParaTipoconceptogeneral.equals(prioridadDescuento.getIntParaTipoconceptogeneral())){
	    		 if (captacionId != null){
	    			    Integer intParaTipoCaptacionCod = captacionId.getIntParaTipoCaptacionCod();
	    			    Integer intItem  = captacionId.getIntItem();
	    			 if (prioridadDescuento.getIntParaTipocaptacion().equals(intParaTipoCaptacionCod) && intItem.equals(prioridadDescuento.getIntcsocItem())){
	    				 dto = prioridadDescuento;
	    				 break;
	    			 }
	    		 }
	    		 else
	    		 if (creditoId != null){
	    			 Integer  itemCredito           = creditoId.getIntItemCredito();
	    			 Integer  itemParaTipoCredito   = creditoId.getIntParaTipoCreditoCod();
	    			 if (prioridadDescuento.getIntCsocItemCredito().equals(itemCredito) && prioridadDescuento.getIntParaTipoCredito().equals(itemParaTipoCredito)){
	    				 dto = prioridadDescuento;
	    				 break;
	    			 }
	    		 }
	    	}
		}
	   
	   return dto;
   }
}

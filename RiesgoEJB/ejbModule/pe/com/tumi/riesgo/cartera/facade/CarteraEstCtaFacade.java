package pe.com.tumi.riesgo.cartera.facade;

import java.util.List;

import javax.ejb.Stateless;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.movimiento.concepto.domain.ExpedienteId;
import pe.com.tumi.riesgo.cartera.bo.CarteraCreditoDetalleBO;
import pe.com.tumi.riesgo.cartera.domain.CarteraCreditoDetalle;

@Stateless
public class CarteraEstCtaFacade implements CarteraEstCtaFacadeRemote, CarteraEstCtaFacadeLocal {
	CarteraCreditoDetalleBO carteraCredDetalleBO = (CarteraCreditoDetalleBO)TumiFactory.get(CarteraCreditoDetalleBO.class);
	
        /**
         * AUTOR Y FECHA CREACION: JCHAVEZ / 06-09-2013 
    	 * OOBTENER CARTERACREDITODETALLE POR EL MAXIMO PERIODO DE PK EXPEDIENTE-MOVIMIENTO 
         * @param pId
         * @return
         * @throws BusinessException
         * @throws Exception
         */
	/*
       	public List<CarteraCreditoDetalle> getListaPorMaxPerYPKExpediente(ExpedienteId pId) throws BusinessException{
       		List<CarteraCreditoDetalle> dato = null;
       		try{
       			dato = carteraCredDetalleBO.getListaXExpOCtaCpto(pId);
       			
       		}catch(BusinessException e){
       			throw e;
       		}catch(Exception e){
       			throw new BusinessException(e);
       		}
       		return dato;
    }*/

}

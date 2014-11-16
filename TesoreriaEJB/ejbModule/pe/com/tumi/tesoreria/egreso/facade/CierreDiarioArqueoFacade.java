package pe.com.tumi.tesoreria.egreso.facade;


import java.sql.Timestamp;
import java.util.Date;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.facade.TumiFacade;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.tesoreria.egreso.bo.CierreDiarioArqueoBO;
import pe.com.tumi.tesoreria.egreso.domain.CierreDiarioArqueo;
import pe.com.tumi.tesoreria.egreso.domain.CierreDiarioArqueoDetalle;
import pe.com.tumi.tesoreria.egreso.domain.ControlFondosFijos;
import pe.com.tumi.tesoreria.egreso.service.CierreDiarioArqueoService;
import pe.com.tumi.tesoreria.ingreso.domain.Ingreso;

@Stateless
public class CierreDiarioArqueoFacade extends TumiFacade implements CierreDiarioArqueoFacadeRemote, CierreDiarioArqueoFacadeLocal {
	
	CierreDiarioArqueoService cierreDiarioArqueoService = (CierreDiarioArqueoService)TumiFactory.get(CierreDiarioArqueoService.class);
	CierreDiarioArqueoBO boCierreDiarioArqueo = (CierreDiarioArqueoBO)TumiFactory.get(CierreDiarioArqueoBO.class);
	
	
   @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
   public boolean existeCierreDiaAnterior(ControlFondosFijos controlFondosFijos)throws BusinessException{
   	boolean poseePermiso = Boolean.FALSE;
		try{
			Integer intIdEmpresa = controlFondosFijos.getId().getIntPersEmpresa();
			poseePermiso = cierreDiarioArqueoService.existeCierreDiaAnterior(intIdEmpresa, controlFondosFijos.getId().getIntSucuIdSucursal(), 
					controlFondosFijos.getIntSudeIdSubsucursal());
  		}catch(BusinessException e){
  			throw e;
  		}catch(Exception e){
  			throw new BusinessException(e);
  		}
		return poseePermiso;
	}
   
   @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
   public boolean existeCierreDiaActual(ControlFondosFijos controlFondosFijos)throws BusinessException{
   	boolean poseePermiso = Boolean.FALSE;
		try{
			Integer intIdEmpresa = controlFondosFijos.getId().getIntPersEmpresa();
			poseePermiso = cierreDiarioArqueoService.existeCierreDiaActual(intIdEmpresa, controlFondosFijos.getId().getIntSucuIdSucursal(), 
					controlFondosFijos.getIntSudeIdSubsucursal());
  		}catch(BusinessException e){
  			throw e;
  		}catch(Exception e){
  			throw new BusinessException(e);
  		}
		return poseePermiso;
	}
   
   @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
   public boolean validarMovimientoCaja(ControlFondosFijos controlFondosFijos)throws BusinessException{
   	boolean movimientoCajaValida = Boolean.FALSE;
		try{
			if (esDistintoaFechaActual(new Date(controlFondosFijos.getTsFechaRegistro().getTime()))) {
				movimientoCajaValida = true;
			}else movimientoCajaValida = (existeCierreDiaAnterior(controlFondosFijos) && !existeCierreDiaActual(controlFondosFijos));
			
  		}catch(BusinessException e){
  			throw e;
  		}catch(Exception e){
  			throw new BusinessException(e);
  		}
		return movimientoCajaValida;
	}
   
   public static final boolean esDistintoaFechaActual(Date fecha){
	   boolean result = false;
	   
	     Date fechaActual = new Date();
	    java.text.SimpleDateFormat sdf  = new java.text.SimpleDateFormat("dd/MM/yyyy");
	                   String strFecha  = sdf.format(fecha);
	    java.text.SimpleDateFormat sdf1 = new java.text.SimpleDateFormat("dd/MM/yyyy");
	                 String strFechaActual = sdf1.format(fechaActual);
	   
	   if (strFecha.equals(strFechaActual)){
		   result = true;
	   }
	  return result;
   }
   
   @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
   public boolean validarMovimientoCaja(Ingreso ingreso)throws BusinessException{
   	boolean movimientoCajaValida = Boolean.FALSE;
		try{
			movimientoCajaValida = (existeCierreDiaAnterior(ingreso.getId().getIntIdEmpresa(), ingreso.getIntSucuIdSucursal(), ingreso.getIntSudeIdSubsucursal()) 
					&& !existeCierreDiaActual(ingreso.getId().getIntIdEmpresa(), ingreso.getIntSucuIdSucursal(), ingreso.getIntSudeIdSubsucursal()));
  		}catch(BusinessException e){
  			throw e;
  		}catch(Exception e){
  			throw new BusinessException(e);
  		}
		return movimientoCajaValida;
	}
   
   @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
   public boolean existeCierreDiaAnterior(Integer intIdEmpresa, Integer intIdSucursal, Integer intIdSubsucursal)throws BusinessException{
   	boolean poseePermiso = Boolean.FALSE;
		try{
			poseePermiso = cierreDiarioArqueoService.existeCierreDiaAnterior(intIdEmpresa, intIdSucursal, intIdSubsucursal);
  		}catch(BusinessException e){
  			throw e;
  		}catch(Exception e){
  			throw new BusinessException(e);
  		}
		return poseePermiso;
	}
   
   @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
   public boolean existeCierreDiaActual(Integer intIdEmpresa, Integer intIdSucursal, Integer intIdSubsucursal)throws BusinessException{
   		boolean poseePermiso = Boolean.FALSE;
		try{
			poseePermiso = cierreDiarioArqueoService.existeCierreDiaActual(intIdEmpresa, intIdSucursal, intIdSubsucursal);
  		}catch(BusinessException e){
  			throw e;
  		}catch(Exception e){
  			throw new BusinessException(e);
  		}
		return poseePermiso;
	}
   //Inicio: REQ14-005 - bizarq - 11/11/2014
   public boolean existeCierreDiaActualSaldo(Integer intIdEmpresa, Integer intIdSucursal, Integer intIdSubsucursal)throws BusinessException{
  		boolean poseePermiso = Boolean.FALSE;
		try{
			poseePermiso = cierreDiarioArqueoService.existeCierreCajaSaldo(intIdEmpresa,  new Date(),intIdSucursal, intIdSubsucursal);
 		}catch(BusinessException e){
 			throw e;
 		}catch(Exception e){
 			throw new BusinessException(e);
 		}
		return poseePermiso;
	}
   //Fin: REQ14-005 - bizarq - 11/11/2014
   	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
   	public Date obtenerFechaACerrar(CierreDiarioArqueo cierreDiarioArqueo)throws BusinessException{
   		Date date = null;
		try{
			date = cierreDiarioArqueoService.obtenerFechaACerrar(cierreDiarioArqueo);
  		}catch(BusinessException e){
  			throw e;
  		}catch(Exception e){
  			throw new BusinessException(e);
  		}
		return date;
	}
   	
   	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
   	public CierreDiarioArqueoDetalle calcularCierreDiarioArqueoDetalleIngresos(CierreDiarioArqueo cierreDiarioArqueo)throws BusinessException{
   		CierreDiarioArqueoDetalle dto = null;
		try{
			dto = cierreDiarioArqueoService.calcularCierreDiarioArqueoDetalleIngresos(cierreDiarioArqueo);
  		}catch(BusinessException e){
  			throw e;
  		}catch(Exception e){
  			throw new BusinessException(e);
  		}
		return dto;
	}
   	
    public CierreDiarioArqueo modificarCierreDiarioArqueo(CierreDiarioArqueo cierreDiarioArqueo) throws BusinessException{
    	CierreDiarioArqueo dto = null;
		try{
			dto = boCierreDiarioArqueo.modificar(cierreDiarioArqueo);
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
	 * Agregado 11.12.2013 JCHAVEZ 
	 * Valida la existencia de al menos un registro en Cierre Diario Arqueo por 
	 * @param intIdEmpresa
	 * @param intIdSucursal
	 * @param intIdSubsucursal
	 * @return
	 * @throws Exception
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public boolean existeCierreDiarioArqueo(Integer intIdEmpresa, Integer intIdSucursal, Integer intIdSubsucursal)throws BusinessException{
    	boolean poseePermiso = Boolean.FALSE;
 		try{
 			poseePermiso = cierreDiarioArqueoService.existeCierreDiarioArqueo(intIdEmpresa, intIdSucursal, intIdSubsucursal);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
 		return poseePermiso;
 	}
    /**
     * Agregado 13.12.2013 JCHAVEZ
     * Busca ingresos y depositos cuando se va a realizar un cierre diario y arqueo por primera vez.(no existe ningun cierre previo)
     * @param cierreDiarioArqueo
     * @return
     * @throws BusinessException
     */
   	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
   	public CierreDiarioArqueoDetalle obtenerCierreDiarioArqueoDetalleIngresos(CierreDiarioArqueo cierreDiarioArqueo)throws BusinessException{
   		CierreDiarioArqueoDetalle dto = null;
		try{
			dto = cierreDiarioArqueoService.obtenerCierreDiarioArqueoDetalleIngresos(cierreDiarioArqueo);
  		}catch(BusinessException e){
  			throw e;
  		}catch(Exception e){
  			throw new BusinessException(e);
  		}
		return dto;
	}
}
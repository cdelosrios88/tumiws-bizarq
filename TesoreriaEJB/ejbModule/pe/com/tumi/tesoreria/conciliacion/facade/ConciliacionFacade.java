/**

* Resumen.
* Objeto: ConciliacionFacade
* Descripción:  Facade principal del proceso de conciliacion bancaria.
* Fecha de Creación: 18/10/2014.
* Requerimiento de Creación: REQ14-006
* Autor: Bizarq
*/
package pe.com.tumi.tesoreria.conciliacion.facade;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import pe.com.tumi.contabilidad.cierre.domain.LibroMayor;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.facade.TumiFacade;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.servicio.solicitudPrestamo.domain.ExpedienteCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.ExpedienteCreditoId;
import pe.com.tumi.servicio.solicitudPrestamo.domain.composite.ExpedienteCreditoComp;
import pe.com.tumi.tesoreria.conciliacion.service.ConciliacionService;
import pe.com.tumi.tesoreria.egreso.bo.ConciliacionBO;
import pe.com.tumi.tesoreria.egreso.bo.ConciliacionDetalleBO;
import pe.com.tumi.tesoreria.egreso.domain.Conciliacion;
import pe.com.tumi.tesoreria.egreso.domain.ConciliacionDetalle;
import pe.com.tumi.tesoreria.egreso.domain.ConciliacionId;
import pe.com.tumi.tesoreria.egreso.domain.comp.ConciliacionComp;

/**
 * Session Bean implementation class ConciliacionFacade
 */
@Stateless
@Local(ConciliacionFacadeLocal.class)
@Remote(ConciliacionFacadeRemote.class)
public class ConciliacionFacade extends TumiFacade implements ConciliacionFacadeRemote, ConciliacionFacadeLocal {
    
	ConciliacionBO conciliacionBO = (ConciliacionBO)TumiFactory.get(ConciliacionBO.class);
	ConciliacionDetalleBO conciliacionDetBO = (ConciliacionDetalleBO)TumiFactory.get(ConciliacionDetalleBO.class);
	ConciliacionService conciliacionService = (ConciliacionService)TumiFactory.get(ConciliacionService.class);
    /**
     * @see TumiFacade#TumiFacade()
     */
    public ConciliacionFacade() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    /**
     * 
     */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Conciliacion getPorPk(ConciliacionId conciliacionId) throws BusinessException{
		Conciliacion conciliacion = null;
		try{
			conciliacion = conciliacionBO.getPorPk(conciliacionId);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return conciliacion;
	}
	
	
	/**
	 * 
	 */
	public List<Conciliacion> getListFilter(ConciliacionComp conciliacionCompBusq)throws BusinessException{
		List<Conciliacion> lstConciliacion = null;
		try {
			lstConciliacion = conciliacionBO.getListFilter(conciliacionCompBusq);
			
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lstConciliacion;
		
	}
	
	/**
	 * 
	 * @param conciliacion
	 * @return
	 * @throws BusinessException
	 */
	public List<ConciliacionDetalle> buscarRegistrosConciliacion(Conciliacion conciliacion)throws BusinessException{
		List<ConciliacionDetalle> lstConcilDet = null;
		try {

			lstConcilDet= conciliacionService.buscarRegistrosConciliacion(conciliacion);
		
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lstConcilDet;
	}
	
	/**
	 * 
	 * @param pId
	 * @return
	 * @throws BusinessException
	 */
	public Conciliacion getConciliacionEdit(ConciliacionId pId) throws BusinessException{
		Conciliacion conciliacion = null;
		try {

			conciliacion= conciliacionService.getConciliacionEdit(pId);
		
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return conciliacion;	
		
		
	}

	/*
	public Conciliacion getUltimaConciliacion(Conciliacion conciliacion)throws BusinessException{
		Conciliacion ultimaConciliacion = null;
		try {
			// conciliacionCompBusq.setEstado conciliado
			ultimaConciliacion = conciliacionBO.getUltimaConciliacion(conciliacion);
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return ultimaConciliacion;
	}
	
	public List<ConciliacionDetalle> getListConcilDet(ConciliacionDetalle pConcilDet) throws BusinessException{
		List<ConciliacionDetalle> lstConciliacionDetalle = null;
		try{
			lstConciliacionDetalle = conciliacionDetBO.getListConcilDet(pConcilDet);

		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lstConciliacionDetalle;
	}	
	

	
	public Conciliacion getConciliacionConDetalleValidado(Conciliacion pConciliacion) throws BusinessException{
		try{
			//pConciliacion = conciliacionService.getConciliacionConDetalleValidado(pConciliacion, pConciliacion.getListaConciliacionDetalle());

		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return pConciliacion;
	}	
		
		
		
	public Boolean anularConciliacion(ConciliacionComp pConciliacionCompAnul) throws BusinessException{
		Boolean blnExit = false;
		try{
			blnExit = conciliacionService.anularConciliacion(pConciliacionCompAnul);

		}catch(BusinessException e){
			throw e;
			blnExit = false;
		}catch(Exception e){
			throw new BusinessException(e);
			blnExit = false;
		}
		return blnExit;
	}
	
	*/

}

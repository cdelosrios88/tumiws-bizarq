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

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.facade.TumiFacade;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.tesoreria.conciliacion.service.ConciliacionService;
import pe.com.tumi.tesoreria.egreso.bo.ConciliacionBO;
import pe.com.tumi.tesoreria.egreso.bo.ConciliacionDetalleBO;
import pe.com.tumi.tesoreria.egreso.bo.SaldoBO;
import pe.com.tumi.tesoreria.egreso.domain.Conciliacion;
import pe.com.tumi.tesoreria.egreso.domain.ConciliacionDetalle;
import pe.com.tumi.tesoreria.egreso.domain.ConciliacionId;
import pe.com.tumi.tesoreria.egreso.domain.Saldo;
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
	SaldoBO boSaldo = (SaldoBO)TumiFactory.get(SaldoBO.class);
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

	/**
	 * 
	 * @param pConciliacionCompAnul
	 * @return
	 * @throws BusinessException
	 */
	public void anularConciliacion(ConciliacionComp pConciliacionCompAnul) throws BusinessException{
		try{
			conciliacionService.anularConciliacion(pConciliacionCompAnul);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
	}
	
	/**
	 * 
	 * @param conciliacion
	 * @throws BusinessException
	 */
	public void grabarConciliacionDiaria(Conciliacion conciliacion) throws BusinessException{
		try{
			conciliacionService.grabarConciliacionDiaria(conciliacion);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
	}
	
	/**
	 * 
	 * @param conciliacion
	 * @return
	 * @throws BusinessException
	 */
	public Conciliacion getLastConciliacionByCuenta(Conciliacion conciliacion) throws BusinessException{
		Conciliacion concTemp;
		try {
			concTemp = conciliacionBO.getLastConciliacionByCuenta(conciliacion);
			}catch(BusinessException e){
				throw e;
			}catch(Exception e){
				throw new BusinessException(e);
			}
	return concTemp;	
	}
	
	/**
	 * 
	 * @param intIdEmpresa
	 * @throws BusinessException
	 * @return Saldo
	 */
	public Saldo obtenerSaldoUltimaFechaSaldo(Integer intIdEmpresa) throws BusinessException{
    	Saldo dto = null;
		try{
			dto = boSaldo.getSaldoUltimaFechaSaldo(intIdEmpresa);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
		return dto;
	}
}

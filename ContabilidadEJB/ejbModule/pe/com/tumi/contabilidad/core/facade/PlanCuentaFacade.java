package pe.com.tumi.contabilidad.core.facade;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import pe.com.tumi.contabilidad.cierre.bo.AnexoDetalleCuentaBO;
import pe.com.tumi.contabilidad.cierre.domain.AnexoDetalleCuenta;
import pe.com.tumi.contabilidad.core.bo.PlanCuentaBO;
import pe.com.tumi.contabilidad.core.domain.PlanCuenta;
import pe.com.tumi.contabilidad.core.domain.PlanCuentaId;
import pe.com.tumi.contabilidad.core.service.PlanCuentaService;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.facade.TumiFacade;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.seguridad.login.domain.Usuario;

@Stateless
public class PlanCuentaFacade extends TumiFacade implements PlanCuentaFacadeRemote, PlanCuentaFacadeLocal {
	PlanCuentaBO boPlanCuenta = (PlanCuentaBO)TumiFactory.get(PlanCuentaBO.class);
	PlanCuentaService planCuentaService = (PlanCuentaService)TumiFactory.get(PlanCuentaService.class);
	AnexoDetalleCuentaBO boAnexoDetalleCuenta = (AnexoDetalleCuentaBO)TumiFactory.get(AnexoDetalleCuentaBO.class);
	
	public PlanCuenta grabarPlanCuenta(PlanCuenta o)throws BusinessException{
		PlanCuenta dto = null;
		try{
			//Modificación por cdelosrios, 16/09/2013
			//dto = boPlanCuenta.grabarPlanCuenta(o);
			dto = planCuentaService.grabarPlanCuenta(o);
			//Fin Modificación por cdelosrios, 16/09/2013
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public PlanCuenta modificarPlanCuenta(PlanCuenta o)throws BusinessException{
		PlanCuenta dto = null;
		try{
			//Modificación por cdelosrios, 16/09/2013
			//dto = boPlanCuenta.modificarPlanCuenta(o);
			dto = planCuentaService.modificarPlanCuenta(o);
			//Fin Modificación por cdelosrios, 16/09/2013
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
   	public PlanCuenta getPlanCuentaPorPk(PlanCuentaId pId) throws BusinessException{
    	PlanCuenta dto = null;
   		try{
   			dto = boPlanCuenta.getPlanCuentaPorPk(pId);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return dto;
   	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<PlanCuenta> getListaPlanCuentaBusqueda(PlanCuenta pPlanCuenta) throws BusinessException{
		List<PlanCuenta> lista = null;
		try{
			lista = boPlanCuenta.getPlanCuentaBusqueda(pPlanCuenta);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<PlanCuenta> findListCuentaOperacional(PlanCuenta pPlanCuenta) throws BusinessException{
		List<PlanCuenta> lista = null;
		try{
			lista = boPlanCuenta.findListCuentaOperacional(pPlanCuenta);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Integer> getListaPeriodos() throws BusinessException{
		List<Integer> lista = null;
		try{
			lista = boPlanCuenta.getListaPeriodos();
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<PlanCuenta> getListaPlanCuentaPorEmpresaCuentaYPeriodoCuenta(
			Integer intEmpresaCuenta, Integer intPeriodoCuenta) 
		throws BusinessException{
		List<PlanCuenta> lista = null;
		try{
			lista = boPlanCuenta.getListaPorEmpresaCuentaYPeriodoCuenta(intEmpresaCuenta,intPeriodoCuenta);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<PlanCuenta> getListaPlanCuentaBusqueda(PlanCuenta planCuentaFiltro, Usuario usuario, Integer intIdTransaccion) throws BusinessException{
		List<PlanCuenta> lista = null;
		try{
			lista = planCuentaService.buscarListaPlanCuenta(planCuentaFiltro, usuario, intIdTransaccion);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public boolean validarMontoPlanCuenta(PlanCuenta planCuenta, BigDecimal bdMontoValidar) throws BusinessException{
		boolean validar = Boolean.FALSE;
		try{
			validar = planCuentaService.validarMontoPlanCuenta(planCuenta, bdMontoValidar);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return validar;
	}
    
    //Agregado por cdelosrios, 16/09/2013
    public List<AnexoDetalleCuenta> getListaAnexoDetalleCuentaPorPlanCuenta(PlanCuenta o) throws BusinessException{
		List<AnexoDetalleCuenta> lista = null;
		try{
			lista = planCuentaService.getAnexoDetalleCuentaPorPlanCuenta(o);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
    //Fin agregado por cdelosrios, 16/09/2013
    /**
     * AUTOR Y FECHA CREACION: JCHAVEZ / 23.10.2013
	 * Recupera lista Plan Cuenta por periodo base
     */
    public List<PlanCuenta> getPlanCtaPorPeriodoBase(Integer intPeriodoCuenta, Integer intEmpresaCuentaPk) throws BusinessException{
    	List<PlanCuenta> dto = null;
   		try{
   			dto = boPlanCuenta.getPlanCtaPorPeriodoBase(intPeriodoCuenta, intEmpresaCuentaPk);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return dto;
   	}
    
    /**
     * AUTOR Y FECHA CREACION: JCHAVEZ / 23.10.2013
	 * Recupera lista Plan Cuenta por periodo proyectado, nro cuenta o descripcion (opcional)
     * @param pCta
     * @return
     * @throws BusinessException
     */
    public List<PlanCuenta> getBusqPorNroCtaDesc(PlanCuenta pCta) throws BusinessException{
    	List<PlanCuenta> dto = null;
   		try{
   			dto = boPlanCuenta.getBusqPorNroCtaDesc(pCta);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return dto;
   	}
}
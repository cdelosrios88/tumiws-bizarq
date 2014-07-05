package pe.com.tumi.credito.socio.captacion.facade;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.facade.TumiFacade;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.movimiento.concepto.domain.CuentaConceptoDetalle;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.CuentaIntegrante;
import pe.com.tumi.credito.socio.captacion.bo.CaptacionBO;
import pe.com.tumi.credito.socio.captacion.bo.ConceptoBO;
import pe.com.tumi.credito.socio.captacion.bo.RequisitoBO;
import pe.com.tumi.credito.socio.captacion.domain.Captacion;
import pe.com.tumi.credito.socio.captacion.domain.CaptacionId;
import pe.com.tumi.credito.socio.captacion.domain.Concepto;
import pe.com.tumi.credito.socio.captacion.domain.Requisito;
import pe.com.tumi.credito.socio.convenio.domain.composite.CaptacionComp;
import pe.com.tumi.credito.socio.convenio.service.CaptacionService;
@Stateless
public class CaptacionFacade extends TumiFacade implements CaptacionFacadeRemote, CaptacionFacadeLocal {
	
	private CaptacionBO boCaptacion = (CaptacionBO)TumiFactory.get(CaptacionBO.class);
	private RequisitoBO boRequisito = (RequisitoBO)TumiFactory.get(RequisitoBO.class);
	private ConceptoBO boConcepto = (ConceptoBO)TumiFactory.get(ConceptoBO.class);
	private CaptacionService captacionService = (CaptacionService)TumiFactory.get(CaptacionService.class);
	
	public List<Captacion> listarCaptacion(CaptacionId o) throws BusinessException {
		List<Captacion> lista = null;
		try{
			lista = boCaptacion.getListaCaptacionPorPKCaptacion(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public Captacion listarCaptacionPorPK(CaptacionId o) throws BusinessException {
		Captacion dto=null;
		try{
			dto = boCaptacion.getCaptacionPorPK(o);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public List<CaptacionComp> getListaCaptacionCompDeBusquedaCaptacion(Captacion o) throws BusinessException{
		List<CaptacionComp> lista = null;
		try{
			lista = captacionService.getListaCaptacionCompDeBusquedaCaptacion(o);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public Captacion grabarCaptacion(Captacion o) throws BusinessException{
		Captacion captacion = null;
		try{
			captacion = captacionService.grabarCaptacion(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return captacion;
	}
	
	public CaptacionId eliminarCaptacion(CaptacionId o) throws BusinessException{
		CaptacionId captacionId = null;
		try{
			captacionId = boCaptacion.eliminarCaptacion(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return captacionId;
	}
	
	public Captacion modificarCaptacion(Captacion o) throws BusinessException{
		Captacion captacion = null;
		try{
			captacion = captacionService.modificarCaptacion(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return captacion;
	}
	
	public Captacion getCaptacionPorIdCaptacion(CaptacionId pId) throws BusinessException{
		Captacion captacion = null;
		try{
			captacion = captacionService.getCaptacionPorIdCaptacion(pId);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return captacion;
	}
	
	public List<Captacion> getCaptacionPorPKOpcional(CaptacionId o) throws BusinessException {
		List<Captacion> lista = null;
		try{
			lista = boCaptacion.getListaCaptacionPorPKOpcional(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<Requisito> getListaRequisitoPorPKCaptacion (CaptacionId o) throws BusinessException {
		List<Requisito> lista = null;
		try{
			lista = boRequisito.getListaRequisitoPorPKCaptacion(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<Concepto> getListaConceptoPorPKCaptacion (CaptacionId o) throws BusinessException {
		List<Concepto> lista = null;
		try{
			lista = boConcepto.getListaConceptoPorPKCaptacion(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return lista;
	}
	
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Captacion getCaptacionPorCuentaConceptoDetalle(CuentaConceptoDetalle cuentaConceptoDetalle) throws BusinessException{
    	Captacion dto = null;
    	try{
    		dto = boCaptacion.getCaptacionPorCuentaConceptoDetalle(cuentaConceptoDetalle);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return dto;
    }
}

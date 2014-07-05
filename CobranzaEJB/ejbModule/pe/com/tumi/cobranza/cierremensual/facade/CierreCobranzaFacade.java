package pe.com.tumi.cobranza.cierremensual.facade;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.log4j.Logger;

import pe.com.tumi.cobranza.cierremensual.bo.CierreCobranzaBO;
import pe.com.tumi.cobranza.cierremensual.bo.CierreCobranzaOperacionBO;
import pe.com.tumi.cobranza.cierremensual.bo.CierreCobranzaPlanillaBO;
import pe.com.tumi.cobranza.cierremensual.domain.CierreCobranza;
import pe.com.tumi.cobranza.cierremensual.domain.CierreCobranzaOperacion;
import pe.com.tumi.cobranza.cierremensual.domain.CierreCobranzaPlanilla;
import pe.com.tumi.cobranza.cierremensual.domain.composite.CierreCobranzaComp;
import pe.com.tumi.cobranza.cierremensual.service.CierreCobranzaService;
import pe.com.tumi.contabilidad.cierre.domain.LibroMayor;
import pe.com.tumi.contabilidad.cierre.domain.LibroMayorDetalle;
import pe.com.tumi.contabilidad.cierre.facade.CierreFacadeRemote;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.facade.TumiFacade;
import pe.com.tumi.framework.negocio.factory.TumiFactory;

@Stateless
public class CierreCobranzaFacade extends TumiFacade implements CierreCobranzaFacadeRemote, CierreCobranzaFacadeLocal {
	protected  static Logger log = Logger.getLogger(CierreCobranzaFacade.class);
	private CierreCobranzaBO cierreCobranzaBO = (CierreCobranzaBO)TumiFactory.get(CierreCobranzaBO.class);
	private CierreCobranzaOperacionBO cierreCobranzaOperacionBO = (CierreCobranzaOperacionBO)TumiFactory.get(CierreCobranzaOperacionBO.class);
	private CierreCobranzaPlanillaBO cierreCobranzaPlanillaBO = (CierreCobranzaPlanillaBO)TumiFactory.get(CierreCobranzaPlanillaBO.class);
	private CierreCobranzaService cierreCobranzaService = (CierreCobranzaService)TumiFactory.get(CierreCobranzaService.class);
	
	public List<CierreCobranzaComp> getListaCierreCobranzaBusq(CierreCobranzaComp o) throws BusinessException{
		List<CierreCobranzaComp> lista = null;
		try{
			lista = cierreCobranzaService.getListaCierreCobranza(o);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public CierreCobranza getCierreCobranza(CierreCobranza o) throws BusinessException{
    	CierreCobranza cierreCobranza = null;
		try{
			cierreCobranza = cierreCobranzaBO.getCierreCobranzaPorPK(o);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return cierreCobranza;
	}
    
    public CierreCobranza grabarCierreCobranza(CierreCobranza o) throws BusinessException {
    	CierreCobranza cierreCobranza = null;
		try{
			cierreCobranza = cierreCobranzaService.grabarCierreCobranza(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return cierreCobranza;
	}
    
    public CierreCobranza modificarCierreCobranza(CierreCobranza o) throws BusinessException {
    	CierreCobranza cierreCobranza = null;
		try{
			cierreCobranza = cierreCobranzaService.modificarCierreCobranza(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return cierreCobranza;
	}
    
    public List<CierreCobranzaOperacion> getListaCierreCobranzaOperacion(CierreCobranza o) throws BusinessException{
		List<CierreCobranzaOperacion> lista = null;
		try{
			lista = cierreCobranzaOperacionBO.getListaCierreOperacionPorPkCierreCobranza(o);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
    
    public List<CierreCobranzaPlanilla> getListaCierrePlanillaPorPkCierreCobranza(CierreCobranzaPlanilla o) throws BusinessException{
		List<CierreCobranzaPlanilla> lista = null;
		try{
			lista = cierreCobranzaPlanillaBO.getListaCierrePlanillaPorPkCierreCobranza(o);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
    
    //Inicio modificación cdelosrios, 27/08/2013
    public List<LibroMayor> buscarLibroMayor(LibroMayor o) throws BusinessException{
		List<LibroMayor> lista = null;
   		try{
   			CierreFacadeRemote cierreFacade = (CierreFacadeRemote)EJBFactory.getRemote(CierreFacadeRemote.class);
   			lista = cierreFacade.buscarLibroMayor(o);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return lista;
	}
    
    public LibroMayor grabarLibroMayor(LibroMayor o)throws BusinessException{
		LibroMayor dto = null;
		try{
			CierreFacadeRemote cierreFacade = (CierreFacadeRemote)EJBFactory.getRemote(CierreFacadeRemote.class);
			dto = cierreFacade.grabarLibroMayor(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public LibroMayor modificarLibroMayor(LibroMayor o)throws BusinessException{
		LibroMayor dto = null;
		try{
			CierreFacadeRemote cierreFacade = (CierreFacadeRemote)EJBFactory.getRemote(CierreFacadeRemote.class);
			dto = cierreFacade.modificarLibroMayor(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public List<LibroMayorDetalle> getListaLibroMayorDetallePorLibroMayor(LibroMayor libroMayor) throws BusinessException{
		List<LibroMayorDetalle> lista = null;
   		try{
   			CierreFacadeRemote cierreFacade = (CierreFacadeRemote)EJBFactory.getRemote(CierreFacadeRemote.class);
   			lista = cierreFacade.getListaLibroMayorDetallePorLibroMayor(libroMayor);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return lista;
	}
	
	public List<LibroMayor> getLibroMayorTodos() throws BusinessException{
		List<LibroMayor> lista = null;
   		try{
   			CierreFacadeRemote cierreFacade = (CierreFacadeRemote)EJBFactory.getRemote(CierreFacadeRemote.class);
   			LibroMayor o = new LibroMayor();
   			lista = cierreFacade.getLibroMayorTodos();
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return lista;
	}
    //Fin modificación cdelosrios, 27/08/2013
}
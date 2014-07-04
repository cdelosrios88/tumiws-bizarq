package pe.com.tumi.cobranza.gestion.facade;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.log4j.Logger;


import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.facade.TumiFacade;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.cobranza.gestion.bo.GestionCobranzaCierreBO;
import pe.com.tumi.cobranza.gestion.bo.GestionCobranzaSocBO;
import pe.com.tumi.cobranza.gestion.domain.GestionCobranza;
import pe.com.tumi.cobranza.gestion.domain.GestionCobranzaCierre;
import pe.com.tumi.cobranza.gestion.domain.GestionCobranzaEnt;
import pe.com.tumi.cobranza.gestion.domain.GestionCobranzaSoc;
import pe.com.tumi.cobranza.gestion.domain.GestorCobranza;
import pe.com.tumi.cobranza.gestion.domain.GestorCobranzaId;
import pe.com.tumi.cobranza.gestion.service.GestionCobranzaService;
import pe.com.tumi.cobranza.gestion.service.GestionCobranzaSocService;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.CuentaId;
import pe.com.tumi.persona.core.domain.Persona;

@Stateless
public class GestionCobranzaFacade extends TumiFacade implements GestionCobranzaFacadeRemote, GestionCobranzaFacadeLocal {
    
	protected  static Logger log = Logger.getLogger(GestionCobranzaFacade.class);
	
	private GestionCobranzaCierreBO gestionCobranzaCierreBO = (GestionCobranzaCierreBO)TumiFactory.get(GestionCobranzaCierreBO.class);
	
	private GestionCobranzaService gestionCobranzaService = (GestionCobranzaService)TumiFactory.get(GestionCobranzaService.class);
	private GestionCobranzaSocService gestionCobranzaSocService = (GestionCobranzaSocService)TumiFactory.get(GestionCobranzaSocService.class);
	//AUTOR Y FECHA CREACION: JCHAVEZ / 04.10.2013 
	private GestionCobranzaSocBO gestionCobranzaSocBO = (GestionCobranzaSocBO)TumiFactory.get(GestionCobranzaSocBO.class);
	
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public GestorCobranza existeGestorCobranza(GestorCobranzaId id) throws BusinessException{
    	log.info("-----------------------Debugging GestionCobranzaFacade.getListaGestionCobranza-----------------------------");
    	GestorCobranza gestorCob = null;
		try{
			  gestorCob = gestionCobranzaService.existeGestorCobranza(id);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return gestorCob;
	}
    
	
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<GestionCobranza> getListaGestionCobranza(GestionCobranza o) throws BusinessException{
    	log.info("-----------------------Debugging GestionCobranzaFacade.getListaGestionCobranza-----------------------------");
		List<GestionCobranza> lista = null;
		try{
			lista = gestionCobranzaService.getListaGestionCobranza(o);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
    
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public GestionCobranza getGestionCobranza(GestionCobranza o) throws BusinessException{
    	log.info("-----------------------Debugging GestionCobranzaFacade.getListaGestionCobranza-----------------------------");
    	GestionCobranza gestionCobranza = null;
		try{
			gestionCobranza = gestionCobranzaService.getGestionCobranzaPorPK(o);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return gestionCobranza;
	}
    
    public GestionCobranza grabarGestionCobranza(GestionCobranza o) throws BusinessException {
    	GestionCobranza gestionCobranza = null;
		try{
			
			gestionCobranza = gestionCobranzaService.grabarGestionCobranza(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return gestionCobranza;
	}
    
    public GestionCobranza modificarGestionCobranza(GestionCobranza o) throws BusinessException {
    	GestionCobranza gestionCobranza = null;
		try{
			gestionCobranza = gestionCobranzaService.modificarGestionCobranza(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return gestionCobranza;
	}
    
    public GestionCobranza elimnarGestionCobranza(GestionCobranza o) throws BusinessException {
    	GestionCobranza gestionCobranza = null;
		try{
			gestionCobranza = gestionCobranzaService.eliminarGestionCobranza(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return gestionCobranza;
	}
    
    public List<GestionCobranzaEnt> getListaGestionCobranzaEnt(GestionCobranzaEnt o) throws BusinessException {
		log.info("-----------------------Debugging GestionCobranzaService.getListaGestionCobranzaEnt-----------------------------");
		List<GestionCobranzaEnt> listGestionCobranzaEnt  = null;
		try{
			
			listGestionCobranzaEnt = gestionCobranzaService.getListaGestionCobranzaEnt(o);
		
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return listGestionCobranzaEnt;
	}
     
    	
    	 public void eliminarListaGestionCobranzaEnt(GestionCobranzaEnt o) throws BusinessException {
    			log.info("-----------------------Debugging GestionCobranzaService.getListaGestionCobranzaEnt-----------------------------");
    			
    			try{
    				
    				gestionCobranzaService.eliminarListaGestionCobranzaEnt(o);
    			
    			}catch(BusinessException e){
    				throw e;
    			}catch(Exception e){
    				throw new BusinessException(e);
    			}
    			
    	}
    	 
    	 
    	 public List<GestionCobranzaSoc>  getListaGestionCobranzaSoc(GestionCobranzaSoc o) throws BusinessException {
 			log.info("-----------------------Debugging getListaGestionCobranzaSoc-----------------------------");
 			List<GestionCobranzaSoc> listGestionCobranzaSoc  = null;
 			
 			try{
 				
 				listGestionCobranzaSoc = gestionCobranzaSocService.getListaGestionCobranzaSoc(o);
 			
 			}catch(BusinessException e){
 				throw e;
 			}catch(Exception e){
 				throw new BusinessException(e);
 			}
 			
 			return listGestionCobranzaSoc;
 	}
 	 
    	 public void eliminarGestionCobranzaSoc(GestionCobranzaSoc o) throws BusinessException {
  			log.info("-----------------------Debugging getListaGestionCobranzaSoc-----------------------------");
  			
  			try{
  				
  				gestionCobranzaSocService.eliminarGestionCobranzaSoc(o);
  			
  			}catch(BusinessException e){
  				throw e;
  			}catch(Exception e){
  				throw new BusinessException(e);
  			}	
  		}
  			
  		public GestionCobranzaCierre grabarGestionCobranzaCierre(GestionCobranzaCierre o) throws BusinessException {
  	  			log.info("-----------------------Debugging grabarGestionCobCierre-----------------------------");
  	  		GestionCobranzaCierre dto = null;	
	  			try{
  	  			      dto =  gestionCobranzaService.grabarGestionCobranzaCierre(o);
  	  			      
  	  			}catch(BusinessException e){
  	  				throw e;
  	  			}catch(Exception e){
  	  				throw new BusinessException(e);
  	  	  	    }
  	  		return dto;
  		  }
  
  	    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
  		public Date obtUltimaFechaGestion(GestionCobranzaCierre o) throws BusinessException 	{
  			Date dto = null;	
  			try{
	  			      dto =  gestionCobranzaCierreBO.obtUltimaFechaGestion(o);
	  			
	  			}catch(BusinessException e){
	  				throw e;
	  			}catch(Exception e){
	  				throw new BusinessException(e);
	  	  	    }
	  		return dto;
  		}
  	    
  	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public GestorCobranza getGestorCobranzaPorPersona(Persona persona, Integer intIdEmpresa) throws BusinessException{
	  GestorCobranza gestorCobranza = null;
		try{
			gestorCobranza = gestionCobranzaService.getGestorCobranzaPorPersona(persona, intIdEmpresa);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return gestorCobranza;
	}
  	/**
  	 * AUTOR Y FECHA CREACION: JCHAVEZ / 04.10.2013
	 * Recupera Gestion Cobranza Soc por Cuenta Id desde un peiorio en adelante
  	 * @param pId
  	 * @param dtFechaGestion
  	 * @return
  	 * @throws BusinessException
  	 */
  	public List<GestionCobranzaSoc> getListaPorCuentaPkYPeriodo(CuentaId pId, String strFechaGestion) throws BusinessException {
  		log.info("-----------------------Debugging GestionCobranzaFacade.getListaPorCuentaPkYPeriodo-----------------------------");
  		List<GestionCobranzaSoc> lista = null;
    	try{
    		lista = gestionCobranzaSocBO.getListaPorCuentaPkYPeriodo(pId, strFechaGestion);
    	}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
  	}
}
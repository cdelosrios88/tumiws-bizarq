package pe.com.tumi.cobranza.gestion.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.cobranza.gestion.bo.DocumentoCobranzaBO;
import pe.com.tumi.cobranza.gestion.bo.GestionCobranzaBO;
import pe.com.tumi.cobranza.gestion.bo.GestionCobranzaCierreBO;
import pe.com.tumi.cobranza.gestion.bo.GestionCobranzaEntBO;
import pe.com.tumi.cobranza.gestion.bo.GestionCobranzaSocBO;
import pe.com.tumi.cobranza.gestion.bo.GestorCobranzaBO;
import pe.com.tumi.cobranza.gestion.domain.DocumentoCobranza;
import pe.com.tumi.cobranza.gestion.domain.GestionCobranza;
import pe.com.tumi.cobranza.gestion.domain.GestionCobranzaCierre;
import pe.com.tumi.cobranza.gestion.domain.GestionCobranzaEnt;
import pe.com.tumi.cobranza.gestion.domain.GestionCobranzaEntId;
import pe.com.tumi.cobranza.gestion.domain.GestionCobranzaSoc;
import pe.com.tumi.cobranza.gestion.domain.GestionCobranzaSocId;
import pe.com.tumi.cobranza.gestion.domain.GestorCobranza;
import pe.com.tumi.cobranza.gestion.domain.GestorCobranzaId;
import pe.com.tumi.common.util.Constante;
import pe.com.tumi.common.util.UtilCobranza;
import pe.com.tumi.credito.socio.estructura.domain.EstructuraId;
import pe.com.tumi.credito.socio.estructura.facade.EstructuraFacadeRemote;
import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.empresa.domain.SucursalId;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.persona.contacto.domain.Documento;
import pe.com.tumi.persona.core.domain.Natural;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.persona.core.facade.PersonaFacadeRemote;
import pe.com.tumi.seguridad.empresa.facade.EmpresaFacadeRemote;

public class GestionCobranzaService {
	
	protected  static Logger log = Logger.getLogger(GestionCobranzaService.class);
	
	private GestionCobranzaBO boGestionCobranza = (GestionCobranzaBO)TumiFactory.get(GestionCobranzaBO.class);
	private GestionCobranzaCierreBO gestionCobranzaCierreBO = (GestionCobranzaCierreBO)TumiFactory.get(GestionCobranzaCierreBO.class);
	
	private GestorCobranzaBO boGestorCobranza = (GestorCobranzaBO)TumiFactory.get(GestorCobranzaBO.class);
	private DocumentoCobranzaBO boDocumentoCobranza = (DocumentoCobranzaBO)TumiFactory.get(DocumentoCobranzaBO.class);
	
	
	private GestionCobranzaEntBO boGestionCobranzaEnt = (GestionCobranzaEntBO)TumiFactory.get(GestionCobranzaEntBO.class);
	private GestionCobranzaSocBO boGestionCobranzaSoc = (GestionCobranzaSocBO)TumiFactory.get(GestionCobranzaSocBO.class);
	
	private GestionCobranzaEntService gestionCobranzaEntService = (GestionCobranzaEntService)TumiFactory.get(GestionCobranzaEntService.class);
	private GestionCobranzaSocService gestionCobranzaSocService = (GestionCobranzaSocService)TumiFactory.get(GestionCobranzaSocService.class);
	
	private Integer TIPO_ENTIDAD = 1;
	
	
	public GestorCobranza existeGestorCobranza(GestorCobranzaId id) throws BusinessException{
		
		List<GestorCobranza> listaGestorCobranza = null;
		GestorCobranza gestorCobranzaDos = null;
			try{
				GestorCobranza gestorCobranza = new GestorCobranza();
				
				gestorCobranza.getId().setIntPersEmpresaPk(id.getIntPersEmpresaPk());
				gestorCobranza.getId().setIntPersPersonaGestorPk(id.getIntPersPersonaGestorPk());
				
				listaGestorCobranza = boGestorCobranza.getListaGestorCobranza(gestorCobranza);
				
				if (listaGestorCobranza != null && listaGestorCobranza.size() > 0){
				  
				  for (int i = 0; i < listaGestorCobranza.size(); i++) {
					
					GestorCobranza gestorCob = (GestorCobranza)listaGestorCobranza.get(i);
					
					if (gestorCob.getId().getIntPersEmpresaPk().equals(id.getIntPersEmpresaPk()) &&
					    gestorCob.getId().getIntPersPersonaGestorPk().equals(id.getIntPersPersonaGestorPk())) {
						gestorCobranzaDos = gestorCob;
						 break;
					}
					
				  }	
				   			
				}
				
			}catch(BusinessException e){
				throw e;
			}catch(Exception e){
				throw new BusinessException(e);
			}
		
		return gestorCobranzaDos;
		
	}
	public List<GestionCobranza> getListaGestionCobranza(GestionCobranza o) throws BusinessException{
		log.info("-----------------------Debugging GestionCobranzaService.getListaGestionCobranza-----------------------------");
		List<GestionCobranza> lista = null;
		List<GestionCobranza> listaGestionCobranza = null;
		List<DocumentoCobranza> listaDocCobr = null;
		
			
		GestionCobranza gestionCobranza = null;
		
		try{
			listaGestionCobranza = boGestionCobranza.getListaGestionCobranza(o);
			log.info("listaGestionCobranza.size: "+listaGestionCobranza.size());
			lista = new ArrayList<GestionCobranza>();
			
			for(int i=0; i<listaGestionCobranza.size(); i++){
				boolean boEncontradoSucursal = false;
				boolean boEncontradoDni = false;
				gestionCobranza = listaGestionCobranza.get(i);
				
				PersonaFacadeRemote personaFaceRemote = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
				Persona persona = personaFaceRemote.getPersonaPorPK(gestionCobranza.getIntIdPersonaGestor());
				EmpresaFacadeRemote empresaFaceRemote = (EmpresaFacadeRemote)EJBFactory.getRemote(EmpresaFacadeRemote.class);
				
				gestionCobranza.setGestorCobranza(new GestorCobranza());
				Sucursal     suc = new Sucursal();
				SucursalId sucId = new SucursalId();
				sucId.setIntIdSucursal(gestionCobranza.getIntIdSucursalGestion());
				sucId.setIntPersEmpresaPk(gestionCobranza.getIntIdEmpresaGestor());
				suc.setId(sucId);
				Sucursal sucursal = empresaFaceRemote.getSucursalPorPK(suc);
				
			    gestionCobranza.getGestorCobranza().setSucursal(sucursal);
				List<Documento> listaDoc = persona.getListaDocumento();
				 
				for (Documento documento : listaDoc) {
					if (documento.getIntTipoIdentidadCod() == 1){
						persona.setDocumento(documento);
						break;
					}
				}
				

				if (gestionCobranza.getIntTipoGestionCobCod().equals(Constante.PARAM_T_TIPOCOBRANZA_ACTIVA)){
					   DocumentoCobranza doc =  new DocumentoCobranza();
					   doc.setIntParaTipoCobranza(Constante.PARAMA_T_TIPO_COBRANZA_ACTIVA);
					   listaDocCobr = boDocumentoCobranza.getListaDocumentoCobranza(doc);
				} 
				   else{
					   DocumentoCobranza doc =  new DocumentoCobranza();
					   doc.setIntParaTipoCobranza(Constante.PARAMA_T_TIPO_COBRANZA_PASIVA);
					   listaDocCobr = boDocumentoCobranza.getListaDocumentoCobranza(doc);
				}
				
				if (listaDocCobr != null)
				for (DocumentoCobranza docCob : listaDocCobr) {
					
					if (gestionCobranza.getIntItemDocCob() != null &&  gestionCobranza.getIntItemDocCob().equals(docCob.getId().getIntItemDocCob())){
						gestionCobranza.setDocCobranza(docCob);
						break;
					}
					
				} 
				
				
				
				
				Natural natural = personaFaceRemote.getNaturalPorPK(gestionCobranza.getIntIdPersonaGestor());
				persona.setNatural(natural);
				gestionCobranza.setPersona(persona);
				
				//Ini Filtro de busqueda por Sucursal y Dni
				 Integer tipoSucursal = 0;
				
				    if (o.getIntSucursal() == -3) {
				    	    tipoSucursal = 1; //Toda las agencias
				    }
				    else
				   	if (o.getIntSucursal() == -4){
					    	tipoSucursal = 2; //Toda las Filiales
					}
				    else
				    if (o.getIntSucursal() == -5){
					    	tipoSucursal = 4; //Toda las oficinas principal
				    }
				    if (o.getIntSucursal() == -6){
				    	    tipoSucursal = 3; //Toda las sedes
			        }
					    
				   
				    if (o.getIntSucursal().equals(Constante.PARAM_T_TODOS) && o.getStrNroDocuIdentidad().equals("")){
				    	lista.add(gestionCobranza);
				    }
				    else
				    {
				   	    //Para el combo sucursal
						    if (!o.getIntSucursal().equals(Constante.PARAM_T_TODOS))
						    {
						    	//Por su tipo
						    	if (tipoSucursal.equals(Constante.PARAM_T_TIPOSUCURSAL_AGENCIA)     || 
									tipoSucursal.equals(Constante.PARAM_T_TIPOSUCURSAL_FILIAL)      || 
									tipoSucursal.equals(Constante.PARAM_T_TIPOSUCURSAL_SEDECENTRAL) || 
									tipoSucursal.equals(Constante.PARAM_T_TIPOSUCURSAL_OFICINAPRINCIPAL)){
						    		
						    		if (gestionCobranza.getGestorCobranza().getSucursal().getIntIdTipoSucursal().equals(tipoSucursal)) 
						    		{
						    			boEncontradoSucursal = true;
									}
						    	
						    	}
						    	else
						    	 if (gestionCobranza.getGestorCobranza().getSucursal().getId().getIntIdSucursal().equals(o.getIntSucursal())){
						    		   boEncontradoSucursal = true;
								}
								    
						    }
						    //Para caja de texo dni
						    
						    if (!o.getStrNroDocuIdentidad().equals("")){
						    	
						    	if (o.getStrNroDocuIdentidad().equals(gestionCobranza.getPersona().getDocumento().getStrNumeroIdentidad())){
						    		boEncontradoDni = true;
								}
						    	
						    }
						    
						    
						    if (!o.getIntSucursal().equals(Constante.PARAM_T_TODOS) && !o.getStrNroDocuIdentidad().equals("")){
						    	
						    	if (boEncontradoSucursal && boEncontradoDni){
						    		lista.add(gestionCobranza);
						     	}
						    }
						    else
						    {
						        if (boEncontradoSucursal || boEncontradoDni){
						        	lista.add(gestionCobranza);
						        }
						    }
						    
					 }
				
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public GestionCobranza getGestionCobranzaPorPK(GestionCobranza o) throws BusinessException{
		log.info("-----------------------Debugging GestionCobranzaService.getGestionCobranzaPorPK-----------------------------");
		GestionCobranza gestionCobranza = null;
		
		try{
			gestionCobranza = boGestionCobranza.getGestionCobranzaPorPK(o);
			log.info("Se obtuvo GestionCobranzaPorPK...");
			
            PersonaFacadeRemote personaFaceRemote = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
			Persona persona = personaFaceRemote.getPersonaPorPK(gestionCobranza.getIntIdPersonaGestor());
			EmpresaFacadeRemote empresaFaceRemote = (EmpresaFacadeRemote)EJBFactory.getRemote(EmpresaFacadeRemote.class);
			
			gestionCobranza.setGestorCobranza(new GestorCobranza());
			Sucursal     suc = new Sucursal();
			SucursalId sucId = new SucursalId();
			sucId.setIntIdSucursal(gestionCobranza.getIntIdSucursalGestion());
			sucId.setIntPersEmpresaPk(gestionCobranza.getIntIdEmpresaGestor());
			suc.setId(sucId);
			Sucursal sucursal = empresaFaceRemote.getSucursalPorPK(suc);
			
		    gestionCobranza.getGestorCobranza().setSucursal(sucursal);
			List<Documento> listaDoc = persona.getListaDocumento();
			 
			for (Documento documento : listaDoc) {
				if (documento.getIntTipoIdentidadCod() == 1){
					persona.setDocumento(documento);
					break;
				}
			}
			
			if (boDocumentoCobranza.getListaDocumentoCobranza(new DocumentoCobranza()) != null)
			for (DocumentoCobranza docCob : boDocumentoCobranza.getListaDocumentoCobranza(new DocumentoCobranza())) {
				
				if (gestionCobranza.getIntItemDocCob() != null &&  gestionCobranza.getIntItemDocCob().equals(docCob.getId().getIntItemDocCob())){
					gestionCobranza.setDocCobranza(docCob);
					break;
				}
				
			} 
			
			Natural natural = personaFaceRemote.getNaturalPorPK(gestionCobranza.getIntIdPersonaGestor());
			persona.setNatural(natural);
			gestionCobranza.setPersona(persona);
			
			
	
			
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return gestionCobranza;
	}

	public GestionCobranza grabarGestionCobranza(GestionCobranza o) throws BusinessException {
		log.info("-----------------------Debugging GestionCobranzaService.grabarGestionCobranza-----------------------------");
		GestionCobranza gestionCobranza = null;
		
		try{
			
			   gestionCobranza = boGestionCobranza.grabarGestionCobranza(o);
			log.info(""+o.getId().getIntItemGestionCobranza());
			if (o.getIntTipoGestionCobCod().equals(Constante.PARAM_T_TIPOCOBRANZA_PROMOCION) || o.getIntTipoGestionCobCod().equals(Constante.PARAM_T_TIPOCOBRANZA_CHEQUE)){
				gestionCobranzaEntService.grabarDinamicoListaGestionCobranzaEnt(o);
			}else{
				grabarDinamicoListaGestionCobranzaDet(o);
			}
			
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return gestionCobranza;
	}

	public GestionCobranza modificarGestionCobranza(GestionCobranza o) throws BusinessException {
		log.info("-----------------------Debugging GestionCobranzaService.modificarGestionCobranza-----------------------------");
		GestionCobranza gestionCobranza = null;
		try{
			gestionCobranza = boGestionCobranza.modificarGestionCobranza(o);
			log.info(""+o.getId().getIntItemGestionCobranza());
			if (o.getIntTipoGestionCobCod().equals(Constante.PARAM_T_TIPOCOBRANZA_PROMOCION)){
			    gestionCobranzaEntService.grabarDinamicoListaGestionCobranzaEnt(o);
			}else
			if (o.getIntTipoGestionCobCod().equals(Constante.PARAM_T_TIPOCOBRANZA_TRAMITEADMIN)) {
				
				grabarDinamicoListaGestionCobranzaDet(o);
			}else
			if	(o.getIntTipoGestionCobCod().equals(Constante.PARAM_T_TIPOCOBRANZA_CHEQUE)) {
				
				modificarGestioCobranzaEntidad(o);
			}
			else{
				modificarDinamicoListaGestionCobranzaDet(o);
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return gestionCobranza;
	}

	public GestionCobranza eliminarGestionCobranza(GestionCobranza o) throws BusinessException {
		log.info("-----------------------Debugging GestionCobranzaService.eliminarGestionCobranza-----------------------------");
		GestionCobranza gestionCobranza = null;
		try{
			///o.set(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO); ??
			
			gestionCobranza = boGestionCobranza.modificarGestionCobranza(o);
		
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return gestionCobranza;
	}
	
	public List<GestionCobranzaEnt> getListaGestionCobranzaEnt(GestionCobranzaEnt o) throws BusinessException {
		log.info("-----------------------Debugging GestionCobranzaService.eliminarGestionCobranza-----------------------------");
		List<GestionCobranzaEnt> listGestionCobranzaEnt  = new ArrayList<GestionCobranzaEnt>();
		try{
			
			
			EstructuraFacadeRemote estructuraFacadeRemote = (EstructuraFacadeRemote)EJBFactory.getRemote(EstructuraFacadeRemote.class);
			PersonaFacadeRemote personaFacadeRemote = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
			
			
			for (GestionCobranzaEnt gestionCobranzaEnt :  boGestionCobranzaEnt.getListaGestionCobranzaEnt(o)) {
				EstructuraId id = new EstructuraId();
				id.setIntCodigo(gestionCobranzaEnt.getIntCodigo());
				id.setIntNivel(gestionCobranzaEnt.getIntNivel());
				gestionCobranzaEnt.setEstructura(estructuraFacadeRemote.getEstructuraPorPk(id));
				gestionCobranzaEnt.getEstructura().getJuridica().setPersona(personaFacadeRemote.getPersonaJuridicaPorIdPersona(gestionCobranzaEnt.getEstructura().getJuridica().getIntIdPersona()));
				listGestionCobranzaEnt.add(gestionCobranzaEnt);
			}
			
		
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return listGestionCobranzaEnt;
	}
	
	public void eliminarListaGestionCobranzaEnt(GestionCobranzaEnt gestionCobranzaEnt) throws BusinessException {
		log.info("-----------------------Debugging GestionCobranzaService.eliminarGestionCobranza-----------------------------");
		
		try{
			
			 gestionCobranzaEntService.eliminarListaGestionCobranzaEnt(gestionCobranzaEnt);
			
		
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		
	}
	
	public void grabarDinamicoListaGestionCobranzaDet(GestionCobranza o) throws BusinessException {
		List<Object> listaGestionCobranzaDet = null;
		GestionCobranzaSoc gestionCobranzaSoc = null;
		GestionCobranzaEnt gestionCobranzaEnt = null;
		GestionCobranzaEnt gestionCobranzaEntTemp = null;
		GestionCobranzaSoc gestionCobranzaSocTemp = null;
		
		try{
			listaGestionCobranzaDet = o.getListaGestionCobranzaDetalle();
			
			for(int i=0; i<listaGestionCobranzaDet.size(); i++){
				if (listaGestionCobranzaDet.get(i) instanceof GestionCobranzaEnt){
					
					gestionCobranzaEnt = (GestionCobranzaEnt)listaGestionCobranzaDet.get(i);
					log.info("GestionCobranzaEnt.intEstadoCod: "+gestionCobranzaEnt.getIntEstadoCod());
					log.info("GestionCobranzaEnt.intNivel: "+gestionCobranzaEnt.getIntNivel());
					log.info("GestionCobranzaEnt.id: "+gestionCobranzaEnt.getId());
					if(gestionCobranzaEnt.getId().getIntItemGestCobrEntidad() == null){
						gestionCobranzaEnt.setId(new GestionCobranzaEntId());
						gestionCobranzaEnt.getId().setIntPersEmpresaGestion(o.getId().getIntPersEmpresaGestionPK());
						gestionCobranzaEnt.getId().setIntItemGestionCobranza(o.getId().getIntItemGestionCobranza());
						gestionCobranzaEnt.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
						boGestionCobranzaEnt.grabarGestionCobranzaEnt(gestionCobranzaEnt);
					}else{
						gestionCobranzaEntTemp = boGestionCobranzaEnt.getGestionCobranzaEntPorPK(gestionCobranzaEnt);
						if(gestionCobranzaEntTemp == null){
							gestionCobranzaEnt = boGestionCobranzaEnt.grabarGestionCobranzaEnt(gestionCobranzaEnt);
						}else{
							gestionCobranzaEnt = boGestionCobranzaEnt.modificarGestionCobranzaEnt(gestionCobranzaEnt);
						}
					}
					log.info("GestionCobranzaEnt.id.intItemGestCobrEntidadPK: "+gestionCobranzaEnt.getId().getIntItemGestCobrEntidad());

				}
				else{
					gestionCobranzaSoc = (GestionCobranzaSoc)listaGestionCobranzaDet.get(i);
					log.info("GestionCobranzaSoc.intPersEmpresa: "+gestionCobranzaSoc.getIntPersEmpresa());
					log.info("GestionCobranzaSoc.IntPersPersona: "+gestionCobranzaSoc.getIntPersPersona());
					log.info("GestionCobranzaSoc.id: "+gestionCobranzaSoc.getId());
					if(gestionCobranzaSoc.getId().getIntItemGestCobrSocio() == null){
						gestionCobranzaSoc.setId(new GestionCobranzaSocId());
						gestionCobranzaSoc.getId().setIntPersEmpresaGestion(o.getId().getIntPersEmpresaGestionPK());
						gestionCobranzaSoc.getId().setIntItemGestionCobranza(o.getId().getIntItemGestionCobranza());
						gestionCobranzaSoc.setIntPersEmpresa(gestionCobranzaSoc.getSocio().getId().getIntIdEmpresa());
						gestionCobranzaSoc.setIntPersPersona(gestionCobranzaSoc.getSocio().getId().getIntIdPersona());
						gestionCobranzaSoc.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
						boGestionCobranzaSoc.grabarGestionCobranzaSoc(gestionCobranzaSoc);
					}else{
						gestionCobranzaSocTemp = boGestionCobranzaSoc.getGestionCobranzaSocPorPK(gestionCobranzaSoc);
						if(gestionCobranzaSocTemp == null){
							gestionCobranzaSoc = boGestionCobranzaSoc.grabarGestionCobranzaSoc(gestionCobranzaSoc);
						}else{
							gestionCobranzaSoc = boGestionCobranzaSoc.modificarGestionCobranzaSoc(gestionCobranzaSoc);
						}
				}
				log.info("GestionCobranzaSoc.id.intItemGestCobrSocidadPK: "+gestionCobranzaSoc.getId().getIntItemGestCobrSocio());
			  }	
				
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		
	}
	
	public void modificarDinamicoListaGestionCobranzaDet(GestionCobranza o) throws BusinessException {
		
		List<Object> listaGestionCobranzaDet = null;
		GestionCobranzaSoc gestionCobranzaSoc = null;
		
		try{
			listaGestionCobranzaDet = o.getListaGestionCobranzaDetalle();
			for(int i=0; i<listaGestionCobranzaDet.size(); i++){
			     gestionCobranzaSoc = (GestionCobranzaSoc)listaGestionCobranzaDet.get(i);
				 gestionCobranzaSoc.setId(new GestionCobranzaSocId());
			     gestionCobranzaSoc.getId().setIntPersEmpresaGestion(o.getId().getIntPersEmpresaGestionPK());
			     gestionCobranzaSoc.getId().setIntItemGestionCobranza(o.getId().getIntItemGestionCobranza());
			     gestionCobranzaSoc.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
			     gestionCobranzaSoc = boGestionCobranzaSoc.modificarGestionCobranzaSoc(gestionCobranzaSoc);
			     gestionCobranzaSoc.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			     gestionCobranzaSoc = boGestionCobranzaSoc.grabarGestionCobranzaSoc(gestionCobranzaSoc);
			     log.info("GestionCobranzaSoc.id.intItemGestCobrSocidadPK: "+gestionCobranzaSoc.getId().getIntItemGestCobrSocio());
		    }
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
    }
	
   public void modificarGestioCobranzaEntidad(GestionCobranza o) throws BusinessException {
		
		List<GestionCobranzaEnt> listaGestionCobranzaDet = null;
		GestionCobranzaEnt gestionCobranzaEnt = null;
		
		try{
			listaGestionCobranzaDet = o.getListaGestionCobranzaEnt();
			for(int i=0; i<listaGestionCobranzaDet.size(); i++){
				 gestionCobranzaEnt = (GestionCobranzaEnt)listaGestionCobranzaDet.get(i);
				 gestionCobranzaEnt.setId(new GestionCobranzaEntId());
				 gestionCobranzaEnt.getId().setIntPersEmpresaGestion(o.getId().getIntPersEmpresaGestionPK());
				 gestionCobranzaEnt.getId().setIntItemGestionCobranza(o.getId().getIntItemGestionCobranza());
				 gestionCobranzaEnt.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
				 gestionCobranzaEnt = boGestionCobranzaEnt.modificarGestionCobranzaEnt(gestionCobranzaEnt);
			     gestionCobranzaEnt.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			     gestionCobranzaEnt = boGestionCobranzaEnt.grabarGestionCobranzaEnt(gestionCobranzaEnt);
			     log.info("GestionCobranzaSoc.id.intItemGestCobrSocidadPK: "+gestionCobranzaEnt.getId().getIntItemGestCobrEntidad());
		    }
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
    }
	
	public GestionCobranzaCierre grabarGestionCobranzaCierre(GestionCobranzaCierre o) throws BusinessException {
		GestionCobranzaCierre dto = null;
		try{
			 dto =  gestionCobranzaCierreBO.grabarGestionCobranzaCierre(o);
			 
			 for (GestionCobranza gesCob:o.getListaGestionCobranza()){
				     gesCob.setIntItemcierregescobcie(dto.getId().getIntItemCierreGesCobCie());
				     gesCob.setDtFechaCierre(dto.getId().getDtFechaCierre());
				     String fechaCierre = UtilCobranza.convierteDateAString(gesCob.getDtFechaCierre());
				      if (gesCob.getIntHoraInicio() != null){
				    	 if  (gesCob.getIntMiniInicio() == null) gesCob.setIntMiniInicio(00);
				    	 if  (gesCob.getIntMiniFin() == null) gesCob.setIntMiniFin(00);
				          Timestamp timHoraInicio = UtilCobranza.convierteStringATimestamp(fechaCierre+" "+gesCob.getIntHoraInicio()+":"+gesCob.getIntMiniInicio());
					      gesCob.setDtHoraInicio(timHoraInicio);
				      }
				      if (gesCob.getIntHoraFin() != null){
				          Timestamp timHoraFin    = UtilCobranza.convierteStringATimestamp(fechaCierre+" "+gesCob.getIntHoraFin()+":"+gesCob.getIntMiniFin());
				          gesCob.setDtHoraFin(timHoraFin);
				      }
				   
					 boGestionCobranza.modificarGestionCobranza(gesCob);
			 }
			 
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		
		return dto;
	
	}
  	  	
	public GestorCobranza getGestorCobranzaPorPersona(Persona persona, Integer intIdEmpresa)throws Exception{
		GestorCobranza gestorCobranza = null;
		try{
			GestorCobranza gestorCobranzaFiltro = new GestorCobranza();
			gestorCobranzaFiltro.getId().setIntPersEmpresaPk(intIdEmpresa);
			gestorCobranzaFiltro.getId().setIntPersPersonaGestorPk(persona.getIntIdPersona());
			gestorCobranzaFiltro.setDtFechaActual(obtenerFechaActual());
			System.out.println(gestorCobranzaFiltro);
			
			gestorCobranza = boGestorCobranza.getPorPersona(gestorCobranzaFiltro);
			gestorCobranza.setPersona(persona);
		}catch(Exception e){
			throw new BusinessException(e);
		}		
		return gestorCobranza;
	}
	
	private Timestamp obtenerFechaActual(){
		return new Timestamp(new Date().getTime());
	}
	
	
}

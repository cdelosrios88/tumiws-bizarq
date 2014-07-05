package pe.com.tumi.cobranza.planilla.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pe.com.tumi.cobranza.planilla.bo.EstadoSolicitudCtaCteBO;
import pe.com.tumi.cobranza.planilla.bo.SolicitudCtaCteBO;
import pe.com.tumi.cobranza.planilla.bo.SolicitudCtaCteTipoBO;
import pe.com.tumi.cobranza.planilla.domain.EstadoSolicitudCtaCte;
import pe.com.tumi.cobranza.planilla.domain.EstadoSolicitudCtaCteId;
import pe.com.tumi.cobranza.planilla.domain.SolicitudCtaCte;
import pe.com.tumi.cobranza.planilla.domain.SolicitudCtaCteTipo;
import pe.com.tumi.cobranza.planilla.domain.SolicitudCtaCteTipoId;
import pe.com.tumi.common.util.Constante;
import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.persona.contacto.domain.Documento;
import pe.com.tumi.persona.contacto.facade.ContactoFacadeRemote;
import pe.com.tumi.persona.core.domain.Natural;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.persona.core.domain.PersonaEmpresaPK;
import pe.com.tumi.persona.core.domain.PersonaRol;
import pe.com.tumi.persona.core.facade.PersonaFacadeRemote;
import pe.com.tumi.persona.empresa.domain.Juridica;
import pe.com.tumi.seguridad.empresa.facade.EmpresaFacadeRemote;

public class SolicitudCtaCteService {
	
	private SolicitudCtaCteBO 	  boSolicitudCtaCte = (SolicitudCtaCteBO)TumiFactory.get(SolicitudCtaCteBO.class);
	private SolicitudCtaCteTipoBO boSolicitudCtaCteTipo = (SolicitudCtaCteTipoBO)TumiFactory.get(SolicitudCtaCteTipoBO.class);
	private EstadoSolicitudCtaCteBO boEstadoSolicitudCtaCte = (EstadoSolicitudCtaCteBO)TumiFactory.get(EstadoSolicitudCtaCteBO.class);
	
	
	public List<SolicitudCtaCte> buscarSolicitudCtaCte(Integer intEmpresasolctacte,Integer intSucuIdsucursalsocio,Integer intTipoSolicitud,Integer intEstadoSolicitud,String nroDni) throws BusinessException{
		
		List<SolicitudCtaCte> listaSolicitudCtaCteTemp = null;
		List<SolicitudCtaCte> listaSolicitudCtaCte = new ArrayList<SolicitudCtaCte>();
		
		
		try{
			
			PersonaFacadeRemote personaFacade = (PersonaFacadeRemote) EJBFactory.getRemote(PersonaFacadeRemote.class);
			EmpresaFacadeRemote empresaFacade = (EmpresaFacadeRemote) EJBFactory.getRemote(EmpresaFacadeRemote.class);
			
			
			listaSolicitudCtaCteTemp = boSolicitudCtaCte.getListaSolicitudCtaCtePorEmpYSucTipSolYEstSol(intEmpresasolctacte, intSucuIdsucursalsocio, intTipoSolicitud, intEstadoSolicitud);
			
			for (SolicitudCtaCte solicitudCtaCte : listaSolicitudCtaCteTemp) {
				boolean existeDni = false;
				boolean existeTipoSol = false;
				
				
				Sucursal sucursal = new Sucursal();
				sucursal.getId().setIntIdSucursal(solicitudCtaCte.getIntSucuIdsucursalsocio());
				sucursal.getId().setIntPersEmpresaPk(solicitudCtaCte.getIntPersEmpresa());
				sucursal = empresaFacade.getSucursalPorPK(sucursal);
				
				Juridica juridica = personaFacade.getJuridicaPorPK(sucursal.getIntPersPersonaPk());
				solicitudCtaCte.setSucursal(juridica);
				
				if (intTipoSolicitud == 0){
					Integer intPersEmpresasolctacte = solicitudCtaCte.getId().getIntEmpresasolctacte();
					Integer intCcobItemsolctacte = solicitudCtaCte.getId().getIntCcobItemsolctacte();
					List<SolicitudCtaCteTipo> listaSolTipoCtaCte =  boSolicitudCtaCteTipo.getListaPorSolicitudCtacte(intPersEmpresasolctacte, intCcobItemsolctacte);
					solicitudCtaCte.setListaSolCtaCteTipo(listaSolTipoCtaCte);
					existeTipoSol = true;
				}
				else
				{	
					Integer intPersEmpresasolctacte = solicitudCtaCte.getId().getIntEmpresasolctacte();
					Integer intCcobItemsolctacte = solicitudCtaCte.getId().getIntCcobItemsolctacte();
					List<SolicitudCtaCteTipo> listaSolTipoCtaCte =  boSolicitudCtaCteTipo.getListaPorSolicitudCtacte(intPersEmpresasolctacte, intCcobItemsolctacte);
					List<SolicitudCtaCteTipo> listaSolTipoCtaCteTemp = new ArrayList<SolicitudCtaCteTipo> ();
					for (SolicitudCtaCteTipo solicitudCtaCteTipo : listaSolTipoCtaCte) {
						if(solicitudCtaCteTipo.getId().getIntTipoSolicitudctacte().equals(intTipoSolicitud)){
							listaSolTipoCtaCteTemp.add(solicitudCtaCteTipo);
							existeTipoSol = true;
						}
					}
					
					solicitudCtaCte.setListaSolCtaCteTipo(listaSolTipoCtaCteTemp);
				}
				
				EstadoSolicitudCtaCteId ppId = new EstadoSolicitudCtaCteId();
				ppId.setIntPersEmpresaSolctacte(solicitudCtaCte.getId().getIntEmpresasolctacte());
				ppId.setIntCcobItemSolCtaCte(solicitudCtaCte.getId().getIntCcobItemsolctacte());
				ppId.setIntCcobItemEstado(solicitudCtaCte.getEstSolCtaCte().getId().getIntCcobItemEstado());
				EstadoSolicitudCtaCte estSolCtaCte = boEstadoSolicitudCtaCte.getEstadoSolicitudCtaCtePorPk(ppId);
				solicitudCtaCte.setEstSolCtaCte(estSolCtaCte);
				
				Natural socio  = personaFacade.getNaturalPorPK(solicitudCtaCte.getIntPersPersona());
				solicitudCtaCte.setSocio(socio);
				
				Natural usuario  = personaFacade.getNaturalPorPK(estSolCtaCte.getIntPersUsuarioEstado());
				solicitudCtaCte.setUsuario(usuario);
				
				if (nroDni != null) nroDni.trim();  
				
				if (!nroDni.equals("")){
					Persona persona = personaFacade.getPersonaNaturalPorDocIdentidadYIdEmpresa(1, nroDni, solicitudCtaCte.getId().getIntEmpresasolctacte());
					if (persona != null){
						 if (solicitudCtaCte.getSocio().getIntIdPersona().equals(persona.getIntIdPersona())){
							 solicitudCtaCte.setSocio(persona.getNatural());
							 existeDni = true;
						 }
					}else
					{
					   break;
					}
				}
				else{
					 if (existeTipoSol == true){
					     listaSolicitudCtaCte.add(solicitudCtaCte);
					 }
				}
				
				if(existeTipoSol && existeDni){
					 listaSolicitudCtaCte.add(solicitudCtaCte);
				}
				
			}
			
			return listaSolicitudCtaCte;
			
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
	}
	
public List<SolicitudCtaCte> buscarMovimientoCtaCte(Integer intEmpresasolctacte,Integer intSucuIdsucursalsocio,Integer intTipoSolicitud,Integer intEstadoSolicitud,String nroDni,Integer intCboParaTipoEstado,
                                                    Date    dtFechaInicio,Date dtFechaFin) throws BusinessException{
		
		List<SolicitudCtaCte> listaSolicitudCtaCteTemp = null;
		List<SolicitudCtaCte> listaSolicitudCtaCte = new ArrayList<SolicitudCtaCte>();
		
		
		try{
			
			PersonaFacadeRemote  personaFacade = (PersonaFacadeRemote) EJBFactory.getRemote(PersonaFacadeRemote.class);
			EmpresaFacadeRemote  empresaFacade = (EmpresaFacadeRemote) EJBFactory.getRemote(EmpresaFacadeRemote.class);
			ContactoFacadeRemote contactoFacade = (ContactoFacadeRemote) EJBFactory.getRemote(ContactoFacadeRemote.class);
			  
			
			
			listaSolicitudCtaCteTemp = boSolicitudCtaCte.getListaSolicitudCtaCtePorEmpYSucTipSolYEstSolAten(intEmpresasolctacte, intSucuIdsucursalsocio, intTipoSolicitud, intEstadoSolicitud);
			
			for (SolicitudCtaCte solicitudCtaCte : listaSolicitudCtaCteTemp) {
				boolean existeDni = false;
				boolean existeEstadoSol = false;
				boolean existeEstadoSolEsp = false;
				
				boolean existeFechInicio = false;
				boolean existeFechFin = false;
				
				
				
				
				Sucursal sucursal = new Sucursal();
				sucursal.getId().setIntIdSucursal(solicitudCtaCte.getIntSucuIdsucursalsocio());
				sucursal.getId().setIntPersEmpresaPk(solicitudCtaCte.getIntPersEmpresa());
				sucursal = empresaFacade.getSucursalPorPK(sucursal);
				
				Juridica juridica = personaFacade.getJuridicaPorPK(sucursal.getIntPersPersonaPk());
				solicitudCtaCte.setSucursal(juridica);
				
					Integer intPersEmpresasolctacte = solicitudCtaCte.getId().getIntEmpresasolctacte();
					Integer intCcobItemsolctacte = solicitudCtaCte.getId().getIntCcobItemsolctacte();
					List<SolicitudCtaCteTipo> listaSolTipoCtaCte =  boSolicitudCtaCteTipo.getListaPorSolicitudCtacte(intPersEmpresasolctacte, intCcobItemsolctacte);
					solicitudCtaCte.setListaSolCtaCteTipo(listaSolTipoCtaCte);
				
				
				Integer intPersEmpresasolctacte2 = solicitudCtaCte.getId().getIntEmpresasolctacte();
				Integer intCcobItemsolctacte2 = solicitudCtaCte.getId().getIntCcobItemsolctacte();
			
				List<EstadoSolicitudCtaCte> listaEstadoSolicitudCtaCte  = boEstadoSolicitudCtaCte.getListaPorSolicitudCtacte(intPersEmpresasolctacte2, intCcobItemsolctacte2);
				List<EstadoSolicitudCtaCte> listaEstSolCtaCteTemp = new ArrayList<EstadoSolicitudCtaCte>();
				
				if (listaEstSolCtaCteTemp != null)
				solicitudCtaCte.setNroEstadosSolicitud(listaEstSolCtaCteTemp.size());
				
				for (EstadoSolicitudCtaCte estadoSolicitudCtaCte : listaEstadoSolicitudCtaCte) {
					
					if (intEstadoSolicitud == 0) {
						if (estadoSolicitudCtaCte.getIntParaEstadoSolCtaCte().equals(Constante.PARAM_T_TIPESTADOSOLCITUD_APROBADO) ||
						    estadoSolicitudCtaCte.getIntParaEstadoSolCtaCte().equals(Constante.PARAM_T_TIPESTADOSOLCITUD_RECHAZADO))
						{	
							Natural usuario  = personaFacade.getNaturalPorPK(estadoSolicitudCtaCte.getIntPersUsuarioEstado());
							estadoSolicitudCtaCte.setUsuarioAtencion(usuario);
							listaEstSolCtaCteTemp.add(estadoSolicitudCtaCte);
							existeEstadoSol = true;
							
						}
						else
						if (estadoSolicitudCtaCte.getIntParaEstadoSolCtaCte().equals(Constante.PARAM_T_TIPESTADOSOLCITUD_PENDIENTE)){
							Natural usuario  = personaFacade.getNaturalPorPK(estadoSolicitudCtaCte.getIntPersUsuarioEstado());
							estadoSolicitudCtaCte.setUsuarioSolicitud(usuario);
							listaEstSolCtaCteTemp.add(estadoSolicitudCtaCte);
							existeEstadoSol = true;
						}
						else
						if (estadoSolicitudCtaCte.getIntParaEstadoSolCtaCte().equals(Constante.PARAM_T_TIPESTADOSOLCITUD_ANULADO)){
							existeEstadoSol = false;
						}
						
					}else{
						
						if (estadoSolicitudCtaCte.getIntParaEstadoSolCtaCte().equals(Constante.PARAM_T_TIPESTADOSOLCITUD_ATENDIDO))
						{	
								Natural usuario  = personaFacade.getNaturalPorPK(estadoSolicitudCtaCte.getIntPersUsuarioEstado());
								estadoSolicitudCtaCte.setUsuarioAtencion(usuario);
								listaEstSolCtaCteTemp.add(estadoSolicitudCtaCte);
						        existeEstadoSol = true;
						} 
						else
						if (estadoSolicitudCtaCte.getIntParaEstadoSolCtaCte().equals(Constante.PARAM_T_TIPESTADOSOLCITUD_PENDIENTE)){
							Natural usuario  = personaFacade.getNaturalPorPK(estadoSolicitudCtaCte.getIntPersUsuarioEstado());
							estadoSolicitudCtaCte.setUsuarioSolicitud(usuario);
							listaEstSolCtaCteTemp.add(estadoSolicitudCtaCte);
							existeEstadoSol = true;
						}
					}
						
					
					
				}	
					 //Buscamos la fecha del estado segun el rango dada.
				if (intCboParaTipoEstado == 1){	
						for (EstadoSolicitudCtaCte estSolCtaCte : listaEstSolCtaCteTemp) {
							
							if (estSolCtaCte.getIntParaEstadoSolCtaCte().equals(Constante.PARAM_T_TIPESTADOSOLCITUD_PENDIENTE))
							 {
								if ((estSolCtaCte.getDtEsccFechaEstado().after(dtFechaInicio) || estSolCtaCte.getDtEsccFechaEstado().equals(dtFechaInicio)) &&
								    (estSolCtaCte.getDtEsccFechaEstado().before(dtFechaFin)   || estSolCtaCte.getDtEsccFechaEstado().equals(dtFechaFin))	
								   ){
									existeFechInicio = true;
									existeFechFin = true;
								 }
							
							 }	
						}	
				}	
				else
					 if (intCboParaTipoEstado == 2){
						 
						 for (EstadoSolicitudCtaCte estSolCtaCte : listaEstSolCtaCteTemp) {
						    if (estSolCtaCte.getIntParaEstadoSolCtaCte().equals(Constante.PARAM_T_TIPESTADOSOLCITUD_APROBADO) ||
						    		estSolCtaCte.getIntParaEstadoSolCtaCte().equals(Constante.PARAM_T_TIPESTADOSOLCITUD_RECHAZADO))
							 {
						    	  if ((estSolCtaCte.getDtEsccFechaEstado().after(dtFechaInicio) || estSolCtaCte.getDtEsccFechaEstado().equals(dtFechaInicio)) &&
									    (estSolCtaCte.getDtEsccFechaEstado().before(dtFechaFin) || estSolCtaCte.getDtEsccFechaEstado().equals(dtFechaFin))	
									   ){
										existeFechInicio = true;
										existeFechFin = true;
								  }
							 }	
						 }  
				 }
					
					
				solicitudCtaCte.setListaEstSolCtaCte(listaEstadoSolicitudCtaCte);
				
				//Busca Estado Especifico de LA Cta Cte
				for (EstadoSolicitudCtaCte estadoSolicitudCtaCte2 : listaEstadoSolicitudCtaCte) {
					
					if (estadoSolicitudCtaCte2.getIntParaEstadoSolCtaCte().equals(intEstadoSolicitud)){
						existeEstadoSolEsp = true;
					}
					
				}
				
				
				Natural socio  = personaFacade.getNaturalPorPK(solicitudCtaCte.getIntPersPersona());
				solicitudCtaCte.setSocio(socio);
				
				solicitudCtaCte.setDocumentoSocio(contactoFacade.getDocumentoPorIdPersonaYTipoIdentidad(solicitudCtaCte.getIntPersPersona(), 1));
				
				PersonaEmpresaPK pk = new PersonaEmpresaPK();
			        pk.setIntIdEmpresa(solicitudCtaCte.getIntPersEmpresa());
			        pk.setIntIdPersona(solicitudCtaCte.getIntPersPersona());
			        
			    List<PersonaRol>  listaPersonaSocioRol =   personaFacade.getListaPersonaRolPorPKPersonaEmpresa(pk);
			    solicitudCtaCte.setListaPersonaSocioRol(listaPersonaSocioRol);
				
				
				if (nroDni != null) nroDni.trim();  
				if (!nroDni.equals("")){
					
					Persona persona = personaFacade.getPersonaPorPK(solicitudCtaCte.getIntPersPersona());
					
					if (persona != null){
						
						List<Documento> listDoc = persona.getListaDocumento();
						for (Documento documento : listDoc) {
							
							if (documento.getIntTipoIdentidadCod().equals(1) && documento.getStrNumeroIdentidad().equals(nroDni)){
								 Natural natural = personaFacade.getNaturalPorPK(solicitudCtaCte.getIntPersPersona());
								 //solicitudCtaCte.setSocio(persona.getNatural());
								 existeDni = true;
								 break;
							}
							
						}
					  
					}else
					{
					   break;
					}
				}
								
				if (existeEstadoSol == true){
						//VVV
						if ((nroDni == null ||  nroDni.equals("")) && intEstadoSolicitud == 0  && intCboParaTipoEstado == 0){
							listaSolicitudCtaCte.add(solicitudCtaCte);
						}
						//fvv
						else
						if ((!nroDni.trim().equals("")) && intEstadoSolicitud == 0  && intCboParaTipoEstado == 0){
							if(existeDni){
								listaSolicitudCtaCte.add(solicitudCtaCte);
								break;
							}
						}
						//ffv
						else
						if ((!nroDni.trim().equals("")) && intEstadoSolicitud != 0  && intCboParaTipoEstado == 0){
							if(existeDni && existeEstadoSolEsp)
							listaSolicitudCtaCte.add(solicitudCtaCte);
						}
						//vvv
						else
						if ((!nroDni.trim().equals("")) && intEstadoSolicitud != 0  && intCboParaTipoEstado != 0){
							if(existeDni && existeEstadoSolEsp &&(existeFechInicio || existeFechFin))
							listaSolicitudCtaCte.add(solicitudCtaCte);
						}
						//fvf
						else
						if ((!nroDni.trim().equals("")) && intEstadoSolicitud == 0  && intCboParaTipoEstado != 0){
							if(existeDni && (existeFechInicio || existeFechFin))
							listaSolicitudCtaCte.add(solicitudCtaCte);
						}
						//vfv
						else
						if ((nroDni == null ||  nroDni.equals("")) && intEstadoSolicitud != 0  && intCboParaTipoEstado == 0){
								if(existeEstadoSolEsp)
								listaSolicitudCtaCte.add(solicitudCtaCte);
						}
						//vvf
						else
						if ((nroDni == null ||  nroDni.equals("")) && intEstadoSolicitud == 0  && intCboParaTipoEstado != 0){
							 if(existeFechInicio || existeFechFin)
							 listaSolicitudCtaCte.add(solicitudCtaCte);
						}
						//vff
						else
						if ((nroDni == null ||  nroDni.equals("")) && intEstadoSolicitud != 0  && intCboParaTipoEstado != 0){
							 if(existeEstadoSolEsp && (existeFechInicio || existeFechFin))
							 listaSolicitudCtaCte.add(solicitudCtaCte);
						}
				}
			}
			
			return listaSolicitudCtaCte;
			
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
	}
	
	public SolicitudCtaCte grabarSolicitudCtaCte(SolicitudCtaCte o) throws BusinessException
	{
		SolicitudCtaCte solCtaCte = null;
		try{
		  solCtaCte = boSolicitudCtaCte.grabarSolicitudCtaCte(o);
		  o.getEstSolCtaCte().getId().setIntCcobItemSolCtaCte(solCtaCte.getId().getIntCcobItemsolctacte());
		  o.getEstSolCtaCte().getId().setIntPersEmpresaSolctacte(solCtaCte.getId().getIntEmpresasolctacte());
		  boEstadoSolicitudCtaCte.grabarEstadoSolicitudCtaCte(o.getEstSolCtaCte());
		  for (SolicitudCtaCteTipo solCtaCteTipo:o.getListaSolCtaCteTipo()){
			  solCtaCteTipo.getId().setIntCcobItemsolctacte(solCtaCte.getId().getIntCcobItemsolctacte());
			  solCtaCteTipo.getId().setIntPersEmpresasolctacte(solCtaCte.getId().getIntEmpresasolctacte());
			  boSolicitudCtaCteTipo.grabarSolicitudCtaCteTipo(solCtaCteTipo);
		  }
			}catch(BusinessException e){
				throw e;
			}catch(Exception e){
				throw new BusinessException(e);
		   }
	  return solCtaCte;
   }	
	
	public SolicitudCtaCte modificarSolicitudCtaCte(SolicitudCtaCte o) throws BusinessException
	{
		SolicitudCtaCte solCtaCte = null;
		try{
		  solCtaCte = boSolicitudCtaCte.modificarSolicitudCtaCte(o);
		  o.getEstSolCtaCte().getId().setIntPersEmpresaSolctacte(solCtaCte.getId().getIntEmpresasolctacte());
		  boEstadoSolicitudCtaCte.modificarEstadoSolicitudCtaCte(o.getEstSolCtaCte());
		  
		   List<SolicitudCtaCteTipo> listaSolCtaTipo = o.getListaSolCtaCteTipo();
		   for (SolicitudCtaCteTipo solCtaCteTipo:listaSolCtaTipo){
			    
			    SolicitudCtaCteTipoId pId = new SolicitudCtaCteTipoId();
			    pId.setIntCcobItemsolctacte(solCtaCte.getId().getIntCcobItemsolctacte());
			    pId.setIntPersEmpresasolctacte(solCtaCte.getId().getIntEmpresasolctacte());
			    pId.setIntTipoSolicitudctacte(solCtaCteTipo.getId().getIntTipoSolicitudctacte());
                			   
			    SolicitudCtaCteTipo solCtaCteTipo2 =  boSolicitudCtaCteTipo.getSolicitudCtaCteTipoPorPk(pId);
			    
			    if (solCtaCteTipo2 == null){
			  	    solCtaCteTipo.getId().setIntCcobItemsolctacte(solCtaCte.getId().getIntCcobItemsolctacte());
				    solCtaCteTipo.getId().setIntPersEmpresasolctacte(solCtaCte.getId().getIntEmpresasolctacte());
				    boSolicitudCtaCteTipo.grabarSolicitudCtaCteTipo(solCtaCteTipo);
			    }
			    else{
			    	solCtaCteTipo2.setIntTaraEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			    	boSolicitudCtaCteTipo.modificarSolicitudCtaCteTipo(solCtaCteTipo2);
			    }
		    }
			}catch(BusinessException e){
				throw e;
			}catch(Exception e){
				throw new BusinessException(e);
		   }
	  return solCtaCte;
   }	
	
   public 	SolicitudCtaCte anularSolicitudCtaCte(SolicitudCtaCte o)throws BusinessException
   {
	   try{
	       o.getEstSolCtaCte().getId().setIntCcobItemSolCtaCte(o.getId().getIntCcobItemsolctacte());
		   o.getEstSolCtaCte().getId().setIntPersEmpresaSolctacte(o.getId().getIntEmpresasolctacte());
		   
		   List<EstadoSolicitudCtaCte> lista  =  boEstadoSolicitudCtaCte.getListaPorSolicitudCtacte(o.getId().getIntEmpresasolctacte(),o.getId().getIntCcobItemsolctacte());
		  
		   for (EstadoSolicitudCtaCte estadoSolicitudCtaCte : lista) {
			  estadoSolicitudCtaCte.setIntParaEstadoSolCtaCte(Constante.PARAM_T_TIPESTADOSOLCITUD_ANULADO);
			  estadoSolicitudCtaCte.setIntPersEmpresaEstado(o.getEstSolCtaCte().getIntPersEmpresaEstado());
			  estadoSolicitudCtaCte.setIntPersUsuarioEstado(o.getEstSolCtaCte().getIntPersUsuarioEstado());
			  estadoSolicitudCtaCte.setIntSucuIduSusucursal(o.getEstSolCtaCte().getIntSucuIduSusucursal());
			  estadoSolicitudCtaCte.setIntSudeIduSusubsucursal(o.getEstSolCtaCte().getIntSudeIduSusubsucursal());
			  o.setEstSolCtaCte(estadoSolicitudCtaCte);
			  break;
		   }
		   
	
	       boEstadoSolicitudCtaCte.modificarEstadoSolicitudCtaCte(o.getEstSolCtaCte());
	       
	       
	    }catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
	  }
		
		return o;
   }
   
   
   public 	SolicitudCtaCte obtenerSolicitudCtaCte(SolicitudCtaCte o)throws BusinessException
   {
	   try{
	       
	     List<EstadoSolicitudCtaCte> lista =   boEstadoSolicitudCtaCte.getListaPorSolicitudCtacte(o.getId().getIntEmpresasolctacte(), o.getId().getIntCcobItemsolctacte());
	     for (EstadoSolicitudCtaCte estSolCtaCte : lista) {
	    	 o.setEstSolCtaCte(estSolCtaCte);
	    	 break;
		 } 
	     List<SolicitudCtaCteTipo> listaTipo =   boSolicitudCtaCteTipo.getListaPorSolicitudCtacte(o.getId().getIntEmpresasolctacte(), o.getId().getIntCcobItemsolctacte());
		     o.setListaSolCtaCteTipo(listaTipo);
		 
		// solCtaCte = o;
	    }catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
	  }
		
	 return  o;
   }
   
   
	
	
}

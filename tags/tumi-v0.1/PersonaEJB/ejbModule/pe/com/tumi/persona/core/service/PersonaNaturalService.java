package pe.com.tumi.persona.core.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.common.util.FileUtil;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.parametro.general.domain.Archivo;
import pe.com.tumi.parametro.general.domain.ArchivoId;
import pe.com.tumi.parametro.general.facade.GeneralFacadeRemote;
import pe.com.tumi.persona.contacto.bo.DocumentoBO;
import pe.com.tumi.persona.contacto.domain.Documento;
import pe.com.tumi.persona.core.bo.NaturalBO;
import pe.com.tumi.persona.core.bo.PersonaBO;
import pe.com.tumi.persona.core.bo.PersonaEmpresaBO;
import pe.com.tumi.persona.core.domain.Natural;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.persona.core.domain.PersonaEmpresa;
import pe.com.tumi.persona.core.domain.PersonaEmpresaPK;
import pe.com.tumi.persona.core.domain.PersonaRol;
import pe.com.tumi.persona.empresa.bo.PerLaboralBO;
import pe.com.tumi.persona.empresa.domain.PerLaboral;
import pe.com.tumi.persona.empresa.service.PerLaboralService;
import pe.com.tumi.persona.vinculo.bo.VinculoBO;
import pe.com.tumi.persona.vinculo.domain.Vinculo;

public class PersonaNaturalService {
	
	protected  static Logger log = Logger.getLogger(PersonaNaturalService.class);
	private PersonaBO boPersona = (PersonaBO)TumiFactory.get(PersonaBO.class);
	private NaturalBO boNatural = (NaturalBO)TumiFactory.get(NaturalBO.class);
	private DocumentoBO boDocumento = (DocumentoBO)TumiFactory.get(DocumentoBO.class);
	private PersonaEmpresaBO boPersonaEmpresa = (PersonaEmpresaBO)TumiFactory.get(PersonaEmpresaBO.class);
	private PerLaboralBO boPerLaboral = (PerLaboralBO)TumiFactory.get(PerLaboralBO.class);
	private PersonaEmpresaService personaEmpresaService = (PersonaEmpresaService)TumiFactory.get(PersonaEmpresaService.class);
	private PersonaDetalleService detallePersonaService = (PersonaDetalleService)TumiFactory.get(PersonaDetalleService.class);
	private PersonaRolService personaRolService = (PersonaRolService)TumiFactory.get(PersonaRolService.class);
	private PerLaboralService perLaboralService = (PerLaboralService)TumiFactory.get(PerLaboralService.class);
	private VinculoBO boVinculo = (VinculoBO)TumiFactory.get(VinculoBO.class);
	
	public Persona grabarPersonaNatural(Persona pPersona)throws BusinessException {
		Persona persona = null;
		try{
				persona = boPersona.grabarPersona(pPersona);
				pPersona.getNatural().setIntIdPersona(persona.getIntIdPersona());
				boNatural.grabarNatural(pPersona.getNatural());
				
				if(pPersona.getPersonaEmpresa()!=null){
					personaEmpresaService.grabarDinamicaPersonaEmpresa(pPersona.getPersonaEmpresa(), persona.getIntIdPersona());
				}else if(pPersona.getListaPersonaEmpresa()!=null && pPersona.getListaPersonaEmpresa().size()!=0){
					personaEmpresaService.grabarListaDinamicaPersonaEmpresa(pPersona.getListaPersonaEmpresa(), persona.getIntIdPersona());
				}else{
					throw new BusinessException("La Persona Debe Asociarse a una Empresa");
				}
				
				detallePersonaService.grabarDinamicoDetalleDePersonaPorIdPersona(pPersona,persona.getIntIdPersona());
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return persona;
	}
	
	public Persona grabarPersonaNaturalYPerLaboral(Persona pPersona)throws BusinessException {
		Persona persona = null;
		try{
			//Grabar Persona Natural y su detalle
			persona = grabarPersonaNatural(pPersona);
			
			//Grabar Archivo Firma de Natural
			if(persona.getNatural().getFirma()!=null){
				String strOldName = persona.getNatural().getFirma().getStrNombrearchivo();
				
				GeneralFacadeRemote generalFacade = (GeneralFacadeRemote) EJBFactory.getRemote(GeneralFacadeRemote.class);
				Archivo archivo = generalFacade.grabarArchivo(persona.getNatural().getFirma());
				
				if(archivo!=null){
					persona.getNatural().setIntTipoArchivoFirma(archivo.getId().getIntParaTipoCod());
					persona.getNatural().setIntItemArchivoFirma(archivo.getId().getIntItemArchivo());
					persona.getNatural().setIntItemHistoricoFirma(archivo.getId().getIntItemHistorico());
					
					boNatural.modificarNatural(persona.getNatural());
					
					FileUtil.renombrarArchivo(archivo.getTipoarchivo().getStrRuta()+"\\"+strOldName, archivo.getTipoarchivo().getStrRuta()+"\\"+archivo.getStrNombrearchivo());
				}
			}
			
			//Grabar Archivo Foto de Natural
			if(persona.getNatural().getFoto()!=null){
				String strOldName = persona.getNatural().getFoto().getStrNombrearchivo();
				
				GeneralFacadeRemote generalFacade = (GeneralFacadeRemote) EJBFactory.getRemote(GeneralFacadeRemote.class);
				Archivo archivo = generalFacade.grabarArchivo(persona.getNatural().getFoto());
				
				if(archivo!=null){
					persona.getNatural().setIntTipoArchivoFoto(archivo.getId().getIntParaTipoCod());
					persona.getNatural().setIntItemArchivoFoto(archivo.getId().getIntItemArchivo());
					persona.getNatural().setIntItemHistoricoFoto(archivo.getId().getIntItemHistorico());
					
					boNatural.modificarNatural(persona.getNatural());
					
					FileUtil.renombrarArchivo(archivo.getTipoarchivo().getStrRuta()+"\\"+strOldName, archivo.getTipoarchivo().getStrRuta()+"\\"+archivo.getStrNombrearchivo());
				}
			}
			
			//Grabar PerLaboral de Persona Natural
			if(persona.getNatural()!=null && persona.getNatural().getListaPerLaboral()!=null){
				perLaboralService.grabarListaDinamicaPerLaboral(persona.getNatural().getListaPerLaboral(), persona.getIntIdPersona());
				
				//Grabar Archivo contrato de PerLaboral
				for(PerLaboral perlaboral : persona.getNatural().getListaPerLaboral()){
					GeneralFacadeRemote generalFacade = (GeneralFacadeRemote) EJBFactory.getRemote(GeneralFacadeRemote.class);
					Archivo archivo = generalFacade.grabarArchivo(perlaboral.getContrato());
					
					if(archivo!=null){
						perlaboral.setIntTipoArchivoContrato(archivo.getId().getIntParaTipoCod());
						perlaboral.setIntItemArchivoContrato(archivo.getId().getIntItemArchivo());
						perlaboral.setIntItemHistoricoContrato(archivo.getId().getIntItemHistorico());
						
						boPerLaboral.modificarPerLaboral(perlaboral);
						
						FileUtil.renombrarArchivo(archivo.getTipoarchivo().getStrRuta(), archivo.getTipoarchivo().getStrRuta()+"\\"+archivo.getStrNombrearchivo());
					}
				}
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return persona;
	}
	
	public Persona modificarPersonaNatural(Persona pPersona)throws BusinessException {
		Persona persona = null;
		try{
				persona = boPersona.modificarPersona(pPersona);
				pPersona.getNatural().setIntIdPersona(persona.getIntIdPersona());
				boNatural.modificarNatural(pPersona.getNatural());
				detallePersonaService.grabarDinamicoDetalleDePersonaPorIdPersona(pPersona,persona.getIntIdPersona());
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return persona;
	}
	
	public Persona modificarPersonaNaturalYPerLaboral(Persona pPersona)throws BusinessException {
		Persona persona = null;
		try{
			//Grabar Persona Natural y su detalle
			persona = modificarPersonaNatural(pPersona);
			
			//Grabar Archivo Firma de Natural
			if(persona.getNatural().getFirma()!=null){
				String strOldName = persona.getNatural().getFirma().getStrNombrearchivo();
				
				GeneralFacadeRemote generalFacade = (GeneralFacadeRemote) EJBFactory.getRemote(GeneralFacadeRemote.class);
				Archivo archivo = generalFacade.grabarArchivo(persona.getNatural().getFirma());
				
				if(archivo!=null){
					persona.getNatural().setIntTipoArchivoFirma(archivo.getId().getIntParaTipoCod());
					persona.getNatural().setIntItemArchivoFirma(archivo.getId().getIntItemArchivo());
					persona.getNatural().setIntItemHistoricoFirma(archivo.getId().getIntItemHistorico());
					
					boNatural.modificarNatural(persona.getNatural());
					
					FileUtil.renombrarArchivo(archivo.getTipoarchivo().getStrRuta()+"\\"+strOldName, archivo.getTipoarchivo().getStrRuta()+"\\"+archivo.getStrNombrearchivo());
				}
			}
			
			//Grabar Archivo Foto de Natural
			if(persona.getNatural().getFoto()!=null){
				String strOldName = persona.getNatural().getFoto().getStrNombrearchivo();
				
				GeneralFacadeRemote generalFacade = (GeneralFacadeRemote) EJBFactory.getRemote(GeneralFacadeRemote.class);
				Archivo archivo = generalFacade.grabarArchivo(persona.getNatural().getFoto());
				
				if(archivo!=null){
					persona.getNatural().setIntTipoArchivoFoto(archivo.getId().getIntParaTipoCod());
					persona.getNatural().setIntItemArchivoFoto(archivo.getId().getIntItemArchivo());
					persona.getNatural().setIntItemHistoricoFoto(archivo.getId().getIntItemHistorico());
					
					boNatural.modificarNatural(persona.getNatural());
					
					FileUtil.renombrarArchivo(archivo.getTipoarchivo().getStrRuta()+"\\"+strOldName, archivo.getTipoarchivo().getStrRuta()+"\\"+archivo.getStrNombrearchivo());
				}
			}
			
			//Grabar PerLaboral de Persona Natural
			if(persona.getNatural()!=null && persona.getNatural().getListaPerLaboral()!=null){
				perLaboralService.grabarListaDinamicaPerLaboral(persona.getNatural().getListaPerLaboral(), persona.getIntIdPersona());
				
				//Grabar Archivo contrato de PerLaboral
				for(PerLaboral perlaboral : persona.getNatural().getListaPerLaboral()){
					if(perlaboral.getContrato()!=null){
						String strOldName = perlaboral.getContrato().getStrNombrearchivo();
						
						GeneralFacadeRemote generalFacade = (GeneralFacadeRemote) EJBFactory.getRemote(GeneralFacadeRemote.class);
						Archivo archivo = generalFacade.grabarArchivo(perlaboral.getContrato());
						
						if(archivo!=null){
							perlaboral.setIntTipoArchivoContrato(archivo.getId().getIntParaTipoCod());
							perlaboral.setIntItemArchivoContrato(archivo.getId().getIntItemArchivo());
							perlaboral.setIntItemHistoricoContrato(archivo.getId().getIntItemHistorico());
							
							boPerLaboral.modificarPerLaboral(perlaboral);
							System.out.println("strOldName: "+strOldName);
							System.out.println("archivo.getTipoarchivo().getStrRuta(): "+archivo.getTipoarchivo().getStrRuta());
							System.out.println("archivo.getStrNombrearchivo(): "+archivo.getStrNombrearchivo());
							FileUtil.renombrarArchivo(archivo.getTipoarchivo().getStrRuta()+"\\"+strOldName, archivo.getTipoarchivo().getStrRuta()+"\\"+archivo.getStrNombrearchivo());
						}
					}
				}
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return persona;
	}
	
	/*public void eliminarPersonaNaturalPorIdPersona(Integer pIntIdPersona)throws BusinessException {
		Persona persona = null;
		Natural natural = null;
		try{
				persona = boPersona.getPersonaPorPK(pIntIdPersona);
				persona.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
				boPersona.modificarPersona(persona);
				natural = boNatural.getNaturalPorPK(pIntIdPersona);
				natural.setIntIdPersona(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
				boNatural.modificarNatural(natural);
				detallePersonaService.eliminarDetalleDePersonaPorIdPersona(pIntIdPersona);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
	}*/
	
	public Persona getPersonaNaturalTotalPorIdPersonaYIdEmpresa(Integer intIdPersona ,Integer intIdEmpresaSistema) throws BusinessException {
		Persona persona = null;
		Natural natural = null;
		try{
			natural = boNatural.getNaturalPorPK(intIdPersona);
			if(natural==null){
				return null;
			}
			persona = boPersona.getPersonaPorPK(natural.getIntIdPersona());
			persona.setNatural(natural);
			
			persona.setPersonaEmpresa(new PersonaEmpresa(intIdEmpresaSistema, intIdEmpresaSistema));
			
			//Obteniendo la Firma
			if(persona.getNatural().getIntTipoArchivoFirma()!=null && persona.getNatural().getIntItemArchivoFirma()!=null
					&& persona.getNatural().getIntItemHistoricoFirma()!=null){
				//Obteniendo el contrato 
				ArchivoId archivoId = new ArchivoId();
				archivoId.setIntParaTipoCod(persona.getNatural().getIntTipoArchivoFirma());
				archivoId.setIntItemArchivo(persona.getNatural().getIntItemArchivoFirma());
				archivoId.setIntItemHistorico(persona.getNatural().getIntItemHistoricoFirma());
				GeneralFacadeRemote generalFacade = (GeneralFacadeRemote) EJBFactory.getRemote(GeneralFacadeRemote.class);
				Archivo archivo = generalFacade.getArchivoPorPK(archivoId);
				persona.getNatural().setFirma(archivo);
			}
			
			//Obteniendo la Foto
			if(persona.getNatural().getIntTipoArchivoFoto()!=null && persona.getNatural().getIntItemArchivoFoto()!=null
					&& persona.getNatural().getIntItemHistoricoFoto()!=null){
				//Obteniendo el contrato 
				ArchivoId archivoId = new ArchivoId();
				archivoId.setIntParaTipoCod(persona.getNatural().getIntTipoArchivoFoto());
				archivoId.setIntItemArchivo(persona.getNatural().getIntItemArchivoFoto());
				archivoId.setIntItemHistorico(persona.getNatural().getIntItemHistoricoFoto());
				GeneralFacadeRemote generalFacade = (GeneralFacadeRemote) EJBFactory.getRemote(GeneralFacadeRemote.class);
				Archivo archivo = generalFacade.getArchivoPorPK(archivoId);
				persona.getNatural().setFoto(archivo);
			}
			
			detallePersonaService.getDetalleDePersonaPorIdPersona(persona,persona.getIntIdPersona());
			//Separando el dni de los otros documentos
			if(persona.getListaDocumento()!=null){
				for(Documento documento : persona.getListaDocumento()){
					if(documento.getIntTipoIdentidadCod().equals(Integer.valueOf(Constante.PARAM_T_TIPODOCUMENTO_DNI))){
						persona.setDocumento(documento);
						break;
					}
				}
			}
			
			persona.getNatural().setListaPerLaboral(boPerLaboral.getListaPerLaboralPorIdPersona(natural.getIntIdPersona()));
			//Obteniendo los datos laborales actuales
			if(persona.getNatural().getListaPerLaboral()!=null){
				for(PerLaboral perlaboral : persona.getNatural().getListaPerLaboral()){
					//Obteniendo el contrato 
					ArchivoId archivoId = new ArchivoId();
					archivoId.setIntParaTipoCod(perlaboral.getIntTipoArchivoContrato());
					archivoId.setIntItemArchivo(perlaboral.getIntItemArchivoContrato());
					archivoId.setIntItemHistorico(perlaboral.getIntItemHistoricoContrato());
					GeneralFacadeRemote generalFacade = (GeneralFacadeRemote) EJBFactory.getRemote(GeneralFacadeRemote.class);
					Archivo archivo = generalFacade.getArchivoPorPK(archivoId);
					perlaboral.setContrato(archivo);
					//seteando datos laborales
					persona.getNatural().setPerLaboral(perlaboral);
				}
			}else{
				persona.getNatural().setPerLaboral(new PerLaboral());
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return persona;
	}
	
	public Persona getPersonaNaturalPorDocIdentidadYIdEmpresa(Integer intTipoIdentidad ,String strNroIdentidad,Integer intIdEmpresaSistema) throws BusinessException {
		Persona persona = null;
		Natural natural = null;
		try{
			natural = boNatural.getNaturalPorTipoIdentidadYNroIdentidad(intTipoIdentidad,strNroIdentidad);
			if(natural==null){
				return null;
			}
			persona = boPersona.getPersonaPorPK(natural.getIntIdPersona());
			persona.setNatural(natural);
			detallePersonaService.getDetalleDePersonaPorIdPersona(persona,persona.getIntIdPersona());
			persona.getNatural().setListaPerLaboral(boPerLaboral.getListaPerLaboralPorIdPersona(natural.getIntIdPersona()));
			
			/**
			 * Agregado 19/01/2013
			 * Agregando que se muestre la firma y foto de socio
			 * 
			 */
			if(persona.getNatural().getIntTipoArchivoFirma()!=null && persona.getNatural().getIntItemArchivoFirma()!=null
					&& persona.getNatural().getIntItemHistoricoFirma()!=null){
				//Obteniendo el contrato 
				ArchivoId archivoId = new ArchivoId();
				archivoId.setIntParaTipoCod(persona.getNatural().getIntTipoArchivoFirma());
				archivoId.setIntItemArchivo(persona.getNatural().getIntItemArchivoFirma());
				archivoId.setIntItemHistorico(persona.getNatural().getIntItemHistoricoFirma());
				GeneralFacadeRemote generalFacade = (GeneralFacadeRemote) EJBFactory.getRemote(GeneralFacadeRemote.class);
				Archivo archivo = generalFacade.getArchivoPorPK(archivoId);
				persona.getNatural().setFirma(archivo);
			}
			
			//Obteniendo la Foto
			if(persona.getNatural().getIntTipoArchivoFoto()!=null && persona.getNatural().getIntItemArchivoFoto()!=null
					&& persona.getNatural().getIntItemHistoricoFoto()!=null){
				//Obteniendo el contrato 
				ArchivoId archivoId = new ArchivoId();
				archivoId.setIntParaTipoCod(persona.getNatural().getIntTipoArchivoFoto());
				archivoId.setIntItemArchivo(persona.getNatural().getIntItemArchivoFoto());
				archivoId.setIntItemHistorico(persona.getNatural().getIntItemHistoricoFoto());
				GeneralFacadeRemote generalFacade = (GeneralFacadeRemote) EJBFactory.getRemote(GeneralFacadeRemote.class);
				Archivo archivo = generalFacade.getArchivoPorPK(archivoId);
				persona.getNatural().setFoto(archivo);
			}
			///////////////////////////////////////////
			
			//Obteniendo los datos laborales actuales
			if(persona.getNatural().getListaPerLaboral()!=null && persona.getNatural().getListaPerLaboral().size()>0){
				log.info("listaPerLaboral.size: "+persona.getNatural().getListaPerLaboral().size());
				for(PerLaboral perlaboral : persona.getNatural().getListaPerLaboral()){
					//Obteniendo el contrato 
					ArchivoId archivoId = new ArchivoId();
					archivoId.setIntParaTipoCod(perlaboral.getIntTipoArchivoContrato());
					archivoId.setIntItemArchivo(perlaboral.getIntItemArchivoContrato());
					archivoId.setIntItemHistorico(perlaboral.getIntItemHistoricoContrato());
					GeneralFacadeRemote generalFacade = (GeneralFacadeRemote) EJBFactory.getRemote(GeneralFacadeRemote.class);
					Archivo archivo = generalFacade.getArchivoPorPK(archivoId);
					perlaboral.setContrato(archivo);
					//seteando datos laborales
					persona.getNatural().setPerLaboral(perlaboral);
				}
			}else{
				log.info("listaPerLaboral: "+persona.getNatural().getListaPerLaboral());
				persona.getNatural().setPerLaboral(new PerLaboral());
			}
			
			//Obteniendo la lista de PersonaRol
			PersonaEmpresaPK personaEmpresaPk = new PersonaEmpresaPK();
			personaEmpresaPk.setIntIdEmpresa(intIdEmpresaSistema);
			personaEmpresaPk.setIntIdPersona(persona.getIntIdPersona());
			List<PersonaRol> listaPersonaRol = personaRolService.getListaPersonaRolPorPKPersonaEmpresa(personaEmpresaPk);
			PersonaEmpresa personaEmpresa = new PersonaEmpresa();
			personaEmpresa.setId(personaEmpresaPk);
			personaEmpresa.setListaPersonaRol(listaPersonaRol);
			persona.setPersonaEmpresa(personaEmpresa);
			if(boVinculo.getVinculoPorPersonaEmpresaPK(personaEmpresaPk)!=null)
			persona.getPersonaEmpresa().setListaVinculo(boVinculo.getVinculoPorPersonaEmpresaPK(personaEmpresaPk));
			
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return persona;
	}
	
	/**
	 * Recupera Persona, Natural,Domicilio,Comunicacion, Contacto, CtaBancaria  
	 * @param pIntIdPersona
	 * @return
	 * @throws BusinessException
	 */
	public Persona getPerNaturalYPerLaboralPorIdPersona(Integer pIntIdPersona)throws BusinessException {
		Persona persona = null;
		Natural natural = null;
		try{
				persona = boPersona.getPersonaPorPK(pIntIdPersona);
				if(persona != null){
					natural = boNatural.getNaturalPorPK(pIntIdPersona);
					if(natural != null){
						persona.setNatural(natural);
						persona.getNatural().setListaPerLaboral(boPerLaboral.getListaPerLaboralPorIdPersona(pIntIdPersona));
						
						//actualmente la relación entre Per_Natural y Per_Laboral es de 1 a 1
						if(persona.getNatural().getListaPerLaboral()!=null && persona.getNatural().getListaPerLaboral().size()>0){
							persona.getNatural().setPerLaboral(persona.getNatural().getListaPerLaboral().get(0));
						}
						
					}
					detallePersonaService.getDetalleDePersonaPorIdPersona(persona,pIntIdPersona);
				}
				
				
				
				
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return persona;
	}
	
	
	public Persona getPerNaturalYPerLaboralPorIdPersona2(Integer pIntIdPersona)throws BusinessException {
		Persona persona = null;
		try{
				persona = boPersona.getPersonaPorPK(pIntIdPersona);
				persona.setNatural(boNatural.getNaturalPorPK(pIntIdPersona));
				if(persona.getNatural()!=null){
					detallePersonaService.getDetalleDePersonaPorIdPersona(persona,pIntIdPersona);
					List<PerLaboral> lista = boPerLaboral.getListaPerLaboralPorIdPersona(pIntIdPersona);
					persona.getNatural().setListaPerLaboral(lista);
					//actualmente la relación entre Per_Natural y Per_Laboral es de 1 a 1
					if(persona.getNatural().getListaPerLaboral()!=null && persona.getNatural().getListaPerLaboral().size()>0){
						persona.getNatural().setPerLaboral(persona.getNatural().getListaPerLaboral().get(0));
					}
				}		
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return persona;
	}
	
	public Persona getPerNaturalPorIdPersona(Integer pIntIdPersona)throws BusinessException {
		Persona persona = null;
		try{
				persona = boPersona.getPersonaPorPK(pIntIdPersona);
				persona.setNatural(boNatural.getNaturalPorPK(pIntIdPersona));
				detallePersonaService.getDetalleDePersonaPorIdPersona(persona,pIntIdPersona);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return persona;
	}
	
	/*
	public Persona getPersonaNaturalPorPersonaEmpresaPKYTipoVinculo(PersonaEmpresaPK pk, Integer pIntTipoVinculo)throws BusinessException {
		Persona persona = null;
		try{
				persona = boPersona.getPersonaPorPK(pk.getIntIdPersona());
				persona.setNatural(boNatural.getNaturalPorPK(pk.getIntIdPersona()));
				detallePersonaService.getDetalleDePersonaPorIdPersona(persona,pk.getIntIdPersona());
				persona.getNatural().setListaPerLaboral(boPerLaboral.getListaPerLaboralPorIdPersona(pk.getIntIdPersona()));
				//Obtener las personas relacionadas
				persona.setListaPersona(personaVinculaService.getListaPersonaJuridicaPorPKPersonaEmpresaYTipoVinculo(pk, pIntTipoVinculo));
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return persona;
	}
	*/
	public List<Persona> getFullListPerNaturalBusqueda(Persona o)throws BusinessException {
		log.info("-------------------------------------Debugging PersonaNaturalService.getFullListPerNaturalBusqueda-------------------------------------");
		List<Natural> listaNatu = null;
		List<Persona> lista = null;
		Persona persona = null;
		try{
			listaNatu = boNatural.getListaNaturalBusqueda(o.getNatural());
			log.info("listaNatu.size: "+listaNatu.size());
			
			//Buscar por el Documento de Identidad
			log.info("o.getDocumento(): "+o.getDocumento());
			log.info("o.getDocumento().getStrNumeroIdentidad(): "+o.getDocumento().getStrNumeroIdentidad());
			
			persona = new Persona();
			for(Natural natural : listaNatu){
				persona = getPerNaturalYPerLaboralPorIdPersona(natural.getIntIdPersona());
				//Lista de PersonaEmpresa
				persona.setListaPersonaEmpresa(boPersonaEmpresa.getListaPersonaEmpresaPorIdPersona(persona.getIntIdPersona()));
				
				//Filtra por el documento. Si no se especifica el documento se toma el DNI de la lista de documentos.
				Boolean isValidDocumento = false;
				if(o.getDocumento().getStrNumeroIdentidad()==null || o.getDocumento().getStrNumeroIdentidad().equals("")){
					isValidDocumento = true;
					for(Documento documento : persona.getListaDocumento()){
						persona.setDocumento(documento);
						break;
					}
				}else{
					for(Documento documento : persona.getListaDocumento()){
						if(documento.getStrNumeroIdentidad().contains(o.getDocumento().getStrNumeroIdentidad())){
							isValidDocumento = true;
						}
						if(documento.getIntTipoIdentidadCod().equals(Integer.parseInt(Constante.PARAM_T_TIPODOCUMENTO_DNI))){
							persona.setDocumento(documento);
						}
					}
				}
				
				//Muestra los datos laborales actuales
				Boolean blnValidDate = true;
				if(persona.getNatural().getListaPerLaboral()!=null && persona.getNatural().getListaPerLaboral().size()>0){
					PerLaboral perLaboral = persona.getNatural().getListaPerLaboral().get(0);
					//Filtra por fechas
					if(o.getNatural().getPerLaboral()!=null){
						log.info("perLaboral: "+perLaboral);
						log.info("perLaboral.getDtInicioServicio(): "+perLaboral.getDtInicioServicio());
						log.info("o.getNatural().getPerLaboral().getDtInicioServicioDesde(): "+o.getNatural().getPerLaboral().getDtInicioServicioDesde());
						log.info("o.getNatural().getPerLaboral().getDtInicioServicioHasta(): "+o.getNatural().getPerLaboral().getDtInicioServicioHasta());
						if(perLaboral.getDtInicioServicio()!=null && o.getNatural().getPerLaboral().getDtInicioServicioDesde()!=null){
							if(!(perLaboral.getDtInicioServicio().compareTo(o.getNatural().getPerLaboral().getDtInicioServicioDesde())>=0)){
								blnValidDate = false;
							}
						}
						if(perLaboral.getDtInicioServicio()!=null && o.getNatural().getPerLaboral().getDtInicioServicioHasta()!=null){
							if(!(perLaboral.getDtInicioServicio().compareTo(o.getNatural().getPerLaboral().getDtInicioServicioHasta())<=0)){
								blnValidDate = false;
							}
						}
						if(perLaboral.getDtInicioContrato()!=null && o.getNatural().getPerLaboral().getDtInicioContratoDesde()!=null){
							if(!(perLaboral.getDtInicioContrato().compareTo(o.getNatural().getPerLaboral().getDtInicioContratoDesde())>=0)){
								blnValidDate = false;
							}
						}
						if(perLaboral.getDtInicioContrato()!=null && o.getNatural().getPerLaboral().getDtInicioContratoHasta()!=null){
							if(!(perLaboral.getDtInicioContrato().compareTo(o.getNatural().getPerLaboral().getDtInicioContratoHasta())<=0)){
								blnValidDate = false;
							}
						}
						if(perLaboral.getDtFinContrato()!=null && o.getNatural().getPerLaboral().getDtFinContratoDesde()!=null){
							if(!(perLaboral.getDtFinContrato().compareTo(o.getNatural().getPerLaboral().getDtFinContratoDesde())>=0)){
								blnValidDate = false;
							}
						}
						if(perLaboral.getDtFinContrato()!=null && o.getNatural().getPerLaboral().getDtFinContratoHasta()!=null){
							if(!(perLaboral.getDtFinContrato().compareTo(o.getNatural().getPerLaboral().getDtFinContratoHasta())<=0)){
								blnValidDate = false;
							}
						}
					}
					
					if(blnValidDate)persona.getNatural().setPerLaboral(persona.getNatural().getListaPerLaboral().get(0));
				}
				log.info("blnValidDate: "+blnValidDate);
				
				if(isValidDocumento && blnValidDate){
					if(lista==null)lista = new ArrayList<Persona>();
					lista.add(persona);
				}
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<Persona> getListPerNaturalBusqueda(Persona o)throws BusinessException {
		log.info("-------------------------------------Debugging PersonaNaturalService.getListPerNaturalBusqueda-------------------------------------");
		List<Natural> listaNatu = null;
		List<Persona> lista = null;
		Persona persona = null;
		try{
			listaNatu = boNatural.getListaNaturalBusqueda(o.getNatural());
			log.info("listaNatu.size: "+listaNatu.size());
			
			persona = new Persona();
			for(int i=0; i<listaNatu.size(); i++){
				persona = getPerNaturalPorIdPersona(listaNatu.get(i).getIntIdPersona());
				
				System.out.println("persona: "+persona);
				System.out.println("persona.listaDocumento: "+persona.getListaDocumento());
				System.out.println("persona.listaDocumento: "+persona.getListaDocumento().size());
				//Busqueda por Documento de identidad
				Boolean isValidDocumento = false; 
				if(o.getDocumento()!=null && o.getDocumento().getIntTipoIdentidadCod()!=null
						&& o.getDocumento().getStrNumeroIdentidad()!=null){
					for(Documento documento : persona.getListaDocumento()){
						if(documento.getIntTipoIdentidadCod().equals(o.getDocumento().getIntTipoIdentidadCod())
								&& documento.getStrNumeroIdentidad().equals(o.getDocumento().getStrNumeroIdentidad())){
							isValidDocumento = true;
						}
					}
				}else{
					isValidDocumento = true;
				}
				
				if(isValidDocumento){
					//Obtener el DNI de la lista de documentos
					for(Documento documento : persona.getListaDocumento()){
						System.out.println("documento.intTipoIdentidadCod: "+documento.getIntTipoIdentidadCod());
						System.out.println("documento.strNumeroIdentidad: "+documento.getStrNumeroIdentidad());
						if(documento.getIntTipoIdentidadCod().equals(Integer.valueOf(Constante.PARAM_T_TIPODOCUMENTO_DNI))){
							persona.setDocumento(documento);
						}
					}
					if(persona.getDocumento()==null)persona.setDocumento(new Documento());
					System.out.println("persona.documento.strNumeroIdentidad: "+persona.getDocumento().getStrNumeroIdentidad());
					
					if(lista==null)lista = new ArrayList<Persona>();
					lista.add(persona);
				}
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	
	public List<Persona> getListaBuscarPersonaNatural(Integer pIntIdEmpresa,String pStrNombres,Integer pIntTipoIdentidadCod, String pStrNumeroIdentidad,Integer pIntParaRolPk)throws BusinessException {
		log.info("-------------------------------------Debugging PersonaNaturalService.getListPerNaturalBusqueda-------------------------------------");
		List<Natural> listaNatu = null;
		List<Persona> lista = null;
		Persona persona = null;
		try{
			
			if (pIntTipoIdentidadCod == null)  pIntTipoIdentidadCod = Integer.valueOf(Constante.PARAM_T_TIPODOCUMENTO_DNI);
			
			listaNatu = boNatural.getListaBusqRolODniONomb(pIntIdEmpresa, pStrNombres, pIntTipoIdentidadCod, pStrNumeroIdentidad, pIntParaRolPk);
			log.info("listaNatu.size: "+listaNatu.size());
			
			persona = new Persona();
			for(int i=0; i<listaNatu.size(); i++){
				persona = getPerNaturalPorIdPersona(listaNatu.get(i).getIntIdPersona());
				System.out.println("persona: "+persona);
				Documento documento = boDocumento.getDocumentoPorIdPersonaYTipoIdentidad(persona.getIntIdPersona(), pIntTipoIdentidadCod);
				persona.setDocumento(documento);
				if(lista==null)lista = new ArrayList<Persona>();
				lista.add(persona);
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		
		return lista;
	}
	
	public Natural getNaturalDetalladaPorIdPersona(Integer intIdPersona) throws BusinessException {
		Natural natural = null;
		try{
			natural = boNatural.getNaturalPorPK(intIdPersona);
			
			if(natural==null) return null;
			
			List<PerLaboral> listaPersonaLaboral = boPerLaboral.getListaPerLaboralPorIdPersona(intIdPersona);
			natural.setListaPerLaboral(listaPersonaLaboral);
			//actualmente la relación entre Per_Natural y Per_Laboral es de 1 a 1
			if(listaPersonaLaboral!=null && !listaPersonaLaboral.isEmpty())
				natural.setPerLaboral(listaPersonaLaboral.get(0));
			
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return natural;
	}	
	
	/**
	 * Devuleve SOLO la Persona NAtural en base al id de persona
	 * @param pIntIdPersona
	 * @return
	 * @throws BusinessException
	 */
	public Natural getSoloPersonaNaturalPorIdPersona(Integer pIntIdPersona)throws BusinessException {
		Natural natural = null;
		try{
			natural = boNatural.getNaturalPorPK(pIntIdPersona);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return natural;
	}
}

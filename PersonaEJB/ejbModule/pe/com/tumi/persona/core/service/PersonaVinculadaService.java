package pe.com.tumi.persona.core.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.persona.contacto.bo.DocumentoBO;
import pe.com.tumi.persona.contacto.domain.Documento;
import pe.com.tumi.persona.core.bo.PersonaBO;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.persona.core.domain.PersonaEmpresa;
import pe.com.tumi.persona.core.domain.PersonaEmpresaPK;
import pe.com.tumi.persona.empresa.service.EmpresaService;
import pe.com.tumi.persona.vinculo.bo.VinculoBO;
import pe.com.tumi.persona.vinculo.domain.Vinculo;
import pe.com.tumi.persona.vinculo.service.VinculoService;

public class PersonaVinculadaService {
	
	protected  static Logger log = Logger.getLogger(PersonaVinculadaService.class);
	private PersonaBO boPersona = (PersonaBO)TumiFactory.get(PersonaBO.class);
	private DocumentoBO boDocumento = (DocumentoBO)TumiFactory.get(DocumentoBO.class);
	private PersonaNaturalService servicePersonaNatural = (PersonaNaturalService)TumiFactory.get(PersonaNaturalService.class);
	private PersonaJuridicaService servicePersonaJuridica = (PersonaJuridicaService)TumiFactory.get(PersonaJuridicaService.class);
	private VinculoService vinculoService = (VinculoService)TumiFactory.get(VinculoService.class);
	private VinculoBO boVinculo = (VinculoBO)TumiFactory.get(VinculoBO.class);
	
	public void grabarListaDinamicaPersonaVinculada(List<Persona> lista,Integer intIdPersona)throws BusinessException {
		Persona dto = null;
		Persona dtoTemp = null;
		try{
			for(int i=0; i<lista.size(); i++){
				dto = lista.get(i);
				if(dto.getIntIdPersona() == null){
					//dto.setIntIdPersona(intIdPersona);
					if(dto.getIntTipoPersonaCod().compareTo(Constante.PARAM_T_TIPOPERSONA_NATURAL)==0){
						dto = servicePersonaNatural.grabarPersonaNatural(dto);
					}else if(dto.getIntTipoPersonaCod().compareTo(Constante.PARAM_T_TIPOPERSONA_JURIDICA)==0){
						dto = servicePersonaJuridica.grabarPersonaJuridica(dto);
					}else{
						throw new BusinessException("La Persona Vinculada Debe tener un tipo de Persona");
					}
				}else{	
					dtoTemp = boPersona.getPersonaPorPK(dto.getIntIdPersona());
					if(dtoTemp == null){
						//dto.setIntIdPersona(intIdPersona);
						if(dto.getIntTipoPersonaCod().compareTo(Constante.PARAM_T_TIPOPERSONA_NATURAL)==0){
							dto = servicePersonaNatural.grabarPersonaNatural(dto);
						}else if(dto.getIntTipoPersonaCod().compareTo(Constante.PARAM_T_TIPOPERSONA_JURIDICA)==0){
							dto = servicePersonaJuridica.grabarPersonaJuridica(dto);
						}else{
							throw new BusinessException("La Persona Vinculada Debe tener un tipo de Persona");
						}
					}else{
						if(dto.getIntTipoPersonaCod().compareTo(Constante.PARAM_T_TIPOPERSONA_NATURAL)==0){
							dto = servicePersonaNatural.modificarPersonaNatural(dto);
						}else if(dto.getIntTipoPersonaCod().compareTo(Constante.PARAM_T_TIPOPERSONA_JURIDICA)==0){
							dto = servicePersonaJuridica.modificarPersonaJuridica(dto);
						}else{
							throw new BusinessException("La Persona Vinculada Debe tener un tipo de Persona");
						}
					}
				}
				if(dto.getPersonaEmpresa()!=null){
					//empresaService.grabarDinamicoPersonaEmpresa(dto.getPersonaEmpresa(),dto.getIntIdPersona());
					if(dto.getPersonaEmpresa().getVinculo()!=null && dto.getPersonaEmpresa().getVinculo().getIntTipoVinculoCod()!=null){
						log.info("--grabarDinamicoVinculo");
						log.info("vinculo:"+dto.getPersonaEmpresa().getVinculo());
						vinculoService.grabarDinamicoVinculo(dto.getPersonaEmpresa().getVinculo(),
															 intIdPersona,
															 dto.getIntIdPersona());
					}
					/**parche para grabar lista de Vinculos**/
					if(dto.getPersonaEmpresa().getListaVinculo()!=null){
						log.info("--getPersonaEmpresa().getListaVinculo");
						for(Vinculo vinculo : dto.getPersonaEmpresa().getListaVinculo()){
							log.info("vinculo:"+vinculo);
							vinculoService.grabarDinamicoVinculo(vinculo, intIdPersona, dto.getIntIdPersona());
						}
					}
					/**Fin parche**/
					
				}
			}	
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
	}
	
	public List<Persona> getListaPersonaNaturalPorPersonaEmpresaPK(PersonaEmpresaPK pk) throws BusinessException {
		List<Persona> lista = null;
		List<Vinculo> listaVinculo = null;
		Vinculo vinculo = null;
		Persona persona = null;
		
		try{
			listaVinculo = boVinculo.getVinculoPorPersonaEmpresaPK(pk);
			if(listaVinculo!=null && listaVinculo.size()>0){
				lista = new ArrayList<Persona>(); 
				for(int i=0; i<listaVinculo.size(); i++){
					vinculo = listaVinculo.get(i);
					persona = servicePersonaNatural.getPersonaNaturalTotalPorIdPersonaYIdEmpresa(vinculo.getIntPersonaVinculo(),vinculo.getIntEmpresaVinculo());
					if(persona!=null)System.out.println("persona.getIntIdPersona(): "+persona.getIntIdPersona());
					if(persona!=null)log.info("persona.getPersonaEmpresa(): "+persona.getPersonaEmpresa());
					if(persona!=null && persona.getPersonaEmpresa()!=null){
						persona.getPersonaEmpresa().setVinculo(vinculo);
						lista.add(persona);
					}	
				}
			}	
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return (lista!=null && lista.size()>0)? lista: null;
	}
	
	public List<Persona> getListaPersonaNaturalPorPKPersonaEmpresaYTipoVinculo(PersonaEmpresaPK pk,Integer intTipoVinculo) throws BusinessException {
		List<Persona> lista = null;
		List<Vinculo> listaVinculo = null;
		Vinculo vinculo = null;
		Persona persona = null;
		
		try{
			listaVinculo = boVinculo.getListaVinculoPorPKPersonaEmpresaYTipoVinculo(pk,intTipoVinculo);
			if(listaVinculo!=null && listaVinculo.size()>0){
				lista = new ArrayList<Persona>(); 
				for(int i=0; i<listaVinculo.size(); i++){
					vinculo = listaVinculo.get(i);
					persona = servicePersonaNatural.getPersonaNaturalTotalPorIdPersonaYIdEmpresa(vinculo.getIntPersonaVinculo(),vinculo.getIntEmpresaVinculo());
					if(persona!=null && persona.getPersonaEmpresa()!=null){
						persona.getPersonaEmpresa().setVinculo(vinculo);
						lista.add(persona);
					}	
				}
			}	
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return (lista!=null && lista.size()>0)? lista: null;
	}
	
	public List<Persona> getListaPersonaJuridicaPorPKPersonaEmpresaYTipoVinculo(PersonaEmpresaPK pk,Integer intTipoVinculo) throws BusinessException {
		List<Persona> lista = null;
		List<Vinculo> listaVinculo = null;
		Vinculo vinculo = null;
		
		try{
			listaVinculo = boVinculo.getListaVinculoPorPKPersonaEmpresaYTipoVinculo(pk,intTipoVinculo);
			if(listaVinculo!=null && listaVinculo.size()>0){
				lista = new ArrayList<Persona>(); 
				for(int i=0; i<listaVinculo.size(); i++){
					vinculo = listaVinculo.get(i);
					lista.add(servicePersonaJuridica.getPersonaJuridicaPorIdPersonaYIdEmpresa(vinculo.getIntPersonaVinculo(),vinculo.getIntEmpresaVinculo()));	
				}
			}	
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public Vinculo getVinculoPorPk(Integer intItemVinculo) throws BusinessException{
		Vinculo dto = null;
		List<Documento> listaDocumento;
		Vinculo vinculo = null;
		PersonaEmpresa personaEmpresa = null;
		try{
			dto = boVinculo.getVinculoPorPK(intItemVinculo);
			dto.setPersona(servicePersonaNatural.getPerNaturalYPerLaboralPorIdPersona(dto.getIntPersonaVinculo()));
			listaDocumento = boDocumento.getListaDocumentoPorIdPersona(dto.getIntPersonaVinculo());
			if(listaDocumento!=null && listaDocumento.size()>0){
				for(Documento documento : listaDocumento){
					if(documento.getIntTipoIdentidadCod().toString().equals(Constante.PARAM_T_TIPODOCUMENTO_DNI)){
						dto.getPersona().setDocumento(boDocumento.getDocumentoPorPK(documento.getId()));
					}
				}
			}
			vinculo = boVinculo.getVinculoPorPK(intItemVinculo);
			personaEmpresa = new PersonaEmpresa();
			personaEmpresa.setId(new PersonaEmpresaPK());
			personaEmpresa.getId().setIntIdEmpresa(vinculo.getIntIdEmpresa());
			personaEmpresa.getId().setIntIdPersona(vinculo.getIntPersonaVinculo());
			//personaEmpresa.getId().setIntIdPersona(vinculo.getIntIdPersona());
			dto.getPersona().setPersonaEmpresa(personaEmpresa);
			dto.getPersona().getPersonaEmpresa().setVinculo(vinculo);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return dto;
	}
}

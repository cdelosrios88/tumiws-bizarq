package pe.com.tumi.persona.core.service;

import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.persona.core.bo.PersonaEmpresaBO;
import pe.com.tumi.persona.core.domain.PersonaEmpresa;
import pe.com.tumi.persona.core.domain.PersonaEmpresaPK;
import pe.com.tumi.persona.empresa.service.EmpresaService;

public class PersonaEmpresaService {
	
	protected  static Logger log = Logger.getLogger(PersonaEmpresaService.class);
	private PersonaEmpresaBO boPersonaEmpresa = (PersonaEmpresaBO)TumiFactory.get(PersonaEmpresaBO.class);
	private EmpresaService empresaService = (EmpresaService)TumiFactory.get(EmpresaService.class);
	
	public PersonaEmpresa grabarDinamicaPersonaEmpresa(PersonaEmpresa dto, Integer intIdPersona) throws BusinessException{
		PersonaEmpresa dtoTemp = null;
		try{
				if(dto.getId() == null || dto.getId().getIntIdPersona() == null){
					if(dto.getId() == null)dto.setId(new PersonaEmpresaPK());
					dto.getId().setIntIdPersona(intIdPersona);
					dto.getId().setIntIdEmpresa(Constante.PARAM_EMPRESASESION);
					dto = boPersonaEmpresa.grabarPersonaEmpresa(dto);
				}else{
					dtoTemp = boPersonaEmpresa.getPersonaEmpresaPorPK(dto.getId());
					if(dtoTemp == null){
						dto.getId().setIntIdPersona(intIdPersona);
						dto = boPersonaEmpresa.grabarPersonaEmpresa(dto);
					}else{
						dto = boPersonaEmpresa.modificarPersonaEmpresa(dto);
					}
				}
				if(dto.getListaPersonaRol()!=null){
					empresaService.grabarListaDinamicaPersonaRol(dto.getListaPersonaRol(), intIdPersona);
				}else if(dto.getPersonaRol()!=null){
					empresaService.grabarDinamicaPersonaRol(dto.getPersonaRol(), intIdPersona);
				}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public List<PersonaEmpresa> grabarListaDinamicaPersonaEmpresa(List<PersonaEmpresa> lista, Integer intIdPersona) throws BusinessException{
		PersonaEmpresa dto = null;
		PersonaEmpresa dtoTemp = null;
		try{
			for(int i=0; i<lista.size(); i++){
				dto = lista.get(i);
				if(dto.getId() == null || dto.getId().getIntIdPersona() == null){
					if(dto.getId() == null)dto.setId(new PersonaEmpresaPK());
					dto.getId().setIntIdPersona(intIdPersona);
					dto = boPersonaEmpresa.grabarPersonaEmpresa(dto);
				}else{
					dtoTemp = boPersonaEmpresa.getPersonaEmpresaPorPK(dto.getId());
					if(dtoTemp == null){
						dto.getId().setIntIdPersona(intIdPersona);
						dto = boPersonaEmpresa.grabarPersonaEmpresa(dto);
					}else{
						dto = boPersonaEmpresa.modificarPersonaEmpresa(dto);
					}
				}
				if(dto.getListaPersonaRol()!=null){
					empresaService.grabarListaDinamicaPersonaRol(dto.getListaPersonaRol(), intIdPersona);
				}else if(dto.getPersonaRol()!=null){
					empresaService.grabarDinamicaPersonaRol(dto.getPersonaRol(), intIdPersona);
				}
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
}

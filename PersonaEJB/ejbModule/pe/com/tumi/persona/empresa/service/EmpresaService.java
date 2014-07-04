package pe.com.tumi.persona.empresa.service;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.persona.core.bo.PersonaBO;
import pe.com.tumi.persona.core.bo.PersonaRolBO;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.persona.core.domain.PersonaRol;
import pe.com.tumi.persona.core.domain.PersonaRolPK;
import pe.com.tumi.persona.empresa.bo.EmpresaBO;
import pe.com.tumi.persona.empresa.bo.JuridicaBO;
import pe.com.tumi.persona.empresa.domain.Empresa;
import pe.com.tumi.persona.empresa.domain.Juridica;

public class EmpresaService {
	
	private EmpresaBO boEmpresa = (EmpresaBO)TumiFactory.get(EmpresaBO.class);
	private PersonaBO boPersona = (PersonaBO)TumiFactory.get(PersonaBO.class);
	private JuridicaBO boJuridica = (JuridicaBO)TumiFactory.get(JuridicaBO.class);
	private PersonaRolBO boPersonaRol = (PersonaRolBO)TumiFactory.get(PersonaRolBO.class);
	
	public Empresa grabarEmpresa(Empresa o) throws BusinessException {
		Empresa emp = null;
		Juridica jur = null;
		Persona per = null;
		try{
			if(o.getIntIdEmpresa()==null){
				per = o.getJuridica().getPersona();
				per = boPersona.grabarPersona(per);
				jur = o.getJuridica();
				jur.setIntIdPersona(per.getIntIdPersona());
				jur = boJuridica.grabarJuridica(jur);
				o.setIntIdEmpresa(per.getIntIdPersona());
				emp = boEmpresa.grabarEmpresa(o);
			}else{
				per = o.getJuridica().getPersona();
				per.setIntIdPersona(o.getIntIdEmpresa());
				per = boPersona.modificarPersona(per);
				jur = o.getJuridica();
				jur.setIntIdPersona(o.getIntIdEmpresa());
				jur = boJuridica.modificarJuridica(jur);
				emp = boEmpresa.modificarEmpresa(o);
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return emp;
	}
	
	public List<PersonaRol> grabarListaDinamicaPersonaRol(List<PersonaRol> lista, Integer intIdPersona) throws BusinessException{
		PersonaRol dto = null;
		PersonaRol dtoTemp = null;
		try{
			for(int i=0; i<lista.size(); i++){
				dto = lista.get(i);
				if(dto.getId() == null || dto.getId().getIntIdPersona() == null || 
					dto.getId().getIntParaRolPk()!= null || dto.getId().getDtFechaInicio() == null){
					if(dto.getId()==null)dto.setId(new PersonaRolPK());
					dto.getId().setIntIdPersona(intIdPersona);
					dto = boPersonaRol.grabarPersonaRol(dto);
				}else{
					dtoTemp = boPersonaRol.getPersonaRolPorPK(dto.getId());
					if(dtoTemp == null){
						dto.getId().setIntIdPersona(intIdPersona);
						dto = boPersonaRol.grabarPersonaRol(dto);
					}else{
						dto = boPersonaRol.modificarPersonaRol(dto);
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
	
	public PersonaRol grabarDinamicaPersonaRol(PersonaRol dto, Integer intIdPersona) throws BusinessException{
		PersonaRol dtoTemp = null;
		try{
			if(dto.getId() == null || dto.getId().getIntIdPersona() == null || 
				dto.getId().getIntParaRolPk()!= null || dto.getId().getDtFechaInicio() == null){
				if(dto.getId()==null)dto.setId(new PersonaRolPK());
				dto.getId().setIntIdPersona(intIdPersona);
				dto = boPersonaRol.grabarPersonaRol(dto);
			}else{
				dtoTemp = boPersonaRol.getPersonaRolPorPK(dto.getId());
				if(dtoTemp == null){
					dto.getId().setIntIdPersona(intIdPersona);
					dto = boPersonaRol.grabarPersonaRol(dto);
				}else{
					dto = boPersonaRol.modificarPersonaRol(dto);
				}
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return dto;
	}
}

package pe.com.tumi.persona.empresa.service;

import java.sql.Timestamp;
import java.util.List;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.persona.contacto.domain.Comunicacion;
import pe.com.tumi.persona.contacto.domain.ComunicacionPK;
import pe.com.tumi.persona.core.bo.PersonaBO;
import pe.com.tumi.persona.core.bo.PersonaRolBO;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.persona.core.domain.PersonaRol;
import pe.com.tumi.persona.core.domain.PersonaRolPK;
import pe.com.tumi.persona.empresa.bo.EmpresaBO;
import pe.com.tumi.persona.empresa.bo.JuridicaBO;
import pe.com.tumi.persona.empresa.bo.PerLaboralBO;
import pe.com.tumi.persona.empresa.domain.Empresa;
import pe.com.tumi.persona.empresa.domain.Juridica;
import pe.com.tumi.persona.empresa.domain.PerLaboral;
import pe.com.tumi.persona.empresa.domain.PerLaboralPK;

public class PerLaboralService {
	
	private PerLaboralBO boPerLaboral = (PerLaboralBO)TumiFactory.get(PerLaboralBO.class);
	
	public List<PerLaboral> grabarListaDinamicaPerLaboral(List<PerLaboral> lista, Integer intIdPersona) throws BusinessException{
		PerLaboral dto = null;
		PerLaboral dtoTemp = null;
		try{
			for(int i=0; i<lista.size(); i++){
				dto = lista.get(i);
				if(dto.getId() == null || dto.getId().getIntIdPersona() == null){
					if(dto.getId() == null)dto.setId(new PerLaboralPK());
					dto.getId().setIntIdPersona(intIdPersona);
					dto = boPerLaboral.grabarPerLaboral(dto);
				}else{
					dtoTemp = boPerLaboral.getPerLaboralPorPK(dto.getId());
					if(dtoTemp == null){
						dto.getId().setIntIdPersona(intIdPersona);
						dto = boPerLaboral.grabarPerLaboral(dto);
					}else{
						dto = boPerLaboral.modificarPerLaboral(dto);
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
}

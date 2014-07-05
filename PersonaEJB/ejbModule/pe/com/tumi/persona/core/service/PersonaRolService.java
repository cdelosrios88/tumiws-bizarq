package pe.com.tumi.persona.core.service;

import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.parametro.tabla.domain.Tabla;
import pe.com.tumi.parametro.tabla.facade.TablaFacadeRemote;
import pe.com.tumi.persona.core.bo.PersonaRolBO;
import pe.com.tumi.persona.core.domain.PersonaEmpresaPK;
import pe.com.tumi.persona.core.domain.PersonaRol;

public class PersonaRolService {
	
	protected  static Logger log = Logger.getLogger(PersonaRolService.class);
	private PersonaRolBO boPersonaRol = (PersonaRolBO)TumiFactory.get(PersonaRolBO.class);
	
	public List<PersonaRol> getListaPersonaRolPorPKPersonaEmpresa(PersonaEmpresaPK pk) throws BusinessException {
		List<PersonaRol> lista = null;
		List<Tabla> listTabla = null;
		PersonaRol rol = null;
		Tabla tabla = null;
		
		try{
			lista = boPersonaRol.getListaPersonaRolPorPKPersonaEmpresa(pk);
			log.info("listaPersonaRol.size: "+lista.size());
			TablaFacadeRemote tablaFacade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
    		listTabla = tablaFacade.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_TIPOROL));
    		log.info("listTabla.size: "+listTabla.size());
			
			for(int i=0; i<lista.size(); i++){
				rol = lista.get(i);
				for(int j=0; j<listTabla.size(); j++){
					tabla = listTabla.get(j);
					if(tabla.getIntIdDetalle().equals(rol.getId().getIntParaRolPk())){
						rol.setTabla(tabla);
						log.info("rol.tabla.strDescripcion: "+rol.getTabla().getStrDescripcion());
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
	
	public List<PersonaRol> grabarListaDinamicaPersonaRol(List<PersonaRol> lista, Integer intIdPersona) throws BusinessException{
		try{
			for(PersonaRol personaRol : lista){
				if(personaRol.getId().getIntIdPersona()==null || personaRol.getId().getDtFechaInicio()==null){
					personaRol.getId().setIntIdPersona(intIdPersona);
					boPersonaRol.grabarPersonaRol(personaRol);
				}else{
					boPersonaRol.modificarPersonaRolPorPerEmpYRol(personaRol);
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

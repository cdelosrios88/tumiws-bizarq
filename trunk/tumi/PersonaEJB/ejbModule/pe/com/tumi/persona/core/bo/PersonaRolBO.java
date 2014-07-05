package pe.com.tumi.persona.core.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.framework.util.fecha.JFecha;
import pe.com.tumi.persona.core.dao.PersonaRolDao;
import pe.com.tumi.persona.core.dao.impl.PersonaRolDaoIbatis;
import pe.com.tumi.persona.core.domain.PersonaEmpresaPK;
import pe.com.tumi.persona.core.domain.PersonaRol;
import pe.com.tumi.persona.core.domain.PersonaRolPK;

public class PersonaRolBO {
	
	private PersonaRolDao dao = (PersonaRolDao)TumiFactory.get(PersonaRolDaoIbatis.class);
	
	public PersonaRol grabarPersonaRol(PersonaRol o) throws BusinessException{
		PersonaRol dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public PersonaRol modificarPersonaRol(PersonaRol o) throws BusinessException{
		PersonaRol dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public PersonaRol getPersonaRolPorPK(PersonaRolPK pPK) throws BusinessException{
		PersonaRol domain = null;
		List<PersonaRol> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intIdEmpresa", pPK.getIntIdEmpresa());
			mapa.put("intIdPersona", pPK.getIntIdPersona());
			mapa.put("strParaRolPk", pPK.getIntParaRolPk());
			mapa.put("dtFechaInicio", JFecha.formatearFechaHora(pPK.getDtFechaInicio()));
			lista = dao.getListaPersonaRolPorPK(mapa);
			if(lista!=null){
				if(lista.size()==1){
				   domain = lista.get(0);
				}else if(lista.size()==0){
				   domain = null;
				}else{
				   throw new BusinessException("Obtención de mas de un registro coincidente");
				}
			}	
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return domain;
	}
	
	public List<PersonaRol> getListaPersonaRolPorPKPersonaEmpresa(PersonaEmpresaPK pPK) throws BusinessException{
		List<PersonaRol> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intIdEmpresa", pPK.getIntIdEmpresa());
			mapa.put("intIdPersona", pPK.getIntIdPersona());
			lista = dao.getListaPersonaRolPorPKPersonaEmpresa(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public PersonaRol modificarPersonaRolPorPerEmpYRol(PersonaRol o) throws BusinessException{
		PersonaRol dto = null;
		try{
			dto = dao.modificarPersonaRolPorPerEmpYRol(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
}

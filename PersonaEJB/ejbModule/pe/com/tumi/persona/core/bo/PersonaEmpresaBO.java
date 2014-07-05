package pe.com.tumi.persona.core.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.persona.core.dao.PersonaEmpresaDao;
import pe.com.tumi.persona.core.dao.impl.PersonaEmpresaDaoIbatis;
import pe.com.tumi.persona.core.domain.PersonaEmpresa;
import pe.com.tumi.persona.core.domain.PersonaEmpresaPK;

public class PersonaEmpresaBO {
	
	private PersonaEmpresaDao dao = (PersonaEmpresaDao)TumiFactory.get(PersonaEmpresaDaoIbatis.class);
	
	public PersonaEmpresa grabarPersonaEmpresa(PersonaEmpresa o) throws BusinessException{
		PersonaEmpresa dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public PersonaEmpresa modificarPersonaEmpresa(PersonaEmpresa o) throws BusinessException{
		PersonaEmpresa dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public PersonaEmpresa getPersonaEmpresaPorPK(PersonaEmpresaPK pPK) throws BusinessException{
		PersonaEmpresa domain = null;
		List<PersonaEmpresa> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intIdEmpresa", pPK.getIntIdEmpresa());
			mapa.put("intIdPersona", pPK.getIntIdPersona());
			lista = dao.getListaPersonaEmpresaPorPK(mapa);
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
	
	public List<PersonaEmpresa> getListaPersonaEmpresaPorIdPersona(Integer pId) throws BusinessException{
		List<PersonaEmpresa> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intIdPersona",pId);
			lista = dao.getListaPersonaEmpresaPorIdPersona(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<PersonaEmpresa> getListaPersonaEmpresaPorIdEmpresa(Integer pId) throws BusinessException{
		List<PersonaEmpresa> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intIdEmpresa", pId);
			lista = dao.getListaPersonaEmpresaPorIdEmpresa(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
}

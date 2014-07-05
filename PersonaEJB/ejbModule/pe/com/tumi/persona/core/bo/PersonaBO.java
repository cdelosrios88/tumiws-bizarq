package pe.com.tumi.persona.core.bo;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.persona.core.dao.PersonaDao;
import pe.com.tumi.persona.core.dao.impl.PersonaDaoIbatis;
import pe.com.tumi.persona.core.domain.Persona;

public class PersonaBO {
	
	protected  static Logger log = Logger.getLogger(PersonaBO.class);
	private PersonaDao dao = (PersonaDao)TumiFactory.get(PersonaDaoIbatis.class);
	
	public Persona grabarPersona(Persona o) throws BusinessException{
		log.info("-----------------------Debugging PersonaBO.grabarEmpresa-----------------------------");
		Persona dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public Persona modificarPersona(Persona o) throws BusinessException{
		Persona dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public Persona getPersonaPorPK(Integer pIntPK) throws BusinessException{
		Persona domain = null;
		List<Persona> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intIdPersona", pIntPK);
			lista = dao.getListaPersonaPorPK(mapa);
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
	
	public List<Persona> getPersonaBusqueda(Persona persona) throws BusinessException{
		Persona domain = null;
		List<Persona> lista = null;
		
		try{
			HashMap mapa = new HashMap();
			mapa.put("pIntIdPersona", persona.getIntIdPersona());
			mapa.put("pIntTipoPersonaCod", persona.getIntTipoPersonaCod());
			mapa.put("pStrRuc", persona.getStrRuc());
			mapa.put("pDtFechaBajaRuc", persona.getDtFechaBajaRuc());
			mapa.put("pIntEstadoCod", persona.getIntEstadoCod());
			lista = dao.getListaPersonaBusqueda(mapa);	
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		
		return lista;
	}
	
	public Persona getPersonaPorRuc(String strRuc) throws BusinessException{
		Persona domain = null;
		List<Persona> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("pStrRuc", strRuc);
			lista = dao.getListaPersonaPorRuc(mapa);
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
	
	public Persona getPersonaActivaPorIdPersona(Integer intIdPersona) throws BusinessException{
		Persona domain = null;
		List<Persona> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intIdPersona", intIdPersona);
			lista = dao.getListaPersonaActivaPorIdPersona(mapa);
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
	
}

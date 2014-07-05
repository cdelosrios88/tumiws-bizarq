package pe.com.tumi.persona.contacto.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.persona.contacto.dao.DomicilioDao;
import pe.com.tumi.persona.contacto.dao.impl.DomicilioDaoIbatis;
import pe.com.tumi.persona.contacto.domain.Domicilio;
import pe.com.tumi.persona.contacto.domain.DomicilioPK;

public class DomicilioBO {
	
	private DomicilioDao dao = (DomicilioDao)TumiFactory.get(DomicilioDaoIbatis.class);
	
	public Domicilio grabarDomicilio(Domicilio o) throws BusinessException{
		Domicilio dto = null;
		try{
			if(o.getIntParaUbigeoPkDistrito()!= null && o.getIntParaUbigeoPkDistrito().compareTo(new Integer(0))!=0){
				o.setIntParaUbigeoPk(o.getIntParaUbigeoPkDistrito());
			}else if(o.getIntParaUbigeoPkProvincia()!=null && o.getIntParaUbigeoPkProvincia().compareTo(new Integer(0))!=0){
				o.setIntParaUbigeoPk(o.getIntParaUbigeoPkProvincia());
			}else if(o.getIntParaUbigeoPkDpto()!=null && o.getIntParaUbigeoPkDpto().compareTo(new Integer(0))!=0){
				o.setIntParaUbigeoPk(o.getIntParaUbigeoPkDpto());
			}else{
				o.setIntParaUbigeoPk(null);
			}
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public Domicilio modificarDomicilio(Domicilio o) throws BusinessException{
		Domicilio dto = null;
		try{
			if(o.getIntParaUbigeoPkDistrito()!= null && o.getIntParaUbigeoPkDistrito().compareTo(new Integer(0))!=0){
				o.setIntParaUbigeoPk(o.getIntParaUbigeoPkDistrito());
			}else if(o.getIntParaUbigeoPkProvincia()!=null && o.getIntParaUbigeoPkProvincia().compareTo(new Integer(0))!=0){
				o.setIntParaUbigeoPk(o.getIntParaUbigeoPkProvincia());
			}else if(o.getIntParaUbigeoPkDpto()!=null && o.getIntParaUbigeoPkDpto().compareTo(new Integer(0))!=0){
				o.setIntParaUbigeoPk(o.getIntParaUbigeoPkDpto());
			}else{
				o.setIntParaUbigeoPk(null);
			}
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public Domicilio getDomicilioPorPK(DomicilioPK pPK) throws BusinessException{
		Domicilio domain = null;
		List<Domicilio> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intIdPersona", pPK.getIntIdPersona());
			mapa.put("intIdDomicilio", pPK.getIntIdDomicilio());
			lista = dao.getListaDomicilioPorPK(mapa);
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
	
	public List<Domicilio> getListaDomicilioPorIdPersona(Integer pId) throws BusinessException{
		List<Domicilio> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intIdPersona", pId);
			lista = dao.getListaDomicilioPorIdPersona(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public Domicilio eliminarDomicilio(Domicilio o) throws BusinessException{
		Domicilio dto = null;
		try{
			dto = dao.eliminarDomicilio(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
}

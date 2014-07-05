package pe.com.tumi.persona.contacto.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.persona.contacto.dao.DocumentoDao;
import pe.com.tumi.persona.contacto.dao.impl.DocumentoDaoIbatis;
import pe.com.tumi.persona.contacto.domain.Documento;
import pe.com.tumi.persona.contacto.domain.DocumentoPK;

public class DocumentoBO {
	
	private DocumentoDao dao = (DocumentoDao)TumiFactory.get(DocumentoDaoIbatis.class);
	
	public Documento grabarDocumento(Documento o) throws BusinessException{
		Documento dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public Documento modificarDocumento(Documento o) throws BusinessException{
		Documento dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public Documento getDocumentoPorPK(DocumentoPK pPK) throws BusinessException{
		Documento domain = null;
		List<Documento> lista = null;
		try{
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("intIdPersona", pPK.getIntIdPersona());
			mapa.put("intItemDocumento", pPK.getIntItemDocumento());
			lista = dao.getListaPorPk(mapa);
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

	public Documento getDocumentoPorIdPersonaYTipoIdentidad(Integer intIdPersona,Integer intTipoIdentidad) throws BusinessException{
		Documento domain = null;
		List<Documento> lista = null;
		try{
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("intIdPersona", intIdPersona);
			mapa.put("intTipoIdentidad", intTipoIdentidad);
			lista = dao.getListaPorIdPersonaYTipoIdentidad(mapa);
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
	
	public List<Documento> getListaDocumentoPorIdPersona(Integer pId) throws BusinessException{
		List<Documento> lista = null;
		try{
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("intIdPersona", pId);
			lista = dao.getListaPorIdPersona(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
}

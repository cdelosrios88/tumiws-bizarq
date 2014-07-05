package pe.com.tumi.persona.contacto.facade;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.facade.TumiFacade;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.persona.contacto.bo.ComunicacionBO;
import pe.com.tumi.persona.contacto.bo.DocumentoBO;
import pe.com.tumi.persona.contacto.bo.DomicilioBO;
import pe.com.tumi.persona.contacto.domain.Comunicacion;
import pe.com.tumi.persona.contacto.domain.ComunicacionPK;
import pe.com.tumi.persona.contacto.domain.Documento;
import pe.com.tumi.persona.contacto.domain.Domicilio;
import pe.com.tumi.persona.contacto.domain.DomicilioPK;

@Stateless
public class ContactoFacade extends TumiFacade implements ContactoFacadeRemote, ContactoFacadeLocal {

	private DomicilioBO boDomicilio = (DomicilioBO)TumiFactory.get(DomicilioBO.class);
	private ComunicacionBO boComunicacion = (ComunicacionBO)TumiFactory.get(ComunicacionBO.class);
	private DocumentoBO boDocumento = (DocumentoBO)TumiFactory.get(DocumentoBO.class);
	
	public Domicilio grabarDomicilio(Domicilio o)throws BusinessException{
		Domicilio dto = null;
		try{
			dto = boDomicilio.grabarDomicilio(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Domicilio getDomicilioPorPK(DomicilioPK pPK) throws BusinessException{
		Domicilio domain = null;
		try{
			domain = boDomicilio.getDomicilioPorPK(pPK);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return domain;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Domicilio> getListaDomicilio(Integer pId) throws BusinessException{
		List<Domicilio> lista = null;
		try{
			lista = boDomicilio.getListaDomicilioPorIdPersona(pId);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Comunicacion getComunicacionPorPK(ComunicacionPK pPK) throws BusinessException{
		Comunicacion domain = null;
		try{
			domain = boComunicacion.getComunicacionPorPK(pPK);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return domain;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Comunicacion> getListaComunicacion(Integer pId) throws BusinessException{
		List<Comunicacion> lista = null;
		try{
			lista = boComunicacion.getListaComunicacionPorIdPersona(pId);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public Domicilio eliminarDomicilio(Domicilio o) throws BusinessException{
		Domicilio domicilio = null;
		try{
			domicilio = boDomicilio.eliminarDomicilio(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return domicilio;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Documento getDocumentoPorIdPersonaYTipoIdentidad(Integer intIdPersona,Integer intTipoIdentidad) throws BusinessException{
		Documento dto = null;
		try{
			dto = boDocumento.getDocumentoPorIdPersonaYTipoIdentidad(intIdPersona, intTipoIdentidad);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return dto;
	}
}

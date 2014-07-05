package pe.com.tumi.persona.contacto.service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.common.util.FileUtil;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.parametro.general.domain.Archivo;
import pe.com.tumi.parametro.general.facade.GeneralFacadeRemote;
import pe.com.tumi.parametro.tabla.domain.Tabla;
import pe.com.tumi.parametro.tabla.facade.TablaFacadeRemote;
import pe.com.tumi.persona.contacto.bo.ComunicacionBO;
import pe.com.tumi.persona.contacto.bo.DocumentoBO;
import pe.com.tumi.persona.contacto.bo.DomicilioBO;
import pe.com.tumi.persona.contacto.domain.Comunicacion;
import pe.com.tumi.persona.contacto.domain.ComunicacionPK;
import pe.com.tumi.persona.contacto.domain.Documento;
import pe.com.tumi.persona.contacto.domain.DocumentoPK;
import pe.com.tumi.persona.contacto.domain.Domicilio;
import pe.com.tumi.persona.contacto.domain.DomicilioPK;

public class ContactoService {
	protected static Logger log = Logger.getLogger(ContactoService.class);
	private DomicilioBO boDomicilio = (DomicilioBO)TumiFactory.get(DomicilioBO.class);
	private ComunicacionBO boComunicacion = (ComunicacionBO)TumiFactory.get(ComunicacionBO.class);
	private DocumentoBO boDocumento = (DocumentoBO)TumiFactory.get(DocumentoBO.class); 
	
	public List<Comunicacion> grabarListaDinamicaComunicacion(List<Comunicacion> lista, Integer intIdPersona,Timestamp pTsFechaEliminacion) throws BusinessException{
		Comunicacion dto = null;
		Comunicacion dtoTemp = null;
		try{
			for(int i=0; i<lista.size(); i++){
				dto = lista.get(i);
				if(dto.getId() == null || dto.getId().getIntIdPersona() == null){
					if(dto.getId() == null)dto.setId(new ComunicacionPK());
					dto.getId().setIntIdPersona(intIdPersona);
					dto = boComunicacion.grabarComunicacion(dto);
				}else{
					dtoTemp = boComunicacion.getComunicacionPorPK(dto.getId());
					if(dtoTemp == null){
						dto.getId().setIntIdPersona(intIdPersona);
						dto = boComunicacion.grabarComunicacion(dto);
					}else{
						if(dto.getIntEstadoCod().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO)==0){
							dto.setTsFechaEliminacion(pTsFechaEliminacion);
						}
						dto = boComunicacion.modificarComunicacion(dto);
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
	
	public void eliminarComunicacionPorIdPersona(Integer intIdPersona) throws BusinessException{
		Comunicacion dto = null;
		List<Comunicacion> lista = null;
		try{
			lista = boComunicacion.getListaComunicacionPorIdPersona(intIdPersona);
			if(lista !=null && lista.size()>0){
				for(int i=0; i<lista.size(); i++){
					dto = (Comunicacion) lista.get(i);
					dto.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
					boComunicacion.modificarComunicacion(dto);
				}
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
	}
	
	public List<Domicilio> grabarListaDinamicaDomicilo(List<Domicilio> lista, Integer idPersona,Timestamp pTsFechaEliminacion) throws BusinessException{
		Domicilio dto = null;
		Domicilio dtoTemp = null;
		try{
			for(int i=0; i<lista.size(); i++){
				dto = lista.get(i);
				if(dto.getId()==null) dto.setId(new DomicilioPK());
				if(dto.getId() == null || dto.getId().getIntIdPersona() == null){
					if(dto.getId() == null)dto.setId(new DomicilioPK());
					dto.getId().setIntIdPersona(idPersona);
					//dto.setIntEnviaCorrespondencia(dto.getFgCorrespondencia()==true?1:0);
					if(dto.getCroquis()!=null){
						String strOldName = dto.getCroquis().getStrNombrearchivo();
						
						GeneralFacadeRemote generalFacade = (GeneralFacadeRemote) EJBFactory.getRemote(GeneralFacadeRemote.class);
						Archivo archivo = generalFacade.grabarArchivo(dto.getCroquis());
						
						if(archivo!=null){
							dto.setIntParaTipoArchivo(archivo.getId().getIntParaTipoCod());
							dto.setIntParaItemArchivo(archivo.getId().getIntItemArchivo());
							dto.setIntParaItemHistorico(archivo.getId().getIntItemHistorico());
							FileUtil.renombrarArchivo(archivo.getTipoarchivo().getStrRuta()+"\\"+strOldName, archivo.getTipoarchivo().getStrRuta()+"\\"+archivo.getStrNombrearchivo());
						}
					}
					dto = boDomicilio.grabarDomicilio(dto);
				}else{
					dtoTemp = boDomicilio.getDomicilioPorPK(dto.getId());
					if(dtoTemp == null){
						dto.getId().setIntIdPersona(idPersona);
						if(dto.getCroquis()!=null){
							String strOldName = dto.getCroquis().getStrNombrearchivo();
							
							GeneralFacadeRemote generalFacade = (GeneralFacadeRemote) EJBFactory.getRemote(GeneralFacadeRemote.class);
							Archivo archivo = generalFacade.grabarArchivo(dto.getCroquis());
							
							if(archivo!=null){
								dto.setIntParaTipoArchivo(archivo.getId().getIntParaTipoCod());
								dto.setIntParaItemArchivo(archivo.getId().getIntItemArchivo());
								dto.setIntParaItemHistorico(archivo.getId().getIntItemHistorico());
								FileUtil.renombrarArchivo(archivo.getTipoarchivo().getStrRuta()+"\\"+strOldName, archivo.getTipoarchivo().getStrRuta()+"\\"+archivo.getStrNombrearchivo());
							}
						}
						dto = boDomicilio.grabarDomicilio(dto);
					}else{
						if(dto.getIntEstadoCod().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO)==0){
							dto.setTsFechaEliminacion(pTsFechaEliminacion);
						}
						if(dto.getCroquis()!=null){
							String strOldName = dto.getCroquis().getStrNombrearchivo();
							
							GeneralFacadeRemote generalFacade = (GeneralFacadeRemote) EJBFactory.getRemote(GeneralFacadeRemote.class);
							Archivo archivo = generalFacade.grabarArchivo(dto.getCroquis());
							
							if(archivo!=null){
								dto.setIntParaTipoArchivo(archivo.getId().getIntParaTipoCod());
								dto.setIntParaItemArchivo(archivo.getId().getIntItemArchivo());
								dto.setIntParaItemHistorico(archivo.getId().getIntItemHistorico());
								FileUtil.renombrarArchivo(archivo.getTipoarchivo().getStrRuta()+"\\"+strOldName, archivo.getTipoarchivo().getStrRuta()+"\\"+archivo.getStrNombrearchivo());
							}
						}
						dto = boDomicilio.modificarDomicilio(dto);
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
	
	public void eliminarDomiciloPorIdPersona(Integer idPersona) throws BusinessException{
		Domicilio dto = null;
		List<Domicilio> lista = null;
		try{
			lista = boDomicilio.getListaDomicilioPorIdPersona(idPersona);
			for(int i=0; i<lista.size(); i++){
				dto = lista.get(i);
				dto.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO); 
				boDomicilio.modificarDomicilio(dto);
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
	}
	
	public List<Documento> grabarListaDinamicaDocumento(List<Documento> lista, Integer idPersona) throws BusinessException{
		Documento dto = null;
		Documento dtoTemp = null;
		try{
			for(int i=0; i<lista.size(); i++){
				dto = lista.get(i);
				if(dto.getId() == null || dto.getId().getIntIdPersona() == null){
					if(dto.getId() == null)dto.setId(new DocumentoPK());
					dto.getId().setIntIdPersona(idPersona);
					dto = boDocumento.grabarDocumento(dto);
				}else{
					dtoTemp = boDocumento.getDocumentoPorPK(dto.getId());
					if(dtoTemp == null){
						dto.getId().setIntIdPersona(idPersona);
						//Agregado 25.03.2014 JCHAVEZ
						dtoTemp = boDocumento.getDocumentoPorIdPersonaYTipoIdentidad(dto.getId().getIntIdPersona(), dto.getIntTipoIdentidadCod());
						if (dtoTemp == null) {
							dto = boDocumento.grabarDocumento(dto);
						}						
//						dto = boDocumento.grabarDocumento(dto);
					}else{
						dto = boDocumento.modificarDocumento(dto);
					}
				}
				dto = boDocumento.modificarDocumento(dto);
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public void eliminarDocumentoPorIdPersona(Integer idPersona) throws BusinessException{
		Documento dto = null;
		List<Documento> lista = null;
		try{
			lista = boDocumento.getListaDocumentoPorIdPersona(idPersona);
			for(int i=0; i<lista.size(); i++){
				dto = lista.get(i);
				dto.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO); 
				boDocumento.modificarDocumento(dto);
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
	}
	
	public List<Documento> getListaDocumentoPorIdPersona(Integer pId) throws BusinessException{
		List<Documento> lista = null;
		List<Tabla> listTabla = null;
		Documento documento = null;
		Tabla tabla = null;
		try{
			TablaFacadeRemote tablaFacade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
    		listTabla = tablaFacade.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_TIPODOCUMENTO));
			
    		lista = boDocumento.getListaDocumentoPorIdPersona(pId);
			
			for(int i=0; i<lista.size(); i++){
				documento = lista.get(i);
				for(int j=0; j<listTabla.size(); j++){
					tabla = listTabla.get(j);
					if(tabla.getIntIdDetalle().equals(documento.getIntTipoIdentidadCod())){
						documento.setTabla(tabla);
					}
				}
			}
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
}

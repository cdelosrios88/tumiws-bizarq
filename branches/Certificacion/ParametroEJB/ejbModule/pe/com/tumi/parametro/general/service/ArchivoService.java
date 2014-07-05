package pe.com.tumi.parametro.general.service;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.common.util.Constante;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.framework.util.fecha.JFecha;
import pe.com.tumi.parametro.general.bo.ArchivoBO;
import pe.com.tumi.parametro.general.bo.TipoArchivoBO;
import pe.com.tumi.parametro.general.domain.Archivo;
import pe.com.tumi.parametro.general.domain.ArchivoId;
import pe.com.tumi.parametro.general.domain.TipoArchivo;

public class ArchivoService {
	private ArchivoBO boArchivo = (ArchivoBO)TumiFactory.get(ArchivoBO.class);
	private TipoArchivoBO boTipoArchivo = (TipoArchivoBO)TumiFactory.get(TipoArchivoBO.class);
	
	public Archivo grabarArchivo(Archivo o)throws BusinessException{
		Archivo dto = null;
		Archivo dtoOld = null;
		TipoArchivo tipo = null;
		try{
			if(o.getId()!=null){
				if(o.getId().getIntParaTipoCod() != null && o.getId().getIntItemArchivo() != null){  
					
					if(o.getId().getIntItemHistorico() != null){
						dtoOld = boArchivo.getArchivoPorPK(o.getId());
					}else{
						dtoOld = boArchivo.getListaArchivoDeVersionFinalPorTipoYItem(o.getId().getIntParaTipoCod(),
																					 o.getId().getIntItemArchivo());
					}
					if(dtoOld != null){
						if(dtoOld.getIntParaEstadoCod().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO) !=0 ){
							dtoOld.setTsFechaEliminacion(JFecha.obtenerTimestampDeFechayHoraActual());
							dtoOld.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
							boArchivo.modificarArchivo(dtoOld);
						}
						dto = boArchivo.grabarArchivoVersion(o);
					}else{
						dto = boArchivo.grabarArchivo(o);
					}
				}else{
					dto = boArchivo.grabarArchivo(o);	
				}
				
				tipo = boTipoArchivo.getTipoArchivoPorPk(dto.getId().getIntParaTipoCod());
				dto.setTipoarchivo(tipo);
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public Archivo getArchivoPorPK(ArchivoId pId) throws BusinessException{
		Archivo archivo = null;
		TipoArchivo tipo = null;
		try{
			archivo = boArchivo.getArchivoPorPK(pId);
			if(archivo!=null){
				tipo = boTipoArchivo.getTipoArchivoPorPk(archivo.getId().getIntParaTipoCod());
				archivo.setTipoarchivo(tipo);
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return archivo;
	}
	
	public Archivo eliminarArchivo(Archivo o)throws BusinessException{
		//Archivo dto = null;
		//Archivo dtoOld = null;
		TipoArchivo tipo = null;
		try{
			if(o.getId()!=null){
				if(o.getId().getIntParaTipoCod() != null && o.getId().getIntItemArchivo() != null){
					o.setTsFechaEliminacion(JFecha.obtenerTimestampDeFechayHoraActual());
					o.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
					boArchivo.modificarArchivo(o);
						//o = boArchivo.grabarArchivoVersion(o);
				}
				
				tipo = boTipoArchivo.getTipoArchivoPorPk(o.getId().getIntParaTipoCod());
				o.setTipoarchivo(tipo);
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return o;
		
	}
	
}

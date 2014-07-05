package pe.com.tumi.parametro.general.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.parametro.general.dao.ArchivoDao;
import pe.com.tumi.parametro.general.dao.impl.ArchivoDaoIbatis;
import pe.com.tumi.parametro.general.domain.Archivo;
import pe.com.tumi.parametro.general.domain.ArchivoId;

public class ArchivoBO {
	
	private ArchivoDao dao = (ArchivoDao)TumiFactory.get(ArchivoDaoIbatis.class);
	
	public Archivo grabarArchivo(Archivo o) throws BusinessException{
		Archivo dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public Archivo grabarArchivoVersion(Archivo o) throws BusinessException{
		Archivo dto = null;
		try{
			dto = dao.grabarVersion(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public Archivo modificarArchivo(Archivo o) throws BusinessException{
		Archivo dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public Archivo getArchivoPorPK(ArchivoId pId) throws BusinessException{
		Archivo domain = null;
		List<Archivo> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intParaTipoCod", pId.getIntParaTipoCod());
			mapa.put("intItemArchivo", pId.getIntItemArchivo());
			mapa.put("intItemHistorico", pId.getIntItemHistorico());
			lista = dao.getListaPorPK(mapa);
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
	
	public Archivo getListaArchivoDeVersionFinalPorTipoYItem(Integer pIntParaTipoCod,Integer pIntItemArchivo) throws BusinessException{
		Archivo domain = null;
		List<Archivo> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intParaTipoCod", pIntParaTipoCod);
			mapa.put("intItemArchivo", pIntItemArchivo);
			lista = dao.getListaVersionFinPorTipoYItem(mapa);
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

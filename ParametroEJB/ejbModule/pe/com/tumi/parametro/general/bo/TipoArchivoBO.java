package pe.com.tumi.parametro.general.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.parametro.general.dao.TipoArchivoDao;
import pe.com.tumi.parametro.general.dao.impl.TipoArchivoDaoIbatis;
import pe.com.tumi.parametro.general.domain.TipoArchivo;

public class TipoArchivoBO {
	
	private TipoArchivoDao dao = (TipoArchivoDao)TumiFactory.get(TipoArchivoDaoIbatis.class);
	
	public TipoArchivo grabarTipoArchivo(TipoArchivo o) throws BusinessException{
		TipoArchivo dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public TipoArchivo modificarTipoArchivo(TipoArchivo o) throws BusinessException{
		TipoArchivo dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public TipoArchivo getTipoArchivoPorPk(Integer pIntPK) throws BusinessException{
		TipoArchivo domain = null;
		List<TipoArchivo> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intParaTipoCod", pIntPK);
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
	
}

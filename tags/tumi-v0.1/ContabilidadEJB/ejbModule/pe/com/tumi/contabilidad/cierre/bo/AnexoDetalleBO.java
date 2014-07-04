package pe.com.tumi.contabilidad.cierre.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.contabilidad.cierre.dao.AnexoDetalleDao;
import pe.com.tumi.contabilidad.cierre.dao.impl.AnexoDetalleDaoIbatis;
import pe.com.tumi.contabilidad.cierre.domain.Anexo;
import pe.com.tumi.contabilidad.cierre.domain.AnexoDetalle;
import pe.com.tumi.contabilidad.cierre.domain.AnexoDetalleId;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;

public class AnexoDetalleBO {
	private AnexoDetalleDao dao = (AnexoDetalleDao)TumiFactory.get(AnexoDetalleDaoIbatis.class);

	public AnexoDetalle grabar(AnexoDetalle o) throws BusinessException{
		AnexoDetalle dto = null;
		try{
		    dto = dao.grabar(o);
		}catch(DAOException e){
	  		throw new BusinessException(e);
	    }catch(Exception e){
	    	throw new BusinessException(e);
	    }
	    return dto;
	}
	
  	public AnexoDetalle modificar(AnexoDetalle o) throws BusinessException{
  		AnexoDetalle dto = null;
     	try{
		     dto = dao.modificar(o);
	  	}catch(DAOException e){
	  		 throw new BusinessException(e);
	    }catch(Exception e){
	    	 throw new BusinessException(e);
	    }
	    return dto;
    }
  
	public AnexoDetalle getPorPk(AnexoDetalleId pId) throws BusinessException{
		AnexoDetalle domain = null;
		List<AnexoDetalle> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaAnexo",	pId.getIntPersEmpresaAnexo());
			mapa.put("intContPeriodoAnexo",	pId.getIntContPeriodoAnexo());
			mapa.put("intParaTipoAnexo", 	pId.getIntParaTipoAnexo());
			mapa.put("intItemAnexoDetalle", pId.getIntItemAnexoDetalle());
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
	
	public List<AnexoDetalle> getPorAnexo(Anexo o) throws BusinessException{
		List<AnexoDetalle> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaAnexo",	o.getId().getIntPersEmpresaAnexo());
			mapa.put("intContPeriodoAnexo",	o.getId().getIntContPeriodoAnexo());
			mapa.put("intParaTipoAnexo", 	o.getId().getIntParaTipoAnexo());
			lista = dao.getListaPorAnexo(mapa);

		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public void eliminar(AnexoDetalle o) throws BusinessException{
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaAnexo",	o.getId().getIntPersEmpresaAnexo());
			mapa.put("intContPeriodoAnexo",	o.getId().getIntContPeriodoAnexo());
			mapa.put("intParaTipoAnexo", 	o.getId().getIntParaTipoAnexo());
			mapa.put("intItemAnexoDetalle",	o.getId().getIntItemAnexoDetalle());
			dao.eliminar(mapa);
			
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}		
	}
}
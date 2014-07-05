package pe.com.tumi.parametro.auditoria.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.parametro.auditoria.dao.AuditoriaDao;
import pe.com.tumi.parametro.auditoria.dao.AuditoriaMotivoDao;
import pe.com.tumi.parametro.auditoria.dao.impl.AuditoriaDaoIbatis;
import pe.com.tumi.parametro.auditoria.dao.impl.AuditoriaMotivoDaoIbatis;
import pe.com.tumi.parametro.auditoria.domain.Auditoria;
import pe.com.tumi.parametro.auditoria.domain.AuditoriaMotivo;

public class AuditoriaMotivoBO {
	private AuditoriaMotivoDao dao = (AuditoriaMotivoDao)TumiFactory.get(AuditoriaMotivoDaoIbatis.class);

	public AuditoriaMotivo grabar(AuditoriaMotivo o) throws BusinessException{
		AuditoriaMotivo dto = null;
		try{
		    dto = dao.grabar(o);
		}catch(DAOException e){
	  		throw new BusinessException(e);
	    }catch(Exception e){
	    	throw new BusinessException(e);
	    }
	    return dto;
	}

  	public AuditoriaMotivo modificar(AuditoriaMotivo o) throws BusinessException{
  		AuditoriaMotivo dto = null;
     	try{
		     dto = dao.modificar(o);
	  	}catch(DAOException e){
	  		 throw new BusinessException(e);
	    }catch(Exception e){
	    	 throw new BusinessException(e);
	    }
	    return dto;
    }
  
	public AuditoriaMotivo getListaPorPk(Integer pIdAuditoria, Integer pidMotivo) throws BusinessException{
		AuditoriaMotivo domain = null;
		List<AuditoriaMotivo> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intIdcodigo", pIdAuditoria);
			mapa.put("intAuditoriaMotivo", pidMotivo);
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
	
	public AuditoriaMotivo getListaPorAuditoria(Integer pIdAuditoria) throws BusinessException{
		AuditoriaMotivo domain = null;
		List<AuditoriaMotivo> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intIdcodigo", pIdAuditoria);
			lista = dao.getListaPorAuditoria(mapa);
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

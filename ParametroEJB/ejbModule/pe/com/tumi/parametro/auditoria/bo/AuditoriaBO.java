package pe.com.tumi.parametro.auditoria.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.parametro.auditoria.dao.AuditoriaDao;
import pe.com.tumi.parametro.auditoria.dao.impl.AuditoriaDaoIbatis;
import pe.com.tumi.parametro.auditoria.domain.Auditoria;

public class AuditoriaBO{

	private AuditoriaDao dao = (AuditoriaDao)TumiFactory.get(AuditoriaDaoIbatis.class);

	public Auditoria grabarAuditoria(Auditoria o) throws BusinessException{
		Auditoria dto = null;
		try{
		    dto = dao.grabar(o);
		}catch(DAOException e){
	  		throw new BusinessException(e);
	    }catch(Exception e){
	    	throw new BusinessException(e);
	    }
	    return dto;
	}

  	public Auditoria modificarAuditoria(Auditoria o) throws BusinessException{
     	Auditoria dto = null;
     	try{
		     dto = dao.modificar(o);
	  	}catch(DAOException e){
	  		 throw new BusinessException(e);
	    }catch(Exception e){
	    	 throw new BusinessException(e);
	    }
	    return dto;
    }
  
	public Auditoria getAuditoriaPorPk(java.lang.Integer pId) throws BusinessException{
		Auditoria domain = null;
		List<Auditoria> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intIdcodigo", pId);
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
	
	public Auditoria getAuditoriaDeMaximoPkPorTablaYColumnaYLlave1(String strTabla,String strColumna,String strLlave1) throws BusinessException{
		Auditoria domain = null;
		List<Auditoria> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("strTabla", strTabla);
			mapa.put("strColumna", strColumna);
			mapa.put("strLlave1", strLlave1);
			lista = dao.getListaDeMaxPkPorTabColLlave(mapa);
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

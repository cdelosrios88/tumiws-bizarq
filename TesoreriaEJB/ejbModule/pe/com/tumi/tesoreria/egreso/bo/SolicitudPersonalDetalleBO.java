package pe.com.tumi.tesoreria.egreso.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.tesoreria.egreso.dao.SolicitudPersonalDetalleDao;
import pe.com.tumi.tesoreria.egreso.dao.impl.SolicitudPersonalDetalleDaoIbatis;
import pe.com.tumi.tesoreria.egreso.domain.SolicitudPersonal;
import pe.com.tumi.tesoreria.egreso.domain.SolicitudPersonalDetalle;
import pe.com.tumi.tesoreria.egreso.domain.SolicitudPersonalDetalleId;


public class SolicitudPersonalDetalleBO{

	private SolicitudPersonalDetalleDao dao = (SolicitudPersonalDetalleDao)TumiFactory.get(SolicitudPersonalDetalleDaoIbatis.class);

	public SolicitudPersonalDetalle grabar(SolicitudPersonalDetalle o) throws BusinessException{
		SolicitudPersonalDetalle dto = null;
		try{
		    dto = dao.grabar(o);
		}catch(DAOException e){
	  		throw new BusinessException(e);
	    }catch(Exception e){
	    	throw new BusinessException(e);
	    }
	    return dto;
	}

  	public SolicitudPersonalDetalle modificar(SolicitudPersonalDetalle o) throws BusinessException{
  		SolicitudPersonalDetalle dto = null;
     	try{
		     dto = dao.modificar(o);
	  	}catch(DAOException e){
	  		 throw new BusinessException(e);
	    }catch(Exception e){
	    	 throw new BusinessException(e);
	    }
	    return dto;
    }
  
	public SolicitudPersonalDetalle getPorId(SolicitudPersonalDetalleId pId) throws BusinessException{
		SolicitudPersonalDetalle domain = null;
		List<SolicitudPersonalDetalle> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresa", pId.getIntPersEmpresa());
			mapa.put("intItemSolicitudPersonal", pId.getIntItemSolicitudPersonal());
			mapa.put("intItemSolicitudPersonalDetalle", pId.getIntItemSolicitudPersonal());
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
	
	public List<SolicitudPersonalDetalle> getPorSolicitudPersonal(SolicitudPersonal solicitudPersonal) throws BusinessException{
		List<SolicitudPersonalDetalle> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresa", solicitudPersonal.getId().getIntPersEmpresa());
			mapa.put("intItemSolicitudPersonal", solicitudPersonal.getId().getIntItemSolicitudPersonal());
			lista = dao.getListaPorSolicitudPersonal(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public SolicitudPersonalDetalle eliminar(SolicitudPersonalDetalle solicitadPersonalDetalle) throws BusinessException{
		SolicitudPersonalDetalle domain = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresa", solicitadPersonalDetalle.getId().getIntPersEmpresa());
			mapa.put("intItemSolicitudPersonal", solicitadPersonalDetalle.getId().getIntItemSolicitudPersonal());
			mapa.put("intItemSolicitudPersonalDetalle", solicitadPersonalDetalle.getId().getIntItemSolicitudPersonalDetalle());
			dao.eliminar(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return domain;
	}	
}
package pe.com.tumi.tesoreria.egreso.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.tesoreria.egreso.dao.SolicitudPersonalPagoDao;
import pe.com.tumi.tesoreria.egreso.dao.impl.SolicitudPersonalPagoDaoIbatis;
import pe.com.tumi.tesoreria.egreso.domain.SolicitudPersonal;
import pe.com.tumi.tesoreria.egreso.domain.SolicitudPersonalPago;
import pe.com.tumi.tesoreria.egreso.domain.SolicitudPersonalPagoId;


public class SolicitudPersonalPagoBO{

	private SolicitudPersonalPagoDao dao = (SolicitudPersonalPagoDao)TumiFactory.get(SolicitudPersonalPagoDaoIbatis.class);

	public SolicitudPersonalPago grabar(SolicitudPersonalPago o) throws BusinessException{
		SolicitudPersonalPago dto = null;
		try{
		    dto = dao.grabar(o);
		}catch(DAOException e){
	  		throw new BusinessException(e);
	    }catch(Exception e){
	    	throw new BusinessException(e);
	    }
	    return dto;
	}

  	public SolicitudPersonalPago modificar(SolicitudPersonalPago o) throws BusinessException{
  		SolicitudPersonalPago dto = null;
     	try{
		     dto = dao.modificar(o);
	  	}catch(DAOException e){
	  		 throw new BusinessException(e);
	    }catch(Exception e){
	    	 throw new BusinessException(e);
	    }
	    return dto;
    }
  
	public SolicitudPersonalPago getPorId(SolicitudPersonalPagoId pId) throws BusinessException{
		SolicitudPersonalPago domain = null;
		List<SolicitudPersonalPago> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresa", pId.getIntPersEmpresa());
			mapa.put("intItemSolicitudPersonal", pId.getIntItemSolicitudPersonal());
			mapa.put("intItemSolicitudPersonalPago", pId.getIntItemSolicitudPersonalPago());
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
	
	public List<SolicitudPersonalPago> getPorSolicitudPersonal(SolicitudPersonal solicitudPersonal) throws BusinessException{
		List<SolicitudPersonalPago> lista = null;
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
}
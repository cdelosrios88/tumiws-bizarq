package pe.com.tumi.tesoreria.egreso.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.tesoreria.egreso.dao.SolicitudPersonalDao;
import pe.com.tumi.tesoreria.egreso.dao.impl.SolicitudPersonalDaoIbatis;
import pe.com.tumi.tesoreria.egreso.domain.SolicitudPersonal;
import pe.com.tumi.tesoreria.egreso.domain.SolicitudPersonalId;


public class SolicitudPersonalBO{

	private SolicitudPersonalDao dao = (SolicitudPersonalDao)TumiFactory.get(SolicitudPersonalDaoIbatis.class);

	public SolicitudPersonal grabar(SolicitudPersonal o) throws BusinessException{
		SolicitudPersonal dto = null;
		try{
		    dto = dao.grabar(o);
		}catch(DAOException e){
	  		throw new BusinessException(e);
	    }catch(Exception e){
	    	throw new BusinessException(e);
	    }
	    return dto;
	}

  	public SolicitudPersonal modificar(SolicitudPersonal o) throws BusinessException{
  		SolicitudPersonal dto = null;
     	try{
		     dto = dao.modificar(o);
	  	}catch(DAOException e){
	  		 throw new BusinessException(e);
	    }catch(Exception e){
	    	 throw new BusinessException(e);
	    }
	    return dto;
    }
  
	public SolicitudPersonal getPorId(SolicitudPersonalId pId) throws BusinessException{
		SolicitudPersonal domain = null;
		List<SolicitudPersonal> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresa", pId.getIntPersEmpresa());
			mapa.put("intItemSolicitudPersonal", pId.getIntItemSolicitudPersonal());
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
	
	public List<SolicitudPersonal> getListaParaBuscar(SolicitudPersonal solicitudPersonal) throws BusinessException{
		List<SolicitudPersonal> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresa", solicitudPersonal.getId().getIntPersEmpresa());
			mapa.put("intParaDocumentoGeneral", solicitudPersonal.getIntParaDocumentoGeneral());
			mapa.put("tsFechaRegistro", solicitudPersonal.getTsFechaRegistro());
			mapa.put("intParaEstado", solicitudPersonal.getIntParaEstado());
			mapa.put("intPeriodoPago", solicitudPersonal.getIntPeriodoPago());
			mapa.put("intParaEstadoPago", solicitudPersonal.getIntParaEstadoPago());
			lista = dao.getListaPorBuscar(mapa);
			
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}	
}
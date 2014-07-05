package pe.com.tumi.tesoreria.banco.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.tesoreria.banco.dao.AccesoDao;
import pe.com.tumi.tesoreria.banco.dao.AccesoDetalleDao;
import pe.com.tumi.tesoreria.banco.dao.impl.AccesoDaoIbatis;
import pe.com.tumi.tesoreria.banco.dao.impl.AccesoDetalleDaoIbatis;
import pe.com.tumi.tesoreria.banco.domain.Acceso;
import pe.com.tumi.tesoreria.banco.domain.AccesoDetalle;
import pe.com.tumi.tesoreria.banco.domain.AccesoDetalleId;
import pe.com.tumi.tesoreria.banco.domain.AccesoId;


public class AccesoDetalleBO{

	private AccesoDetalleDao dao = (AccesoDetalleDao)TumiFactory.get(AccesoDetalleDaoIbatis.class);

	public AccesoDetalle grabar(AccesoDetalle o) throws BusinessException{
		AccesoDetalle dto = null;
		try{
		    dto = dao.grabar(o);
		}catch(DAOException e){
	  		throw new BusinessException(e);
	    }catch(Exception e){
	    	throw new BusinessException(e);
	    }
	    return dto;
	}

  	public AccesoDetalle modificar(AccesoDetalle o) throws BusinessException{
  		AccesoDetalle dto = null;
     	try{
		     dto = dao.modificar(o);
	  	}catch(DAOException e){
	  		 throw new BusinessException(e);
	    }catch(Exception e){
	    	 throw new BusinessException(e);
	    }
	    return dto;
    }
  
	public AccesoDetalle getPorPk(AccesoDetalleId pId) throws BusinessException{
		AccesoDetalle domain = null;
		List<AccesoDetalle> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaAcceso", pId.getIntPersEmpresaAcceso());
			mapa.put("intItemAcceso", pId.getIntItemAcceso());
			mapa.put("intItemAccesoDetalle", pId.getIntItemAccesoDetalle());
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
	
	public List<AccesoDetalle> getPorAcceso(Acceso o) throws BusinessException{
		List<AccesoDetalle> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaAcceso",o.getId().getIntPersEmpresaAcceso());
			mapa.put("intItemAcceso", o.getId().getIntItemAcceso());
			lista = dao.getListaPorAcceso(mapa);				
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
}
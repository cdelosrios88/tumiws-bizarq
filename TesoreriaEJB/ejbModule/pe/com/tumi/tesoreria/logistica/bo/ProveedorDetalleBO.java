package pe.com.tumi.tesoreria.logistica.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.tesoreria.logistica.dao.ProveedorDetalleDao;
import pe.com.tumi.tesoreria.logistica.dao.impl.ProveedorDetalleDaoIbatis;
import pe.com.tumi.tesoreria.logistica.domain.Proveedor;
import pe.com.tumi.tesoreria.logistica.domain.ProveedorDetalle;
import pe.com.tumi.tesoreria.logistica.domain.ProveedorDetalleId;


public class ProveedorDetalleBO{

	private ProveedorDetalleDao dao = (ProveedorDetalleDao)TumiFactory.get(ProveedorDetalleDaoIbatis.class);

	public ProveedorDetalle grabar(ProveedorDetalle o) throws BusinessException{
		ProveedorDetalle dto = null;
		try{
		    dto = dao.grabar(o);
		}catch(DAOException e){
	  		throw new BusinessException(e);
	    }catch(Exception e){
	    	throw new BusinessException(e);
	    }
	    return dto;
	}

  	public ProveedorDetalle modificar(ProveedorDetalle o) throws BusinessException{
  		ProveedorDetalle dto = null;
     	try{
		     dto = dao.modificar(o);
	  	}catch(DAOException e){
	  		 throw new BusinessException(e);
	    }catch(Exception e){
	    	 throw new BusinessException(e);
	    }
	    return dto;
    }
  
	public ProveedorDetalle getPorPk(ProveedorDetalleId pId) throws BusinessException{
		ProveedorDetalle domain = null;
		List<ProveedorDetalle> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresa", pId.getIntPersEmpresa());
			mapa.put("intPersPersona", pId.getIntPersPersona());
			mapa.put("intItemProveedorDetalle", pId.getIntItemProveedorDetalle());
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

	public List<ProveedorDetalle> getPorProveedor(Proveedor o) throws BusinessException{
		List<ProveedorDetalle> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresa", o.getId().getIntPersEmpresa());
			mapa.put("intPersPersona", o.getId().getIntPersPersona());
			lista = dao.getListaPorProveedor(mapa);				
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public void eliminar(ProveedorDetalle o) throws BusinessException{
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresa", o.getId().getIntPersEmpresa());
			mapa.put("intPersPersona", o.getId().getIntPersPersona());
			mapa.put("intItemProveedorDetalle", o.getId().getIntItemProveedorDetalle());
			dao.eliminar(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
	}
}
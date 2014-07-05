package pe.com.tumi.tesoreria.logistica.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.tesoreria.logistica.dao.ProveedorDao;
import pe.com.tumi.tesoreria.logistica.dao.impl.ProveedorDaoIbatis;
import pe.com.tumi.tesoreria.logistica.domain.OrdenCompra;
import pe.com.tumi.tesoreria.logistica.domain.Proveedor;
import pe.com.tumi.tesoreria.logistica.domain.ProveedorId;


public class ProveedorBO{

	private ProveedorDao dao = (ProveedorDao)TumiFactory.get(ProveedorDaoIbatis.class);

	public Proveedor grabar(Proveedor o) throws BusinessException{
		Proveedor dto = null;
		try{
		    dto = dao.grabar(o);
		}catch(DAOException e){
	  		throw new BusinessException(e);
	    }catch(Exception e){
	    	throw new BusinessException(e);
	    }
	    return dto;
	}

  	public Proveedor modificar(Proveedor o) throws BusinessException{
  		Proveedor dto = null;
     	try{
		     dto = dao.modificar(o);
	  	}catch(DAOException e){
	  		 throw new BusinessException(e);
	    }catch(Exception e){
	    	 throw new BusinessException(e);
	    }
	    return dto;
    }
  
	public Proveedor getPorPk(ProveedorId pId) throws BusinessException{
		Proveedor domain = null;
		List<Proveedor> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresa", pId.getIntPersEmpresa());
			mapa.put("intPersPersona", pId.getIntPersPersona());
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
	
	public List<Proveedor> getPorBusqueda(Proveedor o) throws BusinessException{
		List<Proveedor> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();			
			mapa.put("intPersEmpresa", o.getId().getIntPersEmpresa());
			mapa.put("intPersPersona", o.getId().getIntPersPersona());
			lista = dao.getListaPorBusqueda(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}

	public Proveedor getPorOrdenCompra(OrdenCompra ordenCompra) throws BusinessException{
		try{
			ProveedorId proveedorId = new ProveedorId();
			proveedorId.setIntPersEmpresa(ordenCompra.getIntPersEmpresaProveedor());
			proveedorId.setIntPersPersona(ordenCompra.getIntPersPersonaProveedor());
			
			return getPorPk(proveedorId);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
	}
}
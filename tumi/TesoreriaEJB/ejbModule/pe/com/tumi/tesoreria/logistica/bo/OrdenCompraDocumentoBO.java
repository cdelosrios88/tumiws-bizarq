package pe.com.tumi.tesoreria.logistica.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.tesoreria.logistica.dao.OrdenCompraDocumentoDao;
import pe.com.tumi.tesoreria.logistica.dao.impl.OrdenCompraDocumentoDaoIbatis;
import pe.com.tumi.tesoreria.logistica.domain.OrdenCompra;
import pe.com.tumi.tesoreria.logistica.domain.OrdenCompraDocumento;
import pe.com.tumi.tesoreria.logistica.domain.OrdenCompraDocumentoId;


public class OrdenCompraDocumentoBO{

	private OrdenCompraDocumentoDao dao = (OrdenCompraDocumentoDao)TumiFactory.get(OrdenCompraDocumentoDaoIbatis.class);

	public OrdenCompraDocumento grabar(OrdenCompraDocumento o) throws BusinessException{
		OrdenCompraDocumento dto = null;
		try{
		    dto = dao.grabar(o);
		}catch(DAOException e){
	  		throw new BusinessException(e);
	    }catch(Exception e){
	    	throw new BusinessException(e);
	    }
	    return dto;
	}

  	public OrdenCompraDocumento modificar(OrdenCompraDocumento o) throws BusinessException{
  		OrdenCompraDocumento dto = null;
     	try{
		     dto = dao.modificar(o);
	  	}catch(DAOException e){
	  		 throw new BusinessException(e);
	    }catch(Exception e){
	    	 throw new BusinessException(e);
	    }
	    return dto;
    }
  
	public OrdenCompraDocumento getPorPk(OrdenCompraDocumentoId pId) throws BusinessException{
		OrdenCompraDocumento domain = null;
		List<OrdenCompraDocumento> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresa", pId.getIntPersEmpresa());
			mapa.put("intItemOrdenCompra", pId.getIntItemOrdenCompra());
			mapa.put("intItemOrdenCompraDocumento", pId.getIntItemOrdenCompraDocumento());
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
	
	public List<OrdenCompraDocumento> getPorOrdenCompra(OrdenCompra ordenCompra) throws BusinessException{
		List<OrdenCompraDocumento> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresa", ordenCompra.getId().getIntPersEmpresa());
			mapa.put("intItemOrdenCompra", ordenCompra.getId().getIntItemOrdenCompra());
			lista = dao.getListaPorOrdenCompra(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}	
}
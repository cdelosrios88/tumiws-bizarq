package pe.com.tumi.tesoreria.logistica.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.tesoreria.logistica.dao.AdelantoSunatDao;
import pe.com.tumi.tesoreria.logistica.dao.ContratoDao;
import pe.com.tumi.tesoreria.logistica.dao.impl.AdelantoSunatDaoIbatis;
import pe.com.tumi.tesoreria.logistica.dao.impl.ContratoDaoIbatis;
import pe.com.tumi.tesoreria.logistica.domain.AdelantoSunat;
import pe.com.tumi.tesoreria.logistica.domain.AdelantoSunatId;
import pe.com.tumi.tesoreria.logistica.domain.Contrato;
import pe.com.tumi.tesoreria.logistica.domain.ContratoId;
import pe.com.tumi.tesoreria.logistica.domain.DocumentoSunatId;
import pe.com.tumi.tesoreria.logistica.domain.OrdenCompraDocumento;


public class AdelantoSunatBO{

	private AdelantoSunatDao dao = (AdelantoSunatDao)TumiFactory.get(AdelantoSunatDaoIbatis.class);

	public AdelantoSunat grabar(AdelantoSunat o) throws BusinessException{
		AdelantoSunat dto = null;
		try{
		    dto = dao.grabar(o);
		}catch(DAOException e){
	  		throw new BusinessException(e);
	    }catch(Exception e){
	    	throw new BusinessException(e);
	    }
	    return dto;
	}

  	public AdelantoSunat modificar(AdelantoSunat o) throws BusinessException{
  		AdelantoSunat dto = null;
     	try{
		     dto = dao.modificar(o);
	  	}catch(DAOException e){
	  		 throw new BusinessException(e);
	    }catch(Exception e){
	    	 throw new BusinessException(e);
	    }
	    return dto;
    }
  
	public AdelantoSunat getPorPk(AdelantoSunatId pId) throws BusinessException{
		AdelantoSunat domain = null;
		List<AdelantoSunat> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresa", pId.getIntPersEmpresa());
			mapa.put("intItemAdelantoSunat", pId.getIntItemAdelantoSunat());
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
	
	public List<AdelantoSunat> getPorOrdenCompraDocumento(OrdenCompraDocumento ordenCompraDocumento) throws BusinessException{
		List<AdelantoSunat> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresa", ordenCompraDocumento.getId().getIntPersEmpresa());
			mapa.put("intItemOrdenCompra", ordenCompraDocumento.getId().getIntItemOrdenCompra());
			mapa.put("intItemOrdenCompraDocumento", ordenCompraDocumento.getId().getIntItemOrdenCompraDocumento());
			lista = dao.getListaPorOrdenCompraDocumento(mapa);	
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	//Agregado por cdelosrios, 13/11/2013
	public AdelantoSunat getPorDocumentoSunat(DocumentoSunatId o) throws BusinessException{
		AdelantoSunat domain = null;
		List<AdelantoSunat> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresa", 			o.getIntPersEmpresa());
			mapa.put("intItemDocumentoSunat", 	o.getIntItemDocumentoSunat());
			lista = dao.getListaPorDocumentoSunat(mapa);
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
	//Fin agregado por cdelosrios, 13/11/2013
}
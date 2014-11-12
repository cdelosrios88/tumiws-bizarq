package pe.com.tumi.tesoreria.logistica.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.tesoreria.logistica.dao.DocumentoSunatDetalleDao;
import pe.com.tumi.tesoreria.logistica.dao.impl.DocumentoSunatDetalleDaoIbatis;
import pe.com.tumi.tesoreria.logistica.domain.DocumentoSunat;
import pe.com.tumi.tesoreria.logistica.domain.DocumentoSunatDetalle;
import pe.com.tumi.tesoreria.logistica.domain.DocumentoSunatDetalleId;
import pe.com.tumi.tesoreria.logistica.domain.OrdenCompraDetalle;


public class DocumentoSunatDetalleBO{

	private DocumentoSunatDetalleDao dao = (DocumentoSunatDetalleDao)TumiFactory.get(DocumentoSunatDetalleDaoIbatis.class);

	public DocumentoSunatDetalle grabar(DocumentoSunatDetalle o) throws BusinessException{
		DocumentoSunatDetalle dto = null;
		try{
		    dto = dao.grabar(o);
		}catch(DAOException e){
	  		throw new BusinessException(e);
	    }catch(Exception e){
	    	throw new BusinessException(e);
	    }
	    return dto;
	}

  	public DocumentoSunatDetalle modificar(DocumentoSunatDetalle o) throws BusinessException{
  		DocumentoSunatDetalle dto = null;
     	try{
		     dto = dao.modificar(o);
	  	}catch(DAOException e){
	  		 throw new BusinessException(e);
	    }catch(Exception e){
	    	 throw new BusinessException(e);
	    }
	    return dto;
    }
  
	public DocumentoSunatDetalle getPorPk(DocumentoSunatDetalleId pId) throws BusinessException{
		DocumentoSunatDetalle domain = null;
		List<DocumentoSunatDetalle> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresa", pId.getIntPersEmpresa());
			mapa.put("intItemDocumentoSunat", pId.getIntItemDocumentoSunat());
			mapa.put("intItemDocumentoSunatDetalle", pId.getIntItemDocumentoSunatDetalle());
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
	
	public List<DocumentoSunatDetalle> getPorDocumentoSunat(DocumentoSunat documentoSunat) throws BusinessException{
		List<DocumentoSunatDetalle> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresa", documentoSunat.getId().getIntPersEmpresa());
			mapa.put("intItemDocumentoSunat", documentoSunat.getId().getIntItemDocumentoSunat());
			lista = dao.getListaPorDocumentoSunat(mapa);
			
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<DocumentoSunatDetalle> getPorOrdenCompraDetalle(OrdenCompraDetalle ordenCompraDetalle) throws BusinessException{
		List<DocumentoSunatDetalle> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaOrdenCompra", ordenCompraDetalle.getId().getIntPersEmpresa());
			mapa.put("intItemOrdenCompra", ordenCompraDetalle.getId().getIntItemOrdenCompra());
			mapa.put("intItemOrdenCompraDetalle", ordenCompraDetalle.getId().getIntItemOrdenCompraDetalle());
			lista = dao.getListaPorOrdenCompraDetalle(mapa);
			
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	//Agregado por cdelosrios, 01/11/2013
	public List<DocumentoSunatDetalle> getPorDocumentoSunatYTipoDocSunat(DocumentoSunat documentoSunat, Integer intParaTipoDocumentoSunat) throws BusinessException{
//		DocumentoSunatDetalle domain = null;
		List<DocumentoSunatDetalle> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresa", 				documentoSunat.getId().getIntPersEmpresa());
			mapa.put("intItemDocumentoSunat", 		documentoSunat.getId().getIntItemDocumentoSunat());
			mapa.put("intParaTipoCptoDocumentoSunat", 	intParaTipoDocumentoSunat);
			lista = dao.getListaPorDocumentoSunatYTipoDocSunat(mapa);
//			if(lista!=null){
//				if(lista.size()==1){
//				   domain = lista.get(0);
//				}else if(lista.size()==0){
//				   domain = null;
//				}else{
//				   throw new BusinessException("Obtención de mas de un registro coincidente");
//				}
//			}
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
//		return domain;
		return lista;
	}
	//Fin agregado por cdelosrios, 01/11/2013
}
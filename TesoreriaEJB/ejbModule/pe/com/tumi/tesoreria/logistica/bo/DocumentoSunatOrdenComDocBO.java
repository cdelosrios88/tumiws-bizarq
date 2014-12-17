package pe.com.tumi.tesoreria.logistica.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.tesoreria.logistica.dao.DocumentoSunatOrdenComDocDao;
import pe.com.tumi.tesoreria.logistica.dao.impl.DocumentoSunatOrdenComDocDaoIbatis;
import pe.com.tumi.tesoreria.logistica.domain.DocumentoSunatOrdenComDoc;
import pe.com.tumi.tesoreria.logistica.domain.DocumentoSunatOrdenComDocId;
import pe.com.tumi.tesoreria.logistica.domain.OrdenCompraDocumento;

public class DocumentoSunatOrdenComDocBO {
	private DocumentoSunatOrdenComDocDao dao = (DocumentoSunatOrdenComDocDao)TumiFactory.get(DocumentoSunatOrdenComDocDaoIbatis.class);

	public DocumentoSunatOrdenComDoc grabar(DocumentoSunatOrdenComDoc o) throws BusinessException{
		DocumentoSunatOrdenComDoc dto = null;
		try{
		    dto = dao.grabar(o);
		}catch(DAOException e){
	  		throw new BusinessException(e);
	    }catch(Exception e){
	    	throw new BusinessException(e);
	    }
	    return dto;
	}

  	public DocumentoSunatOrdenComDoc modificar(DocumentoSunatOrdenComDoc o) throws BusinessException{
  		DocumentoSunatOrdenComDoc dto = null;
     	try{
		     dto = dao.modificar(o);
	  	}catch(DAOException e){
	  		 throw new BusinessException(e);
	    }catch(Exception e){
	    	 throw new BusinessException(e);
	    }
	    return dto;
    }
  
	public DocumentoSunatOrdenComDoc getPorPk(DocumentoSunatOrdenComDocId pId) throws BusinessException{
		DocumentoSunatOrdenComDoc domain = null;
		List<DocumentoSunatOrdenComDoc> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaDocSunatOrden", 	pId.getIntPersEmpresaDocSunatOrden());
			mapa.put("intItemEmpresaDocSunatOrden", 	pId.getIntItemEmpresaDocSunatOrden());
			lista = dao.getListaPorPk(mapa);
			if(lista!=null){
				if(lista.size()==1){
				   domain = lista.get(0);
				}else if(lista.size()==0){
				   domain = null;
				}else{
				   throw new BusinessException("Obtenci�n de mas de un registro coincidente");
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
	//Autor: jchavez / Tarea: Creacion / Fecha: 26.10.2014
	public List<DocumentoSunatOrdenComDoc> getListaPorOrdenCompraDoc(OrdenCompraDocumento ordenCompraDoc) throws BusinessException{
		List<DocumentoSunatOrdenComDoc> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaOrden", ordenCompraDoc.getId().getIntPersEmpresa());
			mapa.put("intItemOrdenCompra", ordenCompraDoc.getId().getIntItemOrdenCompra());
			mapa.put("intItemOrdenCompraDoc", ordenCompraDoc.getId().getIntItemOrdenCompraDocumento());
			
			lista = dao.getListaPorOrdenCompraDoc(mapa);			
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
}
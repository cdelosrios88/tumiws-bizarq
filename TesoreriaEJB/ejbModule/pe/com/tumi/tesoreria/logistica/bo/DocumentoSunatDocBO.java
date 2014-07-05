package pe.com.tumi.tesoreria.logistica.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.tesoreria.logistica.dao.DocumentoSunatDocDao;
import pe.com.tumi.tesoreria.logistica.dao.impl.DocumentoSunatDocDaoIbatis;
import pe.com.tumi.tesoreria.logistica.domain.DocumentoSunatDoc;
import pe.com.tumi.tesoreria.logistica.domain.DocumentoSunatDocId;

public class DocumentoSunatDocBO{

	private DocumentoSunatDocDao dao = (DocumentoSunatDocDao)TumiFactory.get(DocumentoSunatDocDaoIbatis.class);

	public DocumentoSunatDoc grabar(DocumentoSunatDoc o) throws BusinessException{
		DocumentoSunatDoc dto = null;
		try{
		    dto = dao.grabar(o);
		}catch(DAOException e){
	  		throw new BusinessException(e);
	    }catch(Exception e){
	    	throw new BusinessException(e);
	    }
	    return dto;
	}

  	public DocumentoSunatDoc modificar(DocumentoSunatDoc o) throws BusinessException{
  		DocumentoSunatDoc dto = null;
     	try{
		     dto = dao.modificar(o);
	  	}catch(DAOException e){
	  		 throw new BusinessException(e);
	    }catch(Exception e){
	    	 throw new BusinessException(e);
	    }
	    return dto;
    }
  
	public DocumentoSunatDoc getPorPk(DocumentoSunatDocId pId) throws BusinessException{
		DocumentoSunatDoc domain = null;
		List<DocumentoSunatDoc> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresa", 			pId.getIntPersEmpresa());
			mapa.put("intItemDocumentoSunat", 	pId.getIntItemDocumentoSunat());
			mapa.put("intItemDocumentoSunatDoc",pId.getIntItemDocumentoSunatDoc());
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
	
	public List<DocumentoSunatDoc> getListaPorDocumentoSunat(DocumentoSunatDoc o) throws BusinessException{
		List<DocumentoSunatDoc> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresa", 			o.getId().getIntPersEmpresa());
			mapa.put("intItemDocumentoSunat", 	o.getId().getIntItemDocumentoSunat());
			lista = dao.getListaPorDocumentoSunat(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public DocumentoSunatDoc getPorDocumentoSunatYTipoDoc(DocumentoSunatDoc o) throws BusinessException{
		DocumentoSunatDoc domain = null;
		List<DocumentoSunatDoc> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresa", 				o.getId().getIntPersEmpresa());
			mapa.put("intItemDocumentoSunat", 		o.getId().getIntItemDocumentoSunat());
			mapa.put("intParaTipoDocumentoGeneral",	o.getIntParaTipoDocumentoGeneral());
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
}
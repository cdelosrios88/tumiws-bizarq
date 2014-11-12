package pe.com.tumi.tesoreria.logistica.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.tesoreria.logistica.dao.DocumentoSunatLetraDao;
import pe.com.tumi.tesoreria.logistica.dao.impl.DocumentoSunatLetraDaoIbatis;
import pe.com.tumi.tesoreria.logistica.domain.DocumentoSunat;
import pe.com.tumi.tesoreria.logistica.domain.DocumentoSunatLetra;
import pe.com.tumi.tesoreria.logistica.domain.DocumentoSunatLetraId;

public class DocumentoSunatLetraBO {
	private DocumentoSunatLetraDao dao = (DocumentoSunatLetraDao)TumiFactory.get(DocumentoSunatLetraDaoIbatis.class);

	public DocumentoSunatLetra grabar(DocumentoSunatLetra o) throws BusinessException{
		DocumentoSunatLetra dto = null;
		try{
		    dto = dao.grabar(o);
		}catch(DAOException e){
	  		throw new BusinessException(e);
	    }catch(Exception e){
	    	throw new BusinessException(e);
	    }
	    return dto;
	}

  	public DocumentoSunatLetra modificar(DocumentoSunatLetra o) throws BusinessException{
  		DocumentoSunatLetra dto = null;
     	try{
		     dto = dao.modificar(o);
	  	}catch(DAOException e){
	  		 throw new BusinessException(e);
	    }catch(Exception e){
	    	 throw new BusinessException(e);
	    }
	    return dto;
    }
  
	public DocumentoSunatLetra getPorPk(DocumentoSunatLetraId pId) throws BusinessException{
		DocumentoSunatLetra domain = null;
		List<DocumentoSunatLetra> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaDocSunat", 		pId.getIntPersEmpresaDocSunat());
			mapa.put("intItemDocumentoSunat", 		pId.getIntItemDocumentoSunat());
			mapa.put("intItemDocumentoSunatLetra",	pId.getIntItemDocumentoSunatLetra());
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
	
	public List<DocumentoSunatLetra> getListaPorDocumentoSunat(DocumentoSunat o) throws BusinessException{
		List<DocumentoSunatLetra> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaDocSunat", 	o.getId().getIntPersEmpresa());
			mapa.put("intItemDocumentoSunat", 	o.getId().getIntItemDocumentoSunat());
			lista = dao.getListaPorDocumentoSunat(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
}

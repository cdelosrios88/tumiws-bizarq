package pe.com.tumi.tesoreria.logistica.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.tesoreria.logistica.dao.DocumentoSunatEgresoDao;
import pe.com.tumi.tesoreria.logistica.dao.impl.DocumentoSunatEgresoDaoIbatis;
import pe.com.tumi.tesoreria.logistica.domain.DocumentoSunatEgreso;
import pe.com.tumi.tesoreria.logistica.domain.DocumentoSunatEgresoId;


public class DocumentoSunatEgresoBO{

	private DocumentoSunatEgresoDao dao = (DocumentoSunatEgresoDao)TumiFactory.get(DocumentoSunatEgresoDaoIbatis.class);

	public DocumentoSunatEgreso grabar(DocumentoSunatEgreso o) throws BusinessException{
		DocumentoSunatEgreso dto = null;
		try{
		    dto = dao.grabar(o);
		}catch(DAOException e){
	  		throw new BusinessException(e);
	    }catch(Exception e){
	    	throw new BusinessException(e);
	    }
	    return dto;
	}

  	public DocumentoSunatEgreso modificar(DocumentoSunatEgreso o) throws BusinessException{
  		DocumentoSunatEgreso dto = null;
     	try{
		     dto = dao.modificar(o);
	  	}catch(DAOException e){
	  		 throw new BusinessException(e);
	    }catch(Exception e){
	    	 throw new BusinessException(e);
	    }
	    return dto;
    }
  
	public DocumentoSunatEgreso getPorPk(DocumentoSunatEgresoId pId) throws BusinessException{
		DocumentoSunatEgreso domain = null;
		List<DocumentoSunatEgreso> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresa", pId.getIntPersEmpresa());
			mapa.put("intItemDocumentoSunat", pId.getIntItemDocumentoSunat());
			mapa.put("intItemDocumentoSunatEgreso", pId.getIntItemDocumentoSunatEgreso());
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
	
	public List<DocumentoSunatEgreso> getPorBuscar(DocumentoSunatEgreso documentoSunatEgreso) throws BusinessException{
		List<DocumentoSunatEgreso> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresa", documentoSunatEgreso.getId().getIntPersEmpresa());
			mapa.put("intItemDocumentoSunat", documentoSunatEgreso.getId().getIntItemDocumentoSunat());
			lista = dao.getListaPorDocumentoSunat(mapa);
			
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}	
}
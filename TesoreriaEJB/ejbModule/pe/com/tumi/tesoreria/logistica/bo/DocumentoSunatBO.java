package pe.com.tumi.tesoreria.logistica.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.tesoreria.logistica.dao.DocumentoSunatDao;
import pe.com.tumi.tesoreria.logistica.dao.impl.DocumentoSunatDaoIbatis;
import pe.com.tumi.tesoreria.logistica.domain.DocumentoSunat;
import pe.com.tumi.tesoreria.logistica.domain.DocumentoSunatId;
import pe.com.tumi.tesoreria.logistica.domain.OrdenCompra;


public class DocumentoSunatBO{

	private DocumentoSunatDao dao = (DocumentoSunatDao)TumiFactory.get(DocumentoSunatDaoIbatis.class);

	public DocumentoSunat grabar(DocumentoSunat o) throws BusinessException{
		DocumentoSunat dto = null;
		try{
		    dto = dao.grabar(o);
		}catch(DAOException e){
	  		throw new BusinessException(e);
	    }catch(Exception e){
	    	throw new BusinessException(e);
	    }
	    return dto;
	}

  	public DocumentoSunat modificar(DocumentoSunat o) throws BusinessException{
  		DocumentoSunat dto = null;
     	try{
		     dto = dao.modificar(o);
	  	}catch(DAOException e){
	  		 throw new BusinessException(e);
	    }catch(Exception e){
	    	 throw new BusinessException(e);
	    }
	    return dto;
    }
  
	public DocumentoSunat getPorPk(DocumentoSunatId pId) throws BusinessException{
		DocumentoSunat domain = null;
		List<DocumentoSunat> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresa", pId.getIntPersEmpresa());
			mapa.put("intItemDocumentoSunat", pId.getIntItemDocumentoSunat());
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
	
	public List<DocumentoSunat> getPorBuscar(DocumentoSunat documentoSunat) throws BusinessException{
		List<DocumentoSunat> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresa", documentoSunat.getId().getIntPersEmpresa());
			mapa.put("intItemDocumentoSunat", documentoSunat.getId().getIntItemDocumentoSunat());
			mapa.put("intParaEstadoPago", documentoSunat.getIntParaEstadoPago());
			mapa.put("dtFiltroEmisionDesde", documentoSunat.getDtFiltroEmisionDesde());
			mapa.put("dtFiltroEmisionHasta", documentoSunat.getDtFiltroEmisionHasta());
			mapa.put("dtFiltroVencimientoDesde", documentoSunat.getDtFiltroVencimientoDesde());
			mapa.put("dtFiltroVencimientoHasta", documentoSunat.getDtFiltroVencimientoHasta());
			mapa.put("dtFiltroProgramacionDesde", documentoSunat.getDtFiltroProgramacionDesde());
			mapa.put("dtFiltroProgramacionHasta", documentoSunat.getDtFiltroProgramacionHasta());
			mapa.put("intEmpresaLibro", documentoSunat.getIntEmpresaLibro());
			mapa.put("intPeriodoLibro", documentoSunat.getIntPeriodoLibro());
			mapa.put("intCodigoLibro", documentoSunat.getIntCodigoLibro());
			mapa.put("intParaEstado", documentoSunat.getIntParaEstado());
			//Agregado por cdelosrios, 10/10/2013
			mapa.put("intParaTipoComprobante", documentoSunat.getIntParaTipoComprobante());
			mapa.put("intItemDocumentoSunat", documentoSunat.getId().getIntItemDocumentoSunat());
			mapa.put("intNroDocumento", documentoSunat.getStrNumeroDocumento());
			//Fin agregado por cdelosrios, 10/10/2013
			
			lista = dao.getListaPorBuscar(mapa);
			
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<DocumentoSunat> getPorOrdenCompra(OrdenCompra ordenCompra) throws BusinessException{
		List<DocumentoSunat> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresa", ordenCompra.getId().getIntPersEmpresa());
			mapa.put("intPersEmpresaOrden", ordenCompra.getId().getIntPersEmpresa());
			mapa.put("intItemOrdenCompra", ordenCompra.getId().getIntItemOrdenCompra());
			mapa.put("intParaEstado", ordenCompra.getIntParaEstado());
			
			lista = dao.getListaPorOrdenCompra(mapa);			
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<DocumentoSunat> getListaEnlazados(DocumentoSunat documentoSunat) throws BusinessException{
		List<DocumentoSunat> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaDocSunatEnlazado", documentoSunat.getId().getIntPersEmpresa());
			mapa.put("intItemDocumentoSunatEnlazado", documentoSunat.getId().getIntItemDocumentoSunat());	
			
			lista = dao.getListaEnlazados(mapa);			
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	//Agregado por cdelosrios, 18/11/2013
	public List<DocumentoSunat> getListaPorOrdenCompraYTipoDocumento(DocumentoSunat documentoSunat) throws BusinessException{
		List<DocumentoSunat> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaOrden", documentoSunat.getOrdenCompra().getId().getIntPersEmpresa());
			mapa.put("intItemOrdenCompra", documentoSunat.getOrdenCompra().getId().getIntItemOrdenCompra());
			mapa.put("intParaTipoComprobante", documentoSunat.getIntParaTipoComprobante());
			
			lista = dao.getListaPorOrdenCompraYTipoDocumento(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	//Fin agregado por cdelosrios, 18/11/2013
}
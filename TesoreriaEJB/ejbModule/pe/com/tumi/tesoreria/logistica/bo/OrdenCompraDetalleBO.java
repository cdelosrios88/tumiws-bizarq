package pe.com.tumi.tesoreria.logistica.bo;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.tesoreria.logistica.dao.OrdenCompraDetalleDao;
import pe.com.tumi.tesoreria.logistica.dao.impl.OrdenCompraDetalleDaoIbatis;
import pe.com.tumi.tesoreria.logistica.domain.DocumentoSunatDetalle;
import pe.com.tumi.tesoreria.logistica.domain.OrdenCompra;
import pe.com.tumi.tesoreria.logistica.domain.OrdenCompraDetalle;
import pe.com.tumi.tesoreria.logistica.domain.OrdenCompraDetalleId;

public class OrdenCompraDetalleBO{

	private OrdenCompraDetalleDao dao = (OrdenCompraDetalleDao)TumiFactory.get(OrdenCompraDetalleDaoIbatis.class);
	protected static Logger log = Logger.getLogger(OrdenCompraDetalleBO.class);
	
	public OrdenCompraDetalle grabar(OrdenCompraDetalle o) throws BusinessException{
		OrdenCompraDetalle dto = null;
		try{
		    dto = dao.grabar(o);
		}catch(DAOException e){
	  		throw new BusinessException(e);
	    }catch(Exception e){
	    	throw new BusinessException(e);
	    }
	    return dto;
	}

  	public OrdenCompraDetalle modificar(OrdenCompraDetalle o) throws BusinessException{
  		OrdenCompraDetalle dto = null;
     	try{
		     dto = dao.modificar(o);
	  	}catch(DAOException e){
	  		 throw new BusinessException(e);
	    }catch(Exception e){
	    	 throw new BusinessException(e);
	    }
	    return dto;
    }
  
	public OrdenCompraDetalle getPorPk(OrdenCompraDetalleId pId) throws BusinessException{
		OrdenCompraDetalle domain = null;
		List<OrdenCompraDetalle> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresa", pId.getIntPersEmpresa());
			mapa.put("intItemOrdenCompra", pId.getIntItemOrdenCompra());
			mapa.put("intItemOrdenCompraDetalle", pId.getIntItemOrdenCompraDetalle());
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
	
	public List<OrdenCompraDetalle> getPorOrdenCompra(OrdenCompra ordenCompra) throws BusinessException{
		List<OrdenCompraDetalle> lista = null;
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
	
	public OrdenCompraDetalle getPorDocumentoSunatDetalle(DocumentoSunatDetalle documentoSunatDetalle) throws BusinessException{
		OrdenCompraDetalle domain = null;
		try{
			OrdenCompraDetalleId ordenCompraDetalleId = new OrdenCompraDetalleId();
			ordenCompraDetalleId.setIntPersEmpresa(documentoSunatDetalle.getIntPersEmpresaOrdenCompra());
			ordenCompraDetalleId.setIntItemOrdenCompra(documentoSunatDetalle.getIntItemOrdenCompra());
			ordenCompraDetalleId.setIntItemOrdenCompraDetalle(documentoSunatDetalle.getIntItemOrdenCompraDetalle());
			log.info(ordenCompraDetalleId);
			domain = getPorPk(ordenCompraDetalleId);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return domain;
	}
	
	//Autor: jchavez / Tarea: Creación / Fecha: 02.10.2014
	public OrdenCompraDetalle getSumPrecioTotalXCta(OrdenCompraDetalle ordenCompraDetalle) throws BusinessException{
		List<OrdenCompraDetalle> lista = null;
		OrdenCompraDetalle domain = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaCuenta", ordenCompraDetalle.getIntPersEmpresaCuenta());
			mapa.put("intContPeriodoCuenta", ordenCompraDetalle.getIntContPeriodoCuenta());
			mapa.put("strContNumeroCuenta", ordenCompraDetalle.getStrContNumeroCuenta());
			mapa.put("intSucuIdSucursal", ordenCompraDetalle.getIntSucuIdSucursal());
			mapa.put("intSudeIdSubsucursal", ordenCompraDetalle.getIntSudeIdSubsucursal());
			mapa.put("intIdPerfil", ordenCompraDetalle.getIntIdPerfil());
			lista = dao.getSumPrecioTotalXCta(mapa);	

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
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return domain;
	}
}
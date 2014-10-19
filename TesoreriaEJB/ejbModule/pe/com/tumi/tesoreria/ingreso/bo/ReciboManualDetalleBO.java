package pe.com.tumi.tesoreria.ingreso.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.tesoreria.ingreso.dao.ReciboManualDetalleDao;
import pe.com.tumi.tesoreria.ingreso.dao.impl.ReciboManualDetalleDaoIbatis;
import pe.com.tumi.tesoreria.ingreso.domain.Ingreso;
import pe.com.tumi.tesoreria.ingreso.domain.ReciboManual;
import pe.com.tumi.tesoreria.ingreso.domain.ReciboManualDetalle;
import pe.com.tumi.tesoreria.ingreso.domain.ReciboManualDetalleId;


public class ReciboManualDetalleBO{

	private ReciboManualDetalleDao dao = (ReciboManualDetalleDao)TumiFactory.get(ReciboManualDetalleDaoIbatis.class);

	public ReciboManualDetalle grabar(ReciboManualDetalle o) throws BusinessException{
		ReciboManualDetalle dto = null;
		try{
		    dto = dao.grabar(o);
		}catch(DAOException e){
	  		throw new BusinessException(e);
	    }catch(Exception e){
	    	throw new BusinessException(e);
	    }
	    return dto;
	}

  	public ReciboManualDetalle modificar(ReciboManualDetalle o) throws BusinessException{
  		ReciboManualDetalle dto = null;
     	try{
		     dto = dao.modificar(o);
	  	}catch(DAOException e){
	  		 throw new BusinessException(e);
	    }catch(Exception e){
	    	 throw new BusinessException(e);
	    }
	    return dto;
    }
  
	public ReciboManualDetalle getPorId(ReciboManualDetalleId reciboManualId) throws BusinessException{
		ReciboManualDetalle domain = null;
		List<ReciboManualDetalle> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresa", reciboManualId.getIntPersEmpresa());
			mapa.put("intItemReciboManual", reciboManualId.getIntItemReciboManual());
			mapa.put("intItemReciboManualDetalle", reciboManualId.getIntItemReciboManualDetalle());
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
	
	public List<ReciboManualDetalle> getPorReciboManual(ReciboManual reciboManual) throws BusinessException{
		List<ReciboManualDetalle> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresa", reciboManual.getId().getIntPersEmpresa());
			mapa.put("intItemReciboManual", reciboManual.getId().getIntItemReciboManual());
			lista = dao.getListaPorReciboManual(mapa);				
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public ReciboManualDetalle getPorIngreso(Ingreso ingreso) throws BusinessException{
		ReciboManualDetalle domain = null;
		List<ReciboManualDetalle> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaIngreso", ingreso.getId().getIntIdEmpresa());
			mapa.put("intItemIngresoGeneral", ingreso.getId().getIntIdIngresoGeneral());
			lista = dao.getListaPorIngreso(mapa);
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
	
	public String existeNroReciboEnlazado(Integer idEmpresa, Integer idSucursal, Integer idSubSuc, Integer nroSerie, Integer nroRecibo) throws BusinessException{
		String vResult = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaIngreso", idEmpresa);
			mapa.put("intSucuIdSucursal", idSucursal);
			mapa.put("intSudeIdSubsucursal", idSubSuc);
			mapa.put("intNroSerie", nroSerie);
			mapa.put("intNroRecibo", nroRecibo);
			vResult = dao.existeNroReciboEnlazado(mapa);				
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return vResult;
	}
	
	public List<ReciboManualDetalle> getListaPorFiltros(Integer idEmpresa,Integer idSucursal,Integer idSubSuc,Integer idEstadoRecibo,Integer nroSerie,Integer nroRecibo) throws BusinessException{
		List<ReciboManualDetalle> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaIngreso", idEmpresa);
			mapa.put("intSucuIdSucursal", idSucursal);
			mapa.put("intSudeIdSubsucursal", idSubSuc);
			mapa.put("intEstadoRecibo", idEstadoRecibo);
			mapa.put("intNroSerie", nroSerie);
			mapa.put("intNroRecibo", nroRecibo);
			lista = dao.getListaPorFiltros(mapa);				
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
}
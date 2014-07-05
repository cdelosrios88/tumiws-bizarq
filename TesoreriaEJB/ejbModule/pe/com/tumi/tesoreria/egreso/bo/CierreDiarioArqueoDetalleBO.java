package pe.com.tumi.tesoreria.egreso.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.tesoreria.egreso.dao.CierreDiarioArqueoDetalleDao;
import pe.com.tumi.tesoreria.egreso.dao.impl.CierreDiarioArqueoDetalleDaoIbatis;
import pe.com.tumi.tesoreria.egreso.domain.CierreDiarioArqueo;
import pe.com.tumi.tesoreria.egreso.domain.CierreDiarioArqueoDetalle;
import pe.com.tumi.tesoreria.egreso.domain.CierreDiarioArqueoDetalleId;


public class CierreDiarioArqueoDetalleBO{

	private CierreDiarioArqueoDetalleDao dao = (CierreDiarioArqueoDetalleDao)TumiFactory.get(CierreDiarioArqueoDetalleDaoIbatis.class);

	public CierreDiarioArqueoDetalle grabar(CierreDiarioArqueoDetalle o) throws BusinessException{
		CierreDiarioArqueoDetalle dto = null;
		try{
		    dto = dao.grabar(o);
		}catch(DAOException e){
	  		throw new BusinessException(e);
	    }catch(Exception e){
	    	throw new BusinessException(e);
	    }
	    return dto;
	}

  	public CierreDiarioArqueoDetalle modificar(CierreDiarioArqueoDetalle o) throws BusinessException{
  		CierreDiarioArqueoDetalle dto = null;
     	try{
		     dto = dao.modificar(o);
	  	}catch(DAOException e){
	  		 throw new BusinessException(e);
	    }catch(Exception e){
	    	 throw new BusinessException(e);
	    }
	    return dto;
    }
  
	public CierreDiarioArqueoDetalle getPorPk(CierreDiarioArqueoDetalleId cierreDiarioArqueoDetalleId) throws BusinessException{
		CierreDiarioArqueoDetalle domain = null;
		List<CierreDiarioArqueoDetalle> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresa", cierreDiarioArqueoDetalleId.getIntPersEmpresa());
			mapa.put("intParaTipoArqueo", cierreDiarioArqueoDetalleId.getIntParaTipoArqueo());
			mapa.put("intSucuIdSucursal", cierreDiarioArqueoDetalleId.getIntSucuIdSucursal());
			mapa.put("intSudeIdSubsucursal", cierreDiarioArqueoDetalleId.getIntSudeIdSubsucursal());
			mapa.put("intItemCierreDiarioArqueo", cierreDiarioArqueoDetalleId.getIntItemCierreDiarioArqueo());
			mapa.put("intItemDetalle", cierreDiarioArqueoDetalleId.getIntItemDetalle());
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

	public List<CierreDiarioArqueoDetalle> getListaPorCierreDiarioArqueo(CierreDiarioArqueo cierreDiarioArqueo) throws BusinessException{
		List<CierreDiarioArqueoDetalle> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresa", cierreDiarioArqueo.getId().getIntPersEmpresa());
			mapa.put("intParaTipoArqueo", cierreDiarioArqueo.getId().getIntParaTipoArqueo());
			mapa.put("intSucuIdSucursal", cierreDiarioArqueo.getId().getIntSucuIdSucursal());
			mapa.put("intSudeIdSubsucursal", cierreDiarioArqueo.getId().getIntSudeIdSubsucursal());
			mapa.put("intItemCierreDiarioArqueo", cierreDiarioArqueo.getId().getIntItemCierreDiarioArqueo());
			lista = dao.getListaPorCierreDiarioArqueo(mapa);	
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
}
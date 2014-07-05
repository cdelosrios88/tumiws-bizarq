package pe.com.tumi.tesoreria.egreso.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.tesoreria.egreso.dao.CierreDiarioArqueoBilleteDao;
import pe.com.tumi.tesoreria.egreso.dao.impl.CierreDiarioArqueoBilleteDaoIbatis;
import pe.com.tumi.tesoreria.egreso.domain.CierreDiarioArqueo;
import pe.com.tumi.tesoreria.egreso.domain.CierreDiarioArqueoBillete;
import pe.com.tumi.tesoreria.egreso.domain.CierreDiarioArqueoBilleteId;


public class CierreDiarioArqueoBilleteBO{

	private CierreDiarioArqueoBilleteDao dao = (CierreDiarioArqueoBilleteDao)TumiFactory.get(CierreDiarioArqueoBilleteDaoIbatis.class);

	public CierreDiarioArqueoBillete grabar(CierreDiarioArqueoBillete o) throws BusinessException{
		CierreDiarioArqueoBillete dto = null;
		try{
		    dto = dao.grabar(o);
		}catch(DAOException e){
	  		throw new BusinessException(e);
	    }catch(Exception e){
	    	throw new BusinessException(e);
	    }
	    return dto;
	}

  	public CierreDiarioArqueoBillete modificar(CierreDiarioArqueoBillete o) throws BusinessException{
  		CierreDiarioArqueoBillete dto = null;
     	try{
		     dto = dao.modificar(o);
	  	}catch(DAOException e){
	  		 throw new BusinessException(e);
	    }catch(Exception e){
	    	 throw new BusinessException(e);
	    }
	    return dto;
    }
  
	public CierreDiarioArqueoBillete getPorPk(CierreDiarioArqueoBilleteId cierreDiarioArqueoBilleteId) throws BusinessException{
		CierreDiarioArqueoBillete domain = null;
		List<CierreDiarioArqueoBillete> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresa", cierreDiarioArqueoBilleteId.getIntPersEmpresa());
			mapa.put("intParaTipoArqueo", cierreDiarioArqueoBilleteId.getIntParaTipoArqueo());
			mapa.put("intSucuIdSucursal", cierreDiarioArqueoBilleteId.getIntSucuIdSucursal());
			mapa.put("intSudeIdSubsucursal", cierreDiarioArqueoBilleteId.getIntSudeIdSubsucursal());
			mapa.put("intItemCierreDiarioArqueo", cierreDiarioArqueoBilleteId.getIntItemCierreDiarioArqueo());
			mapa.put("intParaTipoMonedaBillete", cierreDiarioArqueoBilleteId.getIntParaTipoMonedaBillete());
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
	
	public List<CierreDiarioArqueoBillete> getListaPorCierreDiarioArqueo(CierreDiarioArqueo cierreDiarioArqueo) throws BusinessException{
		List<CierreDiarioArqueoBillete> lista = null;
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
package pe.com.tumi.contabilidad.cierre.bo;

import java.util.HashMap;
import java.util.List;


import pe.com.tumi.contabilidad.cierre.dao.CuentaCierreDetalleDao;
import pe.com.tumi.contabilidad.cierre.dao.impl.CuentaCierreDetalleDaoIbatis;
import pe.com.tumi.contabilidad.cierre.domain.CuentaCierre;
import pe.com.tumi.contabilidad.cierre.domain.CuentaCierreDetalle;
import pe.com.tumi.contabilidad.cierre.domain.CuentaCierreDetalleId;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;

public class CuentaCierreDetalleBO {
	private CuentaCierreDetalleDao dao = (CuentaCierreDetalleDao)TumiFactory.get(CuentaCierreDetalleDaoIbatis.class);
	
	public CuentaCierreDetalle grabar(CuentaCierreDetalle o) throws BusinessException{
		CuentaCierreDetalle dto = null;
		try{
		    dto = dao.grabar(o);
		}catch(DAOException e){
	  		throw new BusinessException(e);
	    }catch(Exception e){
	    	throw new BusinessException(e);
	    }
	    return dto;
	}
	
  	public CuentaCierreDetalle modificar(CuentaCierreDetalle o) throws BusinessException{
  		CuentaCierreDetalle dto = null;
     	try{
		     dto = dao.modificar(o);
	  	}catch(DAOException e){
	  		 throw new BusinessException(e);
	    }catch(Exception e){
	    	 throw new BusinessException(e);
	    }
	    return dto;
    }
  
	public CuentaCierreDetalle getPorPk(CuentaCierreDetalleId pId) throws BusinessException{
		CuentaCierreDetalle domain = null;
		List<CuentaCierreDetalle> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaCierre",pId.getIntPersEmpresaCierre());
			mapa.put("intContPeriodoCierre",pId.getIntContPeriodoCierre());
			mapa.put("intParaTipoCierre", 	pId.getIntParaTipoCierre());
			mapa.put("intContCodigoCierre", pId.getIntContCodigoCierre());
			mapa.put("intItemCierre", 		pId.getIntItemCierre());
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
	
	public List<CuentaCierreDetalle> getPorCuentaCierre(CuentaCierre o) throws BusinessException{
		List<CuentaCierreDetalle> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaCierre",o.getId().getIntPersEmpresaCierre());
			mapa.put("intContPeriodoCierre",o.getId().getIntContPeriodoCierre());
			mapa.put("intParaTipoCierre", 	o.getId().getIntParaTipoCierre());
			mapa.put("intContCodigoCierre", o.getId().getIntContCodigoCierre());
			lista = dao.getListaPorCuentaCierre(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public boolean eliminar(CuentaCierreDetalle o) throws BusinessException{
		boolean exito = Boolean.FALSE;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaCierre",o.getId().getIntPersEmpresaCierre());
			mapa.put("intContPeriodoCierre",o.getId().getIntContPeriodoCierre());
			mapa.put("intParaTipoCierre", 	o.getId().getIntParaTipoCierre());
			mapa.put("intContCodigoCierre", o.getId().getIntContCodigoCierre());
			mapa.put("intItemCierre", 		o.getId().getIntItemCierre());
			dao.eliminar(mapa);
			exito = Boolean.TRUE;
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return exito;
	}	
}

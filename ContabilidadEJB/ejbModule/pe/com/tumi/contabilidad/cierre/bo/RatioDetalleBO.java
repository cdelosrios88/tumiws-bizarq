package pe.com.tumi.contabilidad.cierre.bo;

import java.util.HashMap;
import java.util.List;


import pe.com.tumi.contabilidad.cierre.dao.RatioDetalleDao;
import pe.com.tumi.contabilidad.cierre.dao.impl.RatioDetalleDaoIbatis;
import pe.com.tumi.contabilidad.cierre.domain.AnexoDetalle;
import pe.com.tumi.contabilidad.cierre.domain.Ratio;
import pe.com.tumi.contabilidad.cierre.domain.RatioDetalle;
import pe.com.tumi.contabilidad.cierre.domain.RatioDetalleId;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;

public class RatioDetalleBO {
	private RatioDetalleDao dao = (RatioDetalleDao)TumiFactory.get(RatioDetalleDaoIbatis.class);

	public RatioDetalle grabar(RatioDetalle o) throws BusinessException{
		RatioDetalle dto = null;
		try{
		    dto = dao.grabar(o);
		}catch(DAOException e){
	  		throw new BusinessException(e);
	    }catch(Exception e){
	    	throw new BusinessException(e);
	    }
	    return dto;
	}
	
  	public RatioDetalle modificar(RatioDetalle o) throws BusinessException{
  		RatioDetalle dto = null;
     	try{
		     dto = dao.modificar(o);
	  	}catch(DAOException e){
	  		 throw new BusinessException(e);
	    }catch(Exception e){
	    	 throw new BusinessException(e);
	    }
	    return dto;
    }
  
	public RatioDetalle getPorPk(RatioDetalleId pId) throws BusinessException{
		RatioDetalle domain = null;
		List<RatioDetalle> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaRatio",pId.getIntPersEmpresaRatio());
			mapa.put("intContPeriodoRatio",pId.getIntContPeriodoRatio());
			mapa.put("intCodigoRatio", 	pId.getIntCodigoRatio());
			mapa.put("intItemRatio", 	pId.getIntItemRatio());
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
	
	public List<RatioDetalle> getPorRatio(Ratio o) throws BusinessException{
		List<RatioDetalle> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaRatio",o.getId().getIntPersEmpresaRatio());
			mapa.put("intContPeriodoRatio",o.getId().getIntContPeriodoRatio());
			mapa.put("intCodigoRatio", 	o.getId().getIntCodigoRatio());
			lista = dao.getListaPorRatio(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public void eliminar(RatioDetalle o) throws BusinessException{
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaRatio",	o.getId().getIntPersEmpresaRatio());
			mapa.put("intContPeriodoRatio",	o.getId().getIntContPeriodoRatio());
			mapa.put("intCodigoRatio", 		o.getId().getIntCodigoRatio());
			mapa.put("intItemRatio", 		o.getId().getIntItemRatio());
			dao.eliminar(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
	}
	
	public List<RatioDetalle> getPorAnexoDetalle(AnexoDetalle o) throws BusinessException{
		List<RatioDetalle> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaAnexo",	o.getId().getIntPersEmpresaAnexo());
			mapa.put("intContPeriodoAnexo",	o.getId().getIntContPeriodoAnexo());
			mapa.put("intParaTipoAnexo", 	o.getId().getIntParaTipoAnexo());
			mapa.put("intItemAnexoDetalle", o.getId().getIntItemAnexoDetalle());
			lista = dao.getListaPorAnexoDetalle(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
}
package pe.com.tumi.contabilidad.cierre.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.contabilidad.cierre.dao.RatioDao;
import pe.com.tumi.contabilidad.cierre.dao.impl.RatioDaoIbatis;
import pe.com.tumi.contabilidad.cierre.domain.Ratio;
import pe.com.tumi.contabilidad.cierre.domain.RatioId;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;

public class RatioBO {
	private RatioDao dao = (RatioDao)TumiFactory.get(RatioDaoIbatis.class);

	public Ratio grabar(Ratio o) throws BusinessException{
		Ratio dto = null;
		try{
		    dto = dao.grabar(o);
		}catch(DAOException e){
	  		throw new BusinessException(e);
	    }catch(Exception e){
	    	throw new BusinessException(e);
	    }
	    return dto;
	}
	
  	public Ratio modificar(Ratio o) throws BusinessException{
  		Ratio dto = null;
     	try{
		     dto = dao.modificar(o);
	  	}catch(DAOException e){
	  		 throw new BusinessException(e);
	    }catch(Exception e){
	    	 throw new BusinessException(e);
	    }
	    return dto;
    }
  
	public Ratio getPorPk(RatioId pId) throws BusinessException{
		Ratio domain = null;
		List<Ratio> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaRatio",pId.getIntPersEmpresaRatio());
			mapa.put("intContPeriodoRatio",pId.getIntContPeriodoRatio());
			mapa.put("intCodigoRatio", 	pId.getIntCodigoRatio());
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
	
	public List<Ratio> buscar(Ratio o) throws BusinessException{
		List<Ratio> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaRatio",	o.getId().getIntPersEmpresaRatio());
			mapa.put("intContPeriodoRatio",	o.getId().getIntContPeriodoRatio());
			mapa.put("intCodigoRatio", 		o.getId().getIntCodigoRatio());
			mapa.put("intParaTipoRatio", 	o.getIntParaTipoRatio());
			lista = dao.getListaPorBusqueda(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
}
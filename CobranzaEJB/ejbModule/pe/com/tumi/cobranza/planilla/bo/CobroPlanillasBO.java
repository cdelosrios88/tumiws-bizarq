package pe.com.tumi.cobranza.planilla.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;

import pe.com.tumi.cobranza.planilla.dao.CobroPlanillasDao;
import pe.com.tumi.cobranza.planilla.dao.impl.CobroPlanillasDaoIbatis;
import pe.com.tumi.cobranza.planilla.domain.CobroPlanillas;
import pe.com.tumi.cobranza.planilla.domain.CobroPlanillasId;
import pe.com.tumi.cobranza.planilla.domain.EfectuadoResumen;

public class CobroPlanillasBO{

	private CobroPlanillasDao dao = (CobroPlanillasDao)TumiFactory.get(CobroPlanillasDaoIbatis.class);

	public CobroPlanillas grabar(CobroPlanillas o) throws BusinessException{
		CobroPlanillas dto = null;
		try{
		    dto = dao.grabar(o);
		}catch(DAOException e){
	  		throw new BusinessException(e);
	    }catch(Exception e){
	    	throw new BusinessException(e);
	    }
	    return dto;
	}

  	public CobroPlanillas modificar(CobroPlanillas o) throws BusinessException{
  		CobroPlanillas dto = null;
     	try{
		     dto = dao.modificar(o);
	  	}catch(DAOException e){
	  		 throw new BusinessException(e);
	    }catch(Exception e){
	    	 throw new BusinessException(e);
	    }
	    return dto;
    }
  
	public CobroPlanillas getPorPk(CobroPlanillasId cobroPlanillasId) throws BusinessException{
		CobroPlanillas domain = null;
		List<CobroPlanillas> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intEmpresa", cobroPlanillasId.getIntEmpresa());
			mapa.put("intItemEfectuadoResumen", cobroPlanillasId.getIntItemEfectuadoResumen());
			mapa.put("intItemPagoPlanillas", cobroPlanillasId.getIntItemPagoPlanillas());
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

	public List<CobroPlanillas> getPorEfectuadoResumen(EfectuadoResumen efectuadoResumen) throws BusinessException{
		List<CobroPlanillas> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intEmpresa", efectuadoResumen.getId().getIntEmpresa());
			mapa.put("intItemEfectuadoResumen", efectuadoResumen.getId().getIntItemEfectuadoResumen());
			lista = dao.getListaPorEfectuadoResumen(mapa);			
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
}
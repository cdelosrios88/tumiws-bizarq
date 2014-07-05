package pe.com.tumi.tesoreria.egreso.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.tesoreria.egreso.dao.ConciliacionDao;
import pe.com.tumi.tesoreria.egreso.dao.impl.ConciliacionDaoIbatis;
import pe.com.tumi.tesoreria.egreso.domain.Conciliacion;
import pe.com.tumi.tesoreria.egreso.domain.ConciliacionId;


public class ConciliacionBO{

	private ConciliacionDao dao = (ConciliacionDao)TumiFactory.get(ConciliacionDaoIbatis.class);

	public Conciliacion grabar(Conciliacion o) throws BusinessException{
		Conciliacion dto = null;
		try{
		    dto = dao.grabar(o);
		}catch(DAOException e){
	  		throw new BusinessException(e);
	    }catch(Exception e){
	    	throw new BusinessException(e);
	    }
	    return dto;
	}

  	public Conciliacion modificar(Conciliacion o) throws BusinessException{
  		Conciliacion dto = null;
     	try{
		     dto = dao.modificar(o);
	  	}catch(DAOException e){
	  		 throw new BusinessException(e);
	    }catch(Exception e){
	    	 throw new BusinessException(e);
	    }
	    return dto;
    }
  
	public Conciliacion getPorPk(ConciliacionId conciliacionId) throws BusinessException{
		Conciliacion domain = null;
		List<Conciliacion> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresa", conciliacionId.getIntPersEmpresa());
			mapa.put("intItemConciliacion", conciliacionId.getIntItemConciliacion());
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
}
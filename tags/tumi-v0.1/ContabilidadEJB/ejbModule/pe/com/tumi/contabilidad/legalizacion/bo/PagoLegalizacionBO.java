package pe.com.tumi.contabilidad.legalizacion.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.contabilidad.legalizacion.dao.PagoLegalizacionDao;
import pe.com.tumi.contabilidad.legalizacion.dao.impl.PagoLegalizacionDaoIbatis;
import pe.com.tumi.contabilidad.legalizacion.domain.LibroLegalizacion;
import pe.com.tumi.contabilidad.legalizacion.domain.PagoLegalizacion;
import pe.com.tumi.contabilidad.legalizacion.domain.PagoLegalizacionId;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;

public class PagoLegalizacionBO {
	
	private PagoLegalizacionDao dao = (PagoLegalizacionDao)TumiFactory.get(PagoLegalizacionDaoIbatis.class);

	public PagoLegalizacion grabar(PagoLegalizacion o) throws BusinessException{
		PagoLegalizacion dto = null;
		try{
		    dto = dao.grabar(o);
		}catch(DAOException e){
	  		throw new BusinessException(e);
	    }catch(Exception e){
	    	throw new BusinessException(e);
	    }
	    return dto;
	}
	
  	public PagoLegalizacion modificar(PagoLegalizacion o) throws BusinessException{
  		PagoLegalizacion dto = null;
     	try{
		     dto = dao.modificar(o);
	  	}catch(DAOException e){
	  		 throw new BusinessException(e);
	    }catch(Exception e){
	    	 throw new BusinessException(e);
	    }
	    return dto;
    }
  
	public PagoLegalizacion getPorPk(PagoLegalizacionId pId) throws BusinessException{
		PagoLegalizacion domain = null;
		List<PagoLegalizacion> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresa",pId.getIntPersEmpresa());
			mapa.put("intParaLibroContable",pId.getIntParaLibroContable());
			mapa.put("intItemLibroLegalizacion",pId.getIntItemLibroLegalizacion());
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
	
	public List<PagoLegalizacion> getPorLibroLegalizacion(LibroLegalizacion o) throws BusinessException{
		List<PagoLegalizacion> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresa",o.getId().getIntPersEmpresa());
			mapa.put("intParaLibroContable",o.getId().getIntParaLibroContable());
			mapa.put("intItemLibroLegalizacion",o.getId().getIntItemLibroLegalizacion());
			lista = dao.getListaPorLibroLegalizacion(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
}

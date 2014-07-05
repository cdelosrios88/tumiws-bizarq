package pe.com.tumi.contabilidad.legalizacion.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.contabilidad.legalizacion.dao.LibroLegalizacionDao;
import pe.com.tumi.contabilidad.legalizacion.dao.impl.LibroLegalizacionDaoIbatis;
import pe.com.tumi.contabilidad.legalizacion.domain.LibroLegalizacion;
import pe.com.tumi.contabilidad.legalizacion.domain.LibroLegalizacionId;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;

public class LibroLegalizacionBO {
	
	private LibroLegalizacionDao dao = (LibroLegalizacionDao)TumiFactory.get(LibroLegalizacionDaoIbatis.class);

	public LibroLegalizacion grabar(LibroLegalizacion o) throws BusinessException{
		LibroLegalizacion dto = null;
		try{
		    dto = dao.grabar(o);
		}catch(DAOException e){
	  		throw new BusinessException(e);
	    }catch(Exception e){
	    	throw new BusinessException(e);
	    }
	    return dto;
	}
	
  	public LibroLegalizacion modificar(LibroLegalizacion o) throws BusinessException{
  		LibroLegalizacion dto = null;
     	try{
		     dto = dao.modificar(o);
	  	}catch(DAOException e){
	  		 throw new BusinessException(e);
	    }catch(Exception e){
	    	 throw new BusinessException(e);
	    }
	    return dto;
    }
  
	public LibroLegalizacion getPorPk(LibroLegalizacionId pId) throws BusinessException{
		LibroLegalizacion domain = null;
		List<LibroLegalizacion> lista = null;
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
	
	public List<LibroLegalizacion> getPorIdPersona(Integer intIdPersona, Integer intIdEmpresa) throws BusinessException{
		List<LibroLegalizacion> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresa",intIdEmpresa);
			mapa.put("intPersPersona",intIdPersona);
			lista = dao.getListaPorIdPersona(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
}

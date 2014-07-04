package pe.com.tumi.contabilidad.core.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.contabilidad.core.dao.ModeloDao;
import pe.com.tumi.contabilidad.core.dao.PlanCuentaDao;
import pe.com.tumi.contabilidad.core.dao.impl.ModeloDaoIbatis;
import pe.com.tumi.contabilidad.core.dao.impl.PlanCuentaDaoIbatis;
import pe.com.tumi.contabilidad.core.domain.Modelo;
import pe.com.tumi.contabilidad.core.domain.ModeloId;
import pe.com.tumi.contabilidad.core.domain.PlanCuenta;
import pe.com.tumi.contabilidad.core.domain.PlanCuentaId;

public class ModeloBO{

	private ModeloDao dao = (ModeloDao)TumiFactory.get(ModeloDaoIbatis.class);

	public Modelo grabarModelo(Modelo o) throws BusinessException{
		Modelo dto = null;
		try{
		    dto = dao.grabar(o); 
		}catch(DAOException e){
			e.printStackTrace();
	  		throw new BusinessException(e);
	    }catch(Exception e){
	    	throw new BusinessException(e);
	    }
	    return dto;
	}

  	public Modelo modificarModelo(Modelo o) throws BusinessException{
     	Modelo dto = null;
     	try{
		     dto = dao.modificar(o);
	  	}catch(DAOException e){
	  		 throw new BusinessException(e);
	    }catch(Exception e){
	    	 throw new BusinessException(e);
	    }
	    return dto;
    }
  
	public Modelo getModeloPorPk(ModeloId pId) throws BusinessException{
		Modelo domain = null;
		List<Modelo> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intEmpresaPk", pId.getIntEmpresaPk());
			mapa.put("intCodigoModelo", pId.getIntCodigoModelo());
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
	
	public List<Modelo> getModeloBusqueda(Modelo pModelo) throws BusinessException{
		List<Modelo> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intEmpresaPk", pModelo.getId().getIntEmpresaPk());
			mapa.put("intTipoModeloContable", pModelo.getIntTipoModeloContable());
			mapa.put("strDescripcion", pModelo.getStrDescripcion());
			mapa.put("intEstado", pModelo.getIntEstado());
			lista = dao.getBusqueda(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
}

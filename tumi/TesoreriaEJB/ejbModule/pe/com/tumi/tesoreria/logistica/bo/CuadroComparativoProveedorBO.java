package pe.com.tumi.tesoreria.logistica.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.tesoreria.logistica.dao.CuadroComparativoProveedorDao;
import pe.com.tumi.tesoreria.logistica.dao.impl.CuadroComparativoProveedorDaoIbatis;
import pe.com.tumi.tesoreria.logistica.domain.CuadroComparativo;
import pe.com.tumi.tesoreria.logistica.domain.CuadroComparativoProveedor;
import pe.com.tumi.tesoreria.logistica.domain.CuadroComparativoProveedorId;


public class CuadroComparativoProveedorBO{

	private CuadroComparativoProveedorDao dao = (CuadroComparativoProveedorDao)TumiFactory.get(CuadroComparativoProveedorDaoIbatis.class);

	public CuadroComparativoProveedor grabar(CuadroComparativoProveedor o) throws BusinessException{
		CuadroComparativoProveedor dto = null;
		try{
		    dto = dao.grabar(o);
		}catch(DAOException e){
	  		throw new BusinessException(e);
	    }catch(Exception e){
	    	throw new BusinessException(e);
	    }
	    return dto;
	}

  	public CuadroComparativoProveedor modificar(CuadroComparativoProveedor o) throws BusinessException{
  		CuadroComparativoProveedor dto = null;
     	try{
		     dto = dao.modificar(o);
	  	}catch(DAOException e){
	  		 throw new BusinessException(e);
	    }catch(Exception e){
	    	 throw new BusinessException(e);
	    }
	    return dto;
    }
  
	public CuadroComparativoProveedor getPorPk(CuadroComparativoProveedorId pId) throws BusinessException{
		CuadroComparativoProveedor domain = null;
		List<CuadroComparativoProveedor> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresa", pId.getIntPersEmpresa());
			mapa.put("intItemCuadroComparativo", pId.getIntItemCuadroComparativo());
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
	
	public List<CuadroComparativoProveedor> getPorCuadroComparativo(CuadroComparativo cuadroComparativo) throws BusinessException{
		List<CuadroComparativoProveedor> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresa", cuadroComparativo.getId().getIntPersEmpresa());
			mapa.put("intItemCuadroComparativo", cuadroComparativo.getId().getIntItemCuadroComparativo());
			lista = dao.getListaPorCuadroComparativo(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}	
}
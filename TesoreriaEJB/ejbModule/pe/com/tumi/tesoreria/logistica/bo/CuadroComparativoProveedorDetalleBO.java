package pe.com.tumi.tesoreria.logistica.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.tesoreria.logistica.dao.CuadroComparativoProveedorDetalleDao;
import pe.com.tumi.tesoreria.logistica.dao.impl.CuadroComparativoProveedorDetalleDaoIbatis;
import pe.com.tumi.tesoreria.logistica.domain.CuadroComparativoProveedor;
import pe.com.tumi.tesoreria.logistica.domain.CuadroComparativoProveedorDetalle;
import pe.com.tumi.tesoreria.logistica.domain.CuadroComparativoProveedorDetalleId;


public class CuadroComparativoProveedorDetalleBO{

	private CuadroComparativoProveedorDetalleDao dao = (CuadroComparativoProveedorDetalleDao)TumiFactory.get(CuadroComparativoProveedorDetalleDaoIbatis.class);

	public CuadroComparativoProveedorDetalle grabar(CuadroComparativoProveedorDetalle o) throws BusinessException{
		CuadroComparativoProveedorDetalle dto = null;
		try{
		    dto = dao.grabar(o);
		}catch(DAOException e){
	  		throw new BusinessException(e);
	    }catch(Exception e){
	    	throw new BusinessException(e);
	    }
	    return dto;
	}

  	public CuadroComparativoProveedorDetalle modificar(CuadroComparativoProveedorDetalle o) throws BusinessException{
  		CuadroComparativoProveedorDetalle dto = null;
     	try{
		     dto = dao.modificar(o);
	  	}catch(DAOException e){
	  		 throw new BusinessException(e);
	    }catch(Exception e){
	    	 throw new BusinessException(e);
	    }
	    return dto;
    }
  
	public CuadroComparativoProveedorDetalle getPorPk(CuadroComparativoProveedorDetalleId pId) throws BusinessException{
		CuadroComparativoProveedorDetalle domain = null;
		List<CuadroComparativoProveedorDetalle> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresa", pId.getIntPersEmpresa());
			mapa.put("intItemCuadroComparativo", pId.getIntItemCuadroComparativo());
			mapa.put("intItemCuadroComparativoProveedor", pId.getIntItemCuadroComparativoProveedor());
			mapa.put("intItemCuadroComparativoProveedorDetalle", pId.getIntItemCuadroComparativoProveedorDetalle());
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
	
	public List<CuadroComparativoProveedorDetalle> getPorCuadroComparativoProveedor(CuadroComparativoProveedor cuadroComparativoProveedor) throws BusinessException{
		List<CuadroComparativoProveedorDetalle> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresa", cuadroComparativoProveedor.getId().getIntPersEmpresa());
			mapa.put("intItemCuadroComparativo", cuadroComparativoProveedor.getId().getIntItemCuadroComparativo());
			mapa.put("intItemCuadroComparativoProveedor", cuadroComparativoProveedor.getId().getIntItemCuadroComparativoProveedor());
			
			lista = dao.getListaPorCuadroComparativoProveedor(mapa);
				
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}	
}
package pe.com.tumi.contabilidad.core.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.contabilidad.core.dao.AccesoPlanCuentaDetalleDao;
import pe.com.tumi.contabilidad.core.dao.impl.AccesoPlanCuentaDetalleDaoIbatis;
import pe.com.tumi.contabilidad.core.domain.AccesoPlanCuenta;
import pe.com.tumi.contabilidad.core.domain.AccesoPlanCuentaDetalle;
import pe.com.tumi.contabilidad.core.domain.AccesoPlanCuentaDetalleId;

public class AccesoPlanCuentaDetalleBO{

	private AccesoPlanCuentaDetalleDao dao = (AccesoPlanCuentaDetalleDao)TumiFactory.get(AccesoPlanCuentaDetalleDaoIbatis.class);

	public AccesoPlanCuentaDetalle grabar(AccesoPlanCuentaDetalle o) throws BusinessException{
		AccesoPlanCuentaDetalle dto = null;
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

  	public AccesoPlanCuentaDetalle modificar(AccesoPlanCuentaDetalle o) throws BusinessException{
  		AccesoPlanCuentaDetalle dto = null;
     	try{
		     dto = dao.modificar(o);
	  	}catch(DAOException e){
	  		 throw new BusinessException(e);
	    }catch(Exception e){
	    	 throw new BusinessException(e);
	    }
	    return dto;
    }
  
	public AccesoPlanCuentaDetalle getPorPk(AccesoPlanCuentaDetalleId pId) throws BusinessException{
		AccesoPlanCuentaDetalle domain = null;
		List<AccesoPlanCuentaDetalle> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intEmpresaCuenta", pId.getIntEmpresaCuenta());
			mapa.put("intPeriodoCuenta", pId.getIntPeriodoCuenta());
			mapa.put("strNumeroCuenta", pId.getStrNumeroCuenta());
			mapa.put("intIdTransaccion", pId.getIntIdTransaccion());
			mapa.put("intItemAccesoPlanCuenta", pId.getIntItemAccesoPlanCuenta());
			mapa.put("intItemAccesoPlanCuentaDetalle", pId.getIntItemAccesoPlanCuentaDetalle());
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
	
	public List<AccesoPlanCuentaDetalle> getPorAccesoPlanCuenta(AccesoPlanCuenta accesoPlanCuenta) throws BusinessException{
		List<AccesoPlanCuentaDetalle> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intEmpresaCuenta", accesoPlanCuenta.getId().getIntEmpresaCuenta());
			mapa.put("intPeriodoCuenta", accesoPlanCuenta.getId().getIntPeriodoCuenta());
			mapa.put("strNumeroCuenta", accesoPlanCuenta.getId().getStrNumeroCuenta());
			mapa.put("intIdTransaccion", accesoPlanCuenta.getId().getIntIdTransaccion());
			mapa.put("intItemAccesoPlanCuenta", accesoPlanCuenta.getId().getIntItemAccesoPlanCuenta());
			lista = dao.getListaPorAccesoPlanCuenta(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}	
}
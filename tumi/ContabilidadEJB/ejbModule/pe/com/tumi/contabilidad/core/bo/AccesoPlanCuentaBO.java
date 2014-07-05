package pe.com.tumi.contabilidad.core.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.contabilidad.core.dao.AccesoPlanCuentaDao;
import pe.com.tumi.contabilidad.core.dao.impl.AccesoPlanCuentaDaoIbatis;
import pe.com.tumi.contabilidad.core.domain.AccesoPlanCuenta;
import pe.com.tumi.contabilidad.core.domain.AccesoPlanCuentaId;
import pe.com.tumi.contabilidad.core.domain.PlanCuenta;

public class AccesoPlanCuentaBO{

	private AccesoPlanCuentaDao dao = (AccesoPlanCuentaDao)TumiFactory.get(AccesoPlanCuentaDaoIbatis.class);

	public AccesoPlanCuenta grabar(AccesoPlanCuenta o) throws BusinessException{
		AccesoPlanCuenta dto = null;
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

  	public AccesoPlanCuenta modificar(AccesoPlanCuenta o) throws BusinessException{
  		AccesoPlanCuenta dto = null;
     	try{
		     dto = dao.modificar(o);
	  	}catch(DAOException e){
	  		 throw new BusinessException(e);
	    }catch(Exception e){
	    	 throw new BusinessException(e);
	    }
	    return dto;
    }
  
	public AccesoPlanCuenta getPorPk(AccesoPlanCuentaId pId) throws BusinessException{
		AccesoPlanCuenta domain = null;
		List<AccesoPlanCuenta> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intEmpresaCuenta", pId.getIntEmpresaCuenta());
			mapa.put("intPeriodoCuenta", pId.getIntPeriodoCuenta());
			mapa.put("strNumeroCuenta", pId.getStrNumeroCuenta());
			mapa.put("intIdTransaccion", pId.getIntIdTransaccion());
			mapa.put("intItemAccesoPlanCuenta", pId.getIntItemAccesoPlanCuenta());
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
	
	public List<AccesoPlanCuenta> getPorPlanCuenta(PlanCuenta planCuenta, Integer intIdTransaccion) throws BusinessException{
		List<AccesoPlanCuenta> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intEmpresaCuenta", planCuenta.getId().getIntEmpresaCuentaPk());
			mapa.put("intPeriodoCuenta", planCuenta.getId().getIntPeriodoCuenta());
			mapa.put("strNumeroCuenta", planCuenta.getId().getStrNumeroCuenta());
			mapa.put("intIdTransaccion", intIdTransaccion);
			lista = dao.getListaPorPlanCuenta(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}	
}
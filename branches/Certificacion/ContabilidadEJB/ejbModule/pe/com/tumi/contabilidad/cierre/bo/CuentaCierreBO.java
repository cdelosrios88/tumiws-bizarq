package pe.com.tumi.contabilidad.cierre.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.contabilidad.cierre.dao.CuentaCierreDao;
import pe.com.tumi.contabilidad.cierre.dao.impl.CuentaCierreDaoIbatis;
import pe.com.tumi.contabilidad.cierre.domain.CuentaCierre;
import pe.com.tumi.contabilidad.cierre.domain.CuentaCierreId;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;

public class CuentaCierreBO {
	private CuentaCierreDao dao = (CuentaCierreDao)TumiFactory.get(CuentaCierreDaoIbatis.class);

	public CuentaCierre grabar(CuentaCierre o) throws BusinessException{
		CuentaCierre dto = null;
		try{
		    dto = dao.grabar(o);
		}catch(DAOException e){
	  		throw new BusinessException(e);
	    }catch(Exception e){
	    	throw new BusinessException(e);
	    }
	    return dto;
	}
	
  	public CuentaCierre modificar(CuentaCierre o) throws BusinessException{
  		CuentaCierre dto = null;
     	try{
		     dto = dao.modificar(o);
	  	}catch(DAOException e){
	  		 throw new BusinessException(e);
	    }catch(Exception e){
	    	 throw new BusinessException(e);
	    }
	    return dto;
    }
  
	public CuentaCierre getPorPk(CuentaCierreId pId) throws BusinessException{
		CuentaCierre domain = null;
		List<CuentaCierre> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaCierre",pId.getIntPersEmpresaCierre());
			mapa.put("intContPeriodoCierre",pId.getIntContPeriodoCierre());
			mapa.put("intParaTipoCierre", 	pId.getIntParaTipoCierre());
			mapa.put("intContCodigoCierre", pId.getIntContCodigoCierre());
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
	
	public List<CuentaCierre> getPorBusqueda(CuentaCierre o) throws BusinessException{
		List<CuentaCierre> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intContPeriodoCierre",o.getId().getIntContPeriodoCierre());
			mapa.put("intParaTipoCierre", 	o.getId().getIntParaTipoCierre());
			mapa.put("strDescripcion", 		o.getStrDescripcion());
			mapa.put("strContNumeroCuenta", o.getStrContNumeroCuenta());
			mapa.put("intParaEstado", 		o.getIntParaEstado());
			lista = dao.getListaPorBusqueda(mapa);

		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
}

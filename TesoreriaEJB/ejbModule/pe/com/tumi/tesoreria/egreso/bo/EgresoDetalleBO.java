package pe.com.tumi.tesoreria.egreso.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.tesoreria.egreso.dao.EgresoDetalleDao;
import pe.com.tumi.tesoreria.egreso.dao.impl.EgresoDetalleDaoIbatis;
import pe.com.tumi.tesoreria.egreso.domain.Egreso;
import pe.com.tumi.tesoreria.egreso.domain.EgresoDetalle;
import pe.com.tumi.tesoreria.egreso.domain.EgresoDetalleId;

public class EgresoDetalleBO{

	private EgresoDetalleDao dao = (EgresoDetalleDao)TumiFactory.get(EgresoDetalleDaoIbatis.class);

	public EgresoDetalle grabar(EgresoDetalle o) throws BusinessException{
		EgresoDetalle dto = null;
		try{
		    dto = dao.grabar(o);
		}catch(DAOException e){
	  		throw new BusinessException(e);
	    }catch(Exception e){
	    	throw new BusinessException(e);
	    }
	    return dto;
	}

  	public EgresoDetalle modificar(EgresoDetalle o) throws BusinessException{
  		EgresoDetalle dto = null;
     	try{
		     dto = dao.modificar(o);
	  	}catch(DAOException e){
	  		 throw new BusinessException(e);
	    }catch(Exception e){
	    	 throw new BusinessException(e);
	    }
	    return dto;
    }
  
	public EgresoDetalle getPorPk(EgresoDetalleId pId) throws BusinessException{
		EgresoDetalle domain = null;
		List<EgresoDetalle> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaEgreso", pId.getIntPersEmpresaEgreso());
			mapa.put("intItemEgresoGeneral", pId.getIntItemEgresoGeneral());
			mapa.put("intItemEgresoDetalle", pId.getIntItemEgresoDetalle());
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
	
	public List<EgresoDetalle> getPorEgreso(Egreso o) throws BusinessException{
		List<EgresoDetalle> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaEgreso", o.getId().getIntPersEmpresaEgreso());
			mapa.put("intItemEgresoGeneral", o.getId().getIntItemEgresoGeneral());
			lista = dao.getListaPorEgreso(mapa);			
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<EgresoDetalle> getListaPorBuscar(EgresoDetalle egresoDetalle) throws BusinessException{
		List<EgresoDetalle> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaEgreso", egresoDetalle.getId().getIntPersEmpresaEgreso());
			mapa.put("strNumeroDocumento", egresoDetalle.getStrNumeroDocumento());
			lista = dao.getListaPorBuscar(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
}
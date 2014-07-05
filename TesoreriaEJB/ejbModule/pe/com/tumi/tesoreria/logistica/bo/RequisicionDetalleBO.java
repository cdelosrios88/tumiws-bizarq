package pe.com.tumi.tesoreria.logistica.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.tesoreria.logistica.dao.RequisicionDetalleDao;
import pe.com.tumi.tesoreria.logistica.dao.impl.RequisicionDetalleDaoIbatis;
import pe.com.tumi.tesoreria.logistica.domain.Requisicion;
import pe.com.tumi.tesoreria.logistica.domain.RequisicionDetalle;
import pe.com.tumi.tesoreria.logistica.domain.RequisicionDetalleId;


public class RequisicionDetalleBO{

	private RequisicionDetalleDao dao = (RequisicionDetalleDao)TumiFactory.get(RequisicionDetalleDaoIbatis.class);

	public RequisicionDetalle grabar(RequisicionDetalle o) throws BusinessException{
		RequisicionDetalle dto = null;
		try{
		    dto = dao.grabar(o);
		}catch(DAOException e){
	  		throw new BusinessException(e);
	    }catch(Exception e){
	    	throw new BusinessException(e);
	    }
	    return dto;
	}

  	public RequisicionDetalle modificar(RequisicionDetalle o) throws BusinessException{
  		RequisicionDetalle dto = null;
     	try{
		     dto = dao.modificar(o);
	  	}catch(DAOException e){
	  		 throw new BusinessException(e);
	    }catch(Exception e){
	    	 throw new BusinessException(e);
	    }
	    return dto;
    }
  
	public RequisicionDetalle getPorPk(RequisicionDetalleId pId) throws BusinessException{
		RequisicionDetalle domain = null;
		List<RequisicionDetalle> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresa", pId.getIntPersEmpresa());
			mapa.put("intItemRequisicion", pId.getIntItemRequisicion());
			mapa.put("intItemRequisicionDetalle", pId.getIntItemRequisicionDetalle());
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

	public List<RequisicionDetalle> getPorRequisicion(Requisicion requisicion) throws BusinessException{
		List<RequisicionDetalle> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresa", requisicion.getId().getIntPersEmpresa());
			mapa.put("intItemRequisicion", requisicion.getId().getIntItemRequisicion());
			lista = dao.getListaPorRequisicion(mapa);				
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
}
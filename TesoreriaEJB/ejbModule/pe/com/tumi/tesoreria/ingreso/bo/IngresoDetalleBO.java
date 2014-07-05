package pe.com.tumi.tesoreria.ingreso.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.tesoreria.egreso.domain.ControlFondosFijosId;
import pe.com.tumi.tesoreria.ingreso.dao.IngresoDetalleDao;
import pe.com.tumi.tesoreria.ingreso.dao.impl.IngresoDetalleDaoIbatis;
import pe.com.tumi.tesoreria.ingreso.domain.Ingreso;
import pe.com.tumi.tesoreria.ingreso.domain.IngresoDetalle;
import pe.com.tumi.tesoreria.ingreso.domain.IngresoDetalleId;

public class IngresoDetalleBO{

	private IngresoDetalleDao dao = (IngresoDetalleDao)TumiFactory.get(IngresoDetalleDaoIbatis.class);

	public IngresoDetalle grabar(IngresoDetalle o) throws BusinessException{
		IngresoDetalle dto = null;
		try{
		    dto = dao.grabar(o);
		}catch(DAOException e){
	  		throw new BusinessException(e);
	    }catch(Exception e){
	    	throw new BusinessException(e);
	    }
	    return dto;
	}

  	public IngresoDetalle modificar(IngresoDetalle o) throws BusinessException{
  		IngresoDetalle dto = null;
     	try{
		     dto = dao.modificar(o);
	  	}catch(DAOException e){
	  		 throw new BusinessException(e);
	    }catch(Exception e){
	    	 throw new BusinessException(e);
	    }
	    return dto;
    }
  
	public IngresoDetalle getPorPk(IngresoDetalleId pId) throws BusinessException{
		IngresoDetalle domain = null;
		List<IngresoDetalle> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresa", pId.getIntPersEmpresa());
			mapa.put("intIdIngresoGeneral", pId.getIntIdIngresoGeneral());
			mapa.put("intIdIngresoDetalle", pId.getIntIdIngresoDetalle());
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
	
	public List<IngresoDetalle> getPorIngreso(Ingreso ingreso) throws BusinessException{
		List<IngresoDetalle> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intIdEmpresa", ingreso.getId().getIntIdEmpresa());
			mapa.put("intIdIngresoGeneral", ingreso.getId().getIntIdIngresoGeneral());
			lista = dao.getListaPorIngreso(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<IngresoDetalle> getPorControlFondosFijos(ControlFondosFijosId pId) throws BusinessException{
		List<IngresoDetalle> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresa", pId.getIntPersEmpresa());
			mapa.put("intParaTipoFondoFijo", pId.getIntParaTipoFondoFijo());
			mapa.put("intItemPeriodoFondo", pId.getIntItemPeriodoFondo());
			mapa.put("intSucuIdSucursal", pId.getIntSucuIdSucursal());
			mapa.put("intItemFondoFijo", pId.getIntItemFondoFijo());
			lista = dao.getPorControlFondosFijos(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
}
package pe.com.tumi.contabilidad.operaciones.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.contabilidad.core.domain.ModeloDetalle;
import pe.com.tumi.contabilidad.core.domain.ModeloDetalleId;
import pe.com.tumi.contabilidad.operaciones.dao.HojaManualDao;
import pe.com.tumi.contabilidad.operaciones.dao.HojaManualDetalleDao;
import pe.com.tumi.contabilidad.operaciones.dao.impl.HojaManualDaoIbatis;
import pe.com.tumi.contabilidad.operaciones.dao.impl.HojaManualDetalleDaoIbatis;
import pe.com.tumi.contabilidad.operaciones.domain.HojaManual;
import pe.com.tumi.contabilidad.operaciones.domain.HojaManualDetalle;
import pe.com.tumi.contabilidad.operaciones.domain.HojaManualDetalleId;
import pe.com.tumi.contabilidad.operaciones.domain.HojaManualId;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;

public class HojaManualDetalleBO{

	private HojaManualDetalleDao dao = (HojaManualDetalleDao)TumiFactory.get(HojaManualDetalleDaoIbatis.class);

	public HojaManualDetalle grabarHojaManualDetalle(HojaManualDetalle o) throws BusinessException{
		HojaManualDetalle dto = null;
		try{
		    dto = dao.grabar(o);
		}catch(DAOException e){
	  		throw new BusinessException(e);
	    }catch(Exception e){
	    	throw new BusinessException(e);
	    }
	    return dto;
	}

  	public HojaManualDetalle modificarHojaManualDetalle(HojaManualDetalle o) throws BusinessException{
     	HojaManualDetalle dto = null;
     	try{
		     dto = dao.modificar(o);
	  	}catch(DAOException e){
	  		 throw new BusinessException(e);
	    }catch(Exception e){
	    	 throw new BusinessException(e);
	    }
	    return dto;
    }
  
	public HojaManualDetalle getHojaManualDetallePorPk(HojaManualDetalleId pId) throws BusinessException{
		HojaManualDetalle domain = null;
		List<HojaManualDetalle> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("pIntPersEmpresaHojaPk", pId.getIntPersEmpresaHojaPk());
			mapa.put("pIntContPeriodoHoja", pId.getIntContPeriodoHoja());
			mapa.put("pIntContCodigoHoja", pId.getIntContCodigoHoja());
			mapa.put("pIntContItemHoja", pId.getIntContItemHoja());
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
	
	public List<HojaManualDetalle> getListHojaManualDetBusq(HojaManualDetalle o) throws BusinessException{
		List<HojaManualDetalle> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("pIntPersEmpresaHojaPk", o.getId().getIntPersEmpresaHojaPk());
			mapa.put("pIntContPeriodoHoja", o.getId().getIntContPeriodoHoja());
			mapa.put("pIntContCodigoHoja", o.getId().getIntContCodigoHoja());
			mapa.put("pStrContNumeroCuenta", o.getStrContNumeroCuenta());
			lista = dao.getBusqueda(mapa);	
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public HojaManualDetalle eliminarHojaManualDetalle(HojaManualDetalleId o) throws BusinessException{
		HojaManualDetalle dto = null;
		try{
			System.out.println("eliminando...");
			System.out.println("intPersEmpresaHojaPk: "+o.getIntPersEmpresaHojaPk());
			System.out.println("intContPeriodoHoja: "+o.getIntContPeriodoHoja());
			System.out.println("intContCodigoHoja: "+o.getIntContCodigoHoja());
		    dto = dao.eliminar(o);
		}catch(DAOException e){
	  		throw new BusinessException(e);
	    }catch(Exception e){
	    	throw new BusinessException(e);
	    }
	    return dto;
	}
}

package pe.com.tumi.contabilidad.operaciones.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.contabilidad.operaciones.dao.HojaManualDao;
import pe.com.tumi.contabilidad.operaciones.dao.impl.HojaManualDaoIbatis;
import pe.com.tumi.contabilidad.operaciones.domain.HojaManual;
import pe.com.tumi.contabilidad.operaciones.domain.HojaManualId;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;

public class HojaManualBO{

	private HojaManualDao dao = (HojaManualDao)TumiFactory.get(HojaManualDaoIbatis.class);

	public HojaManual grabarHojaManual(HojaManual o) throws BusinessException{
		HojaManual dto = null;
		try{
		    dto = dao.grabar(o);
		}catch(DAOException e){
	  		throw new BusinessException(e);
	    }catch(Exception e){
	    	throw new BusinessException(e);
	    }
	    return dto;
	}

  	public HojaManual modificarHojaManual(HojaManual o) throws BusinessException{
     	HojaManual dto = null;
     	try{
		     dto = dao.modificar(o);
	  	}catch(DAOException e){
	  		 throw new BusinessException(e);
	    }catch(Exception e){
	    	 throw new BusinessException(e);
	    }
	    return dto;
    }
  
	public HojaManual getHojaManualPorPk(HojaManualId pId) throws BusinessException{
		HojaManual domain = null;
		List<HojaManual> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("pIntPersEmpresaHojaPk", pId.getIntPersEmpresaHojaPk());
			mapa.put("pIntContPeriodoHoja", pId.getIntContPeriodoHoja());
			mapa.put("pIntContCodigoHoja", pId.getIntContCodigoHoja());
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
	
	public List<HojaManual> getListHojaManualBusqueda(HojaManual o) throws BusinessException{
		List<HojaManual> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("pTsFechaRegistroDesde", o.getTsFechaRegistroDesde());
			mapa.put("pTsFechaRegistroHasta", o.getTsFechaRegistroHasta());
			mapa.put("pIntContCodigoHoja", o.getId().getIntContCodigoHoja());
			lista = dao.getBusqueda(mapa);	
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
}

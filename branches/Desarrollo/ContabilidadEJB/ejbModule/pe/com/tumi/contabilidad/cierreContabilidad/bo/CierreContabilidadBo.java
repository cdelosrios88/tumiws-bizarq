package pe.com.tumi.contabilidad.cierreContabilidad.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.contabilidad.cierreContabilidad.dao.CierreContabilidadDao;
import pe.com.tumi.contabilidad.cierreContabilidad.dao.impl.CierreContabilidadDaoIbatis;
import pe.com.tumi.contabilidad.cierreContabilidad.domain.CierreContabilidad;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;

public class CierreContabilidadBo {
	private CierreContabilidadDao dao = (CierreContabilidadDao)TumiFactory.get(CierreContabilidadDaoIbatis.class);
	
	public CierreContabilidad grabarCierreContabilidad(CierreContabilidad o) throws BusinessException{
		CierreContabilidad dto = null;
		try{
		    dto = dao.grabarCierreCon(o);
		}catch(DAOException e){
	  		throw new BusinessException(e);
	    }catch(Exception e){
	    	throw new BusinessException(e);
	    }
	    return dto;
	}
	
	public CierreContabilidad modificarCierreContabilidad(CierreContabilidad o) throws BusinessException{
		CierreContabilidad dto = null;
     	try{
		     dto = dao.modificarCierreCon(o);
	  	}catch(DAOException e){
	  		 throw new BusinessException(e);
	    }catch(Exception e){
	    	 throw new BusinessException(e);
	    }
	    return dto;
    }
	public List<CierreContabilidad> getListaBuscarCierre(CierreContabilidad o) throws BusinessException{
		CierreContabilidad domain = null;
		List<CierreContabilidad> lista = null;
	
		try{
			HashMap mapa = new HashMap();
			mapa.put("pIntCcobPeriodoCierre", o.getId().getIntCcobPeriodoCierre());
			mapa.put("pIntEstadoCierreCod", o.getId().getIntEstadoCierreCod());
			lista = dao.getListaBuscarCierre(mapa);	
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<CierreContabilidad> getListaCierre(CierreContabilidad o) throws BusinessException{
		CierreContabilidad domain = null;
		List<CierreContabilidad> lista = null;
	
		try{
			HashMap mapa = new HashMap();
			mapa.put("pIntEmpresa", o.getId().getIntPersEmpresaCieCob());
			mapa.put("pIntCcobPeriodo", o.getId().getIntCcobPeriodoCierre());
			lista = dao.getListaCierre(mapa);	
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
}

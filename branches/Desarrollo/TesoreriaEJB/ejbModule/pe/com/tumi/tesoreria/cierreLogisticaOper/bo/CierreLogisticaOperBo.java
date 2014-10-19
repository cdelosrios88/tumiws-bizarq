package pe.com.tumi.tesoreria.cierreLogisticaOper.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.contabilidad.perdidasSiniestro.domain.PerdidasSiniestro;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.tesoreria.cierreLogisticaOper.dao.CierreLogisticaOperDao;
import pe.com.tumi.tesoreria.cierreLogisticaOper.dao.impl.CierreLogisticaOperDaoIbatis;
import pe.com.tumi.tesoreria.cierreLogisticaOper.domain.CierreLogisticaOper;
import pe.com.tumi.tesoreria.cierreLogisticaOper.domain.CierreLogisticaOperId;

public class CierreLogisticaOperBo {
	private CierreLogisticaOperDao dao = (CierreLogisticaOperDao)TumiFactory.get(CierreLogisticaOperDaoIbatis.class);
	
	public CierreLogisticaOper grabarCierreLogistica(CierreLogisticaOper o) throws BusinessException{
		CierreLogisticaOper dto = null;
		try{
		    dto = dao.grabarCierreLogistica(o);
		}catch(DAOException e){
	  		throw new BusinessException(e);
	    }catch(Exception e){
	    	throw new BusinessException(e);
	    }
	    return dto;
	}
	
	public CierreLogisticaOper modificarCierreLogistica(CierreLogisticaOper o) throws BusinessException{
		CierreLogisticaOper dto = null;
     	try{
		     dto = dao.modificarCierreLogistica(o);
	  	}catch(DAOException e){
	  		 throw new BusinessException(e);
	    }catch(Exception e){
	    	 throw new BusinessException(e);
	    }
	    return dto;
    }
	
	public List<CierreLogisticaOper> getListaCierreLogisticaVista(CierreLogisticaOper o) throws BusinessException{
		List<CierreLogisticaOper> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("pIntPersEmpresa", o.getId().getIntPersEmpresaCietes());
			mapa.put("pIntCcoPeriodoCierre", o.getId().getIntCcobPeriodoCierre());
			lista = dao.getListaCierreLogisticaVista(mapa);	
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public CierreLogisticaOper getListaCierreLogisticaValidar(CierreLogisticaOperId o) throws BusinessException{
		List<CierreLogisticaOper> lista = null;
		CierreLogisticaOper cierreLogisticaOper = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("pIntPerEmpresa", o.getIntPersEmpresaCietes());
			mapa.put("pIntCoPeriodo", o.getIntCcobPeriodoCierre());
			mapa.put("pIntTipoRegistro", o.getIntParaTipoRegistroLogis());
			lista = dao.getListaCierreLogisticaValidar(mapa);
			if(lista!=null){
				if(lista.size()==1){
				cierreLogisticaOper = lista.get(0);
			}else if(lista.size()==0){
				cierreLogisticaOper = null;
			}else{
				throw new BusinessException("Obtención de mas de un registro coincidente");
			}
	}	
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return cierreLogisticaOper;
	}
	
	public List<CierreLogisticaOper> getListaBuscarCierre(CierreLogisticaOper o) throws BusinessException{
		CierreLogisticaOper domain = null;
		List<CierreLogisticaOper> lista = null;
			
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("pIntTipoRegistro", o.getId().getIntParaTipoRegistroLogis());
			mapa.put("pIntCoPeriodo", o.getId().getIntCcobPeriodoCierre());
			mapa.put("pIntEstadoCie", o.getIntParaEstadoCierreCod());
			lista = dao.getListaBuscarCierre(mapa);	
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
}

package pe.com.tumi.tesoreria.egreso.bo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.tesoreria.egreso.dao.CierreDiarioArqueoDao;
import pe.com.tumi.tesoreria.egreso.dao.impl.CierreDiarioArqueoDaoIbatis;
import pe.com.tumi.tesoreria.egreso.domain.CierreDiarioArqueo;
import pe.com.tumi.tesoreria.egreso.domain.CierreDiarioArqueoId;


public class CierreDiarioArqueoBO{

	private CierreDiarioArqueoDao dao = (CierreDiarioArqueoDao)TumiFactory.get(CierreDiarioArqueoDaoIbatis.class);

	public CierreDiarioArqueo grabar(CierreDiarioArqueo o) throws BusinessException{
		CierreDiarioArqueo dto = null;
		try{
		    dto = dao.grabar(o);
		}catch(DAOException e){
	  		throw new BusinessException(e);
	    }catch(Exception e){
	    	throw new BusinessException(e);
	    }
	    return dto;
	}

  	public CierreDiarioArqueo modificar(CierreDiarioArqueo o) throws BusinessException{
  		CierreDiarioArqueo dto = null;
     	try{
		     dto = dao.modificar(o);
	  	}catch(DAOException e){
	  		 throw new BusinessException(e);
	    }catch(Exception e){
	    	 throw new BusinessException(e);
	    }
	    return dto;
    }
  
	public CierreDiarioArqueo getPorPk(CierreDiarioArqueoId cierreDiarioArqueoId) throws BusinessException{
		CierreDiarioArqueo domain = null;
		List<CierreDiarioArqueo> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresa", cierreDiarioArqueoId.getIntPersEmpresa());
			mapa.put("intParaTipoArqueo", cierreDiarioArqueoId.getIntParaTipoArqueo());
			mapa.put("intSucuIdSucursal", cierreDiarioArqueoId.getIntSucuIdSucursal());
			mapa.put("intSudeIdSubsucursal", cierreDiarioArqueoId.getIntSudeIdSubsucursal());
			mapa.put("intItemCierreDiarioArqueo", cierreDiarioArqueoId.getIntItemCierreDiarioArqueo());
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

	public List<CierreDiarioArqueo> getListaBuscar(CierreDiarioArqueo cierreDiarioArqueo) throws BusinessException{
		List<CierreDiarioArqueo> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresa", cierreDiarioArqueo.getId().getIntPersEmpresa());
			mapa.put("intParaTipoArqueo", cierreDiarioArqueo.getId().getIntParaTipoArqueo());
			mapa.put("intSucuIdSucursal", cierreDiarioArqueo.getId().getIntSucuIdSucursal());
			mapa.put("intSudeIdSubsucursal", cierreDiarioArqueo.getId().getIntSudeIdSubsucursal());
			mapa.put("tsFechaCierreArqueo", cierreDiarioArqueo.getTsFechaCierreArqueo());
			mapa.put("intParaEstadoCierre", cierreDiarioArqueo.getIntParaEstadoCierre());
			lista = dao.getListaPorBuscar(mapa);
				
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public CierreDiarioArqueo getCierreDiarioArqueoAnterior(CierreDiarioArqueo cierreDiarioArqueo) throws BusinessException{
		CierreDiarioArqueo domain = null;
		List<CierreDiarioArqueo> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresa", cierreDiarioArqueo.getId().getIntPersEmpresa());
			mapa.put("intParaTipoArqueo", cierreDiarioArqueo.getId().getIntParaTipoArqueo());
			mapa.put("intSucuIdSucursal", cierreDiarioArqueo.getId().getIntSucuIdSucursal());
			mapa.put("intSudeIdSubsucursal", cierreDiarioArqueo.getId().getIntSudeIdSubsucursal());
			lista = dao.getListaParaDiaAnterior(mapa);
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
	
	public List<HashMap> getListaFechas(Integer intIdEmpresa) throws BusinessException{
		List<HashMap> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresa", intIdEmpresa);
			lista = dao.getListaFechas(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
}
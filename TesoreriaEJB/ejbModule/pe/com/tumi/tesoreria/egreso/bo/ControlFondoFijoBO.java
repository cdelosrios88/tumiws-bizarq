package pe.com.tumi.tesoreria.egreso.bo;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.tesoreria.cierre.fdoFijo.domain.ControlFondoFijoAnula;
import pe.com.tumi.tesoreria.egreso.dao.ControlFondosFijosDao;
import pe.com.tumi.tesoreria.egreso.dao.impl.ControlFondosFijosDaoIbatis;
import pe.com.tumi.tesoreria.egreso.domain.ControlFondosFijos;
import pe.com.tumi.tesoreria.egreso.domain.ControlFondosFijosId;

public class ControlFondoFijoBO{

	private ControlFondosFijosDao dao = (ControlFondosFijosDao)TumiFactory.get(ControlFondosFijosDaoIbatis.class);

	protected static Logger log = Logger.getLogger(ControlFondoFijoBO.class);
	
	public ControlFondosFijos grabar(ControlFondosFijos o) throws BusinessException{
		ControlFondosFijos dto = null;
		try{
		    dto = dao.grabar(o);
		}catch(DAOException e){
	  		throw new BusinessException(e);
	    }catch(Exception e){
	    	throw new BusinessException(e);
	    }
	    return dto;
	}

  	public ControlFondosFijos modificar(ControlFondosFijos o) throws BusinessException{
  		ControlFondosFijos dto = null;
     	try{
		     dto = dao.modificar(o);
	  	}catch(DAOException e){
	  		 throw new BusinessException(e);
	    }catch(Exception e){
	    	 throw new BusinessException(e);
	    }
	    return dto;
    }
  
	public ControlFondosFijos getPorPk(ControlFondosFijosId pId) throws BusinessException{
		ControlFondosFijos domain = null;
		List<ControlFondosFijos> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresa", pId.getIntPersEmpresa());
			mapa.put("intParaTipoFondoFijo", pId.getIntParaTipoFondoFijo());
			mapa.put("intItemPeriodoFondo", pId.getIntItemPeriodoFondo());
			mapa.put("intSucuIdSucursal", pId.getIntSucuIdSucursal());
			mapa.put("intItemFondoFijo", pId.getIntItemFondoFijo());
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
	
	public List<ControlFondosFijos> getParaItem(ControlFondosFijos o) throws BusinessException{
		List<ControlFondosFijos> lista = null;
		try{
			log.info(o);
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intParaTipoFondoFijo", o.getId().getIntParaTipoFondoFijo());
			mapa.put("intItemPeriodoFondo", o.getId().getIntItemPeriodoFondo());
			mapa.put("intSucuIdSucursal", o.getId().getIntSucuIdSucursal());
			lista = dao.getListaParaItem(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<ControlFondosFijos> getPorBusqueda(ControlFondosFijos o) throws BusinessException{
		List<ControlFondosFijos> lista = null;
		try{
			log.info(o);
			log.info("intPersEmpresa:"+ o.getId().getIntPersEmpresa());
			log.info("intParaTipoFondoFijo:"+ o.getId().getIntParaTipoFondoFijo());
			log.info("intItemPeriodoFondo:"+ o.getId().getIntItemPeriodoFondo());
			log.info("intSucuIdSucursal:"+ o.getId().getIntSucuIdSucursal());
			log.info("intSudeIdSubsucursal:"+ o.getIntSudeIdSubsucursal());
			log.info("intItemFiltro:"+ o.getIntItemFiltro());
			log.info("intEstadoFondo:"+ o.getIntEstadoFondo());
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresa", o.getId().getIntPersEmpresa());
			mapa.put("intParaTipoFondoFijo", o.getId().getIntParaTipoFondoFijo());
			mapa.put("intItemPeriodoFondo", o.getId().getIntItemPeriodoFondo());
			mapa.put("intSucuIdSucursal", o.getId().getIntSucuIdSucursal());
			mapa.put("intSudeIdSubsucursal", o.getIntSudeIdSubsucursal());
			mapa.put("intItemFiltro", o.getIntItemFiltro());
			mapa.put("intEstadoFondo", o.getIntEstadoFondo());
			lista = dao.getListaPorBusqueda(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public ControlFondoFijoAnula grabarAnulaCierre(ControlFondoFijoAnula o) throws BusinessException{
		ControlFondoFijoAnula dto = null;
		try{
		    dto = dao.grabarAnulaCierre(o);
		}catch(DAOException e){
	  		throw new BusinessException(e);
	    }catch(Exception e){
	    	throw new BusinessException(e);
	    }
	    return dto;
	}
	
	public List<ControlFondosFijos> getControlFondoFijo(Integer intEmpresa, Integer intTipoFondo, Integer intIdSucursal, Integer intIdSubSucursal) throws BusinessException{
		List<ControlFondosFijos> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intEmpresa", 			intEmpresa);
			mapa.put("intTipoFondo", 		intTipoFondo);
			mapa.put("intIdSucursal", 		intIdSucursal);
			mapa.put("intIdSubSucursal",	intIdSubSucursal);
			lista = dao.getControlFondoFijo(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
}
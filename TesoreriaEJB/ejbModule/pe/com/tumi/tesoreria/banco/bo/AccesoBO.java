package pe.com.tumi.tesoreria.banco.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.tesoreria.banco.dao.AccesoDao;
import pe.com.tumi.tesoreria.banco.dao.impl.AccesoDaoIbatis;
import pe.com.tumi.tesoreria.banco.domain.Acceso;
import pe.com.tumi.tesoreria.banco.domain.AccesoId;
import pe.com.tumi.tesoreria.egreso.domain.ControlFondosFijos;


public class AccesoBO{

	private AccesoDao dao = (AccesoDao)TumiFactory.get(AccesoDaoIbatis.class);

	public Acceso grabar(Acceso o) throws BusinessException{
		Acceso dto = null;
		try{
		    dto = dao.grabar(o);
		}catch(DAOException e){
	  		throw new BusinessException(e);
	    }catch(Exception e){
	    	throw new BusinessException(e);
	    }
	    return dto;
	}

  	public Acceso modificar(Acceso o) throws BusinessException{
  		Acceso dto = null;
     	try{
		     dto = dao.modificar(o);
	  	}catch(DAOException e){
	  		 throw new BusinessException(e);
	    }catch(Exception e){
	    	 throw new BusinessException(e);
	    }
	    return dto;
    }
  
	public Acceso getPorPk(AccesoId pId) throws BusinessException{
		Acceso domain = null;
		List<Acceso> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaAcceso", pId.getIntPersEmpresaAcceso());
			mapa.put("intItemAcceso", pId.getIntItemAcceso());
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
	
	public List<Acceso> getPorBusqueda(Acceso o) throws BusinessException{
		List<Acceso> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaAcceso", o.getId().getIntPersEmpresaAcceso());
			mapa.put("intPersEmpresaSucursal", o.getIntPersEmpresaSucursal());
			mapa.put("intSucuIdSucursal", o.getIntSucuIdSucursal());
			mapa.put("intParaEstado", o.getIntParaEstado());
			mapa.put("intSudeIdSubsucursal", o.getIntSudeIdSubsucursal());
			lista = dao.getListaPorBusqueda(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<Acceso> getPorControlFondosFijos(ControlFondosFijos controlFondosFijos) throws BusinessException{
		List<Acceso> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaAcceso", controlFondosFijos.getId().getIntPersEmpresa());
			mapa.put("intPersEmpresaSucursal", controlFondosFijos.getId().getIntPersEmpresa());
			mapa.put("intSucuIdSucursal", controlFondosFijos.getId().getIntSucuIdSucursal());
			mapa.put("intParaEstado", Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			mapa.put("intSudeIdSubsucursal", controlFondosFijos.getIntSudeIdSubsucursal());
			lista = dao.getListaPorBusqueda(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
}
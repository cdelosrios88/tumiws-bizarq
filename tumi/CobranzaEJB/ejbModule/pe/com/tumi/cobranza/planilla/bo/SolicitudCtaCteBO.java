package pe.com.tumi.cobranza.planilla.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.cobranza.planilla.dao.SolicitudCtaCteDao;
import pe.com.tumi.cobranza.planilla.dao.impl.SolicitudCtaCteDaoIbatis;
import pe.com.tumi.cobranza.planilla.domain.SolicitudCtaCte;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;

public class SolicitudCtaCteBO{

	private SolicitudCtaCteDao dao = (SolicitudCtaCteDao)TumiFactory.get(SolicitudCtaCteDaoIbatis.class);

	public SolicitudCtaCte grabarSolicitudCtaCte(SolicitudCtaCte o) throws BusinessException{
		SolicitudCtaCte dto = null;
		try{
		    dto = dao.grabar(o);
		}catch(DAOException e){
	  		throw new BusinessException(e);
	    }catch(Exception e){
	    	throw new BusinessException(e);
	    }
	    return dto;
	}

  	public SolicitudCtaCte modificarSolicitudCtaCte(SolicitudCtaCte o) throws BusinessException{
     	SolicitudCtaCte dto = null;
     	try{
		     dto = dao.modificar(o);
	  	}catch(DAOException e){
	  		 throw new BusinessException(e);
	    }catch(Exception e){
	    	 throw new BusinessException(e);
	    }
	    return dto;
    }
	
	public List<SolicitudCtaCte> getListaSolicitudCtaCtePorEmpYSucTipSolYEstSol(Integer intEmpresasolctacte,Integer intSucuIdsucursalsocio,Integer intTipoSolicitud,Integer intEstadoSolicitud) throws BusinessException{
		List<SolicitudCtaCte> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intEmpresasolctacte", 	intEmpresasolctacte);
			mapa.put("intSucuIdsucursalsocio", 	intSucuIdsucursalsocio);
			mapa.put("intTipoSolicitud", 		intTipoSolicitud);
			mapa.put("intEstadoSolicitud", 	    intEstadoSolicitud);
			lista = dao.getListaFilrado(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<SolicitudCtaCte> getListaSolicitudCtaCtePorEmpYSucTipSolYEstSolAten(Integer intEmpresasolctacte,Integer intSucuIdsucursalsocio,Integer intTipoSolicitud,Integer intEstadoSolicitud) throws BusinessException{
		List<SolicitudCtaCte> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intEmpresasolctacte", 	intEmpresasolctacte);
			mapa.put("intSucuIdsucursalsocio", 	intSucuIdsucursalsocio);
			mapa.put("intTipoSolicitud", 		intTipoSolicitud);
			mapa.put("intEstadoSolicitud", 	    intEstadoSolicitud);
			lista = dao.getListaFilradoAtencion(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}




	public List<SolicitudCtaCte> getListaPorCuenta(
			Integer intEmpresasolctacte, Integer intCsocCuenta) throws BusinessException{
		List<SolicitudCtaCte> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intEmpresasolctacte", 	intEmpresasolctacte);
			mapa.put("intCsocCuenta", 	intCsocCuenta);

			lista = dao.getListaPorCuenta(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	

	
	
	
	}
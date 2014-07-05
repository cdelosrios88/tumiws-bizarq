package pe.com.tumi.cobranza.planilla.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;

import pe.com.tumi.cobranza.planilla.dao.SolicitudCtaCteTipoDao;
import pe.com.tumi.cobranza.planilla.dao.impl.SolicitudCtaCteTipoDaoIbatis;
import pe.com.tumi.cobranza.planilla.domain.SolicitudCtaCteTipo;
import pe.com.tumi.cobranza.planilla.domain.SolicitudCtaCteTipoId;
import pe.com.tumi.credito.socio.estructura.domain.EstructuraId;

public class SolicitudCtaCteTipoBO{

	private SolicitudCtaCteTipoDao dao = (SolicitudCtaCteTipoDao)TumiFactory.get(SolicitudCtaCteTipoDaoIbatis.class);

	public SolicitudCtaCteTipo grabarSolicitudCtaCteTipo(SolicitudCtaCteTipo o) throws BusinessException{
		SolicitudCtaCteTipo dto = null;
		try{
		    dto = dao.grabar(o);
		}catch(DAOException e){
	  		throw new BusinessException(e);
	    }catch(Exception e){
	    	throw new BusinessException(e);
	    }
	    return dto;
	}

  	public SolicitudCtaCteTipo modificarSolicitudCtaCteTipo(SolicitudCtaCteTipo o) throws BusinessException{
     	SolicitudCtaCteTipo dto = null;
     	try{
		     dto = dao.modificar(o);
	  	}catch(DAOException e){
	  		 throw new BusinessException(e);
	    }catch(Exception e){
	    	 throw new BusinessException(e);
	    }
	    return dto;
    }
  
	public SolicitudCtaCteTipo getSolicitudCtaCteTipoPorPk(SolicitudCtaCteTipoId pId) throws BusinessException{
		SolicitudCtaCteTipo domain = null;
		List<SolicitudCtaCteTipo> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresasolctacte", pId.getIntPersEmpresasolctacte());
			mapa.put("intCcobItemsolctacte", pId.getIntCcobItemsolctacte());
			mapa.put("intTipoSolicitudctacte", pId.getIntTipoSolicitudctacte());
			
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
	
	public List<SolicitudCtaCteTipo> getListaPorSolCtacteSinEstado(Integer intPersEmpresasolctacte,Integer intCcobItemsolctacte) throws BusinessException{
		List<SolicitudCtaCteTipo> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresasolctacte", intPersEmpresasolctacte);
			mapa.put("intCcobItemsolctacte", intCcobItemsolctacte);
			
			lista = dao.getListaPorSolCtacteSinEstado(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<SolicitudCtaCteTipo> getListaPorSolicitudCtacte(Integer intPersEmpresasolctacte,Integer intCcobItemsolctacte) throws BusinessException{
		List<SolicitudCtaCteTipo> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresasolctacte", intPersEmpresasolctacte);
			mapa.put("intCcobItemsolctacte", intCcobItemsolctacte);
			
			lista = dao.getListaPorSolicitudCtacte(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public void eliminarPorSolicitudCtacte(Integer intPersEmpresasolctacte,Integer intCcobItemsolctacte) throws BusinessException{
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresasolctacte", intPersEmpresasolctacte);
			mapa.put("intCcobItemsolctacte", intCcobItemsolctacte);
			 dao.eliminarPorSolicitudCtacte(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
	}
	

}
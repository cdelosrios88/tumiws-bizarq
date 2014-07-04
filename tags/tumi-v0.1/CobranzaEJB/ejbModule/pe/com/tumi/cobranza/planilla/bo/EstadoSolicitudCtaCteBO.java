package pe.com.tumi.cobranza.planilla.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;

import pe.com.tumi.cobranza.planilla.dao.EstadoSolicitudCtaCteDao;
import pe.com.tumi.cobranza.planilla.dao.impl.EstadoSolicitudCtaCteDaoIbatis;
import pe.com.tumi.cobranza.planilla.domain.EstadoSolicitudCtaCte;
import pe.com.tumi.cobranza.planilla.domain.EstadoSolicitudCtaCteId;
import pe.com.tumi.credito.socio.estructura.domain.EstructuraId;

public class EstadoSolicitudCtaCteBO{

	private EstadoSolicitudCtaCteDao dao = (EstadoSolicitudCtaCteDao)TumiFactory.get(EstadoSolicitudCtaCteDaoIbatis.class);

	public EstadoSolicitudCtaCte grabarEstadoSolicitudCtaCte(EstadoSolicitudCtaCte o) throws BusinessException{
		EstadoSolicitudCtaCte dto = null;
		try{
		    dto = dao.grabar(o);
		}catch(DAOException e){
	  		throw new BusinessException(e);
	    }catch(Exception e){
	    	throw new BusinessException(e);
	    }
	    return dto;
	}

  	public EstadoSolicitudCtaCte modificarEstadoSolicitudCtaCte(EstadoSolicitudCtaCte o) throws BusinessException{
     	EstadoSolicitudCtaCte dto = null;
     	try{
		     dto = dao.modificar(o);
	  	}catch(DAOException e){
	  		 throw new BusinessException(e);
	    }catch(Exception e){
	    	 throw new BusinessException(e);
	    }
	    return dto;
    }
  
	public EstadoSolicitudCtaCte getEstadoSolicitudCtaCtePorPk(EstadoSolicitudCtaCteId pId) throws BusinessException{
		EstadoSolicitudCtaCte domain = null;
		List<EstadoSolicitudCtaCte> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaSolctacte", pId.getIntPersEmpresaSolctacte());
			mapa.put("intCcobItemSolCtaCte", pId.getIntCcobItemSolCtaCte());
			mapa.put("intCcobItemEstado", pId.getIntCcobItemEstado());
			
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
	
	public List<EstadoSolicitudCtaCte> getListaPorSolicitudCtacte(Integer intPersEmpresasolctacte,Integer intCcobItemsolctacte) throws BusinessException{
		List<EstadoSolicitudCtaCte> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaSolctacte",intPersEmpresasolctacte);
			mapa.put("intCcobItemSolCtaCte",intCcobItemsolctacte);
			
			lista = dao.getListaPorSolicitudCtacte(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	

}
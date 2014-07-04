package pe.com.tumi.cobranza.planilla.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;

import pe.com.tumi.cobranza.planilla.dao.EnvioinfladaDao;
import pe.com.tumi.cobranza.planilla.dao.impl.EnvioinfladaDaoIbatis;
import pe.com.tumi.cobranza.planilla.domain.Envioinflada;
import pe.com.tumi.cobranza.planilla.domain.EnvioinfladaId;

public class EnvioinfladaBO{

	private EnvioinfladaDao dao = (EnvioinfladaDao)TumiFactory.get(EnvioinfladaDaoIbatis.class);

	public Envioinflada grabarEnvioinflada(Envioinflada o) throws BusinessException{
		Envioinflada dto = null;
		try{
		    dto = dao.grabar(o);
		}catch(DAOException e){
	  		throw new BusinessException(e);
	    }catch(Exception e){
	    	throw new BusinessException(e);
	    }
	    return dto;
	}

  	public Envioinflada modificarEnvioinflada(Envioinflada o) throws BusinessException{
     	Envioinflada dto = null;
     	try{
		     dto = dao.modificar(o);
	  	}catch(DAOException e){
	  		 throw new BusinessException(e);
	    }catch(Exception e){
	    	 throw new BusinessException(e);
	    }
	    return dto;
    }
  
	public Envioinflada getEnvioinfladaPorPk(EnvioinfladaId pId) throws BusinessException{
		Envioinflada domain = null;
		List<Envioinflada> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intItemenvioconcepto", pId.getIntItemenvioconcepto());
			mapa.put("intItemenvioinflada", pId.getIntItemenvioinflada());
			mapa.put("intItemenviomonto", pId.getIntItemenviomonto());
			mapa.put("intEmpresacuentaPk", pId.getIntEmpresacuentaPk());
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
	
	
	public List<Envioinflada> getListaPorEnvioMonto(Integer pIntItemenvioconcepto,Integer pIntItemenviomonto,Integer pIntEmpresacuentaPk) throws BusinessException{
		List<Envioinflada> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intItemenvioconcepto", pIntItemenvioconcepto);
			mapa.put("intItemenviomonto", pIntItemenviomonto);
			mapa.put("intEmpresacuentaPk", pIntEmpresacuentaPk);
			lista = dao.getListaPorEnvioMonto(mapa);
			
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	
}

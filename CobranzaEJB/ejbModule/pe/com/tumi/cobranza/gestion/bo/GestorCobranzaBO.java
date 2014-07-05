package pe.com.tumi.cobranza.gestion.bo;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;


import pe.com.tumi.cobranza.gestion.dao.GestorCobranzaDao;
import pe.com.tumi.cobranza.gestion.dao.impl.GestorCobranzaDaoIbatis;
import pe.com.tumi.cobranza.gestion.domain.GestorCobranza;
import pe.com.tumi.cobranza.gestion.domain.GestorCobranzaId;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;

public class GestorCobranzaBO {
	
protected  static Logger log = Logger.getLogger(GestorCobranzaBO.class);
private GestorCobranzaDao dao = (GestorCobranzaDao)TumiFactory.get(GestorCobranzaDaoIbatis.class);
	
	public List<GestorCobranza> getListaGestorCobranza(Object o) throws BusinessException{
		log.info("-----------------------Debugging GestorCobranzaBO.getListaGestorCobranza-----------------------------");
		List<GestorCobranza> lista = null;
		
		log.info("Seteando los parametros de busqueda de Gestor Cobranza: ");
		log.info("-------------------------------------------------");
		GestorCobranza gestorCobranza = (GestorCobranza)o;
		HashMap<String,Object> map = new HashMap<String,Object>();
		
		map.put("intPersEmpresaPk", gestorCobranza.getId().getIntPersEmpresaPk());
		map.put("intPersPersonaGestorPk", gestorCobranza.getId().getIntPersPersonaGestorPk());
		map.put("intItemGestorCobranzaSuc", gestorCobranza.getId().getIntItemGestorCobranzaSuc());
		
		try{
			lista = dao.getListaGestorCobranza(map);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}

	public GestorCobranza grabar(GestorCobranza o) throws BusinessException{
		GestorCobranza dto = null;
		try{
		    dto = dao.grabar(o);
		}catch(DAOException e){
	  		throw new BusinessException(e);
	    }catch(Exception e){
	    	throw new BusinessException(e);
	    }
	    return dto;
	}

  	public GestorCobranza modificar(GestorCobranza o) throws BusinessException{
  		GestorCobranza dto = null;
     	try{
		     dto = dao.modificar(o);
	  	}catch(DAOException e){
	  		 throw new BusinessException(e);
	    }catch(Exception e){
	    	 throw new BusinessException(e);
	    }
	    return dto;
    }
  
	public GestorCobranza getPorPk(GestorCobranzaId GestorCobranzaId) throws BusinessException{
		GestorCobranza domain = null;
		List<GestorCobranza> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaPk", GestorCobranzaId.getIntPersEmpresaPk());
			mapa.put("intPersPersonaGestorPk", GestorCobranzaId.getIntPersPersonaGestorPk());
			mapa.put("intItemGestorCobranzaSuc", GestorCobranzaId.getIntItemGestorCobranzaSuc());
			
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
	
	public GestorCobranza getPorPersona(GestorCobranza gestorCobranza) throws BusinessException {
		List<GestorCobranza> lista = null;
		GestorCobranza domain = null;
		try{
			HashMap<String,Object> map = new HashMap<String,Object>();		
			map.put("intPersEmpresaPk", gestorCobranza.getId().getIntPersEmpresaPk());
			map.put("intPersPersonaGestorPk", gestorCobranza.getId().getIntPersPersonaGestorPk());
			map.put("dtFechaActual", gestorCobranza.getDtFechaActual());
			System.out.println("--getPorPersonaIN");
			System.out.println(dao);
			lista = dao.getPorPersona(map);
			System.out.println("--getPorPersonaFIN");
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
		}catch(BusinessException e) {
			throw e;
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return domain;
	}
}

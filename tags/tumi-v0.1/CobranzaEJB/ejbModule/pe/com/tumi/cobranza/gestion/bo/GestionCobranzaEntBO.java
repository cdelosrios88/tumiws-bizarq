package pe.com.tumi.cobranza.gestion.bo;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.cobranza.gestion.dao.GestionCobranzaEntDao;
import pe.com.tumi.cobranza.gestion.dao.impl.GestionCobranzaEntDaoIbatis;
import pe.com.tumi.cobranza.gestion.domain.GestionCobranzaEnt;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;

public class GestionCobranzaEntBO {
	
protected  static Logger log = Logger.getLogger(GestionCobranzaEntBO.class);
private GestionCobranzaEntDao dao = (GestionCobranzaEntDao)TumiFactory.get(GestionCobranzaEntDaoIbatis.class);
	
	public List<GestionCobranzaEnt> getListaGestionCobranzaEnt(Object o) throws BusinessException{
		log.info("-----------------------Debugging GestionCobranzaEntBO.getListaGestionCobranzaEnt-----------------------------");
		List<GestionCobranzaEnt> lista = null;
		
		log.info("Seteando los parametros de busqueda de getListaGestionCobranzaEnt: ");
		log.info("-------------------------------------------------");
		GestionCobranzaEnt gestionCobranzaEnt = (GestionCobranzaEnt)o;
		HashMap map = new HashMap();
		
		
		
		map.put("intPersEmpresaGestion", gestionCobranzaEnt.getId().getIntPersEmpresaGestion());
		map.put("intItemGestionCobranza", gestionCobranzaEnt.getId().getIntItemGestionCobranza());
		try{
			lista = dao.getListaGestionCobranzaEnt(map);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public GestionCobranzaEnt getGestionCobranzaEntPorPK(GestionCobranzaEnt o) throws BusinessException {
		log.info("-----------------------Debugging GestionCobranzaEntBO.getGestionCobranzaEntPorPK-----------------------------");
		List<GestionCobranzaEnt> lista = null;
		GestionCobranzaEnt GestionCobranzaEnt = null;
		
		log.info("Seteando los parametros de busqueda de getGestionCobranzaEntPorPK : ");
		log.info("-------------------------------------------------");
		HashMap map = new HashMap();
		
		  
		
		
		map.put("intPersEmpresaGestion", o.getId().getIntPersEmpresaGestion());
		map.put("intItemGestionCobranza", o.getId().getIntItemGestionCobranza());
		map.put("intItemGestCobrEntidad", o.getId().getIntItemGestCobrEntidad());
		
		
		try{
			lista = dao.getGestionCobranzaEntPorPK(map);
			if(lista!=null){
				if(lista.size()==1){
				   GestionCobranzaEnt = lista.get(0);
				}else if(lista.size()==0){
				   GestionCobranzaEnt = null;
				}else{
				   throw new BusinessException("Obtención de mas de un registro coincidente");
				}
			}
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return GestionCobranzaEnt;
	}
	
	public GestionCobranzaEnt grabarGestionCobranzaEnt(GestionCobranzaEnt o) throws BusinessException {
		GestionCobranzaEnt dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public GestionCobranzaEnt modificarGestionCobranzaEnt(GestionCobranzaEnt o) throws BusinessException {
		GestionCobranzaEnt dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
}

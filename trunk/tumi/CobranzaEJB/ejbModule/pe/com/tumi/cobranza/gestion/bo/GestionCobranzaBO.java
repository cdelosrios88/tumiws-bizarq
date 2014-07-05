package pe.com.tumi.cobranza.gestion.bo;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.cobranza.gestion.dao.GestionCobranzaDao;
import pe.com.tumi.cobranza.gestion.dao.impl.GestionCobranzaDaoIbatis;
import pe.com.tumi.cobranza.gestion.domain.GestionCobranza;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;

public class GestionCobranzaBO {
	
protected  static Logger log = Logger.getLogger(GestionCobranzaBO.class);
private GestionCobranzaDao dao = (GestionCobranzaDao)TumiFactory.get(GestionCobranzaDaoIbatis.class);
	
	public List<GestionCobranza> getListaGestionCobranza(Object o) throws BusinessException{
		log.info("-----------------------Debugging GestionCobranzaBO.getListaGestionCobranza-----------------------------");
		List<GestionCobranza> lista = null;
		
		log.info("Seteando los parametros de busqueda de Gestion Cobranza: ");
		log.info("-------------------------------------------------");
		GestionCobranza gestionCobranza = (GestionCobranza)o;
		HashMap map = new HashMap();
		
		map.put("intPersEmpresaGestionPK", gestionCobranza.getId().getIntPersEmpresaGestionPK());
		map.put("intIdPersonaGestor", gestionCobranza.getIntIdPersonaGestor());
		map.put("intTipoGestionCobCod", gestionCobranza.getIntTipoGestionCobCod());
		map.put("dtFechaGestionIni", gestionCobranza.getDtFechaGestionIni());
		map.put("dtFechaGestionFin", gestionCobranza.getDtFechaGestionFin());
		map.put("intEstado", gestionCobranza.getIntEstado());
		
		
		try{
			lista = dao.getListaGestionCobranza(map);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public GestionCobranza getGestionCobranzaPorPK(GestionCobranza o) throws BusinessException {
		log.info("-----------------------Debugging GestionCobranzaBO.getGestionCobranzaPorPK-----------------------------");
		List<GestionCobranza> lista = null;
		GestionCobranza GestionCobranza = null;
		
		log.info("Seteando los parametros de busqueda de getGestionCobranzaPorPK : ");
		log.info("-------------------------------------------------");
		HashMap map = new HashMap();
		
		map.put("intIdEmpresa", o.getId().getIntPersEmpresaGestionPK());
		map.put("intItemGestionCobranza", o.getId().getIntItemGestionCobranza());
		
		try{
			lista = dao.getGestionCobranzaPorPK(map);
			if(lista!=null){
				if(lista.size()==1){
				   GestionCobranza = lista.get(0);
				}else if(lista.size()==0){
				   GestionCobranza = null;
				}else{
				   throw new BusinessException("Obtención de mas de un registro coincidente");
				}
			}
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return GestionCobranza;
	}
	
	public GestionCobranza grabarGestionCobranza(GestionCobranza o) throws BusinessException {
		GestionCobranza dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public GestionCobranza modificarGestionCobranza(GestionCobranza o) throws BusinessException {
		GestionCobranza dto = null;
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

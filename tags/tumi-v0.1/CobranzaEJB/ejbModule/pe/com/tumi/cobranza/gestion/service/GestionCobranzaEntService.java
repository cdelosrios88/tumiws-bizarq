package pe.com.tumi.cobranza.gestion.service;

import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.cobranza.gestion.bo.GestionCobranzaEntBO;
import pe.com.tumi.cobranza.gestion.domain.GestionCobranza;
import pe.com.tumi.cobranza.gestion.domain.GestionCobranzaEnt;
import pe.com.tumi.cobranza.gestion.domain.GestionCobranzaEntId;
import pe.com.tumi.common.util.Constante;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;

public class GestionCobranzaEntService {
	
	protected  static Logger log = Logger.getLogger(GestionCobranzaEntService.class);
	private GestionCobranzaEntBO boGestionCobranzaEnt = (GestionCobranzaEntBO)TumiFactory.get(GestionCobranzaEntBO.class);
	
	
	
	
	public List<GestionCobranzaEnt> grabarDinamicoListaGestionCobranzaEnt(GestionCobranza o) throws BusinessException {
		List<GestionCobranzaEnt> listaGestionCobranzaEnt = null;
		GestionCobranzaEnt gestionCobranzaEnt = null;
		GestionCobranzaEnt gestionCobranzaEntTemp = null;
		
		try{
			listaGestionCobranzaEnt = o.getListaGestionCobranzaEnt();
			for(int i=0; i<listaGestionCobranzaEnt.size(); i++){
				gestionCobranzaEnt = listaGestionCobranzaEnt.get(i);
				log.info("GestionCobranzaEnt.intEstadoCod: "+gestionCobranzaEnt.getIntEstadoCod());
				log.info("GestionCobranzaEnt.intNivel: "+gestionCobranzaEnt.getIntNivel());
				log.info("GestionCobranzaEnt.id: "+gestionCobranzaEnt.getId());
				if(gestionCobranzaEnt.getId().getIntItemGestCobrEntidad() == null){
					gestionCobranzaEnt.setId(new GestionCobranzaEntId());
					gestionCobranzaEnt.getId().setIntPersEmpresaGestion(o.getId().getIntPersEmpresaGestionPK());
					gestionCobranzaEnt.getId().setIntItemGestionCobranza(o.getId().getIntItemGestionCobranza());
					gestionCobranzaEnt.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
					boGestionCobranzaEnt.grabarGestionCobranzaEnt(gestionCobranzaEnt);
				}else{
					gestionCobranzaEntTemp = boGestionCobranzaEnt.getGestionCobranzaEntPorPK(gestionCobranzaEnt);
					if(gestionCobranzaEntTemp == null){
						gestionCobranzaEnt = boGestionCobranzaEnt.grabarGestionCobranzaEnt(gestionCobranzaEnt);
					}else{
						gestionCobranzaEnt = boGestionCobranzaEnt.modificarGestionCobranzaEnt(gestionCobranzaEnt);
					}
				}
				log.info("GestionCobranzaEnt.id.intItemGestCobrEntidadPK: "+gestionCobranzaEnt.getId().getIntItemGestCobrEntidad());
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return listaGestionCobranzaEnt;
	}
	
	public void eliminarListaGestionCobranzaEnt(GestionCobranzaEnt gestionCobranzaEnt) throws BusinessException {
		
		GestionCobranzaEnt gestionCobranzaEntTemp = null;
		try{
				log.info("GestionCobranzaEnt.intEstadoCod: "+gestionCobranzaEnt.getIntEstadoCod());
				log.info("GestionCobranzaEnt.intNivel: "+gestionCobranzaEnt.getIntNivel());
				log.info("GestionCobranzaEnt.id: "+gestionCobranzaEnt.getId());
			
				 gestionCobranzaEntTemp = boGestionCobranzaEnt.getGestionCobranzaEntPorPK(gestionCobranzaEnt);
				if(gestionCobranzaEntTemp != null && gestionCobranzaEnt.getIsDeleted() == true){
				   gestionCobranzaEntTemp.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
					gestionCobranzaEnt = boGestionCobranzaEnt.modificarGestionCobranzaEnt(gestionCobranzaEntTemp);
				}
				log.info("gestionCobranzaEnt.id.intItemGestCobrEntidadPk: "+gestionCobranzaEnt.getId().getIntItemGestCobrEntidad());
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		
	}
}

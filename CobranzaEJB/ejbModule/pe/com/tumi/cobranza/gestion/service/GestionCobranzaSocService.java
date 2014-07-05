package pe.com.tumi.cobranza.gestion.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.cobranza.gestion.bo.GestionCobranzaSocBO;
import pe.com.tumi.cobranza.gestion.domain.GestionCobranza;
import pe.com.tumi.cobranza.gestion.domain.GestionCobranzaEnt;
import pe.com.tumi.cobranza.gestion.domain.GestionCobranzaSoc;
import pe.com.tumi.cobranza.gestion.domain.GestionCobranzaSocId;
import pe.com.tumi.common.util.Constante;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.Cuenta;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.CuentaId;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.CuentaIntegrante;
import pe.com.tumi.credito.socio.aperturaCuenta.core.facade.CuentaFacadeRemote;
import pe.com.tumi.credito.socio.core.domain.SocioEstructura;
import pe.com.tumi.credito.socio.core.domain.SocioEstructuraPK;
import pe.com.tumi.credito.socio.core.domain.SocioPK;
import pe.com.tumi.credito.socio.core.facade.SocioFacadeRemote;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.servicio.solicitudPrestamo.domain.GarantiaCredito;
import pe.com.tumi.servicio.solicitudPrestamo.facade.SolicitudPrestamoFacadeRemote;

public class GestionCobranzaSocService {
	
	protected  static Logger log = Logger.getLogger(GestionCobranzaSocService.class);
	private GestionCobranzaSocBO boGestionCobranzaSoc = (GestionCobranzaSocBO)TumiFactory.get(GestionCobranzaSocBO.class);
	
	
	
	
	public List<GestionCobranzaSoc> grabarDinamicoListaGestionCobranzaSoc(GestionCobranza o) throws BusinessException {
		List<GestionCobranzaSoc> listaGestionCobranzaSoc = null;
		GestionCobranzaSoc gestionCobranzaSoc = null;
		GestionCobranzaSoc gestionCobranzaSocTemp = null;
		
		try{
			listaGestionCobranzaSoc = o.getListaGestionCobranzaSoc();
			for(int i=0; i<listaGestionCobranzaSoc.size(); i++){
				gestionCobranzaSoc = listaGestionCobranzaSoc.get(i);
				log.info("GestionCobranzaSoc.intPersEmpresa: "+gestionCobranzaSoc.getIntPersEmpresa());
				log.info("GestionCobranzaSoc.IntPersPersona: "+gestionCobranzaSoc.getIntPersPersona());
				log.info("GestionCobranzaSoc.id: "+gestionCobranzaSoc.getId());
				if(gestionCobranzaSoc.getId().getIntItemGestCobrSocio() == null){
					gestionCobranzaSoc.setId(new GestionCobranzaSocId());
					gestionCobranzaSoc.getId().setIntPersEmpresaGestion(o.getId().getIntPersEmpresaGestionPK());
					gestionCobranzaSoc.getId().setIntItemGestionCobranza(o.getId().getIntItemGestionCobranza());
					gestionCobranzaSoc.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
					boGestionCobranzaSoc.grabarGestionCobranzaSoc(gestionCobranzaSoc);
				}else{
					gestionCobranzaSocTemp = boGestionCobranzaSoc.getGestionCobranzaSocPorPK(gestionCobranzaSoc);
					if(gestionCobranzaSocTemp == null){
						gestionCobranzaSoc = boGestionCobranzaSoc.grabarGestionCobranzaSoc(gestionCobranzaSoc);
					}else{
						gestionCobranzaSoc = boGestionCobranzaSoc.modificarGestionCobranzaSoc(gestionCobranzaSoc);
					}
				}
				log.info("GestionCobranzaSoc.id.intItemGestCobrSocidadPK: "+gestionCobranzaSoc.getId().getIntItemGestCobrSocio());
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return listaGestionCobranzaSoc;
	}
	
	public void eliminarListaGestionCobranzaSoc(GestionCobranzaSoc gestionCobranzaSoc) throws BusinessException {
		
		GestionCobranzaSoc gestionCobranzaSocTemp = null;
		try{
				log.info("GestionCobranzaSoc.intPersEmpresa: "+gestionCobranzaSoc.getIntPersEmpresa());
				log.info("GestionCobranzaSoc.IntPersPersona: "+gestionCobranzaSoc.getIntPersPersona());
				log.info("GestionCobranzaSoc.id: "+gestionCobranzaSoc.getId());
			
				 gestionCobranzaSocTemp = boGestionCobranzaSoc.getGestionCobranzaSocPorPK(gestionCobranzaSoc);
				if(gestionCobranzaSocTemp != null){
				   gestionCobranzaSocTemp.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
					gestionCobranzaSoc = boGestionCobranzaSoc.modificarGestionCobranzaSoc(gestionCobranzaSocTemp);
				}
				log.info("gestionCobranzaSoc.id.intItemGestCobrSocidadPk: "+gestionCobranzaSoc.getId().getIntItemGestCobrSocio());
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		
	}
	
	public List<GestionCobranzaSoc> getListaGestionCobranzaSoc(GestionCobranzaSoc o) throws BusinessException {
		log.info("-----------------------Debugging getListaGestionCobranzaSoc-----------------------------");
		List<GestionCobranzaSoc> listGestionCobranzaSoc  = new ArrayList<GestionCobranzaSoc>();
		try{
			
			SocioFacadeRemote socioFacadeRemote = (SocioFacadeRemote)EJBFactory.getRemote(SocioFacadeRemote.class);
			CuentaFacadeRemote cuentaFacade = (CuentaFacadeRemote)EJBFactory.getRemote(CuentaFacadeRemote.class);
			SolicitudPrestamoFacadeRemote facadeSolPres = (SolicitudPrestamoFacadeRemote)EJBFactory.getRemote(SolicitudPrestamoFacadeRemote.class);
			
			for (GestionCobranzaSoc gestionCobranzaSoc :  boGestionCobranzaSoc.getListaGestionCobranzaSoc(o)) {
				SocioPK socioPK = new SocioPK();
				socioPK.setIntIdEmpresa(gestionCobranzaSoc.getIntPersEmpresa());
				socioPK.setIntIdPersona(gestionCobranzaSoc.getIntPersPersona());
				gestionCobranzaSoc.setSocio(socioFacadeRemote.getSocioNatural(socioPK).getSocio());
				List<SocioEstructura> listaSocEs = socioFacadeRemote.getListaSocioEstrucuraPorIdPersona(socioPK.getIntIdPersona(), socioPK.getIntIdEmpresa());
				if (listaSocEs != null && listaSocEs.size() > 0){
					gestionCobranzaSoc.getSocio().setSocioEstructura((SocioEstructura)listaSocEs.get(0));
				}
				gestionCobranzaSoc.setSocioComp(socioFacadeRemote.getSocioNatural(socioPK));
				
				if  (gestionCobranzaSoc.getIntTipoPersonaOpe() != null){
					if (gestionCobranzaSoc.getIntTipoPersonaOpe().equals(Constante.PARAM_T_TIPO_SOCIO_TITTULAR)){
					   List<CuentaIntegrante> listaCueInt = cuentaFacade.getCuentaIntegrantePorIdPersona(gestionCobranzaSoc.getSocioComp().getSocio().getId().getIntIdPersona(),gestionCobranzaSoc.getSocioComp().getSocio().getId().getIntIdEmpresa());
		    		
					
						    if (listaCueInt != null && listaCueInt.size() > 0){
						    	for (CuentaIntegrante cuentaInt : listaCueInt) {
						    		if (cuentaInt.getId().getIntCuenta().equals(gestionCobranzaSoc.getIntCsocCuenta()) && 
						    			cuentaInt.getId().getIntPersEmpresaPk().equals(gestionCobranzaSoc.getIntPersEmpresa())){
							    	      CuentaId cuentaId = new CuentaId();
								    	  cuentaId.setIntCuenta(cuentaInt.getId().getIntCuenta());
								    	  cuentaId.setIntPersEmpresaPk(cuentaInt.getId().getIntPersEmpresaPk());
								    	  Cuenta cuenta= (Cuenta)cuentaFacade.getCuentaPorId(cuentaId);
								    	  gestionCobranzaSoc.getSocioComp().setCuenta(cuenta);
								    	  break;
						    		}
								}
			                }
					 }else{
						 
							List<GarantiaCredito> listaGaratiaCredito =  facadeSolPres.getListaGarantiasPorPkPersona(gestionCobranzaSoc.getSocioComp().getSocio().getId().getIntIdEmpresa(), gestionCobranzaSoc.getSocioComp().getSocio().getId().getIntIdPersona());
							for (GarantiaCredito garantiaCredito : listaGaratiaCredito) {
								Integer nroCuenta   =  garantiaCredito.getId().getIntCuentaPk();
								Integer itemExped   =  garantiaCredito.getId().getIntItemExpediente();
								Integer itemDetExped =  garantiaCredito.getId().getIntItemDetExpediente();
								
								if (gestionCobranzaSoc.getIntCserItemExp().equals(itemExped) && 
									  gestionCobranzaSoc.getIntCserdeteItemExp().equals(itemDetExped)	&&
									  gestionCobranzaSoc.getIntCsocCuenta().equals(nroCuenta)){
									  CuentaId cuentaId = new CuentaId();
							    	  cuentaId.setIntCuenta(nroCuenta);
							    	  cuentaId.setIntPersEmpresaPk(gestionCobranzaSoc.getSocioComp().getSocio().getId().getIntIdEmpresa());
							    	  Cuenta cuenta= (Cuenta)cuentaFacade.getCuentaPorId(cuentaId);
							    	  gestionCobranzaSoc.getSocioComp().setCuenta(cuenta);
							    	  break;
								}
							}
					 }
				}
				 
				listGestionCobranzaSoc.add(gestionCobranzaSoc);
			}
		
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return listGestionCobranzaSoc;
	}
	
    public void eliminarGestionCobranzaSoc(GestionCobranzaSoc gestionCobranzaSoc) throws BusinessException {
		
		GestionCobranzaSoc gestionCobranzaSocTemp = null;
		try{
				log.info("GestionCobranzaSoc.getIntPersEmpresa: "+gestionCobranzaSoc.getIntPersEmpresa());
				log.info("GestionCobranzaSoc.getIntPersPersona: "+gestionCobranzaSoc.getIntPersPersona());
				log.info("GestionCobranzaSoc.id: "+gestionCobranzaSoc.getId());
			
				 gestionCobranzaSocTemp = boGestionCobranzaSoc.getGestionCobranzaSocPorPK(gestionCobranzaSoc);
				if(gestionCobranzaSocTemp != null && gestionCobranzaSoc.getIsDeleted() == true){
				   gestionCobranzaSocTemp.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
					gestionCobranzaSoc = boGestionCobranzaSoc.modificarGestionCobranzaSoc(gestionCobranzaSocTemp);
				}
				log.info("gestionCobranzaSoc.id.intItemGestCobrSocidadPk: "+gestionCobranzaSoc.getId().getIntItemGestCobrSocio());
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		
	}
}

package pe.com.tumi.tesoreria.logistica.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;


import pe.com.tumi.common.util.Constante;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.persona.core.domain.PersonaRol;
import pe.com.tumi.persona.core.domain.PersonaRolPK;
import pe.com.tumi.persona.core.facade.PersonaFacadeRemote;
import pe.com.tumi.tesoreria.logistica.bo.ProveedorBO;
import pe.com.tumi.tesoreria.logistica.bo.ProveedorDetalleBO;
import pe.com.tumi.tesoreria.logistica.domain.Proveedor;
import pe.com.tumi.tesoreria.logistica.domain.ProveedorDetalle;


public class ProveedorService {

	protected static Logger log = Logger.getLogger(ProveedorService.class);
	
	ProveedorBO boProveedor = (ProveedorBO)TumiFactory.get(ProveedorBO.class);
	ProveedorDetalleBO boProveedorDetalle = (ProveedorDetalleBO)TumiFactory.get(ProveedorDetalleBO.class);
	
	
	public Proveedor grabarProveedor(Proveedor proveedor) throws BusinessException{
		try{
			PersonaFacadeRemote personaFacade =(PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
			
			log.info(proveedor);
			boProveedor.grabar(proveedor);
			
			for(ProveedorDetalle proveedorDetalle : proveedor.getListaProveedorDetalle()){
				grabarProveedorDetalle(proveedorDetalle, proveedor);
			}
			
			//Si no posee Rol Proveedor, se le añade.
			Persona personaBus = personaFacade.getPersonaJuridicaPorIdPersonaYIdEmpresa(proveedor.getId().getIntPersPersona(), 
																						proveedor.getId().getIntPersEmpresa());
			boolean poseeRolProveedor = Boolean.FALSE;
			for(PersonaRol personaRol : personaBus.getPersonaEmpresa().getListaPersonaRol()){
				if(personaRol.getId().getIntParaRolPk().equals(Constante.PARAM_T_TIPOROL_PROVEEDOR) 
					&& personaRol.getIntEstadoCod().equals(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)){
					poseeRolProveedor = Boolean.TRUE;
				}
			}
			if(!poseeRolProveedor){
				PersonaRol personaRol = new PersonaRol();
				personaRol.setId(new PersonaRolPK());
				personaRol.getId().setIntIdEmpresa(proveedor.getId().getIntPersEmpresa());
				personaRol.getId().setIntIdPersona(proveedor.getId().getIntPersPersona());
				personaRol.getId().setIntParaRolPk(Constante.PARAM_T_TIPOROL_PROVEEDOR);
				personaRol.getId().setDtFechaInicio(new Date());
				personaRol.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
				
				personaFacade.grabarPersonaRol(personaRol);
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return proveedor;
	}
	
	public Proveedor modificarProveedor(Proveedor proveedor)throws BusinessException{
		try{
			List<ProveedorDetalle> listaProveedorDetalleIU = new ArrayList<ProveedorDetalle>();
			List<ProveedorDetalle> listaProveedorDetalleBD = boProveedorDetalle.getPorProveedor(proveedor);
			
			for(ProveedorDetalle proveedorDetalle : proveedor.getListaProveedorDetalle()){
				if(proveedorDetalle.getId().getIntItemProveedorDetalle()==null){
					grabarProveedorDetalle(proveedorDetalle, proveedor);
				}else{
					listaProveedorDetalleIU.add(proveedorDetalle);
				}
			}
			
			boolean seEncuentraEnIU;
			for(ProveedorDetalle proveedorDetalleBD : listaProveedorDetalleBD){
				seEncuentraEnIU = Boolean.FALSE;
				for(ProveedorDetalle proveedorDetalleIU : listaProveedorDetalleIU){
					if(proveedorDetalleBD.getId().getIntItemProveedorDetalle().equals(proveedorDetalleIU.getId().getIntItemProveedorDetalle())){
						seEncuentraEnIU = Boolean.TRUE;
						break;
					}
				}
				if(!seEncuentraEnIU){
					boProveedorDetalle.eliminar(proveedorDetalleBD);
				}
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return proveedor;
	}
	
	private void grabarProveedorDetalle(ProveedorDetalle proveedorDetalle, Proveedor proveedor) throws BusinessException{
		proveedorDetalle.getId().setIntPersEmpresa(proveedor.getId().getIntPersEmpresa());
		proveedorDetalle.getId().setIntPersPersona(proveedor.getId().getIntPersPersona());
		
		boProveedorDetalle.grabar(proveedorDetalle);
	}
}
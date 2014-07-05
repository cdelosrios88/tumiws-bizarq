package pe.com.tumi.tesoreria.logistica.service;


import java.util.List;

import org.apache.log4j.Logger;


import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.persona.core.facade.PersonaFacadeRemote;
import pe.com.tumi.tesoreria.logistica.bo.InformeGerenciaBO;
import pe.com.tumi.tesoreria.logistica.domain.InformeGerencia;


public class InformeGerenciaService {

	protected static Logger log = Logger.getLogger(InformeGerenciaService.class);
	
	InformeGerenciaBO boInformeGerencia = (InformeGerenciaBO)TumiFactory.get(InformeGerenciaBO.class);
	
	
	public InformeGerencia grabarInformeGerencia(InformeGerencia informeGerencia) throws BusinessException{
		try{
			log.info(informeGerencia);
			
			informeGerencia = boInformeGerencia.grabar(informeGerencia);			
			
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return informeGerencia;
	}

	
	public List<InformeGerencia> buscarInformeGerencia(InformeGerencia informeGerenciaFiltro) throws BusinessException{
		List<InformeGerencia> listaRequisicion = null;
		try{			
			log.info(informeGerenciaFiltro);
			listaRequisicion = boInformeGerencia.getPorBuscar(informeGerenciaFiltro);
		
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return listaRequisicion;
	}
	
	public Persona obtenerPersonaProveedorDeInformeGerencia(InformeGerencia informeGerencia) throws BusinessException{
		Persona personaProveedor = null;
		try{
			PersonaFacadeRemote personaFacade =  (PersonaFacadeRemote) EJBFactory.getRemote(PersonaFacadeRemote.class);
			
			Persona empresaServicio = personaFacade.getPersonaJuridicaPorIdPersona(informeGerencia.getIntPersPersonaServicio());
			empresaServicio.setStrEtiqueta(empresaServicio.getIntIdPersona()+"-"+empresaServicio.getJuridica().getStrRazonSocial()
					+"-RUC:"+empresaServicio.getStrRuc());
			informeGerencia.setEmpresaServicio(empresaServicio);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return personaProveedor;
	}
}
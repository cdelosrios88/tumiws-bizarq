package pe.com.tumi.tesoreria.logistica.service;


import java.util.List;

import org.apache.log4j.Logger;


import pe.com.tumi.common.util.Constante;
import pe.com.tumi.common.util.MyUtil;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.persona.core.facade.PersonaFacadeRemote;
import pe.com.tumi.tesoreria.logistica.bo.ContratoBO;
import pe.com.tumi.tesoreria.logistica.domain.Contrato;


public class ContratoService {

	protected static Logger log = Logger.getLogger(ContratoService.class);
	
	ContratoBO boContrato = (ContratoBO)TumiFactory.get(ContratoBO.class);
	
	
	public Contrato grabarContrato(Contrato contrato) throws BusinessException{
		try{
			contrato.setIntPagoUnico(0);
			contrato.setIntRenovacion(0);
			if(contrato.isSeleccionaPagoUnico())
				contrato.setIntPagoUnico(1);	
			
			if(contrato.isSeleccionaRenovacion())
				contrato.setIntRenovacion(1);
			
			
			log.info(contrato);
			
			contrato = boContrato.grabar(contrato);			
			
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return contrato;
	}

	
	public List<Contrato> buscarContrato(Contrato contratoFiltro) throws BusinessException{
		List<Contrato> listaContrato = null;
		try{			
			log.info(contratoFiltro);
			listaContrato = boContrato.getPorBuscar(contratoFiltro);
		
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return listaContrato;
	}
	
	public Persona obtenerPersonaProveedorDeContrato(Contrato contrato) throws BusinessException{
		Persona empresaServicio = null;
		try{
			PersonaFacadeRemote personaFacade =  (PersonaFacadeRemote) EJBFactory.getRemote(PersonaFacadeRemote.class);
			empresaServicio = personaFacade.getPersonaPorPK(contrato.getIntPersPersonaServicio());
			
			if(empresaServicio.getIntTipoPersonaCod().equals(Constante.PARAM_T_TIPOPERSONA_NATURAL)){
				empresaServicio = personaFacade.getPersonaNaturalPorIdPersona(contrato.getIntPersPersonaServicio());					
				MyUtil.agregarNombreCompleto(empresaServicio);
				MyUtil.agregarDocumentoDNI(empresaServicio);					
				empresaServicio.setStrEtiqueta(empresaServicio.getIntIdPersona()+"-"+empresaServicio.getNatural().getStrNombreCompleto()
						+"-DNI:"+empresaServicio.getDocumento().getStrNumeroIdentidad());					
			
			}else if(empresaServicio.getIntTipoPersonaCod().equals(Constante.PARAM_T_TIPOPERSONA_JURIDICA)){					
				empresaServicio = personaFacade.getPersonaJuridicaPorIdPersona(contrato.getIntPersPersonaServicio());
				empresaServicio.setStrEtiqueta(empresaServicio.getIntIdPersona()+"-"+empresaServicio.getJuridica().getStrRazonSocial()
						+"-RUC:"+empresaServicio.getStrRuc());
			}
			contrato.setEmpresaServicio(empresaServicio);			
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return empresaServicio;
	}	
}
package pe.com.tumi.persona.core.service;

import java.sql.Timestamp;

import org.apache.log4j.Logger;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.framework.util.fecha.JFecha;
import pe.com.tumi.persona.contacto.bo.ComunicacionBO;
import pe.com.tumi.persona.contacto.bo.DomicilioBO;
import pe.com.tumi.persona.contacto.service.ContactoService;
import pe.com.tumi.persona.core.bo.CuentaBancariaBO;
import pe.com.tumi.persona.core.bo.CuentaBancariaFinBO;
import pe.com.tumi.persona.core.domain.CuentaBancaria;
import pe.com.tumi.persona.core.domain.CuentaBancariaFin;
import pe.com.tumi.persona.core.domain.Persona;

public class PersonaDetalleService {
	
	protected  static Logger log = Logger.getLogger(PersonaDetalleService.class);
	private CuentaBancariaBO boCtaBancaria = (CuentaBancariaBO)TumiFactory.get(CuentaBancariaBO.class);
	private ContactoService contactoService = (ContactoService)TumiFactory.get(ContactoService.class);
	private FinanzasService finanzasService = (FinanzasService)TumiFactory.get(FinanzasService.class);
	private DomicilioBO boDomicilio = (DomicilioBO)TumiFactory.get(DomicilioBO.class);
	private ComunicacionBO boComunicacion = (ComunicacionBO)TumiFactory.get(ComunicacionBO.class);
	private CuentaBancariaFinBO boCtaBancariaFin = (CuentaBancariaFinBO)TumiFactory.get(CuentaBancariaFinBO.class);

	private Persona cargarListaCuentaBancariaFin(Persona persona) throws BusinessException{
		if(persona.getListaCuentaBancaria() != null){
			for(CuentaBancaria cuentaBancaria : persona.getListaCuentaBancaria()){
				log.info("cb:"+cuentaBancaria.getId().getIntIdPersona());
				log.info("cb:"+cuentaBancaria.getId().getIntIdCuentaBancaria());
				cuentaBancaria.setListaCuentaBancariaFin(boCtaBancariaFin.getListaPorCuentaBancaria(cuentaBancaria));
			}
		}
		return persona;
	}
	public void getDetalleDePersonaPorIdPersona(Persona persona,Integer intIdPersona) throws BusinessException {
		try{
			persona.setListaDomicilio(boDomicilio.getListaDomicilioPorIdPersona(persona.getIntIdPersona()));
			persona.setListaComunicacion(boComunicacion.getListaComunicacionPorIdPersona(persona.getIntIdPersona()));
			persona.setListaDocumento(contactoService.getListaDocumentoPorIdPersona(persona.getIntIdPersona()));
			persona.setListaCuentaBancaria(boCtaBancaria.getListaCuentaBancariaPorIdPersona(persona.getIntIdPersona()));
			/**Inicio parche para soportar CuentaBancariaFin**/
			log.info("persona:"+persona);
			persona = cargarListaCuentaBancariaFin(persona);
			for(CuentaBancaria cuentaBancaria : persona.getListaCuentaBancaria()){
				for(CuentaBancariaFin cuentaBancariaFin : cuentaBancaria.getListaCuentaBancariaFin()){
					log.info(cuentaBancariaFin);
				}
			}
			/***Fin Parche*/
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}	
	}
	
	public void grabarDinamicoDetalleDePersonaPorIdPersona(Persona persona,Integer intIdPersona) throws BusinessException {
		Timestamp lTsFechaEliminacion = null;
		try{
			lTsFechaEliminacion = JFecha.obtenerTimestampDeFechayHoraActual();
			if(persona.getListaComunicacion()!=null){
				contactoService.grabarListaDinamicaComunicacion(persona.getListaComunicacion(), intIdPersona,lTsFechaEliminacion);
			}
			if(persona.getListaDomicilio()!=null){
				contactoService.grabarListaDinamicaDomicilo(persona.getListaDomicilio(), intIdPersona,lTsFechaEliminacion);
			}
			if(persona.getListaDocumento()!=null){
				contactoService.grabarListaDinamicaDocumento(persona.getListaDocumento(), intIdPersona);
			}
			if(persona.getListaCuentaBancaria()!=null){
				finanzasService.grabarListaDinamicaCtaBancaria(persona.getListaCuentaBancaria(), intIdPersona);
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}		
	}
	
	public void eliminarDetalleDePersonaPorIdPersona(Integer intIdPersona) throws BusinessException {
		try{
			contactoService.eliminarDomiciloPorIdPersona(intIdPersona);
			contactoService.eliminarComunicacionPorIdPersona(intIdPersona);
			contactoService.eliminarDocumentoPorIdPersona(intIdPersona);
			finanzasService.eliminarCtaBancariaPorIdPersona(intIdPersona);			
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
	}
}

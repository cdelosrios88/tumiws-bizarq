package pe.com.tumi.contabilidad.legalizacion.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.contabilidad.legalizacion.bo.LibroLegalizacionBO;
import pe.com.tumi.contabilidad.legalizacion.bo.PagoLegalizacionBO;
import pe.com.tumi.contabilidad.legalizacion.domain.LibroLegalizacion;
import pe.com.tumi.contabilidad.legalizacion.domain.PagoLegalizacion;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;

public class LibroLegalizacionService {
	
	protected static Logger log = Logger.getLogger(LibroLegalizacionService.class);
	
	LibroLegalizacionBO boLibroLegalizacion = (LibroLegalizacionBO)TumiFactory.get(LibroLegalizacionBO.class);
	PagoLegalizacionBO boPagoLegalizacion = (PagoLegalizacionBO)TumiFactory.get(PagoLegalizacionBO.class);
	
	
	public List<PagoLegalizacion> obtenerPagoLegalizacionPorPersona(Integer intIdPersona, Integer intIdEmpresa) throws BusinessException{		
		List<PagoLegalizacion> listaPagoLegalizacion;
		try{
			listaPagoLegalizacion = new ArrayList<PagoLegalizacion>();
			List<LibroLegalizacion> listaLibroLegalizacion = boLibroLegalizacion.getPorIdPersona(intIdPersona, intIdEmpresa);
			List<PagoLegalizacion> listaPagoLegalizacionTemp = null;
			
			for(LibroLegalizacion libroLegalizacion : listaLibroLegalizacion){
				log.info(libroLegalizacion);
				listaPagoLegalizacionTemp = boPagoLegalizacion.getPorLibroLegalizacion(libroLegalizacion);
				
				for(PagoLegalizacion pagoLegalizacion : listaPagoLegalizacionTemp){
					log.info(pagoLegalizacion);
					if(pagoLegalizacion.getIntParaTipoDocumento().equals(Constante.PARAM_T_DOCUMENTOGENERAL_PAGOLEGALIZACIONLIBROS)){
						pagoLegalizacion.setLibroLegalizacion(libroLegalizacion);
						listaPagoLegalizacion.add(pagoLegalizacion);
					}
				}
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		
		return listaPagoLegalizacion;
	}
}
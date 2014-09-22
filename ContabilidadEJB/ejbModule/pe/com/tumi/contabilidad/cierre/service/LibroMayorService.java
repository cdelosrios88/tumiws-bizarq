package pe.com.tumi.contabilidad.cierre.service;

import org.apache.log4j.Logger;

import pe.com.tumi.contabilidad.cierre.bo.LibroMayorBO;
import pe.com.tumi.contabilidad.cierre.comp.LibroMayorComp;
import pe.com.tumi.contabilidad.cierre.domain.LibroMayor;
import pe.com.tumi.framework.negocio.factory.TumiFactory;

public class LibroMayorService {	

	protected static Logger log = Logger.getLogger(LibroMayorService.class);
	
	//LibroDiarioBO boLibroDiario = (LibroDiarioBO)TumiFactory.get(LibroDiarioBO.class);
	//LibroDiarioDetalleBO boLibroDiarioDetalle = (LibroDiarioDetalleBO)TumiFactory.get(LibroDiarioDetalleBO.class);
	LibroMayorBO boLibroMayor = (LibroMayorBO)TumiFactory.get(LibroMayorBO.class);
	
	/*public Integer processMayorizacion(Integer intPeriodo)throws Exception{
		LibroMayorComp libroMayorComp = null;
		Integer intIdLibroMayor = null;
		try{
			intIdLibroMayor = boLibroMayor.processMayorizacion(intPeriodo);
		} catch (Exception e){
			log.error(e.getMessage(), e);
		}
			
		return intIdLibroMayor;
	}*/
}
package pe.com.tumi.contabilidad.legalizacion.service;

import org.apache.log4j.Logger;
import pe.com.tumi.contabilidad.legalizacion.domain.LibroLegalizacion;
import pe.com.tumi.contabilidad.legalizacion.domain.LibroLegalizacionComp;
import pe.com.tumi.contabilidad.legalizacion.bo.LibroLegalizacionBO;
import pe.com.tumi.contabilidad.legalizacion.domain.LibroContableDetalle;
import pe.com.tumi.contabilidad.legalizacion.bo.LibroContableDetalleBO;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;

public class LibroLegalizacionService {
	
	protected static Logger log = Logger.getLogger(LibroLegalizacionService.class);
	
	LibroLegalizacionBO boLibroLegalizacion = (LibroLegalizacionBO)TumiFactory.get(LibroLegalizacionBO.class);
	LibroContableDetalleBO boLibroContableDetalle = (LibroContableDetalleBO)TumiFactory.get(LibroContableDetalleBO.class);

	public LibroLegalizacion grabarLibroLegalizacion(LibroLegalizacionComp o) throws BusinessException{
		LibroLegalizacion domainLegal = null;
		LibroContableDetalle domainLibro = null;
		try{
			domainLegal = o.getLibroLegalizacion();
			domainLegal = boLibroLegalizacion.grabar(domainLegal);
			if (domainLegal !=null && domainLegal.getId().getIntItemLibroLegalizacion()!=null) {
				if (o.getLibroContableDetalle().getId().getIntEmpresaPk() != null && o.getLibroContableDetalle().getId().getIntLibroContable() != null && o.getLibroContableDetalle().getId().getIntItemLibroContableDet() != null){
					log.info(domainLegal.getId().getIntItemLibroLegalizacion());
					o.getLibroContableDetalle().setIntItemLibroLegalizacion(domainLegal.getId().getIntItemLibroLegalizacion());
					domainLibro = o.getLibroContableDetalle();
					domainLibro = boLibroContableDetalle.modificar(domainLibro);
				}
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return domainLegal;
	}
}
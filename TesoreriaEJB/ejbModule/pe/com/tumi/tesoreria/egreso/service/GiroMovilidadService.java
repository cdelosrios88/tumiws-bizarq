package pe.com.tumi.tesoreria.egreso.service;

import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiarioDetalle;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.tesoreria.egreso.bo.MovilidadBO;
import pe.com.tumi.tesoreria.egreso.domain.Egreso;
import pe.com.tumi.tesoreria.egreso.domain.EgresoDetalle;
import pe.com.tumi.tesoreria.egreso.domain.Movilidad;
import pe.com.tumi.tesoreria.egreso.facade.EgresoFacadeLocal;

public class GiroMovilidadService {
	
	protected static Logger log = Logger.getLogger(GiroMovilidadService.class);
	
	MovilidadBO boMovilidad = (MovilidadBO)TumiFactory.get(MovilidadBO.class);

	public Egreso grabarGiroMovilidad(Egreso egreso, List<Movilidad> listaMovilidad)throws BusinessException{
		try{
			EgresoFacadeLocal egresoFacade = (EgresoFacadeLocal) EJBFactory.getLocal(EgresoFacadeLocal.class);
			
			log.info(egreso);
			for(EgresoDetalle egresoDetalle : egreso.getListaEgresoDetalle()){
				log.info(egresoDetalle);
			}
			log.info(egreso.getLibroDiario());
			for(LibroDiarioDetalle libroDiarioDetalle : egreso.getLibroDiario().getListaLibroDiarioDetalle()){
				log.info(libroDiarioDetalle);
			}
			
			egreso = egresoFacade.grabarEgresoParaGiroPrestamo(egreso);
			
			for(Movilidad movilidad : listaMovilidad){
				movilidad.setIntParaEstadoPago(Constante.PARAM_T_ESTADOPAGO_CANCELADO);
				movilidad.setIntPersEmpresaEgreso(egreso.getId().getIntPersEmpresaEgreso());
				movilidad.setIntItemEgresoGeneral(egreso.getId().getIntItemEgresoGeneral());
				boMovilidad.modificar(movilidad);
			}
			
		}catch(Exception e){
			throw new BusinessException(e);
		}		
		return egreso;
	}
}
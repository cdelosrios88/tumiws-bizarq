package pe.com.tumi.presupuesto.indicador.service;

import org.apache.log4j.Logger;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.presupuesto.indicador.bo.IndicadorBO;
import pe.com.tumi.presupuesto.indicador.domain.Indicador;

public class IndicadorService {
	protected static Logger log = Logger.getLogger(IndicadorService.class);
	private IndicadorBO boIndicador = (IndicadorBO)TumiFactory.get(IndicadorBO.class);
	
	//AUTOR Y FECHA CREACION: JCHAVEZ / 10.10.2013
	public Indicador grabarIndicador(Indicador indicador) throws BusinessException{
		Indicador ind = null;
		try{
			ind  = boIndicador.grabar(indicador);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return ind ;
	}
}

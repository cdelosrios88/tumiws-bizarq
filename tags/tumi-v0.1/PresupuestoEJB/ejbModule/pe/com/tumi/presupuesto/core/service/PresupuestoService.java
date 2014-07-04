package pe.com.tumi.presupuesto.core.service;

import org.apache.log4j.Logger;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.presupuesto.core.bo.PresupuestoBO;
import pe.com.tumi.presupuesto.core.domain.Presupuesto;

public class PresupuestoService {
	protected static Logger log = Logger.getLogger(PresupuestoService.class);
	private PresupuestoBO boPresupuesto = (PresupuestoBO)TumiFactory.get(PresupuestoBO.class);
	
	//AUTOR Y FECHA CREACION: JCHAVEZ / 10.10.2013
	public Presupuesto grabarPresupuesto(Presupuesto presupuesto) throws BusinessException{
		Presupuesto psto = null;
		try{
			psto  = boPresupuesto.grabar(presupuesto);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return psto ;
	}
}


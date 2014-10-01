package pe.com.tumi.contabilidad.core.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.contabilidad.cierre.bo.LibroMayorBO;
import pe.com.tumi.contabilidad.cierre.domain.LibroMayor;
import pe.com.tumi.contabilidad.cierreContabilidad.bo.CierreContabilidadBo;
import pe.com.tumi.contabilidad.cierreContabilidad.domain.CierreContabilidad;
import pe.com.tumi.contabilidad.cierreContabilidad.domain.CierreContabilidadId;
import pe.com.tumi.contabilidad.core.bo.PlanCuentaBO;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;

public class MayorizacionService {
	protected static Logger log = Logger.getLogger(MayorizacionService.class);
	PlanCuentaBO boPlanCuenta = (PlanCuentaBO)TumiFactory.get(PlanCuentaBO.class);
	CierreContabilidadBo boCierreContabilidad = (CierreContabilidadBo)TumiFactory.get(CierreContabilidadBo.class);
	LibroMayorBO boLibroMayor = (LibroMayorBO)TumiFactory.get(LibroMayorBO.class);
	
	public Integer processMayorizacion(LibroMayor o, String strPeriodo) 
			throws BusinessException{
		Integer intResult = null;
		CierreContabilidad cierreContabilidad = null;
		List<CierreContabilidad> lstCierreContabilidad = null;
		LibroMayor libroMayor = null;
		try{
			cierreContabilidad = new CierreContabilidad();
			cierreContabilidad.setId(new CierreContabilidadId());
			cierreContabilidad.getId().setIntPersEmpresaCieCob(o.getId().getIntPersEmpresaMayor());
			cierreContabilidad.getId().setIntCcobPeriodoCierre(Integer.valueOf(strPeriodo));
			lstCierreContabilidad = boCierreContabilidad.getListaCierre(cierreContabilidad);
			if(lstCierreContabilidad!=null && !lstCierreContabilidad.isEmpty()){
				cierreContabilidad = (CierreContabilidad) lstCierreContabilidad.get(Constante.INT_ZERO);
				o.setIntParaEstadoCierreCod(cierreContabilidad.getId().getIntEstadoCierreCod());
			}else{
				o.setIntParaEstadoCierreCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			}
			o.setTsFechaRegistro(new Timestamp(new Date().getTime()));
			o.setIntEstadoCod(Integer.valueOf(Constante.PARAM_T_TIPOESTADOMAYORIZACION_REGISTRADO));
			
			libroMayor = boLibroMayor.grabar(o);
			intResult = boLibroMayor.processMayorizacion(libroMayor, strPeriodo);
			
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return intResult;
	}
}
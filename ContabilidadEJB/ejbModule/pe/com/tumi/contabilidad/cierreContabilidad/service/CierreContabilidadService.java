package pe.com.tumi.contabilidad.cierreContabilidad.service;

import pe.com.tumi.contabilidad.cierreContabilidad.bo.CierreContabilidadBo;
import pe.com.tumi.contabilidad.cierreContabilidad.domain.CierreContabilidad;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
//Autor: Rodolfo Villarreal / Tarea: Creación / Fecha: 19.08.2014 /
public class CierreContabilidadService {
	CierreContabilidadBo boCierreContabilidadBO = (CierreContabilidadBo)TumiFactory.get(CierreContabilidadBo.class);
	
	public CierreContabilidad grabarCierreContabilidad(CierreContabilidad o) throws Exception{
		CierreContabilidad domain = null;
		
		domain = boCierreContabilidadBO.grabarCierreContabilidad(o);
		
		return domain;
		}

}

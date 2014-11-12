package pe.com.tumi.contabilidad.reclamosDevoluciones.service;

import pe.com.tumi.contabilidad.reclamosDevoluciones.bo.ReclamosDevolucionesBo;
import pe.com.tumi.contabilidad.reclamosDevoluciones.domain.ReclamosDevoluciones;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
//Autor: Rodolfo Villarreal / Tarea: Creación / Fecha: 08.08.2014 /
public class ReclamosDevolucionesService {
	ReclamosDevolucionesBo boReclamosDevoluciones = (ReclamosDevolucionesBo)TumiFactory.get(ReclamosDevolucionesBo.class);
	public ReclamosDevoluciones grabarReclamos(ReclamosDevoluciones o) throws Exception{
		ReclamosDevoluciones domain = null;
		
		domain = boReclamosDevoluciones.grabarReclamaciones(o);
		
		
		return domain;
		}

}

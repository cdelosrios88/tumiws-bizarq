package pe.com.tumi.contabilidad.perdidasSiniestro.service;

import pe.com.tumi.contabilidad.perdidasSiniestro.bo.PerdidasSiniestroBo;
import pe.com.tumi.contabilidad.perdidasSiniestro.domain.PerdidasSiniestro;
import pe.com.tumi.framework.negocio.factory.TumiFactory;

public class PerdidasSiniestroService {
	PerdidasSiniestroBo boPerdidasSiniestro = (PerdidasSiniestroBo)TumiFactory.get(PerdidasSiniestroBo.class);
	public PerdidasSiniestro grabarPerdidasSiniestro(PerdidasSiniestro o) throws Exception{
		PerdidasSiniestro domain = null;
		
		domain = boPerdidasSiniestro.grabarPerdidas(o);
		return domain;
		}
}

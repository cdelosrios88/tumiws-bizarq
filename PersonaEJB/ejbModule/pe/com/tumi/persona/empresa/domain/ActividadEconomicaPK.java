package pe.com.tumi.persona.empresa.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class ActividadEconomicaPK extends TumiDomain {

	private Integer intIdPersona;
	private Integer intItemActividad;
	
	public Integer getIntIdPersona() {
		return intIdPersona;
	}
	public void setIntIdPersona(Integer intIdPersona) {
		this.intIdPersona = intIdPersona;
	}
	public Integer getIntItemActividad() {
		return intItemActividad;
	}
	public void setIntItemActividad(Integer intItemActividad) {
		this.intItemActividad = intItemActividad;
	}

}
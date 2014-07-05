package pe.com.tumi.persona.empresa.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class PerLaboralPK extends TumiDomain {

	private Integer intIdPersona;
	private Integer intItemLaboral;
	
	//Getters & Setters
	public Integer getIntIdPersona() {
		return intIdPersona;
	}
	public void setIntIdPersona(Integer intIdPersona) {
		this.intIdPersona = intIdPersona;
	}
	public Integer getIntItemLaboral() {
		return intItemLaboral;
	}
	public void setIntItemLaboral(Integer intItemLaboral) {
		this.intItemLaboral = intItemLaboral;
	}
	@Override
	public String toString() {
		return "PerLaboralPK [intIdPersona=" + intIdPersona
				+ ", intItemLaboral=" + intItemLaboral + "]";
	}
	
}

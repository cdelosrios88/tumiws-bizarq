package pe.com.tumi.persona.contacto.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class ComunicacionPK extends TumiDomain{

	private Integer intIdPersona;
	private Integer intIdComunicacion;
	
	public Integer getIntIdPersona() {
		return intIdPersona;
	}
	public void setIntIdPersona(Integer intIdPersona) {
		this.intIdPersona = intIdPersona;
	}
	public Integer getIntIdComunicacion() {
		return intIdComunicacion;
	}
	public void setIntIdComunicacion(Integer intIdComunicacion) {
		this.intIdComunicacion = intIdComunicacion;
	}
	@Override
	public String toString() {
		return "ComunicacionPK [intIdPersona=" + intIdPersona
				+ ", intIdComunicacion=" + intIdComunicacion + "]";
	}
	
}
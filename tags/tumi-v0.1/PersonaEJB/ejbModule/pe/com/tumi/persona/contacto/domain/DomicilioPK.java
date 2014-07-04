package pe.com.tumi.persona.contacto.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class DomicilioPK extends TumiDomain{

	private Integer intIdPersona;
	private Integer intIdDomicilio;
	
	
	public Integer getIntIdPersona() {
		return intIdPersona;
	}
	public void setIntIdPersona(Integer intIdPersona) {
		this.intIdPersona = intIdPersona;
	}
	public Integer getIntIdDomicilio() {
		return intIdDomicilio;
	}
	public void setIntIdDomicilio(Integer intIdDomicilio) {
		this.intIdDomicilio = intIdDomicilio;
	}
	
	@Override
	public String toString() {
		return "DomicilioPK [intIdPersona=" + intIdPersona
				+ ", intIdDomicilio=" + intIdDomicilio + "]";
	}
}
package pe.com.tumi.persona.contacto.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class DocumentoPK extends TumiDomain{

	private Integer intIdPersona;
	private Integer intItemDocumento;
	
	public Integer getIntIdPersona() {
		return intIdPersona;
	}
	public void setIntIdPersona(Integer intIdPersona) {
		this.intIdPersona = intIdPersona;
	}
	public Integer getIntItemDocumento() {
		return intItemDocumento;
	}
	public void setIntItemDocumento(Integer intItemDocumento) {
		this.intItemDocumento = intItemDocumento;
	}
	@Override
	public String toString() {
		return "DocumentoPK [intIdPersona=" + intIdPersona
				+ ", intItemDocumento=" + intItemDocumento + "]";
	}
	
}
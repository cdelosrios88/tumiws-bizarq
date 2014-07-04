package pe.com.tumi.persona.empresa.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class TipoComprobantePK extends TumiDomain {

	private Integer intIdPersona;
	private Integer intItemTipoComprobante;
	
	public Integer getIntIdPersona() {
		return intIdPersona;
	}
	public void setIntIdPersona(Integer intIdPersona) {
		this.intIdPersona = intIdPersona;
	}
	public Integer getIntItemTipoComprobante() {
		return intItemTipoComprobante;
	}
	@Override
	public String toString() {
		return "TipoComprobantePK [intIdPersona=" + intIdPersona
				+ ", intItemTipoComprobante=" + intItemTipoComprobante + "]";
	}
	public void setIntItemTipoComprobante(Integer intItemTipoComprobante) {
		this.intItemTipoComprobante = intItemTipoComprobante;
	}

}
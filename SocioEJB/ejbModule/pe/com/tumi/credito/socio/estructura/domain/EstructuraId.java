package pe.com.tumi.credito.socio.estructura.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class EstructuraId extends TumiDomain {

	private Integer intNivel;
	private Integer intCodigo;
	
	public Integer getIntNivel() {
		return intNivel;
	}
	public void setIntNivel(Integer intNivel) {
		this.intNivel = intNivel;
	}
	public Integer getIntCodigo() {
		return intCodigo;
	}
	public void setIntCodigo(Integer intCodigo) {
		this.intCodigo = intCodigo;
	}
	@Override
	public String toString() {
		return "EstructuraId [intNivel=" + intNivel + ", intCodigo="
				+ intCodigo + "]";
	}
	
}

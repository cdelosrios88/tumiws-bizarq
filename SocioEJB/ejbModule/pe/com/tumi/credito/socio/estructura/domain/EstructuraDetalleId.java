package pe.com.tumi.credito.socio.estructura.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class EstructuraDetalleId extends TumiDomain {

	private Integer intNivel;
	private Integer intCodigo;
	private Integer intCaso;
	private Integer intItemCaso;
	
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
	public Integer getIntCaso() {
		return intCaso;
	}
	public void setIntCaso(Integer intCaso) {
		this.intCaso = intCaso;
	}
	public Integer getIntItemCaso() {
		return intItemCaso;
	}
	public void setIntItemCaso(Integer intItemCaso) {
		this.intItemCaso = intItemCaso;
	}
	
	@Override
	public String toString() {
		return "EstructuraDetalleId [intNivel=" + intNivel
				+ ", intCodigo=" + intCodigo + ", intCaso="
				+ intCaso + ", intItemCaso="
				+ intItemCaso + "]";
	}
}

package pe.com.tumi.movimiento.concepto.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class CuentaConceptoDetalleId extends TumiDomain{

	private Integer intPersEmpresaPk;
	private Integer intCuentaPk;
	private Integer intItemCuentaConcepto;
	private Integer intItemCtaCptoDet;
	
	public Integer getIntPersEmpresaPk() {
		return intPersEmpresaPk;
	}
	public void setIntPersEmpresaPk(Integer intPersEmpresaPk) {
		this.intPersEmpresaPk = intPersEmpresaPk;
	}
	public Integer getIntCuentaPk() {
		return intCuentaPk;
	}
	public void setIntCuentaPk(Integer intCuentaPk) {
		this.intCuentaPk = intCuentaPk;
	}
	public Integer getIntItemCuentaConcepto() {
		return intItemCuentaConcepto;
	}
	public void setIntItemCuentaConcepto(Integer intItemCuentaConcepto) {
		this.intItemCuentaConcepto = intItemCuentaConcepto;
	}
	public Integer getIntItemCtaCptoDet() {
		return intItemCtaCptoDet;
	}
	public void setIntItemCtaCptoDet(Integer intItemCtaCptoDet) {
		this.intItemCtaCptoDet = intItemCtaCptoDet;
	}
	@Override
	public String toString() {
		return "CuentaConceptoDetalleId [intPersEmpresaPk=" + intPersEmpresaPk
				+ ", intCuentaPk=" + intCuentaPk + ", intItemCuentaConcepto="
				+ intItemCuentaConcepto + ", intItemCtaCptoDet="
				+ intItemCtaCptoDet + "]";
	}

}

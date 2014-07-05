package pe.com.tumi.movimiento.concepto.domain;

import java.math.BigDecimal;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class CuentaDetalleBeneficioId extends TumiDomain{

	private Integer intPersEmpresaPk;
	private Integer intCuentaPk;
	private Integer intItemCuentaConcepto;
	private Integer intItemBeneficio;
	
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
	public Integer getIntItemBeneficio() {
		return intItemBeneficio;
	}
	public void setIntItemBeneficio(Integer intItemBeneficio) {
		this.intItemBeneficio = intItemBeneficio;
	}

}

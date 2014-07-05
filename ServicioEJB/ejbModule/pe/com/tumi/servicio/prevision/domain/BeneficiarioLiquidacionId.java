package pe.com.tumi.servicio.prevision.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class BeneficiarioLiquidacionId extends TumiDomain{

	private Integer intPersEmpresaLiquidacion;
	private Integer intItemExpediente;
	private Integer intPersEmpresa;
	private Integer intCuenta;
	private Integer intItemCuentaConcepto;
	private Integer intItemBeneficiario;
	
	public Integer getIntPersEmpresaLiquidacion() {
		return intPersEmpresaLiquidacion;
	}
	public void setIntPersEmpresaLiquidacion(Integer intPersEmpresaLiquidacion) {
		this.intPersEmpresaLiquidacion = intPersEmpresaLiquidacion;
	}
	public Integer getIntItemExpediente() {
		return intItemExpediente;
	}
	public void setIntItemExpediente(Integer intItemExpediente) {
		this.intItemExpediente = intItemExpediente;
	}
	public Integer getIntPersEmpresa() {
		return intPersEmpresa;
	}
	public void setIntPersEmpresa(Integer intPersEmpresa) {
		this.intPersEmpresa = intPersEmpresa;
	}
	public Integer getIntCuenta() {
		return intCuenta;
	}
	public void setIntCuenta(Integer intCuenta) {
		this.intCuenta = intCuenta;
	}
	public Integer getIntItemCuentaConcepto() {
		return intItemCuentaConcepto;
	}
	public void setIntItemCuentaConcepto(Integer intItemCuentaConcepto) {
		this.intItemCuentaConcepto = intItemCuentaConcepto;
	}
	public Integer getIntItemBeneficiario() {
		return intItemBeneficiario;
	}
	public void setIntItemBeneficiario(Integer intItemBeneficiario) {
		this.intItemBeneficiario = intItemBeneficiario;
	}
	@Override
	public String toString() {
		return "BeneficiarioLiquidacionId [intPersEmpresaLiquidacion="
				+ intPersEmpresaLiquidacion + ", intItemExpediente="
				+ intItemExpediente + ", intPersEmpresa=" + intPersEmpresa
				+ ", intCuenta=" + intCuenta + ", intItemCuentaConcepto="
				+ intItemCuentaConcepto + ", intItemBeneficiario="
				+ intItemBeneficiario + "]";
	}
}
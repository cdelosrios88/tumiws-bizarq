package pe.com.tumi.credito.socio.aperturaCuenta.core.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class CuentaMesCobroId extends TumiDomain {
	private Integer intPersEmpresaPk;
	private Integer intCuenta;
	private Integer intParaMesCod;
	
	public Integer getIntPersEmpresaPk() {
		return intPersEmpresaPk;
	}
	public void setIntPersEmpresaPk(Integer intPersEmpresaPk) {
		this.intPersEmpresaPk = intPersEmpresaPk;
	}
	public Integer getIntCuenta() {
		return intCuenta;
	}
	public void setIntCuenta(Integer intCuenta) {
		this.intCuenta = intCuenta;
	}
	public Integer getIntParaMesCod() {
		return intParaMesCod;
	}
	public void setIntParaMesCod(Integer intParaMesCod) {
		this.intParaMesCod = intParaMesCod;
	}
}

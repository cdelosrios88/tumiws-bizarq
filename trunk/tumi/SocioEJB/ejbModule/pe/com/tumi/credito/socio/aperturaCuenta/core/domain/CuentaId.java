package pe.com.tumi.credito.socio.aperturaCuenta.core.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class CuentaId extends TumiDomain {
	private Integer intPersEmpresaPk;
	private Integer intCuenta;
	
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
	@Override
	public String toString() {
		return "CuentaId [intPersEmpresaPk=" + intPersEmpresaPk
				+ ", intCuenta=" + intCuenta + "]";
	}
	
}

package pe.com.tumi.credito.socio.aperturaCuenta.core.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class CuentaIntegranteId extends TumiDomain {
	private Integer intPersEmpresaPk;
	private Integer intCuenta;
	private Integer intPersonaIntegrante;
	
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
	public Integer getIntPersonaIntegrante() {
		return intPersonaIntegrante;
	}
	public void setIntPersonaIntegrante(Integer intPersonaIntegrante) {
		this.intPersonaIntegrante = intPersonaIntegrante;
	}
	@Override
	public String toString() {
		return "CuentaIntegranteId [intPersEmpresaPk=" + intPersEmpresaPk
				+ ", intCuenta=" + intCuenta + ", intPersonaIntegrante="
				+ intPersonaIntegrante + "]";
	}
	
}

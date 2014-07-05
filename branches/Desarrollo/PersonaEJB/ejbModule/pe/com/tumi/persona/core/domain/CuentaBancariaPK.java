package pe.com.tumi.persona.core.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class CuentaBancariaPK extends TumiDomain{

	private Integer intIdPersona;
	private Integer intIdCuentaBancaria;
	
	
	public Integer getIntIdPersona() {
		return intIdPersona;
	}
	public void setIntIdPersona(Integer intIdPersona) {
		this.intIdPersona = intIdPersona;
	}
	public Integer getIntIdCuentaBancaria() {
		return intIdCuentaBancaria;
	}
	public void setIntIdCuentaBancaria(Integer intIdCuentaBancaria) {
		this.intIdCuentaBancaria = intIdCuentaBancaria;
	}
	@Override
	public String toString() {
		return "CuentaBancariaPK [intIdPersona=" + intIdPersona
				+ ", intIdCuentaBancaria=" + intIdCuentaBancaria + "]";
	}

}
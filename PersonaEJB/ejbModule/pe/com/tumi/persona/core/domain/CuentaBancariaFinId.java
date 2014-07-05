package pe.com.tumi.persona.core.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class CuentaBancariaFinId extends TumiDomain{


	private Integer intIdPersona;
	private Integer intIdCuentaBancaria;
	private	Integer	intParaTipoFinCuenta;
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
	public Integer getIntParaTipoFinCuenta() {
		return intParaTipoFinCuenta;
	}
	public void setIntParaTipoFinCuenta(Integer intParaTipoFinCuenta) {
		this.intParaTipoFinCuenta = intParaTipoFinCuenta;
	}
	@Override
	public String toString() {
		return "CuentaBancariaFinId [intIdPersona=" + intIdPersona
				+ ", intIdCuentaBancaria=" + intIdCuentaBancaria
				+ ", intParaTipoFinCuenta=" + intParaTipoFinCuenta + "]";
	}
	
}

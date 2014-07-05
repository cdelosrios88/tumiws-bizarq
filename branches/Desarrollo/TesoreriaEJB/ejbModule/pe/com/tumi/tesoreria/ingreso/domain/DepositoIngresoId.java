package pe.com.tumi.tesoreria.ingreso.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class DepositoIngresoId extends TumiDomain{

	private Integer intIdEmpresaDeposito;
	private Integer intItemDepositoGeneral;
	private Integer intItemDepositoIngreso;
	
	public Integer getIntIdEmpresaDeposito() {
		return intIdEmpresaDeposito;
	}
	public void setIntIdEmpresaDeposito(Integer intIdEmpresaDeposito) {
		this.intIdEmpresaDeposito = intIdEmpresaDeposito;
	}
	public Integer getIntItemDepositoGeneral() {
		return intItemDepositoGeneral;
	}
	public void setIntItemDepositoGeneral(Integer intItemDepositoGeneral) {
		this.intItemDepositoGeneral = intItemDepositoGeneral;
	}
	public Integer getIntItemDepositoIngreso() {
		return intItemDepositoIngreso;
	}
	public void setIntItemDepositoIngreso(Integer intItemDepositoIngreso) {
		this.intItemDepositoIngreso = intItemDepositoIngreso;
	}
	@Override
	public String toString() {
		return "DepositoIngresoId [intIdEmpresaDeposito="
				+ intIdEmpresaDeposito + ", intItemDepositoGeneral="
				+ intItemDepositoGeneral + ", intItemDepositoIngreso="
				+ intItemDepositoIngreso + "]";
	}
	
	
}

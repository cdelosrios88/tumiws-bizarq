package pe.com.tumi.contabilidad.core.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class AccesoPlanCuentaId extends TumiDomain{

	private Integer intEmpresaCuenta;
	private Integer intPeriodoCuenta;
	private String	strNumeroCuenta;
	private Integer	intIdTransaccion;
	private Integer intItemAccesoPlanCuenta;
	
	
	public Integer getIntEmpresaCuenta() {
		return intEmpresaCuenta;
	}
	public void setIntEmpresaCuenta(Integer intEmpresaCuenta) {
		this.intEmpresaCuenta = intEmpresaCuenta;
	}
	public Integer getIntPeriodoCuenta() {
		return intPeriodoCuenta;
	}
	public void setIntPeriodoCuenta(Integer intPeriodoCuenta) {
		this.intPeriodoCuenta = intPeriodoCuenta;
	}
	public String getStrNumeroCuenta() {
		return strNumeroCuenta;
	}
	public void setStrNumeroCuenta(String strNumeroCuenta) {
		this.strNumeroCuenta = strNumeroCuenta;
	}
	public Integer getIntIdTransaccion() {
		return intIdTransaccion;
	}
	public void setIntIdTransaccion(Integer intIdTransaccion) {
		this.intIdTransaccion = intIdTransaccion;
	}
	public Integer getIntItemAccesoPlanCuenta() {
		return intItemAccesoPlanCuenta;
	}
	public void setIntItemAccesoPlanCuenta(Integer intItemAccesoPlanCuenta) {
		this.intItemAccesoPlanCuenta = intItemAccesoPlanCuenta;
	}
	
	@Override
	public String toString() {
		return "AccesoPlanCuentaId [intEmpresaCuenta=" + intEmpresaCuenta
				+ ", intPeriodoCuenta=" + intPeriodoCuenta
				+ ", strNumeroCuenta=" + strNumeroCuenta
				+ ", intIdTransaccion=" + intIdTransaccion
				+ ", intItemAccesoPlanCuenta=" + intItemAccesoPlanCuenta + "]";
	}
}
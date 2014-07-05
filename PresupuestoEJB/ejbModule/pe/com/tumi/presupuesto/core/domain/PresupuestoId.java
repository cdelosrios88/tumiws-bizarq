package pe.com.tumi.presupuesto.core.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class PresupuestoId extends TumiDomain{

	private Integer intEmpresaPresupuestoPk;
	private Integer intPeriodoPresupuesto;
	private Integer intMesPresupuesto;
	private Integer intEmpresaCuentaPk;
	private Integer intPeriodoCuenta;
	private String strNumeroCuenta;
	private Integer intEmpresaSucursalPk;
	private Integer intIdSucursal;
	private Integer intIdSubSucursal;
	
	public Integer getIntEmpresaPresupuestoPk() {
		return intEmpresaPresupuestoPk;
	}
	public void setIntEmpresaPresupuestoPk(Integer intEmpresaPresupuestoPk) {
		this.intEmpresaPresupuestoPk = intEmpresaPresupuestoPk;
	}
	public Integer getIntPeriodoPresupuesto() {
		return intPeriodoPresupuesto;
	}
	public void setIntPeriodoPresupuesto(Integer intPeriodoPresupuesto) {
		this.intPeriodoPresupuesto = intPeriodoPresupuesto;
	}
	public Integer getIntMesPresupuesto() {
		return intMesPresupuesto;
	}
	public void setIntMesPresupuesto(Integer intMesPresupuesto) {
		this.intMesPresupuesto = intMesPresupuesto;
	}
	public Integer getIntEmpresaCuentaPk() {
		return intEmpresaCuentaPk;
	}
	public void setIntEmpresaCuentaPk(Integer intEmpresaCuentaPk) {
		this.intEmpresaCuentaPk = intEmpresaCuentaPk;
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
	public Integer getIntEmpresaSucursalPk() {
		return intEmpresaSucursalPk;
	}
	public void setIntEmpresaSucursalPk(Integer intEmpresaSucursalPk) {
		this.intEmpresaSucursalPk = intEmpresaSucursalPk;
	}
	public Integer getIntIdSucursal() {
		return intIdSucursal;
	}
	public void setIntIdSucursal(Integer intIdSucursal) {
		this.intIdSucursal = intIdSucursal;
	}
	public Integer getIntIdSubSucursal() {
		return intIdSubSucursal;
	}
	public void setIntIdSubSucursal(Integer intIdSubSucursal) {
		this.intIdSubSucursal = intIdSubSucursal;
	}
}

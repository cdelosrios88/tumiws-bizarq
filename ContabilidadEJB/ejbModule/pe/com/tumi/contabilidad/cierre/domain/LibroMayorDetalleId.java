package pe.com.tumi.contabilidad.cierre.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class LibroMayorDetalleId extends TumiDomain{

	private Integer intPersEmpresaMayor;
	private Integer intContPeriodoMayor;
	private Integer intContMesMayor;
	private Integer intPersEmpresaCuenta;
	private Integer intContPeriodoCuenta;
	private String 	strContNumeroCuenta;
	private Integer intPersEmpresaSucursal;
	private Integer intSucuIdSucursal;
	private Integer intSudeIdSubSucursal;
	
	
	public Integer getIntPersEmpresaMayor() {
		return intPersEmpresaMayor;
	}
	public void setIntPersEmpresaMayor(Integer intPersEmpresaMayor) {
		this.intPersEmpresaMayor = intPersEmpresaMayor;
	}
	public Integer getIntContPeriodoMayor() {
		return intContPeriodoMayor;
	}
	public void setIntContPeriodoMayor(Integer intContPeriodoMayor) {
		this.intContPeriodoMayor = intContPeriodoMayor;
	}
	public Integer getIntContMesMayor() {
		return intContMesMayor;
	}
	public void setIntContMesMayor(Integer intContMesMayor) {
		this.intContMesMayor = intContMesMayor;
	}
	public Integer getIntPersEmpresaCuenta() {
		return intPersEmpresaCuenta;
	}
	public void setIntPersEmpresaCuenta(Integer intPersEmpresaCuenta) {
		this.intPersEmpresaCuenta = intPersEmpresaCuenta;
	}
	public Integer getIntContPeriodoCuenta() {
		return intContPeriodoCuenta;
	}
	public void setIntContPeriodoCuenta(Integer intContPeriodoCuenta) {
		this.intContPeriodoCuenta = intContPeriodoCuenta;
	}
	public String getStrContNumeroCuenta() {
		return strContNumeroCuenta;
	}
	public void setStrContNumeroCuenta(String strContNumeroCuenta) {
		this.strContNumeroCuenta = strContNumeroCuenta;
	}
	public Integer getIntPersEmpresaSucursal() {
		return intPersEmpresaSucursal;
	}
	public void setIntPersEmpresaSucursal(Integer intPersEmpresaSucursal) {
		this.intPersEmpresaSucursal = intPersEmpresaSucursal;
	}
	public Integer getIntSucuIdSucursal() {
		return intSucuIdSucursal;
	}
	public void setIntSucuIdSucursal(Integer intSucuIdSucursal) {
		this.intSucuIdSucursal = intSucuIdSucursal;
	}
	public Integer getIntSudeIdSubSucursal() {
		return intSudeIdSubSucursal;
	}
	public void setIntSudeIdSubSucursal(Integer intSudeIdSubSucursal) {
		this.intSudeIdSubSucursal = intSudeIdSubSucursal;
	}
	@Override
	public String toString() {
		return "LibroMayorDetalleId [intPersEmpresaMayor="
				+ intPersEmpresaMayor + ", intContPeriodoMayor="
				+ intContPeriodoMayor + ", intContMesMayor=" + intContMesMayor
				+ ", intPersEmpresaCuenta=" + intPersEmpresaCuenta
				+ ", intContPeriodoCuenta=" + intContPeriodoCuenta
				+ ", strContNumeroCuenta=" + strContNumeroCuenta
				+ ", intPersEmpresaSucursal=" + intPersEmpresaSucursal
				+ ", intSucuIdSucursal=" + intSucuIdSucursal
				+ ", intSudeIdSubSucursal=" + intSudeIdSubSucursal + "]";
	}
}
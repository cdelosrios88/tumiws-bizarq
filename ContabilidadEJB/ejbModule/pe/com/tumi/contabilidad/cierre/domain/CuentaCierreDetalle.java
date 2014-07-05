package pe.com.tumi.contabilidad.cierre.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class CuentaCierreDetalle extends TumiDomain{

	private CuentaCierreDetalleId id;
	private Integer 	intPersEmpresaCuenta;
	private Integer 	intContPeriodoCuenta;
	private String 		strContNumeroCuenta;
	
	//para interfaz
	private String		strDescripcion;
	
	public CuentaCierreDetalle(){
		id = new CuentaCierreDetalleId();
	}
	public CuentaCierreDetalleId getId() {
		return id;
	}
	public void setId(CuentaCierreDetalleId id) {
		this.id = id;
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
	public String getStrDescripcion() {
		return strDescripcion;
	}
	public void setStrDescripcion(String strDescripcion) {
		this.strDescripcion = strDescripcion;
	}
	@Override
	public String toString() {
		return "CuentaCierreDetalle [id=" + id + ", intPersEmpresaCuenta="
				+ intPersEmpresaCuenta + ", intContPeriodoCuenta="
				+ intContPeriodoCuenta + ", strContNumeroCuenta="
				+ strContNumeroCuenta + ", strDescripcion=" + strDescripcion
				+ "]";
	}
	
}

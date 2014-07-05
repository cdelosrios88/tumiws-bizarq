package pe.com.tumi.empresa.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class SubSucursalPK extends TumiDomain{
	private Integer		intPersEmpresaPk;
	private	Integer		intIdSucursal;
	private	Integer		intIdSubSucursal;
	
	//Getters y Setters
	public Integer getIntPersEmpresaPk() {
		return intPersEmpresaPk;
	}
	public void setIntPersEmpresaPk(Integer intPersEmpresaPk) {
		this.intPersEmpresaPk = intPersEmpresaPk;
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
	@Override
	public String toString() {
		return "SubSucursalPK [intPersEmpresaPk=" + intPersEmpresaPk
				+ ", intIdSucursal=" + intIdSucursal + ", intIdSubSucursal="
				+ intIdSubSucursal + "]";
	}
}

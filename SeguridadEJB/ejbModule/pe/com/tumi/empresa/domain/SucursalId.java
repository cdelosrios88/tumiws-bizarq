package pe.com.tumi.empresa.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class SucursalId extends TumiDomain{
	private Integer		intPersEmpresaPk;
	private	Integer		intIdSucursal;
	
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
	@Override
	public String toString() {
		return "SucursalId [intPersEmpresaPk=" + intPersEmpresaPk
				+ ", intIdSucursal=" + intIdSucursal + "]";
	}
	
}

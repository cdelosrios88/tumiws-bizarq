package pe.com.tumi.empresa.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class AreaId extends TumiDomain{
	private Integer intPersEmpresaPk;
	private Integer intIdSucursalPk;
	private Integer intIdArea;
	
	public Integer getIntPersEmpresaPk() {
		return intPersEmpresaPk;
	}
	public void setIntPersEmpresaPk(Integer intPersEmpresaPk) {
		this.intPersEmpresaPk = intPersEmpresaPk;
	}
	public Integer getIntIdSucursalPk() {
		return intIdSucursalPk;
	}
	public void setIntIdSucursalPk(Integer intIdSucursalPk) {
		this.intIdSucursalPk = intIdSucursalPk;
	}
	public Integer getIntIdArea() {
		return intIdArea;
	}
	public void setIntIdArea(Integer intIdArea) {
		this.intIdArea = intIdArea;
	}
	@Override
	public String toString() {
		return "AreaId [intPersEmpresaPk=" + intPersEmpresaPk
				+ ", intIdSucursalPk=" + intIdSucursalPk + ", intIdArea="
				+ intIdArea + "]";
	}
}

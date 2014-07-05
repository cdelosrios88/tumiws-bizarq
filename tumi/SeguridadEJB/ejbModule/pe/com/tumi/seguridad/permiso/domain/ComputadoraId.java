package pe.com.tumi.seguridad.permiso.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class ComputadoraId  extends TumiDomain {

	private Integer intPersEmpresaPk;
	private Integer intIdSucursal;
	private Integer intIdArea;
	private Integer intIdComputadora;

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
	public Integer getIntIdArea() {
		return intIdArea;
	}
	public void setIntIdArea(Integer intIdArea) {
		this.intIdArea = intIdArea;
	}
	public Integer getIntIdComputadora() {
		return intIdComputadora;
	}
	public void setIntIdComputadora(Integer intIdComputadora) {
		this.intIdComputadora = intIdComputadora;
	}
	@Override
	public String toString() {
		return "ComputadoraId [intPersEmpresaPk=" + intPersEmpresaPk
				+ ", intIdSucursal=" + intIdSucursal + ", intIdArea="
				+ intIdArea + ", intIdComputadora=" + intIdComputadora + "]";
	}

}

package pe.com.tumi.tesoreria.logistica.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class CuadroComparativoProveedorId extends TumiDomain{

	private Integer intPersEmpresa;
	private Integer	intItemCuadroComparativo;
	private Integer intItemCuadroComparativoProveedor;
	
	public Integer getIntPersEmpresa() {
		return intPersEmpresa;
	}
	public void setIntPersEmpresa(Integer intPersEmpresa) {
		this.intPersEmpresa = intPersEmpresa;
	}
	public Integer getIntItemCuadroComparativo() {
		return intItemCuadroComparativo;
	}
	public void setIntItemCuadroComparativo(Integer intItemCuadroComparativo) {
		this.intItemCuadroComparativo = intItemCuadroComparativo;
	}
	public Integer getIntItemCuadroComparativoProveedor() {
		return intItemCuadroComparativoProveedor;
	}
	public void setIntItemCuadroComparativoProveedor(
			Integer intItemCuadroComparativoProveedor) {
		this.intItemCuadroComparativoProveedor = intItemCuadroComparativoProveedor;
	}
	@Override
	public String toString() {
		return "CuadroComparativoProveedorId [intPersEmpresa=" + intPersEmpresa
				+ ", intItemCuadroComparativo=" + intItemCuadroComparativo
				+ ", intItemCuadroComparativoProveedor="
				+ intItemCuadroComparativoProveedor + "]";
	}
	
}
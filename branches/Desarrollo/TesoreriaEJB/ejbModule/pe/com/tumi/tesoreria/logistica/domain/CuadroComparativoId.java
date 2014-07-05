package pe.com.tumi.tesoreria.logistica.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class CuadroComparativoId extends TumiDomain{

	private Integer intPersEmpresa;
	private Integer	intItemCuadroComparativo;
	
	
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
	
	@Override
	public String toString() {
		return "CuadroComparativoId [intPersEmpresa=" + intPersEmpresa
				+ ", intItemCuadroComparativo=" + intItemCuadroComparativo
				+ "]";
	}
}

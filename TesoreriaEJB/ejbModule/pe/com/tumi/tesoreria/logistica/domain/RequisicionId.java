package pe.com.tumi.tesoreria.logistica.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class RequisicionId extends TumiDomain{

	private Integer	intPersEmpresa;
	private Integer	intItemRequisicion;
	
	public Integer getIntPersEmpresa() {
		return intPersEmpresa;
	}
	public void setIntPersEmpresa(Integer intPersEmpresa) {
		this.intPersEmpresa = intPersEmpresa;
	}
	public Integer getIntItemRequisicion() {
		return intItemRequisicion;
	}
	public void setIntItemRequisicion(Integer intItemRequisicion) {
		this.intItemRequisicion = intItemRequisicion;
	}
	@Override
	public String toString() {
		return "RequisicionId [intPersEmpresa=" + intPersEmpresa
				+ ", intItemRequisicion=" + intItemRequisicion + "]";
	}	
}
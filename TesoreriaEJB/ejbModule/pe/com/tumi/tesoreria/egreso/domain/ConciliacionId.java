package pe.com.tumi.tesoreria.egreso.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class ConciliacionId extends TumiDomain{

	private Integer intPersEmpresa;
	private Integer intItemConciliacion;
	
	public Integer getIntPersEmpresa() {
		return intPersEmpresa;
	}
	public void setIntPersEmpresa(Integer intPersEmpresa) {
		this.intPersEmpresa = intPersEmpresa;
	}
	public Integer getIntItemConciliacion() {
		return intItemConciliacion;
	}
	public void setIntItemConciliacion(Integer intItemConciliacion) {
		this.intItemConciliacion = intItemConciliacion;
	}
	
	@Override
	public String toString() {
		return "ConciliacionId [intPersEmpresa=" + intPersEmpresa
				+ ", intItemConciliacion=" + intItemConciliacion + "]";
	}
}
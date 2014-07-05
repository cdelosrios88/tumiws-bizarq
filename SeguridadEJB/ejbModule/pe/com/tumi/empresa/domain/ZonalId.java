package pe.com.tumi.empresa.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class ZonalId extends TumiDomain {

	private Integer intPersEmpresaPk;
	private Integer intIdzonal;
	
	public Integer getIntPersEmpresaPk() {
		return intPersEmpresaPk;
	}
	public void setIntPersEmpresaPk(Integer intPersEmpresaPk) {
		this.intPersEmpresaPk = intPersEmpresaPk;
	}
	public Integer getIntIdzonal() {
		return intIdzonal;
	}
	public void setIntIdzonal(Integer intIdzonal) {
		this.intIdzonal = intIdzonal;
	}
}

package pe.com.tumi.tesoreria.logistica.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class AdelantoSunatId extends TumiDomain{

	private Integer	intPersEmpresa;
	private Integer intItemAdelantoSunat;
	
	public Integer getIntPersEmpresa() {
		return intPersEmpresa;
	}
	public void setIntPersEmpresa(Integer intPersEmpresa) {
		this.intPersEmpresa = intPersEmpresa;
	}
	public Integer getIntItemAdelantoSunat() {
		return intItemAdelantoSunat;
	}
	public void setIntItemAdelantoSunat(Integer intItemAdelantoSunat) {
		this.intItemAdelantoSunat = intItemAdelantoSunat;
	}
	
	@Override
	public String toString() {
		return "AdelantoSunatId [intPersEmpresa=" + intPersEmpresa
				+ ", intItemAdelantoSunat=" + intItemAdelantoSunat + "]";
	}	
}
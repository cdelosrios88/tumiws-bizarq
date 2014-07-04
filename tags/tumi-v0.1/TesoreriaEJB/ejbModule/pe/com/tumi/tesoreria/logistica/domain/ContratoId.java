package pe.com.tumi.tesoreria.logistica.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class ContratoId extends TumiDomain{

	private Integer intPersEmpresa;
	private Integer	intItemContrato;
	
	public Integer getIntPersEmpresa() {
		return intPersEmpresa;
	}
	public void setIntPersEmpresa(Integer intPersEmpresa) {
		this.intPersEmpresa = intPersEmpresa;
	}
	public Integer getIntItemContrato() {
		return intItemContrato;
	}
	public void setIntItemContrato(Integer intItemContrato) {
		this.intItemContrato = intItemContrato;
	}
	@Override
	public String toString() {
		return "ContratoId [intPersEmpresa=" + intPersEmpresa
				+ ", intItemContrato=" + intItemContrato + "]";
	}
}

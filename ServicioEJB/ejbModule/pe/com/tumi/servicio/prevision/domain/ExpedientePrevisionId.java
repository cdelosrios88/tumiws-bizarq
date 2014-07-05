package pe.com.tumi.servicio.prevision.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class ExpedientePrevisionId extends TumiDomain{
	private Integer 	intPersEmpresaPk;
	private Integer 	intCuentaPk;
	private Integer 	intItemExpediente;
	
	
	public Integer getIntPersEmpresaPk() {
		return intPersEmpresaPk;
	}
	public void setIntPersEmpresaPk(Integer intPersEmpresaPk) {
		this.intPersEmpresaPk = intPersEmpresaPk;
	}
	public Integer getIntCuentaPk() {
		return intCuentaPk;
	}
	public void setIntCuentaPk(Integer intCuentaPk) {
		this.intCuentaPk = intCuentaPk;
	}
	public Integer getIntItemExpediente() {
		return intItemExpediente;
	}
	public void setIntItemExpediente(Integer intItemExpediente) {
		this.intItemExpediente = intItemExpediente;
	}
	
	@Override
	public String toString() {
		return "ExpedientePrevisionId [intPersEmpresaPk=" + intPersEmpresaPk
				+ ", intCuentaPk=" + intCuentaPk + ", intItemExpediente="
				+ intItemExpediente + "]";
	}
	
}
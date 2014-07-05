package pe.com.tumi.servicio.prevision.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class ExpedienteLiquidacionId extends TumiDomain{
	private Integer 	intPersEmpresaPk;
	private Integer 	intItemExpediente;
	
	
	public Integer getIntPersEmpresaPk() {
		return intPersEmpresaPk;
	}
	public void setIntPersEmpresaPk(Integer intPersEmpresaPk) {
		this.intPersEmpresaPk = intPersEmpresaPk;
	}
	public Integer getIntItemExpediente() {
		return intItemExpediente;
	}
	public void setIntItemExpediente(Integer intItemExpediente) {
		this.intItemExpediente = intItemExpediente;
	}
	@Override
	public String toString() {
		return "ExpedienteLiquidacionId [intPersEmpresaPk=" + intPersEmpresaPk
				+ ", intItemExpediente=" + intItemExpediente + "]";
	}
	
}
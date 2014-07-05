package pe.com.tumi.servicio.prevision.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class RequisitoPrevisionId extends TumiDomain{
	private Integer 	intPersEmpresaPrevision;
	private Integer 	intCuentaPk;
	private Integer 	intItemExpediente;
	private Integer		intItemRequisito;

	
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


	public Integer getIntItemRequisito() {
		return intItemRequisito;
	}


	public void setIntItemRequisito(Integer intItemRequisito) {
		this.intItemRequisito = intItemRequisito;
	}


	public Integer getIntPersEmpresaPrevision() {
		return intPersEmpresaPrevision;
	}


	public void setIntPersEmpresaPrevision(Integer intPersEmpresaPrevision) {
		this.intPersEmpresaPrevision = intPersEmpresaPrevision;
	}


	@Override
	public String toString() {
		return "RequisitoPrevisionId [intPersEmpresaPrevision=" + intPersEmpresaPrevision
				+ ", intCuentaPk=" + intCuentaPk + ", intItemExpediente="
				+ intItemExpediente + ", "+" intItemRequisito= "+intItemRequisito+"]";
	}
}

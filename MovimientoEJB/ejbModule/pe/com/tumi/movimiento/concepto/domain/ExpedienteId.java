package pe.com.tumi.movimiento.concepto.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class ExpedienteId extends TumiDomain{

	private Integer intPersEmpresaPk;
	private Integer intCuentaPk;
	private Integer intItemExpediente;
	private Integer intItemExpedienteDetalle;

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
	public Integer getIntItemExpedienteDetalle() {
		return intItemExpedienteDetalle;
	}
	public void setIntItemExpedienteDetalle(Integer intItemExpedienteDetalle) {
		this.intItemExpedienteDetalle = intItemExpedienteDetalle;
	}
	@Override
	public String toString() {
		return "ExpedienteId [intPersEmpresaPk=" + intPersEmpresaPk
				+ ", intCuentaPk=" + intCuentaPk + ", intItemExpediente="
				+ intItemExpediente + ", intItemExpedienteDetalle="
				+ intItemExpedienteDetalle + "]";
	}
	
	
}

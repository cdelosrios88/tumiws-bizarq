package pe.com.tumi.servicio.prevision.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class EstadoPrevisionId extends TumiDomain{
	private Integer 	intPersEmpresaPk;
	private Integer 	intCuentaPk;
	private Integer 	intItemExpediente;
	private Integer 	intItemEstado;	
	
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
	public Integer getIntItemEstado() {
		return intItemEstado;
	}
	public void setIntItemEstado(Integer intItemEstado) {
		this.intItemEstado = intItemEstado;
	}
	@Override
	public String toString() {
		return "EstadoPrevisionId [intPersEmpresaPk=" + intPersEmpresaPk
				+ ", intCuentaPk=" + intCuentaPk + ", intItemExpediente="
				+ intItemExpediente + ", intItemEstado=" + intItemEstado + "]";
	}
	
	
}

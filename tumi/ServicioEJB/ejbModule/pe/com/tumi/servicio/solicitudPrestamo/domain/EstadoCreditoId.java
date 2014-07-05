package pe.com.tumi.servicio.solicitudPrestamo.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class EstadoCreditoId extends TumiDomain {
	private Integer 	intPersEmpresaPk;
	private Integer 	intCuentaPk;
	private Integer 	intItemEstado;
	private Integer 	intItemExpediente;
	private Integer 	intItemDetExpediente;
	
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
	public Integer getIntItemEstado() {
		return intItemEstado;
	}
	public void setIntItemEstado(Integer intItemEstado) {
		this.intItemEstado = intItemEstado;
	}
	public Integer getIntItemExpediente() {
		return intItemExpediente;
	}
	public void setIntItemExpediente(Integer intItemExpediente) {
		this.intItemExpediente = intItemExpediente;
	}
	public Integer getIntItemDetExpediente() {
		return intItemDetExpediente;
	}
	public void setIntItemDetExpediente(Integer intItemDetExpediente) {
		this.intItemDetExpediente = intItemDetExpediente;
	}
	@Override
	public String toString() {
		return "EstadoCreditoId [intPersEmpresaPk=" + intPersEmpresaPk
				+ ", intCuentaPk=" + intCuentaPk + ", intItemEstado="
				+ intItemEstado + ", intItemExpediente=" + intItemExpediente
				+ ", intItemDetExpediente=" + intItemDetExpediente + "]";
	}
}

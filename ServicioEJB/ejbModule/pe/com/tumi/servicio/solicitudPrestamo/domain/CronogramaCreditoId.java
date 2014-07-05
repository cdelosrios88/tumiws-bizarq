package pe.com.tumi.servicio.solicitudPrestamo.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class CronogramaCreditoId extends TumiDomain {
	private Integer 	intPersEmpresaPk;
	private Integer 	intCuentaPk;
	private Integer 	intItemExpediente;
	private Integer 	intItemDetExpediente;
	private Integer 	intItemCronograma;
	
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
	public Integer getIntItemDetExpediente() {
		return intItemDetExpediente;
	}
	public void setIntItemDetExpediente(Integer intItemDetExpediente) {
		this.intItemDetExpediente = intItemDetExpediente;
	}
	public Integer getIntItemCronograma() {
		return intItemCronograma;
	}
	public void setIntItemCronograma(Integer intItemCronograma) {
		this.intItemCronograma = intItemCronograma;
	}
	@Override
	public String toString() {
		return "CronogramaCreditoId [intPersEmpresaPk=" + intPersEmpresaPk
				+ ", intCuentaPk=" + intCuentaPk + ", intItemExpediente="
				+ intItemExpediente + ", intItemDetExpediente="
				+ intItemDetExpediente + ", intItemCronograma="
				+ intItemCronograma + "]";
	}
	
	
}

package pe.com.tumi.servicio.solicitudPrestamo.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class CapacidadDescuentoId extends TumiDomain {
	private Integer intPersEmpresaPk;
	private Integer intCuentaPk;
	private Integer intItemExpediente;
	private Integer intItemDetExpediente;
	private Integer intItemCapacidad;
	private Integer intItemCapacidadDescuento;
	
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
	public Integer getIntItemCapacidad() {
		return intItemCapacidad;
	}
	public void setIntItemCapacidad(Integer intItemCapacidad) {
		this.intItemCapacidad = intItemCapacidad;
	}
	public Integer getIntItemCapacidadDescuento() {
		return intItemCapacidadDescuento;
	}
	public void setIntItemCapacidadDescuento(Integer intItemCapacidadDescuento) {
		this.intItemCapacidadDescuento = intItemCapacidadDescuento;
	}
}

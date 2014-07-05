package pe.com.tumi.servicio.prevision.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class FallecidoPrevisionId extends TumiDomain{
	private Integer intPersEmpresaPrevision;
	private Integer intCuenta;
	private Integer intItemExpediente;
	private Integer intItemFallecido;
	
	public Integer getIntPersEmpresaPrevision() {
		return intPersEmpresaPrevision;
	}
	public void setIntPersEmpresaPrevision(Integer intPersEmpresaPrevision) {
		this.intPersEmpresaPrevision = intPersEmpresaPrevision;
	}
	public Integer getIntCuenta() {
		return intCuenta;
	}
	public void setIntCuenta(Integer intCuenta) {
		this.intCuenta = intCuenta;
	}
	public Integer getIntItemExpediente() {
		return intItemExpediente;
	}
	public void setIntItemExpediente(Integer intItemExpediente) {
		this.intItemExpediente = intItemExpediente;
	}
	public Integer getIntItemFallecido() {
		return intItemFallecido;
	}
	public void setIntItemFallecido(Integer intItemFallecido) {
		this.intItemFallecido = intItemFallecido;
	}
	
}

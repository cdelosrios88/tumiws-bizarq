package pe.com.tumi.movimiento.concepto.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class InteresCanceladoId extends TumiDomain{
	
	private Integer intPersEmpresaPk;
	private Integer intCuentaPk;
	private Integer intItemExpediente;
	private Integer intItemExpedienteDetalle;
	private Integer intItemMovCtaCte;
	private Integer intItemCancelaInteres;
	
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
	public Integer getIntItemMovCtaCte() {
		return intItemMovCtaCte;
	}
	public void setIntItemMovCtaCte(Integer intItemMovCtaCte) {
		this.intItemMovCtaCte = intItemMovCtaCte;
	}
	public Integer getIntItemCancelaInteres() {
		return intItemCancelaInteres;
	}
	public void setIntItemCancelaInteres(Integer intItemCancelaInteres) {
		this.intItemCancelaInteres = intItemCancelaInteres;
	}
	
	
}

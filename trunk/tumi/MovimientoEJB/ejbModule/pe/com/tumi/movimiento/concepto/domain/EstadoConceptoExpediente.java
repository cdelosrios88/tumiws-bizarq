package pe.com.tumi.movimiento.concepto.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class EstadoConceptoExpediente extends TumiDomain{

	private EstadoConceptoExpedienteId id;
	private Integer intPersEmpresa;
	private Integer intCuenta;
	private Integer intItemCuentaConcepto;
	private Integer intItemExpediente;
	private Integer intItemExpedienteDetalle;
	private Integer intParaEstadoConceptoExpediente;
	private Integer intIndicadorEntrega;
	
	public EstadoConceptoExpediente(){
		id = new EstadoConceptoExpedienteId();
	}
	
	public EstadoConceptoExpedienteId getId() {
		return id;
	}
	public void setId(EstadoConceptoExpedienteId id) {
		this.id = id;
	}
	public Integer getIntPersEmpresa() {
		return intPersEmpresa;
	}
	public void setIntPersEmpresa(Integer intPersEmpresa) {
		this.intPersEmpresa = intPersEmpresa;
	}
	public Integer getIntCuenta() {
		return intCuenta;
	}
	public void setIntCuenta(Integer intCuenta) {
		this.intCuenta = intCuenta;
	}
	public Integer getIntItemCuentaConcepto() {
		return intItemCuentaConcepto;
	}
	public void setIntItemCuentaConcepto(Integer intItemCuentaConcepto) {
		this.intItemCuentaConcepto = intItemCuentaConcepto;
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
	public Integer getIntParaEstadoConceptoExpediente() {
		return intParaEstadoConceptoExpediente;
	}
	public void setIntParaEstadoConceptoExpediente(Integer intParaEstadoConceptoExpediente) {
		this.intParaEstadoConceptoExpediente = intParaEstadoConceptoExpediente;
	}
	public Integer getIntIndicadorEntrega() {
		return intIndicadorEntrega;
	}
	public void setIntIndicadorEntrega(Integer intIndicadorEntrega) {
		this.intIndicadorEntrega = intIndicadorEntrega;
	}
	
	@Override
	public String toString() {
		return "EstadoConceptoExpediente [id=" + id + ", intPersEmpresa="
				+ intPersEmpresa + ", intCuenta=" + intCuenta
				+ ", intItemCuentaConcepto=" + intItemCuentaConcepto
				+ ", intItemExpediente=" + intItemExpediente
				+ ", intItemExpedienteDetalle=" + intItemExpedienteDetalle
				+ ", intParaEstadoConceptoExpediente="
				+ intParaEstadoConceptoExpediente + ", intIndicadorEntrega="
				+ intIndicadorEntrega + "]";
	}	
}
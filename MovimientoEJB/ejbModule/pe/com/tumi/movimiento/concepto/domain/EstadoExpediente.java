package pe.com.tumi.movimiento.concepto.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class EstadoExpediente extends TumiDomain{
	private EstadoExpedienteId id;
	private Integer intEmpresa;
	private Integer intCuenta;
	private Integer intItemCuentaConcepto;
	private Integer intItemExpediente;
	private Integer intItemDetExpediente;
	private Integer intParaEstadoExpediente;
	private Integer intIndicadorEntrega;
	
	public EstadoExpedienteId getId() {
		return id;
	}
	public void setId(EstadoExpedienteId id) {
		this.id = id;
	}
	public Integer getIntEmpresa() {
		return intEmpresa;
	}
	public void setIntEmpresa(Integer intEmpresa) {
		this.intEmpresa = intEmpresa;
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
	public Integer getIntItemDetExpediente() {
		return intItemDetExpediente;
	}
	public void setIntItemDetExpediente(Integer intItemDetExpediente) {
		this.intItemDetExpediente = intItemDetExpediente;
	}
	public Integer getIntParaEstadoExpediente() {
		return intParaEstadoExpediente;
	}
	public void setIntParaEstadoExpediente(Integer intParaEstadoExpediente) {
		this.intParaEstadoExpediente = intParaEstadoExpediente;
	}
	public Integer getIntIndicadorEntrega() {
		return intIndicadorEntrega;
	}
	public void setIntIndicadorEntrega(Integer intIndicadorEntrega) {
		this.intIndicadorEntrega = intIndicadorEntrega;
	}
	
	
	
	
	
}

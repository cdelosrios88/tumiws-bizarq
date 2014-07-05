package pe.com.tumi.cobranza.planilla.domain;

import java.math.BigDecimal;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class CobroPlanillas extends TumiDomain{

	private CobroPlanillasId	id;
	private	Integer		intPersEmpresaIngreso;
	private Integer		intItemIngresoGeneral;
	private BigDecimal	bdMontoPago;
	private	Integer		intParaTipoMoneda;
	private Integer		intParaEstado;
	
	public CobroPlanillas(){
		id = new CobroPlanillasId();
	}
	
	public CobroPlanillasId getId() {
		return id;
	}
	public void setId(CobroPlanillasId id) {
		this.id = id;
	}
	public Integer getIntPersEmpresaIngreso() {
		return intPersEmpresaIngreso;
	}
	public void setIntPersEmpresaIngreso(Integer intPersEmpresaIngreso) {
		this.intPersEmpresaIngreso = intPersEmpresaIngreso;
	}
	public Integer getIntItemIngresoGeneral() {
		return intItemIngresoGeneral;
	}
	public void setIntItemIngresoGeneral(Integer intItemIngresoGeneral) {
		this.intItemIngresoGeneral = intItemIngresoGeneral;
	}
	public BigDecimal getBdMontoPago() {
		return bdMontoPago;
	}
	public void setBdMontoPago(BigDecimal bdMontoPago) {
		this.bdMontoPago = bdMontoPago;
	}
	public Integer getIntParaTipoMoneda() {
		return intParaTipoMoneda;
	}
	public void setIntParaTipoMoneda(Integer intParaTipoMoneda) {
		this.intParaTipoMoneda = intParaTipoMoneda;
	}
	public Integer getIntParaEstado() {
		return intParaEstado;
	}
	public void setIntParaEstado(Integer intParaEstado) {
		this.intParaEstado = intParaEstado;
	}

	@Override
	public String toString() {
		return "CobroPlanillas [id=" + id + ", intPersEmpresaIngreso="
				+ intPersEmpresaIngreso + ", intItemIngresoGeneral="
				+ intItemIngresoGeneral + ", bdMontoPago=" + bdMontoPago
				+ ", intParaTipoMoneda=" + intParaTipoMoneda
				+ ", intParaEstado=" + intParaEstado + "]";
	}	
}
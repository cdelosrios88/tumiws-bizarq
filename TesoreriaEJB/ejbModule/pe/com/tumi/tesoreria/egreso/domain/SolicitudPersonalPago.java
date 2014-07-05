package pe.com.tumi.tesoreria.egreso.domain;

import java.math.BigDecimal;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class SolicitudPersonalPago extends TumiDomain{

	private SolicitudPersonalPagoId id;
	private Integer intPersEmpresaEgreso;
	private Integer intItemEgresoGeneral;
	private BigDecimal bdMonto;
	private Integer	intParaEstado;
	
	
	public SolicitudPersonalPago(){
		id = new SolicitudPersonalPagoId();
	}
	
	public SolicitudPersonalPagoId getId() {
		return id;
	}
	public void setId(SolicitudPersonalPagoId id) {
		this.id = id;
	}
	public Integer getIntPersEmpresaEgreso() {
		return intPersEmpresaEgreso;
	}
	public void setIntPersEmpresaEgreso(Integer intPersEmpresaEgreso) {
		this.intPersEmpresaEgreso = intPersEmpresaEgreso;
	}
	public Integer getIntItemEgresoGeneral() {
		return intItemEgresoGeneral;
	}
	public void setIntItemEgresoGeneral(Integer intItemEgresoGeneral) {
		this.intItemEgresoGeneral = intItemEgresoGeneral;
	}
	public BigDecimal getBdMonto() {
		return bdMonto;
	}
	public void setBdMonto(BigDecimal bdMonto) {
		this.bdMonto = bdMonto;
	}	
	public Integer getIntParaEstado() {
		return intParaEstado;
	}
	public void setIntParaEstado(Integer intParaEstado) {
		this.intParaEstado = intParaEstado;
	}

	@Override
	public String toString() {
		return "SolicitudPersonalPago [id=" + id + ", intPersEmpresaEgreso="
				+ intPersEmpresaEgreso + ", intItemEgresoGeneral="
				+ intItemEgresoGeneral + ", bdMonto=" + bdMonto
				+ ", intParaEstado=" + intParaEstado + "]";
	}
}

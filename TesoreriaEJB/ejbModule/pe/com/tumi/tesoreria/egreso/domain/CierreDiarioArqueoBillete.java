package pe.com.tumi.tesoreria.egreso.domain;

import java.math.BigDecimal;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class CierreDiarioArqueoBillete extends TumiDomain{

	private CierreDiarioArqueoBilleteId id;
	private Integer 	intCantidad;
	private BigDecimal 	bdMonto;
	private Integer 	intParaEstado;
	
	public CierreDiarioArqueoBillete(){
		id = new CierreDiarioArqueoBilleteId();
	}	
	public CierreDiarioArqueoBilleteId getId() {
		return id;
	}
	public void setId(CierreDiarioArqueoBilleteId id) {
		this.id = id;
	}
	public Integer getIntParaEstado() {
		return intParaEstado;
	}
	public void setIntParaEstado(Integer intParaEstado) {
		this.intParaEstado = intParaEstado;
	}
	public Integer getIntCantidad() {
		return intCantidad;
	}
	public void setIntCantidad(Integer intCantidad) {
		this.intCantidad = intCantidad;
	}
	public BigDecimal getBdMonto() {
		return bdMonto;
	}
	public void setBdMonto(BigDecimal bdMonto) {
		this.bdMonto = bdMonto;
	}
	@Override
	public String toString() {
		return "CierreDiarioArqueoBillete [id=" + id + ", intCantidad="
				+ intCantidad + ", bdMonto=" + bdMonto + ", intParaEstado="
				+ intParaEstado + "]";
	}	
}
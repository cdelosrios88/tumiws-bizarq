package pe.com.tumi.tesoreria.logistica.domain;

import java.math.BigDecimal;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class CuadroComparativoProveedorDetalle extends TumiDomain{

	private CuadroComparativoProveedorDetalleId id;
	private String 		strDetalle;
	private String 		strMarca;
	private BigDecimal	bdCantidad;
	private Integer		intParaTipoMoneda;
	private BigDecimal	bdPrecioUnitario;
	private Integer 	intParaEstado;
	
	public CuadroComparativoProveedorDetalle(){
		id = new CuadroComparativoProveedorDetalleId();
	}
	
	public CuadroComparativoProveedorDetalleId getId() {
		return id;
	}
	public void setId(CuadroComparativoProveedorDetalleId id) {
		this.id = id;
	}
	public String getStrDetalle() {
		return strDetalle;
	}
	public void setStrDetalle(String strDetalle) {
		this.strDetalle = strDetalle;
	}
	public String getStrMarca() {
		return strMarca;
	}
	public void setStrMarca(String strMarca) {
		this.strMarca = strMarca;
	}
	public BigDecimal getBdCantidad() {
		return bdCantidad;
	}
	public void setBdCantidad(BigDecimal bdCantidad) {
		this.bdCantidad = bdCantidad;
	}
	public Integer getIntParaTipoMoneda() {
		return intParaTipoMoneda;
	}
	public void setIntParaTipoMoneda(Integer intParaTipoMoneda) {
		this.intParaTipoMoneda = intParaTipoMoneda;
	}
	public BigDecimal getBdPrecioUnitario() {
		return bdPrecioUnitario;
	}
	public void setBdPrecioUnitario(BigDecimal bdPrecioUnitario) {
		this.bdPrecioUnitario = bdPrecioUnitario;
	}
	public Integer getIntParaEstado() {
		return intParaEstado;
	}
	public void setIntParaEstado(Integer intParaEstado) {
		this.intParaEstado = intParaEstado;
	}
	
	@Override
	public String toString() {
		return "CuadroComparativoProveedorDetalle [id=" + id + ", strDetalle="
				+ strDetalle + ", strMarca=" + strMarca + ", bdCantidad="
				+ bdCantidad + ", intParaTipoMoneda=" + intParaTipoMoneda
				+ ", bdPrecioUnitario=" + bdPrecioUnitario + ", intParaEstado="
				+ intParaEstado + "]";
	}
}
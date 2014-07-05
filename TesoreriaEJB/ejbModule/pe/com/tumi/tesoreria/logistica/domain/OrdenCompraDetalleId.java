package pe.com.tumi.tesoreria.logistica.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class OrdenCompraDetalleId extends TumiDomain{

	private Integer intPersEmpresa;
	private Integer intItemOrdenCompra;
	private Integer	intItemOrdenCompraDetalle;
	
	public Integer getIntPersEmpresa() {
		return intPersEmpresa;
	}
	public void setIntPersEmpresa(Integer intPersEmpresa) {
		this.intPersEmpresa = intPersEmpresa;
	}
	public Integer getIntItemOrdenCompra() {
		return intItemOrdenCompra;
	}
	public void setIntItemOrdenCompra(Integer intItemOrdenCompra) {
		this.intItemOrdenCompra = intItemOrdenCompra;
	}
	public Integer getIntItemOrdenCompraDetalle() {
		return intItemOrdenCompraDetalle;
	}
	public void setIntItemOrdenCompraDetalle(Integer intItemOrdenCompraDetalle) {
		this.intItemOrdenCompraDetalle = intItemOrdenCompraDetalle;
	}
	
	@Override
	public String toString() {
		return "OrdenCompraDetalleId [intPersEmpresa=" + intPersEmpresa
				+ ", intItemOrdenCompra=" + intItemOrdenCompra
				+ ", intItemOrdenCompraDetalle=" + intItemOrdenCompraDetalle
				+ "]";
	}	
}
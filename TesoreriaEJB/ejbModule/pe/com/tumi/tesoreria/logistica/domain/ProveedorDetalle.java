package pe.com.tumi.tesoreria.logistica.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class ProveedorDetalle extends TumiDomain{

	private	ProveedorDetalleId	id;
	private	Integer				intParaTipoProveedor;
	
	
	public ProveedorDetalle(){
		id = new ProveedorDetalleId();
	}
	
	public ProveedorDetalleId getId() {
		return id;
	}
	public void setId(ProveedorDetalleId id) {
		this.id = id;
	}
	public Integer getIntParaTipoProveedor() {
		return intParaTipoProveedor;
	}
	public void setIntParaTipoProveedor(Integer intParaTipoProveedor) {
		this.intParaTipoProveedor = intParaTipoProveedor;
	}
	@Override
	public String toString() {
		return "ProveedorDetalle [id=" + id + ", intParaTipoProveedor="
				+ intParaTipoProveedor + "]";
	}
	
}

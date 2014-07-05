package pe.com.tumi.tesoreria.logistica.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class ProveedorDetalleId extends TumiDomain{

	private	Integer	intPersEmpresa;
	private	Integer	intPersPersona;
	private	Integer	intItemProveedorDetalle;
	
	
	public Integer getIntPersEmpresa() {
		return intPersEmpresa;
	}
	public void setIntPersEmpresa(Integer intPersEmpresa) {
		this.intPersEmpresa = intPersEmpresa;
	}
	public Integer getIntPersPersona() {
		return intPersPersona;
	}
	public void setIntPersPersona(Integer intPersPersona) {
		this.intPersPersona = intPersPersona;
	}
	public Integer getIntItemProveedorDetalle() {
		return intItemProveedorDetalle;
	}
	public void setIntItemProveedorDetalle(Integer intItemProveedorDetalle) {
		this.intItemProveedorDetalle = intItemProveedorDetalle;
	}
	@Override
	public String toString() {
		return "ProveedorDetalleId [intPersEmpresa=" + intPersEmpresa
				+ ", intPersPersona=" + intPersPersona
				+ ", intItemProveedorDetalle=" + intItemProveedorDetalle + "]";
	}
	
	
	
}

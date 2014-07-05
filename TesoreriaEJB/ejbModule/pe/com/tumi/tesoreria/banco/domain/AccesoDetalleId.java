package pe.com.tumi.tesoreria.banco.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class AccesoDetalleId extends TumiDomain{

	private Integer	intPersEmpresaAcceso;
	private Integer	intItemAcceso;
	private	Integer	intItemAccesoDetalle;
	public Integer getIntPersEmpresaAcceso() {
		return intPersEmpresaAcceso;
	}
	public void setIntPersEmpresaAcceso(Integer intPersEmpresaAcceso) {
		this.intPersEmpresaAcceso = intPersEmpresaAcceso;
	}
	public Integer getIntItemAcceso() {
		return intItemAcceso;
	}
	public void setIntItemAcceso(Integer intItemAcceso) {
		this.intItemAcceso = intItemAcceso;
	}
	public Integer getIntItemAccesoDetalle() {
		return intItemAccesoDetalle;
	}
	public void setIntItemAccesoDetalle(Integer intItemAccesoDetalle) {
		this.intItemAccesoDetalle = intItemAccesoDetalle;
	}
	@Override
	public String toString() {
		return "AccesoDetalleId [intPersEmpresaAcceso=" + intPersEmpresaAcceso
				+ ", intItemAcceso=" + intItemAcceso
				+ ", intItemAccesoDetalle=" + intItemAccesoDetalle + "]";
	}
	
}

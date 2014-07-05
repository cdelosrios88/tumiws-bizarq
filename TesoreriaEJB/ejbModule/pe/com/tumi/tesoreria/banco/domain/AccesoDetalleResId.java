package pe.com.tumi.tesoreria.banco.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class AccesoDetalleResId extends TumiDomain{
	private Integer	intPersEmpresaAcceso;
	private Integer	intItemAcceso;
	private	Integer	intItemAccesoDetalle;
	private Integer	intItemAccesoDetalleRes;
	
	
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
	public Integer getIntItemAccesoDetalleRes() {
		return intItemAccesoDetalleRes;
	}
	public void setIntItemAccesoDetalleRes(Integer intItemAccesoDetalleRes) {
		this.intItemAccesoDetalleRes = intItemAccesoDetalleRes;
	}
	@Override
	public String toString() {
		return "AccesoDetalleResId [intPersEmpresaAcceso="
				+ intPersEmpresaAcceso + ", intItemAcceso=" + intItemAcceso
				+ ", intItemAccesoDetalle=" + intItemAccesoDetalle
				+ ", intItemAccesoDetalleRes=" + intItemAccesoDetalleRes + "]";
	}
	
}

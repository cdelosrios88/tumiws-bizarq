package pe.com.tumi.servicio.configuracion.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class ConfServDetalleId extends TumiDomain{

	private Integer intPersEmpresaPk;
	private Integer intItemSolicitud;
	private Integer intItemDetalle;
	public Integer getIntPersEmpresaPk() {
		return intPersEmpresaPk;
	}
	public void setIntPersEmpresaPk(Integer intPersEmpresaPk) {
		this.intPersEmpresaPk = intPersEmpresaPk;
	}
	public Integer getIntItemSolicitud() {
		return intItemSolicitud;
	}
	public void setIntItemSolicitud(Integer intItemSolicitud) {
		this.intItemSolicitud = intItemSolicitud;
	}
	public Integer getIntItemDetalle() {
		return intItemDetalle;
	}
	public void setIntItemDetalle(Integer intItemDetalle) {
		this.intItemDetalle = intItemDetalle;
	}
	@Override
	public String toString() {
		return "ConfServDetalleId [intPersEmpresaPk=" + intPersEmpresaPk
				+ ", intItemSolicitud=" + intItemSolicitud
				+ ", intItemDetalle=" + intItemDetalle + "]";
	}
	

}

package pe.com.tumi.servicio.configuracion.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class ConfServEstructuraDetalleId extends TumiDomain{

	private Integer intPersEmpresaPk;
	private Integer intItemSolicitud;
	private Integer intItemEstructura;
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
	public Integer getIntItemEstructura() {
		return intItemEstructura;
	}
	public void setIntItemEstructura(Integer intItemEstructura) {
		this.intItemEstructura = intItemEstructura;
	}
	@Override
	public String toString() {
		return "ConfServEstructuraDetalleId [intPersEmpresaPk="
				+ intPersEmpresaPk + ", intItemSolicitud=" + intItemSolicitud
				+ ", intItemEstructura=" + intItemEstructura + "]";
	}


}

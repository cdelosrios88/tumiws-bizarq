package pe.com.tumi.tesoreria.egreso.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class SolicitudPersonalDetalleId extends TumiDomain{

	private Integer intPersEmpresa;
	private Integer intItemSolicitudPersonal;
	private Integer intItemSolicitudPersonalDetalle;
	
	public Integer getIntPersEmpresa() {
		return intPersEmpresa;
	}
	public void setIntPersEmpresa(Integer intPersEmpresa) {
		this.intPersEmpresa = intPersEmpresa;
	}
	public Integer getIntItemSolicitudPersonal() {
		return intItemSolicitudPersonal;
	}
	public void setIntItemSolicitudPersonal(Integer intItemSolicitudPersonal) {
		this.intItemSolicitudPersonal = intItemSolicitudPersonal;
	}
	public Integer getIntItemSolicitudPersonalDetalle() {
		return intItemSolicitudPersonalDetalle;
	}
	public void setIntItemSolicitudPersonalDetalle(
			Integer intItemSolicitudPersonalDetalle) {
		this.intItemSolicitudPersonalDetalle = intItemSolicitudPersonalDetalle;
	}
	
	@Override
	public String toString() {
		return "SolicitudPersonalDetalleId [intPersEmpresa=" + intPersEmpresa
				+ ", intItemSolicitudPersonal=" + intItemSolicitudPersonal
				+ ", intItemSolicitudPersonalDetalle="
				+ intItemSolicitudPersonalDetalle + "]";
	}	
}
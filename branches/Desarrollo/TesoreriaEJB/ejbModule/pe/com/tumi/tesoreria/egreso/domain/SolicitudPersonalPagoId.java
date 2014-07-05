package pe.com.tumi.tesoreria.egreso.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class SolicitudPersonalPagoId extends TumiDomain{

	private Integer intPersEmpresa;
	private Integer intItemSolicitudPersonal;
	private Integer intItemSolicitudPersonalPago;
	
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
	public Integer getIntItemSolicitudPersonalPago() {
		return intItemSolicitudPersonalPago;
	}
	public void setIntItemSolicitudPersonalPago(Integer intItemSolicitudPersonalPago) {
		this.intItemSolicitudPersonalPago = intItemSolicitudPersonalPago;
	}
	
	@Override
	public String toString() {
		return "SolicitudPersonalPagoId [intPersEmpresa=" + intPersEmpresa
				+ ", intItemSolicitudPersonal=" + intItemSolicitudPersonal
				+ ", intItemSolicitudPersonalPago="
				+ intItemSolicitudPersonalPago + "]";
	}
}

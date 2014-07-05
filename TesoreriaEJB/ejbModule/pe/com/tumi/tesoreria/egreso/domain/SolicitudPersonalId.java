package pe.com.tumi.tesoreria.egreso.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class SolicitudPersonalId extends TumiDomain{

	private Integer intPersEmpresa;
	private Integer intItemSolicitudPersonal;
	
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
	
	@Override
	public String toString() {
		return "SolicitudPersonalId [intPersEmpresa=" + intPersEmpresa
				+ ", intItemSolicitudPersonal=" + intItemSolicitudPersonal
				+ "]";
	}
}
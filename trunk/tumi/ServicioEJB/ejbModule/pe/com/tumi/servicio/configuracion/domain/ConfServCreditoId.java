package pe.com.tumi.servicio.configuracion.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class ConfServCreditoId extends TumiDomain{

	private Integer intPersEmpresaPk;
	private Integer intItemSolicitud;
	private Integer intItemCredito;
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
	public Integer getIntItemCredito() {
		return intItemCredito;
	}
	public void setIntItemCredito(Integer intItemCredito) {
		this.intItemCredito = intItemCredito;
	}
	@Override
	public String toString() {
		return "ConfServCreditoId [intPersEmpresaPk=" + intPersEmpresaPk
				+ ", intItemSolicitud=" + intItemSolicitud
				+ ", intItemCredito=" + intItemCredito + "]";
	}

}

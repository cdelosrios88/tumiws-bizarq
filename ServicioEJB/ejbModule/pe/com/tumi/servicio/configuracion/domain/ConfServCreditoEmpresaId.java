package pe.com.tumi.servicio.configuracion.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class ConfServCreditoEmpresaId extends TumiDomain{

	private Integer intPersEmpresaPk;
	private Integer intItemSolicitud;
	private Integer intParaTipoCreditoEmpresaCod;
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
	public Integer getIntParaTipoCreditoEmpresaCod() {
		return intParaTipoCreditoEmpresaCod;
	}
	public void setIntParaTipoCreditoEmpresaCod(Integer intParaTipoCreditoEmpresaCod) {
		this.intParaTipoCreditoEmpresaCod = intParaTipoCreditoEmpresaCod;
	}
	@Override
	public String toString() {
		return "ConfServCreditoEmpresaId [intPersEmpresaPk=" + intPersEmpresaPk
				+ ", intItemSolicitud=" + intItemSolicitud
				+ ", intParaTipoCreditoEmpresaCod="
				+ intParaTipoCreditoEmpresaCod + "]";
	}

}

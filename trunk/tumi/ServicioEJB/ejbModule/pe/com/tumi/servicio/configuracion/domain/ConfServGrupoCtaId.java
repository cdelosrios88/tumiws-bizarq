package pe.com.tumi.servicio.configuracion.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class ConfServGrupoCtaId extends TumiDomain{

	private Integer intPersEmpresaPk;
	private Integer intItemSolicitud;
	private Integer intParaTipoCuentaCod;
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
	public Integer getIntParaTipoCuentaCod() {
		return intParaTipoCuentaCod;
	}
	public void setIntParaTipoCuentaCod(Integer intParaTipoCuentaCod) {
		this.intParaTipoCuentaCod = intParaTipoCuentaCod;
	}
	@Override
	public String toString() {
		return "ConfServGrupoCtaId [intPersEmpresaPk=" + intPersEmpresaPk
				+ ", intItemSolicitud=" + intItemSolicitud
				+ ", intParaTipoCuentaCod=" + intParaTipoCuentaCod + "]";
	}
	

}

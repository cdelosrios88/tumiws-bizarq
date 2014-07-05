package pe.com.tumi.servicio.configuracion.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class ConfServUsuarioId extends TumiDomain{

	private Integer intPersEmpresaPk;
	private Integer intItemSolicitud;
	private Integer intItemUsuario;
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
	public Integer getIntItemUsuario() {
		return intItemUsuario;
	}
	public void setIntItemUsuario(Integer intItemUsuario) {
		this.intItemUsuario = intItemUsuario;
	}
	@Override
	public String toString() {
		return "ConfServUsuarioId [intPersEmpresaPk=" + intPersEmpresaPk
				+ ", intItemSolicitud=" + intItemSolicitud
				+ ", intItemUsuario=" + intItemUsuario + "]";
	}
}

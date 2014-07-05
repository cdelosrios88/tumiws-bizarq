package pe.com.tumi.seguridad.permiso.domain;

import java.util.Date;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class PermisoUsuarioId extends TumiDomain {

	private Integer intPersEmpresaPk;
	private Integer intIdPerfil;
	private Date dtDesde;
	private Integer intPersPersonaPk;
	private Integer intIdTransaccion;
	
	public Integer getIntPersEmpresaPk() {
		return intPersEmpresaPk;
	}
	public void setIntPersEmpresaPk(Integer intPersEmpresaPk) {
		this.intPersEmpresaPk = intPersEmpresaPk;
	}
	public Integer getIntIdPerfil() {
		return intIdPerfil;
	}
	public void setIntIdPerfil(Integer intIdPerfil) {
		this.intIdPerfil = intIdPerfil;
	}
	public Date getDtDesde() {
		return dtDesde;
	}
	public void setDtDesde(Date dtDesde) {
		this.dtDesde = dtDesde;
	}
	public Integer getIntPersPersonaPk() {
		return intPersPersonaPk;
	}
	public void setIntPersPersonaPk(Integer intPersPersonaPk) {
		this.intPersPersonaPk = intPersPersonaPk;
	}
	public Integer getIntIdTransaccion() {
		return intIdTransaccion;
	}
	public void setIntIdTransaccion(Integer intIdTransaccion) {
		this.intIdTransaccion = intIdTransaccion;
	}

}

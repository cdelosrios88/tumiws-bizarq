package pe.com.tumi.seguridad.login.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class PerfilId extends TumiDomain {

	private Integer intPersEmpresaPk;
	private Integer intIdPerfil;
	
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
}

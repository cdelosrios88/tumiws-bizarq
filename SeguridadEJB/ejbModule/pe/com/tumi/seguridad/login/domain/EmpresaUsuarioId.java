package pe.com.tumi.seguridad.login.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class EmpresaUsuarioId extends TumiDomain {

	private Integer intPersEmpresaPk;
	private Integer intPersPersonaPk;
	
	public Integer getIntPersEmpresaPk() {
		return intPersEmpresaPk;
	}
	public void setIntPersEmpresaPk(Integer intPersEmpresaPk) {
		this.intPersEmpresaPk = intPersEmpresaPk;
	}
	public Integer getIntPersPersonaPk() {
		return intPersPersonaPk;
	}
	public void setIntPersPersonaPk(Integer intPersPersonaPk) {
		this.intPersPersonaPk = intPersPersonaPk;
	}

}

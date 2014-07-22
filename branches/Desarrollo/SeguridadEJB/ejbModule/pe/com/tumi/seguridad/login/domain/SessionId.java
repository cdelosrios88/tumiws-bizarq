package pe.com.tumi.seguridad.login.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class SessionId extends TumiDomain {
	private Integer intPersEmpresaPk;
	private Integer intPersPersonaPk;
	private Integer intSessionPk;
	
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
	public Integer getIntSessionPk() {
		return intSessionPk;
	}
	public void setIntSessionPk(Integer intSessionPk) {
		this.intSessionPk = intSessionPk;
	}
}
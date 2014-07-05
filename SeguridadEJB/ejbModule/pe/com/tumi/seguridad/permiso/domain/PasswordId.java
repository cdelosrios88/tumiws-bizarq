package pe.com.tumi.seguridad.permiso.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class PasswordId  extends TumiDomain {

	private Integer intEmpresaPk;
	private Integer intIdTransaccion;
	private Integer intIdPassword;
	
	public Integer getIntEmpresaPk() {
		return intEmpresaPk;
	}
	public void setIntEmpresaPk(Integer intEmpresaPk) {
		this.intEmpresaPk = intEmpresaPk;
	}
	public Integer getIntIdTransaccion() {
		return intIdTransaccion;
	}
	public void setIntIdTransaccion(Integer intIdTransaccion) {
		this.intIdTransaccion = intIdTransaccion;
	}
	public Integer getIntIdPassword() {
		return intIdPassword;
	}
	public void setIntIdPassword(Integer intIdPassword) {
		this.intIdPassword = intIdPassword;
	}


}

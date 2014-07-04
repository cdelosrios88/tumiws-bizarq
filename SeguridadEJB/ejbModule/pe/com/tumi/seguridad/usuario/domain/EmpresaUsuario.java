package pe.com.tumi.seguridad.usuario.domain;

public class EmpresaUsuario {
	private Integer	intIdEmpresa;
	private String	strEmpresa;
	private Integer	intIdPersona;
	private Boolean chkEmp;
	
	public Integer getIntIdEmpresa() {
		return intIdEmpresa;
	}
	public void setIntIdEmpresa(Integer intIdEmpresa) {
		this.intIdEmpresa = intIdEmpresa;
	}
	public String getStrEmpresa() {
		return strEmpresa;
	}
	public void setStrEmpresa(String strEmpresa) {
		this.strEmpresa = strEmpresa;
	}
	public Integer getIntIdPersona() {
		return intIdPersona;
	}
	public void setIntIdPersona(Integer intIdPersona) {
		this.intIdPersona = intIdPersona;
	}
	public Boolean getChkEmp() {
		return chkEmp;
	}
	public void setChkEmp(Boolean chkEmp) {
		this.chkEmp = chkEmp;
	}
}

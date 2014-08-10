package pe.com.tumi.seguridad.permiso.domain;

public class LiquidateSession {
	private Integer intPersEmpresa;
	private String strUsuario;
	private Integer intEstado;
	private Integer intCboSucursalEmp;
	

	public Integer getIntCboSucursalEmp() {
		return intCboSucursalEmp;
	}

	public void setIntCboSucursalEmp(Integer intCboSucursalEmp) {
		this.intCboSucursalEmp = intCboSucursalEmp;
	}

	public Integer getIntEstado() {
		return intEstado;
	}

	public void setIntEstado(Integer intEstado) {
		this.intEstado = intEstado;
	}

	public String getStrUsuario() {
		return strUsuario;
	}

	public void setStrUsuario(String strUsuario) {
		this.strUsuario = strUsuario;
	}

	public Integer getIntPersEmpresa() {
		return intPersEmpresa;
	}

	public void setIntPersEmpresa(Integer intPersEmpresa) {
		this.intPersEmpresa = intPersEmpresa;
	}
	
}

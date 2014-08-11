package pe.com.tumi.seguridad.permiso.domain;

import java.sql.Timestamp;

public class LiquidateSession {
	private Integer intPersEmpresa;
	private String strUsuario;
	private Integer intEstado;
	private Integer intCboSucursalEmp;
	private Timestamp fechaInicioFiltro;
	private Timestamp fechaFinFiltro;
	

	public Timestamp getFechaInicioFiltro() {
		return fechaInicioFiltro;
	}

	public void setFechaInicioFiltro(Timestamp fechaInicioFiltro) {
		this.fechaInicioFiltro = fechaInicioFiltro;
	}

	public Timestamp getFechaFinFiltro() {
		return fechaFinFiltro;
	}

	public void setFechaFinFiltro(Timestamp fechaFinFiltro) {
		this.fechaFinFiltro = fechaFinFiltro;
	}

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

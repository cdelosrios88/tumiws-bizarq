/************************************************************************
/* Nombre de componente: LiquidateSession
 * Descripción: Bean utilizado para manejar los filtros de búsqueda
 * Cod. Req.: REQ14-003
 * Autor : Luis Polanco  Fecha:12/08/2014 16:20:00
 * Versión : v1.0 - Creacion de componente 
 * Fecha creación : 12/08/2014
/* ********************************************************************* */
package pe.com.tumi.seguridad.permiso.domain;

import java.sql.Timestamp;
import java.util.Date;

public class LiquidateSession {
	private Integer intPersEmpresa;
	private String strUsuario;
	private Integer intEstado;
	private Integer intCboSucursalEmp;
	private Date fechaInicioFiltro;
	private Date fechaFinFiltro;

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

	public Date getFechaInicioFiltro() {
		return fechaInicioFiltro;
	}

	public void setFechaInicioFiltro(Date fechaInicioFiltro) {
		this.fechaInicioFiltro = fechaInicioFiltro;
	}

	public Date getFechaFinFiltro() {
		return fechaFinFiltro;
	}

	public void setFechaFinFiltro(Date fechaFinFiltro) {
		this.fechaFinFiltro = fechaFinFiltro;
	}
	
}
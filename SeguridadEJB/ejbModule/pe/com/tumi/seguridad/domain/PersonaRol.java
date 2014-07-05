package pe.com.tumi.seguridad.domain;

import java.util.Date;

public class PersonaRol extends Rol{
	
	private Integer intIdEmpresa;
	private Integer intIdPersona;
	private Date 	dtFechaIni;
	private String 	strFechaIni;
	private Date 	dtFechaFin;
	private String  strFechaFin;
	private Integer intEstado;
	
	
	public Integer getIntIdEmpresa() {
		return intIdEmpresa;
	}
	public void setIntIdEmpresa(Integer intIdEmpresa) {
		this.intIdEmpresa = intIdEmpresa;
	}
	public Integer getIntIdPersona() {
		return intIdPersona;
	}
	public void setIntIdPersona(Integer intIdPersona) {
		this.intIdPersona = intIdPersona;
	}
	public Date getDtFechaIni() {
		return dtFechaIni;
	}
	public void setDtFechaIni(Date dtFechaIni) {
		this.dtFechaIni = dtFechaIni;
	}
	public String getStrFechaIni() {
		return strFechaIni;
	}
	public void setStrFechaIni(String strFechaIni) {
		this.strFechaIni = strFechaIni;
	}
	public Date getDtFechaFin() {
		return dtFechaFin;
	}
	public void setDtFechaFin(Date dtFechaFin) {
		this.dtFechaFin = dtFechaFin;
	}
	public String getStrFechaFin() {
		return strFechaFin;
	}
	public void setStrFechaFin(String strFechaFin) {
		this.strFechaFin = strFechaFin;
	}
	public Integer getIntEstado() {
		return intEstado;
	}
	public void setIntEstado(Integer intEstado) {
		this.intEstado = intEstado;
	}
	
}

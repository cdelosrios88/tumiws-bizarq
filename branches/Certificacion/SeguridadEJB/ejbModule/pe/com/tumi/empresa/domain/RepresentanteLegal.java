package pe.com.tumi.empresa.domain;

import java.util.Date;
import java.util.List;

public class RepresentanteLegal extends PerNatural {

	private 	Integer 	intIdEmpresa;
	private 	Integer 	intIdPerJuridica;
	private 	Integer 	intTipoVinculo;
	private 	String 		strRoles;
	private 	Date 		dtFechaInicio;
	private 	String 		strFechaInicio;
	private 	Date 		dtFechaFin;
	private 	String 		strFechaFin;
	private 	List 		cursorLista;
	
	
	public Integer getIntIdEmpresa() {
		return intIdEmpresa;
	}
	public void setIntIdEmpresa(Integer intIdEmpresa) {
		this.intIdEmpresa = intIdEmpresa;
	}
	public Integer getIntTipoVinculo() {
		return intTipoVinculo;
	}
	public void setIntTipoVinculo(Integer intTipoVinculo) {
		this.intTipoVinculo = intTipoVinculo;
	}
	public String getStrRoles() {
		return strRoles;
	}
	public void setStrRoles(String strRoles) {
		this.strRoles = strRoles;
	}
	public List getCursorLista() {
		return cursorLista;
	}
	public void setCursorLista(List cursorLista) {
		this.cursorLista = cursorLista;
	}
	public Date getDtFechaInicio() {
		return dtFechaInicio;
	}
	public void setDtFechaInicio(Date dtFechaInicio) {
		this.dtFechaInicio = dtFechaInicio;
	}
	public Date getDtFechaFin() {
		return dtFechaFin;
	}
	public void setDtFechaFin(Date dtFechaFin) {
		this.dtFechaFin = dtFechaFin;
	}
	public String getStrFechaInicio() {
		return strFechaInicio;
	}
	public void setStrFechaInicio(String strFechaInicio) {
		this.strFechaInicio = strFechaInicio;
	}
	public String getStrFechaFin() {
		return strFechaFin;
	}
	public void setStrFechaFin(String strFechaFin) {
		this.strFechaFin = strFechaFin;
	}
	public Integer getIntIdPerJuridica() {
		return intIdPerJuridica;
	}
	public void setIntIdPerJuridica(Integer intIdPerJuridica) {
		this.intIdPerJuridica = intIdPerJuridica;
	}
	
}

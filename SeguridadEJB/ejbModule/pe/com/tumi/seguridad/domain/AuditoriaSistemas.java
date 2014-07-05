package pe.com.tumi.seguridad.domain;

import java.util.Date;
import java.util.List;

public class AuditoriaSistemas {
	private 	Integer 	intIdAuditoria;
	private 	String 		strTabla;
	private 	String 		strColumna;
	private 	Integer 	intIdEmpresa;
	private 	String 		strValorAnterior;
	private 	String 		strValorNuevo;
	private 	String 		strFechaRegistro;
	private 	Date		dtFechaRegistro;
	private 	Integer 	intIdUsuario;
	
	//para la busqueda
	private 	String 		strFechaInicio;
	private 	String 		strFechaFin;
	private 	List		cursorLista;
	private 	Integer 	intValoresNull;
	private 	Integer 	intValoresCero;
	private 	String	 	strOperadorLogico;
	private 	String 		strValorColumna;
	
	public Integer getIntIdAuditoria() {
		return intIdAuditoria;
	}
	public void setIntIdAuditoria(Integer intIdAuditoria) {
		this.intIdAuditoria = intIdAuditoria;
	}
	public String getStrTabla() {
		return strTabla;
	}
	public void setStrTabla(String strTabla) {
		this.strTabla = strTabla;
	}
	public String getStrColumna() {
		return strColumna;
	}
	public void setStrColumna(String strColumna) {
		this.strColumna = strColumna;
	}
	public Integer getIntIdEmpresa() {
		return intIdEmpresa;
	}
	public void setIntIdEmpresa(Integer intIdEmpresa) {
		this.intIdEmpresa = intIdEmpresa;
	}
	public String getStrValorAnterior() {
		return strValorAnterior;
	}
	public void setStrValorAnterior(String strValorAnterior) {
		this.strValorAnterior = strValorAnterior;
	}
	public String getStrValorNuevo() {
		return strValorNuevo;
	}
	public void setStrValorNuevo(String strValorNuevo) {
		this.strValorNuevo = strValorNuevo;
	}
	public String getStrFechaRegistro() {
		return strFechaRegistro;
	}
	public void setStrFechaRegistro(String strFechaRegistro) {
		this.strFechaRegistro = strFechaRegistro;
	}
	public Date getDtFechaRegistro() {
		return dtFechaRegistro;
	}
	public void setDtFechaRegistro(Date dtFechaRegistro) {
		this.dtFechaRegistro = dtFechaRegistro;
	}
	public Integer getIntIdUsuario() {
		return intIdUsuario;
	}
	public void setIntIdUsuario(Integer intIdUsuario) {
		this.intIdUsuario = intIdUsuario;
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
	public List getCursorLista() {
		return cursorLista;
	}
	public void setCursorLista(List cursorLista) {
		this.cursorLista = cursorLista;
	}
	public Integer getIntValoresNull() {
		return intValoresNull;
	}
	public void setIntValoresNull(Integer intValoresNull) {
		this.intValoresNull = intValoresNull;
	}
	public Integer getIntValoresCero() {
		return intValoresCero;
	}
	public void setIntValoresCero(Integer intValoresCero) {
		this.intValoresCero = intValoresCero;
	}
	public String getStrOperadorLogico() {
		return strOperadorLogico;
	}
	public void setStrOperadorLogico(String strOperadorLogico) {
		this.strOperadorLogico = strOperadorLogico;
	}
	public String getStrValorColumna() {
		return strValorColumna;
	}
	public void setStrValorColumna(String strValorColumna) {
		this.strValorColumna = strValorColumna;
	}
	
}

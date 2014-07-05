package pe.com.tumi.seguridad.domain;

import java.util.Date;
import java.util.List;

public class Sesiones {
	private 	Integer 	intIdEmpresa;
	private 	Integer 	intIdUsuario;
	private 	Date 		dtFechaRegistro;
	private 	String 		strFechaRegistro;
	private 	Integer 	intRangoFechas;
	private 	Date 		dtFechaFin;
	private 	String 		strFechaFin;
	private 	Integer 	intIdEmpSucursal;
	private 	Integer 	intIdSucursal;
	private 	String 		strSucursal;
	private 	Integer 	intTipoSucursal;
	private 	Integer 	intAccesoRemoto;
	private 	Integer 	intEstadoSesion;
	//para la busqueda
	private 	String 		strUsuario;
	private 	String 		strFecha;
	private 	String 		strHoraIni;
	private 	String 		strHoraFin;
	private 	String 		strEstado;
	private 	List 		cursorLista;
	private 	Boolean 	blnSeleccionado;
	
	
	public Integer getIntIdUsuario() {
		return intIdUsuario;
	}
	public void setIntIdUsuario(Integer intIdUsuario) {
		this.intIdUsuario = intIdUsuario;
	}
	public String getStrUsuario() {
		return strUsuario;
	}
	public void setStrUsuario(String strUsuario) {
		this.strUsuario = strUsuario;
	}
	public String getStrFecha() {
		return strFecha;
	}
	public void setStrFecha(String strFecha) {
		this.strFecha = strFecha;
	}
	public String getStrHoraIni() {
		return strHoraIni;
	}
	public void setStrHoraIni(String strHoraIni) {
		this.strHoraIni = strHoraIni;
	}
	public String getStrHoraFin() {
		return strHoraFin;
	}
	public void setStrHoraFin(String strHoraFin) {
		this.strHoraFin = strHoraFin;
	}
	public String getStrEstado() {
		return strEstado;
	}
	public void setStrEstado(String strEstado) {
		this.strEstado = strEstado;
	}
	public Integer getIntIdEmpresa() {
		return intIdEmpresa;
	}
	public void setIntIdEmpresa(Integer intIdEmpresa) {
		this.intIdEmpresa = intIdEmpresa;
	}
	public Date getDtFechaRegistro() {
		return dtFechaRegistro;
	}
	public void setDtFechaRegistro(Date dtFechaRegistro) {
		this.dtFechaRegistro = dtFechaRegistro;
	}
	public String getStrFechaRegistro() {
		return strFechaRegistro;
	}
	public void setStrFechaRegistro(String strFechaRegistro) {
		this.strFechaRegistro = strFechaRegistro;
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
	public Integer getIntIdEmpSucursal() {
		return intIdEmpSucursal;
	}
	public void setIntIdEmpSucursal(Integer intIdEmpSucursal) {
		this.intIdEmpSucursal = intIdEmpSucursal;
	}
	public Integer getIntIdSucursal() {
		return intIdSucursal;
	}
	public void setIntIdSucursal(Integer intIdSucursal) {
		this.intIdSucursal = intIdSucursal;
	}
	public Integer getIntAccesoRemoto() {
		return intAccesoRemoto;
	}
	public void setIntAccesoRemoto(Integer intAccesoRemoto) {
		this.intAccesoRemoto = intAccesoRemoto;
	}
	public Integer getIntEstadoSesion() {
		return intEstadoSesion;
	}
	public void setIntEstadoSesion(Integer intEstadoSesion) {
		this.intEstadoSesion = intEstadoSesion;
	}
	public Integer getIntTipoSucursal() {
		return intTipoSucursal;
	}
	public void setIntTipoSucursal(Integer intTipoSucursal) {
		this.intTipoSucursal = intTipoSucursal;
	}
	public Integer getIntRangoFechas() {
		return intRangoFechas;
	}
	public void setIntRangoFechas(Integer intRangoFechas) {
		this.intRangoFechas = intRangoFechas;
	}
	public List getCursorLista() {
		return cursorLista;
	}
	public void setCursorLista(List cursorLista) {
		this.cursorLista = cursorLista;
	}
	public String getStrSucursal() {
		return strSucursal;
	}
	public void setStrSucursal(String strSucursal) {
		this.strSucursal = strSucursal;
	}
	public Boolean getBlnSeleccionado() {
		return blnSeleccionado;
	}
	public void setBlnSeleccionado(Boolean blnSeleccionado) {
		this.blnSeleccionado = blnSeleccionado;
	}
}

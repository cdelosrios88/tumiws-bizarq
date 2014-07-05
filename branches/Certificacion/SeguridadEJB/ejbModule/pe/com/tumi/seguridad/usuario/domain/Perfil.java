package pe.com.tumi.seguridad.usuario.domain;

import java.util.Date;

public class Perfil {
	private 	Integer	intIdEmpresa;
	private		String	strEmpresaPerfil;
	private		Integer intIdPerfil;
	private		String	strDescripcion;
	private		Integer	intIdTipoPerfil;
	private		String	daFecIni;
	private		String	daFecFin;
	private		Integer	intIdEstado;
	private		String	daFecReg;
	private 	Boolean chkRanFec;
	private		Integer	chkEstadoPerfDet;
	private		String  strIdTransaccion;
	private		String  strRanFechas;
	private		String  strEstado;
	
	//Getters y Setters
	public Integer getIntIdEmpresa() {
		return intIdEmpresa;
	}
	public void setIntIdEmpresa(Integer intIdEmpresa) {
		this.intIdEmpresa = intIdEmpresa;
	}
	public String getStrEmpresaPerfil() {
		return strEmpresaPerfil;
	}
	public void setStrEmpresaPerfil(String strEmpresaPerfil) {
		this.strEmpresaPerfil = strEmpresaPerfil;
	}
	public Integer getIntIdPerfil() {
		return intIdPerfil;
	}
	public void setIntIdPerfil(Integer intIdPerfil) {
		this.intIdPerfil = intIdPerfil;
	}
	public String getStrDescripcion() {
		return strDescripcion;
	}
	public void setStrDescripcion(String strDescripcion) {
		this.strDescripcion = strDescripcion;
	}
	public Integer getIntIdTipoPerfil() {
		return intIdTipoPerfil;
	}
	public void setIntIdTipoPerfil(Integer intIdTipoPerfil) {
		this.intIdTipoPerfil = intIdTipoPerfil;
	}
	public String getDaFecIni() {
		return daFecIni;
	}
	public void setDaFecIni(String daFecIni) {
		this.daFecIni = daFecIni;
	}
	public String getDaFecFin() {
		return daFecFin;
	}
	public void setDaFecFin(String daFecFin) {
		this.daFecFin = daFecFin;
	}
	public Integer getIntIdEstado() {
		return intIdEstado;
	}
	public void setIntIdEstado(Integer intIdEstado) {
		this.intIdEstado = intIdEstado;
	}
	public String getDaFecReg() {
		return daFecReg;
	}
	public void setDaFecReg(String daFecReg) {
		this.daFecReg = daFecReg;
	}
	public Boolean getChkRanFec() {
		return chkRanFec;
	}
	public void setChkRanFec(Boolean chkRanFec) {
		this.chkRanFec = chkRanFec;
	}
	public Integer getChkEstadoPerfDet() {
		return chkEstadoPerfDet;
	}
	public void setChkEstadoPerfDet(Integer chkEstadoPerfDet) {
		this.chkEstadoPerfDet = chkEstadoPerfDet;
	}
	public String getStrIdTransaccion() {
		return strIdTransaccion;
	}
	public void setStrIdTransaccion(String strIdTransaccion) {
		this.strIdTransaccion = strIdTransaccion;
	}
	public String getStrRanFechas() {
		return strRanFechas;
	}
	public void setStrRanFechas(String strRanFechas) {
		this.strRanFechas = strRanFechas;
	}
	public String getStrEstado() {
		return strEstado;
	}
	public void setStrEstado(String strEstado) {
		this.strEstado = strEstado;
	}
}

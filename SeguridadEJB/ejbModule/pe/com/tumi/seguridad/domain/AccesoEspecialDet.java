package pe.com.tumi.seguridad.domain;

import java.util.Date;

public class AccesoEspecialDet {
	private 	Integer 	intIdEmpresa;
	private 	Integer 	intIdUsuario;
	private 	String 		strFechaInicio;
	private 	Integer 	intIdDia;
	private 	String	 	strDia;
	private 	String 		strHoraInicio;
	private 	String 		strHoraFin;
	private 	Integer 	intConta;
	
	
	public Integer getIntIdEmpresa() {
		return intIdEmpresa;
	}
	public void setIntIdEmpresa(Integer intIdEmpresa) {
		this.intIdEmpresa = intIdEmpresa;
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
	public Integer getIntIdDia() {
		return intIdDia;
	}
	public void setIntIdDia(Integer intIdDia) {
		this.intIdDia = intIdDia;
	}
	public String getStrHoraInicio() {
		return strHoraInicio;
	}
	public void setStrHoraInicio(String strHoraInicio) {
		this.strHoraInicio = strHoraInicio;
	}
	public String getStrHoraFin() {
		return strHoraFin;
	}
	public void setStrHoraFin(String strHoraFin) {
		this.strHoraFin = strHoraFin;
	}
	public String getStrDia() {
		return strDia;
	}
	public void setStrDia(String strDia) {
		this.strDia = strDia;
	}
	public Integer getIntConta() {
		return intConta;
	}
	public void setIntConta(Integer intConta) {
		this.intConta = intConta;
	}
	
}

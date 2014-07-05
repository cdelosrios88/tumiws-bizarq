package pe.com.tumi.credito.socio.convenio.domain.composite;

import pe.com.tumi.credito.socio.captacion.domain.Captacion;
import pe.com.tumi.credito.socio.captacion.domain.Condicion;
import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class CaptacionComp extends TumiDomain{
	private	Captacion 		captacion;
	private	Condicion 		condicion;
	private String			strCondicionSocio;
	private String			strCtasConsideradas;
	private Boolean			chkCaptacion;
	
	//Getters y Setters
	public Captacion getCaptacion() {
		return captacion;
	}
	public void setCaptacion(Captacion captacion) {
		this.captacion = captacion;
	}
	public Condicion getCondicion() {
		return condicion;
	}
	public void setCondicion(Condicion condicion) {
		this.condicion = condicion;
	}
	public String getStrCondicionSocio() {
		return strCondicionSocio;
	}
	public void setStrCondicionSocio(String strCondicionSocio) {
		this.strCondicionSocio = strCondicionSocio;
	}
	public String getStrCtasConsideradas() {
		return strCtasConsideradas;
	}
	public void setStrCtasConsideradas(String strCtasConsideradas) {
		this.strCtasConsideradas = strCtasConsideradas;
	}
	public Boolean getChkCaptacion() {
		return chkCaptacion;
	}
	public void setChkCaptacion(Boolean chkCaptacion) {
		this.chkCaptacion = chkCaptacion;
	}
}

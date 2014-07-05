package pe.com.tumi.credito.socio.credito.domain.composite;

import pe.com.tumi.credito.socio.creditos.domain.CreditoTipoGarantia;
import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class CreditoTipoGarantiaComp extends TumiDomain{
	private CreditoTipoGarantia creditoTipoGarantia;
	private String 				strCondicionSocio;
	private String 				strCondicionLaboral;
	private String 				strSituacionLaboral;
	
	public CreditoTipoGarantia getCreditoTipoGarantia() {
		return creditoTipoGarantia;
	}
	public void setCreditoTipoGarantia(CreditoTipoGarantia creditoTipoGarantia) {
		this.creditoTipoGarantia = creditoTipoGarantia;
	}
	public String getStrCondicionSocio() {
		return strCondicionSocio;
	}
	public void setStrCondicionSocio(String strCondicionSocio) {
		this.strCondicionSocio = strCondicionSocio;
	}
	public String getStrCondicionLaboral() {
		return strCondicionLaboral;
	}
	public void setStrCondicionLaboral(String strCondicionLaboral) {
		this.strCondicionLaboral = strCondicionLaboral;
	}
	public String getStrSituacionLaboral() {
		return strSituacionLaboral;
	}
	public void setStrSituacionLaboral(String strSituacionLaboral) {
		this.strSituacionLaboral = strSituacionLaboral;
	}
}

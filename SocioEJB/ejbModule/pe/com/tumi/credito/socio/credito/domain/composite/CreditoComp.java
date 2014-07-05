package pe.com.tumi.credito.socio.credito.domain.composite;

import pe.com.tumi.credito.socio.creditos.domain.CondicionCredito;
import pe.com.tumi.credito.socio.creditos.domain.Credito;
import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class CreditoComp extends TumiDomain{
	private	Credito 			credito;
	private	CondicionCredito 	condicionCredito;
	private String				strCondicionSocio;
	private Boolean				chkCredito;
	
	//Getters y Setters
	public Credito getCredito() {
		return credito;
	}
	public void setCredito(Credito credito) {
		this.credito = credito;
	}
	public CondicionCredito getCondicionCredito() {
		return condicionCredito;
	}
	public void setCondicionCredito(CondicionCredito condicionCredito) {
		this.condicionCredito = condicionCredito;
	}
	public String getStrCondicionSocio() {
		return strCondicionSocio;
	}
	public void setStrCondicionSocio(String strCondicionSocio) {
		this.strCondicionSocio = strCondicionSocio;
	}
	public Boolean getChkCredito() {
		return chkCredito;
	}
	public void setChkCredito(Boolean chkCredito) {
		this.chkCredito = chkCredito;
	}
}
package pe.com.tumi.servicio.solicitudPrestamo.domain.composite;

import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.servicio.solicitudPrestamo.domain.AutorizaCredito;

public class AutorizaCreditoComp extends TumiDomain {
	private AutorizaCredito autorizaCredito;
	private Persona persona;
	private String strPerfil;
	
	public String getStrPerfil() {
		return strPerfil;
	}
	public void setStrPerfil(String strPerfil) {
		this.strPerfil = strPerfil;
	}
	public AutorizaCredito getAutorizaCredito() {
		return autorizaCredito;
	}
	public void setAutorizaCredito(AutorizaCredito autorizaCredito) {
		this.autorizaCredito = autorizaCredito;
	}
	public Persona getPersona() {
		return persona;
	}
	public void setPersona(Persona persona) {
		this.persona = persona;
	}
}

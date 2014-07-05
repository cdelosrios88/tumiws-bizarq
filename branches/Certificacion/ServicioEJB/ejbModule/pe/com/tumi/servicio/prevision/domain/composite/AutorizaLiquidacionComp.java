package pe.com.tumi.servicio.prevision.domain.composite;

import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.servicio.prevision.domain.AutorizaLiquidacion;

public class AutorizaLiquidacionComp extends TumiDomain{
	private AutorizaLiquidacion autorizaLiquidacion;
	private Persona persona;
	private String strPerfil;
	
	public AutorizaLiquidacion getAutorizaLiquidacion() {
		return autorizaLiquidacion;
	}
	public void setAutorizaLiquidacion(AutorizaLiquidacion autorizaLiquidacion) {
		this.autorizaLiquidacion = autorizaLiquidacion;
	}
	public Persona getPersona() {
		return persona;
	}
	public void setPersona(Persona persona) {
		this.persona = persona;
	}
	public String getStrPerfil() {
		return strPerfil;
	}
	public void setStrPerfil(String strPerfil) {
		this.strPerfil = strPerfil;
	}
	
	
}

package pe.com.tumi.servicio.prevision.domain.composite;

import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.servicio.prevision.domain.AutorizaPrevision;

public class AutorizaPrevisionComp extends TumiDomain {
		private AutorizaPrevision autorizaPrevision;
		private Persona persona;
		private String strPerfil;
		
		public AutorizaPrevision getAutorizaPrevision() {
			return autorizaPrevision;
		}
		public void setAutorizaPrevision(AutorizaPrevision autorizaPrevision) {
			this.autorizaPrevision = autorizaPrevision;
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

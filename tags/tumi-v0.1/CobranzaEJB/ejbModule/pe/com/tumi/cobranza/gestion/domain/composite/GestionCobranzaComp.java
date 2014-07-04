package pe.com.tumi.cobranza.gestion.domain.composite;

import pe.com.tumi.cobranza.gestion.domain.GestionCobranza;
import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class GestionCobranzaComp extends TumiDomain{
	
	private GestionCobranza gestionCobranza;

	public GestionCobranza getGestionCobranza() {
		return gestionCobranza;
	}

	public void setGestionCobranza(GestionCobranza gestionCobranza) {
		this.gestionCobranza = gestionCobranza;
	}
	

}

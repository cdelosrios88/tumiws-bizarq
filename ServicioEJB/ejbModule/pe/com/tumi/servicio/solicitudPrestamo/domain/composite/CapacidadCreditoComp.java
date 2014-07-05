package pe.com.tumi.servicio.solicitudPrestamo.domain.composite;

import java.util.List;

import pe.com.tumi.credito.socio.core.domain.SocioEstructura;
import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.servicio.solicitudPrestamo.domain.CapacidadCredito;

public class CapacidadCreditoComp extends TumiDomain {
	private SocioEstructura socioEstructura;
	private CapacidadCredito capacidadCredito;
	
	public SocioEstructura getSocioEstructura() {
		return socioEstructura;
	}
	public void setSocioEstructura(SocioEstructura socioEstructura) {
		this.socioEstructura = socioEstructura;
	}
	public CapacidadCredito getCapacidadCredito() {
		return capacidadCredito;
	}
	public void setCapacidadCredito(CapacidadCredito capacidadCredito) {
		this.capacidadCredito = capacidadCredito;
	}
}

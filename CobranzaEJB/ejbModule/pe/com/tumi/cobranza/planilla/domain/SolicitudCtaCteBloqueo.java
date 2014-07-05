package pe.com.tumi.cobranza.planilla.domain;

import java.util.Date;
import java.util.List;

import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.Cuenta;
import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.movimiento.concepto.domain.Movimiento;

public class SolicitudCtaCteBloqueo extends TumiDomain{
	
	SolicitudCtaCteBloqueoId id;
	private Integer intCmovItemblcu;

	
	public SolicitudCtaCteBloqueo(){
		id = new SolicitudCtaCteBloqueoId();
	}

	public SolicitudCtaCteBloqueoId getId() {
		return id;
	}


	public void setId(SolicitudCtaCteBloqueoId id) {
		this.id = id;
	}


	public Integer getIntCmovItemblcu() {
		return intCmovItemblcu;
	}


	public void setIntCmovItemblcu(Integer intCmovItemblcu) {
		this.intCmovItemblcu = intCmovItemblcu;
	}
	
	
	
	
}

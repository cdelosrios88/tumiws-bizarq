package pe.com.tumi.credito.socio.captacion.domain;

import pe.com.tumi.credito.socio.estructura.domain.ConvenioEstructuraDetalle;
import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class EstructuraCaptacion extends TumiDomain {

	private EstructuraCaptacionId id;
	private ConvenioEstructuraDetalle convenioEstructuraDetalle;
	
	public EstructuraCaptacionId getId() {
		return id;
	}
	public void setId(EstructuraCaptacionId id) {
		this.id = id;
	}
	public ConvenioEstructuraDetalle getConvenioDetalle() {
		return convenioEstructuraDetalle;
	}
	public void setConvenioDetalle(ConvenioEstructuraDetalle convenioEstructuraDetalle) {
		this.convenioEstructuraDetalle = convenioEstructuraDetalle;
	} 
	
}

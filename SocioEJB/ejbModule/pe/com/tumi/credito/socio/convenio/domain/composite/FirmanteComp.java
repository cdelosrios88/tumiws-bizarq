package pe.com.tumi.credito.socio.convenio.domain.composite;

import java.util.List;

import pe.com.tumi.credito.socio.convenio.domain.Firmante;
import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.persona.core.domain.Persona;

public class FirmanteComp extends TumiDomain {
	private Firmante firmante;
	private List<Persona> listaPersona;
	private Boolean	chkFirmante;
	
	public Firmante getFirmante() {
		return firmante;
	}
	public void setFirmante(Firmante firmante) {
		this.firmante = firmante;
	}
	public List<Persona> getListaPersona() {
		return listaPersona;
	}
	public void setListaPersona(List<Persona> listaPersona) {
		this.listaPersona = listaPersona;
	}
	public Boolean getChkFirmante() {
		return chkFirmante;
	}
	public void setChkFirmante(Boolean chkFirmante) {
		this.chkFirmante = chkFirmante;
	}
}

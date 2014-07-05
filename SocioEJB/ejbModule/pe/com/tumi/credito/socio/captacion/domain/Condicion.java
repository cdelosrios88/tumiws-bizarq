package pe.com.tumi.credito.socio.captacion.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class Condicion extends TumiDomain {

	private CondicionId id;
	private Captacion 	captacion;
	private Integer 	intValor;
	private Boolean		chkSocio;//plrt
	
	public CondicionId getId() {
		return id;
	}
	public void setId(CondicionId id) {
		this.id = id;
	}
	public Captacion getCaptacion() {
		return captacion;
	}
	public void setCaptacion(Captacion captacion) {
		this.captacion = captacion;
	}
	public Integer getIntValor() {
		return intValor;
	}
	public void setIntValor(Integer intValor) {
		this.intValor = intValor;
	}
	public Boolean getChkSocio() {
		return chkSocio;
	}
	public void setChkSocio(Boolean chkSocio) {
		this.chkSocio = chkSocio;
	}

}

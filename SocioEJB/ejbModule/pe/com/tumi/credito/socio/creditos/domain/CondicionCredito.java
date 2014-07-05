package pe.com.tumi.credito.socio.creditos.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class CondicionCredito extends TumiDomain {

	private CondicionCreditoId id;
	private Integer 	intValor;
	private Boolean		chkSocio;
	
	public CondicionCreditoId getId() {
		return id;
	}
	public void setId(CondicionCreditoId id) {
		this.id = id;
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

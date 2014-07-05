package pe.com.tumi.credito.socio.creditos.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class CreditoTopeCaptacion extends TumiDomain {
	private CreditoTopeCaptacionId id;
	private Integer intValor;
	
	//Getters y Setters
	public CreditoTopeCaptacionId getId() {
		return id;
	}
	public void setId(CreditoTopeCaptacionId id) {
		this.id = id;
	}
	public Integer getIntValor() {
		return intValor;
	}
	public void setIntValor(Integer intValor) {
		this.intValor = intValor;
	}
}

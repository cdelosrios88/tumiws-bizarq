package pe.com.tumi.credito.socio.creditos.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class CreditoDescuentoCaptacion extends TumiDomain {
	private CreditoDescuentoCaptacionId id;
	private Integer intValor;
	
	
	public CreditoDescuentoCaptacionId getId() {
		return id;
	}
	public void setId(CreditoDescuentoCaptacionId id) {
		this.id = id;
	}
	public Integer getIntValor() {
		return intValor;
	}
	public void setIntValor(Integer intValor) {
		this.intValor = intValor;
	}
}

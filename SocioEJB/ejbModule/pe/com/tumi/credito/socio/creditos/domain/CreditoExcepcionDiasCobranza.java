package pe.com.tumi.credito.socio.creditos.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class CreditoExcepcionDiasCobranza extends TumiDomain {
	private CreditoExcepcionDiasCobranzaId id;
	private Integer intValor;
	public CreditoExcepcionDiasCobranzaId getId() {
		return id;
	}
	public void setId(CreditoExcepcionDiasCobranzaId id) {
		this.id = id;
	}
	public Integer getIntValor() {
		return intValor;
	}
	public void setIntValor(Integer intValor) {
		this.intValor = intValor;
	}
}

package pe.com.tumi.credito.socio.creditos.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class CreditoGarantiaTipoValorVenta extends TumiDomain {
	private CreditoGarantiaTipoValorVentaId id;
	private Integer intValor;
	
	//Getters y Setters
	public CreditoGarantiaTipoValorVentaId getId() {
		return id;
	}
	public void setId(CreditoGarantiaTipoValorVentaId id) {
		this.id = id;
	}
	public Integer getIntValor() {
		return intValor;
	}
	public void setIntValor(Integer intValor) {
		this.intValor = intValor;
	}
}

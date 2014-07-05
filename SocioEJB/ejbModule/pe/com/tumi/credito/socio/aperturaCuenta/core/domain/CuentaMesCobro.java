package pe.com.tumi.credito.socio.aperturaCuenta.core.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class CuentaMesCobro extends TumiDomain {
	private CuentaMesCobroId id;
	private Integer intValor;
	
	public CuentaMesCobroId getId() {
		return id;
	}
	public void setId(CuentaMesCobroId id) {
		this.id = id;
	}
	public Integer getIntValor() {
		return intValor;
	}
	public void setIntValor(Integer intValor) {
		this.intValor = intValor;
	}
}
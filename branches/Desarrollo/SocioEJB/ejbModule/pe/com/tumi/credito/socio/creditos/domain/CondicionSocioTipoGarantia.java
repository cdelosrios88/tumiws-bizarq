package pe.com.tumi.credito.socio.creditos.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class CondicionSocioTipoGarantia extends TumiDomain {
	private CondicionSocioTipoGarantiaId id;
	private Integer intValor;
	
	public CondicionSocioTipoGarantiaId getId() {
		return id;
	}
	public void setId(CondicionSocioTipoGarantiaId id) {
		this.id = id;
	}
	public Integer getIntValor() {
		return intValor;
	}
	public void setIntValor(Integer intValor) {
		this.intValor = intValor;
	}
}

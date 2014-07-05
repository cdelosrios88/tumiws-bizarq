package pe.com.tumi.credito.socio.creditos.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class CondicionHabilTipoGarantia extends TumiDomain {
	private CondicionHabilTipoGarantiaId id;
	private Integer intValor;
	
	public CondicionHabilTipoGarantiaId getId() {
		return id;
	}
	public void setId(CondicionHabilTipoGarantiaId id) {
		this.id = id;
	}
	public Integer getIntValor() {
		return intValor;
	}
	public void setIntValor(Integer intValor) {
		this.intValor = intValor;
	}
}

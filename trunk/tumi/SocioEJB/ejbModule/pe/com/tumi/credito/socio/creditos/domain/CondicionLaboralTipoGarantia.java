package pe.com.tumi.credito.socio.creditos.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class CondicionLaboralTipoGarantia extends TumiDomain {
	private CondicionLaboralTipoGarantiaId id;
	private Integer intValor;
	
	public CondicionLaboralTipoGarantiaId getId() {
		return id;
	}
	public void setId(CondicionLaboralTipoGarantiaId id) {
		this.id = id;
	}
	public Integer getIntValor() {
		return intValor;
	}
	public void setIntValor(Integer intValor) {
		this.intValor = intValor;
	}
}

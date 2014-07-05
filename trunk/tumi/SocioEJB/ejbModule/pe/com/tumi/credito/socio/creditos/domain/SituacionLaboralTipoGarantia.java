package pe.com.tumi.credito.socio.creditos.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class SituacionLaboralTipoGarantia extends TumiDomain {
	private SituacionLaboralTipoGarantiaId id;
	private Integer intValor;
	
	public SituacionLaboralTipoGarantiaId getId() {
		return id;
	}
	public void setId(SituacionLaboralTipoGarantiaId id) {
		this.id = id;
	}
	public Integer getIntValor() {
		return intValor;
	}
	public void setIntValor(Integer intValor) {
		this.intValor = intValor;
	}
}

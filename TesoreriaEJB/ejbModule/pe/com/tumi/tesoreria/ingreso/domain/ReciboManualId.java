package pe.com.tumi.tesoreria.ingreso.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class ReciboManualId extends TumiDomain{

	private Integer	intPersEmpresa;
	private Integer	intItemReciboManual;
	
	public Integer getIntPersEmpresa() {
		return intPersEmpresa;
	}
	public void setIntPersEmpresa(Integer intPersEmpresa) {
		this.intPersEmpresa = intPersEmpresa;
	}
	public Integer getIntItemReciboManual() {
		return intItemReciboManual;
	}
	public void setIntItemReciboManual(Integer intItemReciboManual) {
		this.intItemReciboManual = intItemReciboManual;
	}
	
	@Override
	public String toString() {
		return "ReciboManualId [intPersEmpresa=" + intPersEmpresa
				+ ", intItemReciboManual=" + intItemReciboManual + "]";
	}
}
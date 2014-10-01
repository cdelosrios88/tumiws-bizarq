package pe.com.tumi.contabilidad.cierreContabilidad.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class CierreContabilidadId extends TumiDomain{
	private Integer intPersEmpresaCieCob;
	private Integer intCcobPeriodoCierre;
	private Integer intEstadoCierreCod;
	
	public Integer getIntPersEmpresaCieCob() {
		return intPersEmpresaCieCob;
	}
	public void setIntPersEmpresaCieCob(Integer intPersEmpresaCieCob) {
		this.intPersEmpresaCieCob = intPersEmpresaCieCob;
	}
	public Integer getIntCcobPeriodoCierre() {
		return intCcobPeriodoCierre;
	}
	public void setIntCcobPeriodoCierre(Integer intCcobPeriodoCierre) {
		this.intCcobPeriodoCierre = intCcobPeriodoCierre;
	}
	public Integer getIntEstadoCierreCod() {
		return intEstadoCierreCod;
	}
	public void setIntEstadoCierreCod(Integer intEstadoCierreCod) {
		this.intEstadoCierreCod = intEstadoCierreCod;
	}

}

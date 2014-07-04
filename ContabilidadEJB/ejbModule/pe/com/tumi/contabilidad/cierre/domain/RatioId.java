package pe.com.tumi.contabilidad.cierre.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;


public class RatioId extends TumiDomain{

	private	Integer	intPersEmpresaRatio;
	private Integer	intContPeriodoRatio;
	private	Integer	intCodigoRatio;
	
	public Integer getIntPersEmpresaRatio() {
		return intPersEmpresaRatio;
	}
	public void setIntPersEmpresaRatio(Integer intPersEmpresaRatio) {
		this.intPersEmpresaRatio = intPersEmpresaRatio;
	}
	public Integer getIntContPeriodoRatio() {
		return intContPeriodoRatio;
	}
	public void setIntContPeriodoRatio(Integer intContPeriodoRatio) {
		this.intContPeriodoRatio = intContPeriodoRatio;
	}
	public Integer getIntCodigoRatio() {
		return intCodigoRatio;
	}
	public void setIntCodigoRatio(Integer intCodigoRatio) {
		this.intCodigoRatio = intCodigoRatio;
	}
	@Override
	public String toString() {
		return "RatioId [intPersEmpresaRatio=" + intPersEmpresaRatio
				+ ", intContPeriodoRatio=" + intContPeriodoRatio
				+ ", intCodigoRatio=" + intCodigoRatio + "]";
	}
	
}

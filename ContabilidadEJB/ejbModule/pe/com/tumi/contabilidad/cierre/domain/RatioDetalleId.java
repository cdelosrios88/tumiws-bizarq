package pe.com.tumi.contabilidad.cierre.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class RatioDetalleId extends TumiDomain{

	private	Integer	intPersEmpresaRatio;
	private Integer	intContPeriodoRatio;
	private	Integer	intCodigoRatio;
	private Integer	intItemRatio;
	
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
	public Integer getIntItemRatio() {
		return intItemRatio;
	}
	public void setIntItemRatio(Integer intItemRatio) {
		this.intItemRatio = intItemRatio;
	}
	@Override
	public String toString() {
		return "RatioDetalleId [intPersEmpresaRatio=" + intPersEmpresaRatio
				+ ", intContPeriodoRatio=" + intContPeriodoRatio
				+ ", intCodigoRatio=" + intCodigoRatio + ", intItemRatio="
				+ intItemRatio + "]";
	}	
}
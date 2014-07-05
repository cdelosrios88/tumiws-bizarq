package pe.com.tumi.contabilidad.cierre.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class CuentaCierreId extends TumiDomain{

	private Integer intPersEmpresaCierre;
	private Integer intContPeriodoCierre;
	private Integer intParaTipoCierre;
	private Integer intContCodigoCierre;
	public Integer getIntPersEmpresaCierre() {
		return intPersEmpresaCierre;
	}
	public void setIntPersEmpresaCierre(Integer intPersEmpresaCierre) {
		this.intPersEmpresaCierre = intPersEmpresaCierre;
	}
	public Integer getIntContPeriodoCierre() {
		return intContPeriodoCierre;
	}
	public void setIntContPeriodoCierre(Integer intContPeriodoCierre) {
		this.intContPeriodoCierre = intContPeriodoCierre;
	}
	public Integer getIntParaTipoCierre() {
		return intParaTipoCierre;
	}
	public void setIntParaTipoCierre(Integer intParaTipoCierre) {
		this.intParaTipoCierre = intParaTipoCierre;
	}
	public Integer getIntContCodigoCierre() {
		return intContCodigoCierre;
	}
	public void setIntContCodigoCierre(Integer intContCodigoCierre) {
		this.intContCodigoCierre = intContCodigoCierre;
	}
	@Override
	public String toString() {
		return "CuentaCierreId [intPersEmpresaCierre=" + intPersEmpresaCierre
				+ ", intContPeriodoCierre=" + intContPeriodoCierre
				+ ", intParaTipoCierre=" + intParaTipoCierre
				+ ", intContCodigoCierre=" + intContCodigoCierre + "]";
	}
	
}

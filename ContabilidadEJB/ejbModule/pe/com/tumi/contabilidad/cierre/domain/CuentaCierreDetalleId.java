package pe.com.tumi.contabilidad.cierre.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class CuentaCierreDetalleId extends TumiDomain{

	private Integer intPersEmpresaCierre;
	private Integer intContPeriodoCierre;
	private Integer intParaTipoCierre;
	private Integer intContCodigoCierre;
	private Integer intItemCierre;
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
	public Integer getIntItemCierre() {
		return intItemCierre;
	}
	public void setIntItemCierre(Integer intItemCierre) {
		this.intItemCierre = intItemCierre;
	}
	@Override
	public String toString() {
		return "CuentaCierreDetalleId [intPersEmpresaCierre="
				+ intPersEmpresaCierre + ", intContPeriodoCierre="
				+ intContPeriodoCierre + ", intParaTipoCierre="
				+ intParaTipoCierre + ", intContCodigoCierre="
				+ intContCodigoCierre + ", intItemCierre=" + intItemCierre
				+ "]";
	}
	
}

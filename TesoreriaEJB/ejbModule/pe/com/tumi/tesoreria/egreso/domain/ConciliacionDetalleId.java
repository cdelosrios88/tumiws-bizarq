package pe.com.tumi.tesoreria.egreso.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class ConciliacionDetalleId extends TumiDomain{

	private Integer intPersEmpresa;
	private Integer intItemConciliacion;
	private Integer intItemConciliacionDetalle;
	
	public Integer getIntPersEmpresa() {
		return intPersEmpresa;
	}
	public void setIntPersEmpresa(Integer intPersEmpresa) {
		this.intPersEmpresa = intPersEmpresa;
	}
	public Integer getIntItemConciliacion() {
		return intItemConciliacion;
	}
	public void setIntItemConciliacion(Integer intItemConciliacion) {
		this.intItemConciliacion = intItemConciliacion;
	}
	public Integer getIntItemConciliacionDetalle() {
		return intItemConciliacionDetalle;
	}
	public void setIntItemConciliacionDetalle(Integer intItemConciliacionDetalle) {
		this.intItemConciliacionDetalle = intItemConciliacionDetalle;
	}
	
	@Override
	public String toString() {
		return "ConciliacionDetalleId [intPersEmpresa=" + intPersEmpresa
				+ ", intItemConciliacion=" + intItemConciliacion
				+ ", intItemConciliacionDetalle=" + intItemConciliacionDetalle
				+ "]";
	}	
}
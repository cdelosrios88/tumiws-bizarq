package pe.com.tumi.tesoreria.logistica.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class RequisicionDetalleId extends TumiDomain{

	private Integer	intPersEmpresa;
	private Integer	intItemRequisicion;
	private Integer	intItemRequisicionDetalle;
	
	public Integer getIntPersEmpresa() {
		return intPersEmpresa;
	}
	public void setIntPersEmpresa(Integer intPersEmpresa) {
		this.intPersEmpresa = intPersEmpresa;
	}
	public Integer getIntItemRequisicion() {
		return intItemRequisicion;
	}
	public void setIntItemRequisicion(Integer intItemRequisicion) {
		this.intItemRequisicion = intItemRequisicion;
	}
	public Integer getIntItemRequisicionDetalle() {
		return intItemRequisicionDetalle;
	}
	public void setIntItemRequisicionDetalle(Integer intItemRequisicionDetalle) {
		this.intItemRequisicionDetalle = intItemRequisicionDetalle;
	}
	
	@Override
	public String toString() {
		return "RequisicionDetalleId [intPersEmpresa=" + intPersEmpresa
				+ ", intItemRequisicion=" + intItemRequisicion
				+ ", intItemRequisicionDetalle=" + intItemRequisicionDetalle
				+ "]";
	}
}
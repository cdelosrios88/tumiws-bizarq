package pe.com.tumi.tesoreria.ingreso.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class IngresoDetalleId extends TumiDomain{

	private Integer intPersEmpresa;
	private Integer intIdIngresoGeneral;
	private Integer intIdIngresoDetalle;
	
	
	public Integer getIntPersEmpresa() {
		return intPersEmpresa;
	}
	public void setIntPersEmpresa(Integer intPersEmpresa) {
		this.intPersEmpresa = intPersEmpresa;
	}
	public Integer getIntIdIngresoGeneral() {
		return intIdIngresoGeneral;
	}
	public void setIntIdIngresoGeneral(Integer intIdIngresoGeneral) {
		this.intIdIngresoGeneral = intIdIngresoGeneral;
	}
	public Integer getIntIdIngresoDetalle() {
		return intIdIngresoDetalle;
	}
	public void setIntIdIngresoDetalle(Integer intIdIngresoDetalle) {
		this.intIdIngresoDetalle = intIdIngresoDetalle;
	}
	@Override
	public String toString() {
		return "IngresoDetalleId [intPersEmpresa=" + intPersEmpresa
				+ ", intIdIngresoGeneral=" + intIdIngresoGeneral
				+ ", intIdIngresoDetalle=" + intIdIngresoDetalle + "]";
	}
	
}
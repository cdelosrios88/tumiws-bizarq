package pe.com.tumi.tesoreria.ingreso.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class ReciboManualDetalleId extends TumiDomain{

	private Integer	intPersEmpresa;
	private Integer	intItemReciboManual;
	private Integer	intItemReciboManualDetalle;
	
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
	public Integer getIntItemReciboManualDetalle() {
		return intItemReciboManualDetalle;
	}
	public void setIntItemReciboManualDetalle(Integer intItemReciboManualDetalle) {
		this.intItemReciboManualDetalle = intItemReciboManualDetalle;
	}
	
	@Override
	public String toString() {
		return "ManualDetalleId [intPersEmpresa=" + intPersEmpresa
				+ ", intItemReciboManual=" + intItemReciboManual
				+ ", intItemReciboManualDetalle=" + intItemReciboManualDetalle
				+ "]";
	}	
}
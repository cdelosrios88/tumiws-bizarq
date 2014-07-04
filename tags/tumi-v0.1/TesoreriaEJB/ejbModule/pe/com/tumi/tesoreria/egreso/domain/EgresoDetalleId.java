package pe.com.tumi.tesoreria.egreso.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class EgresoDetalleId extends TumiDomain{

	private	Integer	intPersEmpresaEgreso;
	private	Integer	intItemEgresoGeneral;
	private	Integer	intItemEgresoDetalle;
	
	
	public Integer getIntPersEmpresaEgreso() {
		return intPersEmpresaEgreso;
	}
	public void setIntPersEmpresaEgreso(Integer intPersEmpresaEgreso) {
		this.intPersEmpresaEgreso = intPersEmpresaEgreso;
	}
	public Integer getIntItemEgresoGeneral() {
		return intItemEgresoGeneral;
	}
	public void setIntItemEgresoGeneral(Integer intItemEgresoGeneral) {
		this.intItemEgresoGeneral = intItemEgresoGeneral;
	}
	public Integer getIntItemEgresoDetalle() {
		return intItemEgresoDetalle;
	}
	public void setIntItemEgresoDetalle(Integer intItemEgresoDetalle) {
		this.intItemEgresoDetalle = intItemEgresoDetalle;
	}
	@Override
	public String toString() {
		return "EgresoDetalleId [intPersEmpresaEgreso=" + intPersEmpresaEgreso
				+ ", intItemEgresoGeneral=" + intItemEgresoGeneral
				+ ", intItemEgresoDetalle=" + intItemEgresoDetalle + "]";
	}
	
	
}

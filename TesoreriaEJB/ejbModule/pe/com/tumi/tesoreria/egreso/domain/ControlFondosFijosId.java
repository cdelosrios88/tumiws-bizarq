package pe.com.tumi.tesoreria.egreso.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class ControlFondosFijosId extends TumiDomain{

	private Integer	intPersEmpresa;
	private	Integer	intParaTipoFondoFijo;
	private	Integer	intItemPeriodoFondo;
	private	Integer	intSucuIdSucursal;
	private	Integer	intItemFondoFijo;
	
	
	public Integer getIntPersEmpresa() {
		return intPersEmpresa;
	}
	public void setIntPersEmpresa(Integer intPersEmpresa) {
		this.intPersEmpresa = intPersEmpresa;
	}
	public Integer getIntParaTipoFondoFijo() {
		return intParaTipoFondoFijo;
	}
	public void setIntParaTipoFondoFijo(Integer intParaTipoFondoFijo) {
		this.intParaTipoFondoFijo = intParaTipoFondoFijo;
	}
	public Integer getIntItemPeriodoFondo() {
		return intItemPeriodoFondo;
	}
	public void setIntItemPeriodoFondo(Integer intItemPeriodoFondo) {
		this.intItemPeriodoFondo = intItemPeriodoFondo;
	}
	public Integer getIntSucuIdSucursal() {
		return intSucuIdSucursal;
	}
	public void setIntSucuIdSucursal(Integer intSucuIdSucursal) {
		this.intSucuIdSucursal = intSucuIdSucursal;
	}
	public Integer getIntItemFondoFijo() {
		return intItemFondoFijo;
	}
	public void setIntItemFondoFijo(Integer intItemFondoFijo) {
		this.intItemFondoFijo = intItemFondoFijo;
	}
	@Override
	public String toString() {
		return "ControlFondosFijosId [intPersEmpresa=" + intPersEmpresa
				+ ", intParaTipoFondoFijo=" + intParaTipoFondoFijo
				+ ", intItemPeriodoFondo=" + intItemPeriodoFondo
				+ ", intSucuIdSucursal=" + intSucuIdSucursal
				+ ", intItemFondoFijo=" + intItemFondoFijo + "]";
	}
	
}
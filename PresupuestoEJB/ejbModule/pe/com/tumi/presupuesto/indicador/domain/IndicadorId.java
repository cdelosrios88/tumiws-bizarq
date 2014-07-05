package pe.com.tumi.presupuesto.indicador.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class IndicadorId extends TumiDomain{

	private Integer intEmpresaIndicadorPk;
	private Integer intPeriodoIndicador;
	private Integer intMesIndicador;
	private Integer intParaTipoIndicador;
	private Integer intEmpresaSucursalPk;
	private Integer intIdSucursal;
	private Integer intIdSubSucursal;
	
	public Integer getIntEmpresaIndicadorPk() {
		return intEmpresaIndicadorPk;
	}
	public void setIntEmpresaIndicadorPk(Integer intEmpresaIndicadorPk) {
		this.intEmpresaIndicadorPk = intEmpresaIndicadorPk;
	}
	public Integer getIntPeriodoIndicador() {
		return intPeriodoIndicador;
	}
	public void setIntPeriodoIndicador(Integer intPeriodoIndicador) {
		this.intPeriodoIndicador = intPeriodoIndicador;
	}
	public Integer getIntMesIndicador() {
		return intMesIndicador;
	}
	public void setIntMesIndicador(Integer intMesIndicador) {
		this.intMesIndicador = intMesIndicador;
	}
	public Integer getIntParaTipoIndicador() {
		return intParaTipoIndicador;
	}
	public void setIntParaTipoIndicador(Integer intParaTipoIndicador) {
		this.intParaTipoIndicador = intParaTipoIndicador;
	}
	public Integer getIntEmpresaSucursalPk() {
		return intEmpresaSucursalPk;
	}
	public void setIntEmpresaSucursalPk(Integer intEmpresaSucursalPk) {
		this.intEmpresaSucursalPk = intEmpresaSucursalPk;
	}
	public Integer getIntIdSucursal() {
		return intIdSucursal;
	}
	public void setIntIdSucursal(Integer intIdSucursal) {
		this.intIdSucursal = intIdSucursal;
	}
	public Integer getIntIdSubSucursal() {
		return intIdSubSucursal;
	}
	public void setIntIdSubSucursal(Integer intIdSubSucursal) {
		this.intIdSubSucursal = intIdSubSucursal;
	}	
}

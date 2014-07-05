package pe.com.tumi.contabilidad.cierre.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class AnexoDetalleCuentaId extends TumiDomain{

	private Integer	intPersEmpresaCuenta;
	private Integer	intContPeriodoCuenta;
	private	String	strContNumeroCuenta;
	private Integer	intPersEmpresaAnexo;
	private Integer intContPeriodoAnexo;
	private Integer intParaTipoAnexo;
	private	Integer	intItemAnexoDetalle;
	//Agregado por cdelosrios, 16/09/2013
	private	Integer	intItemAnexoDetalleCuenta;
	//Fin agregado por cdelosrios, 16/09/2013
	
	public Integer getIntPersEmpresaCuenta() {
		return intPersEmpresaCuenta;
	}
	public void setIntPersEmpresaCuenta(Integer intPersEmpresaCuenta) {
		this.intPersEmpresaCuenta = intPersEmpresaCuenta;
	}
	public Integer getIntContPeriodoCuenta() {
		return intContPeriodoCuenta;
	}
	public void setIntContPeriodoCuenta(Integer intContPeriodoCuenta) {
		this.intContPeriodoCuenta = intContPeriodoCuenta;
	}
	public String getStrContNumeroCuenta() {
		return strContNumeroCuenta;
	}
	public void setStrContNumeroCuenta(String strContNumeroCuenta) {
		this.strContNumeroCuenta = strContNumeroCuenta;
	}
	public Integer getIntPersEmpresaAnexo() {
		return intPersEmpresaAnexo;
	}
	public void setIntPersEmpresaAnexo(Integer intPersEmpresaAnexo) {
		this.intPersEmpresaAnexo = intPersEmpresaAnexo;
	}
	public Integer getIntContPeriodoAnexo() {
		return intContPeriodoAnexo;
	}
	public void setIntContPeriodoAnexo(Integer intContPeriodoAnexo) {
		this.intContPeriodoAnexo = intContPeriodoAnexo;
	}
	public Integer getIntParaTipoAnexo() {
		return intParaTipoAnexo;
	}
	public void setIntParaTipoAnexo(Integer intParaTipoAnexo) {
		this.intParaTipoAnexo = intParaTipoAnexo;
	}
	public Integer getIntItemAnexoDetalle() {
		return intItemAnexoDetalle;
	}
	public void setIntItemAnexoDetalle(Integer intItemAnexoDetalle) {
		this.intItemAnexoDetalle = intItemAnexoDetalle;
	}
	//Agregado por cdelosrios, 16/09/2013
	public Integer getIntItemAnexoDetalleCuenta() {
		return intItemAnexoDetalleCuenta;
	}
	public void setIntItemAnexoDetalleCuenta(Integer intItemAnexoDetalleCuenta) {
		this.intItemAnexoDetalleCuenta = intItemAnexoDetalleCuenta;
	}	
	//Fin agregado por cdelosrios, 16/09/2013
}
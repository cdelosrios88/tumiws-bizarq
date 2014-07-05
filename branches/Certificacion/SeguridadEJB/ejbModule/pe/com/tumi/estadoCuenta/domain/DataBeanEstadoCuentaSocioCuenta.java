package pe.com.tumi.estadoCuenta.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class DataBeanEstadoCuentaSocioCuenta extends TumiDomain {
	private String strNombreCompletoSocio;
	private Integer intCuenta;
	private String strNumeroCuenta;
	private Integer intCondicionCuenta;
	private Integer intSubCondicionCuenta;
	private Integer intSituacionCuenta;
	private String strDescSituacionCuenta;
	private String strFechaApertura;
	private String strFechaLiquidacion;
	private String strExisteExpedienteLiquidacion;
	
	//Para la búsqueda de socio por nombre y apellidos
	private Integer intIdPersona;
	private String strNombreSocio;
	private String strNumeroIdentidad;
	private String strTipoSocio;
	private String strCondicionLaboral;
	private String strSucursal;
	private String strModalidad;
	private String strTipoCuentaSocio;
	private String strFechaRegistro;
	
	
	public String getStrNombreCompletoSocio() {
		return strNombreCompletoSocio;
	}
	public void setStrNombreCompletoSocio(String strNombreCompletoSocio) {
		this.strNombreCompletoSocio = strNombreCompletoSocio;
	}
	public Integer getIntCuenta() {
		return intCuenta;
	}
	public void setIntCuenta(Integer intCuenta) {
		this.intCuenta = intCuenta;
	}
	public String getStrNumeroCuenta() {
		return strNumeroCuenta;
	}
	public void setStrNumeroCuenta(String strNumeroCuenta) {
		this.strNumeroCuenta = strNumeroCuenta;
	}
	public Integer getIntCondicionCuenta() {
		return intCondicionCuenta;
	}
	public void setIntCondicionCuenta(Integer intCondicionCuenta) {
		this.intCondicionCuenta = intCondicionCuenta;
	}
	public Integer getIntSubCondicionCuenta() {
		return intSubCondicionCuenta;
	}
	public void setIntSubCondicionCuenta(Integer intSubCondicionCuenta) {
		this.intSubCondicionCuenta = intSubCondicionCuenta;
	}
	public Integer getIntSituacionCuenta() {
		return intSituacionCuenta;
	}
	public void setIntSituacionCuenta(Integer intSituacionCuenta) {
		this.intSituacionCuenta = intSituacionCuenta;
	}	
	public String getStrDescSituacionCuenta() {
		return strDescSituacionCuenta;
	}
	public void setStrDescSituacionCuenta(String strDescSituacionCuenta) {
		this.strDescSituacionCuenta = strDescSituacionCuenta;
	}
	public String getStrFechaApertura() {
		return strFechaApertura;
	}
	public void setStrFechaApertura(String strFechaApertura) {
		this.strFechaApertura = strFechaApertura;
	}
	public String getStrFechaLiquidacion() {
		return strFechaLiquidacion;
	}
	public void setStrFechaLiquidacion(String strFechaLiquidacion) {
		this.strFechaLiquidacion = strFechaLiquidacion;
	}
	public String getStrExisteExpedienteLiquidacion() {
		return strExisteExpedienteLiquidacion;
	}
	public void setStrExisteExpedienteLiquidacion(
			String strExisteExpedienteLiquidacion) {
		this.strExisteExpedienteLiquidacion = strExisteExpedienteLiquidacion;
	}
	public Integer getIntIdPersona() {
		return intIdPersona;
	}
	public void setIntIdPersona(Integer intIdPersona) {
		this.intIdPersona = intIdPersona;
	}
	public String getStrNombreSocio() {
		return strNombreSocio;
	}
	public void setStrNombreSocio(String strNombreSocio) {
		this.strNombreSocio = strNombreSocio;
	}
	public String getStrNumeroIdentidad() {
		return strNumeroIdentidad;
	}
	public void setStrNumeroIdentidad(String strNumeroIdentidad) {
		this.strNumeroIdentidad = strNumeroIdentidad;
	}
	public String getStrTipoSocio() {
		return strTipoSocio;
	}
	public void setStrTipoSocio(String strTipoSocio) {
		this.strTipoSocio = strTipoSocio;
	}
	public String getStrCondicionLaboral() {
		return strCondicionLaboral;
	}
	public void setStrCondicionLaboral(String strCondicionLaboral) {
		this.strCondicionLaboral = strCondicionLaboral;
	}
	public String getStrSucursal() {
		return strSucursal;
	}
	public void setStrSucursal(String strSucursal) {
		this.strSucursal = strSucursal;
	}
	public String getStrModalidad() {
		return strModalidad;
	}
	public void setStrModalidad(String strModalidad) {
		this.strModalidad = strModalidad;
	}
	public String getStrTipoCuentaSocio() {
		return strTipoCuentaSocio;
	}
	public void setStrTipoCuentaSocio(String strTipoCuentaSocio) {
		this.strTipoCuentaSocio = strTipoCuentaSocio;
	}
	public String getStrFechaRegistro() {
		return strFechaRegistro;
	}
	public void setStrFechaRegistro(String strFechaRegistro) {
		this.strFechaRegistro = strFechaRegistro;
	}
}

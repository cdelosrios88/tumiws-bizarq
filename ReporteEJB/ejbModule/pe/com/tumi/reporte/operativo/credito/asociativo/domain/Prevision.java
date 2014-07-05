package pe.com.tumi.reporte.operativo.credito.asociativo.domain;

import java.math.BigDecimal;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class Prevision extends TumiDomain{
	//Fondo Retiro
	private Integer intCuentaPk;
	private String strNroSolicitud;
	private String strCuenta;
	private String strTipo;
	private BigDecimal bdMontoBruto;
	private BigDecimal bdMontoGastos;
	private BigDecimal bdMontoNeto;
	private String strFechaFallecimiento;
	private String strFechaSolicitud;
	private String strEvaluacionEstado;
	private String strEvaluacionFecha;
	private String strEgresoFecha;
	
	public Integer getIntCuentaPk() {
		return intCuentaPk;
	}
	public void setIntCuentaPk(Integer intCuentaPk) {
		this.intCuentaPk = intCuentaPk;
	}
	public String getStrNroSolicitud() {
		return strNroSolicitud;
	}
	public void setStrNroSolicitud(String strNroSolicitud) {
		this.strNroSolicitud = strNroSolicitud;
	}
	public String getStrCuenta() {
		return strCuenta;
	}
	public void setStrCuenta(String strCuenta) {
		this.strCuenta = strCuenta;
	}
	public String getStrTipo() {
		return strTipo;
	}
	public void setStrTipo(String strTipo) {
		this.strTipo = strTipo;
	}
	public BigDecimal getBdMontoBruto() {
		return bdMontoBruto;
	}
	public void setBdMontoBruto(BigDecimal bdMontoBruto) {
		this.bdMontoBruto = bdMontoBruto;
	}
	public BigDecimal getBdMontoGastos() {
		return bdMontoGastos;
	}
	public void setBdMontoGastos(BigDecimal bdMontoGastos) {
		this.bdMontoGastos = bdMontoGastos;
	}
	public BigDecimal getBdMontoNeto() {
		return bdMontoNeto;
	}
	public void setBdMontoNeto(BigDecimal bdMontoNeto) {
		this.bdMontoNeto = bdMontoNeto;
	}
	public String getStrFechaFallecimiento() {
		return strFechaFallecimiento;
	}
	public void setStrFechaFallecimiento(String strFechaFallecimiento) {
		this.strFechaFallecimiento = strFechaFallecimiento;
	}
	public String getStrFechaSolicitud() {
		return strFechaSolicitud;
	}
	public void setStrFechaSolicitud(String strFechaSolicitud) {
		this.strFechaSolicitud = strFechaSolicitud;
	}
	public String getStrEvaluacionEstado() {
		return strEvaluacionEstado;
	}
	public void setStrEvaluacionEstado(String strEvaluacionEstado) {
		this.strEvaluacionEstado = strEvaluacionEstado;
	}
	public String getStrEvaluacionFecha() {
		return strEvaluacionFecha;
	}
	public void setStrEvaluacionFecha(String strEvaluacionFecha) {
		this.strEvaluacionFecha = strEvaluacionFecha;
	}
	public String getStrEgresoFecha() {
		return strEgresoFecha;
	}
	public void setStrEgresoFecha(String strEgresoFecha) {
		this.strEgresoFecha = strEgresoFecha;
	}
}
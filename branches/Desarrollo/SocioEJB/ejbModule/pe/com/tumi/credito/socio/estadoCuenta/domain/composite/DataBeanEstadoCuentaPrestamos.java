package pe.com.tumi.credito.socio.estadoCuenta.domain.composite;

import java.math.BigDecimal;

public class DataBeanEstadoCuentaPrestamos {
	private String strFecha;
	private String strDescripcion;	
	private BigDecimal bdTasaInteres;
	private BigDecimal bdMontoTotal;	
	private String strCuotas;
	private BigDecimal bdSaldoCredito;
	private BigDecimal bdDiferencia;
	private BigDecimal bdUltimoEnvio;
	
	//Para reportes...
	private String strTasaInteres;
	private String strMontoTotal;	
	private String strSaldoCredito;
	private String strDiferencia;
	private String strUltimoEnvio;
	
	public String getStrFecha() {
		return strFecha;
	}
	public void setStrFecha(String strFecha) {
		this.strFecha = strFecha;
	}
	public String getStrDescripcion() {
		return strDescripcion;
	}
	public void setStrDescripcion(String strDescripcion) {
		this.strDescripcion = strDescripcion;
	}
	public BigDecimal getBdTasaInteres() {
		return bdTasaInteres;
	}
	public void setBdTasaInteres(BigDecimal bdTasaInteres) {
		this.bdTasaInteres = bdTasaInteres;
	}
	public BigDecimal getBdMontoTotal() {
		return bdMontoTotal;
	}
	public void setBdMontoTotal(BigDecimal bdMontoTotal) {
		this.bdMontoTotal = bdMontoTotal;
	}
	public String getStrCuotas() {
		return strCuotas;
	}
	public void setStrCuotas(String strCuotas) {
		this.strCuotas = strCuotas;
	}
	public BigDecimal getBdSaldoCredito() {
		return bdSaldoCredito;
	}
	public void setBdSaldoCredito(BigDecimal bdSaldoCredito) {
		this.bdSaldoCredito = bdSaldoCredito;
	}
	public BigDecimal getBdDiferencia() {
		return bdDiferencia;
	}
	public void setBdDiferencia(BigDecimal bdDiferencia) {
		this.bdDiferencia = bdDiferencia;
	}
	public BigDecimal getBdUltimoEnvio() {
		return bdUltimoEnvio;
	}
	public void setBdUltimoEnvio(BigDecimal bdUltimoEnvio) {
		this.bdUltimoEnvio = bdUltimoEnvio;
	}
	public String getStrTasaInteres() {
		return strTasaInteres;
	}
	public void setStrTasaInteres(String strTasaInteres) {
		this.strTasaInteres = strTasaInteres;
	}
	public String getStrMontoTotal() {
		return strMontoTotal;
	}
	public void setStrMontoTotal(String strMontoTotal) {
		this.strMontoTotal = strMontoTotal;
	}
	public String getStrSaldoCredito() {
		return strSaldoCredito;
	}
	public void setStrSaldoCredito(String strSaldoCredito) {
		this.strSaldoCredito = strSaldoCredito;
	}
	public String getStrDiferencia() {
		return strDiferencia;
	}
	public void setStrDiferencia(String strDiferencia) {
		this.strDiferencia = strDiferencia;
	}
	public String getStrUltimoEnvio() {
		return strUltimoEnvio;
	}
	public void setStrUltimoEnvio(String strUltimoEnvio) {
		this.strUltimoEnvio = strUltimoEnvio;
	}
}

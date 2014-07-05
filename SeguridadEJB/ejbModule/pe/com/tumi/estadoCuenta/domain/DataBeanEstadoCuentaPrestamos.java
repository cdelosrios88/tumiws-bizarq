package pe.com.tumi.estadoCuenta.domain;

import java.math.BigDecimal;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class DataBeanEstadoCuentaPrestamos extends TumiDomain {
	//Prestamos Aprobados Y Rechazados
	private Integer intTipoCredito;
	private String strFechaSolicitud;
	private String strDescPrestamo;
	private String strSolicitud;
	private BigDecimal bdPorcetajeInteres;
	private String strCuotas;
	private BigDecimal bdMontoTotal;
	private String strEstadoCredito;
	private BigDecimal bdSaldoCredito;
	private Integer intCategoriaCartera;
	private String strFechaGiro;
	//JCHAVEZ 07.01.2014
//	private Integer intNumeroDocumento;
	private String strNumeroDocumento;
	private BigDecimal bdCuotaMensual;
	
	//Personas Garantizadas
	private String strPersonaGarantizada;
	private BigDecimal bdSaldoAporte;

	public Integer getIntTipoCredito() {
		return intTipoCredito;
	}

	public void setIntTipoCredito(Integer intTipoCredito) {
		this.intTipoCredito = intTipoCredito;
	}

	public String getStrFechaSolicitud() {
		return strFechaSolicitud;
	}

	public void setStrFechaSolicitud(String strFechaSolicitud) {
		this.strFechaSolicitud = strFechaSolicitud;
	}

	public String getStrDescPrestamo() {
		return strDescPrestamo;
	}

	public void setStrDescPrestamo(String strDescPrestamo) {
		this.strDescPrestamo = strDescPrestamo;
	}

	public String getStrSolicitud() {
		return strSolicitud;
	}

	public void setStrSolicitud(String strSolicitud) {
		this.strSolicitud = strSolicitud;
	}

	public BigDecimal getBdPorcetajeInteres() {
		return bdPorcetajeInteres;
	}

	public void setBdPorcetajeInteres(BigDecimal bdPorcetajeInteres) {
		this.bdPorcetajeInteres = bdPorcetajeInteres;
	}

	public String getStrCuotas() {
		return strCuotas;
	}

	public void setStrCuotas(String strCuotas) {
		this.strCuotas = strCuotas;
	}

	public BigDecimal getBdMontoTotal() {
		return bdMontoTotal;
	}

	public void setBdMontoTotal(BigDecimal bdMontoTotal) {
		this.bdMontoTotal = bdMontoTotal;
	}

	public String getStrEstadoCredito() {
		return strEstadoCredito;
	}

	public void setStrEstadoCredito(String strEstadoCredito) {
		this.strEstadoCredito = strEstadoCredito;
	}

	public BigDecimal getBdSaldoCredito() {
		return bdSaldoCredito;
	}

	public void setBdSaldoCredito(BigDecimal bdSaldoCredito) {
		this.bdSaldoCredito = bdSaldoCredito;
	}

	public Integer getIntCategoriaCartera() {
		return intCategoriaCartera;
	}

	public void setIntCategoriaCartera(Integer intCategoriaCartera) {
		this.intCategoriaCartera = intCategoriaCartera;
	}

	public String getStrFechaGiro() {
		return strFechaGiro;
	}

	public void setStrFechaGiro(String strFechaGiro) {
		this.strFechaGiro = strFechaGiro;
	}

//	public Integer getIntNumeroDocumento() {
//		return intNumeroDocumento;
//	}
//
//	public void setIntNumeroDocumento(Integer intNumeroDocumento) {
//		this.intNumeroDocumento = intNumeroDocumento;
//	}

	public BigDecimal getBdCuotaMensual() {
		return bdCuotaMensual;
	}

	public void setBdCuotaMensual(BigDecimal bdCuotaMensual) {
		this.bdCuotaMensual = bdCuotaMensual;
	}

	public String getStrPersonaGarantizada() {
		return strPersonaGarantizada;
	}

	public void setStrPersonaGarantizada(String strPersonaGarantizada) {
		this.strPersonaGarantizada = strPersonaGarantizada;
	}

	public BigDecimal getBdSaldoAporte() {
		return bdSaldoAporte;
	}

	public void setBdSaldoAporte(BigDecimal bdSaldoAporte) {
		this.bdSaldoAporte = bdSaldoAporte;
	}

	public String getStrNumeroDocumento() {
		return strNumeroDocumento;
	}

	public void setStrNumeroDocumento(String strNumeroDocumento) {
		this.strNumeroDocumento = strNumeroDocumento;
	}
}

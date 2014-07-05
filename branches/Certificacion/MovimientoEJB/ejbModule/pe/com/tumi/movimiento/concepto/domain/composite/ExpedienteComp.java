package pe.com.tumi.movimiento.concepto.domain.composite;

import java.math.BigDecimal;
import java.sql.Timestamp;

import pe.com.tumi.credito.socio.creditos.domain.Credito;
import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.movimiento.concepto.domain.EstadoExpediente;
import pe.com.tumi.movimiento.concepto.domain.Expediente;
import pe.com.tumi.servicio.solicitudPrestamo.domain.composite.ExpedienteCreditoComp;

public class ExpedienteComp extends TumiDomain{
	private Expediente expediente;
	private String strDescripcionTipoCredito;
	private String strDescripcionTipoCreditoEmpresa;
	private Integer intNroCuotasPagadas;
	private Integer intNroCuotasDefinidas;
	private Integer intNroCuotasAtrasadas;
	private BigDecimal bdCuotaFija;
	private Integer intNroOperacion;
	private BigDecimal bdSaldo;
	private Credito credito;
	//AUTOR Y FECHA CREACION: JCHAVEZ / 05-09-2013
	private String strCuotas;
	private Timestamp tsFechaSolicitud;
	private String strFechaSolicitud;
	//AUTOR Y FECHA CREACION: JCHAVEZ / 17-09-2013
	private String strDescripcionExpediente;
	private String strNroSolicitud;
	private BigDecimal bdCuotaMensual;
	private String strCuotaMensual;
	private String strCategoriaCartera;
	private String strFechaGiro;
	private String strDocumentoGiro;
	//AUTOR Y FECHA CREACION: JCHAVEZ / 18-09-2013
	private String strEstadoTipoCredito;
	
	// CGD - 03.10.2013
	private ExpedienteCreditoComp expedienteCreditoComp;
	private ExpedienteCreditoComp expedienteCreditoAnteriorComp;
	
	// CGD - 06.11.2013
	private EstadoExpediente ultimoEstadoExpediente;
	private BigDecimal bdMontoReprestamo;
	// cgd - 15.11.2013
	private BigDecimal bdMontoInteresAtrasado;
	//14.04.2014 JCHAVEZ
	private BigDecimal bdMontoSaldoReprestamo;
	
	public Expediente getExpediente() {
		return expediente;
	}
	public void setExpediente(Expediente expediente) {
		this.expediente = expediente;
	}
	public String getStrDescripcionTipoCredito() {
		return strDescripcionTipoCredito;
	}
	public void setStrDescripcionTipoCredito(String strDescripcionTipoCredito) {
		this.strDescripcionTipoCredito = strDescripcionTipoCredito;
	}
	public String getStrDescripcionTipoCreditoEmpresa() {
		return strDescripcionTipoCreditoEmpresa;
	}
	public void setStrDescripcionTipoCreditoEmpresa(
			String strDescripcionTipoCreditoEmpresa) {
		this.strDescripcionTipoCreditoEmpresa = strDescripcionTipoCreditoEmpresa;
	}
	public Integer getIntNroCuotasPagadas() {
		return intNroCuotasPagadas;
	}
	public void setIntNroCuotasPagadas(Integer intNroCuotasPagadas) {
		this.intNroCuotasPagadas = intNroCuotasPagadas;
	}
	public Integer getIntNroCuotasDefinidas() {
		return intNroCuotasDefinidas;
	}
	public void setIntNroCuotasDefinidas(Integer intNroCuotasDefinidas) {
		this.intNroCuotasDefinidas = intNroCuotasDefinidas;
	}
	public Integer getIntNroCuotasAtrasadas() {
		return intNroCuotasAtrasadas;
	}
	public void setIntNroCuotasAtrasadas(Integer intNroCuotasAtrasadas) {
		this.intNroCuotasAtrasadas = intNroCuotasAtrasadas;
	}
	public BigDecimal getBdCuotaFija() {
		return bdCuotaFija;
	}
	public void setBdCuotaFija(BigDecimal bdCuotaFija) {
		this.bdCuotaFija = bdCuotaFija;
	}
	public Integer getIntNroOperacion() {
		return intNroOperacion;
	}
	public void setIntNroOperacion(Integer intNroOperacion) {
		this.intNroOperacion = intNroOperacion;
	}
	public BigDecimal getBdSaldo() {
		return bdSaldo;
	}
	public void setBdSaldo(BigDecimal bdSaldo) {
		this.bdSaldo = bdSaldo;
	}
	public Credito getCredito() {
		return credito;
	}
	public void setCredito(Credito credito) {
		this.credito = credito;
	}
	public String getStrCuotas() {
		return strCuotas;
	}
	public void setStrCuotas(String strCuotas) {
		this.strCuotas = strCuotas;
	}
	public Timestamp getTsFechaSolicitud() {
		return tsFechaSolicitud;
	}
	public void setTsFechaSolicitud(Timestamp tsFechaSolicitud) {
		this.tsFechaSolicitud = tsFechaSolicitud;
	}
	public String getStrFechaSolicitud() {
		return strFechaSolicitud;
	}
	public void setStrFechaSolicitud(String strFechaSolicitud) {
		this.strFechaSolicitud = strFechaSolicitud;
	}
	public String getStrDescripcionExpediente() {
		return strDescripcionExpediente;
	}
	public void setStrDescripcionExpediente(String strDescripcionExpediente) {
		this.strDescripcionExpediente = strDescripcionExpediente;
	}
	public String getStrNroSolicitud() {
		return strNroSolicitud;
	}
	public void setStrNroSolicitud(String strNroSolicitud) {
		this.strNroSolicitud = strNroSolicitud;
	}
	public String getStrCuotaMensual() {
		return strCuotaMensual;
	}
	public void setStrCuotaMensual(String strCuotaMensual) {
		this.strCuotaMensual = strCuotaMensual;
	}
	public String getStrCategoriaCartera() {
		return strCategoriaCartera;
	}
	public void setStrCategoriaCartera(String strCategoriaCartera) {
		this.strCategoriaCartera = strCategoriaCartera;
	}
	public String getStrFechaGiro() {
		return strFechaGiro;
	}
	public void setStrFechaGiro(String strFechaGiro) {
		this.strFechaGiro = strFechaGiro;
	}
	public String getStrDocumentoGiro() {
		return strDocumentoGiro;
	}
	public void setStrDocumentoGiro(String strDocumentoGiro) {
		this.strDocumentoGiro = strDocumentoGiro;
	}
	public String getStrEstadoTipoCredito() {
		return strEstadoTipoCredito;
	}
	public void setStrEstadoTipoCredito(String strEstadoTipoCredito) {
		this.strEstadoTipoCredito = strEstadoTipoCredito;
	}
	public BigDecimal getBdCuotaMensual() {
		return bdCuotaMensual;
	}
	public void setBdCuotaMensual(BigDecimal bdCuotaMensual) {
		this.bdCuotaMensual = bdCuotaMensual;
	}
	public ExpedienteCreditoComp getExpedienteCreditoComp() {
		return expedienteCreditoComp;
	}
	public void setExpedienteCreditoComp(ExpedienteCreditoComp expedienteCreditoComp) {
		this.expedienteCreditoComp = expedienteCreditoComp;
	}
	public ExpedienteCreditoComp getExpedienteCreditoAnteriorComp() {
		return expedienteCreditoAnteriorComp;
	}
	public void setExpedienteCreditoAnteriorComp(
			ExpedienteCreditoComp expedienteCreditoAnteriorComp) {
		this.expedienteCreditoAnteriorComp = expedienteCreditoAnteriorComp;
	}
	public EstadoExpediente getUltimoEstadoExpediente() {
		return ultimoEstadoExpediente;
	}
	public void setUltimoEstadoExpediente(EstadoExpediente ultimoEstadoExpediente) {
		this.ultimoEstadoExpediente = ultimoEstadoExpediente;
	}
	public BigDecimal getBdMontoReprestamo() {
		return bdMontoReprestamo;
	}
	public void setBdMontoReprestamo(BigDecimal bdMontoReprestamo) {
		this.bdMontoReprestamo = bdMontoReprestamo;
	}
	public BigDecimal getBdMontoInteresAtrasado() {
		return bdMontoInteresAtrasado;
	}
	public void setBdMontoInteresAtrasado(BigDecimal bdMontoInteresAtrasado) {
		this.bdMontoInteresAtrasado = bdMontoInteresAtrasado;
	}
	public BigDecimal getBdMontoSaldoReprestamo() {
		return bdMontoSaldoReprestamo;
	}
	public void setBdMontoSaldoReprestamo(BigDecimal bdMontoSaldoReprestamo) {
		this.bdMontoSaldoReprestamo = bdMontoSaldoReprestamo;
	}
	
}

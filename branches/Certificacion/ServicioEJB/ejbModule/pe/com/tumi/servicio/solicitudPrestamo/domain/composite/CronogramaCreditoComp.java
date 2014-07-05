package pe.com.tumi.servicio.solicitudPrestamo.domain.composite;

import java.math.BigDecimal;

import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.servicio.solicitudPrestamo.domain.CronogramaCredito;

public class CronogramaCreditoComp extends TumiDomain {
	private CronogramaCredito cronogramaCredito;
	private String strFechaEnvio;
	private String strFechaVencimiento;
	private Integer intDiasTranscurridos;
	private BigDecimal bdSaldoCapital;
	private BigDecimal bdAmortizacion;
	private BigDecimal bdInteres;
	private BigDecimal bdCuotaMensual;
	private BigDecimal bdAportes;
	private BigDecimal bdTotalCuotaMensual;
	private Integer intParaTipoFormapago;
	private String strEstadoDescripcion;
	private BigDecimal bdSaldoCuota;
	
	public CronogramaCredito getCronogramaCredito() {
		return cronogramaCredito;
	}
	public void setCronogramaCredito(CronogramaCredito cronogramaCredito) {
		this.cronogramaCredito = cronogramaCredito;
	}
	public String getStrFechaEnvio() {
		return strFechaEnvio;
	}
	public void setStrFechaEnvio(String strFechaEnvio) {
		this.strFechaEnvio = strFechaEnvio;
	}
	public String getStrFechaVencimiento() {
		return strFechaVencimiento;
	}
	public void setStrFechaVencimiento(String strFechaVencimiento) {
		this.strFechaVencimiento = strFechaVencimiento;
	}
	public Integer getIntDiasTranscurridos() {
		return intDiasTranscurridos;
	}
	public void setIntDiasTranscurridos(Integer intDiasTranscurridos) {
		this.intDiasTranscurridos = intDiasTranscurridos;
	}
	public BigDecimal getBdSaldoCapital() {
		return bdSaldoCapital;
	}
	public void setBdSaldoCapital(BigDecimal bdSaldoCapital) {
		this.bdSaldoCapital = bdSaldoCapital;
	}
	public BigDecimal getBdAmortizacion() {
		return bdAmortizacion;
	}
	public void setBdAmortizacion(BigDecimal bdAmortizacion) {
		this.bdAmortizacion = bdAmortizacion;
	}
	public BigDecimal getBdInteres() {
		return bdInteres;
	}
	public void setBdInteres(BigDecimal bdInteres) {
		this.bdInteres = bdInteres;
	}
	public BigDecimal getBdAportes() {
		return bdAportes;
	}
	public void setBdAportes(BigDecimal bdAportes) {
		this.bdAportes = bdAportes;
	}
	public BigDecimal getBdCuotaMensual() {
		return bdCuotaMensual;
	}
	public void setBdCuotaMensual(BigDecimal bdCuotaMensual) {
		this.bdCuotaMensual = bdCuotaMensual;
	}
	public BigDecimal getBdTotalCuotaMensual() {
		return bdTotalCuotaMensual;
	}
	public void setBdTotalCuotaMensual(BigDecimal bdTotalCuotaMensual) {
		this.bdTotalCuotaMensual = bdTotalCuotaMensual;
	}
	public Integer getIntParaTipoFormapago() {
		return intParaTipoFormapago;
	}
	public void setIntParaTipoFormapago(Integer intParaTipoFormapago) {
		this.intParaTipoFormapago = intParaTipoFormapago;
	}
	public String getStrEstadoDescripcion() {
		return strEstadoDescripcion;
	}
	public void setStrEstadoDescripcion(String strEstadoDescripcion) {
		this.strEstadoDescripcion = strEstadoDescripcion;
	}
	public BigDecimal getBdSaldoCuota() {
		return bdSaldoCuota;
	}
	public void setBdSaldoCuota(BigDecimal bdSaldoCuota) {
		this.bdSaldoCuota = bdSaldoCuota;
	}
	
}

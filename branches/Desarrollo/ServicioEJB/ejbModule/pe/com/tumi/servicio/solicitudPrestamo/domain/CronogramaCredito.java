package pe.com.tumi.servicio.solicitudPrestamo.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class CronogramaCredito extends TumiDomain {
	private CronogramaCreditoId id;
	private Integer intNroCuota;
	private Integer intParaTipoCuotaCod;
	private Integer intParaFormaPagoCod;
	private Integer intParaTipoConceptoCod;
	private BigDecimal bdMontoConcepto;
	private BigDecimal bdMontoCapital;
	private Timestamp tsFechaVencimiento;
	private Integer intPeriodoPlanilla;
	private Integer intParaEstadoCod;
	// CGD - 12/11/2013
	// Se agrego campos para regenerar el cronograma visual
	private Timestamp    tsFechaEnvioView;
    private BigDecimal   bdSaldoCapitalView;
    private BigDecimal   bdAmortizacionView;
    private BigDecimal   bdInteresView;
    private BigDecimal   bdAportesView;
    
    //Autor: jchavez / Tarea: Creación / Fecha: 02.09.2014 / Se agrega campos para generar el cronograma visual
    private String	strCuotaFijaExpediente1;
    private String	strCuotaFijaExpediente2;
    private String	strCuotaFijaExpediente3;
    private String	strCuotaFijaExpediente4;
	
	public CronogramaCreditoId getId() {
		return id;
	}
	public void setId(CronogramaCreditoId id) {
		this.id = id;
	}
	public Integer getIntNroCuota() {
		return intNroCuota;
	}
	public void setIntNroCuota(Integer intNroCuota) {
		this.intNroCuota = intNroCuota;
	}
	public Integer getIntParaTipoCuotaCod() {
		return intParaTipoCuotaCod;
	}
	public void setIntParaTipoCuotaCod(Integer intParaTipoCuotaCod) {
		this.intParaTipoCuotaCod = intParaTipoCuotaCod;
	}
	public Integer getIntParaFormaPagoCod() {
		return intParaFormaPagoCod;
	}
	public void setIntParaFormaPagoCod(Integer intParaFormaPagoCod) {
		this.intParaFormaPagoCod = intParaFormaPagoCod;
	}
	public Integer getIntParaTipoConceptoCod() {
		return intParaTipoConceptoCod;
	}
	public void setIntParaTipoConceptoCod(Integer intParaTipoConceptoCod) {
		this.intParaTipoConceptoCod = intParaTipoConceptoCod;
	}
	public BigDecimal getBdMontoConcepto() {
		return bdMontoConcepto;
	}
	public void setBdMontoConcepto(BigDecimal bdMontoConcepto) {
		this.bdMontoConcepto = bdMontoConcepto;
	}
	public BigDecimal getBdMontoCapital() {
		return bdMontoCapital;
	}
	public void setBdMontoCapital(BigDecimal bdMontoCapital) {
		this.bdMontoCapital = bdMontoCapital;
	}
	public Timestamp getTsFechaVencimiento() {
		return tsFechaVencimiento;
	}
	public void setTsFechaVencimiento(Timestamp tsFechaVencimiento) {
		this.tsFechaVencimiento = tsFechaVencimiento;
	}
	public Integer getIntPeriodoPlanilla() {
		return intPeriodoPlanilla;
	}
	public void setIntPeriodoPlanilla(Integer intPeriodoPlanilla) {
		this.intPeriodoPlanilla = intPeriodoPlanilla;
	}
	public Integer getIntParaEstadoCod() {
		return intParaEstadoCod;
	}
	public void setIntParaEstadoCod(Integer intParaEstadoCod) {
		this.intParaEstadoCod = intParaEstadoCod;
	}
	public Timestamp getTsFechaEnvioView() {
		return tsFechaEnvioView;
	}
	public void setTsFechaEnvioView(Timestamp tsFechaEnvioView) {
		this.tsFechaEnvioView = tsFechaEnvioView;
	}
	public BigDecimal getBdSaldoCapitalView() {
		return bdSaldoCapitalView;
	}
	public void setBdSaldoCapitalView(BigDecimal bdSaldoCapitalView) {
		this.bdSaldoCapitalView = bdSaldoCapitalView;
	}
	public BigDecimal getBdAmortizacionView() {
		return bdAmortizacionView;
	}
	public void setBdAmortizacionView(BigDecimal bdAmortizacionView) {
		this.bdAmortizacionView = bdAmortizacionView;
	}
	public BigDecimal getBdInteresView() {
		return bdInteresView;
	}
	public void setBdInteresView(BigDecimal bdInteresView) {
		this.bdInteresView = bdInteresView;
	}
	public BigDecimal getBdAportesView() {
		return bdAportesView;
	}
	public void setBdAportesView(BigDecimal bdAportesView) {
		this.bdAportesView = bdAportesView;
	}
	@Override
	public String toString() {
		return "CronogramaCredito [id=" + id + ", intNroCuota=" + intNroCuota
				+ ", intParaTipoCuotaCod=" + intParaTipoCuotaCod
				+ ", intParaFormaPagoCod=" + intParaFormaPagoCod
				+ ", intParaTipoConceptoCod=" + intParaTipoConceptoCod
				+ ", bdMontoConcepto=" + bdMontoConcepto + ", bdMontoCapital="
				+ bdMontoCapital + ", tsFechaVencimiento=" + tsFechaVencimiento
				+ ", intPeriodoPlanilla=" + intPeriodoPlanilla
				+ ", intParaEstadoCod=" + intParaEstadoCod + "]";
	}
	public String getStrCuotaFijaExpediente1() {
		return strCuotaFijaExpediente1;
	}
	public void setStrCuotaFijaExpediente1(String strCuotaFijaExpediente1) {
		this.strCuotaFijaExpediente1 = strCuotaFijaExpediente1;
	}
	public String getStrCuotaFijaExpediente2() {
		return strCuotaFijaExpediente2;
	}
	public void setStrCuotaFijaExpediente2(String strCuotaFijaExpediente2) {
		this.strCuotaFijaExpediente2 = strCuotaFijaExpediente2;
	}
	public String getStrCuotaFijaExpediente3() {
		return strCuotaFijaExpediente3;
	}
	public void setStrCuotaFijaExpediente3(String strCuotaFijaExpediente3) {
		this.strCuotaFijaExpediente3 = strCuotaFijaExpediente3;
	}
	public String getStrCuotaFijaExpediente4() {
		return strCuotaFijaExpediente4;
	}
	public void setStrCuotaFijaExpediente4(String strCuotaFijaExpediente4) {
		this.strCuotaFijaExpediente4 = strCuotaFijaExpediente4;
	}
}
package pe.com.tumi.movimiento.concepto.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class Cronograma extends TumiDomain{

	private CronogramaId id;
	private Integer intNumeroCuota;
	private Integer intParaTipoCuotaCod;
	private Integer intParaFormaPagoCod;
	private Integer intParaTipoConceptoCreditoCod;
	private BigDecimal bdMontoConcepto;
	private BigDecimal bdMontoCapital;
	private Timestamp tsFechaVencimiento;
	private Integer intPeriodoPlanilla;
	private Integer intParaEstadoCod;
	private BigDecimal bdSaldoDetalleCredito;
	
	private Expediente expediente;

	public CronogramaId getId() {
		return id;
	}
	public void setId(CronogramaId id) {
		this.id = id;
	}

	public Integer getIntNumeroCuota() {
		return intNumeroCuota;
	}
	public void setIntNumeroCuota(Integer intNumeroCuota) {
		this.intNumeroCuota = intNumeroCuota;
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

	public Integer getIntParaTipoConceptoCreditoCod() {
		return intParaTipoConceptoCreditoCod;
	}
	public void setIntParaTipoConceptoCreditoCod(
			Integer intParaTipoConceptoCreditoCod) {
		this.intParaTipoConceptoCreditoCod = intParaTipoConceptoCreditoCod;
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

	public BigDecimal getBdSaldoDetalleCredito() {
		return bdSaldoDetalleCredito;
	}
	public void setBdSaldoDetalleCredito(BigDecimal bdSaldoDetalleCredito) {
		this.bdSaldoDetalleCredito = bdSaldoDetalleCredito;
	}

	public Expediente getExpediente() {
		return expediente;
	}
	public void setExpediente(Expediente expediente) {
		this.expediente = expediente;
	}
	
	
}

package pe.com.tumi.movimiento.concepto.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class InteresCancelado extends TumiDomain {
	
	private InteresCanceladoId id;
	private Integer intParaTipoFormaPago;
	private BigDecimal bdSaldoCredito;
	private Integer intParaEstado;
	private Timestamp tsFechaInicio;
	private Timestamp tsFechaMovimiento;
	private Integer intDias;
	private BigDecimal bdTasa;	
	private BigDecimal bdMontoInteres;
	
	

	public InteresCanceladoId getId() {
		return id;
	}

	public void setId(InteresCanceladoId id) {
		this.id = id;
	}

	public Integer getIntParaTipoFormaPago() {
		return intParaTipoFormaPago;
	}

	public void setIntParaTipoFormaPago(Integer intParaTipoFormaPago) {
		this.intParaTipoFormaPago = intParaTipoFormaPago;
	}

	public BigDecimal getBdSaldoCredito() {
		return bdSaldoCredito;
	}

	public void setBdSaldoCredito(BigDecimal bdSaldoCredito) {
		this.bdSaldoCredito = bdSaldoCredito;
	}

	public Integer getIntParaEstado() {
		return intParaEstado;
	}

	public void setIntParaEstado(Integer intParaEstado) {
		this.intParaEstado = intParaEstado;
	}

	public Timestamp getTsFechaInicio() {
		return tsFechaInicio;
	}

	public void setTsFechaInicio(Timestamp tsFechaInicio) {
		this.tsFechaInicio = tsFechaInicio;
	}

	public Timestamp getTsFechaMovimiento() {
		return tsFechaMovimiento;
	}

	public void setTsFechaMovimiento(Timestamp tsFechaMovimiento) {
		this.tsFechaMovimiento = tsFechaMovimiento;
	}

	public Integer getIntDias() {
		return intDias;
	}

	public void setIntDias(Integer intDias) {
		this.intDias = intDias;
	}

	public BigDecimal getBdTasa() {
		return bdTasa;
	}

	public void setBdTasa(BigDecimal bdTasa) {
		this.bdTasa = bdTasa;
	}

	public BigDecimal getBdMontoInteres() {
		return bdMontoInteres;
	}

	public void setBdMontoInteres(BigDecimal bdMontoInteres) {
		this.bdMontoInteres = bdMontoInteres;
	}
	
}

package pe.com.tumi.movimiento.concepto.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class InteresProvisionado extends TumiDomain {
	
	private InteresProvisionadoId id;
	private Integer intParaTipoMovInt;	
	private Timestamp tsFechaInicio;
	private Timestamp tsFechaFin;
	private Integer intNumeroDias;	
	private BigDecimal bdSaldoPrestamo;
	private BigDecimal bdTasaInteres;
	private BigDecimal bdMontoInteres;
	private BigDecimal bdMontoAtrasadoInteres;
	private BigDecimal bdMontoTotalInteres;
	private BigDecimal bdMontoSaldo;
	private Integer intParaEstado;	
	
	public InteresProvisionadoId getId() {
		return id;
	}
	
	public void setId(InteresProvisionadoId id) {
		this.id = id;
	}
	
	public Integer getIntParaTipoMovInt() {
		return intParaTipoMovInt;
	}
	public void setIntParaTipoMovInt(Integer intParaTipoMovInt) {
		this.intParaTipoMovInt = intParaTipoMovInt;
	}

	public Timestamp getTsFechaFin() {
		return tsFechaFin;
	}
	public void setTsFechaFin(Timestamp tsFechaFin) {
		this.tsFechaFin = tsFechaFin;
	}
	public Integer getIntNumeroDias() {
		return intNumeroDias;
	}
	public void setIntNumeroDias(Integer intNumeroDias) {
		this.intNumeroDias = intNumeroDias;
	}
	public BigDecimal getBdSaldoPrestamo() {
		return bdSaldoPrestamo;
	}
	public void setBdSaldoPrestamo(BigDecimal bdSaldoPrestamo) {
		this.bdSaldoPrestamo = bdSaldoPrestamo;
	}
	public BigDecimal getBdTasaInteres() {
		return bdTasaInteres;
	}
	public void setBdTasaInteres(BigDecimal bdTasaInteres) {
		this.bdTasaInteres = bdTasaInteres;
	}
	public BigDecimal getBdMontoInteres() {
		return bdMontoInteres;
	}
	public void setBdMontoInteres(BigDecimal bdMontoInteres) {
		this.bdMontoInteres = bdMontoInteres;
	}
	public BigDecimal getBdMontoAtrasadoInteres() {
		return bdMontoAtrasadoInteres;
	}
	public void setBdMontoAtrasadoInteres(BigDecimal bdMontoAtrasadoInteres) {
		this.bdMontoAtrasadoInteres = bdMontoAtrasadoInteres;
	}
	public BigDecimal getBdMontoTotalInteres() {
		return bdMontoTotalInteres;
	}
	public void setBdMontoTotalInteres(BigDecimal bdMontoTotalInteres) {
		this.bdMontoTotalInteres = bdMontoTotalInteres;
	}
	public BigDecimal getBdMontoSaldo() {
		return bdMontoSaldo;
	}
	public void setBdMontoSaldo(BigDecimal bdMontoSaldo) {
		this.bdMontoSaldo = bdMontoSaldo;
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

}



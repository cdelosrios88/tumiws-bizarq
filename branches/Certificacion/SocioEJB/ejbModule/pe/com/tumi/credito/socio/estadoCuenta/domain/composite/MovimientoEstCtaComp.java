package pe.com.tumi.credito.socio.estadoCuenta.domain.composite;

import java.math.BigDecimal;

import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.movimiento.concepto.domain.Movimiento;

public class MovimientoEstCtaComp extends TumiDomain {
	private Movimiento movimiento;	
	private String strFechaMovimiento;
	private String strDescTipoCargoAbono;
	private String strDescTipoMovimiento;
	private String strDescTipoConceptoGral;
	private Integer intPeriodoFechaMovimiento;
	private Integer intPeriodoFechaCptoPago;
	
	//AUTOR Y FECHA CREACION: JCHAVEZ / 30-09-2013
	private BigDecimal bdMontoMovimientoEne;
	private BigDecimal bdMontoMovimientoFeb;
	private BigDecimal bdMontoMovimientoMar;
	private BigDecimal bdMontoMovimientoAbr;
	private BigDecimal bdMontoMovimientoMay;
	private BigDecimal bdMontoMovimientoJun;
	private BigDecimal bdMontoMovimientoJul;
	private BigDecimal bdMontoMovimientoAgo;
	private BigDecimal bdMontoMovimientoSet;
	private BigDecimal bdMontoMovimientoOct;
	private BigDecimal bdMontoMovimientoNov;
	private BigDecimal bdMontoMovimientoDic;
	private BigDecimal bdMontoAcumulado;
	private BigDecimal bdMontoTotal;
	
	public Movimiento getMovimiento() {
		return movimiento;
	}
	public void setMovimiento(Movimiento movimiento) {
		this.movimiento = movimiento;
	}
	public String getStrFechaMovimiento() {
		return strFechaMovimiento;
	}
	public void setStrFechaMovimiento(String strFechaMovimiento) {
		this.strFechaMovimiento = strFechaMovimiento;
	}
	public String getStrDescTipoCargoAbono() {
		return strDescTipoCargoAbono;
	}
	public void setStrDescTipoCargoAbono(String strDescTipoCargoAbono) {
		this.strDescTipoCargoAbono = strDescTipoCargoAbono;
	}
	public Integer getIntPeriodoFechaMovimiento() {
		return intPeriodoFechaMovimiento;
	}
	public void setIntPeriodoFechaMovimiento(Integer intPeriodoFechaMovimiento) {
		this.intPeriodoFechaMovimiento = intPeriodoFechaMovimiento;
	}
	public String getStrDescTipoMovimiento() {
		return strDescTipoMovimiento;
	}
	public void setStrDescTipoMovimiento(String strDescTipoMovimiento) {
		this.strDescTipoMovimiento = strDescTipoMovimiento;
	}
	public String getStrDescTipoConceptoGral() {
		return strDescTipoConceptoGral;
	}
	public void setStrDescTipoConceptoGral(String strDescTipoConceptoGral) {
		this.strDescTipoConceptoGral = strDescTipoConceptoGral;
	}
	public BigDecimal getBdMontoMovimientoEne() {
		return bdMontoMovimientoEne;
	}
	public void setBdMontoMovimientoEne(BigDecimal bdMontoMovimientoEne) {
		this.bdMontoMovimientoEne = bdMontoMovimientoEne;
	}
	public BigDecimal getBdMontoMovimientoFeb() {
		return bdMontoMovimientoFeb;
	}
	public void setBdMontoMovimientoFeb(BigDecimal bdMontoMovimientoFeb) {
		this.bdMontoMovimientoFeb = bdMontoMovimientoFeb;
	}
	public BigDecimal getBdMontoMovimientoMar() {
		return bdMontoMovimientoMar;
	}
	public void setBdMontoMovimientoMar(BigDecimal bdMontoMovimientoMar) {
		this.bdMontoMovimientoMar = bdMontoMovimientoMar;
	}
	public BigDecimal getBdMontoMovimientoAbr() {
		return bdMontoMovimientoAbr;
	}
	public void setBdMontoMovimientoAbr(BigDecimal bdMontoMovimientoAbr) {
		this.bdMontoMovimientoAbr = bdMontoMovimientoAbr;
	}
	public BigDecimal getBdMontoMovimientoMay() {
		return bdMontoMovimientoMay;
	}
	public void setBdMontoMovimientoMay(BigDecimal bdMontoMovimientoMay) {
		this.bdMontoMovimientoMay = bdMontoMovimientoMay;
	}
	public BigDecimal getBdMontoMovimientoJun() {
		return bdMontoMovimientoJun;
	}
	public void setBdMontoMovimientoJun(BigDecimal bdMontoMovimientoJun) {
		this.bdMontoMovimientoJun = bdMontoMovimientoJun;
	}
	public BigDecimal getBdMontoMovimientoJul() {
		return bdMontoMovimientoJul;
	}
	public void setBdMontoMovimientoJul(BigDecimal bdMontoMovimientoJul) {
		this.bdMontoMovimientoJul = bdMontoMovimientoJul;
	}
	public BigDecimal getBdMontoMovimientoAgo() {
		return bdMontoMovimientoAgo;
	}
	public void setBdMontoMovimientoAgo(BigDecimal bdMontoMovimientoAgo) {
		this.bdMontoMovimientoAgo = bdMontoMovimientoAgo;
	}
	public BigDecimal getBdMontoMovimientoSet() {
		return bdMontoMovimientoSet;
	}
	public void setBdMontoMovimientoSet(BigDecimal bdMontoMovimientoSet) {
		this.bdMontoMovimientoSet = bdMontoMovimientoSet;
	}
	public BigDecimal getBdMontoMovimientoOct() {
		return bdMontoMovimientoOct;
	}
	public void setBdMontoMovimientoOct(BigDecimal bdMontoMovimientoOct) {
		this.bdMontoMovimientoOct = bdMontoMovimientoOct;
	}
	public BigDecimal getBdMontoMovimientoNov() {
		return bdMontoMovimientoNov;
	}
	public void setBdMontoMovimientoNov(BigDecimal bdMontoMovimientoNov) {
		this.bdMontoMovimientoNov = bdMontoMovimientoNov;
	}
	public BigDecimal getBdMontoMovimientoDic() {
		return bdMontoMovimientoDic;
	}
	public void setBdMontoMovimientoDic(BigDecimal bdMontoMovimientoDic) {
		this.bdMontoMovimientoDic = bdMontoMovimientoDic;
	}
	public BigDecimal getBdMontoAcumulado() {
		return bdMontoAcumulado;
	}
	public void setBdMontoAcumulado(BigDecimal bdMontoAcumulado) {
		this.bdMontoAcumulado = bdMontoAcumulado;
	}
	public BigDecimal getBdMontoTotal() {
		return bdMontoTotal;
	}
	public void setBdMontoTotal(BigDecimal bdMontoTotal) {
		this.bdMontoTotal = bdMontoTotal;
	}
	public Integer getIntPeriodoFechaCptoPago() {
		return intPeriodoFechaCptoPago;
	}
	public void setIntPeriodoFechaCptoPago(Integer intPeriodoFechaCptoPago) {
		this.intPeriodoFechaCptoPago = intPeriodoFechaCptoPago;
	}
}

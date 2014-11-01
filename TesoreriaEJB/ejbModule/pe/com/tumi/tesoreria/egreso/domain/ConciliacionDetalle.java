package pe.com.tumi.tesoreria.egreso.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;

import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.tesoreria.ingreso.domain.Ingreso;

public class ConciliacionDetalle extends TumiDomain{

	private ConciliacionDetalleId id;
	private Integer intPersEmpresaEgreso;
	private Integer intItemEgresoGeneral;
	private Integer intPersEmpresaIngreso;
	private Integer intItemIngresoGeneral;
	private Integer intIndicadorCheck;
	private Integer intIndicadorConci;
	private BigDecimal bdSaldoInicial;
	private BigDecimal bdMontoDebe;
	private BigDecimal bdMontoHaber;
	private Integer intPersEmpresaCheckConciliacion;
	private Integer intPersPersonaCheckConciliacion;
	private Timestamp tsFechaCheck;
	private Integer intNumeroOperacion;
	
	/* Inicio: REQ14-006 Bizarq - 28/10/2014 */
	private Ingreso ingreso;
	private Egreso	egreso;
	private Boolean blIndicadorCheck;
	private Boolean blIndicadorConci;
	/* Fin: REQ14-006 Bizarq - 28/10/2014 */
	
	// RSIS14-006
	private Integer intSucuIdSucursalGira;
	private Integer intSubSucuIdSucursalGira;
	private Integer intSucuIdSucursalPaga;
	private Integer intSubSucuIdSucursalPaga;
	//
	
	public ConciliacionDetalleId getId() {
		return id;
	}
	public void setId(ConciliacionDetalleId id) {
		this.id = id;
	}
	public Integer getIntPersEmpresaEgreso() {
		return intPersEmpresaEgreso;
	}
	public void setIntPersEmpresaEgreso(Integer intPersEmpresaEgreso) {
		this.intPersEmpresaEgreso = intPersEmpresaEgreso;
	}
	public Integer getIntItemEgresoGeneral() {
		return intItemEgresoGeneral;
	}
	public void setIntItemEgresoGeneral(Integer intItemEgresoGeneral) {
		this.intItemEgresoGeneral = intItemEgresoGeneral;
	}
	public Integer getIntPersEmpresaIngreso() {
		return intPersEmpresaIngreso;
	}
	public void setIntPersEmpresaIngreso(Integer intPersEmpresaIngreso) {
		this.intPersEmpresaIngreso = intPersEmpresaIngreso;
	}
	public Integer getIntItemIngresoGeneral() {
		return intItemIngresoGeneral;
	}
	public void setIntItemIngresoGeneral(Integer intItemIngresoGeneral) {
		this.intItemIngresoGeneral = intItemIngresoGeneral;
	}
	public Integer getIntIndicadorCheck() {
		return intIndicadorCheck;
	}
	public void setIntIndicadorCheck(Integer intIndicadorCheck) {
		this.intIndicadorCheck = intIndicadorCheck;
	}
	public Integer getIntIndicadorConci() {
		return intIndicadorConci;
	}
	public void setIntIndicadorConci(Integer intIndicadorConci) {
		this.intIndicadorConci = intIndicadorConci;
	}
	public BigDecimal getBdSaldoInicial() {
		return bdSaldoInicial;
	}
	public void setBdSaldoInicial(BigDecimal bdSaldoInicial) {
		this.bdSaldoInicial = bdSaldoInicial;
	}
	public BigDecimal getBdMontoDebe() {
		return bdMontoDebe;
	}
	public void setBdMontoDebe(BigDecimal bdMontoDebe) {
		this.bdMontoDebe = bdMontoDebe;
	}
	public BigDecimal getBdMontoHaber() {
		return bdMontoHaber;
	}
	public void setBdMontoHaber(BigDecimal bdMontoHaber) {
		this.bdMontoHaber = bdMontoHaber;
	}
	public Integer getIntPersEmpresaCheckConciliacion() {
		return intPersEmpresaCheckConciliacion;
	}
	public void setIntPersEmpresaCheckConciliacion(Integer intPersEmpresaCheckConciliacion) {
		this.intPersEmpresaCheckConciliacion = intPersEmpresaCheckConciliacion;
	}
	public Integer getIntPersPersonaCheckConciliacion() {
		return intPersPersonaCheckConciliacion;
	}
	public void setIntPersPersonaCheckConciliacion(	Integer intPersPersonaCheckConciliacion) {
		this.intPersPersonaCheckConciliacion = intPersPersonaCheckConciliacion;
	}
	public Timestamp getTsFechaCheck() {
		return tsFechaCheck;
	}
	public void setTsFechaCheck(Timestamp tsFechaCheck) {
		this.tsFechaCheck = tsFechaCheck;
	}
	public Integer getIntNumeroOperacion() {
		return intNumeroOperacion;
	}
	public void setIntNumeroOperacion(Integer intNumeroOperacion) {
		this.intNumeroOperacion = intNumeroOperacion;
	}	
	public Ingreso getIngreso() {
		return ingreso;
	}
	public void setIngreso(Ingreso ingreso) {
		this.ingreso = ingreso;
	}
	public Egreso getEgreso() {
		return egreso;
	}
	public void setEgreso(Egreso egreso) {
		this.egreso = egreso;
	}
	public Boolean getBlIndicadorCheck() {
		return blIndicadorCheck;
	}
	public void setBlIndicadorCheck(Boolean blIndicadorCheck) {
		this.blIndicadorCheck = blIndicadorCheck;
	}
	public Boolean getBlIndicadorConci() {
		return blIndicadorConci;
	}
	public void setBlIndicadorConci(Boolean blIndicadorConci) {
		this.blIndicadorConci = blIndicadorConci;
	}
	
	// BIZARQ
	
	public Integer getIntSucuIdSucursalGira() {
		return intSucuIdSucursalGira;
	}
	public void setIntSucuIdSucursalGira(Integer intSucuIdSucursalGira) {
		this.intSucuIdSucursalGira = intSucuIdSucursalGira;
	}
	public Integer getIntSubSucuIdSucursalGira() {
		return intSubSucuIdSucursalGira;
	}
	public void setIntSubSucuIdSucursalGira(Integer intSubSucuIdSucursalGira) {
		this.intSubSucuIdSucursalGira = intSubSucuIdSucursalGira;
	}
	public Integer getIntSucuIdSucursalPaga() {
		return intSucuIdSucursalPaga;
	}
	public void setIntSucuIdSucursalPaga(Integer intSucuIdSucursalPaga) {
		this.intSucuIdSucursalPaga = intSucuIdSucursalPaga;
	}
	public Integer getIntSubSucuIdSucursalPaga() {
		return intSubSucuIdSucursalPaga;
	}
	
	public void setIntSubSucuIdSucursalPaga(Integer intSubSucuIdSucursalPaga) {
		this.intSubSucuIdSucursalPaga = intSubSucuIdSucursalPaga;
	}
	
	// BIZARQ
	
	@Override
	public String toString() {
		return "ConciliacionDetalle [id=" + id + ", intPersEmpresaEgreso="
				+ intPersEmpresaEgreso + ", intItemEgresoGeneral="
				+ intItemEgresoGeneral + ", intPersEmpresaIngreso="
				+ intPersEmpresaIngreso + ", intItemIngresoGeneral="
				+ intItemIngresoGeneral + ", intIndicadorCheck="
				+ intIndicadorCheck + ", intIndicadorConci="
				+ intIndicadorConci + ", bdSaldoInicial=" + bdSaldoInicial
				+ ", bdMontoDebe=" + bdMontoDebe + ", bdMontoHaber="
				+ bdMontoHaber + ", intPersEmpresaCheckConciliacion="
				+ intPersEmpresaCheckConciliacion
				+ ", intPersPersonaCheckConciliacion="
				+ intPersPersonaCheckConciliacion + ", tsFechaCheck="
				+ tsFechaCheck + ", intNumeroOperacion=" + intNumeroOperacion
				+ "]";
	}
}
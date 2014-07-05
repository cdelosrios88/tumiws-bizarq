package pe.com.tumi.tesoreria.egreso.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.tesoreria.banco.domain.Bancocuenta;

public class Conciliacion extends TumiDomain{

	private ConciliacionId id;
	private Timestamp tsFechaConciliacion;
	private Integer intPersEmpresa;
	private Integer intItemBancoFondo;
	private Integer intItemBancoCuenta;
	private BigDecimal bdMontoSaldoInicial;
	private BigDecimal bdMontoDebe;
	private BigDecimal bdMontoHaber;
	private Integer intRegistrosConciliados;
	private Integer intRegistrosNoConciliados;
	private Integer intPersEmpresaConcilia;
	private Integer intPersPersonaConcilia;
	private Integer intPersEmpresaAnula;
	private Integer intPersPersonaAnula;
	private Timestamp tsFechaAnula;
	private String 	strObservaciónAnula;
	private Integer intParaEstado;
	
	private List<ConciliacionDetalle> listaConciliacionDetalle;
	private Bancocuenta	bancoCuenta;
	private Integer intParaDocumentoGeneralFiltro;
	private Integer intEstadoCheckFiltro;
	
	public Conciliacion(){
		id = new ConciliacionId();
	}
	
	public ConciliacionId getId() {
		return id;
	}
	public void setId(ConciliacionId id) {
		this.id = id;
	}
	public Timestamp getTsFechaConciliacion() {
		return tsFechaConciliacion;
	}
	public void setTsFechaConciliacion(Timestamp tsFechaConciliacion) {
		this.tsFechaConciliacion = tsFechaConciliacion;
	}
	public Integer getIntPersEmpresa() {
		return intPersEmpresa;
	}
	public void setIntPersEmpresa(Integer intPersEmpresa) {
		this.intPersEmpresa = intPersEmpresa;
	}
	public Integer getIntItemBancoFondo() {
		return intItemBancoFondo;
	}
	public void setIntItemBancoFondo(Integer intItemBancoFondo) {
		this.intItemBancoFondo = intItemBancoFondo;
	}
	public Integer getIntItemBancoCuenta() {
		return intItemBancoCuenta;
	}
	public void setIntItemBancoCuenta(Integer intItemBancoCuenta) {
		this.intItemBancoCuenta = intItemBancoCuenta;
	}
	public BigDecimal getBdMontoSaldoInicial() {
		return bdMontoSaldoInicial;
	}
	public void setBdMontoSaldoInicial(BigDecimal bdMontoSaldoInicial) {
		this.bdMontoSaldoInicial = bdMontoSaldoInicial;
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
	public Integer getIntRegistrosConciliados() {
		return intRegistrosConciliados;
	}
	public void setIntRegistrosConciliados(Integer intRegistrosConciliados) {
		this.intRegistrosConciliados = intRegistrosConciliados;
	}
	public Integer getIntRegistrosNoConciliados() {
		return intRegistrosNoConciliados;
	}
	public void setIntRegistrosNoConciliados(Integer intRegistrosNoConciliados) {
		this.intRegistrosNoConciliados = intRegistrosNoConciliados;
	}
	public Integer getIntPersEmpresaConcilia() {
		return intPersEmpresaConcilia;
	}
	public void setIntPersEmpresaConcilia(Integer intPersEmpresaConcilia) {
		this.intPersEmpresaConcilia = intPersEmpresaConcilia;
	}
	public Integer getIntPersPersonaConcilia() {
		return intPersPersonaConcilia;
	}
	public void setIntPersPersonaConcilia(Integer intPersPersonaConcilia) {
		this.intPersPersonaConcilia = intPersPersonaConcilia;
	}
	public Integer getIntPersEmpresaAnula() {
		return intPersEmpresaAnula;
	}
	public void setIntPersEmpresaAnula(Integer intPersEmpresaAnula) {
		this.intPersEmpresaAnula = intPersEmpresaAnula;
	}
	public Integer getIntPersPersonaAnula() {
		return intPersPersonaAnula;
	}
	public void setIntPersPersonaAnula(Integer intPersPersonaAnula) {
		this.intPersPersonaAnula = intPersPersonaAnula;
	}
	public Timestamp getTsFechaAnula() {
		return tsFechaAnula;
	}
	public void setTsFechaAnula(Timestamp tsFechaAnula) {
		this.tsFechaAnula = tsFechaAnula;
	}
	public String getStrObservaciónAnula() {
		return strObservaciónAnula;
	}
	public void setStrObservaciónAnula(String strObservaciónAnula) {
		this.strObservaciónAnula = strObservaciónAnula;
	}
	public Integer getIntParaEstado() {
		return intParaEstado;
	}
	public void setIntParaEstado(Integer intParaEstado) {
		this.intParaEstado = intParaEstado;
	}
	public Bancocuenta getBancoCuenta() {
		return bancoCuenta;
	}
	public void setBancoCuenta(Bancocuenta bancoCuenta) {
		this.bancoCuenta = bancoCuenta;
	}
	public Integer getIntParaDocumentoGeneralFiltro() {
		return intParaDocumentoGeneralFiltro;
	}
	public void setIntParaDocumentoGeneralFiltro(Integer intParaDocumentoGeneralFiltro) {
		this.intParaDocumentoGeneralFiltro = intParaDocumentoGeneralFiltro;
	}
	public Integer getIntEstadoCheckFiltro() {
		return intEstadoCheckFiltro;
	}
	public void setIntEstadoCheckFiltro(Integer intEstadoCheckFiltro) {
		this.intEstadoCheckFiltro = intEstadoCheckFiltro;
	}	
	public List<ConciliacionDetalle> getListaConciliacionDetalle() {
		return listaConciliacionDetalle;
	}
	public void setListaConciliacionDetalle(List<ConciliacionDetalle> listaConciliacionDetalle) {
		this.listaConciliacionDetalle = listaConciliacionDetalle;
	}

	@Override
	public String toString() {
		return "Conciliacion [id=" + id + ", tsFechaConciliacion="
				+ tsFechaConciliacion + ", intPersEmpresa=" + intPersEmpresa
				+ ", intItemBancoFondo=" + intItemBancoFondo
				+ ", intItemBancoCuenta=" + intItemBancoCuenta
				+ ", bdMontoSaldoInicial=" + bdMontoSaldoInicial
				+ ", bdMontoDebe=" + bdMontoDebe + ", bdMontoHaber="
				+ bdMontoHaber + ", intRegistrosConciliados="
				+ intRegistrosConciliados + ", intRegistrosNoConciliados="
				+ intRegistrosNoConciliados + ", intPersEmpresaConcilia="
				+ intPersEmpresaConcilia + ", intPersPersonaConcilia="
				+ intPersPersonaConcilia + ", intPersEmpresaAnula="
				+ intPersEmpresaAnula + ", intPersPersonaAnula="
				+ intPersPersonaAnula + ", tsFechaAnula=" + tsFechaAnula
				+ ", strObservaciónAnula=" + strObservaciónAnula
				+ ", intParaEstado=" + intParaEstado + "]";
	}
}
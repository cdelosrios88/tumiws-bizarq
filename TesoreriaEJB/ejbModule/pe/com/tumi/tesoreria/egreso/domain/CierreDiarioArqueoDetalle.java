package pe.com.tumi.tesoreria.egreso.domain;

import java.math.BigDecimal;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class CierreDiarioArqueoDetalle extends TumiDomain{

	private CierreDiarioArqueoDetalleId	id;
	private Integer		intPersEmpresa;
	private	Integer		intParaTipoFondoFijo;
	private	Integer		intItemPeriodoFondo;
	private	Integer		intItemFondoFijo;
	private BigDecimal 	bdMontoApertura;
	private BigDecimal 	bdMontoRendido;
	private String 		strDetalleRendido;
	private BigDecimal 	bdMontoIngreso;
	private BigDecimal 	bdMontoDeposito;
	private BigDecimal 	bdMontoReciboSunat;
	private String 		strDetalleReciboSunat;
	private BigDecimal 	bdMontoValesRendir;
	private String 		strDetalleValeRendir;
	private BigDecimal 	bdMontoIngPendiente;
	private String 		strDetalleIngPendiente;
	private BigDecimal 	bdAjusteRedondeo;
	private BigDecimal 	bdMontoSaldoEfectivo;
	private Integer 	intItemBancoFondo;
	private Integer 	intItemBancoCuenta;
	private BigDecimal 	bdMontoCuentaBancaria;
	
	private String		strNumeroApertura;
	
	private String 		strFechaRegistro;
	
	//Autor: jchavez / Tarea: Creación / Fecha: 13.08.2014 /
	private BigDecimal	bdMontoAjuste;
	
	public CierreDiarioArqueoDetalle(){
		id = new CierreDiarioArqueoDetalleId();
	}
	
	
	public CierreDiarioArqueoDetalleId getId() {
		return id;
	}
	public void setId(CierreDiarioArqueoDetalleId id) {
		this.id = id;
	}
	public Integer getIntPersEmpresa() {
		return intPersEmpresa;
	}
	public void setIntPersEmpresa(Integer intPersEmpresa) {
		this.intPersEmpresa = intPersEmpresa;
	}
	public Integer getIntParaTipoFondoFijo() {
		return intParaTipoFondoFijo;
	}
	public void setIntParaTipoFondoFijo(Integer intParaTipoFondoFijo) {
		this.intParaTipoFondoFijo = intParaTipoFondoFijo;
	}
	public Integer getIntItemPeriodoFondo() {
		return intItemPeriodoFondo;
	}
	public void setIntItemPeriodoFondo(Integer intItemPeriodoFondo) {
		this.intItemPeriodoFondo = intItemPeriodoFondo;
	}
	public Integer getIntItemFondoFijo() {
		return intItemFondoFijo;
	}
	public void setIntItemFondoFijo(Integer intItemFondoFijo) {
		this.intItemFondoFijo = intItemFondoFijo;
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
	public BigDecimal getBdMontoCuentaBancaria() {
		return bdMontoCuentaBancaria;
	}
	public void setBdMontoCuentaBancaria(BigDecimal bdMontoCuentaBancaria) {
		this.bdMontoCuentaBancaria = bdMontoCuentaBancaria;
	}
	public BigDecimal getBdMontoApertura() {
		return bdMontoApertura;
	}
	public void setBdMontoApertura(BigDecimal bdMontoApertura) {
		this.bdMontoApertura = bdMontoApertura;
	}
	public BigDecimal getBdMontoRendido() {
		return bdMontoRendido;
	}
	public void setBdMontoRendido(BigDecimal bdMontoRendido) {
		this.bdMontoRendido = bdMontoRendido;
	}
	public String getStrDetalleRendido() {
		return strDetalleRendido;
	}
	public void setStrDetalleRendido(String strDetalleRendido) {
		this.strDetalleRendido = strDetalleRendido;
	}
	public BigDecimal getBdMontoIngreso() {
		return bdMontoIngreso;
	}
	public void setBdMontoIngreso(BigDecimal bdMontoIngreso) {
		this.bdMontoIngreso = bdMontoIngreso;
	}
	public BigDecimal getBdMontoDeposito() {
		return bdMontoDeposito;
	}
	public void setBdMontoDeposito(BigDecimal bdMontoDeposito) {
		this.bdMontoDeposito = bdMontoDeposito;
	}
	public BigDecimal getBdMontoReciboSunat() {
		return bdMontoReciboSunat;
	}
	public void setBdMontoReciboSunat(BigDecimal bdMontoReciboSunat) {
		this.bdMontoReciboSunat = bdMontoReciboSunat;
	}
	public String getStrDetalleReciboSunat() {
		return strDetalleReciboSunat;
	}
	public void setStrDetalleReciboSunat(String strDetalleReciboSunat) {
		this.strDetalleReciboSunat = strDetalleReciboSunat;
	}
	public BigDecimal getBdMontoValesRendir() {
		return bdMontoValesRendir;
	}
	public void setBdMontoValesRendir(BigDecimal bdMontoValesRendir) {
		this.bdMontoValesRendir = bdMontoValesRendir;
	}
	public String getStrDetalleValeRendir() {
		return strDetalleValeRendir;
	}
	public void setStrDetalleValeRendir(String strDetalleValeRendir) {
		this.strDetalleValeRendir = strDetalleValeRendir;
	}
	public BigDecimal getBdMontoIngPendiente() {
		return bdMontoIngPendiente;
	}
	public void setBdMontoIngPendiente(BigDecimal bdMontoIngPendiente) {
		this.bdMontoIngPendiente = bdMontoIngPendiente;
	}
	public String getStrDetalleIngPendiente() {
		return strDetalleIngPendiente;
	}
	public void setStrDetalleIngPendiente(String strDetalleIngPendiente) {
		this.strDetalleIngPendiente = strDetalleIngPendiente;
	}
	public BigDecimal getBdAjusteRedondeo() {
		return bdAjusteRedondeo;
	}
	public void setBdAjusteRedondeo(BigDecimal bdAjusteRedondeo) {
		this.bdAjusteRedondeo = bdAjusteRedondeo;
	}
	public BigDecimal getBdMontoSaldoEfectivo() {
		return bdMontoSaldoEfectivo;
	}
	public void setBdMontoSaldoEfectivo(BigDecimal bdMontoSaldoEfectivo) {
		this.bdMontoSaldoEfectivo = bdMontoSaldoEfectivo;
	}
	public String getStrNumeroApertura() {
		return strNumeroApertura;
	}
	public void setStrNumeroApertura(String strNumeroApertura) {
		this.strNumeroApertura = strNumeroApertura;
	}
	public String getStrFechaRegistro() {
		return strFechaRegistro;
	}
	public void setStrFechaRegistro(String strFechaRegistro) {
		this.strFechaRegistro = strFechaRegistro;
	}


	@Override
	public String toString() {
		return "CierreDiarioArqueoDetalle [id=" + id + ", intPersEmpresa="
				+ intPersEmpresa + ", intParaTipoFondoFijo="
				+ intParaTipoFondoFijo + ", intItemPeriodoFondo="
				+ intItemPeriodoFondo + ", intSucuIdSucursal="
				+ intItemFondoFijo
				+ ", intItemBancoFondo=" + intItemBancoFondo
				+ ", intItemBancoCuenta=" + intItemBancoCuenta
				+ ", bdMontoCuentaBancaria=" + bdMontoCuentaBancaria
				+ ", bdMontoApertura=" + bdMontoApertura + ", bdMontoRendido="
				+ bdMontoRendido + ", strDetalleRendido=" + strDetalleRendido
				+ ", bdMontoIngreso=" + bdMontoIngreso + ", bdMontoDeposito="
				+ bdMontoDeposito + ", bdMontoReciboSunat="
				+ bdMontoReciboSunat + ", strDetalleReciboSunat="
				+ strDetalleReciboSunat + ", bdMontoValesRendir="
				+ bdMontoValesRendir + ", strDetalleValeRendir="
				+ strDetalleValeRendir + ", bdMontoIngPendiente="
				+ bdMontoIngPendiente + ", strDetalleIngPendiente="
				+ strDetalleIngPendiente + ", bdAjusteRedondeo="
				+ bdAjusteRedondeo + ", bdMontoSaldoEfectivo="
				+ bdMontoSaldoEfectivo + "]";
	}
	public BigDecimal getBdMontoAjuste() {
		return bdMontoAjuste;
	}
	public void setBdMontoAjuste(BigDecimal bdMontoAjuste) {
		this.bdMontoAjuste = bdMontoAjuste;
	}
}
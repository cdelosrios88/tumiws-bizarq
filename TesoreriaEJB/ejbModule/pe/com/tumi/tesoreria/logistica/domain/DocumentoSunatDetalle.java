package pe.com.tumi.tesoreria.logistica.domain;

import java.math.BigDecimal;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class DocumentoSunatDetalle extends TumiDomain{

	private DocumentoSunatDetalleId	id;
	private Integer intParaTipoDocumentoSunat;
	private Integer intPersEmpresaOrdenCompra;
	private Integer intItemOrdenCompra;
	private Integer intItemOrdenCompraDetalle;
	private Integer intAfectoIGV;
	private Integer intParaTipoMoneda;
	private BigDecimal bdMontoSinTipoCambio;
	private BigDecimal bdMontoTotal;
	private String 	strDescripcion;
	private Integer intSucuIdSucursal;
	private Integer intSudeIdSubsucursal;
	private Integer intIdArea;
	private Integer intParaEstado;
	private Integer intPersEmpresaCuenta;
	private Integer intContPeriodoCuenta;
	private String	strContNumeroCuenta;
	
	private BigDecimal	bdMontoSinIGV;
	private BigDecimal	bdMontoIGV;	
	private OrdenCompraDetalle	ordenCompraDetalle;
	
	
	public DocumentoSunatDetalle(){
		id = new DocumentoSunatDetalleId();
	}
	
	public DocumentoSunatDetalleId getId() {
		return id;
	}
	public void setId(DocumentoSunatDetalleId id) {
		this.id = id;
	}
	public Integer getIntParaTipoDocumentoSunat() {
		return intParaTipoDocumentoSunat;
	}
	public void setIntParaTipoDocumentoSunat(Integer intParaTipoDocumentoSunat) {
		this.intParaTipoDocumentoSunat = intParaTipoDocumentoSunat;
	}
	public Integer getIntPersEmpresaOrdenCompra() {
		return intPersEmpresaOrdenCompra;
	}
	public void setIntPersEmpresaOrdenCompra(Integer intPersEmpresaOrdenCompra) {
		this.intPersEmpresaOrdenCompra = intPersEmpresaOrdenCompra;
	}
	public Integer getIntItemOrdenCompra() {
		return intItemOrdenCompra;
	}
	public void setIntItemOrdenCompra(Integer intItemOrdenCompra) {
		this.intItemOrdenCompra = intItemOrdenCompra;
	}
	public Integer getIntItemOrdenCompraDetalle() {
		return intItemOrdenCompraDetalle;
	}
	public void setIntItemOrdenCompraDetalle(Integer intItemOrdenCompraDetalle) {
		this.intItemOrdenCompraDetalle = intItemOrdenCompraDetalle;
	}
	public Integer getIntAfectoIGV() {
		return intAfectoIGV;
	}
	public void setIntAfectoIGV(Integer intAfectoIGV) {
		this.intAfectoIGV = intAfectoIGV;
	}
	public Integer getIntParaTipoMoneda() {
		return intParaTipoMoneda;
	}
	public void setIntParaTipoMoneda(Integer intParaTipoMoneda) {
		this.intParaTipoMoneda = intParaTipoMoneda;
	}
	public String getStrDescripcion() {
		return strDescripcion;
	}
	public void setStrDescripcion(String strDescripcion) {
		this.strDescripcion = strDescripcion;
	}
	public Integer getIntSucuIdSucursal() {
		return intSucuIdSucursal;
	}
	public void setIntSucuIdSucursal(Integer intSucuIdSucursal) {
		this.intSucuIdSucursal = intSucuIdSucursal;
	}
	public Integer getIntSudeIdSubsucursal() {
		return intSudeIdSubsucursal;
	}
	public void setIntSudeIdSubsucursal(Integer intSudeIdSubsucursal) {
		this.intSudeIdSubsucursal = intSudeIdSubsucursal;
	}
	public Integer getIntIdArea() {
		return intIdArea;
	}
	public void setIntIdArea(Integer intIdArea) {
		this.intIdArea = intIdArea;
	}
	public Integer getIntParaEstado() {
		return intParaEstado;
	}
	public void setIntParaEstado(Integer intParaEstado) {
		this.intParaEstado = intParaEstado;
	}
	public Integer getIntPersEmpresaCuenta() {
		return intPersEmpresaCuenta;
	}
	public void setIntPersEmpresaCuenta(Integer intPersEmpresaCuenta) {
		this.intPersEmpresaCuenta = intPersEmpresaCuenta;
	}
	public Integer getIntContPeriodoCuenta() {
		return intContPeriodoCuenta;
	}
	public void setIntContPeriodoCuenta(Integer intContPeriodoCuenta) {
		this.intContPeriodoCuenta = intContPeriodoCuenta;
	}
	public String getStrContNumeroCuenta() {
		return strContNumeroCuenta;
	}
	public void setStrContNumeroCuenta(String strContNumeroCuenta) {
		this.strContNumeroCuenta = strContNumeroCuenta;
	}
	public OrdenCompraDetalle getOrdenCompraDetalle() {
		return ordenCompraDetalle;
	}
	public void setOrdenCompraDetalle(OrdenCompraDetalle ordenCompraDetalle) {
		this.ordenCompraDetalle = ordenCompraDetalle;
	}	
	public BigDecimal getBdMontoSinIGV() {
		return bdMontoSinIGV;
	}
	public void setBdMontoSinIGV(BigDecimal bdMontoSinIGV) {
		this.bdMontoSinIGV = bdMontoSinIGV;
	}
	public BigDecimal getBdMontoIGV() {
		return bdMontoIGV;
	}
	public void setBdMontoIGV(BigDecimal bdMontoIGV) {
		this.bdMontoIGV = bdMontoIGV;
	}
	public BigDecimal getBdMontoSinTipoCambio() {
		return bdMontoSinTipoCambio;
	}
	public void setBdMontoSinTipoCambio(BigDecimal bdMontoSinTipoCambio) {
		this.bdMontoSinTipoCambio = bdMontoSinTipoCambio;
	}
	public BigDecimal getBdMontoTotal() {
		return bdMontoTotal;
	}
	public void setBdMontoTotal(BigDecimal bdMontoTotal) {
		this.bdMontoTotal = bdMontoTotal;
	}

	@Override
	public String toString() {
		return "DocumentoSunatDetalle [id=" + id
				+ ", intParaTipoDocumentoSunat=" + intParaTipoDocumentoSunat
				+ ", intPersEmpresaOrdenCompra=" + intPersEmpresaOrdenCompra
				+ ", intItemOrdenCompra=" + intItemOrdenCompra
				+ ", intItemOrdenCompraDetalle=" + intItemOrdenCompraDetalle
				+ ", intAfectoIGV=" + intAfectoIGV + ", intParaTipoMoneda="
				+ intParaTipoMoneda + ", bdMontoSinTipoCambio="
				+ bdMontoSinTipoCambio + ", bdMontoTotal=" + bdMontoTotal
				+ ", strDescripcion=" + strDescripcion + ", intSucuIdSucursal="
				+ intSucuIdSucursal + ", intSudeIdSubsucursal="
				+ intSudeIdSubsucursal + ", intIdArea=" + intIdArea
				+ ", intParaEstado=" + intParaEstado
				+ ", intPersEmpresaCuenta=" + intPersEmpresaCuenta
				+ ", intContPeriodoCuenta=" + intContPeriodoCuenta
				+ ", strContNumeroCuenta=" + strContNumeroCuenta + "]";
	}	
}
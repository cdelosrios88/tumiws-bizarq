package pe.com.tumi.contabilidad.cierre.domain;

import java.math.BigDecimal;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class LibroDiarioDetalle extends TumiDomain{

	private LibroDiarioDetalleId id;
	private Integer 	intPersEmpresaCuenta;
	private Integer 	intContPeriodo;
	private String 		strContNumeroCuenta;
	private Integer 	intPersPersona;
	private Integer 	intParaDocumentoGeneral;
	private String 		strSerieDocumento;
	private String		strNumeroDocumento;
	private Integer		intPersEmpresaSucursal;
	private Integer 	intSucuIdSucursal;
	private Integer 	intSudeIdSubSucursal;
	private Integer 	intParaMonedaDocumento;
	private Integer 	intTipoCambio;
	private BigDecimal	bdDebeSoles;
	private BigDecimal 	bdHaberSoles;
	private BigDecimal 	bdDebeExtranjero;
	private BigDecimal 	bdHaberExtranjero;
	private String 		strComentario;
	private Integer 	intParaTipoComprobante;
	
	public LibroDiarioDetalle(){
		id = new LibroDiarioDetalleId();
	}
	
	//GETTERS & SETTERS
	public LibroDiarioDetalleId getId() {
		return id;
	}
	public void setId(LibroDiarioDetalleId id) {
		this.id = id;
	}
	public Integer getIntPersEmpresaCuenta() {
		return intPersEmpresaCuenta;
	}
	public void setIntPersEmpresaCuenta(Integer intPersEmpresaCuenta) {
		this.intPersEmpresaCuenta = intPersEmpresaCuenta;
	}
	public Integer getIntContPeriodo() {
		return intContPeriodo;
	}
	public void setIntContPeriodo(Integer intContPeriodo) {
		this.intContPeriodo = intContPeriodo;
	}
	public String getStrContNumeroCuenta() {
		return strContNumeroCuenta;
	}
	public void setStrContNumeroCuenta(String strContNumeroCuenta) {
		this.strContNumeroCuenta = strContNumeroCuenta;
	}
	public Integer getIntPersPersona() {
		return intPersPersona;
	}
	public void setIntPersPersona(Integer intPersPersona) {
		this.intPersPersona = intPersPersona;
	}
	public Integer getIntParaDocumentoGeneral() {
		return intParaDocumentoGeneral;
	}
	public void setIntParaDocumentoGeneral(Integer intParaDocumentoGeneral) {
		this.intParaDocumentoGeneral = intParaDocumentoGeneral;
	}
	public String getStrSerieDocumento() {
		return strSerieDocumento;
	}
	public void setStrSerieDocumento(String strSerieDocumento) {
		this.strSerieDocumento = strSerieDocumento;
	}
	public String getStrNumeroDocumento() {
		return strNumeroDocumento;
	}
	public void setStrNumeroDocumento(String strNumeroDocumento) {
		this.strNumeroDocumento = strNumeroDocumento;
	}
	public Integer getIntPersEmpresaSucursal() {
		return intPersEmpresaSucursal;
	}
	public void setIntPersEmpresaSucursal(Integer intPersEmpresaSucursal) {
		this.intPersEmpresaSucursal = intPersEmpresaSucursal;
	}
	public Integer getIntSucuIdSucursal() {
		return intSucuIdSucursal;
	}
	public void setIntSucuIdSucursal(Integer intSucuIdSucursal) {
		this.intSucuIdSucursal = intSucuIdSucursal;
	}
	public Integer getIntSudeIdSubSucursal() {
		return intSudeIdSubSucursal;
	}
	public void setIntSudeIdSubSucursal(Integer intSudeIdSubSucursal) {
		this.intSudeIdSubSucursal = intSudeIdSubSucursal;
	}
	public Integer getIntParaMonedaDocumento() {
		return intParaMonedaDocumento;
	}
	public void setIntParaMonedaDocumento(Integer intParaMonedaDocumento) {
		this.intParaMonedaDocumento = intParaMonedaDocumento;
	}
	public Integer getIntTipoCambio() {
		return intTipoCambio;
	}
	public void setIntTipoCambio(Integer intTipoCambio) {
		this.intTipoCambio = intTipoCambio;
	}
	public BigDecimal getBdDebeSoles() {
		return bdDebeSoles;
	}
	public void setBdDebeSoles(BigDecimal bdDebeSoles) {
		this.bdDebeSoles = bdDebeSoles;
	}
	public BigDecimal getBdHaberSoles() {
		return bdHaberSoles;
	}
	public void setBdHaberSoles(BigDecimal bdHaberSoles) {
		this.bdHaberSoles = bdHaberSoles;
	}
	public BigDecimal getBdDebeExtranjero() {
		return bdDebeExtranjero;
	}
	public void setBdDebeExtranjero(BigDecimal bdDebeExtranjero) {
		this.bdDebeExtranjero = bdDebeExtranjero;
	}
	public BigDecimal getBdHaberExtranjero() {
		return bdHaberExtranjero;
	}
	public void setBdHaberExtranjero(BigDecimal bdHaberExtranjero) {
		this.bdHaberExtranjero = bdHaberExtranjero;
	}
	public String getStrComentario() {
		return strComentario;
	}
	public void setStrComentario(String strComentario) {
		this.strComentario = strComentario;
	}
	public Integer getIntParaTipoComprobante() {
		return intParaTipoComprobante;
	}
	public void setIntParaTipoComprobante(Integer intParaTipoComprobante) {
		this.intParaTipoComprobante = intParaTipoComprobante;
	}
	
	@Override
	public String toString() {
		return "LibroDiarioDetalle [id=" + id 
				+ ", intPersEmpresaCuenta="+ intPersEmpresaCuenta 
				+ ", intContPeriodo=" + intContPeriodo
				+ ", strContNumeroCuenta=" + strContNumeroCuenta
				+ ", intPersPersona=" + intPersPersona
				+ ", intParaDocumentoGeneral=" + intParaDocumentoGeneral
				+ ", strSerieDocumento=" + strSerieDocumento
				+ ", strNumeroDocumento=" + strNumeroDocumento
				+ ", intPersEmpresaSucursal=" + intPersEmpresaSucursal
				+ ", intSucuIdSucursal=" + intSucuIdSucursal
				+ ", intSudeIdSubSucursal=" + intSudeIdSubSucursal
				+ ", intParaMonedaDocumento=" + intParaMonedaDocumento
				+ ", intTipoCambio= " + intTipoCambio 
				+ ", bdDebeSoles= "+ bdDebeSoles 
				+ ", bdHaberSoles= " + bdHaberSoles
				+ ", bdDebeExtranjero= " + bdDebeExtranjero
				+ ", bdHaberExtranjero= " + bdHaberExtranjero
				+ ", strComentario= " + strComentario 
				+ ", intParaTipoComprobante= " + intParaTipoComprobante + "]";
	}	
}
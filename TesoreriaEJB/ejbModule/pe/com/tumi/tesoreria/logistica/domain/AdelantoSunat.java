package pe.com.tumi.tesoreria.logistica.domain;

import java.math.BigDecimal;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class AdelantoSunat extends TumiDomain {

	private AdelantoSunatId			id;
	private OrdenCompraDocumentoId	ordenCompraDocumentoId;
	private DocumentoSunatId		documentoSunatId;
	private BigDecimal				bdMonto;
	private Integer					intParaEstado;
	//Agregado por cdelosrios, 12/11/2013
	private Integer					intItemOrdenCompraDet;
	//Fin agregado por cdelosrios, 12/11/2013
	
	public AdelantoSunat(){
		id = new AdelantoSunatId();
	}
	
	public AdelantoSunatId getId() {
		return id;
	}
	public void setId(AdelantoSunatId id) {
		this.id = id;
	}
	public OrdenCompraDocumentoId getOrdenCompraDocumentoId() {
		return ordenCompraDocumentoId;
	}
	public void setOrdenCompraDocumentoId(OrdenCompraDocumentoId ordenCompraDocumentoId) {
		this.ordenCompraDocumentoId = ordenCompraDocumentoId;
	}
	public DocumentoSunatId getDocumentoSunatId() {
		return documentoSunatId;
	}
	public void setDocumentoSunatId(DocumentoSunatId documentoSunatId) {
		this.documentoSunatId = documentoSunatId;
	}
	public BigDecimal getBdMonto() {
		return bdMonto;
	}
	public void setBdMonto(BigDecimal bdMonto) {
		this.bdMonto = bdMonto;
	}
	public Integer getIntParaEstado() {
		return intParaEstado;
	}
	public void setIntParaEstado(Integer intParaEstado) {
		this.intParaEstado = intParaEstado;
	}
	//Agregado por cdelosrios, 12/11/2013
	public Integer getIntItemOrdenCompraDet() {
		return intItemOrdenCompraDet;
	}
	public void setIntItemOrdenCompraDet(Integer intItemOrdenCompraDet) {
		this.intItemOrdenCompraDet = intItemOrdenCompraDet;
	}
	//Fin agregado por cdelosrios, 12/11/2013

	@Override
	public String toString() {
		return "AdelantoSunat [id=" + id + ", ordenCompraDocumentoId="
				+ ordenCompraDocumentoId + ", documentoSunatId="
				+ documentoSunatId + ", bdMonto=" + bdMonto
				+ ", intParaEstado=" + intParaEstado + "]";
	}	
}
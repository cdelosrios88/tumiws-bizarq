package pe.com.tumi.tesoreria.logistica.domain;

import java.math.BigDecimal;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class DocumentoSunatLetra extends TumiDomain{
	private DocumentoSunatLetraId id;
	private Integer intPersEmpresaDocSunatLetra;	//PERS_EMPRESADOCSUNATLETRA_N_PK
	private Integer intItemDocSunatLetraEnlazada; 	//TESO_ITEMDOCSUNLETRA_N
	private BigDecimal bdMontoCancelado;			//DOST_MONTOCANCELADO_N
	private Integer intItemDocumentoSunatDoc;		//TESO_ITEMDOCSUNDOC_N
	
	public DocumentoSunatLetra(){
		id = new DocumentoSunatLetraId();
	}
	public DocumentoSunatLetraId getId() {
		return id;
	}
	public void setId(DocumentoSunatLetraId id) {
		this.id = id;
	}
	public Integer getIntPersEmpresaDocSunatLetra() {
		return intPersEmpresaDocSunatLetra;
	}
	public void setIntPersEmpresaDocSunatLetra(Integer intPersEmpresaDocSunatLetra) {
		this.intPersEmpresaDocSunatLetra = intPersEmpresaDocSunatLetra;
	}	
	public Integer getIntItemDocSunatLetraEnlazada() {
		return intItemDocSunatLetraEnlazada;
	}
	public void setIntItemDocSunatLetraEnlazada(Integer intItemDocSunatLetraEnlazada) {
		this.intItemDocSunatLetraEnlazada = intItemDocSunatLetraEnlazada;
	}
	public BigDecimal getBdMontoCancelado() {
		return bdMontoCancelado;
	}
	public void setBdMontoCancelado(BigDecimal bdMontoCancelado) {
		this.bdMontoCancelado = bdMontoCancelado;
	}
	public Integer getIntItemDocumentoSunatDoc() {
		return intItemDocumentoSunatDoc;
	}
	public void setIntItemDocumentoSunatDoc(Integer intItemDocumentoSunatDoc) {
		this.intItemDocumentoSunatDoc = intItemDocumentoSunatDoc;
	}
}

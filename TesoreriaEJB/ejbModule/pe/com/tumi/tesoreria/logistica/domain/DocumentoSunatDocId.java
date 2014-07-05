package pe.com.tumi.tesoreria.logistica.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class DocumentoSunatDocId extends TumiDomain {
	private Integer intPersEmpresa;
	private Integer intItemDocumentoSunat;
	private Integer intItemDocumentoSunatDoc;
	
	public Integer getIntPersEmpresa() {
		return intPersEmpresa;
	}
	public void setIntPersEmpresa(Integer intPersEmpresa) {
		this.intPersEmpresa = intPersEmpresa;
	}
	public Integer getIntItemDocumentoSunat() {
		return intItemDocumentoSunat;
	}
	public void setIntItemDocumentoSunat(Integer intItemDocumentoSunat) {
		this.intItemDocumentoSunat = intItemDocumentoSunat;
	}
	public Integer getIntItemDocumentoSunatDoc() {
		return intItemDocumentoSunatDoc;
	}
	public void setIntItemDocumentoSunatDoc(Integer intItemDocumentoSunatDoc) {
		this.intItemDocumentoSunatDoc = intItemDocumentoSunatDoc;
	}
}

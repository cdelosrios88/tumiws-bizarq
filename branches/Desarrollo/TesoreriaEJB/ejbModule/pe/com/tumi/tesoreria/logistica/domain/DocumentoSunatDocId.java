package pe.com.tumi.tesoreria.logistica.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class DocumentoSunatDocId extends TumiDomain {
	private Integer intPersEmpresa;				//PERS_EMPRESADOCSUNAT_N_PK
	private Integer intItemDocumentoSunat;		//TESO_ITEMDOCSUN_N
	private Integer intItemDocumentoSunatDoc;	//TESO_ITEMDOCSUNDOC_N
	
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

package pe.com.tumi.tesoreria.logistica.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class DocumentoSunatLetraId extends TumiDomain{
	private Integer intPersEmpresaDocSunat;		//PERS_EMPRESADOCSUNAT_N_PK
	private Integer intItemDocumentoSunat;		//TESO_ITEMDOCSUN_N
	private Integer intItemDocumentoSunatLetra;	//TESO_ITEMDOCUSUNATLETRA_N
	
	public Integer getIntPersEmpresaDocSunat() {
		return intPersEmpresaDocSunat;
	}
	public void setIntPersEmpresaDocSunat(Integer intPersEmpresaDocSunat) {
		this.intPersEmpresaDocSunat = intPersEmpresaDocSunat;
	}
	public Integer getIntItemDocumentoSunat() {
		return intItemDocumentoSunat;
	}
	public void setIntItemDocumentoSunat(Integer intItemDocumentoSunat) {
		this.intItemDocumentoSunat = intItemDocumentoSunat;
	}
	public Integer getIntItemDocumentoSunatLetra() {
		return intItemDocumentoSunatLetra;
	}
	public void setIntItemDocumentoSunatLetra(Integer intItemDocumentoSunatLetra) {
		this.intItemDocumentoSunatLetra = intItemDocumentoSunatLetra;
	}
}

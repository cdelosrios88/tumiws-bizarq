package pe.com.tumi.tesoreria.logistica.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class DocumentoSunatId extends TumiDomain{

	private Integer intPersEmpresa;			//PERS_EMPRESADOCSUNAT_N_PK
	private Integer intItemDocumentoSunat;	//TESO_ITEMDOCSUN_N
	
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
	@Override
	public String toString() {
		return "DocumentoSunatId [intPersEmpresa=" + intPersEmpresa
				+ ", intItemDocumentoSunat=" + intItemDocumentoSunat + "]";
	}
}

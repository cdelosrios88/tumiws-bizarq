package pe.com.tumi.tesoreria.logistica.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class DocumentoSunatEgresoId extends TumiDomain{

	private Integer intPersEmpresa;
	private Integer intItemDocumentoSunat;
	private Integer intItemDocumentoSunatEgreso;
	
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
		return "DocumentoSunatEgresoId [intPersEmpresa=" + intPersEmpresa
				+ ", intItemDocumentoSunat=" + intItemDocumentoSunat
				+ ", intItemDocumentoSunatEgreso="
				+ intItemDocumentoSunatEgreso + "]";
	}
	public Integer getIntItemDocumentoSunatEgreso() {
		return intItemDocumentoSunatEgreso;
	}
	public void setIntItemDocumentoSunatEgreso(Integer intItemDocumentoSunatEgreso) {
		this.intItemDocumentoSunatEgreso = intItemDocumentoSunatEgreso;
	}
}

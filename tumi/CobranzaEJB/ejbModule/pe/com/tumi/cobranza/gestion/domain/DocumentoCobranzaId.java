package pe.com.tumi.cobranza.gestion.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class DocumentoCobranzaId extends TumiDomain{

	private Integer intPersEmresaDocCob;
	private Integer intItemDocCob;
	public Integer getIntPersEmresaDocCob() {
		return intPersEmresaDocCob;
	}
	public void setIntPersEmresaDocCob(Integer intPersEmresaDocCob) {
		this.intPersEmresaDocCob = intPersEmresaDocCob;
	}
	public Integer getIntItemDocCob() {
		return intItemDocCob;
	}
	public void setIntItemDocCob(Integer intItemDocCob) {
		this.intItemDocCob = intItemDocCob;
	}
	
	
	
}	
	
package pe.com.tumi.credito.socio.ctacte.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class CtaCteCondicion extends TumiDomain {

	private CtaCteCondicionId id;
	private CtaCte ctaCte;
	private Integer intValor;
	
	public CtaCteCondicionId getId() {
		return id;
	}
	public void setId(CtaCteCondicionId id) {
		this.id = id;
	}
	public CtaCte getCtaCte() {
		return ctaCte;
	}
	public void setCtaCte(CtaCte ctaCte) {
		this.ctaCte = ctaCte;
	}
	public Integer getIntValor() {
		return intValor;
	}
	public void setIntValor(Integer intValor) {
		this.intValor = intValor;
	}

}

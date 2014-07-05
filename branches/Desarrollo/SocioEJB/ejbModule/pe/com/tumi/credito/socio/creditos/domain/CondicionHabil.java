package pe.com.tumi.credito.socio.creditos.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class CondicionHabil extends TumiDomain {
	private CondicionHabilId id;
	private Integer intValor;
	
	//Getters y Setters
	public Integer getIntValor() {
		return intValor;
	}
	public CondicionHabilId getId() {
		return id;
	}
	public void setId(CondicionHabilId id) {
		this.id = id;
	}
	public void setIntValor(Integer intValor) {
		this.intValor = intValor;
	}
}

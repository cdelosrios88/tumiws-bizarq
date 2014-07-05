package pe.com.tumi.credito.socio.creditos.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class Finalidad extends TumiDomain {
	private FinalidadId id;
	private Integer intValor;
	
	//Getters y Setters
	public FinalidadId getId() {
		return id;
	}
	public void setId(FinalidadId id) {
		this.id = id;
	}
	public Integer getIntValor() {
		return intValor;
	}
	public void setIntValor(Integer intValor) {
		this.intValor = intValor;
	}
}

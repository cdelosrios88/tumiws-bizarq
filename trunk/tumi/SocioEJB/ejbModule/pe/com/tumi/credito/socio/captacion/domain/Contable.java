package pe.com.tumi.credito.socio.captacion.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class Contable extends TumiDomain {

	private ContableId id;
	private Captacion captacion;
	private Integer intValor;
	
	public ContableId getId() {
		return id;
	}
	public void setId(ContableId id) {
		this.id = id;
	}
	public Captacion getCaptacion() {
		return captacion;
	}
	public void setCaptacion(Captacion captacion) {
		this.captacion = captacion;
	}
	public Integer getIntValor() {
		return intValor;
	}
	public void setIntValor(Integer intValor) {
		this.intValor = intValor;
	}

}

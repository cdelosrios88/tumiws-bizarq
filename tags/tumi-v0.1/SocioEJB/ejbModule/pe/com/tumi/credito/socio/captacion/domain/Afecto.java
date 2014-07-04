package pe.com.tumi.credito.socio.captacion.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class Afecto extends TumiDomain {

	private AfectoId id;
	private Captacion captacion;
	private Integer intIdValor;
	
	public AfectoId getId() {
		return id;
	}
	public void setId(AfectoId id) {
		this.id = id;
	}
	public Captacion getCaptacion() {
		return captacion;
	}
	public void setCaptacion(Captacion captacion) {
		this.captacion = captacion;
	}
	public Integer getIntIdValor() {
		return intIdValor;
	}
	public void setIntIdValor(Integer intIdValor) {
		this.intIdValor = intIdValor;
	}

}

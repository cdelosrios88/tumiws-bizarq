package pe.com.tumi.credito.socio.convenio.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class PoblacionDetalle extends TumiDomain {

	private PoblacionDetalleId id;
	private Poblacion poblacion;
	private Integer intPadron;
	
	public PoblacionDetalle(){
	}
	
	public PoblacionDetalle(PoblacionDetalleId id, Integer intPadron) {
		super();
		this.id = id;
		this.intPadron = intPadron;
	}
	public PoblacionDetalleId getId() {
		return id;
	}
	public void setId(PoblacionDetalleId id) {
		this.id = id;
	}
	public Poblacion getPoblacion() {
		return poblacion;
	}
	public void setPoblacion(Poblacion poblacion) {
		this.poblacion = poblacion;
	}
	public Integer getIntPadron() {
		return intPadron;
	}
	public void setIntPadron(Integer intPadron) {
		this.intPadron = intPadron;
	}
}

package pe.com.tumi.riesgo.cartera.domain;

public class EspecificacionId {

	private Integer intItemEspecificacion;
	private Integer intItemCartera;
	public Integer getIntItemEspecificacion() {
		return intItemEspecificacion;
	}
	public void setIntItemEspecificacion(Integer intItemEspecificacion) {
		this.intItemEspecificacion = intItemEspecificacion;
	}
	public Integer getIntItemCartera() {
		return intItemCartera;
	}
	public void setIntItemCartera(Integer intItemCartera) {
		this.intItemCartera = intItemCartera;
	}
	@Override
	public String toString() {
		return "EspecificacionId [intItemEspecificacion="
				+ intItemEspecificacion + ", intItemCartera=" + intItemCartera
				+ "]";
	}
	
	
}

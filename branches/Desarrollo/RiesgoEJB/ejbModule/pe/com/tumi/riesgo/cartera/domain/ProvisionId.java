package pe.com.tumi.riesgo.cartera.domain;

public class ProvisionId {

	private Integer intItemProvision;
	private Integer intItemEspecificacion;
	private Integer intItemCartera;
	public Integer getIntItemProvision() {
		return intItemProvision;
	}
	public void setIntItemProvision(Integer intItemProvision) {
		this.intItemProvision = intItemProvision;
	}
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
		return "ProvisionId [intItemProvision=" + intItemProvision
				+ ", intItemEspecificacion=" + intItemEspecificacion
				+ ", intItemCartera=" + intItemCartera + "]";
	}
	
}

package pe.com.tumi.riesgo.cartera.domain;

public class ProciclicoId {

	private Integer intItemProciclico;
	private Integer intItemEspecificacion;
	private Integer intItemCartera;
	
	public Integer getIntItemProciclico() {
		return intItemProciclico;
	}
	public void setIntItemProciclico(Integer intItemProciclico) {
		this.intItemProciclico = intItemProciclico;
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
		return "ProciclicoId [intItemProciclico=" + intItemProciclico
				+ ", intItemEspecificacion=" + intItemEspecificacion
				+ ", intItemCartera=" + intItemCartera + "]";
	}
	
	
	
}

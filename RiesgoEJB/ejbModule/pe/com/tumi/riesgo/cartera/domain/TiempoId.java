package pe.com.tumi.riesgo.cartera.domain;

public class TiempoId {

	private Integer intItemTiempo;
	private Integer intParaTipoSbsCod;
	private Integer intParaTipoProvisionCod;
	public Integer getIntItemTiempo() {
		return intItemTiempo;
	}
	public void setIntItemTiempo(Integer intItemTiempo) {
		this.intItemTiempo = intItemTiempo;
	}
	public Integer getIntParaTipoSbsCod() {
		return intParaTipoSbsCod;
	}
	public void setIntParaTipoSbsCod(Integer intParaTipoSbsCod) {
		this.intParaTipoSbsCod = intParaTipoSbsCod;
	}
	public Integer getIntParaTipoProvisionCod() {
		return intParaTipoProvisionCod;
	}
	public void setIntParaTipoProvisionCod(Integer intParaTipoProvisionCod) {
		this.intParaTipoProvisionCod = intParaTipoProvisionCod;
	}
	@Override
	public String toString() {
		return "TiempoId [intItemTiempo=" + intItemTiempo
				+ ", intParaTipoSbsCod=" + intParaTipoSbsCod
				+ ", intParaTipoProvisionCod=" + intParaTipoProvisionCod + "]";
	}
	
	
}

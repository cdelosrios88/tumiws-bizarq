package pe.com.tumi.riesgo.cartera.domain;

public class PlantillaId {

	private Integer intParaTipoSbsCod;
	private Integer intParaTipoProvisionCod;
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
		return "PlantillaId [intParaTipoSbsCod=" + intParaTipoSbsCod
				+ ", intParaTipoProvisionCod=" + intParaTipoProvisionCod + "]";
	}
	
}

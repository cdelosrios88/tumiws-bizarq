package pe.com.tumi.riesgo.cartera.domain;

public class PlantillaDetalleId {

	private Integer intItemPlantillaDetalle;
	private Integer intParaTipoSbsCod;
	private Integer intParaTipoProvisionCod;
	public Integer getIntItemPlantillaDetalle() {
		return intItemPlantillaDetalle;
	}
	public void setIntItemPlantillaDetalle(Integer intItemPlantillaDetalle) {
		this.intItemPlantillaDetalle = intItemPlantillaDetalle;
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
		return "PlantillaDetalleId [intItemPlantillaDetalle="
				+ intItemPlantillaDetalle + ", intParaTipoSbsCod="
				+ intParaTipoSbsCod + ", intParaTipoProvisionCod="
				+ intParaTipoProvisionCod + "]";
	}
	
	
}

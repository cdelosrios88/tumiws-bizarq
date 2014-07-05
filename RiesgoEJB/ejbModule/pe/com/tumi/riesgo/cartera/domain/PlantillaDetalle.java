package pe.com.tumi.riesgo.cartera.domain;

public class PlantillaDetalle {

	private PlantillaDetalleId id;
	private Integer intParaTipoGarantiaCod;
	private Integer intParaTipoPorcionCod;
	private Integer intParaTipoEstadoCod;
	
	public PlantillaDetalle(){
		id = new PlantillaDetalleId();
	}
	public PlantillaDetalleId getId() {
		return id;
	}
	public void setId(PlantillaDetalleId id) {
		this.id = id;
	}
	public Integer getIntParaTipoGarantiaCod() {
		return intParaTipoGarantiaCod;
	}
	public void setIntParaTipoGarantiaCod(Integer intParaTipoGarantiaCod) {
		this.intParaTipoGarantiaCod = intParaTipoGarantiaCod;
	}
	public Integer getIntParaTipoPorcionCod() {
		return intParaTipoPorcionCod;
	}
	public void setIntParaTipoPorcionCod(Integer intParaTipoPorcionCod) {
		this.intParaTipoPorcionCod = intParaTipoPorcionCod;
	}
	public Integer getIntParaTipoEstadoCod() {
		return intParaTipoEstadoCod;
	}
	public void setIntParaTipoEstadoCod(Integer intParaTipoEstadoCod) {
		this.intParaTipoEstadoCod = intParaTipoEstadoCod;
	}
	@Override
	public String toString() {
		return "PlantillaDetalle [id=" + id + ", intParaTipoGarantiaCod="
				+ intParaTipoGarantiaCod + ", intParaTipoPorcionCod="
				+ intParaTipoPorcionCod + ", intParaTipoEstadoCod="
				+ intParaTipoEstadoCod + "]";
	}
	
}

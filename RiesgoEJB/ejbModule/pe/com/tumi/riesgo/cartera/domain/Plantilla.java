package pe.com.tumi.riesgo.cartera.domain;

public class Plantilla {

	private PlantillaId id;
	private Integer intParaEstadoCod;
	
	public Plantilla(){
		id = new PlantillaId();
	}
	public PlantillaId getId() {
		return id;
	}
	public void setId(PlantillaId id) {
		this.id = id;
	}
	public Integer getIntParaEstadoCod() {
		return intParaEstadoCod;
	}
	public void setIntParaEstadoCod(Integer intParaEstadoCod) {
		this.intParaEstadoCod = intParaEstadoCod;
	}
	@Override
	public String toString() {
		return "Plantilla [id=" + id + ", intParaEstadoCod=" + intParaEstadoCod
				+ "]";
	}
	
}

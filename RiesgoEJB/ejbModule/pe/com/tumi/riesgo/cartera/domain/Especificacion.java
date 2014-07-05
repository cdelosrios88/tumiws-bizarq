package pe.com.tumi.riesgo.cartera.domain;

import java.util.ArrayList;
import java.util.List;

public class Especificacion implements Cloneable{

	private EspecificacionId id;
	private Integer intItemPlantillaDetalle;
	private Integer intParaTipoSbsCod;
	private Integer intParaTipoProvisionCod;

	private PlantillaDetalle plantillaDetalle;
	private List<Provision> listaProvision;
	private Prociclico prociclico;
	
	public Especificacion(){
		id = new EspecificacionId();
		plantillaDetalle = new PlantillaDetalle();
		listaProvision = new ArrayList<Provision>();
		prociclico = new Prociclico();
	}
	
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
	public EspecificacionId getId() {
		return id;
	}
	public void setId(EspecificacionId id) {
		this.id = id;
	}
	public List<Provision> getListaProvision() {
		return listaProvision;
	}
	public void setListaProvision(List<Provision> listaProvision) {
		this.listaProvision = listaProvision;
	}
	public PlantillaDetalle getPlantillaDetalle() {
		return plantillaDetalle;
	}
	public void setPlantillaDetalle(PlantillaDetalle plantillaDetalle) {
		this.plantillaDetalle = plantillaDetalle;
	}
	public Prociclico getProciclico() {
		return prociclico;
	}
	public void setProciclico(Prociclico prociclico) {
		this.prociclico = prociclico;
	}

	@Override
	public String toString() {
		return "Especificacion [id=" + id + ", intItemPlantillaDetalle="
				+ intItemPlantillaDetalle + ", intParaTipoSbsCod="
				+ intParaTipoSbsCod + ", intParaTipoProvisionCod="
				+ intParaTipoProvisionCod + ", plantillaDetalle="
				+ plantillaDetalle + ", listaProvision=" + listaProvision
				+ ", prociclico=" + prociclico + "]";
	}
	
	
}
package pe.com.tumi.persona.empresa.domain;

import java.sql.Timestamp;
import java.util.Date;

import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.parametro.tabla.domain.Tabla;

public class ActividadEconomica extends TumiDomain{

	private ActividadEconomicaPK id;
	private Integer intSectorEconomicoCod;
	private Integer intActividadEconomicaCod;
	private Integer intEstadoCod;
	private Timestamp tsFechaEliminacion;
	private	Tabla	tabla;
	private Boolean blnChecked;
	private Juridica juridica;
	
	public ActividadEconomicaPK getId() {
		return id;
	}
	public void setId(ActividadEconomicaPK id) {
		this.id = id;
	}
	public Integer getIntActividadEconomicaCod() {
		return intActividadEconomicaCod;
	}
	public void setIntActividadEconomicaCod(Integer intActividadEconomicaCod) {
		this.intActividadEconomicaCod = intActividadEconomicaCod;
	}
	public Integer getIntEstadoCod() {
		return intEstadoCod;
	}
	public void setIntEstadoCod(Integer intEstadoCod) {
		this.intEstadoCod = intEstadoCod;
	}
	public Integer getIntSectorEconomicoCod() {
		return intSectorEconomicoCod;
	}
	public void setIntSectorEconomicoCod(Integer intSectorEconomicoCod) {
		this.intSectorEconomicoCod = intSectorEconomicoCod;
	}
	public Juridica getJuridica() {
		return juridica;
	}
	public void setJuridica(Juridica juridica) {
		this.juridica = juridica;
	}
	public Tabla getTabla() {
		return tabla;
	}
	public void setTabla(Tabla tabla) {
		this.tabla = tabla;
	}
	public Boolean getBlnChecked() {
		return blnChecked;
	}
	public void setBlnChecked(Boolean blnChecked) {
		this.blnChecked = blnChecked;
	}
	public Timestamp getTsFechaEliminacion() {
		return tsFechaEliminacion;
	}
	public void setTsFechaEliminacion(Timestamp tsFechaEliminacion) {
		this.tsFechaEliminacion = tsFechaEliminacion;
	}
	
}
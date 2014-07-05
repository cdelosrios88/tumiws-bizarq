package pe.com.tumi.persona.core.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.parametro.tabla.domain.Tabla;

import java.util.Date;


public class PersonaRol extends TumiDomain{

	private PersonaRolPK id;
	private Integer intEstadoCod;
	private Date dtFechaFin;
	private Date dtFechaEliminacion;
	private PersonaEmpresa personaEmpresa;
	private Tabla tabla;
	private Boolean isChecked;
	
	
	public PersonaRolPK getId() {
		return id;
	}
	public void setId(PersonaRolPK id) {
		this.id = id;
	}
	public Integer getIntEstadoCod() {
		return intEstadoCod;
	}
	public void setIntEstadoCod(Integer intEstadoCod) {
		this.intEstadoCod = intEstadoCod;
	}
	public PersonaEmpresa getPersonaEmpresa() {
		return personaEmpresa;
	}
	public void setPersonaEmpresa(PersonaEmpresa personaEmpresa) {
		this.personaEmpresa = personaEmpresa;
	}
	public Tabla getTabla() {
		return tabla;
	}
	public void setTabla(Tabla tabla) {
		this.tabla = tabla;
	}
	public Date getDtFechaFin() {
		return dtFechaFin;
	}
	public void setDtFechaFin(Date dtFechaFin) {
		this.dtFechaFin = dtFechaFin;
	}
	public Date getDtFechaEliminacion() {
		return dtFechaEliminacion;
	}
	public void setDtFechaEliminacion(Date dtFechaEliminacion) {
		this.dtFechaEliminacion = dtFechaEliminacion;
	}
	public Boolean getIsChecked() {
		return isChecked;
	}
	public void setIsChecked(Boolean isChecked) {
		this.isChecked = isChecked;
	}
	@Override
	public String toString() {
		return "PersonaRol [id=" + id + ", intEstadoCod=" + intEstadoCod
				+ ", dtFechaFin=" + dtFechaFin + ", dtFechaEliminacion="
				+ dtFechaEliminacion + "]";
	}
	
}
package pe.com.tumi.persona.contacto.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.parametro.tabla.domain.Tabla;
import pe.com.tumi.persona.core.domain.Persona;

public class Documento extends TumiDomain{

	private DocumentoPK id;
	private Integer intTipoIdentidadCod;
	private String strNumeroIdentidad;
	private Integer intEstadoCod;
	private Tabla tabla;

	private Persona persona;

	public DocumentoPK getId() {
		return id;
	}
	public void setId(DocumentoPK id) {
		this.id = id;
	}

	public String getStrNumeroIdentidad() {
		return strNumeroIdentidad;
	}
	public void setStrNumeroIdentidad(String strNumeroIdentidad) {
		this.strNumeroIdentidad = strNumeroIdentidad;
	}

	public Integer getIntEstadoCod() {
		return intEstadoCod;
	}
	public void setIntEstadoCod(Integer intEstadoCod) {
		this.intEstadoCod = intEstadoCod;
	}

	public Persona getPersona() {
		return persona;
	}
	public void setPersona(Persona persona) {
		this.persona = persona;
	}
	public Integer getIntTipoIdentidadCod() {
		return intTipoIdentidadCod;
	}
	public void setIntTipoIdentidadCod(Integer intTipoIdentidadCod) {
		this.intTipoIdentidadCod = intTipoIdentidadCod;
	}
	public Tabla getTabla() {
		return tabla;
	}
	public void setTabla(Tabla tabla) {
		this.tabla = tabla;
	}
	@Override
	public String toString() {
		return "Documento [id=" + id + ", intTipoIdentidadCod="
				+ intTipoIdentidadCod + ", strNumeroIdentidad="
				+ strNumeroIdentidad + ", intEstadoCod=" + intEstadoCod + "]";
	}

}
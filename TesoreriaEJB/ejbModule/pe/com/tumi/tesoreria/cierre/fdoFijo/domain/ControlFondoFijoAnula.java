package pe.com.tumi.tesoreria.cierre.fdoFijo.domain;
/*Creado 05.12.2013 JCHAVEZ*/

import java.sql.Timestamp;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class ControlFondoFijoAnula extends TumiDomain {
	private ControlFondoFijoAnulaId id;
	private Timestamp tsFechaAnulacion;
	private Integer intParaTipoAnulaFondo;
	private String strObservacion;
	private Integer intIdEmpresaUsuario;
	private Integer intIdPersonaUsuario;
	
	public ControlFondoFijoAnulaId getId() {
		return id;
	}
	public void setId(ControlFondoFijoAnulaId id) {
		this.id = id;
	}
	public Timestamp getTsFechaAnulacion() {
		return tsFechaAnulacion;
	}
	public void setTsFechaAnulacion(Timestamp tsFechaAnulacion) {
		this.tsFechaAnulacion = tsFechaAnulacion;
	}
	public Integer getIntParaTipoAnulaFondo() {
		return intParaTipoAnulaFondo;
	}
	public void setIntParaTipoAnulaFondo(Integer intParaTipoAnulaFondo) {
		this.intParaTipoAnulaFondo = intParaTipoAnulaFondo;
	}
	public String getStrObservacion() {
		return strObservacion;
	}
	public void setStrObservacion(String strObservacion) {
		this.strObservacion = strObservacion;
	}
	public Integer getIntIdEmpresaUsuario() {
		return intIdEmpresaUsuario;
	}
	public void setIntIdEmpresaUsuario(Integer intIdEmpresaUsuario) {
		this.intIdEmpresaUsuario = intIdEmpresaUsuario;
	}
	public Integer getIntIdPersonaUsuario() {
		return intIdPersonaUsuario;
	}
	public void setIntIdPersonaUsuario(Integer intIdPersonaUsuario) {
		this.intIdPersonaUsuario = intIdPersonaUsuario;
	}
}

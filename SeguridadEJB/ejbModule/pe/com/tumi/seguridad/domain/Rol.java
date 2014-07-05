package pe.com.tumi.seguridad.domain;

import java.util.ArrayList;
import java.util.List;
import pe.com.tumi.common.domain.EntidadBase;

public class Rol {
	
	private Integer intIdRol;
	private String 	strNombRol;
	private Integer intEstado;
	
	
	public Integer getIntIdRol() {
		return intIdRol;
	}
	public void setIntIdRol(Integer intIdRol) {
		this.intIdRol = intIdRol;
	}
	public String getStrNombRol() {
		return strNombRol;
	}
	public void setStrNombRol(String strNombRol) {
		this.strNombRol = strNombRol;
	}
	public Integer getIntEstado() {
		return intEstado;
	}
	public void setIntEstado(Integer intEstado) {
		this.intEstado = intEstado;
	}
	
}
package pe.com.tumi.parametro.general.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class Ubigeo extends TumiDomain{
	private Integer intIdUbigeo;
	private String 	strDescripcion;
	private Integer intEstado;
	
	public Integer getIntIdUbigeo() {
		return intIdUbigeo;
	}
	public void setIntIdUbigeo(Integer intIdUbigeo) {
		this.intIdUbigeo = intIdUbigeo;
	}
	public String getStrDescripcion() {
		return strDescripcion;
	}
	public void setStrDescripcion(String strDescripcion) {
		this.strDescripcion = strDescripcion;
	}
	public Integer getIntEstado() {
		return intEstado;
	}
	public void setIntEstado(Integer intEstado) {
		this.intEstado = intEstado;
	}
}

package pe.com.tumi.parametro.tabla.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class Tabla extends TumiDomain{
	private Integer intIdMaestro;
	private Integer intIdDetalle;
	private String 	strDescripcion;
	private String  strAbreviatura;
	private Integer intOrden;
	private Integer intEstado;
	private String  strIdAgrupamientoA;
	private String	strIdAgrupamientoB;
	private Integer tipoVisualizacion;
	
	public Integer getIntIdMaestro() {
		return intIdMaestro;
	}
	public void setIntIdMaestro(Integer intIdMaestro) {
		this.intIdMaestro = intIdMaestro;
	}
	public Integer getIntIdDetalle() {
		return intIdDetalle;
	}
	public void setIntIdDetalle(Integer intIdDetalle) {
		this.intIdDetalle = intIdDetalle;
	}
	public String getStrDescripcion() {
		return strDescripcion;
	}
	public void setStrDescripcion(String strDescripcion) {
		this.strDescripcion = strDescripcion;
	}
	public String getStrAbreviatura() {
		return strAbreviatura;
	}
	public void setStrAbreviatura(String strAbreviatura) {
		this.strAbreviatura = strAbreviatura;
	}
	public Integer getIntOrden() {
		return intOrden;
	}
	public void setIntOrden(Integer intOrden) {
		this.intOrden = intOrden;
	}
	public Integer getIntEstado() {
		return intEstado;
	}
	public void setIntEstado(Integer intEstado) {
		this.intEstado = intEstado;
	}
	public String getStrIdAgrupamientoA() {
		return strIdAgrupamientoA;
	}
	public void setStrIdAgrupamientoA(String strIdAgrupamientoA) {
		this.strIdAgrupamientoA = strIdAgrupamientoA;
	}
	public String getStrIdAgrupamientoB() {
		return strIdAgrupamientoB;
	}
	public void setStrIdAgrupamientoB(String strIdAgrupamientoB) {
		this.strIdAgrupamientoB = strIdAgrupamientoB;
	}
	public Integer getTipoVisualizacion() {
		return tipoVisualizacion;
	}
	public void setTipoVisualizacion(Integer tipoVisualizacion) {
		this.tipoVisualizacion = tipoVisualizacion;
	}
}
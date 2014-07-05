package pe.com.tumi.seguridad.permiso.domain;

import java.util.List;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class Diccionario extends TumiDomain {

	private Integer intCodigo;
	private Integer intTipo;
	private String  strDescripcion;
	private Integer intNivel;
	private Integer intSeguridad;
	private Integer intIdEstado;
	private List<SolicitudCambio> listaSolicitudCambio;
	private List<Contenido> listaContenido;
	public Integer getIntCodigo() {
		return intCodigo;
	}
	public void setIntCodigo(Integer intCodigo) {
		this.intCodigo = intCodigo;
	}
	public Integer getIntTipo() {
		return intTipo;
	}
	public void setIntTipo(Integer intTipo) {
		this.intTipo = intTipo;
	}
	public String getStrDescripcion() {
		return strDescripcion;
	}
	public void setStrDescripcion(String strDescripcion) {
		this.strDescripcion = strDescripcion;
	}
	public Integer getIntNivel() {
		return intNivel;
	}
	public void setIntNivel(Integer intNivel) {
		this.intNivel = intNivel;
	}
	public Integer getIntSeguridad() {
		return intSeguridad;
	}
	public void setIntSeguridad(Integer intSeguridad) {
		this.intSeguridad = intSeguridad;
	}
	public Integer getIntIdEstado() {
		return intIdEstado;
	}
	public void setIntIdEstado(Integer intIdEstado) {
		this.intIdEstado = intIdEstado;
	}
	public List<SolicitudCambio> getListaSolicitudCambio() {
		return listaSolicitudCambio;
	}
	public void setListaSolicitudCambio(List<SolicitudCambio> listaSolicitudCambio) {
		this.listaSolicitudCambio = listaSolicitudCambio;
	}
	public List<Contenido> getListaContenido() {
		return listaContenido;
	}
	public void setListaContenido(List<Contenido> listaContenido) {
		this.listaContenido = listaContenido;
	}
	
}

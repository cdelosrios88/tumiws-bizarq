package pe.com.tumi.contabilidad.cierre.domain;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import pe.com.tumi.contabilidad.core.domain.PlanCuenta;
import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class Anexo extends TumiDomain{

	private AnexoId		id;
	private String		strDescripcion;
	private Timestamp	tsFechaRegistro;
	private Integer		intParaTipoLibroAnexo;
	private Integer		intParaTipoIndicador;
	private Integer		intParaPeriodicidad;
	private Integer		intPersEmpresaUsuario;
	private	Integer		intPersPersonaUsuario;
	private	List<AnexoDetalle>	listaAnexoDetalle;
	
	//Interfaz
	private String		strAnexoDetalleBusqueda;
	private List<String>listaDescripciones;
	
	public Anexo(){
		super();
		id = new AnexoId();
		listaAnexoDetalle = new ArrayList<AnexoDetalle>();
		listaDescripciones = new ArrayList<String>();
	}
	
	public AnexoId getId() {
		return id;
	}
	public void setId(AnexoId id) {
		this.id = id;
	}
	public String getStrDescripcion() {
		return strDescripcion;
	}
	public void setStrDescripcion(String strDescripcion) {
		this.strDescripcion = strDescripcion;
	}
	public Timestamp getTsFechaRegistro() {
		return tsFechaRegistro;
	}
	public void setTsFechaRegistro(Timestamp tsFechaRegistro) {
		this.tsFechaRegistro = tsFechaRegistro;
	}
	public Integer getIntParaTipoLibroAnexo() {
		return intParaTipoLibroAnexo;
	}
	public void setIntParaTipoLibroAnexo(Integer intParaTipoLibroAnexo) {
		this.intParaTipoLibroAnexo = intParaTipoLibroAnexo;
	}
	public Integer getIntParaTipoIndicador() {
		return intParaTipoIndicador;
	}
	public void setIntParaTipoIndicador(Integer intParaTipoIndicador) {
		this.intParaTipoIndicador = intParaTipoIndicador;
	}
	public Integer getIntParaPeriodicidad() {
		return intParaPeriodicidad;
	}
	public void setIntParaPeriodicidad(Integer intParaPeriodicidad) {
		this.intParaPeriodicidad = intParaPeriodicidad;
	}
	public Integer getIntPersEmpresaUsuario() {
		return intPersEmpresaUsuario;
	}
	public void setIntPersEmpresaUsuario(Integer intPersEmpresaUsuario) {
		this.intPersEmpresaUsuario = intPersEmpresaUsuario;
	}
	public Integer getIntPersPersonaUsuario() {
		return intPersPersonaUsuario;
	}
	public void setIntPersPersonaUsuario(Integer intPersPersonaUsuario) {
		this.intPersPersonaUsuario = intPersPersonaUsuario;
	}
	public String getStrAnexoDetalleBusqueda() {
		return strAnexoDetalleBusqueda;
	}
	public void setStrAnexoDetalleBusqueda(String strAnexoDetalleBusqueda) {
		this.strAnexoDetalleBusqueda = strAnexoDetalleBusqueda;
	}
	public List<AnexoDetalle> getListaAnexoDetalle() {
		return listaAnexoDetalle;
	}
	public void setListaAnexoDetalle(List<AnexoDetalle> listaAnexoDetalle) {
		this.listaAnexoDetalle = listaAnexoDetalle;
	}
	public List<String> getListaDescripciones() {
		return listaDescripciones;
	}
	public void setListaDescripciones(List<String> listaDescripciones) {
		this.listaDescripciones = listaDescripciones;
	}
	@Override
	public String toString() {
		return "Anexo [id=" + id + ", strDescripcion=" + strDescripcion
				+ ", tsFechaRegistro=" + tsFechaRegistro
				+ ", intParaTipoLibroAnexo=" + intParaTipoLibroAnexo
				+ ", intParaTipoIndicador=" + intParaTipoIndicador
				+ ", intParaPeriodicidad=" + intParaPeriodicidad
				+ ", intPersEmpresaUsuario=" + intPersEmpresaUsuario
				+ ", intPersPersonaUsuario=" + intPersPersonaUsuario+ "]";
	}
}
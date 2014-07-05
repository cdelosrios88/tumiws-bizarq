package pe.com.tumi.tesoreria.logistica.domain;

import java.sql.Timestamp;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class RequisicionDetalle extends TumiDomain{

	private RequisicionDetalleId id;
	private Integer 	intCantidad;
	private String 		strDetalle;
	private String 		strObservacion;
	private Integer 	intParaEstado;
	private Integer 	intPersEmpresaUsuario;
	private Integer 	intPersPersonaUsuario;
	private Timestamp	tsFechaRegistro;
	private Integer 	intPersEmpresaAnula;
	private Integer 	intPersPersonaAnula;
	private Timestamp 	tsFechaAnula;
	
	public RequisicionDetalle(){
		id = new RequisicionDetalleId();
	}
	
	public RequisicionDetalleId getId() {
		return id;
	}
	public void setId(RequisicionDetalleId id) {
		this.id = id;
	}
	public Integer getIntCantidad() {
		return intCantidad;
	}
	public void setIntCantidad(Integer intCantidad) {
		this.intCantidad = intCantidad;
	}
	public String getStrDetalle() {
		return strDetalle;
	}
	public void setStrDetalle(String strDetalle) {
		this.strDetalle = strDetalle;
	}
	public String getStrObservacion() {
		return strObservacion;
	}
	public void setStrObservacion(String strObservacion) {
		this.strObservacion = strObservacion;
	}
	public Integer getIntParaEstado() {
		return intParaEstado;
	}
	public void setIntParaEstado(Integer intParaEstado) {
		this.intParaEstado = intParaEstado;
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
	public Timestamp getTsFechaRegistro() {
		return tsFechaRegistro;
	}
	public void setTsFechaRegistro(Timestamp tsFechaRegistro) {
		this.tsFechaRegistro = tsFechaRegistro;
	}
	public Integer getIntPersEmpresaAnula() {
		return intPersEmpresaAnula;
	}
	public void setIntPersEmpresaAnula(Integer intPersEmpresaAnula) {
		this.intPersEmpresaAnula = intPersEmpresaAnula;
	}
	public Integer getIntPersPersonaAnula() {
		return intPersPersonaAnula;
	}
	public void setIntPersPersonaAnula(Integer intPersPersonaAnula) {
		this.intPersPersonaAnula = intPersPersonaAnula;
	}
	public Timestamp getTsFechaAnula() {
		return tsFechaAnula;
	}
	public void setTsFechaAnula(Timestamp tsFechaAnula) {
		this.tsFechaAnula = tsFechaAnula;
	}

	@Override
	public String toString() {
		return "RequisicionDetalle [id=" + id + ", intCantidad=" + intCantidad
				+ ", strDetalle=" + strDetalle + ", strObservacion="
				+ strObservacion + ", intParaEstado=" + intParaEstado
				+ ", intPersEmpresaUsuario=" + intPersEmpresaUsuario
				+ ", intPersPersonaUsuario=" + intPersPersonaUsuario
				+ ", tsFechaRegistro=" + tsFechaRegistro
				+ ", intPersEmpresaAnula=" + intPersEmpresaAnula
				+ ", intPersPersonaAnula=" + intPersPersonaAnula
				+ ", tsFechaAnula=" + tsFechaAnula + "]";
	}
}
package pe.com.tumi.servicio.configuracion.domain;

import java.sql.Timestamp;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class ConfServDetalle extends TumiDomain{

	private ConfServDetalleId id;
	private ConfServSolicitud solicitud;
	private Integer intParaTipoDescripcion;
	private Integer intParaTipoPersonaOperacionCod;
	private Integer intOpcionAdjunta;
	private Integer intParaEstadoCod;
	private Timestamp tsFechaRegistro;
	private Integer intPersPersonaUsuarioPk;
	private Timestamp tsFechaEliminacion;
	private Integer intPersPersonaEliminaPk;
	
	public ConfServDetalle(){
		id = new ConfServDetalleId();
	}
	public ConfServDetalleId getId() {
		return id;
	}
	public void setId(ConfServDetalleId id) {
		this.id = id;
	}
	public ConfServSolicitud getSolicitud() {
		return solicitud;
	}
	public void setSolicitud(ConfServSolicitud solicitud) {
		this.solicitud = solicitud;
	}
	public Integer getIntParaTipoDescripcion() {
		return intParaTipoDescripcion;
	}
	public void setIntParaTipoDescripcion(Integer intParaTipoDescripcion) {
		this.intParaTipoDescripcion = intParaTipoDescripcion;
	}
	public Integer getIntParaTipoPersonaOperacionCod() {
		return intParaTipoPersonaOperacionCod;
	}
	public void setIntParaTipoPersonaOperacionCod(
			Integer intParaTipoPersonaOperacionCod) {
		this.intParaTipoPersonaOperacionCod = intParaTipoPersonaOperacionCod;
	}
	public Integer getIntOpcionAdjunta() {
		return intOpcionAdjunta;
	}
	public void setIntOpcionAdjunta(Integer intOpcionAdjunta) {
		this.intOpcionAdjunta = intOpcionAdjunta;
	}
	public Integer getIntParaEstadoCod() {
		return intParaEstadoCod;
	}
	public void setIntParaEstadoCod(Integer intParaEstadoCod) {
		this.intParaEstadoCod = intParaEstadoCod;
	}
	public Timestamp getTsFechaRegistro() {
		return tsFechaRegistro;
	}
	public void setTsFechaRegistro(Timestamp tsFechaRegistro) {
		this.tsFechaRegistro = tsFechaRegistro;
	}
	public Integer getIntPersPersonaUsuarioPk() {
		return intPersPersonaUsuarioPk;
	}
	public void setIntPersPersonaUsuarioPk(Integer intPersPersonaUsuarioPk) {
		this.intPersPersonaUsuarioPk = intPersPersonaUsuarioPk;
	}
	public Timestamp getTsFechaEliminacion() {
		return tsFechaEliminacion;
	}
	public void setTsFechaEliminacion(Timestamp tsFechaEliminacion) {
		this.tsFechaEliminacion = tsFechaEliminacion;
	}
	public Integer getIntPersPersonaEliminaPk() {
		return intPersPersonaEliminaPk;
	}
	public void setIntPersPersonaEliminaPk(Integer intPersPersonaEliminaPk) {
		this.intPersPersonaEliminaPk = intPersPersonaEliminaPk;
	}
	@Override
	public String toString() {
		return "ConfServDetalle [id=" + id + ", solicitud=" + solicitud
				+ ", intParaTipoDescripcion=" + intParaTipoDescripcion
				+ ", intParaTipoPersonaOperacionCod="
				+ intParaTipoPersonaOperacionCod + ", intOpcionAdjunta="
				+ intOpcionAdjunta + ", intParaEstadoCod=" + intParaEstadoCod
				+ ", tsFechaRegistro=" + tsFechaRegistro
				+ ", intPersPersonaUsuarioPk=" + intPersPersonaUsuarioPk
				+ ", tsFechaEliminacion=" + tsFechaEliminacion
				+ ", intPersPersonaEliminaPk=" + intPersPersonaEliminaPk + "]";
	}
	

}

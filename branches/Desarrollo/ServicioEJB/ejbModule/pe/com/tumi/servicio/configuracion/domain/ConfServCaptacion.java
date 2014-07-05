package pe.com.tumi.servicio.configuracion.domain;

import java.sql.Timestamp;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class ConfServCaptacion extends TumiDomain{

	private ConfServCaptacionId id;
	private ConfServSolicitud solicitud;
	private Integer intParaTipoCaptacionCod;
	private Integer intSocioItem;
	private Integer intParaEstadoCod;
	private Timestamp tsFechaRegistro;
	private Integer intPersPersonaUsuarioPk;
	private Timestamp tsFechaEliminacion;
	private Integer intPersPersonaEliminaPk;
	
	public ConfServCaptacion(){
		id = new ConfServCaptacionId();
	}
	
	//para mostrar en pantalla
	private String strDescripcion;
	
	public ConfServCaptacionId getId() {
		return id;
	}
	public void setId(ConfServCaptacionId id) {
		this.id = id;
	}
	public ConfServSolicitud getSolicitud() {
		return solicitud;
	}
	public void setSolicitud(ConfServSolicitud solicitud) {
		this.solicitud = solicitud;
	}
	public Integer getIntParaTipoCaptacionCod() {
		return intParaTipoCaptacionCod;
	}
	public void setIntParaTipoCaptacionCod(Integer intParaTipoCaptacionCod) {
		this.intParaTipoCaptacionCod = intParaTipoCaptacionCod;
	}
	public Integer getIntSocioItem() {
		return intSocioItem;
	}
	public void setIntSocioItem(Integer intSocioItem) {
		this.intSocioItem = intSocioItem;
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
	public String getStrDescripcion() {
		return strDescripcion;
	}
	public void setStrDescripcion(String strDescripcion) {
		this.strDescripcion = strDescripcion;
	}
	@Override
	public String toString() {
		return "ConfServCaptacion [id=" + id + ", solicitud=" + solicitud
				+ ", intParaTipoCaptacionCod=" + intParaTipoCaptacionCod
				+ ", intSocioItem=" + intSocioItem + ", intParaEstadoCod="
				+ intParaEstadoCod + ", tsFechaRegistro=" + tsFechaRegistro
				+ ", intPersPersonaUsuarioPk=" + intPersPersonaUsuarioPk
				+ ", tsFechaEliminacion=" + tsFechaEliminacion
				+ ", intPersPersonaEliminaPk=" + intPersPersonaEliminaPk
				+ ", strDescripcion=" + strDescripcion + "]";
	}
	

}

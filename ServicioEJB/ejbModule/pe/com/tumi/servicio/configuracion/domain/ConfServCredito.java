package pe.com.tumi.servicio.configuracion.domain;

import java.sql.Timestamp;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class ConfServCredito extends TumiDomain{

	private ConfServCreditoId id;
	private ConfServSolicitud solicitud;
	private Integer intParaTipocreditoCod;
	private Integer intSocioItemCredito;
	private Integer intParaEstadoCod;
	private Timestamp tsFechaRegistro;
	private Integer intPersPersonaUsuarioPk;
	private Timestamp tsFechaEliminacion;
	private Integer intPersPersonaEliminaPk;
	//para mostrar en pantalla
	private String strDescripcion;
	private Integer intParaTipoCreditoEmpresa;
	
	public ConfServCredito(){
		id = new ConfServCreditoId();
	}
	public ConfServCreditoId getId() {
		return id;
	}
	public void setId(ConfServCreditoId id) {
		this.id = id;
	}
	public ConfServSolicitud getSolicitud() {
		return solicitud;
	}
	public void setSolicitud(ConfServSolicitud solicitud) {
		this.solicitud = solicitud;
	}
	public Integer getIntParaTipocreditoCod() {
		return intParaTipocreditoCod;
	}
	public void setIntParaTipocreditoCod(Integer intParaTipocreditoCod) {
		this.intParaTipocreditoCod = intParaTipocreditoCod;
	}
	public Integer getIntSocioItemCredito() {
		return intSocioItemCredito;
	}
	public void setIntSocioItemCredito(Integer intSocioItemCredito) {
		this.intSocioItemCredito = intSocioItemCredito;
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
	public Integer getIntParaTipoCreditoEmpresa() {
		return intParaTipoCreditoEmpresa;
	}
	public void setIntParaTipoCreditoEmpresa(Integer intParaTipoCreditoEmpresa) {
		this.intParaTipoCreditoEmpresa = intParaTipoCreditoEmpresa;
	}
	@Override
	public String toString() {
		return "ConfServCredito [id=" + id + ", solicitud=" + solicitud
				+ ", intParaTipocreditoCod=" + intParaTipocreditoCod
				+ ", intSocioItemCredito=" + intSocioItemCredito
				+ ", intParaEstadoCod=" + intParaEstadoCod
				+ ", tsFechaRegistro=" + tsFechaRegistro
				+ ", intPersPersonaUsuarioPk=" + intPersPersonaUsuarioPk
				+ ", tsFechaEliminacion=" + tsFechaEliminacion
				+ ", intPersPersonaEliminaPk=" + intPersPersonaEliminaPk + "]";
	}

}

package pe.com.tumi.servicio.configuracion.domain;

import java.sql.Timestamp;

import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.seguridad.login.domain.composite.UsuarioComp;

public class ConfServUsuario extends TumiDomain{

	private ConfServUsuarioId id;
	private ConfServSolicitud solicitud;
	private Integer intPersUsuarioPk;
	private Integer intParaEstadoCod;
	private Timestamp tsFechaRegistro;
	private Integer intPersPersonaUsuarioPk;
	private Timestamp tsFechaEliminacion;
	private Integer intPersPersonaEliminaPk;
	private UsuarioComp usuarioComp;
	
	public ConfServUsuario(){
		id = new ConfServUsuarioId();
		usuarioComp = new UsuarioComp();
	}
	public ConfServUsuarioId getId() {
		return id;
	}
	public void setId(ConfServUsuarioId id) {
		this.id = id;
	}
	public ConfServSolicitud getSolicitud() {
		return solicitud;
	}
	public void setSolicitud(ConfServSolicitud solicitud) {
		this.solicitud = solicitud;
	}
	public Integer getIntPersUsuarioPk() {
		return intPersUsuarioPk;
	}
	public void setIntPersUsuarioPk(Integer intPersUsuarioPk) {
		this.intPersUsuarioPk = intPersUsuarioPk;
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
	public UsuarioComp getUsuarioComp() {
		return usuarioComp;
	}
	public void setUsuarioComp(UsuarioComp usuarioComp) {
		this.usuarioComp = usuarioComp;
	}
	@Override
	public String toString() {
		return "ConfServUsuario [id=" + id + ", solicitud=" + solicitud
				+ ", intPersUsuarioPk=" + intPersUsuarioPk
				+ ", intParaEstadoCod=" + intParaEstadoCod
				+ ", tsFechaRegistro=" + tsFechaRegistro
				+ ", intPersPersonaUsuarioPk=" + intPersPersonaUsuarioPk
				+ ", tsFechaEliminacion=" + tsFechaEliminacion
				+ ", intPersPersonaEliminaPk=" + intPersPersonaEliminaPk
				+ ", usuarioComp=" + usuarioComp + "]";
	}

}

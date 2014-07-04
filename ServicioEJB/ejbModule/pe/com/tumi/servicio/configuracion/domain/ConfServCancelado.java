package pe.com.tumi.servicio.configuracion.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class ConfServCancelado extends TumiDomain{

	private ConfServCanceladoId id;
	private ConfServSolicitud solicitud;
	private Integer intParaModalidadPagoCod;
	private BigDecimal bdPorcentajeCancelado;
	private Integer intParaEstadoCod;
	private Timestamp tsFechaRegistro;
	private Integer intPersPersonaUsuarioPk;
	private Timestamp tsFechaEliminacion;
	private Integer intPersPersonaEliminaPk;
	
	public ConfServCancelado(){
		id = new ConfServCanceladoId();
	}
	
	public ConfServCanceladoId getId() {
		return id;
	}
	public void setId(ConfServCanceladoId id) {
		this.id = id;
	}
	public ConfServSolicitud getSolicitud() {
		return solicitud;
	}
	public void setSolicitud(ConfServSolicitud solicitud) {
		this.solicitud = solicitud;
	}
	public Integer getIntParaModalidadPagoCod() {
		return intParaModalidadPagoCod;
	}
	public void setIntParaModalidadPagoCod(Integer intParaModalidadPagoCod) {
		this.intParaModalidadPagoCod = intParaModalidadPagoCod;
	}
	public BigDecimal getBdPorcentajeCancelado() {
		return bdPorcentajeCancelado;
	}
	public void setBdPorcentajeCancelado(BigDecimal bdPorcentajeCancelado) {
		this.bdPorcentajeCancelado = bdPorcentajeCancelado;
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
		return "ConfServCancelado [id=" + id + ", solicitud=" + solicitud
				+ ", intParaModalidadPagoCod=" + intParaModalidadPagoCod
				+ ", bdPorcentajeCancelado=" + bdPorcentajeCancelado
				+ ", intParaEstadoCod=" + intParaEstadoCod
				+ ", tsFechaRegistro=" + tsFechaRegistro
				+ ", intPersPersonaUsuarioPk=" + intPersPersonaUsuarioPk
				+ ", tsFechaEliminacion=" + tsFechaEliminacion
				+ ", intPersPersonaEliminaPk=" + intPersPersonaEliminaPk + "]";
	}

}

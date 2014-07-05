package pe.com.tumi.seguridad.permiso.domain;

import java.sql.Timestamp;
import java.util.Date;

import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.seguridad.login.domain.UsuarioPerfil;

public class PermisoUsuario extends TumiDomain {

	private PermisoUsuarioId id;
	private Transaccion transaccion;
	private UsuarioPerfil usuarioPerfil;
	private Timestamp tsFechaRegistro;
	private Date dtDesde;
	private Date dtHasta;
	private Integer intIdEstado;
	private Timestamp tsFechaEliminacion;
	
	public PermisoUsuarioId getId() {
		return id;
	}
	public void setId(PermisoUsuarioId id) {
		this.id = id;
	}
	public UsuarioPerfil getUsuarioPerfil() {
		return usuarioPerfil;
	}
	public void setUsuarioPerfil(UsuarioPerfil usuarioPerfil) {
		this.usuarioPerfil = usuarioPerfil;
	}
	public Date getDtDesde() {
		return dtDesde;
	}
	public void setDtDesde(Date dtDesde) {
		this.dtDesde = dtDesde;
	}
	public Date getDtHasta() {
		return dtHasta;
	}
	public void setDtHasta(Date dtHasta) {
		this.dtHasta = dtHasta;
	}
	public Integer getIntIdEstado() {
		return intIdEstado;
	}
	public void setIntIdEstado(Integer intIdEstado) {
		this.intIdEstado = intIdEstado;
	}
	public Transaccion getTransaccion() {
		return transaccion;
	}
	public void setTransaccion(Transaccion transaccion) {
		this.transaccion = transaccion;
	}
	public Timestamp getTsFechaRegistro() {
		return tsFechaRegistro;
	}
	public void setTsFechaRegistro(Timestamp tsFechaRegistro) {
		this.tsFechaRegistro = tsFechaRegistro;
	}
	public Timestamp getTsFechaEliminacion() {
		return tsFechaEliminacion;
	}
	public void setTsFechaEliminacion(Timestamp tsFechaEliminacion) {
		this.tsFechaEliminacion = tsFechaEliminacion;
	}
}

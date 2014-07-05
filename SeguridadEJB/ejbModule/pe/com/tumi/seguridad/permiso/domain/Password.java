package pe.com.tumi.seguridad.permiso.domain;

import java.sql.Timestamp;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class Password  extends TumiDomain {

	private PasswordId id;
	private String strDetalle;
	private String strContrasena;
	private Timestamp tsFechaRegistro;
	private Integer intParaEstadoCod;
	private Timestamp tsFechaEliminacion;
	private Transaccion transaccion;
	
	public PasswordId getId() {
		return id;
	}
	public void setId(PasswordId id) {
		this.id = id;
	}
	public String getStrDetalle() {
		return strDetalle;
	}
	public void setStrDetalle(String strDetalle) {
		this.strDetalle = strDetalle;
	}
	public String getStrContrasena() {
		return strContrasena;
	}
	public void setStrContrasena(String strContrasena) {
		this.strContrasena = strContrasena;
	}
	public Timestamp getTsFechaRegistro() {
		return tsFechaRegistro;
	}
	public void setTsFechaRegistro(Timestamp tsFechaRegistro) {
		this.tsFechaRegistro = tsFechaRegistro;
	}
	public Integer getIntParaEstadoCod() {
		return intParaEstadoCod;
	}
	public void setIntParaEstadoCod(Integer intParaEstadoCod) {
		this.intParaEstadoCod = intParaEstadoCod;
	}
	public Timestamp getTsFechaEliminacion() {
		return tsFechaEliminacion;
	}
	public void setTsFechaEliminacion(Timestamp tsFechaEliminacion) {
		this.tsFechaEliminacion = tsFechaEliminacion;
	}
	public Transaccion getTransaccion() {
		return transaccion;
	}
	public void setTransaccion(Transaccion transaccion) {
		this.transaccion = transaccion;
	}

}

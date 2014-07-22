package pe.com.tumi.seguridad.login.domain;

import java.sql.Timestamp;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class Session extends TumiDomain {
	private SessionId id;
	private Timestamp tsFechaRegistro;
	private Timestamp tsFechaTermino;
	private Integer intIdSucursal;
	private Integer intInAccesoRemoto;
	private Integer intIdEstado;
	private String intIdWebSession;
	private String intIdBDSession;
	
	public Session(){
		id = new SessionId();
	}
	
	public SessionId getId() {
		return id;
	}
	public void setId(SessionId id) {
		this.id = id;
	}
	public Timestamp getTsFechaRegistro() {
		return tsFechaRegistro;
	}
	public void setTsFechaRegistro(Timestamp tsFechaRegistro) {
		this.tsFechaRegistro = tsFechaRegistro;
	}
	public Timestamp getTsFechaTermino() {
		return tsFechaTermino;
	}
	public void setTsFechaTermino(Timestamp tsFechaTermino) {
		this.tsFechaTermino = tsFechaTermino;
	}
	public Integer getIntIdSucursal() {
		return intIdSucursal;
	}
	public void setIntIdSucursal(Integer intIdSucursal) {
		this.intIdSucursal = intIdSucursal;
	}
	public Integer getIntInAccesoRemoto() {
		return intInAccesoRemoto;
	}
	public void setIntInAccesoRemoto(Integer intInAccesoRemoto) {
		this.intInAccesoRemoto = intInAccesoRemoto;
	}
	public Integer getIntIdEstado() {
		return intIdEstado;
	}
	public void setIntIdEstado(Integer intIdEstado) {
		this.intIdEstado = intIdEstado;
	}
	public String getIntIdWebSession() {
		return intIdWebSession;
	}
	public void setIntIdWebSession(String intIdWebSession) {
		this.intIdWebSession = intIdWebSession;
	}
	public String getIntIdBDSession() {
		return intIdBDSession;
	}
	public void setIntIdBDSession(String intIdBDSession) {
		this.intIdBDSession = intIdBDSession;
	}
}
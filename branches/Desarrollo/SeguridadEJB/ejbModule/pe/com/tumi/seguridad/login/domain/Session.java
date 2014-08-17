/************************************************************************
/* Nombre de componente: Session
 * Descripción: Bean utilizado para grabar los atributos de la tabla SEG_V_SESSION
 * Cod. Req.: REQ14-003   
 * Autor : Luis Polanco  Fecha:12/08/2014 16:20:00
 * Versión : v1.0 - Creacion de componente 
 * Fecha creación : 12/08/2014
/* ********************************************************************* */
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
	private String strMacAddress;
	private Integer intIndCabina;
	private Timestamp tsFechaActividad;
	
	public Timestamp getTsFechaActividad() {
		return tsFechaActividad;
	}

	public void setTsFechaActividad(Timestamp tsFechaActividad) {
		this.tsFechaActividad = tsFechaActividad;
	}

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

	public String getStrMacAddress() {
		return strMacAddress;
	}

	public void setStrMacAddress(String strMacAddress) {
		this.strMacAddress = strMacAddress;
	}

	public Integer getIntIndCabina() {
		return intIndCabina;
	}

	public void setIntIndCabina(Integer intIndCabina) {
		this.intIndCabina = intIndCabina;
	}
}
package pe.com.tumi.contabilidad.cierre.domain;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.persona.core.domain.Persona;

public class CuentaCierre extends TumiDomain{

	private CuentaCierreId	id;
	private String 	strDescripcion;
	private Integer	intPersEmpresaCuenta;
	private Integer	intContPeriodoCuenta;
	private String 	strContNumeroCuenta;
	private Timestamp	tsFechaRegistro;
	private	Integer	intPersEmpresaUsuario;
	private Integer	intPersPersonaUsuario;
	private Integer intParaEstado;
	private Timestamp	tsFechaEliminacion;
	private List<CuentaCierreDetalle> listaCuentaCierreDetalle;
	private Persona persona;
	
	public CuentaCierre(){
		super();
		id = new CuentaCierreId();
		listaCuentaCierreDetalle = new ArrayList<CuentaCierreDetalle>();
	}
	
	public CuentaCierreId getId() {
		return id;
	}
	public void setId(CuentaCierreId id) {
		this.id = id;
	}
	public String getStrDescripcion() {
		return strDescripcion;
	}
	public void setStrDescripcion(String strDescripcion) {
		this.strDescripcion = strDescripcion;
	}
	public Integer getIntPersEmpresaCuenta() {
		return intPersEmpresaCuenta;
	}
	public void setIntPersEmpresaCuenta(Integer intPersEmpresaCuenta) {
		this.intPersEmpresaCuenta = intPersEmpresaCuenta;
	}
	public Integer getIntContPeriodoCuenta() {
		return intContPeriodoCuenta;
	}
	public void setIntContPeriodoCuenta(Integer intContPeriodoCuenta) {
		this.intContPeriodoCuenta = intContPeriodoCuenta;
	}
	public String getStrContNumeroCuenta() {
		return strContNumeroCuenta;
	}
	public void setStrContNumeroCuenta(String strContNumeroCuenta) {
		this.strContNumeroCuenta = strContNumeroCuenta;
	}
	public Timestamp getTsFechaRegistro() {
		return tsFechaRegistro;
	}
	public void setTsFechaRegistro(Timestamp tsFechaRegistro) {
		this.tsFechaRegistro = tsFechaRegistro;
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
	public Integer getIntParaEstado() {
		return intParaEstado;
	}
	public void setIntParaEstado(Integer intParaEstado) {
		this.intParaEstado = intParaEstado;
	}
	public Timestamp getTsFechaEliminacion() {
		return tsFechaEliminacion;
	}
	public void setTsFechaEliminacion(Timestamp tsFechaEliminacion) {
		this.tsFechaEliminacion = tsFechaEliminacion;
	}
	public List<CuentaCierreDetalle> getListaCuentaCierreDetalle() {
		return listaCuentaCierreDetalle;
	}
	public void setListaCuentaCierreDetalle(
			List<CuentaCierreDetalle> listaCuentaCierreDetalle) {
		this.listaCuentaCierreDetalle = listaCuentaCierreDetalle;
	}
	public Persona getPersona() {
		return persona;
	}
	public void setPersona(Persona persona) {
		this.persona = persona;
	}
	@Override
	public String toString() {
		return "CuentaCierre [id=" + id + ", strDescripcion=" + strDescripcion
				+ ", intPersEmpresaCuenta=" + intPersEmpresaCuenta
				+ ", intContPeriodoCuenta=" + intContPeriodoCuenta
				+ ", strContNumeroCuenta=" + strContNumeroCuenta
				+ ", tsFechaRegistro=" + tsFechaRegistro
				+ ", intPersEmpresaUsuario=" + intPersEmpresaUsuario
				+ ", intPersPersonaUsuario=" + intPersPersonaUsuario
				+ ", intParaEstado=" + intParaEstado + ", tsFechaEliminacion="
				+ tsFechaEliminacion + "]";
	}
}

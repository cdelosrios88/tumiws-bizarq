package pe.com.tumi.credito.socio.core.domain;


import java.sql.Timestamp;
import java.util.Date;

import pe.com.tumi.empresa.domain.Subsucursal;
import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.persona.core.domain.Natural;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.persona.empresa.domain.Juridica;

public class SocioEstructura extends TumiDomain {

	private SocioEstructuraPK id;
	private Integer intEmpresaSucUsuario;
	private Integer intIdSucursalUsuario;
	private Integer intIdSubSucursalUsuario;
	private String 	strSucursalUsuario;
	private Integer intEmpresaSucAdministra;
	private Integer intIdSucursalAdministra;
	private Integer	intIdSubsucurAdministra;
	private String 	strSucursalAdministra;
	private Integer intTipoSocio;
	private Integer intModalidad;
	private Integer intNivel;
	private Integer intCodigo;
	private String 	strEntidad;
	private String 	strCodigoPlanilla;
	private Integer intTipoEstructura;
	private Date 	dtFechaRegistro;
	private String 	strFechaRegistro;
	private Integer intEmpresaUsuario;
	private Integer intPersonaUsuario;
	private Integer intEstadoCod;
	private Timestamp tsFechaEliminacion;
	private Integer intEmpresaEliminar;
	private Integer intPersonaEliminar;
	
	private Sucursal sucursal;
	private Subsucursal	subsucursal;
	
	//Auxiliar
	private String strMensaje;
	//AUTOR Y FECHA CREACION: JCHAVEZ / 03-10-2013 
	private String strRazonSocial;
	private Integer intCondicionLaboral;
	//CGD-23.012014
	private Integer intUltimoPeriodo;
	
	//Getters & Setters
	public SocioEstructuraPK getId() {
		return id;
	}
	public void setId(SocioEstructuraPK id) {
		this.id = id;
	}
	public Integer getIntEmpresaSucUsuario() {
		return intEmpresaSucUsuario;
	}
	public void setIntEmpresaSucUsuario(Integer intEmpresaSucUsuario) {
		this.intEmpresaSucUsuario = intEmpresaSucUsuario;
	}
	public Integer getIntIdSucursalUsuario() {
		return intIdSucursalUsuario;
	}
	public void setIntIdSucursalUsuario(Integer intIdSucursalUsuario) {
		this.intIdSucursalUsuario = intIdSucursalUsuario;
	}
	public Integer getIntEmpresaSucAdministra() {
		return intEmpresaSucAdministra;
	}
	public void setIntEmpresaSucAdministra(Integer intEmpresaSucAdministra) {
		this.intEmpresaSucAdministra = intEmpresaSucAdministra;
	}
	public Integer getIntIdSucursalAdministra() {
		return intIdSucursalAdministra;
	}
	public void setIntIdSucursalAdministra(Integer intIdSucursalAdministra) {
		this.intIdSucursalAdministra = intIdSucursalAdministra;
	}
	public Integer getIntTipoSocio() {
		return intTipoSocio;
	}
	public void setIntTipoSocio(Integer intTipoSocio) {
		this.intTipoSocio = intTipoSocio;
	}
	public Integer getIntModalidad() {
		return intModalidad;
	}
	public void setIntModalidad(Integer intModalidad) {
		this.intModalidad = intModalidad;
	}
	public Integer getIntCodigo() {
		return intCodigo;
	}
	public void setIntCodigo(Integer intCodigo) {
		this.intCodigo = intCodigo;
	}
	public String getStrCodigoPlanilla() {
		return strCodigoPlanilla;
	}
	public void setStrCodigoPlanilla(String strCodigoPlanilla) {
		this.strCodigoPlanilla = strCodigoPlanilla;
	}
	public Integer getIntTipoEstructura() {
		return intTipoEstructura;
	}
	public void setIntTipoEstructura(Integer intTipoEstructura) {
		this.intTipoEstructura = intTipoEstructura;
	}
	public Date getDtFechaRegistro() {
		return dtFechaRegistro;
	}
	public void setDtFechaRegistro(Date dtFechaRegistro) {
		this.dtFechaRegistro = dtFechaRegistro;
	}
	public Integer getIntEmpresaUsuario() {
		return intEmpresaUsuario;
	}
	public void setIntEmpresaUsuario(Integer intEmpresaUsuario) {
		this.intEmpresaUsuario = intEmpresaUsuario;
	}
	public Integer getIntPersonaUsuario() {
		return intPersonaUsuario;
	}
	public void setIntPersonaUsuario(Integer intPersonaUsuario) {
		this.intPersonaUsuario = intPersonaUsuario;
	}
	public Integer getIntEstadoCod() {
		return intEstadoCod;
	}
	public void setIntEstadoCod(Integer intEstadoCod) {
		this.intEstadoCod = intEstadoCod;
	}
	public Timestamp getTsFechaEliminacion() {
		return tsFechaEliminacion;
	}
	public void setTsFechaEliminacion(Timestamp tsFechaEliminacion) {
		this.tsFechaEliminacion = tsFechaEliminacion;
	}
	public Integer getIntEmpresaEliminar() {
		return intEmpresaEliminar;
	}
	public void setIntEmpresaEliminar(Integer intEmpresaEliminar) {
		this.intEmpresaEliminar = intEmpresaEliminar;
	}
	public Integer getIntPersonaEliminar() {
		return intPersonaEliminar;
	}
	public void setIntPersonaEliminar(Integer intPersonaEliminar) {
		this.intPersonaEliminar = intPersonaEliminar;
	}
	public String getStrSucursalUsuario() {
		return strSucursalUsuario;
	}
	public void setStrSucursalUsuario(String strSucursalUsuario) {
		this.strSucursalUsuario = strSucursalUsuario;
	}
	public String getStrSucursalAdministra() {
		return strSucursalAdministra;
	}
	public void setStrSucursalAdministra(String strSucursalAdministra) {
		this.strSucursalAdministra = strSucursalAdministra;
	}
	public String getStrFechaRegistro() {
		return strFechaRegistro;
	}
	public void setStrFechaRegistro(String strFechaRegistro) {
		this.strFechaRegistro = strFechaRegistro;
	}
	public Integer getIntNivel() {
		return intNivel;
	}
	public void setIntNivel(Integer intNivel) {
		this.intNivel = intNivel;
	}
	public String getStrEntidad() {
		return strEntidad;
	}
	public void setStrEntidad(String strEntidad) {
		this.strEntidad = strEntidad;
	}
	public Integer getIntIdSubsucurAdministra() {
		return intIdSubsucurAdministra;
	}
	public void setIntIdSubsucurAdministra(Integer intIdSubsucurAdministra) {
		this.intIdSubsucurAdministra = intIdSubsucurAdministra;
	}
	public Integer getIntIdSubSucursalUsuario() {
		return intIdSubSucursalUsuario;
	}
	public void setIntIdSubSucursalUsuario(Integer intIdSubSucursalUsuario) {
		this.intIdSubSucursalUsuario = intIdSubSucursalUsuario;
	}
	public Sucursal getSucursal() {
		return sucursal;
	}
	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}
	public Subsucursal getSubsucursal() {
		return subsucursal;
	}
	public void setSubsucursal(Subsucursal subsucursal) {
		this.subsucursal = subsucursal;
	}
	public String getStrMensaje() {
		return strMensaje;
	}
	public void setStrMensaje(String strMensaje) {
		this.strMensaje = strMensaje;
	}
	public String getStrRazonSocial() {
		return strRazonSocial;
	}
	public void setStrRazonSocial(String strRazonSocial) {
		this.strRazonSocial = strRazonSocial;
	}
	public Integer getIntCondicionLaboral() {
		return intCondicionLaboral;
	}
	public void setIntCondicionLaboral(Integer intCondicionLaboral) {
		this.intCondicionLaboral = intCondicionLaboral;
	}
	public Integer getIntUltimoPeriodo() {
		return intUltimoPeriodo;
	}
	public void setIntUltimoPeriodo(Integer intUltimoPeriodo) {
		this.intUltimoPeriodo = intUltimoPeriodo;
	}
	@Override
	public String toString() {
		return "SocioEstructura [id=" + id + ", intTipoSocio=" + intTipoSocio
				+ ", intModalidad=" + intModalidad + ", intNivel=" + intNivel
				+ ", intCodigo=" + intCodigo + ", intTipoEstructura="
				+ intTipoEstructura + "]";
	}
	
	
}

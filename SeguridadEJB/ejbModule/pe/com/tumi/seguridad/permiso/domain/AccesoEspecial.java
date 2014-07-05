package pe.com.tumi.seguridad.permiso.domain;

import java.sql.Timestamp;

import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.persona.core.domain.Natural;
import pe.com.tumi.persona.empresa.domain.Juridica;

public class AccesoEspecial extends TumiDomain{

	private Integer 	intItemAcceso;
	private Timestamp 	tsFechaInicio;
	private Timestamp 	tsFechaFin;
	private Integer 	intFeriados;
	private Integer 	intAccesoRemoto;
	private String 		strObservacion;
	private Integer 	intParaTipoMotivo;
	private Integer 	intParaTipoAcceso;
	private Timestamp	tsFechaRegistro;
	private Integer		intPersEmpresa;
	private Integer 	intPersPersonaRegistra;
	private Integer 	intPersPersonaOpera;
	private Integer 	intPersPersonaAutoriza;
	private Integer 	intIdSucursal;
	private Integer 	intIdEstado;
	private Timestamp	tsFechaEliminacion;
	private Natural		naturalOpera;
	private Natural		naturalAutoriza;
	private Juridica 	juridica;
	
	public AccesoEspecial(){
		super();
		naturalOpera = new Natural();
		naturalAutoriza = new Natural();
		juridica = new Juridica();
	}

	public Integer getIntItemAcceso() {
		return intItemAcceso;
	}
	public void setIntItemAcceso(Integer intItemAcceso) {
		this.intItemAcceso = intItemAcceso;
	}
	public Timestamp getTsFechaInicio() {
		return tsFechaInicio;
	}
	public void setTsFechaInicio(Timestamp tsFechaInicio) {
		this.tsFechaInicio = tsFechaInicio;
	}
	public Timestamp getTsFechaFin() {
		return tsFechaFin;
	}
	public void setTsFechaFin(Timestamp tsFechaFin) {
		this.tsFechaFin = tsFechaFin;
	}
	public Integer getIntFeriados() {
		return intFeriados;
	}
	public void setIntFeriados(Integer intFeriados) {
		this.intFeriados = intFeriados;
	}
	public Integer getIntAccesoRemoto() {
		return intAccesoRemoto;
	}
	public void setIntAccesoRemoto(Integer intAccesoRemoto) {
		this.intAccesoRemoto = intAccesoRemoto;
	}
	public String getStrObservacion() {
		return strObservacion;
	}
	public void setStrObservacion(String strObservacion) {
		this.strObservacion = strObservacion;
	}
	public Integer getIntParaTipoMotivo() {
		return intParaTipoMotivo;
	}
	public void setIntParaTipoMotivo(Integer intParaTipoMotivo) {
		this.intParaTipoMotivo = intParaTipoMotivo;
	}
	public Integer getIntParaTipoAcceso() {
		return intParaTipoAcceso;
	}
	public void setIntParaTipoAcceso(Integer intParaTipoAcceso) {
		this.intParaTipoAcceso = intParaTipoAcceso;
	}
	public Timestamp getTsFechaRegistro() {
		return tsFechaRegistro;
	}
	public void setTsFechaRegistro(Timestamp tsFechaRegistro) {
		this.tsFechaRegistro = tsFechaRegistro;
	}
	public Integer getIntPersEmpresa() {
		return intPersEmpresa;
	}
	public void setIntPersEmpresa(Integer intPersEmpresa) {
		this.intPersEmpresa = intPersEmpresa;
	}
	public Integer getIntPersPersonaRegistra() {
		return intPersPersonaRegistra;
	}
	public void setIntPersPersonaRegistra(Integer intPersPersonaRegistra) {
		this.intPersPersonaRegistra = intPersPersonaRegistra;
	}
	public Integer getIntPersPersonaOpera() {
		return intPersPersonaOpera;
	}
	public void setIntPersPersonaOpera(Integer intPersPersonaOpera) {
		this.intPersPersonaOpera = intPersPersonaOpera;
	}
	public Integer getIntPersPersonaAutoriza() {
		return intPersPersonaAutoriza;
	}
	public void setIntPersPersonaAutoriza(Integer intPersPersonaAutoriza) {
		this.intPersPersonaAutoriza = intPersPersonaAutoriza;
	}
	public Integer getIntIdSucursal() {
		return intIdSucursal;
	}
	public void setIntIdSucursal(Integer intIdSucursal) {
		this.intIdSucursal = intIdSucursal;
	}
	public Integer getIntIdEstado() {
		return intIdEstado;
	}
	public void setIntIdEstado(Integer intIdEstado) {
		this.intIdEstado = intIdEstado;
	}
	public Timestamp getTsFechaEliminacion() {
		return tsFechaEliminacion;
	}
	public void setTsFechaEliminacion(Timestamp tsFechaEliminacion) {
		this.tsFechaEliminacion = tsFechaEliminacion;
	}
	public Natural getNaturalOpera() {
		return naturalOpera;
	}
	public void setNaturalOpera(Natural naturalOpera) {
		this.naturalOpera = naturalOpera;
	}
	public Natural getNaturalAutoriza() {
		return naturalAutoriza;
	}
	public void setNaturalAutoriza(Natural naturalAutoriza) {
		this.naturalAutoriza = naturalAutoriza;
	}
	public Juridica getJuridica() {
		return juridica;
	}
	public void setJuridica(Juridica juridica) {
		this.juridica = juridica;
	}

	@Override
	public String toString() {
		return "AccesoEspecial [intItemAcceso=" + intItemAcceso
				+ ", tsFechaInicio=" + tsFechaInicio + ", tsFechaFin="
				+ tsFechaFin + ", intFeriados=" + intFeriados
				+ ", intAccesoRemoto=" + intAccesoRemoto + ", strObservacion="
				+ strObservacion + ", intParaTipoMotivo=" + intParaTipoMotivo
				+ ", intParaTipoAcceso=" + intParaTipoAcceso
				+ ", tsFechaRegistro=" + tsFechaRegistro + ", intPersEmpresa="
				+ intPersEmpresa + ", intPersPersonaRegistra="
				+ intPersPersonaRegistra + ", intPersPersonaOpera="
				+ intPersPersonaOpera + ", intPersPersonaAutoriza="
				+ intPersPersonaAutoriza + ", intIdSucursal=" + intIdSucursal
				+ ", intIdEstado=" + intIdEstado + ", tsFechaEliminacion="
				+ tsFechaEliminacion + ", naturalOpera=" + naturalOpera
				+ ", naturalAutoriza=" + naturalAutoriza + "]";
	}
}
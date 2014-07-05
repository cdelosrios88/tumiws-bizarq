package pe.com.tumi.persona.vinculo.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.persona.empresa.domain.Empresa;

import java.sql.Timestamp;
import java.util.Date;

public class Vinculo extends TumiDomain{

	private Integer intItemVinculo;
	private Integer intIdEmpresa;
	private Integer intIdPersona;
	private Integer intTipoVinculoCod;
	private Integer intEmpresaVinculo;
	private Integer intPersonaVinculo;
	private Integer intCargoVinculoCod;
	private Date dtInicioCargo;
	private Integer intEstadoCod;
	private Timestamp tsFechaEliminacion;

	private Persona persona;
	private Empresa empresa;
	
	public Integer getIntCargoVinculoCod() {
		return intCargoVinculoCod;
	}
	public void setIntCargoVinculoCod(Integer intCargoVinculoCod) {
		this.intCargoVinculoCod = intCargoVinculoCod;
	}
	public Integer getIntEstadoCod() {
		return intEstadoCod;
	}
	public void setIntEstadoCod(Integer intEstadoCod) {
		this.intEstadoCod = intEstadoCod;
	}
	public Date getDtInicioCargo() {
		return dtInicioCargo;
	}
	public void setDtInicioCargo(Date dtInicioCargo) {
		this.dtInicioCargo = dtInicioCargo;
	}
	public Persona getPersona() {
		return persona;
	}
	public void setPersona(Persona persona) {
		this.persona = persona;
	}
	public Empresa getEmpresa() {
		return empresa;
	}
	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
	public Integer getIntItemVinculo() {
		return intItemVinculo;
	}
	public void setIntItemVinculo(Integer intItemVinculo) {
		this.intItemVinculo = intItemVinculo;
	}
	public Integer getIntIdEmpresa() {
		return intIdEmpresa;
	}
	public void setIntIdEmpresa(Integer intIdEmpresa) {
		this.intIdEmpresa = intIdEmpresa;
	}
	public Integer getIntIdPersona() {
		return intIdPersona;
	}
	public void setIntIdPersona(Integer intIdPersona) {
		this.intIdPersona = intIdPersona;
	}
	public Integer getIntTipoVinculoCod() {
		return intTipoVinculoCod;
	}
	public void setIntTipoVinculoCod(Integer intTipoVinculoCod) {
		this.intTipoVinculoCod = intTipoVinculoCod;
	}
	public Integer getIntEmpresaVinculo() {
		return intEmpresaVinculo;
	}
	public void setIntEmpresaVinculo(Integer intEmpresaVinculo) {
		this.intEmpresaVinculo = intEmpresaVinculo;
	}
	public Integer getIntPersonaVinculo() {
		return intPersonaVinculo;
	}
	public void setIntPersonaVinculo(Integer intPersonaVinculo) {
		this.intPersonaVinculo = intPersonaVinculo;
	}
	public Timestamp getTsFechaEliminacion() {
		return tsFechaEliminacion;
	}
	public void setTsFechaEliminacion(Timestamp tsFechaEliminacion) {
		this.tsFechaEliminacion = tsFechaEliminacion;
	}
	
	@Override
	public String toString() {
		return "Vinculo [intItemVinculo=" + intItemVinculo + ", intIdEmpresa="
				+ intIdEmpresa + ", intIdPersona=" + intIdPersona
				+ ", intTipoVinculoCod=" + intTipoVinculoCod
				+ ", intEmpresaVinculo=" + intEmpresaVinculo
				+ ", intPersonaVinculo=" + intPersonaVinculo
				+ ", intCargoVinculoCod=" + intCargoVinculoCod
				+ ", dtInicioCargo=" + dtInicioCargo + ", intEstadoCod="
				+ intEstadoCod + ", tsFechaEliminacion=" + tsFechaEliminacion
				+ "]";
	}
}
package pe.com.tumi.credito.socio.convenio.domain;

import java.sql.Timestamp;

import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.persona.core.domain.Persona;

public class Firmante extends TumiDomain {
	private FirmanteId id;
	private Persona persona;
	private Integer intPersEmpresaPk;
	private Integer intPersPersonaEntidadPk;
	private Integer intPersPersonaFirmantePk;
	private Integer intParaEstadoCod;
	private Integer intPersonaRegistraPk;
	private Timestamp tsFechaRegistra;
	private Integer intPersonaEliminaPk;
	private Timestamp tsFechaEliminacion;
	
	public FirmanteId getId() {
		return id;
	}
	public void setId(FirmanteId id) {
		this.id = id;
	}
	public Persona getPersona() {
		return persona;
	}
	public void setPersona(Persona persona) {
		this.persona = persona;
	}
	public Integer getIntPersEmpresaPk() {
		return intPersEmpresaPk;
	}
	public void setIntPersEmpresaPk(Integer intPersEmpresaPk) {
		this.intPersEmpresaPk = intPersEmpresaPk;
	}
	public Integer getIntPersPersonaEntidadPk() {
		return intPersPersonaEntidadPk;
	}
	public void setIntPersPersonaEntidadPk(Integer intPersPersonaEntidadPk) {
		this.intPersPersonaEntidadPk = intPersPersonaEntidadPk;
	}
	public Integer getIntPersPersonaFirmantePk() {
		return intPersPersonaFirmantePk;
	}
	public void setIntPersPersonaFirmantePk(Integer intPersPersonaFirmantePk) {
		this.intPersPersonaFirmantePk = intPersPersonaFirmantePk;
	}
	public Integer getIntParaEstadoCod() {
		return intParaEstadoCod;
	}
	public void setIntParaEstadoCod(Integer intParaEstadoCod) {
		this.intParaEstadoCod = intParaEstadoCod;
	}
	public Integer getIntPersonaRegistraPk() {
		return intPersonaRegistraPk;
	}
	public void setIntPersonaRegistraPk(Integer intPersonaRegistraPk) {
		this.intPersonaRegistraPk = intPersonaRegistraPk;
	}
	public Timestamp getTsFechaRegistra() {
		return tsFechaRegistra;
	}
	public void setTsFechaRegistra(Timestamp tsFechaRegistra) {
		this.tsFechaRegistra = tsFechaRegistra;
	}
	public Integer getIntPersonaEliminaPk() {
		return intPersonaEliminaPk;
	}
	public void setIntPersonaEliminaPk(Integer intPersonaEliminaPk) {
		this.intPersonaEliminaPk = intPersonaEliminaPk;
	}
	public Timestamp getTsFechaEliminacion() {
		return tsFechaEliminacion;
	}
	public void setTsFechaEliminacion(Timestamp tsFechaEliminacion) {
		this.tsFechaEliminacion = tsFechaEliminacion;
	}
}

package pe.com.tumi.persona.core.domain;

import java.util.Date;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class PersonaRolPK extends TumiDomain{

	private Integer intIdEmpresa;
	private Integer intIdPersona;
	private Integer intParaRolPk;
	private Date 	dtFechaInicio;
	
	
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
	public Integer getIntParaRolPk() {
		return intParaRolPk;
	}
	public void setIntParaRolPk(Integer intParaRolPk) {
		this.intParaRolPk = intParaRolPk;
	}
	public Date getDtFechaInicio() {
		return dtFechaInicio;
	}
	public void setDtFechaInicio(Date dtFechaInicio) {
		this.dtFechaInicio = dtFechaInicio;
	}
	@Override
	public String toString() {
		return "PersonaRolPK [intIdEmpresa=" + intIdEmpresa + ", intIdPersona="
				+ intIdPersona + ", intParaRolPk=" + intParaRolPk
				+ ", dtFechaInicio=" + dtFechaInicio + "]";
	}
	
}
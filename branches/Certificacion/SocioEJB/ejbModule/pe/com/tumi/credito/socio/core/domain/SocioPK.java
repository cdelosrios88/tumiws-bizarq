package pe.com.tumi.credito.socio.core.domain;


import java.util.List;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class SocioPK extends TumiDomain {

	private Integer intIdPersona;
	private Integer intIdEmpresa;
	
	//Getters & Setters
	public Integer getIntIdPersona() {
		return intIdPersona;
	}
	public void setIntIdPersona(Integer intIdPersona) {
		this.intIdPersona = intIdPersona;
	}
	public Integer getIntIdEmpresa() {
		return intIdEmpresa;
	}
	public void setIntIdEmpresa(Integer intIdEmpresa) {
		this.intIdEmpresa = intIdEmpresa;
	}
	@Override
	public String toString() {
		return "SocioPK [intIdPersona=" + intIdPersona + ", intIdEmpresa="
				+ intIdEmpresa + "]";
	}
	
}

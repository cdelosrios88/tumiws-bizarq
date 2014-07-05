package pe.com.tumi.credito.socio.core.domain;


import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.persona.core.domain.Natural;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.persona.empresa.domain.Juridica;

public class SocioEstructuraPK extends TumiDomain {

	private Integer intIdPersona;
	private Integer intIdEmpresa;
	private Integer intItemSocioEstructura;
	
	//Getters & Setters
	public Integer getIntIdPersona() {
		return intIdPersona;
	}
	public void setIntIdPersona(Integer intIdPersona) {
		this.intIdPersona = intIdPersona;
	}
	public Integer getIntItemSocioEstructura() {
		return intItemSocioEstructura;
	}
	public void setIntItemSocioEstructura(Integer intItemSocioEstructura) {
		this.intItemSocioEstructura = intItemSocioEstructura;
	}
	public Integer getIntIdEmpresa() {
		return intIdEmpresa;
	}
	public void setIntIdEmpresa(Integer intIdEmpresa) {
		this.intIdEmpresa = intIdEmpresa;
	}
	@Override
	public String toString() {
		return "SocioEstructuraPK [intIdPersona=" + intIdPersona
				+ ", intIdEmpresa=" + intIdEmpresa
				+ ", intItemSocioEstructura=" + intItemSocioEstructura + "]";
	}
	
}

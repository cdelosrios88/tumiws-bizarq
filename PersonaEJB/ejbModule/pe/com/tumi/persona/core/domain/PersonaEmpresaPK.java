package pe.com.tumi.persona.core.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class PersonaEmpresaPK extends TumiDomain{

	private Integer intIdEmpresa;
	private Integer intIdPersona;
	
	public PersonaEmpresaPK(){
		
	}
	
	public PersonaEmpresaPK(Integer intIdEmpresa, Integer intIdPersona) {
		super();
		this.intIdEmpresa = intIdEmpresa;
		this.intIdPersona = intIdPersona;
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

	@Override
	public String toString() {
		return "PersonaEmpresaPK [intIdEmpresa=" + intIdEmpresa
				+ ", intIdPersona=" + intIdPersona + "]";
	}
	
}
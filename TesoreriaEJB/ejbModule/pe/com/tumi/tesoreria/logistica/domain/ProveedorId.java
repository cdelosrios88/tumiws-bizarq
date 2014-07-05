package pe.com.tumi.tesoreria.logistica.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class ProveedorId extends TumiDomain{

	private Integer	intPersEmpresa;
	private	Integer	intPersPersona;
	
	
	public Integer getIntPersEmpresa() {
		return intPersEmpresa;
	}
	public void setIntPersEmpresa(Integer intPersEmpresa) {
		this.intPersEmpresa = intPersEmpresa;
	}
	public Integer getIntPersPersona() {
		return intPersPersona;
	}
	public void setIntPersPersona(Integer intPersPersona) {
		this.intPersPersona = intPersPersona;
	}
	@Override
	public String toString() {
		return "ProveedorId [intPersEmpresa=" + intPersEmpresa
				+ ", intPersPersona=" + intPersPersona + "]";
	}
	
}

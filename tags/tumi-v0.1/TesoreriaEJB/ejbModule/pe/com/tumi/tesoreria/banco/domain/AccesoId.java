package pe.com.tumi.tesoreria.banco.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class AccesoId extends TumiDomain{

	private Integer	intPersEmpresaAcceso;
	private Integer	intItemAcceso;
	public Integer getIntPersEmpresaAcceso() {
		return intPersEmpresaAcceso;
	}
	public void setIntPersEmpresaAcceso(Integer intPersEmpresaAcceso) {
		this.intPersEmpresaAcceso = intPersEmpresaAcceso;
	}
	public Integer getIntItemAcceso() {
		return intItemAcceso;
	}
	public void setIntItemAcceso(Integer intItemAcceso) {
		this.intItemAcceso = intItemAcceso;
	}
	@Override
	public String toString() {
		return "AccesoId [intPersEmpresaAcceso=" + intPersEmpresaAcceso
				+ ", intItemAcceso=" + intItemAcceso + "]";
	}
	
}

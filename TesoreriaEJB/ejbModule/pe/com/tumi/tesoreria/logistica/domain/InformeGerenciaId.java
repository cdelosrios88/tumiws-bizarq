package pe.com.tumi.tesoreria.logistica.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class InformeGerenciaId extends TumiDomain{

	private Integer intPersEmpresa;
	private Integer intItemInformeGerencia;
	
	public Integer getIntPersEmpresa() {
		return intPersEmpresa;
	}
	public void setIntPersEmpresa(Integer intPersEmpresa) {
		this.intPersEmpresa = intPersEmpresa;
	}
	public Integer getIntItemInformeGerencia() {
		return intItemInformeGerencia;
	}
	public void setIntItemInformeGerencia(Integer intItemInformeGerencia) {
		this.intItemInformeGerencia = intItemInformeGerencia;
	}
	
	@Override
	public String toString() {
		return "InformeGerenciaId [intPersEmpresa=" + intPersEmpresa
				+ ", intItemInformeGerencia=" + intItemInformeGerencia + "]";
	}
	
}

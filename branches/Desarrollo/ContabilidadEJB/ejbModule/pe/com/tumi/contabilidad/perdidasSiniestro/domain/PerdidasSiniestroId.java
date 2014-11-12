package pe.com.tumi.contabilidad.perdidasSiniestro.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class PerdidasSiniestroId extends TumiDomain{
	private Integer intPersEmpresa;
	private Integer intContItemSiniestro;
	
	public Integer getIntPersEmpresa() {
		return intPersEmpresa;
	}
	public void setIntPersEmpresa(Integer intPersEmpresa) {
		this.intPersEmpresa = intPersEmpresa;
	}
	public Integer getIntContItemSiniestro() {
		return intContItemSiniestro;
	}
	public void setIntContItemSiniestro(Integer intContItemSiniestro) {
		this.intContItemSiniestro = intContItemSiniestro;
	}

}

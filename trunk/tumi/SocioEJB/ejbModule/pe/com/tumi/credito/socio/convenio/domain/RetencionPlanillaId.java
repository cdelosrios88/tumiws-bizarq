package pe.com.tumi.credito.socio.convenio.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class RetencionPlanillaId extends TumiDomain {
	private Integer intConvenio;
	private Integer intItemConvenio;
	private Integer intItemRetPlla;
	
	public Integer getIntConvenio() {
		return intConvenio;
	}
	public void setIntConvenio(Integer intConvenio) {
		this.intConvenio = intConvenio;
	}
	public Integer getIntItemConvenio() {
		return intItemConvenio;
	}
	public void setIntItemConvenio(Integer intItemConvenio) {
		this.intItemConvenio = intItemConvenio;
	}
	public Integer getIntItemRetPlla() {
		return intItemRetPlla;
	}
	public void setIntItemRetPlla(Integer intItemRetPlla) {
		this.intItemRetPlla = intItemRetPlla;
	}
}

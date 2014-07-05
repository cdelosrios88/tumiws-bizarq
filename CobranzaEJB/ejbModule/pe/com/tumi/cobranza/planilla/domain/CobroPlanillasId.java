package pe.com.tumi.cobranza.planilla.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class CobroPlanillasId extends TumiDomain{

	private Integer	intEmpresa;
	private Integer	intItemEfectuadoResumen;
	private Integer	intItemPagoPlanillas;
	
	
	public Integer getIntEmpresa() {
		return intEmpresa;
	}
	public void setIntEmpresa(Integer intEmpresa) {
		this.intEmpresa = intEmpresa;
	}
	public Integer getIntItemEfectuadoResumen() {
		return intItemEfectuadoResumen;
	}
	public void setIntItemEfectuadoResumen(Integer intItemEfectuadoResumen) {
		this.intItemEfectuadoResumen = intItemEfectuadoResumen;
	}
	public Integer getIntItemPagoPlanillas() {
		return intItemPagoPlanillas;
	}
	public void setIntItemPagoPlanillas(Integer intItemPagoPlanillas) {
		this.intItemPagoPlanillas = intItemPagoPlanillas;
	}
	
	@Override
	public String toString() {
		return "CobroPlanillasId [intEmpresa=" + intEmpresa
				+ ", intItemEfectuadoResumen=" + intItemEfectuadoResumen
				+ ", intItemPagoPlanillas=" + intItemPagoPlanillas + "]";
	}	
}
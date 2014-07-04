package pe.com.tumi.cobranza.planilla.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class EfectuadoResumenId extends TumiDomain{

 	private Integer intEmpresa;
 	private Integer intItemEfectuadoResumen;
 	
	
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
	
	@Override
	public String toString() {
		return "EfectuadoResumenId [intEmpresa=" + intEmpresa
				+ ", intItemEfectuadoResumen=" + intItemEfectuadoResumen + "]";
	}
}

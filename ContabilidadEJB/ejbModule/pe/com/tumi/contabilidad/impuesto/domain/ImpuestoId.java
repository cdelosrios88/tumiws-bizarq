package pe.com.tumi.contabilidad.impuesto.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class ImpuestoId extends TumiDomain{
	private Integer intPersEmpresa;
	private Integer intItemImpuesto;
	
	public Integer getIntPersEmpresa() {
		return intPersEmpresa;
	}
	public void setIntPersEmpresa(Integer intPersEmpresa) {
		this.intPersEmpresa = intPersEmpresa;
	}
	public Integer getIntItemImpuesto() {
		return intItemImpuesto;
	}
	public void setIntItemImpuesto(Integer intItemImpuesto) {
		this.intItemImpuesto = intItemImpuesto;
	}
}

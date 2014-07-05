package pe.com.tumi.tesoreria.egreso.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class EgresoId extends TumiDomain{

	private	Integer	intPersEmpresaEgreso;
	private	Integer	intItemEgresoGeneral;
	
	
	public Integer getIntPersEmpresaEgreso() {
		return intPersEmpresaEgreso;
	}
	public void setIntPersEmpresaEgreso(Integer intPersEmpresaEgreso) {
		this.intPersEmpresaEgreso = intPersEmpresaEgreso;
	}
	public Integer getIntItemEgresoGeneral() {
		return intItemEgresoGeneral;
	}
	public void setIntItemEgresoGeneral(Integer intItemEgresoGeneral) {
		this.intItemEgresoGeneral = intItemEgresoGeneral;
	}
	@Override
	public String toString() {
		return "EgresoId [intPersEmpresaEgreso=" + intPersEmpresaEgreso
				+ ", intItemEgresoGeneral=" + intItemEgresoGeneral + "]";
	}
	
	
}

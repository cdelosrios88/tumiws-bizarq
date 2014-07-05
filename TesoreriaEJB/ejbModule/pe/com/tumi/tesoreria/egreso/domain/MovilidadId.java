package pe.com.tumi.tesoreria.egreso.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class MovilidadId extends TumiDomain{

	private Integer	intPersEmpresaMovilidad;
	private	Integer	intItemMovilidad;
	
	
	public Integer getIntPersEmpresaMovilidad() {
		return intPersEmpresaMovilidad;
	}
	public void setIntPersEmpresaMovilidad(Integer intPersEmpresaMovilidad) {
		this.intPersEmpresaMovilidad = intPersEmpresaMovilidad;
	}
	public Integer getIntItemMovilidad() {
		return intItemMovilidad;
	}
	public void setIntItemMovilidad(Integer intItemMovilidad) {
		this.intItemMovilidad = intItemMovilidad;
	}
	@Override
	public String toString() {
		return "MovilidadId [intPersEmpresaMovilidad="
				+ intPersEmpresaMovilidad + ", intItemMovilidad="
				+ intItemMovilidad + "]";
	}

	
}

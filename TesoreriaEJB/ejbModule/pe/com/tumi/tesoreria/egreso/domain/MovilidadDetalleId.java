package pe.com.tumi.tesoreria.egreso.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class MovilidadDetalleId extends TumiDomain{

	private Integer	intPersEmpresaMovilidad;
	private	Integer	intItemMovilidad;
	private	Integer	intItemMovilidadDetalle;
	

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
	public Integer getIntItemMovilidadDetalle() {
		return intItemMovilidadDetalle;
	}
	public void setIntItemMovilidadDetalle(Integer intItemMovilidadDetalle) {
		this.intItemMovilidadDetalle = intItemMovilidadDetalle;
	}
	@Override
	public String toString() {
		return "MovilidadDetalleId [intPersEmpresaMovilidad="
				+ intPersEmpresaMovilidad + ", intItemMovilidad="
				+ intItemMovilidad + ", intItemMovilidadDetalle="
				+ intItemMovilidadDetalle + "]";
	}

	
}

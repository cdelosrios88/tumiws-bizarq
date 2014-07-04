package pe.com.tumi.cobranza.planilla.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class DevolucionId extends TumiDomain{
	private	Integer		intPersEmpresaDevolucion;
	private Integer		intItemDevolucion;
	
	
	public Integer getIntPersEmpresaDevolucion() {
		return intPersEmpresaDevolucion;
	}
	public void setIntPersEmpresaDevolucion(Integer intPersEmpresaDevolucion) {
		this.intPersEmpresaDevolucion = intPersEmpresaDevolucion;
	}
	public Integer getIntItemDevolucion() {
		return intItemDevolucion;
	}
	public void setIntItemDevolucion(Integer intItemDevolucion) {
		this.intItemDevolucion = intItemDevolucion;
	}

}

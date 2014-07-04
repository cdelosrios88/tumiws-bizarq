package pe.com.tumi.riesgo.archivo.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class NombreId extends TumiDomain{

	private Integer intPersEmpresaPk;
	private Integer intItemConfiguracion;
	private Integer intItemNombre;
		
	public Integer getIntPersEmpresaPk() {
		return intPersEmpresaPk;
	}
	public void setIntPersEmpresaPk(Integer intPersEmpresaPk) {
		this.intPersEmpresaPk = intPersEmpresaPk;
	}
	public Integer getIntItemConfiguracion() {
		return intItemConfiguracion;
	}
	public void setIntItemConfiguracion(Integer intItemConfiguracion) {
		this.intItemConfiguracion = intItemConfiguracion;
	}
	public Integer getIntItemNombre() {
		return intItemNombre;
	}
	public void setIntItemNombre(Integer intItemNombre) {
		this.intItemNombre = intItemNombre;
	}
	@Override
	public String toString() {
		return "NombreId [intPersEmpresaPk=" + intPersEmpresaPk
				+ ", intItemConfiguracion=" + intItemConfiguracion
				+ ", intItemNombre=" + intItemNombre + "]";
	}
	
}

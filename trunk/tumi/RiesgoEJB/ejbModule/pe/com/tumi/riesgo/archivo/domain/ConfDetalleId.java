package pe.com.tumi.riesgo.archivo.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class ConfDetalleId extends TumiDomain{

	private Integer intPersEmpresaPk;
	private Integer intItemConfiguracion;
	private Integer intItemConfiguracionDetalle;
	
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
	public Integer getIntItemConfiguracionDetalle() {
		return intItemConfiguracionDetalle;
	}
	public void setIntItemConfiguracionDetalle(Integer intItemConfiguracionDetalle) {
		this.intItemConfiguracionDetalle = intItemConfiguracionDetalle;
	}
	@Override
	public String toString() {
		return "ConfDetalleId [intPersEmpresaPk=" + intPersEmpresaPk
				+ ", intItemConfiguracion=" + intItemConfiguracion
				+ ", intItemConfiguracionDetalle="
				+ intItemConfiguracionDetalle + "]";
	}
	

}

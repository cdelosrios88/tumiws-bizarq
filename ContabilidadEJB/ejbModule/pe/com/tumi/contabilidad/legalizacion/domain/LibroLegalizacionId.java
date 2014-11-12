package pe.com.tumi.contabilidad.legalizacion.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

@SuppressWarnings("serial")
public class LibroLegalizacionId extends TumiDomain{

	private	Integer intPersEmpresa;
	private Integer	intParaLibroContable;
	private Integer	intItemLibroLegalizacion;
	
	
	public Integer getIntPersEmpresa() {
		return intPersEmpresa;
	}
	public void setIntPersEmpresa(Integer intPersEmpresa) {
		this.intPersEmpresa = intPersEmpresa;
	}
	public Integer getIntParaLibroContable() {
		return intParaLibroContable;
	}
	public void setIntParaLibroContable(Integer intParaLibroContable) {
		this.intParaLibroContable = intParaLibroContable;
	}
	public Integer getIntItemLibroLegalizacion() {
		return intItemLibroLegalizacion;
	}
	public void setIntItemLibroLegalizacion(Integer intItemLibroLegalizacion) {
		this.intItemLibroLegalizacion = intItemLibroLegalizacion;
	}
	@Override
	public String toString() {
		return "LibroLegalizacionId [intPersEmpresa=" + intPersEmpresa
				+ ", intParaLibroContable=" + intParaLibroContable
				+ ", intItemLibroLegalizacion=" + intItemLibroLegalizacion
				+ "]";
	}
	
	
}

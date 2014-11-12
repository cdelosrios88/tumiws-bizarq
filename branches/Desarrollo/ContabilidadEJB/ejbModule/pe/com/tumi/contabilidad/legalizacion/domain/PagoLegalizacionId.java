package pe.com.tumi.contabilidad.legalizacion.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

@SuppressWarnings("serial")
public class PagoLegalizacionId extends TumiDomain{

	private	Integer intPersEmpresa;
	private Integer	intParaLibroContable;
	private Integer	intItemLibroLegalizacion;
	private Integer	intItemPagoLegalizacion;
	
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
	public Integer getIntItemPagoLegalizacion() {
		return intItemPagoLegalizacion;
	}
	public void setIntItemPagoLegalizacion(Integer intItemPagoLegalizacion) {
		this.intItemPagoLegalizacion = intItemPagoLegalizacion;
	}
	@Override
	public String toString() {
		return "PagoLegalizacionId [intPersEmpresa=" + intPersEmpresa
				+ ", intParaLibroContable=" + intParaLibroContable
				+ ", intItemLibroLegalizacion=" + intItemLibroLegalizacion
				+ ", intItemPagoLegalizacion=" + intItemPagoLegalizacion + "]";
	}
	
	
}

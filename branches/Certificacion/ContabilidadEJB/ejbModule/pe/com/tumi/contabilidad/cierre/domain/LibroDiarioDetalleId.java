package pe.com.tumi.contabilidad.cierre.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class LibroDiarioDetalleId extends TumiDomain{

	private Integer intPersEmpresaLibro;
	private Integer intContPeriodoLibro;
	private Integer intContCodigoLibro;
	private Integer intContItemLibro;
	public Integer getIntPersEmpresaLibro() {
		return intPersEmpresaLibro;
	}
	public void setIntPersEmpresaLibro(Integer intPersEmpresaLibro) {
		this.intPersEmpresaLibro = intPersEmpresaLibro;
	}
	public Integer getIntContPeriodoLibro() {
		return intContPeriodoLibro;
	}
	public void setIntContPeriodoLibro(Integer intContPeriodoLibro) {
		this.intContPeriodoLibro = intContPeriodoLibro;
	}
	public Integer getIntContCodigoLibro() {
		return intContCodigoLibro;
	}
	public void setIntContCodigoLibro(Integer intContCodigoLibro) {
		this.intContCodigoLibro = intContCodigoLibro;
	}
	public Integer getIntContItemLibro() {
		return intContItemLibro;
	}
	public void setIntContItemLibro(Integer intContItemLibro) {
		this.intContItemLibro = intContItemLibro;
	}
	@Override
	public String toString() {
		return "LibroDiarioDetalleId [intPersEmpresaLibro="
				+ intPersEmpresaLibro + ", intContPeriodoLibro="
				+ intContPeriodoLibro + ", intContCodigoLibro="
				+ intContCodigoLibro + ", intContItemLibro=" + intContItemLibro
				+ "]";
	}
	
}

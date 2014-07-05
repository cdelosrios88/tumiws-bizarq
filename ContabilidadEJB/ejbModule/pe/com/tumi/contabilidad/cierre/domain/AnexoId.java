package pe.com.tumi.contabilidad.cierre.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class AnexoId extends TumiDomain{

	private Integer	intPersEmpresaAnexo;
	private Integer	intContPeriodoAnexo;
	private Integer	intParaTipoAnexo;
	
	public Integer getIntPersEmpresaAnexo() {
		return intPersEmpresaAnexo;
	}
	public void setIntPersEmpresaAnexo(Integer intPersEmpresaAnexo) {
		this.intPersEmpresaAnexo = intPersEmpresaAnexo;
	}
	public Integer getIntContPeriodoAnexo() {
		return intContPeriodoAnexo;
	}
	public void setIntContPeriodoAnexo(Integer intPersPeriodoAnexo) {
		this.intContPeriodoAnexo = intPersPeriodoAnexo;
	}
	public Integer getIntParaTipoAnexo() {
		return intParaTipoAnexo;
	}
	public void setIntParaTipoAnexo(Integer intParaTipoAnexo) {
		this.intParaTipoAnexo = intParaTipoAnexo;
	}
	@Override
	public String toString() {
		return "AnexoId [intPersEmpresaAnexo=" + intPersEmpresaAnexo
				+ ", intContPeriodoAnexo=" + intContPeriodoAnexo
				+ ", intParaTipoAnexo=" + intParaTipoAnexo + "]";
	}
}

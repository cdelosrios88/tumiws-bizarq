package pe.com.tumi.contabilidad.cierre.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class AnexoDetalleOperadorId extends TumiDomain{

	private Integer	intPersEmpresaAnexo;
	private Integer intContPeriodoAnexo;
	private Integer intParaTipoAnexo;	
	private	Integer	intItemAnexoDetalle;
	private	Integer	intItemOperador;
	
	public Integer getIntPersEmpresaAnexo() {
		return intPersEmpresaAnexo;
	}
	public void setIntPersEmpresaAnexo(Integer intPersEmpresaAnexo) {
		this.intPersEmpresaAnexo = intPersEmpresaAnexo;
	}
	public Integer getIntContPeriodoAnexo() {
		return intContPeriodoAnexo;
	}
	public void setIntContPeriodoAnexo(Integer intContPeriodoAnexo) {
		this.intContPeriodoAnexo = intContPeriodoAnexo;
	}
	public Integer getIntParaTipoAnexo() {
		return intParaTipoAnexo;
	}
	public void setIntParaTipoAnexo(Integer intParaTipoAnexo) {
		this.intParaTipoAnexo = intParaTipoAnexo;
	}
	public Integer getIntItemAnexoDetalle() {
		return intItemAnexoDetalle;
	}
	public void setIntItemAnexoDetalle(Integer intItemAnexoDetalle) {
		this.intItemAnexoDetalle = intItemAnexoDetalle;
	}
	public Integer getIntItemOperador() {
		return intItemOperador;
	}
	public void setIntItemOperador(Integer intItemOperador) {
		this.intItemOperador = intItemOperador;
	}
	@Override
	public String toString() {
		return "AnexoDetalleOperadorId [intPersEmpresaAnexo="
				+ intPersEmpresaAnexo + ", intContPeriodoAnexo="
				+ intContPeriodoAnexo + ", intParaTipoAnexo="
				+ intParaTipoAnexo + ", intItemAnexoDetalle="
				+ intItemAnexoDetalle + ", intItemOperador=" + intItemOperador
				+ "]";
	}


}
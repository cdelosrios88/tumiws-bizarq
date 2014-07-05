package pe.com.tumi.contabilidad.cierre.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class RatioDetalle extends TumiDomain implements Comparable<RatioDetalle>{

	
	private RatioDetalleId id;
	private	Integer	intParte;
	private	Integer	intParteGrupo;
	private	Integer	intPersEmpresaAnexo;
	private	Integer	intContPeriodoAnexo;
	private	Integer	intParaTipoAnexo;
	private	Integer	intAndeItemAnexoDetalle;
	private	Integer	intParaOperacionInterno;
	private	Integer	intPersEmpresaAnexo2;
	private	Integer	intContPeriodoAnexo2;
	private	Integer	intParaTipoAnexo2;
	private	Integer	intAndeItemAnexoDetalle2;
	private Integer	intParaOperacionExterno;
	
	private AnexoDetalle operando1;
	private AnexoDetalle operando2;
	
	//interfaz
	private boolean	habilitarAgregar;
	
	public RatioDetalle(){
		id = new RatioDetalleId();
		operando1 = new AnexoDetalle();
		operando2 = new AnexoDetalle();
	}
	
	public RatioDetalleId getId() {
		return id;
	}
	public void setId(RatioDetalleId id) {
		this.id = id;
	}
	public Integer getIntParte() {
		return intParte;
	}
	public void setIntParte(Integer intParte) {
		this.intParte = intParte;
	}
	public Integer getIntParteGrupo() {
		return intParteGrupo;
	}
	public void setIntParteGrupo(Integer intParteGrupo) {
		this.intParteGrupo = intParteGrupo;
	}
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
	public Integer getIntParaOperacionInterno() {
		return intParaOperacionInterno;
	}
	public void setIntParaOperacionInterno(Integer intParaOperacionInterno) {
		this.intParaOperacionInterno = intParaOperacionInterno;
	}
	public Integer getIntPersEmpresaAnexo2() {
		return intPersEmpresaAnexo2;
	}
	public void setIntPersEmpresaAnexo2(Integer intPersEmpresaAnexo2) {
		this.intPersEmpresaAnexo2 = intPersEmpresaAnexo2;
	}
	public Integer getIntContPeriodoAnexo2() {
		return intContPeriodoAnexo2;
	}
	public void setIntContPeriodoAnexo2(Integer intContPeriodoAnexo2) {
		this.intContPeriodoAnexo2 = intContPeriodoAnexo2;
	}
	public Integer getIntParaTipoAnexo2() {
		return intParaTipoAnexo2;
	}
	public void setIntParaTipoAnexo2(Integer intParaTipoAnexo2) {
		this.intParaTipoAnexo2 = intParaTipoAnexo2;
	}
	public Integer getIntParaOperacionExterno() {
		return intParaOperacionExterno;
	}
	public void setIntParaOperacionExterno(Integer intParaOperacionExterno) {
		this.intParaOperacionExterno = intParaOperacionExterno;
	}
	public Integer getIntAndeItemAnexoDetalle() {
		return intAndeItemAnexoDetalle;
	}
	public void setIntAndeItemAnexoDetalle(Integer intAndeItemAnexoDetalle) {
		this.intAndeItemAnexoDetalle = intAndeItemAnexoDetalle;
	}
	public Integer getIntAndeItemAnexoDetalle2() {
		return intAndeItemAnexoDetalle2;
	}
	public void setIntAndeItemAnexoDetalle2(Integer intAndeItemAnexoDetalle2) {
		this.intAndeItemAnexoDetalle2 = intAndeItemAnexoDetalle2;
	}
	public AnexoDetalle getOperando1() {
		return operando1;
	}
	public void setOperando1(AnexoDetalle operando1) {
		this.operando1 = operando1;
	}
	public AnexoDetalle getOperando2() {
		return operando2;
	}
	public void setOperando2(AnexoDetalle operando2) {
		this.operando2 = operando2;
	}
	public boolean isHabilitarAgregar() {
		return habilitarAgregar;
	}
	public void setHabilitarAgregar(boolean habilitarAgregar) {
		this.habilitarAgregar = habilitarAgregar;
	}

	
	public int compareTo(RatioDetalle otro) {
        return getIntParteGrupo().compareTo(otro.getIntParteGrupo());
    }
	
	@Override
	public String toString() {
		return "RatioDetalle [id=" + id + ", intParte=" + intParte
				+ ", intParteGrupo=" + intParteGrupo + ", intPersEmpresaAnexo="
				+ intPersEmpresaAnexo + ", intContPeriodoAnexo="
				+ intContPeriodoAnexo + ", intParaTipoAnexo="
				+ intParaTipoAnexo + ", intAndeItemAnexoDetalle="
				+ intAndeItemAnexoDetalle + ", intParaOperacionInterno="
				+ intParaOperacionInterno + ", intPersEmpresaAnexo2="
				+ intPersEmpresaAnexo2 + ", intContPeriodoAnexo2="
				+ intContPeriodoAnexo2 + ", intParaTipoAnexo2="
				+ intParaTipoAnexo2 + ", intAndeItemAnexoDetalle2="
				+ intAndeItemAnexoDetalle2 + ", intParaOperacionExterno="
				+ intParaOperacionExterno + "]";
	}
}
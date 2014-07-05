package pe.com.tumi.cobranza.cierremensual.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class CierreCobranzaPlanillaId extends TumiDomain {
	private Integer intPersEmpresaCierreCob;
	private Integer intParaTipoRegistro;
	private Integer intPeriodoCierre;
	private Integer intEstrucGrupo;
	private Integer intParaTipoSocio;
	private Integer intParaModalidad;
	
	public Integer getIntPersEmpresaCierreCob() {
		return intPersEmpresaCierreCob;
	}
	public void setIntPersEmpresaCierreCob(Integer intPersEmpresaCierreCob) {
		this.intPersEmpresaCierreCob = intPersEmpresaCierreCob;
	}
	public Integer getIntParaTipoRegistro() {
		return intParaTipoRegistro;
	}
	public void setIntParaTipoRegistro(Integer intParaTipoRegistro) {
		this.intParaTipoRegistro = intParaTipoRegistro;
	}
	public Integer getIntPeriodoCierre() {
		return intPeriodoCierre;
	}
	public void setIntPeriodoCierre(Integer intPeriodoCierre) {
		this.intPeriodoCierre = intPeriodoCierre;
	}
	public Integer getIntEstrucGrupo() {
		return intEstrucGrupo;
	}
	public void setIntEstrucGrupo(Integer intEstrucGrupo) {
		this.intEstrucGrupo = intEstrucGrupo;
	}
	public Integer getIntParaTipoSocio() {
		return intParaTipoSocio;
	}
	public void setIntParaTipoSocio(Integer intParaTipoSocio) {
		this.intParaTipoSocio = intParaTipoSocio;
	}
	public Integer getIntParaModalidad() {
		return intParaModalidad;
	}
	public void setIntParaModalidad(Integer intParaModalidad) {
		this.intParaModalidad = intParaModalidad;
	}
}
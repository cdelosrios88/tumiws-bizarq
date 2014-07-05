package pe.com.tumi.cobranza.cierremensual.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class CierreCobranzaOperacionId extends TumiDomain {
	private Integer intEmpresaCierreCob;
	private Integer intParaTipoRegistroCob;
	private Integer intPeriodoCierre;
	private Integer intParaTipoSolicitudCtaCte;
	
	public Integer getIntEmpresaCierreCob() {
		return intEmpresaCierreCob;
	}
	public void setIntEmpresaCierreCob(Integer intEmpresaCierreCob) {
		this.intEmpresaCierreCob = intEmpresaCierreCob;
	}
	public Integer getIntParaTipoRegistroCob() {
		return intParaTipoRegistroCob;
	}
	public void setIntParaTipoRegistroCob(Integer intParaTipoRegistroCob) {
		this.intParaTipoRegistroCob = intParaTipoRegistroCob;
	}
	public Integer getIntPeriodoCierre() {
		return intPeriodoCierre;
	}
	public void setIntPeriodoCierre(Integer intPeriodoCierre) {
		this.intPeriodoCierre = intPeriodoCierre;
	}
	public Integer getIntParaTipoSolicitudCtaCte() {
		return intParaTipoSolicitudCtaCte;
	}
	public void setIntParaTipoSolicitudCtaCte(Integer intParaTipoSolicitudCtaCte) {
		this.intParaTipoSolicitudCtaCte = intParaTipoSolicitudCtaCte;
	}
}
package pe.com.tumi.cobranza.gestion.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class GestorCobranzaId extends TumiDomain{
	
	private Integer intPersEmpresaPk;
	private Integer intPersPersonaGestorPk;
	private Integer intItemGestorCobranzaSuc;
	
	public Integer getIntPersEmpresaPk() {
		return intPersEmpresaPk;
	}
	public void setIntPersEmpresaPk(Integer intPersEmpresaPk) {
		this.intPersEmpresaPk = intPersEmpresaPk;
	}
	public Integer getIntPersPersonaGestorPk() {
		return intPersPersonaGestorPk;
	}
	public void setIntPersPersonaGestorPk(Integer intPersPersonaGestorPk) {
		this.intPersPersonaGestorPk = intPersPersonaGestorPk;
	}
	public Integer getIntItemGestorCobranzaSuc() {
		return intItemGestorCobranzaSuc;
	}
	public void setIntItemGestorCobranzaSuc(Integer intItemGestorCobranzaSuc) {
		this.intItemGestorCobranzaSuc = intItemGestorCobranzaSuc;
	}
	@Override
	public String toString() {
		return "GestorCobranzaId [intPersEmpresaPk=" + intPersEmpresaPk
				+ ", intPersPersonaGestorPk=" + intPersPersonaGestorPk
				+ ", intItemGestorCobranzaSuc=" + intItemGestorCobranzaSuc
				+ "]";
	}
}
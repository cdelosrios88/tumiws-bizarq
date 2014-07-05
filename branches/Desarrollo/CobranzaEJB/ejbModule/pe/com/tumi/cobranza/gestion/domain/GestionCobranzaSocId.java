package pe.com.tumi.cobranza.gestion.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class GestionCobranzaSocId extends TumiDomain {
	
	private Integer intPersEmpresaGestion;
	private Integer intItemGestionCobranza;
	private Integer intItemGestCobrSocio;
	
	
	public Integer getIntPersEmpresaGestion() {
		return intPersEmpresaGestion;
	}
	public void setIntPersEmpresaGestion(Integer intPersEmpresaGestion) {
		this.intPersEmpresaGestion = intPersEmpresaGestion;
	}
	public Integer getIntItemGestionCobranza() {
		return intItemGestionCobranza;
	}
	public void setIntItemGestionCobranza(Integer intItemGestionCobranza) {
		this.intItemGestionCobranza = intItemGestionCobranza;
	}
	public Integer getIntItemGestCobrSocio() {
		return intItemGestCobrSocio;
	}
	public void setIntItemGestCobrSocio(Integer intItemGestCobrSocio) {
		this.intItemGestCobrSocio = intItemGestCobrSocio;
	}
	
}

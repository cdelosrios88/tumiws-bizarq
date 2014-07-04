package pe.com.tumi.cobranza.gestion.domain;

import java.util.Date;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class GestionCobranzaCierreId extends TumiDomain{

	private Integer intPersEmpresaCierreGestCob;
	private Integer intItemCierreGesCobCie;
	private Date    dtFechaCierre;
	
	public Integer getIntPersEmpresaCierreGestCob() {
		return intPersEmpresaCierreGestCob;
	}
	public void setIntPersEmpresaCierreGestCob(Integer intPersEmpresaCierreGestCob) {
		this.intPersEmpresaCierreGestCob = intPersEmpresaCierreGestCob;
	}
	public Integer getIntItemCierreGesCobCie() {
		return intItemCierreGesCobCie;
	}
	public void setIntItemCierreGesCobCie(Integer intItemCierreGesCobCie) {
		this.intItemCierreGesCobCie = intItemCierreGesCobCie;
	}
	public Date getDtFechaCierre() {
		return dtFechaCierre;
	}
	public void setDtFechaCierre(Date dtFechaCierre) {
		this.dtFechaCierre = dtFechaCierre;
	}
	
	
	
	
	
}	
	
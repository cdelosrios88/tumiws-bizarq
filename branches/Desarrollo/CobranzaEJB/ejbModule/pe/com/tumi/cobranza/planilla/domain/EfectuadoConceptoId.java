package pe.com.tumi.cobranza.planilla.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class EfectuadoConceptoId extends TumiDomain{

 	private java.lang.Integer intEmpresaCuentaEnvioPk;
 	private java.lang.Integer intItemEfectuado;
 	private java.lang.Integer intItemEfectuadoConcepto;
 	
	public java.lang.Integer getIntEmpresaCuentaEnvioPk() {
		return intEmpresaCuentaEnvioPk;
	}
	public void setIntEmpresaCuentaEnvioPk(java.lang.Integer intEmpresaCuentaEnvioPk) {
		this.intEmpresaCuentaEnvioPk = intEmpresaCuentaEnvioPk;
	}
	
	public java.lang.Integer getIntItemEfectuado() {
		return intItemEfectuado;
	}
	public void setIntItemEfectuado(java.lang.Integer intItemEfectuado) {
		this.intItemEfectuado = intItemEfectuado;
	}
	
	public java.lang.Integer getIntItemEfectuadoConcepto() {
		return intItemEfectuadoConcepto;
	}
	public void setIntItemEfectuadoConcepto(
			java.lang.Integer intItemEfectuadoConcepto) {
		this.intItemEfectuadoConcepto = intItemEfectuadoConcepto;
	}
	@Override
	public String toString() {
		return "EfectuadoConceptoId [intItemEfectuado=" + intItemEfectuado
				+ "]";
	}
	
}

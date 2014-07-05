package pe.com.tumi.cobranza.planilla.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class EfectuadoId extends TumiDomain{

     	private java.lang.Integer intEmpresacuentaPk;
     	private java.lang.Integer intItemefectuado;
     	
		public java.lang.Integer getIntEmpresacuentaPk() {
			return intEmpresacuentaPk;
		}
		public void setIntEmpresacuentaPk(java.lang.Integer intEmpresacuentaPk) {
			this.intEmpresacuentaPk = intEmpresacuentaPk;
		}
		public java.lang.Integer getIntItemefectuado() {
			return intItemefectuado;
		}
		public void setIntItemefectuado(java.lang.Integer intItemefectuado) {
			this.intItemefectuado = intItemefectuado;
		}
		@Override
		public String toString() {
			return "EfectuadoId [intEmpresacuentaPk=" + intEmpresacuentaPk
					+ ", intItemefectuado=" + intItemefectuado + "]";
		}
		
}

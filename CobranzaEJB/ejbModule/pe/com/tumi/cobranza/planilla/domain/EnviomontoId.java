package pe.com.tumi.cobranza.planilla.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class EnviomontoId extends TumiDomain{

     	private java.lang.Integer intItemenvioconcepto;
     	private java.lang.Integer intItemenviomonto;
     	private java.lang.Integer intEmpresacuentaPk;
		
		public java.lang.Integer getIntItemenvioconcepto(){
			return this.intItemenvioconcepto;
		}
		public void setIntItemenvioconcepto(java.lang.Integer intItemenvioconcepto){
			this.intItemenvioconcepto = intItemenvioconcepto;
		}
		
		public java.lang.Integer getIntItemenviomonto(){
			return this.intItemenviomonto;
		}
		public void setIntItemenviomonto(java.lang.Integer intItemenviomonto){
			this.intItemenviomonto = intItemenviomonto;
		}
		
		public java.lang.Integer getIntEmpresacuentaPk(){
			return this.intEmpresacuentaPk;
		}
		public void setIntEmpresacuentaPk(java.lang.Integer intEmpresacuentaPk){
			this.intEmpresacuentaPk = intEmpresacuentaPk;
		}
		
		@Override
		public String toString() {
			return "EnviomontoId [intEmpresacuentaPk=" + intEmpresacuentaPk
					+ ", intItemenvioconcepto=" + intItemenvioconcepto + ", intItemenviomonto="
					+ intItemenviomonto +  "]";
		}
}

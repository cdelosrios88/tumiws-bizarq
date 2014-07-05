package pe.com.tumi.cobranza.planilla.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class EnvioconceptoId extends TumiDomain{

		private java.lang.Integer intEmpresacuentaPk; 	
		private java.lang.Integer intItemenvioconcepto;
     	private java.lang.Integer intItemenvioconceptoDet;
     	
		public java.lang.Integer getIntItemenvioconcepto(){
			return this.intItemenvioconcepto;
		}
		public void setIntItemenvioconcepto(java.lang.Integer intItemenvioconcepto){
			this.intItemenvioconcepto = intItemenvioconcepto;
		}
		
		public java.lang.Integer getIntEmpresacuentaPk(){
			return this.intEmpresacuentaPk;
		}
		public void setIntEmpresacuentaPk(java.lang.Integer intEmpresacuentaPk){
			this.intEmpresacuentaPk = intEmpresacuentaPk;
		}
		
		public java.lang.Integer getIntItemenvioconceptoDet() {
			return intItemenvioconceptoDet;
		}
		public void setIntItemenvioconceptoDet(java.lang.Integer intItemenvioconceptoDet) {
			this.intItemenvioconceptoDet = intItemenvioconceptoDet;
		}
		@Override
		public String toString() {
			return "EnvioconceptoId [intEmpresacuentaPk=" + intEmpresacuentaPk
					+ ", intItemenvioconcepto=" + intItemenvioconcepto + ", intItemenvioconceptoDet="
					+ intItemenvioconceptoDet +  "]";
		}
}

package pe.com.tumi.cobranza.planilla.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class EnvioresumenId extends TumiDomain{

     	private java.lang.Integer intItemenvioresumen;
     	private java.lang.Integer intEmpresaPk;
		
		public java.lang.Integer getIntItemenvioresumen(){
			return this.intItemenvioresumen;
		}
		public void setIntItemenvioresumen(java.lang.Integer intItemenvioresumen){
			this.intItemenvioresumen = intItemenvioresumen;
		}
		
		public java.lang.Integer getIntEmpresaPk(){
			return this.intEmpresaPk;
		}
		public void setIntEmpresaPk(java.lang.Integer intEmpresaPk){
			this.intEmpresaPk = intEmpresaPk;
		}
		@Override
		public String toString() {
			return "EnvioresumenId [intItemenvioresumen=" + intItemenvioresumen
					+ ", intEmpresaPk=" + intEmpresaPk +   "]";
		}
}



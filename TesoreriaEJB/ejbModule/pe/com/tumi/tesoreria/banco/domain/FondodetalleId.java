package pe.com.tumi.tesoreria.banco.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class FondodetalleId extends TumiDomain{

     	private java.lang.Integer intEmpresaPk;
     	private java.lang.Integer intItembancofondo;
     	private java.lang.Integer intItemfondodetalle;
		
		public java.lang.Integer getIntEmpresaPk(){
			return this.intEmpresaPk;
		}
		public void setIntEmpresaPk(java.lang.Integer intEmpresaPk){
			this.intEmpresaPk = intEmpresaPk;
		}
		
		public java.lang.Integer getIntItembancofondo(){
			return this.intItembancofondo;
		}
		public void setIntItembancofondo(java.lang.Integer intItembancofondo){
			this.intItembancofondo = intItembancofondo;
		}
		
		public java.lang.Integer getIntItemfondodetalle(){
			return this.intItemfondodetalle;
		}
		public void setIntItemfondodetalle(java.lang.Integer intItemfondodetalle){
			this.intItemfondodetalle = intItemfondodetalle;
		}
		@Override
		public String toString() {
			return "FondodetalleId [intEmpresaPk=" + intEmpresaPk
					+ ", intItembancofondo=" + intItembancofondo
					+ ", intItemfondodetalle=" + intItemfondodetalle + "]";
		}
		
}

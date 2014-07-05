package pe.com.tumi.tesoreria.banco.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class BancocuentaId extends TumiDomain{

     	private java.lang.Integer intEmpresaPk;
     	private java.lang.Integer intItembancocuenta;
     	private java.lang.Integer intItembancofondo;
		
		public java.lang.Integer getIntEmpresaPk(){
			return this.intEmpresaPk;
		}
		public void setIntEmpresaPk(java.lang.Integer intEmpresaPk){
			this.intEmpresaPk = intEmpresaPk;
		}
		
		public java.lang.Integer getIntItembancocuenta(){
			return this.intItembancocuenta;
		}
		public void setIntItembancocuenta(java.lang.Integer intItembancocuenta){
			this.intItembancocuenta = intItembancocuenta;
		}
		
		public java.lang.Integer getIntItembancofondo(){
			return this.intItembancofondo;
		}
		public void setIntItembancofondo(java.lang.Integer intItembancofondo){
			this.intItembancofondo = intItembancofondo;
		}
		@Override
		public String toString() {
			return "BancocuentaId [intEmpresaPk=" + intEmpresaPk
					+ ", intItembancocuenta=" + intItembancocuenta
					+ ", intItembancofondo=" + intItembancofondo + "]";
		}
		
}

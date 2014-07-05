package pe.com.tumi.contabilidad.core.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class PlanCuentaId extends TumiDomain{

     	private String strNumeroCuenta;
     	private Integer intPeriodoCuenta;
     	private Integer intEmpresaCuentaPk;
     	
     	public PlanCuentaId(){
     		super();
     	}
     	
     	public PlanCuentaId(Integer pIntEmpresaCuentaPk, Integer pIntPeriodoCuenta, String pStrNumeroCuenta){
     		this.intEmpresaCuentaPk = pIntEmpresaCuentaPk;
     		this.intPeriodoCuenta = pIntPeriodoCuenta;
     		this.strNumeroCuenta = pStrNumeroCuenta;
     	}
     	
		public java.lang.String getStrNumeroCuenta() {
			return strNumeroCuenta;
		}
		public void setStrNumeroCuenta(java.lang.String strNumeroCuenta) {
			this.strNumeroCuenta = strNumeroCuenta;
		}
		public java.lang.Integer getIntPeriodoCuenta() {
			return intPeriodoCuenta;
		}
		public void setIntPeriodoCuenta(java.lang.Integer intPeriodoCuenta) {
			this.intPeriodoCuenta = intPeriodoCuenta;
		}
		public java.lang.Integer getIntEmpresaCuentaPk() {
			return intEmpresaCuentaPk;
		}
		public void setIntEmpresaCuentaPk(java.lang.Integer intEmpresaCuentaPk) {
			this.intEmpresaCuentaPk = intEmpresaCuentaPk;
		}
		@Override
		public String toString() {
			return "PlanCuentaId [strNumeroCuenta=" + strNumeroCuenta
					+ ", intPeriodoCuenta=" + intPeriodoCuenta
					+ ", intEmpresaCuentaPk=" + intEmpresaCuentaPk + "]";
		}
}

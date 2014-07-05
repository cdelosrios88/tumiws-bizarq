package pe.com.tumi.contabilidad.core.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class ModeloDetalleId extends TumiDomain{

     	private Integer intEmpresaPk;
     	private Integer intCodigoModelo;
     	private Integer intPersEmpresaCuenta;
     	private Integer intContPeriodoCuenta;
     	private String 	strContNumeroCuenta;
     	
		public Integer getIntEmpresaPk() {
			return intEmpresaPk;
		}
		public void setIntEmpresaPk(Integer intEmpresaPk) {
			this.intEmpresaPk = intEmpresaPk;
		}
		public Integer getIntCodigoModelo() {
			return intCodigoModelo;
		}
		public void setIntCodigoModelo(Integer intCodigoModelo) {
			this.intCodigoModelo = intCodigoModelo;
		}
		public Integer getIntPersEmpresaCuenta() {
			return intPersEmpresaCuenta;
		}
		public void setIntPersEmpresaCuenta(Integer intPersEmpresaCuenta) {
			this.intPersEmpresaCuenta = intPersEmpresaCuenta;
		}
		public Integer getIntContPeriodoCuenta() {
			return intContPeriodoCuenta;
		}
		public void setIntContPeriodoCuenta(Integer intContPeriodoCuenta) {
			this.intContPeriodoCuenta = intContPeriodoCuenta;
		}
		public String getStrContNumeroCuenta() {
			return strContNumeroCuenta;
		}
		public void setStrContNumeroCuenta(String strContNumeroCuenta) {
			this.strContNumeroCuenta = strContNumeroCuenta;
		}
		@Override
		public String toString() {
			return "ModeloDetalleId [intEmpresaPk=" + intEmpresaPk
					+ ", intCodigoModelo=" + intCodigoModelo
					+ ", intPersEmpresaCuenta=" + intPersEmpresaCuenta
					+ ", intContPeriodoCuenta=" + intContPeriodoCuenta
					+ ", strContNumeroCuenta=" + strContNumeroCuenta + "]";
		}
		
}

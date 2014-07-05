package pe.com.tumi.contabilidad.core.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class ModeloId extends TumiDomain{

     	private Integer intEmpresaPk;
     	private Integer intCodigoModelo;
     	
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
		@Override
		public String toString() {
			return "ModeloId [intEmpresaPk=" + intEmpresaPk
					+ ", intCodigoModelo=" + intCodigoModelo + "]";
		}
		
		
}

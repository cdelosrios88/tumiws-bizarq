package pe.com.tumi.cobranza.planilla.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class TransferenciaId extends TumiDomain{
	
	private Integer intPersEmpresaTransferencia;
	private Integer intCcobItemtransferencia;
	
	public Integer getIntPersEmpresaTransferencia() {
		return intPersEmpresaTransferencia;
	}
	public void setIntPersEmpresaTransferencia(Integer intPersEmpresaTransferencia) {
		this.intPersEmpresaTransferencia = intPersEmpresaTransferencia;
	}
	public Integer getIntCcobItemtransferencia() {
		return intCcobItemtransferencia;
	}
	public void setIntCcobItemtransferencia(Integer intCcobItemtransferencia) {
		this.intCcobItemtransferencia = intCcobItemtransferencia;
	}
	
	
	
}
package pe.com.tumi.movimiento.concepto.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class EstadoConceptoExpedienteId extends TumiDomain{

	private Integer intPersEmpresa;
	private Integer intItemEstadoConceptoExpediente;
	
	public Integer getIntPersEmpresa() {
		return intPersEmpresa;
	}
	public void setIntPersEmpresa(Integer intPersEmpresa) {
		this.intPersEmpresa = intPersEmpresa;
	}
	public Integer getIntItemEstadoConceptoExpediente() {
		return intItemEstadoConceptoExpediente;
	}
	public void setIntItemEstadoConceptoExpediente(Integer intItemEstadoConceptoExpediente) {
		this.intItemEstadoConceptoExpediente = intItemEstadoConceptoExpediente;
	}
	
	@Override
	public String toString() {
		return "EstadoConceptoExpedienteId [intPersEmpresa=" + intPersEmpresa
				+ ", intItemEstadoConceptoExpediente="
				+ intItemEstadoConceptoExpediente + "]";
	}
}
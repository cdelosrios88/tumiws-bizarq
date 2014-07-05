package pe.com.tumi.servicio.prevision.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class AutorizaPrevisionId extends TumiDomain{
	private Integer 	intPersEmpresaPrevisionPk;
	private Integer 	intCuentaPk;
	private Integer 	intItemExpediente;
	private Integer 	intItemAutoriza;
	
	

	public Integer getIntPersEmpresaPrevisionPk() {
		return intPersEmpresaPrevisionPk;
	}
	public void setIntPersEmpresaPrevisionPk(Integer intPersEmpresaPrevisionPk) {
		this.intPersEmpresaPrevisionPk = intPersEmpresaPrevisionPk;
	}
	public Integer getIntCuentaPk() {
		return intCuentaPk;
	}
	public void setIntCuentaPk(Integer intCuentaPk) {
		this.intCuentaPk = intCuentaPk;
	}
	public Integer getIntItemExpediente() {
		return intItemExpediente;
	}
	public void setIntItemExpediente(Integer intItemExpediente) {
		this.intItemExpediente = intItemExpediente;
	}
	public Integer getIntItemAutoriza() {
		return intItemAutoriza;
	}
	public void setIntItemAutoriza(Integer intItemAutoriza) {
		this.intItemAutoriza = intItemAutoriza;
	}
	
	
}

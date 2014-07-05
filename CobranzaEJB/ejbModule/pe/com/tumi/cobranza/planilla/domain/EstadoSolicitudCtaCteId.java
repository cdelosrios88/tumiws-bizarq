package pe.com.tumi.cobranza.planilla.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class EstadoSolicitudCtaCteId extends TumiDomain{
	
	private Integer intPersEmpresaSolctacte;
	private Integer intCcobItemSolCtaCte;
	private Integer intCcobItemEstado;
	
	public Integer getIntPersEmpresaSolctacte() {
		return intPersEmpresaSolctacte;
	}
	public void setIntPersEmpresaSolctacte(Integer intPersEmpresaSolctacte) {
		this.intPersEmpresaSolctacte = intPersEmpresaSolctacte;
	}
	public Integer getIntCcobItemSolCtaCte() {
		return intCcobItemSolCtaCte;
	}
	public void setIntCcobItemSolCtaCte(Integer intCcobItemSolCtaCte) {
		this.intCcobItemSolCtaCte = intCcobItemSolCtaCte;
	}
	public Integer getIntCcobItemEstado() {
		return intCcobItemEstado;
	}
	public void setIntCcobItemEstado(Integer intCcobItemEstado) {
		this.intCcobItemEstado = intCcobItemEstado;
	}
	
}
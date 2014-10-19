package pe.com.tumi.tesoreria.cierreLogisticaOper.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class CierreLogisticaOperId extends TumiDomain{

	private Integer intPersEmpresaCietes;
	private Integer intParaTipoRegistroLogis;
	private Integer intCcobPeriodoCierre;
	
	
	public Integer getIntPersEmpresaCietes() {
		return intPersEmpresaCietes;
	}
	public void setIntPersEmpresaCietes(Integer intPersEmpresaCietes) {
		this.intPersEmpresaCietes = intPersEmpresaCietes;
	}
	public Integer getIntCcobPeriodoCierre() {
		return intCcobPeriodoCierre;
	}
	public void setIntCcobPeriodoCierre(Integer intCcobPeriodoCierre) {
		this.intCcobPeriodoCierre = intCcobPeriodoCierre;
	}
	public Integer getIntParaTipoRegistroLogis() {
		return intParaTipoRegistroLogis;
	}
	public void setIntParaTipoRegistroLogis(Integer intParaTipoRegistroLogis) {
		this.intParaTipoRegistroLogis = intParaTipoRegistroLogis;
	}
}

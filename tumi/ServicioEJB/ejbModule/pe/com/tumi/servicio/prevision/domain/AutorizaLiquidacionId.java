package pe.com.tumi.servicio.prevision.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class AutorizaLiquidacionId extends TumiDomain{
	private Integer 	intPersEmpresaLiquidacionPk;
	private Integer 	intItemExpedienteLiqui;
	private Integer 	intItemAutoriza;
	
	
	
	public Integer getIntPersEmpresaLiquidacionPk() {
		return intPersEmpresaLiquidacionPk;
	}
	public void setIntPersEmpresaLiquidacionPk(Integer intPersEmpresaLiquidacionPk) {
		this.intPersEmpresaLiquidacionPk = intPersEmpresaLiquidacionPk;
	}
	public Integer getIntItemExpedienteLiqui() {
		return intItemExpedienteLiqui;
	}
	public void setIntItemExpedienteLiqui(Integer intItemExpedienteLiqui) {
		this.intItemExpedienteLiqui = intItemExpedienteLiqui;
	}
	public Integer getIntItemAutoriza() {
		return intItemAutoriza;
	}
	public void setIntItemAutoriza(Integer intItemAutoriza) {
		this.intItemAutoriza = intItemAutoriza;
	}
	
}

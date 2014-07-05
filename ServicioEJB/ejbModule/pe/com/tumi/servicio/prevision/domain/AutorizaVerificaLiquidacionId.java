package pe.com.tumi.servicio.prevision.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class AutorizaVerificaLiquidacionId extends TumiDomain{
	private Integer 	intPersEmpresaLiquidacionPk;
	private Integer 	intItemExpedienteLiqui;
	private Integer 	intItemAutorizaVerifica;
	
	
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
	public Integer getIntItemAutorizaVerifica() {
		return intItemAutorizaVerifica;
	}
	public void setIntItemAutorizaVerifica(Integer intItemAutorizaVerifica) {
		this.intItemAutorizaVerifica = intItemAutorizaVerifica;
	}
	
	
}

package pe.com.tumi.credito.socio.creditos.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class CreditoGarantiaTipoValorVentaId extends TumiDomain {
	private Integer	intPersEmpresaPk;
	private Integer intParaTipoCreditoCod;
	private Integer intItemCredito;
	private Integer intParaTipoGarantiaCod;
	private Integer intItemCreditoGarantia;
	private Integer intParaTipoValorVentaCod;
	
	//Getters y Setters
	public Integer getIntPersEmpresaPk() {
		return intPersEmpresaPk;
	}
	public void setIntPersEmpresaPk(Integer intPersEmpresaPk) {
		this.intPersEmpresaPk = intPersEmpresaPk;
	}
	public Integer getIntParaTipoCreditoCod() {
		return intParaTipoCreditoCod;
	}
	public void setIntParaTipoCreditoCod(Integer intParaTipoCreditoCod) {
		this.intParaTipoCreditoCod = intParaTipoCreditoCod;
	}
	public Integer getIntItemCredito() {
		return intItemCredito;
	}
	public void setIntItemCredito(Integer intItemCredito) {
		this.intItemCredito = intItemCredito;
	}
	public Integer getIntParaTipoGarantiaCod() {
		return intParaTipoGarantiaCod;
	}
	public void setIntParaTipoGarantiaCod(Integer intParaTipoGarantiaCod) {
		this.intParaTipoGarantiaCod = intParaTipoGarantiaCod;
	}
	public Integer getIntItemCreditoGarantia() {
		return intItemCreditoGarantia;
	}
	public void setIntItemCreditoGarantia(Integer intItemCreditoGarantia) {
		this.intItemCreditoGarantia = intItemCreditoGarantia;
	}
	public Integer getIntParaTipoValorVentaCod() {
		return intParaTipoValorVentaCod;
	}
	public void setIntParaTipoValorVentaCod(Integer intParaTipoValorVentaCod) {
		this.intParaTipoValorVentaCod = intParaTipoValorVentaCod;
	}
}

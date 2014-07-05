package pe.com.tumi.credito.socio.creditos.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class CreditoExcepcionAporteNoTransId extends TumiDomain {
	private Integer	intPersEmpresaPk;
	private Integer intParaTipoCreditoCod;
	private Integer intItemCredito;
	private Integer intItemCreditoExcepcion;
	private Integer intParaTipoCaptacionCod;
	
	
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
	public Integer getIntItemCreditoExcepcion() {
		return intItemCreditoExcepcion;
	}
	public void setIntItemCreditoExcepcion(Integer intItemCreditoExcepcion) {
		this.intItemCreditoExcepcion = intItemCreditoExcepcion;
	}
	public Integer getIntParaTipoCaptacionCod() {
		return intParaTipoCaptacionCod;
	}
	public void setIntParaTipoCaptacionCod(Integer intParaTipoCaptacionCod) {
		this.intParaTipoCaptacionCod = intParaTipoCaptacionCod;
	}
}

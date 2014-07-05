package pe.com.tumi.riesgo.cartera.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class CarteraCreditoId extends TumiDomain{

	private Integer	intPersEmpresaCartera;
	private Integer	intCriePeriodoCartera;
	private Integer	intCrieItemCarteraCredito;
	public Integer getIntPersEmpresaCartera() {
		return intPersEmpresaCartera;
	}
	public void setIntPersEmpresaCartera(Integer intPersEmpresaCartera) {
		this.intPersEmpresaCartera = intPersEmpresaCartera;
	}
	public Integer getIntCriePeriodoCartera() {
		return intCriePeriodoCartera;
	}
	public void setIntCriePeriodoCartera(Integer intCriePeriodoCartera) {
		this.intCriePeriodoCartera = intCriePeriodoCartera;
	}
	public Integer getIntCrieItemCarteraCredito() {
		return intCrieItemCarteraCredito;
	}
	public void setIntCrieItemCarteraCredito(Integer intCrieItemCarteraCredito) {
		this.intCrieItemCarteraCredito = intCrieItemCarteraCredito;
	}
	
	
}

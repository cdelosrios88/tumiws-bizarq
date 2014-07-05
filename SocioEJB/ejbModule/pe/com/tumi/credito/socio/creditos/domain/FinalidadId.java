package pe.com.tumi.credito.socio.creditos.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class FinalidadId extends TumiDomain {
	private Integer	intPersEmpresaPk;
	private Integer intParaTipoCreditoCod;
	private Integer intItemCredito;
	private Integer intParaFinalidadCod;
	
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
	public Integer getIntParaFinalidadCod() {
		return intParaFinalidadCod;
	}
	public void setIntParaFinalidadCod(Integer intParaFinalidadCod) {
		this.intParaFinalidadCod = intParaFinalidadCod;
	}
}

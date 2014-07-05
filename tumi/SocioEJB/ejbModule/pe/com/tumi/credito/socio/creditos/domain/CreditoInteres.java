package pe.com.tumi.credito.socio.creditos.domain;

import java.math.BigDecimal;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class CreditoInteres extends TumiDomain {
	private CreditoInteresId id;
	private BigDecimal bdTasaInteres;
	private Integer intMesMaximo;
	private BigDecimal bdMontoMaximo;
	private Integer intParaEstadoCod;
	private Boolean chkCreditoInteres;
	private Integer intParaTipoSocio;
	
	
	//Getters y Setters
	public CreditoInteresId getId() {
		return id;
	}
	public void setId(CreditoInteresId id) {
		this.id = id;
	}
	public BigDecimal getBdTasaInteres() {
		return bdTasaInteres;
	}
	public void setBdTasaInteres(BigDecimal bdTasaInteres) {
		this.bdTasaInteres = bdTasaInteres;
	}
	public Integer getIntMesMaximo() {
		return intMesMaximo;
	}
	public void setIntMesMaximo(Integer intMesMaximo) {
		this.intMesMaximo = intMesMaximo;
	}
	public BigDecimal getBdMontoMaximo() {
		return bdMontoMaximo;
	}
	public void setBdMontoMaximo(BigDecimal bdMontoMaximo) {
		this.bdMontoMaximo = bdMontoMaximo;
	}
	public Integer getIntParaEstadoCod() {
		return intParaEstadoCod;
	}
	public void setIntParaEstadoCod(Integer intParaEstadoCod) {
		this.intParaEstadoCod = intParaEstadoCod;
	}
	public Boolean getChkCreditoInteres() {
		return chkCreditoInteres;
	}
	public void setChkCreditoInteres(Boolean chkCreditoInteres) {
		this.chkCreditoInteres = chkCreditoInteres;
	}
	public Integer getIntParaTipoSocio() {
		return intParaTipoSocio;
	}
	public void setIntParaTipoSocio(Integer intParaTipoSocio) {
		this.intParaTipoSocio = intParaTipoSocio;
	}
	
}